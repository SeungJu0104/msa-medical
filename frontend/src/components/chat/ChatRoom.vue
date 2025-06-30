<template>
    <div class="chat-container">
      <h3>채팅방</h3>
      
      {{ roomId }},{{state.roomName}}
      <div class="chat-box">
        <div v-for="(msg, idx) in state.messages" :key="idx"
        :class="msg.uuid === uuid ? 'my-msg' : 'other-msg'">
          [{{ msg.name }}] {{ msg.content }} || {{ dayjs(msg.createDate).format('A h:mm:ss') }}
        </div>
      </div>
      <input v-model="state.content" @keyup.enter="sendMessage" placeholder="메시지를 입력하세요" />
      <button @click="sendMessage">전송</button>

      <button @click="exit">나가기</button>
      
    </div>
</template>

<script setup>
import { getAccessToken } from '@/auth/accessToken';
import { useUserStore } from '@/stores/userStore';
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { Client } from '@stomp/stompjs';
import dayjs from 'dayjs';
import 'dayjs/locale/ko'
import SockJS from 'sockjs-client';
import { computed, onMounted, reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';
dayjs.locale('ko') // 오후 || 오전 표시 해주는거
    onMounted(() => {
        loadChatName()
        joinTime()
        loadChatMessage()
        client.activate()
    })
    const route = useRoute()
    const router = useRouter()
    const roomId= route.params.roomId
    const userStore = useUserStore();
    const uuid = computed(() => userStore.user?.uuid ?? '');
    const token = getAccessToken()


    const state = reactive({
        content:'',
        messages:[],
        roomName: ''
    })

    // stomp 연결 및 읽음 처리(실시간 읽음 처리)
    const client = new Client({
        webSocketFactory:() => new SockJS('/ws'),
        connectHeaders:{
            sender:uuid.value,
            'Authorization': `Bearer ${token}`,
        },
        onConnect: ()=>{
            client.subscribe(`/sub/chatroom/${roomId}`, async (message) => {
                const msg = JSON.parse(message.body)
                state.messages.push(msg)
                if(msg.uuid !== uuid.value){
                    const data = {
                        roomId,
                        uuid: uuid.value,
                        messageId: msg.messageId
                    }
                    try {
                        //실시간 채팅시에 읽음표시
                        await customFetch(ENDPOINTS.chat.readtime,{data})
                    } catch (error) {
                        console.error("에러:", error)
                    }
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
                    uuid :uuid.value,
                    content:state.content
                })
            })
            state.content=''
        }
    }
    // 채팅기록 가져오기
    const loadChatMessage = async () => {
        try {
            const response = await customFetch(ENDPOINTS.chat.messageList(roomId))
            if(response.status===200 && response.data.length >0){
                state.messages=response.data
                // 안읽은거 있을시에 읽었다고 해주기
                JoinTimeRead()
            }
        } catch (error) {
            console.error("에러:", error)
        }
    }
    // 채팅방 이름 가져오기
    const loadChatName = async () => {
        try {
            const response = await customFetch(ENDPOINTS.chat.loadChatName(roomId))
            if(response.status===200 ){
                state.roomName=response.data 
            }
        } catch (error) {
            console.error("에러:", error)
        }
    }

    // 입장시간 갱신
    const joinTime = async () => {
        try {
            await customFetch(ENDPOINTS.chat.updateJoinTime,{ data : {roomId, uuid :uuid.value}})
        } catch (error) {
            console.error("에러:", error)
        }
    }
    //입장시 안읽은 메세지 읽음처리하기
    const JoinTimeRead= async () =>{
        try {
            await customFetch(ENDPOINTS.chat.joinreadtime,{ data:{ roomId, uuid :uuid.value }})
        } catch (error) {
            console.error("에러:", error)
        }
    }

    // 퇴장 시간 갱신
    const exitTime = async () =>{
        try {
            const response = await customFetch(ENDPOINTS.chat.updateOutTime,{ data:{roomId,uuid :uuid.value}})
            if (response.status===200){
                router.push({name:'chatrooms'})
            }
        } catch (error) {
            console.error("에러:", error)
        }
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
