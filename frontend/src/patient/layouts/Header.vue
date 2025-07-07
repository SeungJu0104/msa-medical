<template>
  <header>
    <nav class="navbar navbar-expand navbar-light bg-secondary-subtle py-0">
      <div class="container-fluid justify-content-end">
        <ul class="navbar-nav">
          <li class="nav-item">
            <RouterLink :to="{ name: 'loginView' }" class="nav-link px-2 text-secondary fs-sm">로그인</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink :to="{ name: 'patientRegister' }" class="nav-link px-2 text-secondary fs-sm">회원가입</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink :to="{ name: 'mypage' }" class="nav-link px-2 text-secondary fs-sm">회원님</RouterLink>
          </li>
          <li class="nav-item">
            <LogoutButton class="nav-link px-2 text-secondary fs-sm" />
          </li>
        </ul>
      </div>
    </nav>
  
    <nav class="navbar navbar-expand navbar-light bg-white border-bottom py-3">
      <div class="container-fluid">
        <RouterLink :to="{ name: 'home' }" class="navbar-brand me-4">로고</RouterLink>
        <ul class="navbar-nav mx-auto mb-0 overflow-auto">
          <li class="nav-item px-2">
            <RouterLink class="nav-link fw-bold fs-lg">진료 예약</RouterLink>
          </li>
          <li class="nav-item px-2">
            <RouterLink class="nav-link fw-bold fs-lg">병원 소개</RouterLink>
          </li>
          <li class="nav-item px-2">
            <RouterLink class="nav-link fw-bold fs-lg">오시는 길</RouterLink>
          </li>
        </ul>
      </div>
    </nav>
  </header>
</template>

<script setup>
import LogoutButton from '@/auth/components/LogoutButton.vue';
</script>
