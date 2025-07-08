<template>
    <div class="document-wrapper">
    <h2>진단서</h2>

    <div class="patient-info">
      <p>환자명: {{ state.documentList.name }}</p>
      <p>주민등록번호: {{ state.documentList.rrn }}</p>
      <p>접수 번호: {{ state.documentList.uuid }}</p>
    </div>
    
    <div class="doctor-info">
      <p>진료일: {{ state.documentList.treatDate }}</p>
      <p>담당의: {{ state.documentList.doctorName }}</p>
    </div>

    <div class="disease-info">
      <h3>질병명</h3>
      <ul>
        <li v-for="(dis, index) in state.diseaseList" :key="index">
          {{ dis.code }} : {{ dis.name }}
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
    documentList : {},
    diseaseList:[],
  })
  onMounted(async ()=>{
    try {
      const response = await customFetch(ENDPOINTS.treatment.document(props.treatmentId))
      if(response.status===200){
          state.documentList = response.data.documentList
          state.diseaseList = response.data.diseaseList
      }
  } catch (error) {
      console.error("에러",error)
  }
})
</script>

<style lang="scss" scoped>

</style>