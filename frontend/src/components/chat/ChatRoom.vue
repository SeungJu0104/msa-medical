<template>
    <div class="chat-container">
      <h3>채팅방</h3>
      
      {{ roomId }},{{state.roomName}}
      <div class="chat-box">
        <div v-for="(msg, idx) in state.messages" :key="idx"
        :class="msg.uuid === uuid ? 'my-msg' : 'other-msg'">
          [{{ msg.uuid }}] {{ msg.content }}
        </div>
      </div>
      <input v-model="state.content" @keyup.enter="sendMessage" placeholder="메시지를 입력하세요" />
      <button @click="sendMessage">전송</button>

      <button @click="exit">나가기</button>
      
    </div>
</template>

<script setup>
import { useAuthStore } from '@/stores/counter';
import { customFetch } from '@/util/customFetch';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

import { onMounted, reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';
    const route = useRoute()
    const router = useRouter()
    const auth = useAuthStore()
    const uuid = auth.user.uuid
    const roomId= route.params.roomId

    const state = reactive({
        content:'',
        messages:[],
        roomName: ''
    })

    // stomp 연결 및 읽음 처리(실시간 읽음 처리)
    const client = new Client({
        webSocketFactory:() => new SockJS('/ws'),
        connectHeaders:{
            sender:uuid
        },
        onConnect: ()=>{
            console.log("연결성공")
            client.subscribe(`/sub/chatroom/${roomId}`,(message) => {
                const msg = JSON.parse(message.body)
                state.messages.push(msg)

            if(msg.uuid !== uuid){
                const data = {
                    roomId,
                    uuid,
                    messageId: msg.messageId
                }
                customFetch({
                    url : `/chatread/readtime`,
                    method:"POST",
                    data
                })
                .then(({data,status}) => {
                    if(status===200){
                        console.log(data)
                    }
                })
                .catch(error=> {
                    console.log("읽음처리 실패 ")
                    console.log(error)
                })
            }
            })
        },
        onStompError: (e) =>{
        console.error('연결 실패 : ' ,e)
  }
    }) 
    //메세지 전송
    const sendMessage = () =>{
        if(state.content.trim() && client.connected){
            client.publish({
                destination : `/pub/chat/message`,
                body: JSON.stringify({
                    roomId,
                    uuid,
                    content:state.content
                })
            })
            state.content=''
        }
    }
    // 채팅기록 가져오기
    const loadChatMessage = () => {
        customFetch({
            url:`/chatmessage/messageList/${roomId}`,
            method:"GET"
        })
        .then(({data,status})=>{
            if(status===200 && data.length >0){
                state.messages=data
                // 안읽은거 있을시에 읽었다고 해주기
                JoinTimeRead()
            }
        })
        .catch(e => {
            console.log("에러:",e)
        })
    }
    // 채팅방 이름 가져오기
    const loadChatName = () => {
        customFetch({
            url:`/chatroom/loadChatName/${roomId}`,
            method:"GET"
        })
        .then(({data,status})=>{
            if(status===200 ){
                state.roomName=data 
            }
        })
        .catch(e => {
            console.log("에러:",e)
        })
    }

    // 입장시간 갱신
    const joinTime = () => {
        customFetch({
            url : `/chatjoin/updateJoinTime`,
            method:"PUT",
            data : {roomId, uuid}
        })
        .then(({status,data})=>{
            if(status===200){
                console.log("입장시간 갱신 완료")
            }
        }).catch(e=>{
            console.error("에러:", e)
        })
    }
    //입장시 안읽은 메세지 읽음처리하기
    const JoinTimeRead= () =>{
        customFetch({
            url :`/chatread/joinreadtime`,
            method:"POST",
            data : {roomId, uuid}
        })
        .then(({status,data})=>{
            if(status===200){
                console.log("입장시 읽음처리 완료")
            }
        }).catch(e=>{
            console.error("에러:", e)
        })
    }

    onMounted(() => {
    loadChatName()
    joinTime()
    loadChatMessage()
    client.activate()
})
    // 퇴장 시간 갱신
    const exitTime = () =>{
        customFetch({
            url: `/chatjoin/updateOutTime`,
            method:"PUT",
            data:{roomId,uuid}
        })
        .then(({data,status}) => {
            if(status===200){
                console.log("퇴장시간 갱신 완료")
                router.push({name:'chatrooms'})
            }
        }).catch(error => {
            console.error(에러)
        })
        
    }

    // 나가기
    const exit = () => {
    if (client && client.active) {
    client.deactivate()
    exitTime()

  }
}


</script>
<style scoped>
.my-msg {
  align-self: flex-end;
  max-width: 60%;
  margin: 5px;
  text-align: right;
}

.other-msg {
  align-self: flex-start;
  max-width: 60%;
  margin: 5px;
  text-align: left;
}

</style>
