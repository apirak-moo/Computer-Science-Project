<template>
  <div class="w-full font-sans">
    <label
      :for="id"
      class="relative flex flex-col items-center justify-center w-full min-h-64 p-5 text-center border-2 border-dashed rounded-2xl cursor-pointer transition-all duration-300 ease-in-out group"
      :class="{
        'border-slate-300/80 dark:border-slate-700 bg-slate-50 dark:bg-slate-800/20 hover:border-sky-500 dark:hover:border-sky-600 hover:bg-white dark:hover:bg-slate-800/60': !hasFiles,
        'bg-white dark:bg-slate-800/80 border-slate-300/80 dark:border-slate-700': hasFiles,
        'border-sky-500 bg-sky-50 dark:bg-sky-900/30 ring-4 ring-sky-500/20': isDragging,
        'opacity-60 cursor-not-allowed': disabled,
      }"
      @dragover.prevent="onDragOver"
      @dragleave.prevent="onDragLeave"
      @drop.prevent="onDrop"
    >
      <input
        :id="id"
        ref="fileInput"
        type="file"
        class="hidden"
        :accept="accept"
        :disabled="disabled"
        :multiple="multiple"
        @change="onFileChange"
      />

      <div v-if="hasFiles" class="w-full h-full p-2">
        <TransitionGroup
          tag="div"
          name="file-list"
          class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-4"
        >
          <div
            v-for="(fileWrapper, idx) in fileWrappers"
            :key="fileWrapper.id"
            class="relative aspect-square bg-slate-100 dark:bg-slate-700/50 rounded-lg overflow-hidden group/item"
          >
            <img
              v-if="isImage(fileWrapper.file)"
              :src="fileWrapper.previewUrl"
              :alt="fileWrapper.file.name"
              class="w-full h-full object-cover"
            />
            <div v-else class="flex items-center justify-center w-full h-full">
              <svg class="w-12 h-12 text-slate-400 dark:text-slate-500" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" d="M19.5 14.25v-2.625a3.375 3.375 0 0 0-3.375-3.375h-1.5A1.125 1.125 0 0 1 13.5 7.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H8.25m-1.5 18.75h12.75c.621 0 1.125-.504 1.125-1.125V11.25a9 9 0 0 0-9-9h-3.375c-.621 0-1.125.504-1.125 1.125v17.25c0 .621.504 1.125 1.125 1.125Z" /></svg>
            </div>

            <div class="absolute inset-0 bg-black/50 flex flex-col justify-end text-white p-2 transition-opacity duration-300 opacity-0 group-hover/item:opacity-100">
              <p class="text-xs font-semibold truncate">{{ fileWrapper.file.name }}</p>
              <p class="text-[10px] text-slate-300">{{ humanSize(fileWrapper.file.size) }}</p>
            </div>
            
            <button
              type="button"
              class="absolute top-1 right-1 p-1 bg-white/70 dark:bg-slate-800/70 text-slate-800 dark:text-slate-100 rounded-full hover:bg-red-500 hover:text-white transition-all duration-200 opacity-0 group-hover/item:opacity-100"
              title="ลบไฟล์นี้"
              @click.prevent="removeFile(idx)"
            >
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="w-4 h-4"><path d="M6.28 5.22a.75.75 0 0 0-1.06 1.06L8.94 10l-3.72 3.72a.75.75 0 1 0 1.06 1.06L10 11.06l3.72 3.72a.75.75 0 1 0 1.06-1.06L11.06 10l3.72-3.72a.75.75 0 0 0-1.06-1.06L10 8.94 6.28 5.22Z" /></svg>
            </button>
          </div>
        </TransitionGroup>

        <div v-if="hasFiles" class="text-center mt-6">
            <button
              type="button"
              class="px-4 py-2 text-xs font-semibold text-red-600 dark:text-red-500 hover:underline"
              @click.prevent="removeAllFiles"
            >
              ลบไฟล์ทั้งหมด ({{ fileWrappers.length }} ไฟล์)
            </button>
        </div>
      </div>

      <div v-else class="flex flex-col items-center justify-center text-center">
        <div v-if="isDragging" class="z-10">
          <svg class="w-16 h-16 mb-4 text-sky-500 animate-bounce" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" d="M9 8.25H7.5a2.25 2.25 0 0 0-2.25 2.25v9a2.25 2.25 0 0 0 2.25 2.25h9a2.25 2.25 0 0 0 2.25-2.25v-9a2.25 2.25 0 0 0-2.25-2.25H15M9 12l3 3m0 0 3-3m-3 3V2.25" /></svg>
          <p class="text-lg font-semibold text-sky-700 dark:text-sky-300">วางไฟล์ที่นี่ได้เลย</p>
        </div>
        <div v-else>
          <svg class="w-16 h-16 mx-auto mb-4 text-slate-400 dark:text-slate-500 group-hover:text-sky-500 transition-colors" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.2" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" d="M12 16.5V9.75m0 0 3 3m-3-3-3 3M6.75 19.5a4.5 4.5 0 0 1-1.41-8.775 5.25 5.25 0 0 1 10.233-2.33 3 3 0 0 1 3.758 3.848A3.752 3.752 0 0 1 18 19.5H6.75Z" /></svg>
          <p class="mb-2 text-base font-semibold text-slate-700 dark:text-slate-200">
            <span class="text-sky-600 dark:text-sky-500 font-bold">คลิกเพื่ออัปโหลด</span>
            หรือลากไฟล์มาวาง
          </p>
          <p v-if="accept" class="text-xs text-slate-500 dark:text-slate-400">
            รองรับ: {{ accept.replaceAll(',', ', ') }}
          </p>
        </div>
      </div>
    </label>

    <div v-if="errorMessage" class="mt-2 text-sm text-red-600 dark:text-red-500" aria-live="polite">
      {{ errorMessage }}
    </div>
  </div>
</template>

<style>
/* Transition for file list */
.file-list-enter-active,
.file-list-leave-active {
  transition: all 0.5s ease;
}
.file-list-enter-from,
.file-list-leave-to {
  opacity: 0;
  transform: translateY(30px);
}
</style>

<script setup lang="ts">

// UPDATE: ทำให้ Interface เรียบง่ายขึ้น ไม่ต้องมี status/progress
interface FileWrapper {
  id: string;
  file: File;
  previewUrl: string;
}

/* ───────────────────────── Props & Emits ───────────────────────── */
const props = withDefaults(defineProps<{
  // UPDATE: รองรับ File[] และ File, แต่ภายในจะจัดการเป็น Array เสมอ
  modelValue: File | File[] | null
  id?: string
  accept?: string
  multiple?: boolean
  disabled?: boolean
  errorMessage?: string
}>(), {
  id: `file-upload-${Math.random().toString(36).substring(2, 9)}`,
  multiple: false,
})

const emit = defineEmits<{
  (e: 'update:modelValue', value: File | File[] | null): void
}>()

/* ───────────────────────── Refs & State ───────────────────────── */
const fileInput = ref<HTMLInputElement | null>(null)
const isDragging = ref(false)
const fileWrappers = ref<FileWrapper[]>([])

/* ───────────────────────── Sync with v-model ───────────────────────── */
// เมื่อ modelValue จากภายนอกเปลี่ยน (เช่น parent set ให้เป็น null) ให้ update state ภายใน
watch(() => props.modelValue, (newValue) => {
  if (!newValue || (Array.isArray(newValue) && newValue.length === 0)) {
    if (fileWrappers.value.length > 0) {
      // Clear files if modelValue becomes empty
      fileWrappers.value.forEach(fw => URL.revokeObjectURL(fw.previewUrl));
      fileWrappers.value = [];
    }
  } else {
    // Sync from parent to child if needed (e.g., pre-populating files)
    const newFiles = Array.isArray(newValue) ? newValue : [newValue];
    const currentFiles = fileWrappers.value.map(fw => fw.file);

    // Only update if there's a difference
    if (newFiles.some(f => !currentFiles.includes(f)) || newFiles.length !== currentFiles.length) {
       handleFiles(newFiles, true); // `true` to indicate it's a replacement
    }
  }
}, { deep: true });

// Initial population from modelValue
if(props.modelValue) {
    const initialFiles = Array.isArray(props.modelValue) ? props.modelValue : [props.modelValue];
    handleFiles(initialFiles, true);
}


/* ───────────────────────── Computed ───────────────────────── */
const hasFiles = computed(() => fileWrappers.value.length > 0)

/* ───────────────────────── Utils ───────────────────────── */
const humanSize = (size: number) => {
  if (size === 0) return '0 Bytes'
  const k = 1024
  const units = ['Bytes', 'KB', 'MB', 'GB', 'TB']
  const i = Math.floor(Math.log(size) / Math.log(k))
  return `${parseFloat((size / Math.pow(k, i)).toFixed(2))} ${units[i]}`
}

const isImage = (file: File) => file.type.startsWith('image/')

/* ───────────────────────── File Handling ───────────────────────── */
const acceptFile = (file: File): boolean => {
  if (!props.accept) return true
  const acceptedTypes = props.accept.split(',').map(t => t.trim())
  return acceptedTypes.some(type => {
    const regex = new RegExp(`^${type.replace(/\*/g, '.*')}$`, 'i')
    return regex.test(file.type) || regex.test(file.name)
  })
}

const handleFiles = (files: FileList | File[], replace = false) => {
  if (props.disabled) return

  const newValidFiles = Array.from(files).filter(acceptFile);
  if (newValidFiles.length === 0) return;

  const newWrappers: FileWrapper[] = newValidFiles.map(file => ({
    id: `${file.name}-${file.size}-${file.lastModified}-${Math.random()}`,
    file,
    previewUrl: URL.createObjectURL(file),
  }));

  if (props.multiple) {
    // ถ้า replace = true ให้แทนที่ของเก่าทั้งหมด
    if (replace) {
        fileWrappers.value.forEach(fw => URL.revokeObjectURL(fw.previewUrl)); // clear old blobs
        fileWrappers.value = newWrappers;
    } else {
        fileWrappers.value = [...fileWrappers.value, ...newWrappers];
    }
  } else {
    fileWrappers.value.forEach(fw => URL.revokeObjectURL(fw.previewUrl)); // clear old blobs
    fileWrappers.value = [newWrappers[0]];
  }

  updateAndEmitModelValue();
}


const onFileChange = (e: Event) => {
  const target = e.target as HTMLInputElement
  if (target.files) handleFiles(target.files)
  target.value = ''
}

const onDrop = (e: DragEvent) => {
  if (props.disabled) return
  isDragging.value = false
  if (e.dataTransfer?.files) handleFiles(e.dataTransfer.files)
}

const onDragOver = () => { if (!props.disabled) isDragging.value = true }
const onDragLeave = () => { isDragging.value = false }


/* ───────────────────────── Actions & Emitting ───────────────────────── */
const updateAndEmitModelValue = () => {
  const files = fileWrappers.value.map(fw => fw.file);
  
  if (files.length === 0) {
    emit('update:modelValue', null);
  } else {
    // ถ้า prop `multiple` เป็นจริง ส่ง Array, ถ้าไม่ ส่งแค่ไฟล์เดียว
    emit('update:modelValue', props.multiple ? files : files[0]);
  }
}

const removeFile = (index: number) => {
  const wrapper = fileWrappers.value[index];
  if (wrapper) {
    URL.revokeObjectURL(wrapper.previewUrl);
    fileWrappers.value.splice(index, 1);
    updateAndEmitModelValue();
  }
}

const removeAllFiles = () => {
  fileWrappers.value.forEach(fw => URL.revokeObjectURL(fw.previewUrl));
  fileWrappers.value = [];
  updateAndEmitModelValue();
}
</script>