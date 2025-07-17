<template>
    <div>
        <h5>문서 발급</h5>
        <button class="btn btn-outline-secondary" @click="selectedType = 'DiseaseDocument'">진단서</button>
        <button class="btn btn-outline-secondary" @click="selectedType = 'MedicineDocument'"  >처방전</button>
    </div>
    <Suspense>
        <div v-if="selectedType ==='DiseaseDocument'">
            <DiseaseDocument :treatmentId ="props.id"  @back="goBack" />
        </div>
        <div v-else-if="selectedType ==='MedicineDocument'">
            <MedicineDocument :treatmentId ="props.id" @back="goBack"/>
        </div>
    </Suspense>

</template>  
<script setup>
import { onMounted, reactive, ref } from 'vue';
import DiseaseDocument from './DiseaseDocument.vue';
import MedicineDocument from './MedicineDocument.vue';
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';

const props = defineProps({
    id:Number
})
const selectedType = ref(null)
const state = reactive({
    documentList : {},
    diseaseList:[],
    medicineList:[],
})
onMounted(()=>{
    loadDocument()
})
const loadDocument = async () => {
    try {
    const response = await customFetch(ENDPOINTS.treatment.document(props.id))
    if(response.status===200){
        state.documentList = response.data.documentList
        state.diseaseList = response.data.diseaseList
        state.medicineList = response.data.medicineList
    }
} catch (error) {
    console.error("에러",error)
}}
const goBack = () => {
selectedType.value = null
}
</script>
