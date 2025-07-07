<template>
    <div>
        <VisitHistoryDetail :id="props.item.treatmentId"/>

        <PaymentWindow :item="props.item" />
        <Document :item="props.item" />
    </div>
</template>

<script setup>
import PaymentWindow from './PaymentWindow.vue';
import Document from './Document.vue';
import VisitHistoryDetail from '../diagnosis/VisitHistoryDetail.vue';

    const props = defineProps({
        item:Object
    })
</script>

<style lang="scss" scoped>

</style>
