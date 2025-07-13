<template>
  <div class="attachment-card" @click="triggerFileInput">
    <!-- 상단 제목 -->
    <div class="attachment-card-header">
      <span>📎 이미지</span>
    </div>

    <!-- 이미지 미리보기 리스트 -->
    <div class="attachment-card-body">
      <template v-if="state.previewUrls.length">
        <div class="attachment-image-list">
          <div
            class="attachment-image-item"
            v-for="(url, index) in state.previewUrls"
            :key="index"
          >
            <img :src="url" />
            <button class="remove-btn" @click.stop="removeImage(index)">✕</button>
          </div>
        </div>
      </template>
      <template v-else>
        <div class="attachment-placeholder">이미지를 클릭하여 업로드</div>
      </template>
    </div>

    <!-- 숨겨진 파일 선택 -->
    <input
      ref="fileInput"
      type="file"
      accept="image/*"
      multiple
      @change="onFileChange"
      class="attachment-hidden-input"
    />
  </div>
</template>


<script setup>
import { reactive, ref } from 'vue';
import '@/assets/css/attachment.css';

    const fileInput = ref(null) //직접 접근할때 (DOM) 쓰기 좋다 
    const state = reactive({
        files : [],
        previewUrls : [],
    })
    const triggerFileInput = () => {
  fileInput.value?.click();
};

    const onFileChange = (e) => {
        const selectedFiles = Array.from(e.target.files)
        
        selectedFiles.forEach((file) => {
        state.files.push(file)
        state.previewUrls.push(URL.createObjectURL(file))
        })
        if (fileInput.value) {
        fileInput.value.value = ''
    }
     
    }
    const removeImage = (index) => {
        state.files.splice(index,1)
        state.previewUrls.splice(index,1)
  }
  defineExpose({state})
</script>