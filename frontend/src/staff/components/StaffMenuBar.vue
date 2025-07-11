<script setup>
import '@/css/staff/StaffMenuBar.css'
import {useRouter} from "vue-router";

  const router = useRouter();

  const routerPush = (route) => {
    router.push(route);
  }



</script>

<template>
  <div class="my-3">
    <div class="menuContainer">
      <div id="registration" @click="routerPush({name: 'regPatientByStaff'})">
        <span>환자 등록</span>
      </div>
      <div id="searching" @click="routerPush({name: 'search'})">
        <span>환자 검색</span>
      </div>
      <div id="payment" @click="routerPush({})">
        <span>결제</span>
      </div>
      <div id="setting" @click="routerPush({})">
        <span>설정</span>
      </div>
    </div>
  </div>
</template>