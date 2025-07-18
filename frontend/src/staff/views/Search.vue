<template>
  <div class="search-modal-backdrop" @click.self="emit('close')">
    <div class="search-modal-content">
      <div class="search-title">
        <img src="@/assets/icons/search.png" alt="검색" class="icon"/>
        환자검색
      </div>

      <div class="search-input-group">
        <input
            type="text"
            v-model="searchVal.input"
            minlength="1"
            maxlength="6"
            @keyup.enter="search"
            placeholder="환자명"
        />
        <button type="button" @click="search">
          <img class="search" src="@/assets/icons/search.png" alt="검색" />
        </button>
      </div>

      <div class="search-results">
        <table v-if="runSearch && searchRes.length > 0">
          <thead>
          <tr>
            <th @click="detail">이름</th>
            <th>생년월일</th>
            <th>전화번호</th>
            <th>예약</th>
            <th>대기</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="res in searchRes" :key="res.uuid">
            <td @click="openVisitHistory(res)">{{ res.name }}</td>
            <td>{{ res.rrn }}</td>
            <td>{{ res.phone }}</td>
            <td><button @click="reservation(res.uuid)">추가</button></td>
            <td><button @click="waiting(res)">추가</button></td>
          </tr>
          </tbody>
        </table>

        <div v-if="runSearch && searchRes.length < 1" class="search-no-result">
          검색결과가 존재하지 않습니다.
        </div>
      </div>

      <button class="search-close-btn" @click="emit('close')">닫기</button>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, defineEmits } from "vue"
import { useRouter } from "vue-router"
import { customFetch } from "@/util/customFetch.js"
import { ENDPOINTS } from "@/util/endpoints.js"
import { common } from "@/util/common.js"
import { errorMessage } from "@/util/errorMessage.js"
import '@/assets/css/search.css'
import '@/assets/css/icons.css'
import { usePatientViewStore } from '@/stores/patientViewStore.js'
  
  const store = usePatientViewStore()
  const searchVal = reactive({ input: "" })
  const searchRes = ref([])
  const runSearch = ref(false)
  const router = useRouter()

  const emit = defineEmits(['close']);

  const search = async () => {
    if (searchVal.input.length < 1) {
      common.alert(errorMessage.staff.searchValLength)
      return
    }

    try {
      const response = await customFetch(ENDPOINTS.staff.search(searchVal.input))
      runSearch.value = true
      if (response.status === 200) {
        searchRes.value = response.data?.list
      }
    } catch (err) {
      common.errMsg(err)
    }
  }

  const reservation = (uuid) => {
    router.push({
      name: "regReservationByStaff",
      query: { patientUuid: uuid },
    });
    emit('close');
  }

  const waiting = ({ uuid, name, rrn }) => {
    router.push({
      name: "acceptPatientByStaff",
      query: {
        patientUuid: uuid,
      },
    });
    emit('close');
  }

  const openVisitHistory = (res) =>{
    store.setVisitView({
      patientUuid: res.uuid,
      name :res.name
    })
    emit('close')
  }
</script>

