<template>
    <div class="document-wrapper">
    <h2>처방전</h2>

    <div class="patient-info">
      <p>환자명: {{ document.patientName }}</p>
      <p>주민등록번호: {{ document.rrn }}</p>
      <p>환자 UUID: {{ document.patientUuid }}</p>
    </div>

    <div class="doctor-info">
      <p>진료일: {{ document.treatDate }}</p>
      <p>담당의: {{ document.doctorName }}</p>
    </div>

    <div class="disease-info">
      <p>병명: {{ document.diseaseName }}</p>
      <p>질병코드: {{ document.diseaseCode }}</p>
    </div>

    <div class="medicine-list">
      <h3>처방 약 목록</h3>
      <ul>
        <li v-for="(med, index) in documentList" :key="index">
          {{ med.medicineName }} ({{ med.prescriptionCode }}) - {{ med.volume }}정
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { customFetch } from '@/util/customFetch'
import { ENDPOINTS } from '@/util/endpoints'
import { onMounted, reactive } from 'vue'

    const props = defineProps({
    treatmentId: Number
    })
    const state = reactive({
        diseaseList : []
    })
    onMounted(()=>{
        loadDocument()
    })
    const loadDocument = async () => {
        try {
        const response = await customFetch(ENDPOINTS.treatment.document(props.item.treatmentId))
        if(response.status===200){
            state.diseaseList = response.data.list
        }
    } catch (error) {
        console.error("에러",error)
    }}
</script>

<style lang="scss" scoped>

</style>