<script setup>
import {onMounted, reactive, ref} from "vue";
import {common} from "@/util/common.js";
import {patientMethods} from "@/util/reservation.js";
import {customFetch} from "@/util/customFetch.js";
import {ENDPOINTS} from "@/util/endpoints.js";
import {omit} from "lodash";
import {errorMessage} from "@/util/errorMessage.js";

  const selectedVal = reactive({
    doctorUuid: null,
    patientUuid: '550e8400-e29b-41d4-a716-446655440020',
    dateTime: new Date(),
    name: null,
    symptom: null
  });

  const acceptChk = reactive({
    doctorChk : false,
    symptomChk : false
  });

  const fieldLabels = {
    doctorChk : '의사를 선택해주세요.',
    symptomChk : '증상을 입력해주세요.'
  }

  const doctorList = ref();

  const MEMBER_UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";

  const selectDoctor = (doctor) => {

    selectedVal.doctorUuid = doctor.uuid;
    selectedVal.name = doctor.name;

    acceptChk.doctorChk = selectedVal.doctorUuid !== null && MEMBER_UUID_REGEX.test(selectedVal.doctorUuid);

  }


  const writeSymptom = (symptom) => {

    acceptChk.symptomChk = symptom !== null && symptom.trim().length > 0;

  }

  function goHome () {
    common.goHome();
  }

  async function getDoctorList () {
    doctorList.value = await patientMethods.getDoctorList();
  }

  function acceptPatientByStaff(selectedVal) {

    for(const [key, val] of Object.entries(acceptChk)) {
      if(!val) {
        common.alertError(errorMessage.common[key]);
        return;
      }
    }

    customFetch(
        ENDPOINTS.staff.acceptPatientByStaff, {
          ...omit(selectedVal, ['name'])
        }
    )
  }

  onMounted(
      getDoctorList
  )

</script>

<template>
  <div class="dropdown my-3">
    <h3>의사</h3>
    <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false" v-cloak>
      {{ selectedVal?.name || '의사를 선택해주세요.' }}
    </button>
    <ul class="dropdown-menu">
      <template v-for="doctor in doctorList" :key="doctor.uuid">
        <li class="dropdown-item" @click="selectDoctor(doctor)" v-cloak>{{doctor.name}}</li>
      </template>
    </ul>
  </div>
  <div class="my-3 input-group">
    <span class="input-group-text">증상</span>
    <textarea class="form-control" aria-label="symptom" v-model="selectedVal.symptom"
              @change = "writeSymptom(selectedVal.symptom)" maxlength="100"
              placeholder="100자 이내로 작성해주세요.">
      </textarea>
  </div>
  <button type="button" class="btn btn-outline-success" @click="accept(selectedVal)">접수</button>
  <button type="button" class="btn btn-outline-warning" @click="goHome">취소</button>

</template>