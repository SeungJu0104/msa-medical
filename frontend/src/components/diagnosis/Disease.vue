<template>
    <div>
      <label>질병</label>
      <div> <input type="text" :value="state.search" @input="onInput" @focus="state.isFocus = true" @blur="onBlur" @keyup.enter="submit"/>
        <button @click="clearSearch">지움</button>
        <button @click="submit">확인</button>
      </div>
      <ul v-if="state.isFocus">
        <li v-if="state.diseaseList.length === 0"> 검색 결과가 없습니다. </li>
        <li v-else v-for="item in state.diseaseList" :key="item.id" @mousedown="selectedDisease(item)">
          {{ item.code }} || {{ item.name }}
        </li>
      </ul>

      <li v-for="(item, index) in state.inputText" :key="index">
        {{ item.code }} - {{ item.name }}
        <button @click="removeDisease(index)">삭제</button> 
      </li>
    </div>
  </template>
  <script setup>
  import { reactive } from 'vue'
  import { customFetch } from '@/util/customFetch'
  import { ENDPOINTS } from '@/util/endpoints'
  
  const state = reactive({
    search: '',
    isFocus: false,
    diseaseList: [],
    selectedDisease: null,
    inputText : [],
  })
  
  const onInput = async (e) => {
    state.search = e.target.value
    if (state.search.trim() === '') {
      state.diseaseList = []
      return
    }
  
    try {
      const response = await customFetch(ENDPOINTS.disease.searchlist(state.search)
      )
      if (response.status === 200) {
        state.diseaseList = response.data
      }
    } catch (error) {
      console.error('에러:', error)
    }
  }
  
  const selectedDisease = (item) => {
    state.selectedDisease = item
    state.search = item.name
    state.diseaseList = []
    state.isFocus = false
  }
  
  const clearSearch = () => {
    state.search = ''
    state.diseaseList = []
    state.selectedDisease = null
  }
  const onBlur = () => {
  setTimeout(() => {
    state.isFocus = false
  }, 100)
}

const submit = () => {
    if (state.search.trim() !== '' && state.selectedDisease) {
        state.inputText.push(state.selectedDisease)
        state.search = ''
        state.selectedDisease = null
  }
}

const removeDisease=  (index) => {
    state.inputText.splice(index,1)
}

defineExpose({
  inputText: () => state.inputText
})
  </script>
  