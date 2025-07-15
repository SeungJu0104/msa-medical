<template>
    <div class="payment-wrapper">
      <button @click="emit('back')" class="x-btn" >x</button>
      <div class="left">
        <VisitHistoryDetail :id="props.item.treatmentId" />
      </div>
      <div class="right">
        <PaymentWindow :item="props.item" />
        <Document :id="props.item.treatmentId"/>
      </div>
    </div>
  </template>
  
  <script setup>
  import PaymentWindow from './PaymentWindow.vue'
  import Document from './Document.vue'
  import VisitHistoryDetail from '../diagnosis/VisitHistoryDetail.vue'
  const emit = defineEmits(['back'])
  const props = defineProps({
    item: Object
  })
  </script>
  
  <style scoped >
  .payment-wrapper {
  display: flex;
  gap: 32px;
  align-items: flex-start;
  padding: 32px;
  background-color: #f8faff;
}

.left, .right {
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  padding: 24px;
}

.left {
  flex: 2;
}

.right {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 24px;
  align-items: center;
}

button.btn {
  padding: 8px 16px;
  border: 1px solid #007bff;
  background-color: white;
  color: #007bff;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s;
}

button.btn:hover {
  background-color: #007bff;
  color: white;
}

button.btn-primary {
  align-self: flex-start;
}
.x-btn {
  background: none;
  border: none;
  color: #333;
  font-size: 20px;
  font-weight: bold;
  cursor: pointer;
  padding: 4px 8px;
}


  </style>
  