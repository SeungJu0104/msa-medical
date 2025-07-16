<script setup>
import {ref, reactive, onMounted, computed, onBeforeUnmount} from 'vue'
import VueDatepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import {common} from '@/util/common.js'
import { patientMethods } from '@/reservation/util/reservation.js'
import {omit} from 'lodash'
import dayjs from "dayjs";
import {errorMessage} from "@/util/errorMessage.js";
import {useUserStore} from "@/stores/userStore.js";
import {useRouter, useRoute} from "vue-router";
import '@/assets/css/RegReservation.css';
import '@/assets/css/icons.css';

  const userInfo = computed(() => useUserStore().user);
  const router = useRouter();
  const route = useRoute();

  const selectedVal = reactive({
    doctorUuid: null,
    patientUuid: null,
    reservationDate: new Date(),
    time: null,
    symptom: null,
    name: null,
    isToday: false
  });

  const reservationChk = reactive({
    doctorChk : false,
    dateChk : false,
    timeChk : false,
    symptomChk : false
  });

  const doctorList = ref();
  const reservationTime = ref();
  const today = dayjs();
  const minDate = today.toDate();
  const maxDate = today.add(6, 'day').toDate();

  const selectDoctor = (doctor) => {

    selectedVal.doctorUuid = doctor.uuid;
    selectedVal.name = doctor.name;

    reservationChk.doctorChk = true;

    handleDate(selectedVal.reservationDate);

  }

  const handleDate = async (selectedDate) => {

    console.log("handleDate selectedDate:", selectedDate);
    selectedVal.reservationDate = selectedDate;

    reservationChk.dateChk = true;

    if(!reservationChk.doctorChk) {
      alert(errorMessage.common.doctorChk);
      return;
    }

    reservationTime.value = await patientMethods.getReservationTime(selectedVal);

    // 예약 정보를 가져오지 못했을 때, 타임 슬롯 생성 막기
    if(reservationTime.value === false) {
      return;
    }

    console.log("다시 프론트");
    console.log(reservationTime.value);

  }

  const disabledWeekends = (date) => {
      return dayjs(date).toDate().getDay() === 0;
  };

  const selectTime = (time) => {
    selectedVal.time = time;
    reservationChk.timeChk = true;

    patientMethods.reservationHold({
      patientUuid: selectedVal.patientUuid,
      doctorUuid: selectedVal.doctorUuid,
      dateTime :
          dayjs(`${common.dateFormatter(selectedVal.reservationDate, 'YYYY-MM-DD')}T${selectedVal.time}:00`).toDate().toISOString()
    })

  }

  const writeSymptom = () => {

    reservationChk.symptomChk = selectedVal.symptom !== null && selectedVal.symptom.trim().length > 0;

  }

  const goHome = async () => {

    if(!reservationChk.timeChk) {
      await common.goStaffHome();
    }

    if(await patientMethods.cancelHoldingReservation(selectedVal.patientUuid)) {
      await common.goStaffHome();
    }

  }

  const reservation = () => {
    console.log(selectedVal.reservationDate);
    console.log(selectedVal.time);

    for (const [key, value] of Object.entries(reservationChk)) {
      if (!value) {
        common.alertError(errorMessage.common[key]);
        return;
      }
    }

    console.log("예약 수행 : ", selectedVal);

    patientMethods.reservation({

      ...omit(selectedVal, ['reservationDate', 'time', 'name']), // date와 time, name 속성을 제외한 나머지 속성들을 복사
      dateTime:
          dayjs(`${common.dateFormatter(selectedVal.reservationDate, 'YYYY-MM-DD')}T${selectedVal.time}:00`).toDate().toISOString()
      // `${dayjs(selectedVal.reservationDate).format('YYYY-MM-DD')}T${selectedVal.time}:00`

    }, router, userInfo.value.role);

  }

  async function getDoctorList () {
    doctorList.value = await patientMethods.getDoctorList();
  }

  const checkRole = () => {

    if(userInfo.value.role === 'PATIENT') {

      selectedVal.patientUuid = userInfo.value.uuid;

    }

    if(userInfo.value.role === 'DOCTOR' || userInfo.value.role === 'NURSE') {

      selectedVal.patientUuid = route.query.patientUuid;

    }

  }

  const isDropdownOpen = ref(false);
  const toggleDropdown = () => {
    isDropdownOpen.value = !isDropdownOpen.value;
  };

  const closeDropdown = () => {
    isDropdownOpen.value = false;
  };

  const handleClickOutside = (event) => {
    const dropdown = document.querySelector('.reg-dropdown-group');
    if (dropdown && !dropdown.contains(event.target)) {
      closeDropdown();
    }
  };

  onMounted(() => {
    getDoctorList();
    checkRole();
    document.addEventListener('mousedown', handleClickOutside);
  });
  onBeforeUnmount(() => {
    document.removeEventListener('mousedown', handleClickOutside);
  });


</script>

<template>
  <div class="reg-reservation-card">
    <div class="reg-reservation-title">
      <img src="@/assets/icons/reservation.png" alt="예약" class="icon"/>
      예약등록
    </div>
    <form class="reg-reservation-form" @submit.prevent>
      <!-- 의사 선택 -->
      <div class="reg-form-row">
        <label class="reg-form-label">의사<span class="reg-required">*</span></label>
        <div class="reg-dropdown-group">
          <button
            class="reg-dropdown-btn"
            type="button"
            @click="toggleDropdown"
            :aria-expanded="isDropdownOpen"
          >
            {{ selectedVal?.name || '의사를 선택해주세요.' }}
          </button>
          <ul class="reg-dropdown-menu" v-if="isDropdownOpen">
            <template v-for="doctor in doctorList" :key="doctor.uuid">
              <li class="reg-dropdown-item" @click="selectDoctor(doctor); closeDropdown()">{{doctor.name}}</li>
            </template>
          </ul>
        </div>
      </div>
      <!-- 날짜 선택 -->
      <div class="reg-form-row">
        <label class="reg-form-label">일자<span class="reg-required">*</span></label>
        <VueDatepicker
          :model-value="selectedVal.reservationDate"
          :format="'yyyy-MM-dd'"
          :min-date="minDate"
          :max-date="maxDate"
          :disabled-dates="disabledWeekends"
          :enable-time-picker="false"
          :input-class="'reg-form-input'"
          :esc-close="false"
          :space-confirm="false"
          @update:model-value="handleDate"
          prevent-min-max-navigation
        />
      </div>
      <!-- 시간 선택 -->
      <div class="reg-form-row" v-if="reservationChk.dateChk && reservationChk.doctorChk && reservationTime">
        <label class="reg-form-label">시간<span class="reg-required">*</span></label>
        <div class="reg-time-group">
          <template v-for="time in Array.from(reservationTime).sort()" :key="time">
            <button type="button" class="reg-btn-time" :class="{ active: selectedVal.time === time }" @click="selectTime(time)" ref="selectedVal.time" v-cloak>{{time}}</button>
          </template>
          <template v-if="!reservationTime.size">
            <span class="reg-helper-danger">예약 가능한 시간대가 없습니다.</span>
          </template>
        </div>
      </div>
      <!-- 증상 입력 -->
      <div class="reg-form-row" v-if="reservationChk.timeChk">
        <label class="reg-form-label">증상<span class="reg-required">*</span></label>
        <textarea class="reg-form-input" aria-label="symptom" v-model="selectedVal.symptom"
                  @change="writeSymptom" maxlength="100"
                  placeholder="100자 이내로 작성해주세요."></textarea>
      </div>
      <!-- 버튼 -->
      <div class="reg-form-actions">
        <button type="button" class="reg-btn-main" @click="reservation">예약</button>
        <button type="button" class="reg-btn-sub" @click="goHome">취소</button>
      </div>
    </form>
  </div>
</template>