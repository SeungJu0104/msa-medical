<template>
  <button @click="logout">로그아웃</button>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { removeAccessToken } from '../accessToken';
import { removeRefreshToken } from '../refreshToken';

const router = useRouter();

function logout() {
  removeAccessToken();
  removeRefreshToken();
  router.push({ name: 'loginView' });
}
</script>
