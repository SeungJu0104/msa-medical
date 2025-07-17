<template>
  <div id="print-area" >
  <div class="medicinedocument-wrapper">
    <h2>처방전</h2>

    <div class="info-section">
      <div class="info-row">
        <div>환자명: {{ state.documentList.patientName }}</div>
        <div>주민등록번호: {{ state.documentList.rrn }}</div>
      </div>
      <div class="info-row">
        <div>접수 번호: {{ state.documentList.uuid }}</div>
        <div>진료일: {{ state.documentList.treatDate }}</div>
      </div>
      <div class="info-row">
        <div>담당의: {{ state.documentList.doctorName }}</div>
      </div>
    </div>

    <div class="section">
      <h3>질병명</h3>
      <ul>
        <li v-for="(dis, index) in state.diseaseList" :key="index">
          {{ dis.code }} : {{ dis.name }}
        </li>
      </ul>
    </div>

    <div class="section">
      <h3>처방 약 목록</h3>
      <ul>
        <li v-for="(med, index) in state.medicineList" :key="index">
          <div class="med-item">
            <p><strong>{{ med.code }}</strong> ({{ med.name }}) - 총 투약 일수 {{ med.volume }}</p>
            <p v-if="med.timesPerDay || med.perDay || med.instructions">
              복용: {{ med.perDay || '1회' }}, {{ med.timesPerDay || '1일' }}회,
              복용 방법: {{ med.instructions || '없음' }}
            </p>
          </div>
        </li>
      </ul>
    </div>

    <div class="button-group">
      <button @click="printDocument" class="btn btn-outline-dark">출력하기</button>
      <button @click="emit('back')" class="btn btn-primary">목록으로</button>
    </div>
  </div>
</div>
</template>

<script setup>
import { customFetch } from '@/util/customFetch'
import { ENDPOINTS } from '@/util/endpoints'
import { nextTick, onMounted, reactive } from 'vue'
import '@/assets/css/Document.css'
  
const props = defineProps({
  treatmentId: Number
})
const emit = defineEmits(['back'])
const state = reactive({
  documentList : {},
  diseaseList:[],
  medicineList:[],
})
onMounted(async ()=>{
  try {
    const response = await customFetch(ENDPOINTS.treatment.document(props.treatmentId))
    if(response.status===200){
        state.documentList = response.data.documentList
        state.diseaseList = response.data.diseaseList
        state.medicineList = response.data.medicineList
        
        await nextTick()
        window.print()
    }
} catch (error) {
    console.error("에러",error)
}}
)
function printDocument() {
window.print()
}

</script>
