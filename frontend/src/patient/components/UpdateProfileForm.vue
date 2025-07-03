<template>
  <form>
    <div>
      <label>아이디</label>
      <input type="text" :value="member.userid" readonly>
    </div>
    <div>
      <label>이름</label>
      <input type="text" v-model="member.name">
    </div>
    <div>
      <label>전화번호</label>
      <input type="text" v-model="member.phone">
    </div>
  </form>
  <button @click="updateProfile">수정</button>
  <button @click="goBack">취소</button>
</template>

<script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { pick } from 'lodash';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const member = ref({});

onMounted(async () => {
  const response = await customFetch(ENDPOINTS.patient.profile);
  member.value = response.data.member;
});

async function updateProfile() {
  const response = await customFetch(ENDPOINTS.patient.updateProfile, {
    data: pick(member.value, ["name", "phone"])
  });
  router.push({ name: 'mypage' });
}

async function goBack() {
  router.back();
}
</script>
