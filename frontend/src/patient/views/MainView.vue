<template>
  <div class="menu">
    <RouterLink class="menu-card reservation">진료 예약</RouterLink>
    <RouterLink class="menu-card confirmation">예약 확인</RouterLink>
  </div>
</template>

<script setup>
</script>

<style scoped>
.menu {
  display: flex;
  gap: 1rem;
  padding: 2rem;
  justify-content: center;
}

.menu-card {
  flex: 0 1 300px;
  height: 180px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.4rem;
  font-weight: 600;
  text-decoration: none;
}

.reservation {
  background-color: #1e4ed0;
  color: #fff;
}

.confirmation {
  background-color: #fff;
  color: #000;
  border: 1px solid #e0e0e0;
}
</style>
