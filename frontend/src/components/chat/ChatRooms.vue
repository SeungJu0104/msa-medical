<template>
    <div>
      <h2>채팅방 리스트</h2>
      {{name}}님
      
      <button class="btn" @click="showModal = true">
        알림창 <span v-if="totalCount > 0">({{ totalCount }})</span>
      </button>
      <alarm v-if="showModal" :alarms="alarmList" @close="showModal = false " :loadAlarmList="loadAlarmList" />

      <pre>{{ state.chatList }}</pre>
      <ul class="list-group">
        <li v-for="room in state.chatList" :key="room.roomId">
          <router-link :to="{name : 'chatroom', params:{roomId : room.roomId }}">{{ room.roomName }}</router-link>
          {{ room.content }}
          {{ room.count }}
          {{ room.finalReadTime }}
          {{ dayjs(room.lastMessageTime).format('A h:mm:ss') }}
        </li>
        
      </ul>
    
      <button class="btn" @click="create = true">등록하기</button>
    <ChatCreate v-if="create" @close="create = false" @refresh="loadList" />

  </div>
  </template>
  
  <script setup>
  
import ChatCreate from './ChatCreate.vue'
import alarm from './Alarm.vue'
import { customFetch } from '@/util/customFetch';
import {onMounted, computed, reactive, ref } from 'vue';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { useRouter } from 'vue-router';
import { ENDPOINTS } from '@/util/endpoints';
import { useUserStore } from '@/stores/userStore';
import { getAccessToken } from '@/auth/accessToken';
import dayjs from 'dayjs';
import 'dayjs/locale/ko'
dayjs.locale('ko') // 오후 || 오전 표시 해주는거

  const userStore = useUserStore();
  const name = computed(() => userStore.user?.name ?? '');
  const uuid = computed(() => userStore.user?.uuid ?? '');
  const token = getAccessToken()


  const alarmList = ref([])
  const create = ref(false)
  const showModal = ref(false)
  const router = useRouter()
  const state = reactive({
    chatList : [],
  });

  const totalCount = computed(() => {
  return state.chatList.reduce((sum, room) => sum + room.count, 0)
  })

  onMounted(()=>{
    loadchatList()
    loadAlarmList()
    client.activate()
  })

  const loadchatList= async () =>{
    try {
      const response = await customFetch(ENDPOINTS.chat.chatRoomList(uuid.value))
    if(response.status===200){
      state.chatList = response.data
    }
    } catch (error) {
      console.error("에러:", error)
    }
};
const client = new Client({
  webSocketFactory:() => new SockJS('/ws'),
  connectHeaders:{
    sender:uuid.value,
    'Authorization': `Bearer ${token}`
  },
  onConnect: () => {
    client.subscribe(`/sub/chatrooms/${uuid.value}`,(message) =>{
    const list = JSON.parse(message.body)
    state.chatList = list
    })
    client.subscribe(`/sub/alarms/${uuid.value}`, (message) => {
    const alarm = JSON.parse(message.body)
    alarmList.value = alarm
  })
  },
  onStompError: (e) =>{
  console.error('연결 실패 : ' ,e)
  }
})
const loadList = (roomId) =>{
  
  if (client.connected) {
    client.publish({
      destination: `/pub/chat/list`,
      body: JSON.stringify({ uuid:uuid.value,roomId })
    });
  create.value =false;
  router.push({name:'chatroom', params:{roomId}}) 
}}

const loadAlarmList = async () => {
  try {
    const response = await customFetch(ENDPOINTS.chat.chatReadList(uuid.value))
    if(response.status===200){
      alarmList.value = response.data
  }
  } catch (error) {
    console.error("에러",error)
  }
}
</script>


<style scoped>
.modal-overlay {
  position: fixed;
  top: 0; left: 0;
  width: 100vw; height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}
.modal-window {
  background: white;
  padding: 1.5rem;
  border-radius: 8px;
  min-width: 300px;
}
.modal-footer {
  margin-top: 1rem;
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}
</style>