<template>
    <div>
        <input v-model="state.uuid" placeholder="아이디 입력하쇼"/>
        <button @click="'login'">로그인</button>
    </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useAuthStore } from '@/stores/counter'
import { customFetch } from '@/util/customFetch'

const stat = reactive({
  uuid:''
})
const login = async () => {
  if (!uuid.value) return alert('아이디 입력하세요')

  try {
    const { data, status } = await customFetch(
      {
        url: '/member/login',
        method: 'POST'
      },
      {
        data: {
          uuid: uuid.value
        }
      }
    )

    if (status === 200) {
      auth.setUser(data)
      alert('로그인 성공')
      router.push('/chatrooms')
    }

  } catch (error) {
    console.error('로그인 실패:', error)
    alert('아이디 또는 비밀번호가 틀렸습니다.')
  }
}

</script>
<style lang="scss" scoped>

</style>