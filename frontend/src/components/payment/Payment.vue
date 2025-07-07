<template>
    <div>
        

        <VisitHistoryDetail :id="props.item.treatmentId"/>

        <PaymentWindow :item="props.item" />
        <!-- <Document :prescriptions="" /> -->


        <!-- 안쓸거임 <button @click="activeTab = 'diagnosis'">진단서</button>
        <button @click="activeTab = 'prescription'">처방전</button>
        <button @click="activeTab = 'payment'">결제하기</button> -->
    </div>
</template>

<script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { onMounted, reactive, ref } from 'vue';
import PaymentWindow from './PaymentWindow.vue';
import Document from './Document.vue';
import VisitHistoryDetail from '../diagnosis/VisitHistoryDetail.vue';

    const props = defineProps({
        item:Object
    })
</script>

<style lang="scss" scoped>

</style>
