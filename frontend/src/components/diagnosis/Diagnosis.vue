<template>
  <div class="diagnosis-wrapper">
    <div class="top-section">
      <div class="left">
        <VisitHistory />
      </div>
      <div class="center">
        <MedicalTreatment />
        <Disease />
      </div>
      <div class="right">
        <Attachment />
      </div>
    </div>
    <div class="bottom-section">
      
      <Medicine />
    </div>
  </div>
</template>

<script setup>
import Attachment from './Attachment.vue'
import Disease from './Disease.vue'
import MedicalTreatment from './MedicalTreatment.vue'
import Medicine from './Medicine.vue'
import VisitHistory from './VisitHistory.vue'
</script>

<style scoped>
.diagnosis-wrapper {
  height: 400%;
  padding: 20px;
  background-color: #f5f5f5;
}

.top-section {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.left {
  background: white;
  border: 1px solid #ccc;
  padding: 80px;
  height: 400px;
}

.center{
  background: white;
  border: 1px solid #ccc;
  padding: 80px;
  height: 400px;
} 
.right{
  background: white;
  border: 1px solid #ccc;
  padding: 80px;
  height: 400px;
}


.bottom-section {
  background: white;
  border: 1px solid #ccc;
  padding: 80px;
  height: 400px;
  
}

</style>
