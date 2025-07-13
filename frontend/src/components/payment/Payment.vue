<template>
    <div class="payment-wrapper">
      <!-- 왼쪽: 진료 내용 -->
      <div class="left">
        <VisitHistoryDetail :id="props.item.treatmentId" />
      </div>
  
      <!-- 오른쪽: 수납 내역 + 문서 발급 -->
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
  
  const props = defineProps({
    item: Object
  })
  </script>
  
  <style scoped >
  .payment-wrapper {
    display: flex;
    gap: 32px;
    align-items: flex-start;
    padding: 24px;
  
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
  }
  </style>
  