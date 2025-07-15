<template>
    <div class="paymentList">
      <div  class="payment-item"  v-for="item in state.list"  :key="item.id" >
        <!-- 이름 버튼 -->
        <button  class="btn btn-primary"  @click="namePayment(item)"> {{ item.patientName }}
        </button>
  
        <!-- 상태 배지 -->
        <span 
          class="btn btn-primary"
          :class="item.paymentStatus === 'Y' ? 'bg-success' : 'bg-secondary'" >
          {{ item.paymentStatus === 'Y' ? '결제완료' : '결제대기 중' }}
        </span>
      </div>
     
    </div>
  </template>
  
  
<script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { computed, inject, onMounted, reactive, ref } from 'vue';
import { getStompClient, subscribeChannel } from '@/util/stompMethod';
const currentView = inject('currentView');
const selectedItem = inject('selectedItem');
let client ;
const state = reactive({
    list : [],
})

onMounted(()=>{
    loadPaymentList()
    client = getStompClient((client) => {
    statusSub(client)
})
})

const statusSub = (client) => {
    subscribeChannel(client,`/sub/status`,() => {
    loadPaymentList()
})
}

const loadPaymentList = async () => {
    try {
        const response = await customFetch(ENDPOINTS.payment.loadPaymentList)
        if(response.status===200){
            state.list = response.data.list
        }
    } catch (error) {
        console.error("에러",error)
    }
    
}
const namePayment = (item) => {
    selectedItem.value= item;
    currentView.value = 'payment';
}
</script>

<style scoped>
.paymentList {
  display: flex;
  flex-direction: column;
  gap: 10px; /* 항목 간 여백 */
}

.payment-item {
  display: flex;
  align-items: center;
  gap: 8px; /* 버튼 사이 여백 */
}

.payment-item button,
.payment-item span {
  padding: 4px 10px;
  font-size: 13px;
  height: 30px;
  border-radius: 6px;
  line-height: 1.2;
}

</style>
