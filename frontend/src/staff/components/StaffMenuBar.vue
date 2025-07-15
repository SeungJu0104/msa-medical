<script setup>
// import '@/assets/css/StaffMenuBar.css'
import LogoutButton from "@/auth/components/LogoutButton.vue";
import {defineEmits} from 'vue'

const emit = defineEmits(['open']);

const menuItems = [
  { label: '홈', icon: new URL('@/assets/icons/home.png', import.meta.url), to: { name: 'staffMain' } },
  { label: '환자 검색', icon: new URL('@/assets/icons/search.png', import.meta.url), action: () => emit('open') },
  { label: '환자 등록', icon: new URL('@/assets/icons/registration.png', import.meta.url), to: { name: 'regPatientByStaff' } },
  { label: '예약 등록', icon: new URL('@/assets/icons/reservation.png', import.meta.url), to: { name: 'regReservationByStaff' } },
  // { label: '통계', icon: new URL('@/assets/icons/stat.png', import.meta.url), to: { name: 'stat' } },
  // { label: '설정', icon: new URL('@/assets/icons/settings.png', import.meta.url), to: { name: 'settings' } },
]


</script>

<template>
  <div class="sidebar">
    <nav>
      <ul>
        <li v-for="item in menuItems" :key="item.label" class="menu-item">
          <router-link v-if="item.to" :to="item.to">
            <img :src="item.icon" alt="icon" class="icon" />
            <span>{{ item.label }}</span>
          </router-link>
          <a v-else @click="item.action" class="menu-clickable">
            <img :src="item.icon" alt="icon" class="icon" />
            <span>{{ item.label }}</span>
          </a>
        </li>
      </ul>
    </nav>
  </div>
</template>