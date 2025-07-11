<script setup lang="ts">

import {
    Card,
    CardDescription,
    CardHeader,
    CardContent,
    CardTitle,
} from '@/components/ui/card'

import { Input } from '@/components/ui/input'

import { Button } from '@/components/ui/button'

import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
} from '@/components/ui/dialog'
import type { PaginatedResponse } from '~/types/PaginatedResponse'
import type { Major } from '~/types/Major'

definePageMeta({
    middleware: ['auth'],
    roles: ['Administrator', 'Head of Department']
})

const token = useCookie('token')

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const { data: majors, refresh } = await useFetch<PaginatedResponse<Major>>(`${apiBase}/major`)

// ---- START: ส่วนที่แก้ไขและเพิ่มเติม ----

// 1. ลบ isDialogOpen ออก แล้วสร้าง state ใหม่สำหรับเก็บสาขาที่ถูกเลือก
const selectedMajor = ref<Major | null>(null)

// 2. สร้าง computed property เพื่อควบคุมการเปิด/ปิด Dialog โดยเช็คจาก selectedMajor
const isConfirmDialogOpen = computed({
    get: () => selectedMajor.value !== null,
    set: (value) => {
        // ถ้า value เป็น false (สั่งปิด dialog) ให้เคลียร์ค่า selectedMajor
        if (!value) {
            selectedMajor.value = null
        }
    },
})

// 3. สร้างฟังก์ชันสำหรับรับค่า major มาเก็บไว้ใน state เพื่อเปิด Dialog
const openConfirmationDialog = (major: Major) => {
    selectedMajor.value = major
}

// 4. แก้ไขฟังก์ชัน useUpdateMajor ให้ใช้ข้อมูลจาก selectedMajor
const useUpdateMajor = async () => {
    // Guard clause: ป้องกันการทำงานถ้าไม่มี major ถูกเลือก
    if (!selectedMajor.value) return

    const status = !selectedMajor.value.status
    await $fetch(`${apiBase}/major/${selectedMajor.value.id}`, {
        method: 'PATCH',
        query: { status },
        headers:{
            Authorization: `Bearer ${token.value}`
        },
    })

    refresh()
    selectedMajor.value = null // ปิด Dialog หลังทำงานเสร็จ
}

// ---- END: ส่วนที่แก้ไขและเพิ่มเติม ----

const search = ref('')

const filteredMajors = computed(() => {
    if (!majors.value?.content) return []
    const keyword = search.value.toLowerCase().trim()
    return majors.value.content.filter(m =>
        m.nameTh.toLowerCase().includes(keyword) ||
        m.nameEn.toLowerCase().includes(keyword)
    )
})


function toLanguage(text: string): string {
    // ... (โค้ดเดิม ไม่มีการเปลี่ยนแปลง)
    switch (text) {
        case 'THAI':
            return 'หลักสูตรภาษาไทย'
        case 'ENGLISH':
            return 'หลักสูตรนานาชาติ'
        case 'THAI_ENGLISH':
            return 'หลักสูตรสองภาษา'
        default:
            return text
    }
}

</script>

<template>
    <div class="container mx-auto p-4">
        <!-- Card 1: สูงตามเนื้อหา -->
        <Card class="mb-4">
            <CardHeader>
                <CardTitle>ระบบจัดการสาขาวิชา</CardTitle>
                <CardDescription>สาขาวิชาของคณะเทคโนโลยีสารสนเทศ</CardDescription>
            </CardHeader>
        </Card>

        <!-- Card 1: สูงตามเนื้อหา -->
        <Card class="mb-4">
            <CardContent class="grid grid-cols-6 gap-5">
                <Input class="col-span-4 md:col-span-5" type="text" v-model="search" placeholder="ค้นหาสาขาวิชา" />
                <NuxtLink to="/admin/major/add">
                    <Button class="col-span-2 w-full md:col-span-1 bg-sky-700 hover:bg-sky-800 cursor-pointer">
                        เพิ่มสาขาวิชา
                    </Button>
                </NuxtLink>
            </CardContent>

            <CardContent>
                <div v-if="majors?.content.length"
                    class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                    <div v-for="major in filteredMajors" :key="major.id"
                        :class="major.status ? '' : 'grayscale hover:filter-none'"
                        class="bg-white rounded-2xl shadow-md hover:shadow-sky-300/60 hover:ring-2 hover:ring-sky-300 transition-all duration-300 ease-in-out overflow-hidden group flex flex-col h-full justify-between">
                        <NuxtImg :src="`${apiBase}/images/${major.image}`" width="600" height="400" format="webp"
                            quality="80" :alt="major.nameTh" placeholder loading="lazy"
                            class="w-full aspect-[6/3] object-cover transition-transform duration-300 ease-in-out group-hover:scale-105" />

                        <div class="p-4 flex flex-col justify-between flex-1 gap-3">
                            <!-- ชื่อสาขา -->
                            <NuxtLink :to="`/admin/major/${major.id}`" class="flex flex-col gap-0.5">
                                <h3
                                    class="text-base sm:text-lg font-semibold text-sky-700 group-hover:underline line-clamp-1">
                                    {{ major.nameTh }}
                                </h3>
                                <h3
                                    class="text-sm sm:text-md font-semibold text-sky-500 group-hover:underline line-clamp-1">
                                    {{ major.nameEn }}
                                </h3>
                            </NuxtLink>

                            <!-- Badges -->
                            <div class="flex flex-wrap gap-2">
                                <Badge
                                    class="bg-sky-100 text-sky-800 dark:bg-sky-800 dark:text-sky-100 text-xs sm:text-sm px-3 py-1 rounded-full">
                                    {{ major.program.degree.nameTh }}
                                </Badge>
                                <Badge
                                    class="bg-gray-100 text-gray-800 dark:bg-gray-800 dark:text-gray-100 text-xs sm:text-sm px-3 py-1 rounded-full">
                                    {{ toLanguage(major.language) }}
                                </Badge>
                                <Button @click="openConfirmationDialog(major)"
                                    class="rounded-full cursor-pointer transition duration-300 ease-in-out"
                                    :class="major.status ? 'bg-red-200 text-red-700 hover:bg-red-300' : 'bg-green-200 text-green-700 hover:bg-green-300'">
                                    {{ major.status ? 'ปิดสาขา' : 'เปิดสาขา' }}
                                </Button>
                            </div>
                        </div>
                    </div>

                </div>
                <div v-else
                    class="flex flex-col items-center justify-center min-h-[80vh] bg-white dark:bg-gray-950 py-16 px-4 sm:px-6 lg:px-8 transition-colors duration-300">
                    <div class="max-w-md w-full text-center space-y-6">
                        <div class="flex justify-center mb-4">
                            <svg class="h-20 w-20 text-gray-500 dark:text-gray-400 opacity-80" fill="none"
                                viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                <path stroke-linecap="round" stroke-linejoin="round"
                                    d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                        </div>

                        <h2 class="text-3xl font-extrabold text-gray-900 dark:text-white leading-tight">
                            หลักสูตรนี้ยังไม่เปิดให้บริการในขณะนี้
                        </h2>

                        <p class="mt-3 text-lg text-gray-600 dark:text-gray-400">
                            ขออภัยในความไม่สะดวก หลักสูตรที่คุณกำลังค้นหาอาจอยู่ในระหว่างการปรับปรุง
                            หรือยังไม่พร้อมให้บริการ
                        </p>

                        <div class="mt-8">
                            <NuxtLink to="/courses"
                                class="inline-flex items-center px-8 py-3 border border-transparent text-lg font-medium rounded-full shadow-lg text-white bg-sky-700 hover:bg-sky-800 focus:outline-none focus:ring-4 focus:ring-sky-600 focus:ring-opacity-50 transition-all duration-300 ease-in-out transform hover:scale-105">
                                <svg class="w-6 h-6 mr-2 -ml-1" fill="none" viewBox="0 0 24 24" stroke="currentColor"
                                    stroke-width="2">
                                    <path stroke-linecap="round" stroke-linejoin="round"
                                        d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M18 12a2 2 0 012 2V7a2 2 0 00-2-2h-2m-3 7a3 3 0 11-6 0 3 3 0 016 0z" />
                                </svg>
                                ดูหลักสูตรทั้งหมด
                            </NuxtLink>
                        </div>
                    </div>
                </div>
            </CardContent>

        </Card>

        <Dialog v-if="selectedMajor" v-model:open="isConfirmDialogOpen">
            <DialogContent class="sm:max-w-[425px] bg-white">
                <DialogHeader>
                    <DialogTitle>คุณต้องการ{{ selectedMajor.status ? 'ปิด' : 'เปิด' }}สาขาวิชานี้ใช่ไหม?</DialogTitle>
                    <DialogDescription>
                        กดยืนยันเพื่อ{{ selectedMajor.status ? 'ปิด' : 'เปิด' }}สาขาวิชา "{{ selectedMajor.nameTh }}"
                    </DialogDescription>
                </DialogHeader>
                <DialogFooter>
                    <Button variant="outline" @click="isConfirmDialogOpen = false">ยกเลิก</Button>
                    <Button type="submit" @click="useUpdateMajor">ยืนยัน</Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>

    </div>
</template>
