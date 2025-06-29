<script setup>

import {computed, ref} from "vue";

const props = defineProps({
                status: Array,
                value: String
              })

console.log(props.status);
console.log(props.value);
const patientCurrentStatus = ref(props.value);
const leftOver = computed(() => new Set(props.status.filter(v => v !== props.value)));

</script>

<template>
  <button
      class="btn btn-secondary btn-sm dropdown-toggle"
      type="button" data-bs-toggle="dropdown"
      aria-expanded="false" :value="patientCurrentStatus">
  </button>
  <ul class="dropdown-menu" v-for="left in leftOver">
    <li :value="left"></li>
  </ul>
</template>