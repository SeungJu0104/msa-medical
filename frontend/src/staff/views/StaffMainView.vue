<template>
  
    <div class="body">
    <aside class="left">
        
    </aside>
    <section class="center">
      <WaitingReservationParent class="my-3"/>
    </section>
    
    <aside class="right">오른쪽
      <ChatRooms/>
    </aside>
  </div>

</template>
  
<script setup>
import ChatRooms from '@/components/chat/ChatRooms.vue';
import WaitingList from '@/reception/views/WaitingList.vue';
import WaitingReservationParent from "@/views/staff/components/WaitingReservationParent.vue";
</script>
<style scoped>
  .body {
    display: flex;
    align-items: stretch
  }
  
  .left {
    width: 20vw;
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