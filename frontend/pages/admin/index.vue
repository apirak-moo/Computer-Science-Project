<script setup lang="ts">
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from '@/components/ui/breadcrumb'
import { Button } from '@/components/ui/button'
import { Card, CardContent } from '@/components/ui/card'

// Import icons from lucide-vue-next
import {
    Home,
    Settings,
    BookMarked,
    Compass,
    Newspaper,
    Library,
    Users,
    History,
    ChevronRight,
} from 'lucide-vue-next'

definePageMeta({
  middleware: 'auth',
  roles: ['Administrator']
})

// Define the structure for our management items
const managementItems = [
    {
        title: "จัดการสาขาวิชาคณะ",
        path: "major",
        icon: BookMarked,
        description: "เพิ่ม ลบ และแก้ไขข้อมูลสาขาวิชา"
    },
    {
        title: "จัดการแขนงวิชาคณะ",
        path: "branch",
        icon: Compass,
        description: "กำหนดและบริหารแขนงวิชาต่างๆ"
    },
    {
        title: "จัดการข่าวสารคณะ",
        path: "news",
        icon: Newspaper,
        description: "เผยแพร่ประกาศและข่าวสารล่าสุด"
    },
    {
        title: "จัดการโครงสร้างองค์กร",
        path: "structure",
        icon: Library,
        description: "ปรับปรุงโครงสร้างและแผนผังองค์กร"
    },
    {
        title: "จัดการบุคลากรคณะ",
        path: "personnel",
        icon: Users,
        description: "บริหารข้อมูลสมาชิกและบุคลากร"
    },
    {
        title: "จัดการประวัติคณะ",
        path: "history",
        icon: History,
        description: "บันทึกและแก้ไขประวัติความเป็นมา"
    },
]
</script>

<template>
    <main class=" min-h-screen">
        <div class="container mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <header class="mb-8">
                <Breadcrumb class="mb-4">
                    <BreadcrumbList>
                        <BreadcrumbItem>
                            <BreadcrumbLink as-child>
                                <NuxtLink to="/" class="flex items-center gap-1.5 text-muted-foreground hover:text-foreground transition-colors">
                                    <Home class="w-4 h-4" />
                                    <span>หน้าหลัก</span>
                                </NuxtLink>
                            </BreadcrumbLink>
                        </BreadcrumbItem>
                        <BreadcrumbSeparator />
                        <BreadcrumbItem>
                            <BreadcrumbPage class="font-medium text-sky-700 dark:text-sky-500">
                                แดชบอร์ดผู้ดูแลระบบ
                            </BreadcrumbPage>
                        </BreadcrumbItem>
                    </BreadcrumbList>
                </Breadcrumb>

                <div class="flex flex-col sm:flex-row justify-between items-start gap-4">
                    <div>
                        <h1 class="text-3xl md:text-4xl font-bold tracking-tight text-slate-900 dark:text-slate-50">
                            ระบบจัดการเว็บไซต์
                        </h1>
                        <p class="mt-2 text-lg text-muted-foreground">
                            เลือกเมนูที่คุณต้องการเข้าไปจัดการข้อมูล
                        </p>
                    </div>
                    <Button variant="outline" class="shrink-0">
                        <Settings class="mr-2 h-4 w-4" />
                        ตั้งค่าทั่วไป
                    </Button>
                </div>
            </header>

            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                <NuxtLink
                    v-for="(item, index) in managementItems"
                    :key="index"
                    :to="`/admin/${item.path}`"
                    class="group"
                >
                    <Card class="h-full bg-card hover:bg-sky-50/50 dark:hover:bg-slate-800/60 border-border/80 hover:border-sky-500 shadow-sm hover:shadow-lg transition-all duration-300 ease-in-out hover:-translate-y-1">
                        <CardContent class="p-6 flex flex-col items-start justify-between h-full">
                            <div>
                                <div class="mb-4 p-3 bg-sky-100 dark:bg-sky-900/50 rounded-lg w-fit">
                                    <component :is="item.icon" class="w-7 h-7 text-sky-700 dark:text-sky-400" />
                                </div>
                                <h3 class="text-lg font-semibold text-card-foreground group-hover:text-sky-700 dark:group-hover:text-sky-400 transition-colors">
                                    {{ item.title }}
                                </h3>
                                <p class="mt-1 text-sm text-muted-foreground">
                                    {{ item.description }}
                                </p>
                            </div>
                            <div class="mt-6 flex items-center text-sm font-medium text-sky-600 dark:text-sky-500 group-hover:text-sky-700 dark:group-hover:text-sky-400">
                               <span>เข้าไปจัดการ</span>
                               <ChevronRight class="w-4 h-4 ml-1 transition-transform duration-300 group-hover:translate-x-1" />
                            </div>
                        </CardContent>
                    </Card>
                </NuxtLink>
            </div>
        </div>
    </main>
</template>