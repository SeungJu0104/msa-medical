<script setup>
import { computed, reactive, ref, watch } from "vue";
import { useRouter } from 'vue-router';
import { common } from "@/util/common.js";
import { REGEX_PATTERN as RegexPattern } from "@/util/RegexPattern.js";
import { StaffMessage } from "@/staff/util/StaffMessage.js";
import { ENDPOINTS } from "@/util/endpoints.js";
import { customFetch } from "@/util/customFetch.js";
import {number} from "sockjs-client/lib/utils/random.js";
import '@/assets/css/icons.css'

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
<style scoped>
.reg-patient-card {
  max-width: 500px;
  min-width: 400px;
  margin: 40px auto;
  background: #fff;
  border-radius: 14px;
  box-shadow: 0 2px 8px rgba(30, 64, 175, 0.06);
  padding: 32px 28px 24px 28px;
  border: 1px solid #f0f1f3;
  font-family: 'Noto Sans KR', sans-serif;
}

.reg-patient-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #222;
  margin-bottom: 24px;
  letter-spacing: -0.5px;
}

.reg-patient-form {
  display: flex;
  flex-direction: column;
  gap: 22px;
}

.reg-form-row {
  max-width: 500px;
  min-width: 300px;
  display: flex;
  flex-direction: column;
  gap: 7px;
}

.reg-form-label {
  font-size: 0.97rem;
  color: #6b7280;
  font-weight: 400;
  margin-bottom: 2px;
  letter-spacing: -0.5px;
}

.reg-required {
  color: #e74c3c;
  margin-left: 2px;
  font-size: 1em;
}

.reg-form-input {
  border: 1.5px solid #e5e7eb;
  border-radius: 7px;
  padding: 9px 12px;
  font-size: 1rem;
  background: #f8fafc;
  transition: border 0.2s;
  outline: none;
}

.reg-form-input:focus {
  border-color: #e6f0ff;
  background: #fff;
}

.reg-rrn-group {
  display: flex;
  align-items: center;
  gap: 7px;
}

.reg-rrn-input {
  width: 150px;
  text-align: center;
}

.reg-rrn-dash {
  font-size: 1.2em;
  color: #bbb;
  margin: 0 2px;
}

.reg-form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 18px;
}

.reg-btn-main {
  background: #e6f0ff;
  color: #2563eb;
  border: none;
  border-radius: 7px;
  padding: 8px 22px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
}

.reg-btn-main:hover {
  background: #82a5f0;
}

.reg-btn-sub {
  background: rgb(253, 179, 179);
  color: red;
  border: none;
  border-radius: 7px;
  padding: 8px 18px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.2s;
}

.reg-btn-sub:hover {
  background: rgb(255, 110, 110);
}

.reg-form-helper {
  font-size: 0.92em;
  margin-top: 2px;
  margin-left: 2px;
}

.reg-helper-success {
  color: #2563eb;
}

.reg-helper-danger {
  color: #e74c3c;
}
</style>
