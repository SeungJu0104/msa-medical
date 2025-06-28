<template>
  <RouterView />
</template>

<script setup>
import { onMounted } from 'vue';
import { getMe } from '@/util/getMe';

onMounted(getMe);
</script>
