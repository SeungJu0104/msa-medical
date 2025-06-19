<template>
  <div>
    <div>------------</div>
    <div>로고</div>
    <div>
      <div>진료예약</div>
      <div>병원소개</div>
      <div>오시는길</div>
    </div>
    <div>
      <div>
        <RouterLink>로그인</RouterLink>
        <RouterLink :to="{ name: 'patientSignup' }">회원가입</RouterLink>
      </div>
      <div>
        <RouterLink>회원님</RouterLink>
        <RouterLink>로그아웃</RouterLink>
      </div>
    </div>
    <div>------------</div>
  </div>
</template>

<script setup>
</script>
