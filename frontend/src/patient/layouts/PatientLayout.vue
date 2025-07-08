<template>
  <div class="layout-container">
    <Header />
    <main class="content-area">
      <RouterView />
    </main>
    <Footer />
  </div>
</template>

<script setup>
import Footer from './Footer.vue';
import Header from './Header.vue';
</script>

<style scoped>
.layout-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.content-area {
  flex: 1;
}
</style>
