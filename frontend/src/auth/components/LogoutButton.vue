<template>
  <button @click="logout">로그아웃</button>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { removeAccessToken } from '../accessToken';
import { getRefreshToken, removeRefreshToken } from '../refreshToken';
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { useUserStore } from '@/stores/userStore';
import { disconnectStompClient } from '@/util/stompMethod';

const router = useRouter();

async function logout() {
  await customFetch(ENDPOINTS.auth.logout, {
    data: { refreshToken: getRefreshToken() }
  });
  removeAccessToken();
  removeRefreshToken();
  useUserStore().clearUser();
  await disconnectStompClient();

  router.push({ name: 'loginView' });
}
</script>
