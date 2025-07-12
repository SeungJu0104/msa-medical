<script setup>
import '@/css/staff/StaffMenuBar.css'

</script>

<template>
  <div class="my-3">

    <div class="menuContainer">
      <router-link :to="{name: 'regPatientByStaff'}">환자 등록</router-link>
      <router-link :to="{name: 'search'}">환자 검색</router-link>
      <router-link :to="{name: ''}">결제</router-link>
      <router-link :to="{name: ''}">설정</router-link>

    </div>
  </div>
</template>