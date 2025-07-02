// stompSingleton.js
import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'

let stompClient = null

export function getStompClient(uuid, token,onConnectedCallback) {
  if (stompClient && stompClient.connected){
    if (onConnectedCallback) onConnectedCallback(stompClient)
      return stompClient}

  stompClient = new Client({
    webSocketFactory: () => new SockJS('/ws'),
    connectHeaders: {
      sender: uuid,
      Authorization: `Bearer ${token}`,
    },
    onConnect: () => {
      console.log("Singleton 연결됨")
      if (onConnectedCallback) onConnectedCallback(stompClient)
    },
    onStompError: (error) => {
      console.error("에러", error)
      // if (error.headers.message.includes("만료된 토큰")) {
      //   alert("로그인 시간이 만료되었습니다. 다시 로그인해주세요.");
      //   stompClient.deactivate();
      //   router.push('/login')
      // }
    },
  })

  stompClient.activate()
  return stompClient
}

export function subscribeChannel(client, destination, callback) {
    if (!client || !client.connected) {
      console.warn("STOMP 클라이언트가 연결되지 않았습니다.");
      return null;
    }
  
    try {
      return client.subscribe(destination, (message) => {
        try {
          const payload = JSON.parse(message.body);
          callback(payload);
        } catch (e) {
          console.error("메시지 파싱 오류:", e);
        }
      });
    } catch (err) {
      console.error(`구독 실패 (${destination}):`, err);
      return null;
    }
  }
  

  export function sendMsg(client, destination, Object) {
    if (!client || !client.connected) {
      console.warn("메시지를 보낼 수 없습니다. STOMP 클라이언트가 연결되지 않았습니다.");
      return;
    }
  
    try {
      client.publish({
        destination,
        body: JSON.stringify(Object),
      });
    } catch (err) {
      console.error(`메시지 전송 실패 (${destination}):`, err);
    }
  }
  