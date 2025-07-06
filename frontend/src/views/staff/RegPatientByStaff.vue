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

  // 주민번호 전체
  const fullRrn = computed(() => {
    return rrnValid.value ? `${rrnNo.rrnFront}-${rrnNo.rrnBack}` : '';
  });

  // 최초 input 시 실시간 검증 동작 -> 처음에 유효성 메세지 안나타나도록
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

  // 실시간으로 inputData.rrn 값 갱신
  watch(fullRrn, (newVal) => {

    inputData.rrn = newVal;

  });

  // 주민번호 앞자리 입력 완료 시 자동 포커스
  watch(() => rrnNo.rrnFront, (newVal) => {

    if (newVal.length === 6) {
      rrnBackInput.value?.focus();
    }

  });

  const registerPatientByStaff = async () => {

    const name = document.querySelector("#name");
    if(name.length < 1 || name.length > 6) {
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
        resetForm();
        closeModal();
        // router.push({ name: 'regReservationByStaff' });

      }

    } catch (err) {

      common.errMsg(err);

    }

  };

  // 리셋
  const resetForm = () => {

    inputData.name = '';
    inputData.rrn = '';
    inputData.phone = '';
    rrnNo.rrnFront = '';
    rrnNo.rrnBack = '';
    touched.rrn = false;
    touched.phone = false;

  };

  const closeModal = () => {

    document.activeElement?.blur();
    Modal.getOrCreateInstance(document.getElementById('staticBackdrop'))?.hide();

  }

  // Bootstrap 5 모달은 닫힐 때 hidden.bs.modal 이벤트가 발생.
  // 이를 이용해 이벤트 발생 시 초기화 실행.
  onMounted(() => {

    document.getElementById('staticBackdrop')?.addEventListener('hidden.bs.modal', resetForm);

  });

</script>


<template>
  <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
    환자 등록
  </button>

  <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">환자 등록</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="my-3">
            <div class="row g-3 align-items-center">
              <label for="name" class="col-form-label">이름</label>
              <div class="col-auto">
                <input
                    type="text"
                    id="name"
                    v-model="inputData.name"
                    class="form-control"
                    minlength="1"
                    maxlength="6"
                />
              </div>

              <label for="rrn" class="col-form-label">주민등록번호</label>
              <div class="col-auto rrnNo">
                <input
                    type="text"
                    inputmode="numeric"
                    id="rrnFront"
                    v-model="rrnNo.rrnFront"
                    ref="rrnFrontInput"
                    class="form-control"
                    minlength="6"
                    maxlength="6"
                    @input="handleRrnFrontInput"
                />
              </div>
              <div class="col-auto">
                <span>-</span>
              </div>
              <div class="col-auto rrnNo">
                <input
                    type="password"
                    inputmode="numeric"
                    id="rrnBack"
                    v-model="rrnNo.rrnBack"
                    ref="rrnBackInput"
                    class="form-control"
                    minlength="7"
                    maxlength="7"
                    @input="handleRrnBackInput"
                />
              </div>
              <div v-show="touched.rrn && !rrnValid" class="text-danger small">유효한 주민번호 형식이 아닙니다.</div>
              <div v-show="touched.rrn && rrnValid" class="text-success small">유효한 주민번호 형식입니다.</div>

              <label for="telephone" class="col-form-label">전화번호</label>
              <div class="col-auto">
                <input
                    type="tel"
                    id="telephone"
                    class="form-control"
                    v-model.lazy="inputData.phone"
                    minlength="13"
                    maxlength="13"
                    @input="touched.phone = true"
                />
              </div>
              <div v-show="touched.phone && !phoneValid" class="text-danger small">010-0000-0000 형식으로 입력해주세요.</div>
              <div v-show="touched.phone && phoneValid" class="text-success small">유효한 전화번호 형식입니다.</div>
            </div>
          </div>

          <div class="modal-footer">
            <button type="button" class="btn btn-primary" @click="registerPatientByStaff">등록</button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >취소</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

