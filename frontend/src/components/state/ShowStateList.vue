<template>
    <div>
      <div v-for="(item,index) in state.list" :key="item[0]">
        <div v-if="item[1] !='예약' || item[1] !='진료완료'">
            {{ item[0] }} 
            {{item[1]}}
            
        </div>
      </div>
      
    </div>
  </template>
  
  <script setup>
  import { reactive } from 'vue'
  const state = reactive({
    list : [
    ["a", "진료완료"],
    ["b", "대기중"],
    ["c", "대기중"],
    ["d", "예약"],
    ["e", "예약"],
    ["f", "예약"]
  ]
  })
  

    

  
  </script>
  
  