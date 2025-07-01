package com.emr.slgi.config;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.emr.slgi.chat.DAO.ChatJoinDAO;
import com.emr.slgi.util.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor{
	private final JwtUtil jwtUtil;
	private final ChatJoinDAO chatJoinDAO;
	
	@Value("${jwt.access-token-secret}")
    private String jwtSecret;
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel){
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		if (StompCommand.CONNECT == accessor.getCommand()) {
		    String token = accessor.getFirstNativeHeader("Authorization");
		    
		    if (token == null || token.isBlank()) {
		        throw new IllegalArgumentException("로그인된 사용자만 접속 가능합니다.");
		    }
		    Claims jwt = jwtUtil.parseToken(token.replace("Bearer ", ""), jwtSecret);
		    String uuid = jwt.get("uuid",String.class);
		    Date expiration =  jwt.getExpiration();
		    
		    if (expiration.getTime() < System.currentTimeMillis()) {
		        throw new IllegalArgumentException("만료된 토큰입니다.");
		    }
		   accessor.getSessionAttributes().put("uuid", uuid);
		   accessor.getSessionAttributes().put("tokenExp", expiration.getTime());

		}
		else if(StompCommand.SUBSCRIBE == accessor.getCommand()) {
			log.info("UNSUBSCRIBE = {}", StompCommand.SUBSCRIBE);
            String destination = accessor.getDestination();
            if (destination != null && destination.startsWith("/sub/chatroom/")) {
                String roomId = destination.substring("/sub/chatroom/".length());
                String subscriptionId = accessor.getSubscriptionId();
                accessor.getSessionAttributes().put("roomId", roomId);
                accessor.getSessionAttributes().put(subscriptionId, roomId); 
                String uuid = (String) accessor.getSessionAttributes().get("uuid");               
                chatJoinDAO.updateJoinTime(roomId, uuid);
            }
		}
		
		else if(StompCommand.UNSUBSCRIBE == accessor.getCommand()) {
			log.info("UNSUBSCRIBE = {}", StompCommand.UNSUBSCRIBE);
			String subscriptionId = accessor.getSubscriptionId();
			String roomId = (String) accessor.getSessionAttributes().get(subscriptionId);
		    String uuid = (String) accessor.getSessionAttributes().get("uuid");
		    if (roomId != null && uuid != null) {
		    	chatJoinDAO.updateOutTime(roomId, uuid);
            }
		}
		
		return message;
	}
	

}