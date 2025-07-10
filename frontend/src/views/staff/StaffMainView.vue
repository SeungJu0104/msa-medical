<template>
    <router-view />
    <div class= "chat-container">
      
    </div>
  
    <div class="body">
    <aside class="left">왼쪽
      <div class="my-3">
        <router-view name="waitingReservationParent"/>
      </div>
  
    </aside>
    <section class="center">중앙</section>
    
    <aside class="right">오른쪽
      
    </aside>
  </div>

  </template>
  
  <script setup>
import ChatRooms from '@/components/chat/ChatRooms.vue';

  </script>
  <style scoped>
  .chat-container {
    position: absolute;
    top: 100px; /* Header 밑으로 약간 내려오게 */
    right: 20px;
    width: 300px;
  }
  .body {
    display: flex;
    align-items: stretch
  }
  
  .left {
    width: 200px;
    background: #f0f0f0;
  }
  
  .center {
    flex: 1;
    text-align: center;
    background: #e0e0ff;
  }
  
  .right {
    width: 300px;
    background: #ffe0e0;
    height: 100vh;
    display: flex;
    flex-direction: column; /* 수직 정렬 기준 설정 */
    justify-content: flex-start; /* 위로 붙이기 */
  }
  
  </style>