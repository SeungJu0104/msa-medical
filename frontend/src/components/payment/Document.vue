<template>
    <div>
        <h2>문서 발급</h2>
        <button @click="selectedType = 'DiseaseDocument'">진단서</button>
        <button @click="selectedType = 'MedicineDocument'" >처방전</button>
    </div>

    <div v-if="selectedType ==='DiseaseDocument'">
        <DiseaseDocument :treatmten-id ="props.item.treatmtenId"/>
    </div>
    <div v-else-if="selectedType ==='MedicineDocument'">
        <MedicineDocument :treatmten-id ="props.item.treatmtenId"/>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import DiseaseDocument from './DiseaseDocument.vue';
import MedicineDocument from './MedicineDocument.vue';


    const props = defineProps({
        item:Object
    })
    const selectedType = ref(null)
</script>

<style lang="scss" scoped>

</style>