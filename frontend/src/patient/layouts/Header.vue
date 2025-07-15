<template>
  <header>
    <nav class="navbar navbar-expand navbar-light bg-secondary-subtle py-0">
      <div class="container-fluid justify-content-end">
        <ul class="navbar-nav">
          <template v-if="user">
            <li class="nav-item">
              <RouterLink :to="{ name: 'mypage' }" class="nav-link px-2 text-secondary fs-sm">마이페이지</RouterLink>
            </li>
            <li class="nav-item">
              <LogoutButton class="nav-link px-2 text-secondary fs-sm" />
            </li>
          </template>
          <template v-else>
            <li class="nav-item">
              <RouterLink :to="{ name: 'loginView' }" class="nav-link px-2 text-secondary fs-sm">로그인</RouterLink>
            </li>
            <li class="nav-item">
              <RouterLink :to="{ name: 'patientRegister' }" class="nav-link px-2 text-secondary fs-sm">회원가입</RouterLink>
            </li>
          </template>
        </ul>
      </div>
    </nav>
  
    <nav class="navbar navbar-expand navbar-light bg-white border-bottom py-3">
      <div class="container-fluid">
        <RouterLink :to="{ name: 'home' }" class="navbar-brand me-4">로고</RouterLink>
        <ul class="navbar-nav mx-auto mb-0 overflow-auto">
          <li class="nav-item px-2">
            <RouterLink :to="{ name: 'regReservationByPatient' }" class="nav-link fw-bold fs-lg">진료 예약</RouterLink>
          </li>
          <li class="nav-item px-2">
            <RouterLink :to="{ name: 'reservationListByPatient' }" class="nav-link fw-bold fs-lg">예약 확인</RouterLink>
          </li>
        </ul>
      </div>
    </nav>
  </header>
</template>

<script setup>
import LogoutButton from '@/auth/components/LogoutButton.vue';
import { useUserStore } from '@/stores/userStore';
import { computed } from 'vue';

const user = computed(() => useUserStore().user);
</script>
