<script setup lang="ts">

import LogoutButton from "@/auth/components/LogoutButton.vue";
</script>

<template>
  <nav class="navbar navbar-expand navbar-light bg-secondary-subtle py-0">
    <div class="container-fluid justify-content-end">
      <ul class="navbar-nav">
        <li class="nav-item">
          <LogoutButton class="nav-link px-2 text-secondary fs-sm" />
        </li>
      </ul>
    </div>
  </nav>
</template>