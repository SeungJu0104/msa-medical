<template>
    <div class="modal-overlay">
     <div class="modal-window">
       <h3>채팅방 만들기</h3>
       <input v-model="state.roomName" placeholder="채팅방 이름" />
       <div v-for="member in state.members" :key="member.uuid">
         <input type="checkbox" :value="member.uuid" v-model="state.selectedMembers" />
         {{ member.uuid }}
       </div>
 
       <div class="modal-footer">
         <button @click="submit">등록</button>
         <button @click="$emit('close')">닫기</button>
       </div>
     </div>
   </div>
     
 </template>
<script setup>
import { inject, onMounted, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/counter'
import { customFetch } from '@/util/customFetch';
import { ENDPOINTS } from '@/util/endpoints';

const router = useRouter()
const auth = useAuthStore();
const uuid = auth.user.uuid
const emit = defineEmits(['close','refresh'])

const state = reactive({
    roomName: '',
    selectedMembers: [uuid],
    members: [],
})

onMounted(()=>{
  loadUuidList()
})

const loadUuidList = async () => {
  try {
    const response= await customFetch(ENDPOINTS.staff.list(uuid))
    state.members = response.data.list
    
  } catch (error) {
    alert("채팅방 목록을 불러올 수 없습니다.")
    console.error("에러:", error)
  }
}


const submit =   async ()  => {
    if (!state.roomName.trim()) {
    alert('채팅방 이름을 입력하세요.')
    return
  }
  if (state.selectedMembers.length === 0) {
    alert('참여할 멤버를 선택하세요.')
    return
  }
  const data = {
    roomName: state.roomName,
    uuid: uuid,
    members: state.selectedMembers
  }

  try {
    const response = await customFetch(ENDPOINTS.chat.createChatRoom,{data})
    if(response.status===200){
      const roomId = response.data.roomId;
      alert("채팅방 등록이 완료되었습니다.")
      emit('refresh',roomId)
      emit('close')
  } }
  catch (error) {
    console.error("에러:", error)
  }}
</script>