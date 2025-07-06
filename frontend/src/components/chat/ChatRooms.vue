<template>
    <div class="chat-rooms">
      <div class = "chat-header">
      {{name}}님
      
      <button class="btn-alarm" @click="showModal = true">
        알림창 <span v-if="totalCount > 0">({{ totalCount }})</span>
      </button>
    </div>
      <alarm v-if="showModal" :alarms="alarmList" @close="showModal = false " :loadAlarmList="loadAlarmList" />
      <ul class="chat-list">
  <li v-for="room in state.chatList" :key="room.roomId" class="chat-item">
    <router-link class="chat-link" :to="{ name: 'chatroom', params: { roomId: room.roomId } }">
      <div class="chat-top">
        <span class="chat-room-name">{{ room.roomName }}</span>
        <span class="chat-content">{{ room.content }}</span>
        <span v-if="room.count > 0" class="badge">{{ room.count }}</span>
      </div>
      <div class="chat-bottom">
        {{ dayjs(room.lastMessageTime).format('A h:mm:ss') }}
      </div>
    </router-link>
  </li>
</ul>
    
      <button class="btn-create" @click="create = true">등록하기</button>
    <ChatCreate v-if="create" @close="create = false" @refresh="loadList" />
  </div>
  </template>
  
  <script setup>
  
import ChatCreate from './ChatCreate.vue'
import alarm from './Alarm.vue'
import { customFetch } from '@/util/customFetch';
import {onMounted, computed, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ENDPOINTS } from '@/util/endpoints';
import { useUserStore } from '@/stores/userStore';
import { getAccessToken } from '@/auth/accessToken';
import dayjs from 'dayjs';
import 'dayjs/locale/ko'
import { getStompClient, sendMsg, subscribeChannel } from '@/util/stompMethod';
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
    let client;
  onMounted(()=>{
    client = getStompClient(uuid.value,token,(client) => {
      subList(client)
      subAlarm(client)
    })  
    loadchatList()
    loadAlarmList()
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
    const subList = (client) => {
        subscribeChannel(client,`/sub/chatrooms/${uuid.value}`,(message) => {
        state.chatList = message
      })
    }
    const subAlarm = (client) => {
        subscribeChannel(client,`/sub/alarms/${uuid.value}`,(message)=>{
        alarmList.value = message
      })
    }

    const loadList = (roomId) =>{
      if (client.connected) {
        sendMsg(client,`/pub/chat/list`,{
        uuid:uuid.value,roomId 
      });
      create.value =false;
      router.push({name:'chatroom', params:{roomId}}) 
}}

const loadAlarmList = async ()=> {
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
.chat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
  padding: 0 0.5rem; /* 좌우 여백 추가 */
}
.chat-rooms {
  width: 100%;
  height: 100%;
  padding: 1rem;
  box-sizing: border-box;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
}
.chat-list {
  list-style: none;         /* ● 점 제거 */
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 1.75rem;             /* 각 채팅방 간격 */
  overflow-y: auto;
}
.chat-link {
  text-decoration: none;
  color: inherit;
  display: block;
}

.chat-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
  margin-bottom: 0.25rem;
  position: relative;
}

.chat-room-name {
  font-weight: bold;
  font-size: 0.8rem;
  white-space: nowrap;
  overflow: hidden;            /* 넘치는 부분 숨김 */
  text-overflow: ellipsis;     /* 말줄임 (...) 표시 */
  max-width: 60px;            /* 최대 너비 지정 필수! */
  display: inline-block; 
}

.chat-content {
  position: absolute;
  left: 50%;
  text-align: center;  
  transform: translateX(-50%);   
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #555;
  max-width:  60px;          /* 최대 너비 지정 필수! */
  font-size: 0.9rem;
  pointer-events: none; /* 클릭 막힘 방지용 */
}

.badge {
  background-color: #ff4d4f;
  color: white;
  font-size: 0.75rem;
  font-weight: bold;
  padding: 2px 6px;
  border-radius: 12px;
  flex-shrink: 0;
}

.chat-bottom {
  font-size: 0.75rem;
  color: #888;
  text-align: right;
}

</style>