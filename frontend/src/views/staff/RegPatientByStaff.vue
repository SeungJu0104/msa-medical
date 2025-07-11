<script setup>
import {computed, onMounted, reactive, ref, watch} from "vue";
import { useRouter } from 'vue-router';
import { common } from "@/util/common.js";
import { REGEX_PATTERN as RegexPattern } from "@/util/RegexPattern.js";
import { StaffMessage } from "@/staff/util/StaffMessage.js";
import { ENDPOINTS } from "@/util/endpoints.js";
import { customFetch } from "@/util/customFetch.js";
import { Modal } from 'bootstrap';
import {number} from "sockjs-client/lib/utils/random.js";

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
        common.alert(response.data?.message); // 팀장님에게 환자 등록 완료시 메세지 요청하기
        router.back(); // 모달 닫기
      }
    } catch (err) {
      common.errMsg(err);
    }
  };

  const closeModal = () => {
    router.back();
  }

</script>


<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">환자 등록</h5>
        <button type="button" class="btn-close" @click="router.back()"></button>
      </div>
      <div class="modal-body">
        <div class="mb-3">
          <label for="name">이름</label>
          <input
              id="name"
              type="text"
              v-model="inputData.name"
              class="form-control"
              minlength="1"
              maxlength="6"
          />
        </div>
        <div class="mb-3">
          <label>주민등록번호</label>
          <div class="input-group">
            <input
                type="text"
                inputmode="numeric"
                v-model="rrnNo.rrnFront"
                ref="rrnFrontInput"
                class="form-control"
                maxlength="6"
                @input="handleRrnFrontInput"
            />
            <span class="input-group-text">-</span>
            <input
                type="password"
                inputmode="numeric"
                v-model="rrnNo.rrnBack"
                ref="rrnBackInput"
                class="form-control"
                maxlength="7"
                @input="handleRrnBackInput"
            />
          </div>
          <div v-if="touched.rrn" class="form-text" :class="rrnValid ? 'text-success' : 'text-danger'">
            {{ rrnValid ? '유효한 주민번호입니다.' : '유효하지 않은 주민번호입니다.' }}
          </div>
        </div>
        <div class="mb-3">
          <label for="telephone">전화번호</label>
          <input
              id="telephone"
              type="tel"
              class="form-control"
              v-model.lazy="inputData.phone"
              @input="touched.phone = true"
              maxlength="13"
          />
          <div v-if="touched.phone" class="form-text" :class="phoneValid ? 'text-success' : 'text-danger'">
            {{ phoneValid ? '유효한 전화번호입니다.' : '010-0000-0000 형식으로 입력해주세요.' }}
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-primary" @click="registerPatientByStaff">등록</button>
        <button class="btn btn-secondary" @click="closeModal">취소</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1050;
}
.modal-content {
  background: white;
  border-radius: 10px;
  padding: 1.5rem;
  width: 500px;
  max-width: 95%;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
}
</style>

