<template>
    <div>
        진단<textarea v-model="state.treatContent" placeholder="증상"></textarea>
    </div>
</template>

<script setup>
import { reactive } from 'vue';
    const state = reactive({
        treatContent: '',
        patientUuid : 'a', //환자 이건 상태전환 되는것까지 보고 빼겠습니다.
        doctorUuid : 'b', //의사 이건 상태전환 되는것까지 보고 빼겠습니다.
    })

    defineExpose({ state })
</script>

<style lang="scss" scoped>

</style>