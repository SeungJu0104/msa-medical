<template>
  <div>아이디</div>
  <div v-text="member.userid"></div>
  <div>이름</div>
  <div v-text="member.name"></div>
  <div>전화번호</div>
  <div v-text="member.phone"></div>
  <div>가입일</div>
  <div v-text="dayjs(member.registerDate).format('YYYY-MM-DD')"></div>
</template>

<script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import dayjs from 'dayjs';
import { onMounted, ref } from 'vue';

const member = ref({});

onMounted(async () => {
  const response = await customFetch(ENDPOINTS.patient.profile);
  member.value = response.data.member;
});
</script>
