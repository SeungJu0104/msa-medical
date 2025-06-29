<script setup>
import { computed, defineProps } from "vue";

const { status, value } = defineProps({
  status: Array,
  value: String
});

console.log(value);


const patientCurrentStatus = computed(() => value);

const leftOver = computed(() => {
  return status.filter(v => v.name !== value);
});

const emit = defineEmits(["update:value"]);

const selectedStatus = (name) => {
  emit("update:value", name);
}
</script>

<template>
  <button
      class="btn btn-secondary btn-sm dropdown-toggle"
      type="button"
      data-bs-toggle="dropdown"
      aria-expanded="false"
  >
    {{ patientCurrentStatus }}
  </button>
  <template v-if="patientCurrentStatus.trim() !== '진료 중'">
  <ul class="dropdown-menu">
    <li
        v-for="left in leftOver"
        :key="left.id"
        @click="selectedStatus(left.name)"
    >
      {{ left.name }}
    </li>
  </ul>
  </template>
</template>
