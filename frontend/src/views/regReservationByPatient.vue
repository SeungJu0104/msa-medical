<script setup lang="ts">
import {ref, reactive, onMounted, computed} from 'vue'
import VueDatepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import { customFetch } from '@/util/customFetch'
import router from '@/router'

  const doctorList = ref();
  const selectedDoctor = ref({
    uuid: '',
    name: ''
  });

  const selectDoctor = (doctor) => {
    console.log(doctor.uuid);
    console.log(doctor.name);
    selectedDoctor.value.uuid = doctor.uuid;
    selectedDoctor.value.name = doctor.name;
  }
const minDate = new Date();
let maxDate = new Date();
maxDate.setDate(minDate.getDate() + 7);
// const allowedDates = computed(() => {
  //   const dates = [];
  //   let date;
  //   for(let i = 0; i < 7; i++){
  //     date = new Date(new Date().setDate(new Date().getDate() + i));
  //     dates.push(date);
  //   }
  //   return dates;
  // });

  let selectedTime = ref([]);

  let selectedDate = ref(new Date());

  onMounted(() =>
      customFetch({
        method: 'get',
        url: '/doctor/list'
      })
      .then(
          response => {
            if (response.status === 200) {
              doctorList.value = response.data?.list;
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
      <VueDatepicker
          v-model="selectedDate" :format="'yyyy-MM-dd'" :min-date="minDate" :max-date="maxDate"
          :enable-time-picker="false"  :input-class="'form-control'" prevent-min-max-navigation
      />
    </div>
    <div class="my-3">
      <h3>시간</h3>

    </div>
  </div>
</template>