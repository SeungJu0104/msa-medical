<template>
    <div>
        <h3>수납 내역</h3>
        {{props.item.name }}님
        <p>받을 금액{{ props.item.payment.toLocaleString() }}원</p>

        <div v-for="(pay,index) in state.paymentList" :key="index">
            {{ pay.method }} : {{ pay.amount.toLocaleString() }}원
        </div>
        <p>남은 금액: {{ reloadAmount.toLocaleString() }}원</p>

        <button @click ="paymentModal=true">수납추가</button>
       <div v-if="paymentModal" class="modal">
            <h4>수납추가</h4>
            <p>남은 금액 : {{ reloadAmount.toLocaleString()}}원</p>

            <div >
                <label>결재수단:</label>
                <button @click="state.paymentType = '카드'">카드</button>
                <button @click="state.paymentType = '현금'">현금</button>
            </div>

            <div>
                <label>결재금액</label>
                <input type="number" v-model.number="state.paymentAmount"/>
            </div>

            <div>
                <label>할부선택</label>
                <select v-model="state.installment">
                    <option>일시불</option>
                    <option>3개월</option>
                    <option>6개월</option>
                    <option>12개월</option>
                </select>
            </div>

            <button @click="addPayment()" >추가</button>
            <button @click="paymentModal=false">취소</button>
        </div>

        <button v-if="reloadAmount ===0 && !isSubmit" @click="submit">결재완료</button>
    </div>
</template>

<script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { getStompClient } from '@/util/stompMethod';
import { computed, onMounted, reactive, ref } from 'vue';

    const isSubmit = ref(false) // 결재완료 버튼 누를시 없어짐
    const props = defineProps({
        item: Object
    })
    const paymentModal = ref(false)
    const state = reactive({
        paymentList :[],
        paymentType: '카드',
        paymentAmount : 0,
        installment : '일시불',
    })
    const reloadAmount = computed(()=> {
        const total =state.paymentList.reduce((sum,item) => sum + item.amount,0)
        return props.item.payment-total
    })

    function addPayment(){
        if(state.paymentAmount <=0) return;
        if(reloadAmount.value < state.paymentAmount){
            alert("지불 금액이 남은 금액보다 큽니다")
            return
        }
        state.paymentList.push({
            method: state.paymentType,
            amount: state.paymentAmount,
            installment:state.installment,
        })
        state.paymentAmount = 0
        state.paymentType = '카드'
        state.installment = '일시불'
        paymentModal.value = false
    }

    const submit = async () => {
        try {
            const response = await customFetch(ENDPOINTS.payment.statusPayment(props.item.id))
        if(response.status===200){
            isSubmit.value = true
        }    
        } catch (error) {
            console.error("에러",error)   
        }
    }

    
</script>
    
<style >
.modal {
    position: fixed;
  inset: 0;
  background-color: rgba(0,0,0,0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

</style>