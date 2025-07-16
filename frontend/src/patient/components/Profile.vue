<template>
  <div class="card w-100" style="max-width: 720px;">
    <div class="card-body text-center">
      <Loading v-if="loading" />
      <template v-else>
        <div class="mt-3 mb-1 fs-4" v-text="member.name"></div>
        <p class="text-muted mb-4">가입일: {{ dayjs(member.registerDate).format('YYYY-MM-DD') }}</p>
        <ul class="list-group list-group-flush text-start">
          <li class="list-group-item d-flex justify-content-between">
            <span class="fw-bold">아이디</span>
            <span v-text="member.userid"></span>
          </li>
          <li class="list-group-item d-flex justify-content-between">
            <span class="fw-bold">전화번호</span>
            <span v-text="member.phone"></span>
          </li>
        </ul>
      </template>
      <div class="d-flex justify-content-end gap-2 mt-4">
        <RouterLink :to="{ name: 'updateProfile' }" class="btn btn-primary">프로필 수정</RouterLink>
      </div>
    </div>
  </div>
</template>

<script setup>
import Loading from '@/common/components/Loading.vue';
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import dayjs from 'dayjs';
import { onMounted, ref } from 'vue';

const loading = ref(true);
const member = ref({});

onMounted(async () => {
  const response = await customFetch(ENDPOINTS.patient.profile);
  member.value = response.data.member;
  loading.value = false;
});
</script>
