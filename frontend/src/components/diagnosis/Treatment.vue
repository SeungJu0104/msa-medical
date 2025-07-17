<template>
    <div class="treatment-box">
    <textarea id="symptom" v-model="state.treatContent" placeholder="증상 내용을 입력하세요..."></textarea>
  </div>
</template>

<script setup>
import {reactive } from 'vue';
import '@/assets/css/Treatment.css';

    const state = reactive({
        treatContent: '',   
     })

    defineExpose({ state })
</script>
