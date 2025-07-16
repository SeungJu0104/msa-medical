<template>
  <div class="card p-4 w-100" style="max-width: 720px;">
    <form>
      <div class="mb-3">
        <input type="text" class="form-control" placeholder="아이디" v-model="form.userid"/>
      </div>
      <div class="mb-3">
        <input type="password" class="form-control" placeholder="비밀번호" v-model="form.password"/>
      </div>
      <div class="d-grid mb-3">
        <button class="btn btn-primary text-white" @click.prevent="login">로그인</button>
      </div>
    </form>
    <RouterLink :to="{ name: 'patientRegister' }" class="btn btn-light text-secondary">회원가입</RouterLink>
  </div>
</template>

<script setup>
import { setAccessToken } from '@/auth/accessToken';
import { setRefreshToken } from '@/auth/refreshToken';
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
  try {
    const response = await customFetch(ENDPOINTS.auth.login, {
      data: form
    });
    setAccessToken(response.data.accessToken);
    setRefreshToken(response.data.refreshToken);
  } catch (err) {
    alert(err.response.data.message);
    return;
  }
  await getMe();
  if (user.value.role === "PATIENT") {
    router.push({ name: 'home' });
  } else if (user.value.role === "DOCTOR" || user.value.role === "NURSE") {
    router.push({ name: 'staffMain'});
  } else if (user.value.role === "ADMIN") {
    router.push({ name: 'adminView' });
  }
}
</script>
