<template>
  <div class="visit-history-wrapper">
    <!-- 왼쪽: 내원 이력 리스트 -->
    <div class="visit-history-list">
      <h3>내원이력</h3>
      <ul v-if="state.list.length > 0" class="visit-history-list">
  <li class="visit-history-item" v-for="(item, index) in state.list" :key="index" @click="division(item)">
    <div class="visit-history-card">
      <p class="visit-date">{{ dayjs(item.treatDate).format('YYYY-MM-DD') }}</p>
    </div>
  </li>
</ul>
      <p v-else class="no-history">내원이력이 없습니다.</p>

      <!-- 페이징 -->
      <div v-if="state.pageInfo.totalPage > 1" class="pagination">
        <button v-if="state.pageInfo.prev" @click="changePage(state.pageInfo.startPage - 1)">이전</button>
        <button v-for="page in pageNumbers" :key="page" @click="changePage(page)">
          {{ page }}
        </button>
        <button v-if="state.pageInfo.next" @click="changePage(state.pageInfo.endPage + 1)">다음</button>
      </div>
    </div>

    <!-- 오른쪽: 상세 또는 작성 -->
    <div class="visit-history-detail" v-if="selecte.id">
      <VisitHistoryDetail
        v-if="mode === 'treatmentDetail'"
        :id="selecte.id" />
      <MedicalTreatment
        v-if="mode === 'treatment'"
        :id="selecte.id"
        :uuid="selecte.uuid"
        @back="goBack" />
    </div>
  </div>
</template>

  
  <script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import dayjs from 'dayjs';
import { computed, onBeforeMount, onMounted, reactive, ref, watch } from 'vue';
import VisitHistoryDetail from './VisitHistoryDetail.vue';
import MedicalTreatment from './MedicalTreatment.vue';
import { getStompClient, subscribeChannel } from '@/util/stompMethod';
const props = defineProps({
  patientUuid: String,
  doctorUuid: String
})
let client;

onMounted(() => {
  historyList()
  client = getStompClient((client) => {
    statusSub(client)
  });
})

const selecte = reactive({ id: null, uuid: null })
const mode = ref('')

const division = (item) => {
selecte.id = item.id
selecte.uuid = item.uuid ?? null
mode.value = item.writeYn === 'Y' ? 'treatmentDetail' : 'treatment';
};
const state = reactive({
    list:[],
    pageInfo: {},
    pageNo: 1,
    size: 5,
})

const historyList = async () => {
try {
    const response = await customFetch(ENDPOINTS.treatment.history,
    {params:{
        patientUuid: props.patientUuid,
        doctorUuid: props.doctorUuid,
        pageNo: state.pageNo,
        size: state.size,
    }})
    if(response.status===200){
      state.list =  response.data.list
      state.pageInfo =  response.data.pageInfo
    }
} catch (error) {
console.error("에러",error)
}}

const statusSub = (client) => {
  setTimeout(() => {
    subscribeChannel(client, `/sub/status`, () => {
      historyList();
});
}, 100); 
};

const changePage = (page) => {
  state.pageNo = page;
  historyList();
};

const pageNumbers = computed(() => {
  const pages = [];
  for (let i = state.pageInfo.startPage; i <= state.pageInfo.endPage; i++) {
    pages.push(i);
}
  return pages;
});
const goBack = () => {
  selecte.id = null
  selecte.uuid = null
  mode.value = '';
}

watch(() => props.patientUuid, (newUuid, oldUuid) => {
  if (newUuid && newUuid !== oldUuid) {
    state.pageNo = 1;
    historyList();
    selecte.id = null;
    selecte.uuid = null;
    mode.value = '';
  }
  }
);
</script>

<style scoped>
.visit-history-wrapper {
display: flex;
gap: 20px;
margin-top: 20px;
height: 1000px;
}

.visit-history-list {
display: flex;
flex-direction: column;
gap: 12px;
height: 100%;
}
.visit-history-detail {
width: 70%;
background-color: #f9f9f9;
padding: 16px;
border: 1px solid #ddd;
border-radius: 8px;
overflow-y: auto;
height: 100%;
}
.visit-history-card {
background: white;
padding: 16px 24px;
border-radius: 8px;
box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
transition: transform 0.2s ease;
}

.history-box {
margin-bottom: 20px;
}

.visit-date {
font-weight: bold;
font-size: 16px;
}
.no-history {
text-align: center;
margin-top: 20px;
}

.pagination {
margin-top: 10px;
}

</style>
