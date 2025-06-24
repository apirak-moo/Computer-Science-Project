<script setup lang="ts">
import {
    Breadcrumb,
    BreadcrumbItem,
    BreadcrumbList,
    BreadcrumbPage,
    BreadcrumbSeparator,
} from '@/components/ui/breadcrumb'

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

import { LucideGraduationCap, LucideBookOpenCheck, LucideGlobe, LucideWallet, LucideFileText, LucideFileUser, CalendarIcon, BriefcaseBusiness } from 'lucide-vue-next';

import type { Major } from '~/types/Major'

const route = useRoute()
const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const { data: major } = await useFetch<Major>(`${apiBase}/major/${route.params.id}`)

const { data: branchs, error } = await useFetch(`${apiBase}/branch/mini`);

if (error.value) {
    console.error(error.value)
}

console.log(branchs)

function toLanguage(text: string): string {
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

// Function สำหรับ Format ตัวเลขค่าเทอม
function formatTuition(fee: number | null | undefined): string {
    if (!fee) return 'ไม่ระบุ'
    return `${new Intl.NumberFormat('th-TH').format(fee)} บาท/ภาคการศึกษา`
}

useHead({
    title: major.value?.nameTh || 'ข้อมูลสาขาวิชา',
    htmlAttrs: {
        lang: 'th'
    },
    meta: [
        {
            name: 'description',
            content: major.value?.description?.slice(0, 160) || 'รายละเอียดหลักสูตร สาขาวิชา คณะ และอาชีพในอนาคต',
        },
        {
            name: 'keywords',
            content: `${major.value?.nameTh}, ${major.value?.nameEn}, มหาวิทยาลัย, สาขาวิชา, คณะ, เรียนต่อ, ค่าเทอม, อาชีพ`,
        },
        {
            property: 'og:title',
            content: major.value?.nameTh || 'ข้อมูลสาขาวิชา',
        },
        {
            property: 'og:description',
            content: major.value?.description?.slice(0, 160) || 'รายละเอียดหลักสูตร สาขาวิชา คณะ และอาชีพในอนาคต',
        },
        {
            property: 'og:image',
            content: major.value?.image ? `${apiBase}/images/${major.value.image}` : '/default-og-image.jpg',
        },
        {
            property: 'og:url',
            content: `${apiBase}/major/${route.params.id}`,
        },
        {
            name: 'twitter:card',
            content: 'summary_large_image',
        },
    ],
    link: [
        {
            rel: 'canonical',
            href: `${apiBase}/major/${route.params.id}`,
        },
    ],
})

const careers = major.value?.careers.split(",")

</script>

<template>
    <div>

        <div class="container mx-auto p-4 space-y-8">

            <Breadcrumb>
                <BreadcrumbList>
                    <BreadcrumbItem>
                        <NuxtLink to="/" class="hover:underline">หน้าแรก</NuxtLink>
                    </BreadcrumbItem>
                    <BreadcrumbSeparator />
                    <BreadcrumbItem>
                        <DropdownMenu>
                            <DropdownMenuTrigger class="flex items-center gap-1 cursor-pointer">
                                <BreadcrumbEllipsis class="h-4 w-4" />
                                <span class="sr-only">Toggle menu</span>
                            </DropdownMenuTrigger>
                            <DropdownMenuContent align="start">
                                <NuxtLink to="/degree" class="hover:underline">
                                    <DropdownMenuItem class="cursor-pointer">
                                        หลักสูตร
                                    </DropdownMenuItem>
                                </NuxtLink>

                                <NuxtLink to="/major" class="hover:underline">
                                    <DropdownMenuItem class="cursor-pointer">
                                        สาขาวิชา
                                    </DropdownMenuItem>
                                </NuxtLink>
                                <NuxtLink to="/branch" class="hover:underline">
                                    <DropdownMenuItem class="cursor-pointer">
                                        แขนงวิชา
                                    </DropdownMenuItem>
                                </NuxtLink>
                            </DropdownMenuContent>
                        </DropdownMenu>
                    </BreadcrumbItem>
                    <BreadcrumbSeparator />
                    <BreadcrumbItem>
                        <BreadcrumbPage class="font-normal">
                            {{ major?.nameTh ?? '...' }}
                        </BreadcrumbPage>
                    </BreadcrumbItem>
                </BreadcrumbList>
            </Breadcrumb>

            <div class="relative w-full aspect-[16/6] rounded-2xl shadow-lg overflow-hidden text-white">
                <NuxtImg v-if="major?.image" :src="`${apiBase}/images/${major?.image}`" width="1920" height="1080"
                    format="webp" sizes="(max-width: 1080px) 100vw, 1080px" alt="`ภาพปกสาขาวิชา ${major?.nameTh}`"
                    priority class="object-cover w-full aspect-[6/3]" />

                <div v-else class="w-full h-full bg-sky-800 flex items-center justify-center"
                    aria-label="ไม่มีภาพปกสำหรับสาขาวิชานี้">
                    <span class="sr-only">ไม่มีภาพปกสำหรับสาขาวิชานี้</span>
                </div>

                <div class="absolute inset-0 bg-gradient-to-t from-black/70 via-black/40 to-transparent"></div>

                <div class="absolute bottom-0 left-0 p-6 md:p-8 space-y-4">
                    <div class="space-y-1">
                        <h1 class="text-sm sm:text-xl md:text-2xl lg:text-4xl font-bold text-balance">
                            {{ major?.nameTh }}
                        </h1>
                        <p class="text-xs sm:text-xl text-gray-200 italic">{{ major?.nameEn }}</p>
                    </div>
                </div>
            </div>

            <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">

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
                                        {{ major?.program.degree.nameTh }}
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
                                        {{ major?.program.nameTh }}
                                    </div>
                                </div>
                            </li>
                            <li class="flex items-start gap-4">
                                <div
                                    class="flex-shrink-0 w-8 h-8 flex items-center justify-center rounded-lg bg-indigo-100 dark:bg-indigo-900/50">
                                    <LucideGlobe class="w-5 h-5 text-indigo-600 dark:text-indigo-400" />
                                </div>
                                <div>
                                    <div class="text-sm text-muted-foreground">ภาษา</div>
                                    <div class="font-semibold text-gray-900 dark:text-white">
                                        {{ toLanguage(major?.language || '') }}
                                    </div>
                                </div>
                            </li>
                            <li class="flex items-start gap-4">
                                <div
                                    class="flex-shrink-0 w-8 h-8 flex items-center justify-center rounded-lg bg-amber-100 dark:bg-amber-900/50">
                                    <LucideWallet class="w-5 h-5 text-amber-600 dark:text-amber-400" />
                                </div>
                                <div>
                                    <div class="text-sm text-muted-foreground">ค่าธรรมเนียมการศึกษา (โดยประมาณ)</div>
                                    <div class="font-semibold text-gray-900 dark:text-white">
                                        {{ formatTuition(major?.tuitionFees) }}
                                    </div>
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
                            <p v-if="major?.description"
                                class="mt-4 text-gray-700 dark:text-gray-300 leading-relaxed text-pretty indent-8 whitespace-pre-wrap"
                                v-text="major.description" />
                            <p v-else class="mt-4 text-gray-500 italic">
                                ไม่มีคำอธิบายหลักสูตร
                            </p>
                        </div>

                        <div class="border-t border-gray-200 dark:border-gray-700 pt-6 flex flex-col sm:flex-row gap-4">
                            <a href="https://reg.pbru.ac.th/registrar/apphome.asp" target="_blank"
                                rel="noopener noreferrer"
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
                        <div class="flex items-center gap-4 mb-6">
                            <div
                                class="flex-shrink-0 w-12 h-12 flex items-center justify-center rounded-xl bg-gradient-to-br from-sky-500 to-indigo-600 text-white shadow-lg">
                                <BriefcaseBusiness class="w-7 h-7" />
                            </div>
                            <div>
                                <h3 class="text-2xl font-bold text-gray-900 dark:text-white">อาชีพหลังจบการศึกษา</h3>
                                <p class="text-gray-500 dark:text-gray-400">เส้นทางสู่อาชีพในฝันของคุณ</p>
                            </div>
                        </div>

                        <div class="p-4">
                            <ul class="grid grid-cols-2 md:grid-cols-3 gap-4">
                                <li v-for="(career, index) in careers" :key="index"
                                    class="flex items-center gap-3 p-4 bg-gray-50 text-gray-700 rounded-xl border-2 hover:border-blue-800 transition-colors duration-200">
                                    <NuxtImg src="/job-work-chart-data-analysis-statistic-report-svgrepo-com.svg"
                                        class="object-contain" :alt="`career_${career}`" width="30" height="30"
                                        loading="lazy" />
                                    <span class="text-xs md:text-base font-medium">{{ career }}</span>
                                </li>
                            </ul>
                        </div>

                    </div>
                </div>

            </div>

            <div
                class="bg-white rounded-2xl shadow-md border border-gray-100 p-6 space-y-6 dark:bg-gray-950 dark:border-gray-800">
                <h2 class="text-xl font-semibold text-gray-900 dark:text-white">
                    ข้อมูลหลักสูตร
                </h2>
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
                                <div v-if="major?.courses.find(c => c.sector === 1)" class="space-y-2">
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
                                                v-for="course in major?.courses?.filter(c => c.year === 1 && c.sector === 1)"
                                                :key="course.id" class="hover:bg-muted/10">
                                                <TableCell class="font-medium">{{ course.code }}</TableCell>
                                                <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                                <TableCell class="text-center">{{ course.credit }}</TableCell>
                                            </TableRow>
                                        </TableBody>
                                    </Table>
                                </div>

                                <Separator v-if="major?.courses.find(c => c.sector === 2)" class="my-4" />

                                <!-- ภาคการศึกษาที่ 2 -->
                                <div v-if="major?.courses.find(c => c.sector === 2)" class="space-y-2">
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
                                                v-for="course in major?.courses?.filter(c => c.year === 1 && c.sector === 2)"
                                                :key="course.id" class="hover:bg-muted/10">
                                                <TableCell class="font-medium">{{ course.code }}</TableCell>
                                                <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                                <TableCell class="text-center">{{ course.credit }}</TableCell>
                                            </TableRow>
                                        </TableBody>
                                    </Table>
                                </div>

                                <Separator v-if="major?.courses.find(c => c.sector === 3)" class="my-4" />

                                <!-- ภาคการศึกษาที่ 3 -->
                                <div v-if="major?.courses.find(c => c.sector === 3)" class="space-y-2">
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
                                                v-for="course in major?.courses?.filter(c => c.year === 1 && c.sector === 3)"
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
                                <div v-if="major?.courses.find(c => c.sector === 1)" class="space-y-2">
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
                                                v-for="course in major?.courses?.filter(c => c.year === 2 && c.sector === 1)"
                                                :key="course.id" class="hover:bg-muted/10">
                                                <TableCell class="font-medium">{{ course.code }}</TableCell>
                                                <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                                <TableCell class="text-center">{{ course.credit }}</TableCell>
                                            </TableRow>
                                        </TableBody>
                                    </Table>
                                </div>

                                <Separator v-if="major?.courses.find(c => c.sector === 2)" class="my-4" />

                                <!-- ภาคการศึกษาที่ 2 -->
                                <div v-if="major?.courses.find(c => c.sector === 2)" class="space-y-2">
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
                                                v-for="course in major?.courses?.filter(c => c.year === 2 && c.sector === 2)"
                                                :key="course.id" class="hover:bg-muted/10">
                                                <TableCell class="font-medium">{{ course.code }}</TableCell>
                                                <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                                <TableCell class="text-center">{{ course.credit }}</TableCell>
                                            </TableRow>
                                        </TableBody>
                                    </Table>
                                </div>

                                <Separator v-if="major?.courses.find(c => c.sector === 3)" class="my-4" />

                                <!-- ภาคการศึกษาที่ 3 -->
                                <div v-if="major?.courses.find(c => c.sector === 3)" class="space-y-2">
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
                                                v-for="course in major?.courses?.filter(c => c.year === 2 && c.sector === 3)"
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
                                <div v-if="major?.courses.find(c => c.sector === 1)" class="space-y-2">
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
                                                v-for="course in major?.courses?.filter(c => c.year === 3 && c.sector === 1)"
                                                :key="course.id" class="hover:bg-muted/10">
                                                <TableCell class="font-medium">{{ course.code }}</TableCell>
                                                <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                                <TableCell class="text-center">{{ course.credit }}</TableCell>
                                            </TableRow>
                                        </TableBody>
                                    </Table>
                                </div>

                                <Separator v-if="major?.courses.find(c => c.sector === 2)" class="my-4" />

                                <!-- ภาคการศึกษาที่ 2 -->
                                <div v-if="major?.courses.find(c => c.sector === 2)" class="space-y-2">
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
                                                v-for="course in major?.courses?.filter(c => c.year === 3 && c.sector === 2)"
                                                :key="course.id" class="hover:bg-muted/10">
                                                <TableCell class="font-medium">{{ course.code }}</TableCell>
                                                <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                                <TableCell class="text-center">{{ course.credit }}</TableCell>
                                            </TableRow>
                                        </TableBody>
                                    </Table>
                                </div>

                                <Separator v-if="major?.courses.find(c => c.sector === 3)" class="my-4" />

                                <!-- ภาคการศึกษาที่ 3 -->
                                <div v-if="major?.courses.find(c => c.sector === 3)" class="space-y-2">
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
                                                v-for="course in major?.courses?.filter(c => c.year === 3 && c.sector === 3)"
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
                                <div v-if="major?.courses.find(c => c.sector === 1)" class="space-y-2">
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
                                                v-for="course in major?.courses?.filter(c => c.year === 4 && c.sector === 1)"
                                                :key="course.id" class="hover:bg-muted/10">
                                                <TableCell class="font-medium">{{ course.code }}</TableCell>
                                                <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                                <TableCell class="text-center">{{ course.credit }}</TableCell>
                                            </TableRow>
                                        </TableBody>
                                    </Table>
                                </div>

                                <Separator v-if="major?.courses.find(c => c.sector === 2)" class="my-4" />

                                <!-- ภาคการศึกษาที่ 2 -->
                                <div v-if="major?.courses.find(c => c.sector === 2)" class="space-y-2">
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
                                                v-for="course in major?.courses?.filter(c => c.year === 4 && c.sector === 2)"
                                                :key="course.id" class="hover:bg-muted/10">
                                                <TableCell class="font-medium">{{ course.code }}</TableCell>
                                                <TableCell>{{ course.nameTh }} <br> {{ course.nameEn }}</TableCell>
                                                <TableCell class="text-center">{{ course.credit }}</TableCell>
                                            </TableRow>
                                        </TableBody>
                                    </Table>
                                </div>

                                <Separator v-if="major?.courses.find(c => c.sector === 3)" class="my-4" />

                                <!-- ภาคการศึกษาที่ 3 -->
                                <div v-if="major?.courses.find(c => c.sector === 3)" class="space-y-2">
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
                                                v-for="course in major?.courses?.filter(c => c.year === 4 && c.sector === 3)"
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

            <Card v-if="branchs?.filter(b => b.major.id === major?.id).length">
                <CardHeader>
                    <CardTitle class="text-xl font-semibold">แขนงวิชา</CardTitle>
                    <CardDescription>แขนงวิชาที่ตอบโจทย์ตลาดแรงงาน</CardDescription>
                </CardHeader>
                <CardContent>
                    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                        <NuxtLink
                            v-for="branch in branchs?.filter(b => b.major.id === major?.id && b.major.program.degree.id === major?.program.degree.id)"
                            :key="branch.id" :to="`/branch/${branch.id}`"
                            class="bg-white rounded-2xl shadow-md hover:shadow-sky-300/60 hover:ring-2 hover:ring-sky-300 transition-all duration-300 ease-in-out overflow-hidden group flex flex-col h-full justify-between">
                            <NuxtImg :src="`${apiBase}/images/${branch.image}`" width="600" height="400" format="webp"
                                quality="80" :alt="`ภาพของสาขา ${branch.nameTh}`" densities="x1 x2"
                                sizes="(max-width: 768px) 100vw, 600px" placeholder loading="lazy"
                                class="w-full aspect-[6/3] object-cover transition-transform duration-300 ease-in-out group-hover:scale-105" />
                            <div class="p-4 flex flex-col justify-between flex-1 gap-3">
                                <!-- ชื่อแขนง -->
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

                                <!-- Badges -->
                                <div class="flex flex-wrap gap-2">
                                    <Badge
                                        class="bg-sky-100 text-sky-800 dark:bg-sky-800 dark:text-sky-100 text-xs sm:text-sm px-3 py-1 rounded-full">
                                        {{ branch.major.program.degree.nameTh }}
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
            </Card>

        </div>

    </div>
</template>