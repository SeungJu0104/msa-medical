<template>
  <form>
    <div>
      <label for="userid">아이디</label>
      <input type="text" id="userid" v-model="user.userid">
    </div>
    <div>
      <label for="password">비밀번호</label>
      <input type="password" id="password" v-model="user.password">
    </div>
    <button type="submit" @click.prevent="login">로그인</button>
  </form>
</template>

<script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { reactive } from 'vue';

const user = reactive({
  userid: '',
  password: ''
});

async function login() {
  console.log("login() 실행");
  // const response = await customFetch(ENDPOINTS.auth.login);
}
</script>
