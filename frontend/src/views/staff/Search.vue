<script setup>

import {reactive, ref} from "vue";
  import {customFetch} from "@/util/customFetch.js";
  import {ENDPOINTS} from "@/util/endpoints.js";
  import {common} from "@/util/common.js";
import {patientMethods} from "@/reservation/util/reservation.js";
import RegReservationByPatient from "@/reservation/views/RegReservationByPatient.vue";
import {useRouter} from 'vue-router'

  const searchVal = reactive({
    input: ''
  });

  const router = useRouter();
  const searchRes = ref();
  // const emit = defineEmits(['searchRes']);

  const search = async () => {

    try {

      const response = await customFetch(ENDPOINTS.staff.search(searchVal.input));

      if(response.status === 200) {

        searchRes.value = response.data?.list;
        console.log(searchRes.value);

        // emit('searchRes', searchRes.value);

      }

    } catch (err) {

      common.errMsg(err);

    }

  }

  const reservation = (uuid) => {

    router.push({
      name: 'regReservationByPatient',
      query: {patientUuid: uuid}
    });

  }

  const waiting = () => {



  }

</script>

<template>

  <div class="container">
    <div class="input-group mb-3">
      <input class="form-control form-control-lg" type="text" v-model="searchVal.input" minlength="1" maxlength="6" placeholder="환자명"/>
      <button class="btn btn-outline-secondary" type="button" @click="search">
        <img class="search" src="@/assets/search.png" alt="검색" style="width: 20px; height: 20px;" /><!--css 분리하기-->
      </button>
    </div>
    <div class="my-3">
      <table v-for="res in searchRes" :key="res.uuid">
        <tr>
          <th>이름</th>
          <th>생년월일</th>
          <th>전화번호</th>
        </tr>
        <tr>
          <td v-cloak>{{res.name}}</td>
          <td v-cloak>{{res.rrn}}</td>
          <td v-cloak>{{res.phone}}</td>
          <button class="btn btn-outline-secondary" type="button" @click="reservation(res.uuid)">예약</button>
          <button class="btn btn-outline-secondary" type="button" @click="waiting">대기</button>
        </tr>
      </table>
    </div>
  </div>

</template>