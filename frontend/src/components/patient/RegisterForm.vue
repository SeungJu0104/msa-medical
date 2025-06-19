<template>
  <form>
    <div>
      <label for="userid">아이디</label>
      <input type="text" id="userid" v-model="member.userid">
    </div>
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
import { reactive } from 'vue';

const member = reactive({
  userid: '',
  password: '',
  name: '',
  phone: '',
  rrn: ''
});

async function registerPatient() {
  console.log("register patient");
}
</script>
