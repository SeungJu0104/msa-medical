<template>
    <div>
        <div v-for="item in state.list" :key="item.id">
            <span @click="payment(item)">
            {{ item.name }}
            </span>
            <span>
            {{ item.paymentStatus === 'Y' ? '결제완료' : '결제대기 중' }}
        </span>
    </div>
        <Payment
        v-if="selectedItem"
        :item="selectedItem"
        />
    </div>
</template>

<script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { onMounted, reactive, ref } from 'vue';
import Payment from './Payment.vue';


    const selectedItem = ref(null)
    const state = reactive({
        list : [],
    })

    onMounted(()=>{
        loadPaymentList()
    })

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

<style lang="scss" scoped>

</style>
