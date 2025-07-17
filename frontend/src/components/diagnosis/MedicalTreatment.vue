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
import '@/assets/css/treatment.css';
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
