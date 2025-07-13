<template>
  <div class="total-wrapper">
      <div class="center">
        <div class="position-section">
        <Disease ref="diseaseRef"/>
        
        <Treatment ref ="treatmentRef" />
        <div class="attachment-section">
          <Attachment ref ="attachmentRef"/>
        </div>
     </div>
        
        <Medicine ref="medicineRef"/>
      </div>
    <button @click="submit" class ="btn btn-primary" >진료 완료</button>
    <button @click="emit('back')" class ="btn btn-primary" >목록으로</button>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import Attachment from './Attachment.vue'
import Disease from './Disease.vue'
import Medicine from './Medicine.vue'
import { customFetch } from '@/util/customFetch'
import { ENDPOINTS } from '@/util/endpoints'
import Treatment from './Treatment.vue'
import { useUserStore } from '@/stores/userStore'

const props = defineProps({
                  id:Number,
                  uuid:String
                })
const emit = defineEmits(['back'])

const treatmentRef= ref()
const diseaseRef = ref()
const attachmentRef = ref()
const medicineRef= ref()

const submit = async ()  => {
  const treatment = treatmentRef.value?.state.treatContent//진단 증상
  const disease = diseaseRef.value?.inputText() || [] // 질병 리스트 
  const attachment = attachmentRef.value?.state || [] // 첨부파일
  const medicine = medicineRef.value?.inputText() || []// 약 리스트

  const isMissingDiagnosis = !treatment || treatment.trim() === '';
  const isMissingMedicine = medicine.length === 0;
  const isMissingDisease = disease.length === 0;

  if (isMissingDiagnosis || isMissingMedicine || isMissingDisease) {
    alert('진단, 약, 질병은 모두 입력해야 합니다.');
    return;
  }

  const formData = new FormData();

  const data ={
    treatment : {
      id: props.id,
      uuid : props.uuid,
      treatContent : treatment
    },
    prescriptions: medicine.map(item => ({
      code: item.code,
      volume: item.volume,
      timesPerDay: item.timesPerDay,
      perDay: item.perDay,
      instructions: item.instructions
    })),
    diagnosis: disease.map(item => ({
      id: item.id
    }))
  }
  formData.append('data', new Blob([JSON.stringify(data)],{type:'application/json'}))

  if (attachment.files.length > 0) {
    attachment.files.forEach(file => {
      formData.append('files', file)
    })
  }
  try {
      const response = await customFetch(ENDPOINTS.treatment.totalTreatment, {
      data: formData,})
      
      if(response.status ===200){
        alert("진료 작성이 등록되었습니다.")
      }
  } catch (error) {
    console.error('진료 전체 등록 실패', error)
  }
}
</script>

<style scoped>
.total-wrapper {
  padding: 20px;
  background-color: #f5f5f5;
  max-width: 1000px;

}

.top-section {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.bottom-section {
  background: white;
  border: 1px solid #ccc;
  padding: 80px;
  height: 400px;
  
}
.position-section{
  display: flex;
  gap: 50px;
  align-items: flex-start;
}
.attachment-section{
  margin-left: 30px
}

</style>
