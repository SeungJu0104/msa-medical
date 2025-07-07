<template>
    <div>
        <h3>{{ state.treatment.treatContent }}</h3>
        <h3>{{ state.treatment.treatDate }}</h3>
        <ul>
        <li v-for="d in state.diagnosis" :key="d.id">
            질병명: {{ d.id }} || {{ d.name }}
        </li>
        <li v-for="p in state.prescriptions" :key="p.id">
            약: {{ p.code }} || {{ p.name }} ||({{ p.volume }})
        </li>
        <li v-for="a in state.attachments" :key="a.id">
            첨부파일: {{ a.originalName }}
            <div v-if="a.contentType.startsWith('image/')">
            <img
            :src="`http://localhost:8080/attachment/${a.fileName}`"
            alt="첨부 이미지"
            style="max-width: 200px; margin-top: 8px"
            />
        </div>

        <div v-else>
            <a :href="`http://localhost:8080/attachment/${a.fileName}`" download>
            파일 다운로드
            </a>
        </div>
        </li>
        </ul>

    <button @click="emit('back')">목록으로</button>
    </div>
</template>

<script setup>
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';
import { onMounted, reactive } from 'vue';


const props = defineProps({id:Number})
const emit = defineEmits(['back'])
const state = reactive({
    treatment: {},
    diagnosis: [],
    prescriptions: [],
    attachments: []
})
onMounted( async ()=> {
    try {
        const response = await customFetch(ENDPOINTS.treatment.historyDetail(props.id))
        if(response.status ===200){
        const data = response.data.total
        state.treatment = data.treatment
        state.diagnosis = data.diagnosis
        state.prescriptions = data.prescriptions
        state.attachments = data.attachments
    }    
    } catch (error) {
        console.error("에러",error)
    }
})
</script>