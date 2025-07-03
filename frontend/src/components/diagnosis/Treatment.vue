<template>
    <div>
        진단<textarea v-model="state.treatContent" placeholder="증상"></textarea>
    </div>
</template>

<script setup>
import {reactive } from 'vue';

    const state = reactive({
        treatContent: '',   
     })

    defineExpose({ state })
</script>

<style lang="scss" scoped>

</style>