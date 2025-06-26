<template>
    <div>
        <input type ="file" accept="image/*" multiple @change="onFileChange" ref="fileInput"/>

        <div v-if="state.previewUrls.length">
            <div v-for="(url,index) in state.previewUrls" :key="index">
                <img :src="url" alt="미리보기" class="preview-image" />
                <button @click="removeImage(index)">삭제</button>
            </div>
        </div>
    </div>
    <div v-if="state.files.length > 0">
        선택한 파일 수: {{ state.files.length }}개
    <ul>
    <li v-for="(file, index) in state.files" :key="index">
      {{ index + 1 }}. {{ file.name }}
    </li>
  </ul>
</div>

</template>

<script setup>
import { reactive, ref } from 'vue';

    const fileInput = ref(null) //직접 접근할때 (DOM) 쓰기 좋다 
    const state = reactive({
        files : [],
        previewUrls : [],
    })

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
    
</script>

<style>
    .preview-image{
    max-width: 200px;
    max-height: 200px;
    object-fit: contain;
    border: 1px solid #ddd;
    border-radius: 6px;
    }
</style>