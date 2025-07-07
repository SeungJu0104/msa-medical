<template>
    <div>

    </div>
</template>

<script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { reactive } from 'vue';

    const props = defineProps({
    treatmentId: Number
    })
    const state = reactive({
        diseaseList : []
    })
    try {
        const response = await customFetch(ENDPOINTS.treatment.document(props.item.treatmentId))
        if(response.status===200){
            state.diseaseList = response.data.list
        }
    } catch (error) {
        console.error("에러",error)
    }

</script>

<style lang="scss" scoped>

</style>