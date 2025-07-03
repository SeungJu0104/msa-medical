<template>
  <div>
    <div>------------</div>
    <RouterLink :to="{ name: 'home' }">로고</RouterLink>
    <div>
      <div>진료예약</div>
      <div>병원소개</div>
      <div>오시는길</div>
    </div>
    <div>
      <div>
        <RouterLink :to="{ name: 'loginView' }">로그인</RouterLink>
        <RouterLink :to="{ name: 'patientRegister' }">회원가입</RouterLink>
      </div>
      <div>
        <RouterLink>회원님</RouterLink>
        <LogoutButton />
      </div>
    </div>
    <div>------------</div>
  </div>
</template>

<script setup>
import LogoutButton from '@/auth/components/LogoutButton.vue';
</script>
