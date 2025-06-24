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

import type { Branch } from '~/types/Branch'

const route = useRoute()
const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const { data: branch, error } = await useFetch<Branch>(`${apiBase}/branch/${route.params.id}`)

if (error.value) {
    console.error(error.value)
}

console.log(branch)

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

const careers = branch.value?.careers.split(",")

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
                            {{ branch?.nameTh ?? '...' }}
                        </BreadcrumbPage>
                    </BreadcrumbItem>
                </BreadcrumbList>
            </Breadcrumb>

            <div class="relative w-full aspect-[16/6] rounded-2xl shadow-lg overflow-hidden text-white">
                <NuxtImg v-if="branch?.image" :src="`${apiBase}/images/${branch?.image}`" width="1920" height="1080"
                    format="webp" sizes="(max-width: 1080px) 100vw, 1080px" alt="`ภาพปกสาขาวิชา ${branch?.nameTh}`"
                    priority class="object-cover w-full aspect-[6/3]" />

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
                                        {{ branch?.major.program.degree.nameTh }}
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
                                        {{ branch?.major.program.nameTh }}
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
                                        {{ toLanguage(branch?.language || '') }}
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
                                        {{ formatTuition(branch?.tuitionFees) }}
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
                            <p v-if="branch?.description"
                                class="mt-4 text-gray-700 dark:text-gray-300 leading-relaxed text-pretty indent-8 whitespace-pre-wrap"
                                v-text="branch.description">
                            </p>
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

        </div>

    </div>
</template>