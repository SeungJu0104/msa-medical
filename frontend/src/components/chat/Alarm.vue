
<template>
    <div class="modal-window">
      <h3>알림</h3>
      <div v-if="alarms.length === 0" class="empty">
        읽지 않은 메시지가 없습니다.
      </div>
  
      <div v-for="room in alarms" :key="room.roomId" class="chat-item">
        <div class="chat-text">
          <div class="title">
          <div><router-link :to="{name : 'chatroom', params:{roomId : room.roomId }}">{{ room.roomName }}</router-link></div>
        </div>
          <div class="last-message">{{ room.content }}</div>
        </div>
        <div class="badge">{{ room.alarmCount }}</div>
      </div>
  
      <div class="modal-footer">
        <button @click="$emit('close')">닫기</button>
      </div>
    </div>
  </template>
  
    
  <script setup>
  
import { useAuthStore } from '@/stores/counter'
import { customFetch } from '@/util/customFetch'
import { reactive , computed, onMounted } from 'vue'


  
  const emit = defineEmits(['close'])
  

  const props = defineProps({
  alarms: Array,
  loadAlarmList: Function
})

onMounted(() => {
  props.loadAlarmList()  // 알림 수동 새로고침할 때
})
  const auth = useAuthStore()
  const uuid = auth.user.uuid
  

  const alarms = computed(() => {
    return props.alarms.filter(room => room.alarmCount > 0)
  })
  </script>
    <style scoped>
    
    .modal-overlay {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.4);
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 1000;
    }
    
    .modal-window {
      background: white;
      border-radius: 12px;
      width: 340px;
      max-height: 80vh;
      padding: 20px;
      overflow-y: auto;
      box-shadow: 0 0 15px rgba(0,0,0,0.2);
    }
    
    .chat-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      border-bottom: 1px solid #eee;
      padding: 12px 0;
    }
    
    .chat-text .title {
      font-weight: bold;
      margin-bottom: 4px;
    }
    
    .chat-text .last-message {
      font-size: 13px;
      color: #888;
    }
    
    .badge {
      background-color: black;
      color: white;
      border-radius: 50%;
      padding: 6px 10px;
      font-size: 14px;
    }
    
    .modal-footer {
      text-align: right;
      margin-top: 20px;
    }
    </style>
    