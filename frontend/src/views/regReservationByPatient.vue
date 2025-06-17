<script setup lang="ts">
import {ref, reactive, onMounted} from 'vue'
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import { customFetch } from '@/util/customFetch'
import router from '@/router'

  const doctorList = ref();
  const selectedDoctor = ref({
    uuid: '',
    name: ''
  });

  const selectDoctor = (doctor) => {
    selectedDoctor.value.uuid = doctor.uuid;
    selectedDoctor.value.name = doctor.name;
  }
  const today = new Date();
  let maxDate = new Date();
  maxDate.setDate(today.getDate() + 7);

  let selectedTime = ref();

  let selectedDate = ref(today);

  onMounted(
      customFetch({
        method: 'post',
        url: '/patient/getDoctorList'
      })
      .then(
          response => {
            if (response.status === 200) {
              doctorList.value = response.doctorList;
            }
          }
      )
      .catch(
          err => {
            err.response?.data?.message ? alert(err.response?.data?.message) : alert("실행 중 오류가 발생했습니다. 다시 실행해주세요.");
            router.push({name: 'home'});
          }
      )
  );


</script>

<template>
  <div class="container mt-4">
    <h2>예약등록</h2>
    <div class="dropdown my-3">
      <h3>의사</h3>
      <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
        {{ selectedDoctor?.name || '의사를 선택해주세요.' }}
      </button>
      <ul class="dropdown-menu">
          <template v-for="doctor in doctorList" :key="doctor.uuid">
            <li class="dropdown-item" @click="selectDoctor(doctor)">{{doctor.name}}</li>
          </template>
      </ul>
    </div>
    <div class="my-3">
      <h3>일자</h3>
      <Datepicker v-model="selectedDate" :format="'yyyy-MM-dd'" :min-date="today" :max-date="maxDate" :input-class="'form-control'"/>
    </div>
    <div class="my-3">
      <h3>시간</h3>

    </div>
  </div>
</template>