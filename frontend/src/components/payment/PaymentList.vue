<template>
    <div class="paymentList">
      <div  class="payment-item"  v-for="item in state.list"  :key="item.id" >
        <!-- 이름 버튼 -->
        <button  class="btn btn-primary"  @click="payment(item)"> {{ item.patientName }}
        </button>
  
        <!-- 상태 배지 -->
        <span 
          class="btn btn-primary"
          :class="item.paymentStatus === 'Y' ? 'bg-success' : 'bg-secondary'" >
          {{ item.paymentStatus === 'Y' ? '결제완료' : '결제대기 중' }}
        </span>
      </div>
  
      <!-- 결제 컴포넌트 -->
      <Payment
        v-if="selectedItem"
        :item="selectedItem"
        :key="selectedItem.id" />
    </div>
  </template>
  
  
<script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { computed, onMounted, reactive, ref } from 'vue';
import Payment from './Payment.vue';
import { getStompClient, subscribeChannel } from '@/util/stompMethod';

const selectedItem = ref(null)
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
const payment = (item) => {
    selectedItem.value= item;
}
</script>

<style scoped>
.paymentList {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.payment-item {
  display: flex;
  align-items: center;
  gap: 10px;
}
</style>
