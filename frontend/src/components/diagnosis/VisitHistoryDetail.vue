<template>
    <div class="visit-detail">
      <h2 class="section-title">진료 내용</h2>
      <div class="treatment-info">
        <p><strong>내용:</strong> {{ state.treatment.treatContent }}</p>
        <p><strong>날짜:</strong> {{ state.treatment.treatDate }}</p>
      </div>
  
      <h3 class="section-title">진단 결과</h3>
      <ul class="diagnosis-list">
        <li v-for="d in state.diagnosis" :key="d.id">
          • {{ d.name }} (ID: {{ d.id }})
        </li>
      </ul>
  
      <h3 class="section-title">처방 약</h3>
      <ul class="prescription-list">
        <li v-for="p in state.prescriptions" :key="p.code" class="prescription-item">
          <p>
            <strong>약:</strong> {{ p.name }} (코드: {{ p.code }})  
            <br>
            <strong>용량:</strong> {{ p.volume }}정,
            <strong>1회:</strong> {{ p.timesPerDay }}회,
            <strong>1일:</strong> {{ p.perDay }}일,
            <strong>복용 방법:</strong> {{ p.instructions }}
          </p>
        </li>
      </ul>
  
      <h3 class="section-title">첨부파일</h3>
      <ul class="attachment-list">
        <li v-for="a in state.attachments" :key="a.id">
          <p>• {{ a.originalName }}</p>
          <div v-if="a.contentType.startsWith('image/')">
            <img
              :src="`http://localhost:8080/attachment/${a.fileName}`"
              alt="첨부 이미지"
              style="max-width: 200px; margin-top: 8px"
            />
          </div>
          <div v-else>
            <a :href="`http://localhost:8080/attachment/${a.fileName}`" download>
              파일 다운로드
            </a>
          </div>
        </li>
      </ul>

       <Document :id="props.id"/>
    </div>
  </template>
  
  <script setup>
  import { customFetch } from '@/util/customFetch'
  import { ENDPOINTS } from '@/util/endpoints'
  import { onMounted, reactive } from 'vue'
import Document from '../payment/Document.vue'
  const props = defineProps({ id: Number })
  const emit = defineEmits(['back'])
  
  const state = reactive({
    treatment: {},
    diagnosis: [],
    prescriptions: [],
    attachments: []
  })
  
  onMounted(async () => {
    try {
      const response = await customFetch(ENDPOINTS.treatment.historyDetail(props.id))
      if (response.status === 200) {
        const data = response.data.total
        state.treatment = data.treatment
        state.diagnosis = data.diagnosis
        state.prescriptions = data.prescriptions
        state.attachments = data.attachments
      }
    } catch (error) {
      console.error('에러', error)
    }
  })
  </script>
  
  <style scoped>
  .visit-detail {
    padding: 20px;
    background-color: #fff;
    border-radius: 10px;
    max-width: 900px;
    margin: auto;
    font-size: 14px;
  }
  
  .section-title {
    font-size: 18px;
    font-weight: bold;
    margin: 16px 0 8px;
  }
  
  .treatment-info p,
  .prescription-item p {
    margin: 4px 0;
  }
  
  ul {
    list-style: none;
    padding-left: 0;
  }
  
  .prescription-item {
    margin-bottom: 12px;
    background: #f9f9f9;
    padding: 10px;
    border-radius: 6px;
    border: 1px solid #ddd;
  }
  
  .btn-outline {
    margin-top: 24px;
    padding: 8px 16px;
    border: 1px solid #007bff;
    background-color: white;
    color: #007bff;
    border-radius: 6px;
    cursor: pointer;
  }
  </style>
  