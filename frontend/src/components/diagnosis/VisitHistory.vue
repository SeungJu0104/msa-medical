<template>
  <div class="visit-history-wrapper">
    <div class="visit-history-list">
      <div class="close-button-wrapper">
        <button @click="emit('back')" class="x-btn" >x</button>
      </div>
      <h5> {{ props.name }}님  내원이력</h5>
      
      <ul v-if="state.list.length > 0">
        <li
          class="visit-history-item"
          :class="{ active: selecte.id === item.id }"
          v-for="(item, index) in state.list"
          :key="index"
          @click="division(item)">
            <div class="visit-history-card">
              <p class="visit-date">{{ dayjs(item.treatDate).format('YYYY-MM-DD') }}</p>
            </div>
        </li>
      </ul>
      <p v-else class="no-history">내원이력이 없습니다.</p>

      <div class="pagination-wrapper" v-if="state.pageInfo.totalPage > 1">
        <button
          v-if="state.pageInfo.prev"
          @click="changePage(state.pageInfo.startPage - 1)"
          class="btn btn-primary">이전</button>

        <button
          v-for="page in pageNumbers"
          :key="page"
          @click="changePage(page)"
          :class="['btn', 'btn-outline-primary', { 'btn-primary text-white': state.pageNo === page }]">
          {{ page }}</button>

        <button
          v-if="state.pageInfo.next"
          @click="changePage(state.pageInfo.endPage + 1)"
          class="btn btn-primary">다음</button>
      </div>
    </div>

    <!-- 오른쪽: 상세 또는 작성 -->
    <div class="visit-history-detail" v-if="selecte.id">
      <VisitHistoryDetail
        v-if="mode === 'treatmentDetail'"
        :id="selecte.id" 
        :key="selecte.uuid" 
        @back="goBack" />
      <MedicalTreatment
        v-if="mode === 'treatment'"
        :id="selecte.id"
        :uuid="selecte.uuid"
        :key="selecte.uuid" 
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
import '@/assets/css/VisitHistory.css';
let client;
const emit = defineEmits(['back'])
const props = defineProps({
  patientUuid: String,
  name:String,
})

onBeforeMount(async () => {
    await historyList()
  });

onMounted(() => {
  client = getStompClient((client) => {
    statusSub(client)
  });
});

const statusSub = (client) => {
  setTimeout(() => {
    subscribeChannel(client, `/sub/status`, () => {
      historyList();
});
}, 100); 
};

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
