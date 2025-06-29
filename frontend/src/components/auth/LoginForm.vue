<template>
  <form>
    <div>
      <label for="userid">아이디</label>
      <input type="text" id="userid" v-model="form.userid">
    </div>
    <div>
      <label for="password">비밀번호</label>
      <input type="password" id="password" v-model="form.password">
    </div>
    <button type="submit" @click.prevent="login">로그인</button>
  </form>
</template>

<script setup>
import { setAccessToken } from '@/auth/accessToken';
import { useUserStore } from '@/stores/userStore';
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { getMe } from '@/util/getMe';
import { computed, reactive } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const user = computed(() => useUserStore().user);
const form = reactive({
  userid: '',
  password: ''
});

async function login() {
  const response = await customFetch(ENDPOINTS.auth.login, {
    data: form
  });
  setAccessToken(response.data.accessToken);
  await getMe();
  if (user.value.role === "PATIENT") {
    router.push({ name: 'home' });
  } else if (user.value.role === "DOCTOR" || user.value.role === "NURSE") {
    router.push({ name: 'home'});
  } else if (user.value.role === "ADMIN") {
    router.push({ name: 'adminView' });
  }
}
</script>
