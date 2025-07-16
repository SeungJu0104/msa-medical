<script setup>
import { computed, reactive, ref, watch } from "vue";
import { useRouter } from 'vue-router';
import { common } from "@/util/common.js";
import { REGEX_PATTERN as RegexPattern } from "@/util/RegexPattern.js";
import { StaffMessage } from "@/staff/util/StaffMessage.js";
import { ENDPOINTS } from "@/util/endpoints.js";
import { customFetch } from "@/util/customFetch.js";
import {number} from "sockjs-client/lib/utils/random.js";
import '@/assets/css/icons.css';
import '@/assets/css/RegPatientByStaff.css';

  const inputData = reactive({
    name: '',
    rrn: '',
    phone: ''
  });

  const rrnNo = reactive({
    rrnFront: '',
    rrnBack: ''
  });

  const rrnFrontInput = ref();
  const rrnBackInput = ref();
  const router = useRouter();

  const rrnValid = computed(() => {
    const { rrnFront, rrnBack } = common.Validate({
      rrnFront: { regex: RegexPattern.RRN_FRONT, values: [rrnNo.rrnFront] },
      rrnBack: { regex: RegexPattern.RRN_BACK, values: [rrnNo.rrnBack] }
    });
    return rrnFront.includes(true) && rrnBack.includes(true);
  });

  const phoneValid = computed(() => {
    const { phone } = common.Validate({
      phone: { regex: RegexPattern.PHONE, values: [inputData.phone] }
    });
    return phone.includes(true);
  });

  const fullRrn = computed(() => {
    return rrnValid.value ? `${rrnNo.rrnFront}-${rrnNo.rrnBack}` : '';
  });

  const touched = reactive({
    rrn: false,
    phone: false
  });

  const handleRrnFrontInput = (e) => {
    touched.rrn = true;
    rrnNo.rrnFront = e.target.value.replace(/\D/g, '');
  };

  const handleRrnBackInput = (e) => {
    touched.rrn = true;
    rrnNo.rrnBack = e.target.value.replace(/\D/g, '');
  };

  watch(fullRrn, (newVal) => {
    inputData.rrn = newVal;
  });

  watch(() => rrnNo.rrnFront, (newVal) => {
    if (newVal.length === 6) rrnBackInput.value?.focus();
  });

  const registerPatientByStaff = async () => {
    if (inputData.name.length < 1 || inputData.name.length > 6) {
      common.alert(StaffMessage.ERROR.NAME_NOT_VALID);
      return;
    }
    if (!rrnValid.value) {
      common.alert(StaffMessage.ERROR.RRN_NOT_VALID);
      return;
    }
    if (!phoneValid.value) {
      common.alert(StaffMessage.ERROR.PHONE_NOT_VALID);
      return;
    }

    try {
      const response = await customFetch(ENDPOINTS.staff.register, {
        data: inputData
      });
      if (response.status === 201) {
        common.alert(response.data?.message);
        await router.push({ name: 'staffMain' });
      }
    } catch (err) {
      common.errMsg(err);
    }
  };

  const cancel = () => {
    router.push({ name: 'staffMain' });
  };
</script>


<template>
  <div class="reg-patient-card">
    <div class="reg-patient-title">
      <img src="@/assets/icons/registration.png" alt="등록" class="icon"/>
      환자등록
    </div>
    <form class="reg-patient-form" @submit.prevent>
      <div class="reg-form-row">
        <label for="name" class="reg-form-label">이름<span class="reg-required">*</span></label>
        <input
          id="name"
          type="text"
          v-model="inputData.name"
          class="reg-form-input"
          maxlength="6"
          autocomplete="off"
        />
      </div>
      <div class="reg-form-row">
        <label class="reg-form-label">주민등록번호<span class="reg-required">*</span></label>
        <div class="reg-rrn-group">
          <input
            type="text"
            ref="rrnFrontInput"
            v-model="rrnNo.rrnFront"
            maxlength="6"
            class="reg-form-input reg-rrn-input"
            autocomplete="off"
            @input="handleRrnFrontInput"
            placeholder="앞 6자리"
          />
          <span class="reg-rrn-dash">-</span>
          <input
            type="password"
            v-model="rrnNo.rrnBack"
            maxlength="7"
            class="reg-form-input reg-rrn-input"
            ref="rrnBackInput"
            autocomplete="off"
            placeholder="뒤 7자리"
            @input="handleRrnBackInput"
          />
        </div>
        <div v-if="touched.rrn" class="reg-form-helper" :class="rrnValid ? 'reg-helper-success' : 'reg-helper-danger'">
          {{ rrnValid ? '유효한 주민번호입니다.' : '유효하지 않은 주민번호입니다.' }}
        </div>
      </div>
      <div class="reg-form-row">
        <label for="telephone" class="reg-form-label">전화번호<span class="reg-required">*</span></label>
        <input
          id="telephone"
          type="tel"
          v-model="inputData.phone"
          class="reg-form-input"
          @input="touched.phone = true"
          maxlength="13"
          autocomplete="off"
          placeholder="010-0000-0000"
        />
        <div v-if="touched.phone" class="reg-form-helper" :class="phoneValid ? 'reg-helper-success' : 'reg-helper-danger'">
          {{ phoneValid ? '유효한 전화번호입니다.' : '010-0000-0000 형식으로 입력해주세요.' }}
        </div>
      </div>
      <div class="reg-form-actions">
        <button type="submit" class="reg-btn-main" @click="registerPatientByStaff">등록</button>
        <button type="button" class="reg-btn-sub" @click="cancel">취소</button>
      </div>
    </form>
  </div>
</template>
