<template>
  <button @click="logout">로그아웃</button>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { removeAccessToken } from '../accessToken';
import { getRefreshToken, removeRefreshToken } from '../refreshToken';
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';

const router = useRouter();

async function logout() {
  await customFetch(ENDPOINTS.auth.logout, {
    data: { refreshToken: getRefreshToken() }
  });
  removeAccessToken();
  removeRefreshToken();
  router.push({ name: 'loginView' });
}
</script>
