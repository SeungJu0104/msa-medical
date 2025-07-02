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
import { getStompClient, sendMsg, subscribeChannel } from '@/util/stompMethod';
import dayjs from 'dayjs';
import 'dayjs/locale/ko'
import { computed, onMounted, reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';
    dayjs.locale('ko') // 오후 || 오전 표시 해주는거
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
    let client;
    onMounted(() => {
        client = getStompClient(uuid.value,token,(client) => {
        chatSub(client)
        
    })  
        loadChatName()
        loadChatMessage()
    })
    let chatRoomSub;

    const chatSub =  (client) => {
        if(client && client.connected){
            chatRoomSub = subscribeChannel(client,`/sub/chatroom/${roomId}`,async (message) => {
            console.log("[📩 수신]", message)
            state.messages.push(message)
            if(message.uuid !== uuid.value){
                //읽음처리
                try {
                    await customFetch(ENDPOINTS.chat.readtime,{
                    data:{
                        roomId,
                        uuid: uuid.value,
                        messageId: message.messageId
                }})
                } catch (error) {
                    console.error("에러:", error)
                }
            }
        })
        }
    }
    //메세지 전송
    const sendMessage = () =>{
        if (state.content.trim() && client.connected) {
            sendMsg(client,`/pub/chat/message`,{
            roomId,
            uuid :uuid.value,
            content:state.content
            });
            console.log("[📤 보냄]", state.content)
        }
            state.content=''
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

    //입장시 안읽은 메세지 읽음처리하기
    const JoinTimeRead= async () =>{
        try {
            await customFetch(ENDPOINTS.chat.joinreadtime,{ data:{ roomId, uuid :uuid.value }})
        } catch (error) {
            console.error("에러:", error)
        }
    }

    // 나가기
    const exit = () => {
        if (chatRoomSub) {
            chatRoomSub.unsubscribe();
            chatRoomSub = null;
        }
        router.push({ name: 'chatrooms' });
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
