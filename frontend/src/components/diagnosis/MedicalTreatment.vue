<template>
  <div class="total-wrapper">
    <div class="top-bar">
      <button @click="emit('back')" class="x-btn">x</button>
    </div>

    <div class="grid-wrapper">
      <div class="grid-item"><Treatment ref="treatmentRef" /></div>
      <div class="grid-item"><Attachment ref="attachmentRef" /></div>
      <div class="grid-item"><Medicine ref="medicineRef" /></div>
      <div class="grid-item"><Disease ref="diseaseRef" /></div>
    </div>

    <div class="submit-button-wrapper">
      <button @click="submit" class="btn-submit">진료 완료</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import Attachment from './Attachment.vue'
import Disease from './Disease.vue'
import Medicine from './Medicine.vue'
import Treatment from './Treatment.vue'
import { customFetch } from '@/util/customFetch'
import { ENDPOINTS } from '@/util/endpoints'

const props = defineProps({
  id: Number,
  uuid: String,
})

const emit = defineEmits(['back'])

const treatmentRef = ref()
const diseaseRef = ref()
const attachmentRef = ref()
const medicineRef = ref()

const submit = async () => {
  const treatment = treatmentRef.value?.state.treatContent
  const disease = diseaseRef.value?.inputText() || []
  const attachment = attachmentRef.value?.state || []
  const medicine = medicineRef.value?.inputText() || []

  const isMissingDiagnosis = !treatment || treatment.trim() === ''
  const isMissingMedicine = medicine.length === 0
  const isMissingDisease = disease.length === 0

  if (isMissingDiagnosis || isMissingMedicine || isMissingDisease) {
    alert('진단, 약, 질병은 모두 입력해야 합니다.')
    return
  }

  const formData = new FormData()

  const data = {
    treatment: {
      id: props.id,
      uuid: props.uuid,
      treatContent: treatment,
    },
    prescriptions: medicine.map(item => ({
      code: item.code,
      volume: item.volume,
      timesPerDay: item.timesPerDay,
      perDay: item.perDay,
      instructions: item.instructions,
    })),
    diagnosis: disease.map(item => ({
      id: item.id,
    })),
  }

  formData.append('data', new Blob([JSON.stringify(data)], { type: 'application/json' }))
  if (attachment.files.length > 0) {
    attachment.files.forEach(file => {
      formData.append('files', file)
    })
  }

  try {
    const response = await customFetch(ENDPOINTS.treatment.totalTreatment, { data: formData })
    if (response.status === 200) {
      alert('진료 작성이 등록되었습니다.')
      emit('back')
    }
  } catch (error) {
    console.error('진료 전체 등록 실패', error)
  }
}
</script>

<style scoped>
.total-wrapper {
  width: 100%;
  min-height: 80vh;
  overflow-y: auto;
  padding: 20px;
  background-color: #ffffff;
  max-width: 110vh;
  font-size: 14px;
  position: relative;
}

.top-bar {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 16px;
}

.grid-wrapper {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: auto auto;
  gap: 24px;
}

.grid-item {
  max-height: 400px;      
  overflow-y: hidden;   
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 24px;
  overflow-y: auto;
}

.submit-button-wrapper {
  display: flex;
  justify-content: flex-end;
  margin-top: 24px;
}

.btn-submit {
  background-color: #007bff;
  color: white;
  padding: 10px 24px;
  font-size: 15px;
  font-weight: 600;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
  transition: background-color 0.2s ease;
  position: absolute;
  bottom: 24px;
  right: 32px;
}

.btn-submit:hover {
  background-color: #0056b3;
}

.x-btn {
  background: none;
  border: none;
  color: #333;
  font-size: 20px;
  font-weight: bold;
  cursor: pointer;
  padding: 4px 8px;
}
</style>
