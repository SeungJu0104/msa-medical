<template>
    <div>
        <input v-model="state.uuid" placeholder="아이디 입력하쇼"/>
        <button @click="login">로그인</button>
    </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useAuthStore } from '@/stores/counter'
import { customFetch } from '@/util/customFetch'
import { useRouter } from 'vue-router';

const state = reactive({
  uuid:''
})
const auth = useAuthStore();
const router = useRouter();

const login = () => {
  if (!state.uuid) return alert('아이디 입력하세요')

  customFetch({
    url: '/member/login',
    method: 'POST',
    data: {uuid: state.uuid}
  })
  .then(({ data, status }) => {
  if (status === 200) {
    auth.setUser(data)
    alert('로그인 성공')
    router.push({ name: 'chatrooms' })
  }
  })
  .catch(error => {
  console.error('로그인 실패:', error)
  alert('아이디 또는 비밀번호가 틀렸습니다.')
  })

}

</script>
<style lang="scss" scoped>

</style>