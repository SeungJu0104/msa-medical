<script setup>
import {computed, defineProps} from "vue";
import dayjs from "dayjs";

const { status, value, date } = defineProps({
  status: Array,
  value: String,
  date: dayjs
});

const patientCurrentStatus = computed(() => value);

const filterStages = {
  left: { //'현재 상태 제외'
    condition: ({v, value}) => {
      return (v.name !== value)
    }
  },
  excludeWaiting: { //'오늘이 아니면 대기 제외'
    condition: ({v, date}) => {
      const isToday = dayjs().isSame(date, 'day');
      return !(!isToday && v.name === '대기');

    }
  }
};

const leftOver = computed(() => {
  return Object.values(filterStages).reduce((acc, stage) => {
    return acc.filter(v => stage.condition({v, date, value}));
  }, status);
});

const emit = defineEmits(["update:value"]);

const selectedStatus = (name) => {
  emit("update:value", name);
}

</script>

<template>

  <button
      class="btn btn-secondary btn-sm"
      type="button"
      v-bind="patientCurrentStatus.trim() !== '진료 중' ? { 'data-bs-toggle': 'dropdown', 'aria-expanded': 'false' } : {}"
      :class="{ 'dropdown-toggle': patientCurrentStatus.trim() !== '진료 중' }"
  >
    {{ patientCurrentStatus }}
  </button>

  <ul
      class="dropdown-menu"
      v-if="patientCurrentStatus.trim() !== '진료 중'"
  >
    <li
        v-for="left in leftOver"
        :key="left.id"
        @click="selectedStatus(left.name)"
    >
      <span v-cloak>{{ left.name }}</span>
    </li>
  </ul>
</template>

