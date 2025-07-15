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
          <strong>약:</strong> {{ p.name }}<br />
          <strong>코드:</strong> {{ p.code }}<br />
          <strong>용량:</strong> {{ p.volume }}정<br />
          <strong>1회:</strong> {{ p.timesPerDay }}회<br />
          <strong>1일:</strong> {{ p.perDay }}일<br />
          <strong>복용 방법:</strong> {{ p.instructions }}
        </p>
      </li>
    </ul>

    <h3 class="section-title">첨부파일</h3>
    <ul class="attachment-list">
      <li v-for="a in state.attachments" :key="a.id" class="attachment-item">
        <p>• {{ a.originalName }}</p>
        <div v-if="a.contentType.startsWith('image/')">
          <img
              :src="a.blobUrl"
            alt="첨부 이미지"
            class="attachment-image"
          />
        </div>
      </li>
    </ul>

    <Document :id="props.id" />
  </div>
</template>

<script setup>
import { customFetch } from '@/util/customFetch'
import { ENDPOINTS } from '@/util/endpoints'
import { onMounted, reactive } from 'vue'
import Document from '../payment/Document.vue'

const props = defineProps({ id: Number })

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

      const attachmentsWithPreview = await Promise.all(
        data.attachments.map(async (a) => {
          // 이미지일 경우에만 blob URL 생성
          if (a.contentType.startsWith('image/')) {
            const { data: blob } = await customFetch(
              ENDPOINTS.treatment.getFile(a.fileName),
              {
    responseType: 'blob',
  }
            )
            a.blobUrl = URL.createObjectURL(blob)
          }
          return a
        })
      )
      state.attachments = attachmentsWithPreview
    }
  } catch (error) {
    console.error('에러', error)
  }
})
</script>

<style scoped>
.visit-detail {
  width: 100%;
  min-height: 80vh;
  overflow-y: auto;
  padding: 20px;
  background-color: #ffffff;
  max-width: 100vh;
  font-size: 14px;
  justify-content: flex-end;
}

.section-title {
  font-size: 18px;
  font-weight: bold;
  margin: 32px 0 12px;
  border-bottom: 1px solid #ccc;
  padding-bottom: 6px;
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

.attachment-item {
  margin-bottom: 16px;
}

.attachment-image {
  max-width: 200px;
  margin-top: 8px;
  margin-bottom: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

</style>
