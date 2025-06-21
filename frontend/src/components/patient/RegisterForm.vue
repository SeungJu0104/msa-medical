<template>
  <form>
    <div>
      <label for="userid">아이디</label>
      <input type="text" id="userid" v-model="member.userid">
    </div>
    <button type="button" @click="checkIdDuplicate">중복확인</button>
    <div>
      <label for="password">비밀번호</label>
      <input type="password" id="password" v-model="member.password">
    </div>
    <div>
      <label for="name">이름</label>
      <input type="text" id="name" v-model="member.name">
    </div>
    <div>
      <label for="rrn">주민번호</label>
      <input type="text" id="rrn" v-model="member.rrn">
    </div>
    <div>
      <label for="phone">전화번호</label>
      <input type="text" id="phone" v-model="member.phone">
    </div>
  </form>
  <button @click="registerPatient">회원가입</button>
  <RouterLink :to="{ name: 'home' }">취소</RouterLink>
</template>

<script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { reactive } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

let validChecked = false;
let validId = "";

const member = reactive({
  userid: '',
  password: '',
  name: '',
  phone: '',
  rrn: ''
});

async function registerPatient() {
  // TODO: 실패 시 이동 안 함
  const response = await customFetch(
    ENDPOINTS.auth.register.patient,
    { data: member }
  );
  router.push({ name: 'loginView' });
}

async function checkIdDuplicate() {
  if (8 <= member.userid.length && member.userid.length <= 20) {
    const userid = member.userid;
    const response = await customFetch(ENDPOINTS.auth.checkId, {
      params: { userid }
    });
    if (!response.data.exists) {
      validChecked = true;
      validId = userid;
      alert("아이디가 사용가능합니다.");
      return;
    }
    alert("아이디가 이미 존재합니다.");
  }
}
</script>
