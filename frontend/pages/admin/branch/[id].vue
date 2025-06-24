<script setup lang="ts">

import {
    Tabs,
    TabsContent,
    TabsList,
    TabsTrigger,
} from '@/components/ui/tabs'

import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from '@/components/ui/table'

import { Separator } from '@/components/ui/separator'

import { Label } from '@/components/ui/label'
import { Textarea } from '@/components/ui/textarea'
import {
    NumberField,
    NumberFieldContent,
    NumberFieldDecrement,
    NumberFieldIncrement,
    NumberFieldInput,
} from '@/components/ui/number-field'

import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from '@/components/ui/select'

import { Input } from '@/components/ui/input'

import FileUploader from '~/components/FileUploader.vue'

import { LucideGraduationCap, LucideBookOpenCheck, LucideGlobe, LucideWallet, Save, Trash2, LucideFileText, LucideFileUser, Upload, CircleX, Download, CalendarIcon, BriefcaseBusiness, Plus } from 'lucide-vue-next';

import type { Branch } from '~/types/Branch'
import type { Major } from '~/types/Major'
import type { PaginatedResponse } from '~/types/PaginatedResponse'

const route = useRoute()
const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const { data: branch, refresh, error } = await useFetch<Branch>(`${apiBase}/branch/${route.params.id}`)

const { data: majors } = await useFetch<PaginatedResponse<Major>>(`${apiBase}/major`)

if (error.value) {
    console.error(error.value)
}

console.log(branch.value)

const languages = [
    {
        value: 'THAI',
        text: 'หลักสูตรภาษาไทย'
    },
    {
        value: 'ENGLISH',
        text: 'หลักสูตรนานาชาติ'
    },
    {
        value: 'THAI_ENGLISH',
        text: 'หลักสูตรสองภาษา'
    }
]

useHead({
    title: branch.value?.nameTh || 'ข้อมูลสาขาวิชา',
    htmlAttrs: {
        lang: 'th'
    },
    meta: [
        {
            name: 'description',
            content: branch.value?.description?.slice(0, 160) || 'รายละเอียดหลักสูตร สาขาวิชา คณะ และอาชีพในอนาคต',
        },
        {
            name: 'keywords',
            content: `${branch.value?.nameTh}, ${branch.value?.nameEn}, มหาวิทยาลัย, สาขาวิชา, คณะ, เรียนต่อ, ค่าเทอม, อาชีพ`,
        },
        {
            property: 'og:title',
            content: branch.value?.nameTh || 'ข้อมูลสาขาวิชา',
        },
        {
            property: 'og:description',
            content: branch.value?.description?.slice(0, 160) || 'รายละเอียดหลักสูตร สาขาวิชา คณะ และอาชีพในอนาคต',
        },
        {
            property: 'og:image',
            content: branch.value?.image ? `${apiBase}/images/${branch.value.image}` : '/default-og-image.jpg',
        },
        {
            property: 'og:url',
            content: `${apiBase}/branch/${route.params.id}`,
        },
        {
            name: 'twitter:card',
            content: 'summary_large_image',
        },
    ],
    link: [
        {
            rel: 'canonical',
            href: `${apiBase}/branch/${route.params.id}`,
        },
    ],
})

const careersComputed = computed<string[]>({
    get() {
        return (branch.value?.careers ?? '')
            .split(',')
            .map(c => c.trim())
            .filter(Boolean)
    },
    set(newArray: string[]) {
        if (branch.value) {
            branch.value.careers = newArray.join(',')
        }
    }
})

function addCareer() {
    if (newCareer.value && newCareer.value.trim() !== '') {
        // เพิ่มอาชีพใหม่และ sync กลับ
        careersComputed.value = [...careersComputed.value, newCareer.value.trim()]
        newCareer.value = null
    }
}

function removeCareer(index: number) {
    const updated = careersComputed.value.filter((_, i) => i !== index)
    careersComputed.value = updated
}

const newCareer = ref<string | null>(null)

const ImageFile = ref<File | null>(null)

const CoursesFile = ref<File | null>(null)

function handleFileCoursesChange(event: Event) {
    const target = event.target as HTMLInputElement
    const file = target.files?.[0]
    if (file) {
        CoursesFile.value = file
    } else {
        CoursesFile.value = null
    }
}

async function submitForm() {
    const formData = new FormData()

    formData.append('majorId', String(branch.value?.major.id ?? ''))
    formData.append('nameTh', branch.value?.nameTh ?? '')
    formData.append('nameEn', branch.value?.nameEn ?? '')
    formData.append('tuitionFees', String(branch.value?.tuitionFees ?? ''))
    formData.append('language', branch.value?.language ?? '')
    formData.append('description', branch.value?.description ?? '')
    formData.append('careers', branch.value?.careers ?? '')
    formData.append('status', false)

    // ✅ ตรวจสอบก่อนว่าเป็น File จริง
    if (CoursesFile.value instanceof File) {
        formData.append('courseFile', CoursesFile.value)
    }

    if (ImageFile.value instanceof File) {
        formData.append('image', ImageFile.value)
    }

    // ✅ ตรวจสอบค่าที่แนบ
    for (const [key, value] of formData.entries()) {
        console.log(`${key}:`, value)
    }

    // ✅ ส่งไปยัง API
    await $fetch(`${apiBase}/branch/${route.params.id}`, {
        method: 'PUT',
        body: formData,
    })

    refresh()

    ImageFile.value = null
    CoursesFile.value = null

}

async function deleteForm() {
    // ✅ ส่งไปยัง API
    await $fetch(`${apiBase}/branch/${route.params.id}`, {
        method: 'DELETE',
    })

    await navigateTo('/admin/branch')
}

</script>

<template>
    <div class="container mx-auto p-4">

        <Card class="mb-4">
            <CardHeader class="flex items-center justify-between">
                <div>
                    <CardTitle>ระบบจัดการแขนงวิชา</CardTitle>
                    <CardDescription>รายการสาขาวิชาทั้งหมดของคณะเทคโนโลยีสารสนเทศ</CardDescription>
                </div>
                <div class="flex flex-col md:flex-row gap-3">

                    <Dialog>
                        <DialogTrigger as-child>
                            <!-- ขวา: ปุ่มเพิ่มข้อมูล -->
                            <Button
                                class="bg-sky-700 cursor-pointer hover:bg-sky-800 transition duration-300 ease-in-out">
                                <Save type="submit" />
                            </Button>
                        </DialogTrigger>
                        <DialogContent class="sm:max-w-[425px]">
                            <DialogHeader>
                                <DialogTitle>เปลี่ยนแปลงข้อมูล</DialogTitle>
                                <DialogDescription>
                                    คุณต้องเปลี่ยนแปลงข้อมูลสาขาวิชา {{ branch?.nameTh }} หรือไม่
                                </DialogDescription>
                            </DialogHeader>
                            <DialogClose as-child>
                                <DialogFooter>
                                    <Button @click="submitForm" type="button">ยืนยัน</Button>
                                </DialogFooter>
                            </DialogClose>
                        </DialogContent>
                    </Dialog>

                    <Dialog>
                        <DialogTrigger as-child>
                            <!-- ขวา: ปุ่มเพิ่มข้อมูล -->
                            <Button
                                class="bg-red-700 cursor-pointer hover:bg-red-800 transition duration-300 ease-in-out">
                                <Trash2 type="submit" class="" />
                            </Button>
                        </DialogTrigger>
                        <DialogContent class="sm:max-w-[425px]">
                            <DialogHeader>
                                <DialogTitle>ลบสาขาวิชา</DialogTitle>
                                <DialogDescription>
                                    คุณต้องการลบสาขาวิชา {{ branch?.nameTh }} หรือไม่
                                </DialogDescription>
                            </DialogHeader>
                            <DialogClose as-child>
                                <DialogFooter>
                                    <Button @click="deleteForm" type="button">ยืนยัน</Button>
                                </DialogFooter>
                            </DialogClose>
                        </DialogContent>
                    </Dialog>

                </div>
            </CardHeader>
        </Card>

        <div class="relative w-full aspect-[16/6] rounded-2xl shadow-lg overflow-hidden text-white">
            <NuxtImg v-if="branch?.image" :src="`${apiBase}/images/${branch?.image}`" width="1920" height="1080"
                format="webp" sizes="(max-width: 1080px) 100vw, 1080px" alt="`ภาพปกสาขาวิชา ${branch?.nameTh}`" priority
                class="object-cover" />
            <div v-else class="w-full h-full bg-sky-800 flex items-center justify-center"
                aria-label="ไม่มีภาพปกสำหรับสาขาวิชานี้">
                <span class="sr-only">ไม่มีภาพปกสำหรับสาขาวิชานี้</span>
            </div>

            <div class="absolute inset-0 bg-gradient-to-t from-black/70 via-black/40 to-transparent"></div>

            <div class="absolute bottom-0 left-0 p-6 md:p-8 space-y-4">
                <div class="space-y-1">
                    <h1 class="text-sm sm:text-xl md:text-2xl lg:text-4xl font-bold text-balance">
                        {{ branch?.nameTh }}
                    </h1>
                    <p class="text-xs sm:text-xl text-gray-200 italic">{{ branch?.nameEn }}</p>
                </div>
            </div>

        </div>

        <div class="grid grid-cols-1 lg:grid-cols-3 mt-8 gap-8">

            <div class="lg:col-span-1 space-y-6">
                <Card>
                    <CardHeader>
                        <CardTitle class="text-xl font-semibold text-gray-900 dark:text-white">ชื่อสาขาวิชา</CardTitle>
                    </CardHeader>
                    <CardContent class="flex flex-col gap-3">
                        <Label>TH</Label>
                        <Input type="text" v-model="branch.nameTh" placeholder="ชื่อสาขาวิชาภาษาไทย" />
                        <Label>EN</Label>
                        <Input type="text" v-model="branch.nameEn" placeholder="ชื่อสาขาวิชาภาษาอังกฤษ" />
                    </CardContent>
                </Card>
            </div>

            <div class="lg:col-span-2 space-y-6">
                <Card>
                    <CardHeader>
                        <CardTitle class="text-xl font-semibold text-gray-900 dark:text-white">รูปภาพปกสาขาวิชา 1 รูป
                        </CardTitle>
                    </CardHeader>
                    <CardContent class="flex flex-col gap-3">
                        <ClientOnly>
                           <FileUploader v-model="ImageFile" /> 
                        </ClientOnly>
                    </CardContent>
                </Card>
            </div>

            <div class="lg:col-span-1 space-y-6">
                <div
                    class="bg-white rounded-2xl shadow-md border border-gray-100 p-6 dark:bg-gray-950 dark:border-gray-800">
                    <h2 class="text-xl font-semibold text-gray-900 dark:text-white mb-4">
                        ข้อมูลหลักสูตร
                    </h2>
                    <ul class="space-y-4">
                        <li class="flex items-start gap-4">
                            <div
                                class="flex-shrink-0 w-8 h-8 flex items-center justify-center rounded-lg bg-sky-100 dark:bg-sky-900/50">
                                <LucideGraduationCap class="w-5 h-5 text-sky-600 dark:text-sky-400" />
                            </div>
                            <div>
                                <div class="text-sm text-muted-foreground">วุฒิการศึกษา</div>
                                <div class="font-semibold text-gray-900 dark:text-white">
                                    {{ majors?.content.find( m => m.id === branch?.major.id )?.program.degree.nameTh || '' }}
                                </div>
                            </div>
                        </li>
                        <li class="flex items-start gap-4">
                            <div
                                class="flex-shrink-0 w-8 h-8 flex items-center justify-center rounded-lg bg-emerald-100 dark:bg-emerald-900/50">
                                <LucideBookOpenCheck class="w-5 h-5 text-emerald-600 dark:text-emerald-400" />
                            </div>
                            <div>
                                <div class="text-sm text-muted-foreground">หลักสูตร</div>
                                <div class="font-semibold text-gray-900 dark:text-white">
                                    {{ majors?.content.find( m => m.id === branch?.major.id )?.program.nameTh || '' }}
                                </div>
                            </div>
                        </li>
                        <li class="flex items-start gap-4">
                            <div
                                class="flex-shrink-0 w-8 h-8 flex items-center justify-center rounded-lg bg-emerald-100 dark:bg-emerald-900/50">
                                <LucideBookOpenCheck class="w-5 h-5 text-emerald-600 dark:text-emerald-400" />
                            </div>
                            <div>
                                <div class="text-sm text-muted-foreground">สาขาวิชา</div>
                                <Select v-model="branch.major.id">
                                    <SelectTrigger>
                                        <SelectValue placeholder="Select a language" />
                                    </SelectTrigger>
                                    <SelectContent>
                                        <SelectGroup>
                                            <SelectItem v-for="(major, index) in majors?.content" :key="index"
                                                :value="major.id">
                                                {{ major.nameTh }}
                                            </SelectItem>
                                        </SelectGroup>
                                    </SelectContent>
                                </Select>
                            </div>
                        </li>
                        <li class="flex items-start gap-4">
                            <div
                                class="flex-shrink-0 w-8 h-8 flex items-center justify-center rounded-lg bg-indigo-100 dark:bg-indigo-900/50">
                                <LucideGlobe class="w-5 h-5 text-indigo-600 dark:text-indigo-400" />
                            </div>
                            <div>
                                <div class="text-sm text-muted-foreground">ภาษา</div>
                                <Select v-model:model-value="branch.language">
                                    <SelectTrigger>
                                        <SelectValue placeholder="Select a language" />
                                    </SelectTrigger>
                                    <SelectContent>
                                        <SelectGroup>
                                            <SelectItem v-for="(language, index) in languages" :key="index"
                                                :value="language.value">
                                                {{ language.text }}
                                            </SelectItem>
                                        </SelectGroup>
                                    </SelectContent>
                                </Select>
                            </div>
                        </li>
                        <li class="flex items-start gap-4">
                            <div
                                class="flex-shrink-0 w-8 h-8 flex items-center justify-center rounded-lg bg-amber-100 dark:bg-amber-900/50">
                                <LucideWallet class="w-5 h-5 text-amber-600 dark:text-amber-400" />
                            </div>
                            <div>
                                <div class="text-sm text-muted-foreground">ค่าธรรมเนียมการศึกษา/ภาคการศึกษา</div>
                                <NumberField v-model="branch.tuitionFees" id="number" :default-value="0"
                                    :format-options="{
                                        signDisplay: 'exceptZero',
                                        minimumFractionDigits: 1,
                                    }">
                                    <NumberFieldContent>
                                        <NumberFieldDecrement />
                                        <NumberFieldInput />
                                        <NumberFieldIncrement />
                                    </NumberFieldContent>
                                </NumberField>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="lg:col-span-2 space-y-6">
                <div
                    class="bg-white rounded-2xl shadow-md border border-gray-100 p-6 space-y-6 dark:bg-gray-950 dark:border-gray-800">
                    <div>
                        <h3 class="text-xl font-semibold text-gray-900 dark:text-white">เกี่ยวกับหลักสูตร</h3>

                        <Textarea v-model.lazy="branch.description" placeholder="Type your message here." />

                    </div>

                    <div class="border-t border-gray-200 dark:border-gray-700 pt-6 flex flex-col sm:flex-row gap-4">
                        <a href="https://reg.pbru.ac.th/registrar/apphome.asp" target="_blank" rel="noopener noreferrer"
                            class="inline-flex items-center justify-center gap-2 rounded-md bg-sky-800 hover:bg-sky-900 text-white text-sm font-medium px-6 h-12 w-full sm:w-auto min-w-[48px] min-h-[48px]">
                            <LucideFileUser class="w-4 h-4 mr-2" />
                            ระเบียบการรับสมัคร
                        </a>
                        <a href="https://acad.pbru.ac.th/acad58/index.php/academic-job/pbru-course/faculty-of-information-technology"
                            target="_blank" rel="noopener noreferrer"
                            class="inline-flex items-center justify-center gap-2 rounded-md border bg-background hover:bg-accent hover:text-accent-foreground dark:bg-input/30 dark:border-input dark:hover:bg-input/50 text-gray-600 text-sm font-medium px-6 h-12 w-full sm:w-auto min-w-[48px] min-h-[48px]">
                            <LucideFileText class="w-4 h-4 mr-2" />
                            เล่มหลักสูตร
                        </a>
                    </div>

                </div>
            </div>

            <div class="lg:col-span-3">
                <div
                    class="bg-white rounded-2xl shadow-md border border-gray-100 p-6 sm:p-8 dark:bg-gray-950 dark:border-gray-800">

                    <div class="flex items-center justify-between mb-6 w-full">
                        <!-- ซ้าย: ไอคอน + ข้อความ -->
                        <div class="flex items-center gap-4">
                            <div
                                class="flex-shrink-0 w-12 h-12 flex items-center justify-center rounded-xl bg-gradient-to-br from-sky-500 to-indigo-600 text-white shadow-lg">
                                <BriefcaseBusiness class="w-7 h-7" />
                            </div>
                            <div>
                                <h3 class="text-2xl font-bold text-gray-900 dark:text-white">อาชีพหลังจบการศึกษา</h3>
                                <p class="text-gray-500 dark:text-gray-400">เส้นทางสู่อาชีพในฝันของคุณ</p>
                            </div>
                        </div>

                        <Dialog>
                            <DialogTrigger as-child>
                                <!-- ขวา: ปุ่มเพิ่มข้อมูล -->
                                <Button
                                    class="inline-flex items-center px-4 py-2 text-sm font-medium cursor-pointer text-white bg-blue-600 rounded-lg hover:bg-blue-700 transition">
                                    <Plus />
                                </Button>
                            </DialogTrigger>
                            <DialogContent class="sm:max-w-[425px]">
                                <DialogHeader>
                                    <DialogTitle>เพิ่มอาชีพ</DialogTitle>
                                    <DialogDescription>
                                        เป็นแค่การเพิ่มเท่านั้น ยังไม่ลงฐานข้อมูลจริง
                                    </DialogDescription>
                                </DialogHeader>
                                <div class="grid gap-4 py-4">
                                    <div class="grid grid-cols-4 items-center gap-4">
                                        <Label for="name" class="text-right">
                                            ชื่ออาชีพ
                                        </Label>
                                        <Input id="name" v-model="newCareer" class="col-span-3" />
                                    </div>
                                </div>
                                <DialogClose as-child>
                                    <DialogFooter>
                                        <Button type="submit" @click="addCareer">ยืนยัน</Button>
                                    </DialogFooter>
                                </DialogClose>
                            </DialogContent>
                        </Dialog>

                    </div>


                    <div class="p-4">
                        <ul class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
                            <li v-for="(career, index) in careersComputed" :key="index"
                                class="flex justify-between items-center gap-3 p-4 bg-gray-50 text-gray-700 rounded-xl border-2 hover:border-blue-800 transition-colors duration-200">
                                <div class="flex items-center gap-4">
                                    <NuxtImg src="/job-work-chart-data-analysis-statistic-report-svgrepo-com.svg"
                                        class="object-contain" :alt="`career_${career}`" width="30" height="30"
                                        loading="lazy" />
                                    <span class="text-xs md:text-base font-medium">{{ career }}</span>
                                </div>
                                <!-- <CircleX
                                    class="text-red-500 cursor-pointer hover:scale-105 transition duration-300 ease-in-out" /> -->
                                <Dialog>
                                    <DialogTrigger>
                                        <CircleX
                                            class="text-red-500 cursor-pointer hover:scale-105 transition duration-300 ease-in-out" />
                                    </DialogTrigger>
                                    <DialogContent>
                                        <DialogHeader>
                                            <DialogTitle>ลบข้อมูลอาชีพ</DialogTitle>
                                            <DialogDescription>
                                                คุณต้องการลบอาชีพ {{ career }} หรือไม่
                                                ถ้าคุณยังไม่ได้ยืนยันกดบันทึกฟอร์ม ข้อมูลจะไม่ถูกลบจริง
                                            </DialogDescription>
                                        </DialogHeader>

                                        <DialogClose as-child>
                                            <DialogFooter>
                                                <Button @click="removeCareer(index)" type="submit">ลบข้อมูล</Button>
                                            </DialogFooter>
                                        </DialogClose>

                                    </DialogContent>
                                </Dialog>
                            </li>
                        </ul>
                    </div>

                </div>
            </div>

        </div>

        <div
            class="bg-white rounded-2xl shadow-md border border-gray-100 p-6 space-y-6 mt-8 dark:bg-gray-950 dark:border-gray-800">
            <div class="flex items-center justify-between">
                <h2 class="text-xl font-semibold text-gray-900 dark:text-white">
                    ข้อมูลหลักสูตร
                </h2>
                <div class="flex flex-col gap-2 sm:gap-3">
                    <!-- กลุ่มปุ่ม (สลับเป็นคอลัมน์บนจอเล็ก, เป็นแถวบนจอกว้าง) -->
                    <div class="flex flex-col sm:flex-row sm:items-center gap-2 sm:gap-4">
                        <!-- ปุ่มดาวน์โหลดไฟล์ตัวอย่าง -->
                        <a :href="`${apiBase}/files/courses/download`" for="download-sample"
                            class="w-full sm:w-auto flex items-center justify-center gap-2 px-4 py-2 rounded-lg border border-dashed border-sky-500 text-sky-700 cursor-pointer hover:bg-sky-50 transition">
                            <Download class="w-5 h-5" />
                            <span class="truncate">ดาวน์โหลดไฟล์ตัวอย่าง</span>
                        </a>

                        <!-- ปุ่มเลือกไฟล์ -->
                        <label for="courses"
                            class="w-full sm:w-auto flex items-center justify-center gap-2 px-4 py-2 rounded-lg border border-dashed border-sky-500 text-sky-700 cursor-pointer hover:bg-sky-50 transition">
                            <Upload class="w-5 h-5" />
                            <span class="truncate">เลือกไฟล์ .CSV</span>
                        </label>

                        <!-- input file (ซ่อน) -->
                        <input id="courses" type="file" accept=".csv" class="hidden"
                            @change="handleFileCoursesChange" />
                    </div>

                    <!-- ชื่อไฟล์ (แสดงใต้ปุ่มบนจอเล็ก, เลื่อนไปขวาบนจอกว้าง) -->
                    <p v-if="CoursesFile" class="text-sm text-gray-600 italic sm:ml-auto truncate">
                        ไฟล์ที่เลือก: <span class="font-medium text-sky-700">{{ CoursesFile.name }}</span>
                    </p>
                </div>
            </div>
            <!-- <h3 class="text-lg font-semibold text-gray-900 text-center dark:text-white mb-4">ปีการศึกษา</h3> -->
            <Tabs default-value="sector1" class="w-full">
                <TabsList class="grid w-full grid-cols-4">
                    <TabsTrigger value="sector1">
                        ปีที่ 1
                    </TabsTrigger>
                    <TabsTrigger value="sector2">
                        ปีที่ 2
                    </TabsTrigger>
                    <TabsTrigger value="sector3">
                        ปีที่ 3
                    </TabsTrigger>
                    <TabsTrigger value="sector4">
                        ปีที่ 4
                    </TabsTrigger>
                </TabsList>
                <TabsContent value="sector1">
                    <Card>
                        <CardHeader>
                            <CardTitle>รายวิชา</CardTitle>
                            <CardDescription>รายละเอียดรายวิชาอาจมีการเปลี่ยนแปลงตามแผนหลักสูตร</CardDescription>
                        </CardHeader>
                        <CardContent class="space-y-6">

                            <!-- ภาคการศึกษาที่ 1 -->
                            <div v-if="branch?.courses.find(c => c.sector === 1)" class="space-y-2">
                                <div class="flex items-center gap-2">
                                    <CalendarIcon class="h-4 w-4 text-muted-foreground" />
                                    <Label class="font-semibold text-base">ภาคการศึกษาที่ 1</Label>
                                </div>
                                <Table>
                                    <TableHeader class="bg-muted/40">
                                        <TableRow>
                                            <TableHead class="w-[120px]">รหัสรายวิชา</TableHead>
                                            <TableHead>ชื่อรายวิชา</TableHead>
                                            <TableHead class="text-center w-[80px]">หน่วยกิต</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        <TableRow
                                            v-for="course in branch?.courses?.filter(c => c.year === 1 && c.sector === 1)"
                                            :key="course.id" class="hover:bg-muted/10">
                                            <TableCell class="font-medium">{{ course.code }}</TableCell>
                                            <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                            <TableCell class="text-center">{{ course.credit }}</TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>

                            <Separator v-if="branch?.courses.find(c => c.sector === 2)" class="my-4" />

                            <!-- ภาคการศึกษาที่ 2 -->
                            <div v-if="branch?.courses.find(c => c.sector === 2)" class="space-y-2">
                                <div class="flex items-center gap-2">
                                    <CalendarIcon class="h-4 w-4 text-muted-foreground" />
                                    <Label class="font-semibold text-base">ภาคการศึกษาที่ 2</Label>
                                </div>
                                <Table>
                                    <TableHeader class="bg-muted/40">
                                        <TableRow>
                                            <TableHead class="w-[120px]">รหัสรายวิชา</TableHead>
                                            <TableHead>ชื่อรายวิชา</TableHead>
                                            <TableHead class="text-center w-[80px]">หน่วยกิต</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        <TableRow
                                            v-for="course in branch?.courses?.filter(c => c.year === 1 && c.sector === 2)"
                                            :key="course.id" class="hover:bg-muted/10">
                                            <TableCell class="font-medium">{{ course.code }}</TableCell>
                                            <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                            <TableCell class="text-center">{{ course.credit }}</TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>

                            <Separator v-if="branch?.courses.find(c => c.sector === 3)" class="my-4" />

                            <!-- ภาคการศึกษาที่ 3 -->
                            <div v-if="branch?.courses.find(c => c.sector === 3)" class="space-y-2">
                                <div class="flex items-center gap-2">
                                    <CalendarIcon class="h-4 w-4 text-muted-foreground" />
                                    <Label class="font-semibold text-base">ภาคการศึกษาที่ 3</Label>
                                </div>
                                <Table>
                                    <TableHeader class="bg-muted/40">
                                        <TableRow>
                                            <TableHead class="w-[120px]">รหัสรายวิชา</TableHead>
                                            <TableHead>ชื่อรายวิชา</TableHead>
                                            <TableHead class="text-center w-[80px]">หน่วยกิต</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        <TableRow
                                            v-for="course in branch?.courses?.filter(c => c.year === 1 && c.sector === 3)"
                                            :key="course.id" class="hover:bg-muted/10">
                                            <TableCell class="font-medium">{{ course.code }}</TableCell>
                                            <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                            <TableCell class="text-center">{{ course.credit }}</TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>

                        </CardContent>
                    </Card>
                </TabsContent>

                <TabsContent value="sector2">
                    <Card>
                        <CardHeader>
                            <CardTitle>รายวิชา</CardTitle>
                            <CardDescription>รายละเอียดรายวิชาอาจมีการเปลี่ยนแปลงตามแผนหลักสูตร</CardDescription>
                        </CardHeader>
                        <CardContent class="space-y-6">

                            <!-- ภาคการศึกษาที่ 1 -->
                            <div v-if="branch?.courses.find(c => c.sector === 1)" class="space-y-2">
                                <div class="flex items-center gap-2">
                                    <CalendarIcon class="h-4 w-4 text-muted-foreground" />
                                    <Label class="font-semibold text-base">ภาคการศึกษาที่ 1</Label>
                                </div>
                                <Table>
                                    <TableHeader class="bg-muted/40">
                                        <TableRow>
                                            <TableHead class="w-[120px]">รหัสรายวิชา</TableHead>
                                            <TableHead>ชื่อรายวิชา</TableHead>
                                            <TableHead class="text-center w-[80px]">หน่วยกิต</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        <TableRow
                                            v-for="course in branch?.courses?.filter(c => c.year === 2 && c.sector === 1)"
                                            :key="course.id" class="hover:bg-muted/10">
                                            <TableCell class="font-medium">{{ course.code }}</TableCell>
                                            <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                            <TableCell class="text-center">{{ course.credit }}</TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>

                            <Separator v-if="branch?.courses.find(c => c.sector === 2)" class="my-4" />

                            <!-- ภาคการศึกษาที่ 2 -->
                            <div v-if="branch?.courses.find(c => c.sector === 2)" class="space-y-2">
                                <div class="flex items-center gap-2">
                                    <CalendarIcon class="h-4 w-4 text-muted-foreground" />
                                    <Label class="font-semibold text-base">ภาคการศึกษาที่ 2</Label>
                                </div>
                                <Table>
                                    <TableHeader class="bg-muted/40">
                                        <TableRow>
                                            <TableHead class="w-[120px]">รหัสรายวิชา</TableHead>
                                            <TableHead>ชื่อรายวิชา</TableHead>
                                            <TableHead class="text-center w-[80px]">หน่วยกิต</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        <TableRow
                                            v-for="course in branch?.courses?.filter(c => c.year === 2 && c.sector === 2)"
                                            :key="course.id" class="hover:bg-muted/10">
                                            <TableCell class="font-medium">{{ course.code }}</TableCell>
                                            <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                            <TableCell class="text-center">{{ course.credit }}</TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>

                            <Separator v-if="branch?.courses.find(c => c.sector === 3)" class="my-4" />

                            <!-- ภาคการศึกษาที่ 3 -->
                            <div v-if="branch?.courses.find(c => c.sector === 3)" class="space-y-2">
                                <div class="flex items-center gap-2">
                                    <CalendarIcon class="h-4 w-4 text-muted-foreground" />
                                    <Label class="font-semibold text-base">ภาคการศึกษาที่ 3</Label>
                                </div>
                                <Table>
                                    <TableHeader class="bg-muted/40">
                                        <TableRow>
                                            <TableHead class="w-[120px]">รหัสรายวิชา</TableHead>
                                            <TableHead>ชื่อรายวิชา</TableHead>
                                            <TableHead class="text-center w-[80px]">หน่วยกิต</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        <TableRow
                                            v-for="course in branch?.courses?.filter(c => c.year === 2 && c.sector === 3)"
                                            :key="course.id" class="hover:bg-muted/10">
                                            <TableCell class="font-medium">{{ course.code }}</TableCell>
                                            <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                            <TableCell class="text-center">{{ course.credit }}</TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>

                        </CardContent>
                    </Card>
                </TabsContent>
                <TabsContent value="sector3">
                    <Card>
                        <CardHeader>
                            <CardTitle>รายวิชา</CardTitle>
                            <CardDescription>รายละเอียดรายวิชาอาจมีการเปลี่ยนแปลงตามแผนหลักสูตร</CardDescription>
                        </CardHeader>
                        <CardContent class="space-y-6">

                            <!-- ภาคการศึกษาที่ 1 -->
                            <div v-if="branch?.courses.find(c => c.sector === 1)" class="space-y-2">
                                <div class="flex items-center gap-2">
                                    <CalendarIcon class="h-4 w-4 text-muted-foreground" />
                                    <Label class="font-semibold text-base">ภาคการศึกษาที่ 1</Label>
                                </div>
                                <Table>
                                    <TableHeader class="bg-muted/40">
                                        <TableRow>
                                            <TableHead class="w-[120px]">รหัสรายวิชา</TableHead>
                                            <TableHead>ชื่อรายวิชา</TableHead>
                                            <TableHead class="text-center w-[80px]">หน่วยกิต</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        <TableRow
                                            v-for="course in branch?.courses?.filter(c => c.year === 3 && c.sector === 1)"
                                            :key="course.id" class="hover:bg-muted/10">
                                            <TableCell class="font-medium">{{ course.code }}</TableCell>
                                            <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                            <TableCell class="text-center">{{ course.credit }}</TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>

                            <Separator v-if="branch?.courses.find(c => c.sector === 2)" class="my-4" />

                            <!-- ภาคการศึกษาที่ 2 -->
                            <div v-if="branch?.courses.find(c => c.sector === 2)" class="space-y-2">
                                <div class="flex items-center gap-2">
                                    <CalendarIcon class="h-4 w-4 text-muted-foreground" />
                                    <Label class="font-semibold text-base">ภาคการศึกษาที่ 2</Label>
                                </div>
                                <Table>
                                    <TableHeader class="bg-muted/40">
                                        <TableRow>
                                            <TableHead class="w-[120px]">รหัสรายวิชา</TableHead>
                                            <TableHead>ชื่อรายวิชา</TableHead>
                                            <TableHead class="text-center w-[80px]">หน่วยกิต</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        <TableRow
                                            v-for="course in branch?.courses?.filter(c => c.year === 3 && c.sector === 2)"
                                            :key="course.id" class="hover:bg-muted/10">
                                            <TableCell class="font-medium">{{ course.code }}</TableCell>
                                            <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                            <TableCell class="text-center">{{ course.credit }}</TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>

                            <Separator v-if="branch?.courses.find(c => c.sector === 3)" class="my-4" />

                            <!-- ภาคการศึกษาที่ 3 -->
                            <div v-if="branch?.courses.find(c => c.sector === 3)" class="space-y-2">
                                <div class="flex items-center gap-2">
                                    <CalendarIcon class="h-4 w-4 text-muted-foreground" />
                                    <Label class="font-semibold text-base">ภาคการศึกษาที่ 3</Label>
                                </div>
                                <Table>
                                    <TableHeader class="bg-muted/40">
                                        <TableRow>
                                            <TableHead class="w-[120px]">รหัสรายวิชา</TableHead>
                                            <TableHead>ชื่อรายวิชา</TableHead>
                                            <TableHead class="text-center w-[80px]">หน่วยกิต</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        <TableRow
                                            v-for="course in branch?.courses?.filter(c => c.year === 3 && c.sector === 3)"
                                            :key="course.id" class="hover:bg-muted/10">
                                            <TableCell class="font-medium">{{ course.code }}</TableCell>
                                            <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                            <TableCell class="text-center">{{ course.credit }}</TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>

                        </CardContent>
                    </Card>
                </TabsContent>
                <TabsContent value="sector4">
                    <Card>
                        <CardHeader>
                            <CardTitle>รายวิชา</CardTitle>
                            <CardDescription>รายละเอียดรายวิชาอาจมีการเปลี่ยนแปลงตามแผนหลักสูตร</CardDescription>
                        </CardHeader>
                        <CardContent class="space-y-6">

                            <!-- ภาคการศึกษาที่ 1 -->
                            <div v-if="branch?.courses.find(c => c.sector === 1)" class="space-y-2">
                                <div class="flex items-center gap-2">
                                    <CalendarIcon class="h-4 w-4 text-muted-foreground" />
                                    <Label class="font-semibold text-base">ภาคการศึกษาที่ 1</Label>
                                </div>
                                <Table>
                                    <TableHeader class="bg-muted/40">
                                        <TableRow>
                                            <TableHead class="w-[120px]">รหัสรายวิชา</TableHead>
                                            <TableHead>ชื่อรายวิชา</TableHead>
                                            <TableHead class="text-center w-[80px]">หน่วยกิต</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        <TableRow
                                            v-for="course in branch?.courses?.filter(c => c.year === 4 && c.sector === 1)"
                                            :key="course.id" class="hover:bg-muted/10">
                                            <TableCell class="font-medium">{{ course.code }}</TableCell>
                                            <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                            <TableCell class="text-center">{{ course.credit }}</TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>

                            <Separator v-if="branch?.courses.find(c => c.sector === 2)" class="my-4" />

                            <!-- ภาคการศึกษาที่ 2 -->
                            <div v-if="branch?.courses.find(c => c.sector === 2)" class="space-y-2">
                                <div class="flex items-center gap-2">
                                    <CalendarIcon class="h-4 w-4 text-muted-foreground" />
                                    <Label class="font-semibold text-base">ภาคการศึกษาที่ 2</Label>
                                </div>
                                <Table>
                                    <TableHeader class="bg-muted/40">
                                        <TableRow>
                                            <TableHead class="w-[120px]">รหัสรายวิชา</TableHead>
                                            <TableHead>ชื่อรายวิชา</TableHead>
                                            <TableHead class="text-center w-[80px]">หน่วยกิต</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        <TableRow
                                            v-for="course in branch?.courses?.filter(c => c.year === 4 && c.sector === 2)"
                                            :key="course.id" class="hover:bg-muted/10">
                                            <TableCell class="font-medium">{{ course.code }}</TableCell>
                                            <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                            <TableCell class="text-center">{{ course.credit }}</TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>

                            <Separator v-if="branch?.courses.find(c => c.sector === 3)" class="my-4" />

                            <!-- ภาคการศึกษาที่ 3 -->
                            <div v-if="branch?.courses.find(c => c.sector === 3)" class="space-y-2">
                                <div class="flex items-center gap-2">
                                    <CalendarIcon class="h-4 w-4 text-muted-foreground" />
                                    <Label class="font-semibold text-base">ภาคการศึกษาที่ 3</Label>
                                </div>
                                <Table>
                                    <TableHeader class="bg-muted/40">
                                        <TableRow>
                                            <TableHead class="w-[120px]">รหัสรายวิชา</TableHead>
                                            <TableHead>ชื่อรายวิชา</TableHead>
                                            <TableHead class="text-center w-[80px]">หน่วยกิต</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        <TableRow
                                            v-for="course in branch?.courses?.filter(c => c.year === 4 && c.sector === 3)"
                                            :key="course.id" class="hover:bg-muted/10">
                                            <TableCell class="font-medium">{{ course.code }}</TableCell>
                                            <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                            <TableCell class="text-center">{{ course.credit }}</TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>

                        </CardContent>
                    </Card>
                </TabsContent>
            </Tabs>
        </div>

        <!-- <Card class="mt-8">
            <CardHeader>
                <CardTitle class="text-xl font-semibold">แขนงวิชา</CardTitle>
                <CardDescription>แขนงวิชาที่ตอบโจทย์ตลาดแรงงาน</CardDescription>
            </CardHeader>
            <CardContent>
                <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                    <NuxtLink
                        v-for="branch in branchs?.filter(b => b.branch.id === branch?.id && b.branch.program.degree.id === branch?.program.degree.id)"
                        :key="branch.id" :to="`/branch/${branch.id}`"
                        class="bg-white rounded-2xl shadow-md hover:shadow-sky-300/60 hover:ring-2 hover:ring-sky-300 transition-all duration-300 ease-in-out overflow-hidden group flex flex-col h-full justify-between">
                        <NuxtImg :src="`${apiBase}/images/${branch.image}`" width="600" height="400" format="webp"
                            quality="80" :alt="`ภาพของสาขา ${branch.nameTh}`" densities="x1 x2"
                            sizes="(max-width: 768px) 100vw, 600px" placeholder loading="lazy"
                            class="w-full aspect-[6/3] object-cover transition-transform duration-300 ease-in-out group-hover:scale-105" />
                        <div class="p-4 flex flex-col justify-between flex-1 gap-3">
                            
                            <div class="flex flex-col gap-0.5">
                                <h3
                                    class="text-base sm:text-lg font-semibold text-sky-700 group-hover:underline line-clamp-1">
                                    {{ branch.nameTh }}
                                </h3>
                                <h3
                                    class="text-sm sm:text-md font-semibold text-sky-700 group-hover:underline line-clamp-1">
                                    {{ branch.nameEn }}
                                </h3>
                            </div>

                            
                            <div class="flex flex-wrap gap-2">
                                <Badge
                                    class="bg-sky-100 text-sky-800 dark:bg-sky-800 dark:text-sky-100 text-xs sm:text-sm px-3 py-1 rounded-full">
                                    {{ branch.branch.program.degree.nameTh }}
                                </Badge>
                                <Badge
                                    class="bg-gray-100 text-gray-800 dark:bg-gray-800 dark:text-gray-100 text-xs sm:text-sm px-3 py-1 rounded-full">
                                    {{ toLanguage(branch.language) }}
                                </Badge>
                            </div>
                        </div>
                    </NuxtLink>
                </div>
            </CardContent>
        </Card> -->

    </div>
</template>