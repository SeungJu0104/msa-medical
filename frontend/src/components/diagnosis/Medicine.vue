<template>
    <div>
      <label>약</label>
      <div> <input type="text" :value="state.search" @input="onInput" @focus="state.isFocus = true" @blur="onBlur" @keyup.enter="submit"/>
        <button @click="clearSearch">지움</button>
        <button @click="submit">확인</button>
      </div>
      <ul v-if="state.isFocus">
        <li v-if="state.medicineList.length === 0"> 검색 결과가 없습니다. </li>
        <li v-else v-for="item in state.medicineList" :key="item.code" @mousedown="selectMedicine(item)">
          {{ item.code }} || {{ item.name }}
        </li>
      </ul>

      <li v-for="(item, index) in state.inputText" :key="index">
        {{ item.code }} - {{ item.name }}
        <input type="number" v-model.number="item.volume" min="1" />
        <button @click="removeMedicine(index)">삭제</button>
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
    medicineList: [],
    selectedMedicine: null,
    inputText : []
  })
  
  const onInput = async (e) => {
    state.search = e.target.value
    if (state.search.trim() === '') {
      state.medicineList = []
      return
    }
  
    try {
      const response = await customFetch(ENDPOINTS.medicine.searchlist(state.search)
      )
      if (response.status === 200) {
        state.medicineList = response.data
      }
    } catch (error) {
      console.error('에러:', error)
    }
  }
  
  const selectMedicine = (item) => {
    state.selectedMedicine = item
    state.search = item.name
    state.medicineList = []
    state.isFocus = false
  }
  
  const clearSearch = () => {
    state.search = ''
    state.medicineList = []
    state.selectedMedicine = null
  }
  const onBlur = () => {
  setTimeout(() => {
    state.isFocus = false
  }, 100)
}

const submit = () => {
    if (state.search.trim() !== '' && state.selectedMedicine) {
        state.inputText.push({
        code: state.selectedMedicine.code,
        name: state.selectedMedicine.name,
        volume: 1 
    })
    state.search = ''
    state.selectedMedicine = null
  }
}
  const removeMedicine = (index) => {
    state.inputText.splice(index,1)
}

  defineExpose({
    inputText: () => state.inputText
})
  </script>
  