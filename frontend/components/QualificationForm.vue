<script setup lang="ts">
import { computed } from 'vue'
import type { ProfessorQualification } from '~/types/ProfessorQualification';

// --- ส่วนที่แก้ไข ---

// 1. กำหนด props เพื่อรับ "modelValue" จาก v-model ของคอมโพเนนต์แม่
const props = defineProps<{
  modelValue: ProfessorQualification
}>()

// 2. กำหนด emits เพื่อส่ง event "update:modelValue" กลับไปหาคอมโพเนนต์แม่
const emit = defineEmits<{
  (e: 'update:modelValue', value: ProfessorQualification): void
}>()

// 3. สร้าง computed property เพื่อเชื่อม props และ emits เข้าด้วยกัน
//    - get(): อ่านค่าจาก props.modelValue ที่ส่งมาจากแม่
//    - set(newValue): เมื่อมีการเปลี่ยนแปลงในฟอร์ม จะ emit event พร้อมข้อมูลใหม่กลับไปหาแม่
const localQualification = computed({
  get: () => props.modelValue,
  set: (newValue) => {
    emit('update:modelValue', newValue)
  }
})

// --- ส่วนเดิม ---
const config = useRuntimeConfig()
const apiBase = config.public.apiBase
const { data: degrees } = await useFetch(`${apiBase}/degree`);

</script>

<template>
  <div class="grid lg:grid-cols-3 gap-5">
    <div>
      <Label for="qualifications_degree" class="text-base font-bold mb-2">วุฒิการศึกษา</Label>
      <Select v-model="localQualification.degreeId">
        <SelectTrigger id="academic-position" class="w-full">
          <SelectValue placeholder="กรุณาเลือกระดับการศึกษา" />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectLabel>เลือกระดับการศึกษา</SelectLabel>
            <SelectItem v-for="degree in degrees" :key="degree.id" :value="degree.id">
              {{ degree.nameTh }}
            </SelectItem>
          </SelectGroup>
        </SelectContent>
      </Select>
    </div>
    <div>
      <Label for="qualifications_university" class="text-base font-bold mb-2">มหาวิทยาลัย</Label>
      <Input 
        id="qualifications_university"
        type="text" 
        placeholder="ชื่อสถาบันการศึกษา" 
        v-model="localQualification.university" 
      />
    </div>
    <div>
      <Label for="qualifications_major" class="text-base font-bold mb-2">สาขาวิชา</Label>
      <Input 
        id="qualifications_major"
        type="text" 
        placeholder="ชื่อสาขาวิชา" 
        v-model="localQualification.major" 
      />
    </div>
  </div>
</template>