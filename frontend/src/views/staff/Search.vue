<script setup>

import {reactive, ref} from "vue";
  import {customFetch} from "@/util/customFetch.js";
  import {ENDPOINTS} from "@/util/endpoints.js";
  import {common} from "@/util/common.js";
import {patientMethods} from "@/reservation/util/reservation.js";
import RegReservation from "@/reservation/views/RegReservation.vue";
import {useRouter} from 'vue-router'
import {errorMessage} from "@/util/errorMessage.js";

  const searchVal = reactive({
    input: ''
  });

  const router = useRouter();
  const searchRes = ref([]);
  const runSearch = ref(false);

  const search = async () => {

    if(searchVal.input.length < 1) {
      common.alert(errorMessage.staff.searchValLength);
      return;
    }

    try {

      const response = await customFetch(ENDPOINTS.staff.search(searchVal.input));

      runSearch.value = true;

      if(response.status === 200) {

        searchRes.value = response.data?.list;

      }

    } catch (err) {

      common.errMsg(err);

    }

  }

  const reservation = (uuid) => {

    router.push({
      name: 'regReservation',
      query: {patientUuid: uuid}
    });

  }

  const waiting = ({uuid, name, rrn}) => {

    router.push({
      name: 'acceptPatientByStaff',
      query: {
        patientUuid: uuid,
        patientName: name,
        birthDate: rrn
      }
    });

  }

</script>

<template>

  <div class="container">
    <div class="input-group mb-3">
      <input class="form-control form-control-lg" type="text" v-model="searchVal.input" minlength="1" maxlength="6" @keyup.enter="search" placeholder="환자명"/>
      <button class="btn btn-outline-secondary" type="button" @click="search">
        <img class="search" src="@/assets/search.png" alt="검색" style="width: 20px; height: 20px;" /><!--css 분리하기-->
      </button>
    </div>
    <div class="my-3">
      <table v-if="runSearch && searchRes.length > 0">
        <tr>
          <th>이름</th>
          <th>생년월일</th>
          <th>전화번호</th>
        </tr>
        <tr v-for="res in searchRes" :key="res.uuid">
          <td v-cloak>{{res.name}}</td>
          <td v-cloak>{{res.rrn}}</td>
          <td v-cloak>{{res.phone}}</td>
          <td><button class="btn btn-outline-secondary" type="button" @click="reservation(res.uuid)">예약</button></td>
          <td><button class="btn btn-outline-secondary" type="button" @click="waiting(res)">대기</button></td>
        </tr>
      </table>

      <div v-if="runSearch && searchRes.length < 1">
        검색결과가 존재하지 않습니다.
      </div>

    </div>
  </div>

</template>