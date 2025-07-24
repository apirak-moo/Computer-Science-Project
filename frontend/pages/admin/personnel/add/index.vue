<script setup lang="ts">
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import { Button } from '@/components/ui/button' // เพิ่ม Button import
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card' // เพิ่ม Card components
import { Input } from '@/components/ui/input' // เพิ่ม Input component
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogDescription, DialogClose, DialogFooter, DialogTrigger } from '@/components/ui/dialog' // เพิ่ม Dialog components

import Label from '~/components/ui/label/Label.vue';
import QualificationForm from '~/components/QualificationForm.vue';

import type { ProfessorRequest } from '~/types/ProfessorRequest';

definePageMeta({
  middleware: 'auth',
  roles: ['ผู้ดูแล']
})

const token = useCookie('token')

const config = useRuntimeConfig()
const apiBase = config.public.apiBase
const { data: titles } = await useFetch(`${apiBase}/professor_title`);
const { data: positions } = await useFetch(`${apiBase}/position`);

const form = ref<ProfessorRequest>({
  email: '',
  password: '', // เพิ่ม password ใน form state
  positions: [],
  qualifications: [], // ข้อมูลวุฒิจะถูกเก็บในนี้
  profile: {
    titleId: 0,
    name: '',
    phone: '',
    git: '',
    major: '',
  },
})

const imageFile = ref<File | null>(null); // สำหรับเก็บไฟล์รูปภาพที่ผู้ใช้เลือก

const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    imageFile.value = file;
  }
};

// ฟังก์ชันสำหรับเพิ่มวุฒิการศึกษาใหม่
const addQualification = () => {
  form.value.qualifications.push({
    degree: null, // หรือค่าเริ่มต้นอื่นๆ
    major: '',
    university: '',
  });
}

// ฟังก์ชันสำหรับลบวุฒิการศึกษา (ตาม index)
const removeQualification = (index: number) => {
  form.value.qualifications.splice(index, 1);
}

const isLoading = ref(false);
const errorMessage = ref('');
const successMessage = ref('');

const handleSubmit = async () => {

  isLoading.value = true;
  errorMessage.value = '';
  successMessage.value = '';

  // 1. สร้าง FormData object
  const body = new FormData();

  // 2. แปลง formData ทั้งหมดเป็น JSON string และสร้างเป็น Blob (Binary Large Object)
  // จากนั้น append เข้า FormData ในชื่อ part ว่า 'request'
  // Backend Spring Boot ที่ใช้ @RequestPart("request") ProfessorRequest request จะรับและแปลงกลับเป็น Object ได้โดยอัตโนมัติ
  body.append('request', new Blob([JSON.stringify(form.value)], {
    type: 'application/json'
  }));

  // 3. แนบไฟล์รูปภาพเข้าไปใน part ที่ชื่อว่า 'image' (ถ้าผู้ใช้ได้เลือกไฟล์)
  if (imageFile.value) {
    body.append('image', imageFile.value);
  }

  // for (const pair of body.entries()) {
  //   console.log(`${pair[0]}:`, pair[1]);
  // }


  try {
    // 4. ส่ง Request ด้วย $fetch ไปยัง Backend
    // **สำคัญ:** อย่าลืมแก้ URL ของ Backend ให้ถูกต้อง
    const response = await $fetch(`${apiBase}/auth/register`, {
      method: 'POST',
      body: body,
      headers: {
        Authorization: `Bearer ${token.value}`
      },
    });

    successMessage.value = `Registration successful! Professor ID: ${response.id}`;
    console.log('Server Response:', response);
    // สามารถเคลียร์ฟอร์ม หรือ redirect ไปหน้าอื่นได้

    await navigateTo('/admin/personnel'); // เปลี่ยนเส้นทางไปยังหน้าบุคลากรหลังจากลงทะเบียนสำเร็จ

  } catch (error) {
    console.error('Registration failed:', error);
    errorMessage.value = error.data?.message || 'An unexpected error occurred.';
  } finally {
    isLoading.value = false;
  }

};

</script>

<template>
  <div class="container mx-auto p-4">
    <Card class="mb-4">
      <CardHeader>
        <CardTitle>เพิ่มบุคลากร</CardTitle>
        <CardDescription>กรุณากรอกข้อมูลบุคลากรใหม่</CardDescription>
      </CardHeader>
    </Card>
    <Card class="mb-4">
      <CardContent class="p-6">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">

          <div class="flex flex-col gap-2">
            <Label for="academic-position" class="text-base font-bold">ตำแหน่งทางวิชาการ</Label>
            <Select v-model="form.profile.titleId">
              <SelectTrigger id="academic-position" class="w-full">
                <SelectValue placeholder="กรุณาเลือกตำแหน่ง" />
              </SelectTrigger>
              <SelectContent>
                <SelectGroup>
                  <SelectLabel>เลือกตำแหน่ง</SelectLabel>
                  <SelectItem v-for="title in titles" :key="title.id" :value="title.id">
                    {{ title.name }}
                  </SelectItem>
                </SelectGroup>
              </SelectContent>
            </Select>
          </div>
          <div class="flex flex-col gap-2">
            <Label for="full-name" class="text-base font-bold">ชื่อ-นามสกุล</Label>
            <Input v-model="form.profile.name" id="full-name" type="text" placeholder="กรุณากรอกชื่อ-นามสกุล" />
          </div>
          <div class="flex flex-col gap-2">
            <Label for="profile-image" class="text-base font-bold">รูปโปรไฟล์</Label>
            <Input type="file" id="profile-image" @change="handleFileChange" accept="image/*" />
          </div>
          <div class="flex flex-col gap-2">
            <Label for="phone" class="text-base font-bold">เบอร์ติดต่อ</Label>
            <Input v-model="form.profile.phone" id="phone" type="text" placeholder="กรุณากรอกเบอร์ติดต่อ" />
          </div>
          <div class="flex flex-col gap-2">
            <Label for="git" class="text-base font-bold">Git</Label>
            <Input v-model="form.profile.git" id="git" type="text" placeholder="URL Git หรือ Github ..." />
          </div>
          <div class="flex flex-col gap-2">
            <Label for="major" class="text-base font-bold">สังกัดสาขาวิชา</Label>
            <Input v-model="form.profile.major" id="major" type="text" placeholder="กรุณากรอกสาขาวิชา" />
          </div>
          <div class="flex flex-col gap-2">
            <Label for="email" class="text-base font-bold">อีเมลล์</Label>
            <Input v-model="form.email" type="email" placeholder="กรุณากรอกอีเมลล์" />
          </div>
          <div class="flex flex-col gap-2">
            <Label for="password" class="text-base font-bold">รหัสผ่าน</Label>
            <Input v-model="form.password" type="password" placeholder="กรุณากรอกรหัสผ่าน" />
          </div>

          <hr class="md:col-span-3 border-gray-300 my-4" />

          <div class="md:col-span-3 flex flex-col gap-4 pt-4">
            <Label class="text-base font-bold">ตำแหน่งงานภายในคณะ</Label>
            <div class="flex flex-row flex-wrap gap-x-6 gap-y-4">
              <div v-for="position in positions" :key="position.id" class="flex items-center gap-2">
                <input v-model="form.positions" :id="'pos-' + position.id" type="checkbox"
                  class="h-5 w-5 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500" :value="position" />
                <Label :for="'pos-' + position.id" class="font-normal">
                  {{ position.name }}
                </Label>
              </div>
            </div>
          </div>

          <hr class="md:col-span-3 border-gray-300 my-4" />

          <div class="flex flex-col gap-4 md:col-span-3">
            <div class="flex items-center justify-between gap-2">
              <Label class="text-base font-bold">คุณวุฒิการศึกษา</Label>
              <Button @click="addQualification" type="button" variant="outline">เพิ่มวุฒิ</Button>
            </div>

            <div v-if="form.qualifications.length === 0"
              class="text-center text-gray-500 p-4 border-2 border-dashed rounded-lg">
              คลิก 'เพิ่มวุฒิ' เพื่อเริ่มกรอกข้อมูล
            </div>

            <div v-for="(qualification, index) in form.qualifications" :key="index"
              class="border p-4 rounded-lg relative space-y-4 bg-gray-50">
              <Button @click="removeQualification(index)" type="button" variant="destructive" size="sm"
                class="absolute top-2 right-2">
                ลบ
              </Button>

              <QualificationForm v-model="form.qualifications[index]" />
            </div>
          </div>
        </div>
      </CardContent>
    </Card>

    <Dialog>
      <DialogTrigger as-child>
        <Button class="w-full cursor-pointer">บันทึกข้อมูล</Button>
      </DialogTrigger>
      <DialogContent class="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>ยืนยันการเพิ่มบุคลากร</DialogTitle>
          <DialogDescription>
            คุณต้องการบันทึกข้อมูลบุคลากรใหม่ใช่หรือไม่?
          </DialogDescription>
        </DialogHeader>
        <DialogFooter>
          <DialogClose as-child>
            <Button @click="handleSubmit" type="button">ยืนยัน</Button>
          </DialogClose>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  </div>
</template>