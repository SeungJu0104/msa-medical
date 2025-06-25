<template>
    <div>
      <div v-for="(item,index) in state.list" :key="item[0]">
        {{ item[0] }} <br/>
        <button @click="change(index)">{{item[1]}}</button><br/>
        <div v-if="item[1] === '예약'">
            <button>예약 취소</button><br/>
        </div>
        
      </div>
      
    </div>
  </template>
  
  <script setup>
  import { reactive } from 'vue'
  const state = reactive({
    list : [
    ["a", "진료중"],
    ["b", "대기중"],
    ["c", "대기중"],
    ["d", "예약"],
    ["e", "예약"],
    ["f", "예약"]
  ]
  })
  
  
    function change(index) {
  const current = state.list[index][1]

  if (current === "예약") {
    state.list[index][1] = "대기중"
  } else if (current === "대기중") {
    state.list[index][1] = "진료중"
  }else if (current === "진료중") {
    state.list[index][1] = "진료완료"
  }else if (current === "진료완료") {
    state.list[index][1] = "예약"
  }

  // "진료중"이면 아무것도 하지 않음
}
    

  
  </script>
  
  