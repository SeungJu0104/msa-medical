<script setup>
import '@/assets/css/StaffMenuBar.css';
import '@/assets/css/icons.css';
import { defineEmits } from 'vue';
import { useRouter } from 'vue-router';

const emit = defineEmits(['open']);
const router = useRouter();

const action = () => {
  emit('open');
}

const goStaffMain = () => {
  router.push({ name: 'staffMain' });
}

const menuItems = [
  { label: '홈', icon: new URL('@/assets/icons/home.png', import.meta.url), to: { name: 'staffMain' } },
  { label: '환자검색', icon: new URL('@/assets/icons/search.png', import.meta.url), action: action },
  { label: '환자등록', icon: new URL('@/assets/icons/registration.png', import.meta.url), to: { name: 'regPatientByStaff' } },
  { label: '예약등록', icon: new URL('@/assets/icons/reservation.png', import.meta.url), action: action },
  { label: '방문접수', icon: new URL('@/assets/icons/waiting.png', import.meta.url), action: action },
]


</script>

<template>
  <div class="sidebar">
    <nav>
      <img class="logo" src="@/assets/logo.png" @click="goStaffMain" />
      <ul>
        <li v-for="item in menuItems" :key="item.label" class="menu-item">
          <router-link v-if="item.to" :to="item.to">
            <img :src="item.icon" alt="아이콘" class="icon" />
            <span>{{ item.label }}</span>
          </router-link>
          <div v-else @click="item.action" class="menu-clickable">
            <img :src="item.icon" alt="아이콘" class="icon" />
            <span>{{ item.label }}</span>
          </div>
        </li>
      </ul>
    </nav>
  </div>
</template>
