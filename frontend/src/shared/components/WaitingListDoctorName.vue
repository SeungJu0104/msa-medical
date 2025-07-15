<script setup>

defineProps({
  value: Object
})

</script>

<template>

  <span v-cloak>{{value.name}}</span>

</template>
