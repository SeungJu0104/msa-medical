<template>
  <div class="card p-4 w-100" style="max-width: 720px;">
    <Loading v-if="loading" />
    <template v-else>
      <form>
        <div class="mb-3">
          <label class="form-label">아이디</label>
          <input type="text" :value="member.userid" class="form-control" disabled>
        </div>
        <div class="mb-3">
          <label for="name" class="form-label">이름</label>
          <input type="text" v-model="member.name" class="form-control" id="name">
        </div>
        <div class="mb-3">
          <label for="phone" class="form-label">전화번호</label>
          <input type="text" v-model="member.phone" class="form-control" id="phone">
        </div>
      </form>
      <div class="d-flex justify-content-end gap-2">
        <button @click="updateProfile" class="btn btn-primary">수정</button>
        <button @click="goBack" class="btn btn-danger">취소</button>
      </div>
    </template>
  </div>
</template>

<script setup>
import Loading from '@/common/components/Loading.vue';
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { pick } from 'lodash';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const loading = ref(true);
const member = ref({});

onMounted(async () => {
  const response = await customFetch(ENDPOINTS.patient.profile);
  member.value = response.data.member;
  loading.value = false;
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
