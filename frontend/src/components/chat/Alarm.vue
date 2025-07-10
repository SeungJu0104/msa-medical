
<template>
    <div class="chat-alarm">
      <div v-if="alarms.length === 0" class="empty">
        읽지 않은 메시지가 없습니다.
      </div>
      <div v-for="room in alarms" :key="room.roomId" class="chat-alarmCounts">
        <div class="chat-alarmText">
          <div class="chat-alarmTitle">
          <div @click="$emit('open', room.roomId)">
            {{ room.roomName }}</div>
        </div>
          <div class="chat-alarm-lastMessage">{{ room.content }}</div>
        </div>
        <div class="chat-alarmCount">{{ room.alarmCount }}</div>
      </div>
      
      <div class="chat-alarmBtn">
        <button class="btn btn-primary" @click="$emit('close')">닫기</button>
      </div>
    </div>
  </template>
  
    
  <script setup>
import { computed, onMounted } from 'vue'
import '@/assets/css/chat.css';
  defineEmits(['close','open'])

  const props = defineProps({
  alarms: Array,
  loadAlarmList: Function
})
  onMounted(() => {
    props.loadAlarmList()  // 알림 수동 새로고침할 때
})
  const alarms = computed(() => {
    return props.alarms.filter(room => room.alarmCount > 0)
  })
  </script>
