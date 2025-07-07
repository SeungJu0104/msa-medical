<template>
    <div>
      <h2>내원이력</h2>
      <template v-if="!selecte.id">
      <ul v-if="state.list.length > 0" >
        <li class="history-box" v-for="(item, index) in state.list" :key="index">
            <p class="date" @click="division(item)" style="cursor: pointer;">
                {{ dayjs(item.treatDate).format('YYYY-MM-DD') }}</p>
        </li>
      </ul>
      <p v-else style="text-align: center; margin-top: 20px;">내원이력이 없습니다.</p>

      <div v-if="state.pageInfo.totalPage > 1">
        <button v-if="state.pageInfo.prev" @click="changePage(state.pageInfo.startPage - 1)">
            이전
        </button>

        <button v-for="page in pageNumbers" :key="page" @click="changePage(page)">
        {{ page }}
        </button>

        <button v-if="state.pageInfo.next" @click="changePage(state.pageInfo.endPage + 1)">
            다음
        </button>
    </div>
      </template>

    <template v-else-if="mode === 'treatmentDetail'">
      <VisitHistoryDetail :id="selecte.id" @back = "goBack"/>
    </template>
    <template v-else-if="mode === 'treatment'">
    <MedicalTreatment :id="selecte.id" :uuid="selecte.uuid" @back = "goBack"/>
    </template>
    </div>
  </template>
  
  <script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import dayjs from 'dayjs';
import { computed, onMounted, reactive, ref, watch } from 'vue';
import VisitHistoryDetail from './VisitHistoryDetail.vue';
import MedicalTreatment from './MedicalTreatment.vue';
    
    const props = defineProps({
      patientUuid: String,
      doctorUuid: String
    })


    onMounted(() => {
      historyList()
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

    watch(
      () => props.patientUuid, (newUuid, oldUuid) => {
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
  .history-box {
    margin-bottom: 20px;
  }
  
  .date {
    font-weight: bold;
    margin-bottom: 5px;
  }
  
  .box {
    background-color: #e0e0e0;
    padding: 15px;
    border-radius: 5px;
  }
  </style>
  