<script setup lang="ts">
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
import { Button } from '@/components/ui/button'
import { Separator } from '@/components/ui/separator'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { Mail, CheckCircle2, GraduationCap, Calendar, User, Briefcase, Info, Landmark, Github, Smartphone, Plus, Trash2, Loader2 } from 'lucide-vue-next'
import type { Professor } from '~/types/Professor'
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from '@/components/ui/dialog'
import type { ProfessorQualification } from '~/types/ProfessorQualification'
import type { Degree } from '~/types/Degree'

// --- 1. การดึงข้อมูล ---
// ใช้ useRoute() เพื่อเข้าถึงพารามิเตอร์ของ URL (เช่น id)
const route = useRoute()
const personId = route.params.id

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const { data: degrees } = await useFetch<Degree[]>(`${apiBase}/degree`)

// ใช้ useFetch หรือ useAsyncData เพื่อดึงข้อมูลของบุคลากรตาม id
// *** อย่าลืมเปลี่ยน '/api/personnel/' เป็น Endpoint จริงของคุณ ***
const { data: person, pending, error } = await useAsyncData<Professor>(
    `person-${personId}`,
    () => $fetch(`${apiBase}/professor/${personId}`) // สมมติว่านี่คือ API Endpoint ของคุณ
)

// ถ้าไม่พบข้อมูลหรือเกิด Error ให้แสดงหน้า 404
if (error.value) {
    showError({ statusCode: 404, statusMessage: 'ไม่พบข้อมูลบุคลากร' })
}

// --- 2. การเตรียมข้อมูลสำหรับแสดงผล ---
const nameFallback = computed(() => {
    return person.value?.profile?.nameTh?.split(' ').map((n: string) => n[0]).join('').toUpperCase() ?? ''
})

const formattedBirthday = computed(() => {
    if (!person.value?.profile?.birthday) return 'ไม่มีข้อมูล'
    const [year, month, day] = person.value.profile.birthday
    return new Date(year, month - 1, day).toLocaleDateString('th-TH', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
    })
})

const qualification = ref<ProfessorQualification>({
    university: '',
    major: '',
    yearGraduation: '',
    degree: undefined
})

async function addQualification(data: ProfessorQualification) {
    if (data != null && data != undefined) {
        const qualification2: ProfessorQualification = {
            university: qualification.value.university,
            major: qualification.value.major,
            yearGraduation: qualification.value.yearGraduation,
            degree: data.degree
        }
        person.value?.qualifications?.push(qualification2)
    }
    qualification.value = {
        university: '',
        major: '',
        yearGraduation: '',
        degree: undefined
    }
}

async function removeQualification(data: ProfessorQualification) {
    if (!data) return;

    const qualifications = person.value?.qualifications
    if (!qualifications) return;

    person.value.qualifications = qualifications.filter(
        (item) => item !== data
    );
}

// URL สำหรับรูปภาพ (อย่าลืมเปลี่ยน)
const imageUrl = computed(() => `${apiBase}/images/${person.value?.profile?.image ?? ''}`)

</script>

<template>
    <div class="container mx-auto p-4">

        <div v-if="pending" class="grid grid-cols-1 lg:grid-cols-12 gap-8">
            <aside class="lg:col-span-4 xl:col-span-3">
                <div class="sticky top-24 p-6 rounded-2xl border border-gray-200 dark:border-gray-700 space-y-4">
                    <Skeleton class="w-32 h-32 rounded-full mx-auto" />
                    <Skeleton class="h-8 w-3/4 mx-auto" />
                    <Skeleton class="h-6 w-1/2 mx-auto" />
                    <Skeleton class="h-12 w-full mt-4" />
                    <Separator class="my-6" />
                    <Skeleton class="h-6 w-1/3" />
                    <Skeleton class="h-5 w-full" />
                    <Skeleton class="h-5 w-full" />
                </div>
            </aside>
            <main class="lg:col-span-8 xl:col-span-9 space-y-6">
                <Skeleton class="h-10 w-full" />
                <div class="p-6 rounded-2xl border border-gray-200 dark:border-gray-700 space-y-6">
                    <Skeleton class="h-8 w-1/4" />
                    <Skeleton class="h-6 w-full" />
                    <Skeleton class="h-6 w-full" />
                    <Skeleton class="h-6 w-full" />
                </div>
            </main>
        </div>

        <div v-else-if="person" class="grid grid-cols-1 lg:grid-cols-12 gap-8">

            <aside class="lg:col-span-4 xl:col-span-3">
                <div
                    class="sticky top-24 bg-white dark:bg-gray-800/50 p-6 rounded-2xl shadow-sm border border-gray-200 dark:border-gray-700">
                    <Avatar class="w-32 h-32 mx-auto mb-4 border-4 border-white dark:border-gray-700 shadow-lg">
                        <AvatarImage :src="imageUrl" :alt="person.profile.nameTh" class="object-cover" />
                        <AvatarFallback
                            class="bg-gray-200 dark:bg-gray-600 text-4xl font-semibold text-gray-600 dark:text-gray-300">
                            {{ nameFallback }}
                        </AvatarFallback>
                    </Avatar>

                    <div class="text-center">
                        <h1 class="text-2xl font-bold text-gray-900 dark:text-white">{{ person.profile.nameTh }}</h1>
                        <p v-if="person.positions.length > 0"
                            class="mt-1 text-base font-medium text-sky-800 dark:text-sky-400">
                            {{ person.positions[0].nameTh }}
                        </p>
                        <p class="mt-1 text-sm text-gray-500 dark:text-gray-400">{{ person.profile?.major }}</p>
                    </div>

                    <div class="mt-6 flex justify-center items-center gap-x-3">

                        <TooltipProvider>
                            <Tooltip>
                                <TooltipTrigger as-child>
                                    <a :href="`mailto:${person.email}`">
                                        <Button class="cursor-pointer" variant="outline" size="icon">
                                            <Mail class="size-5" />
                                        </Button>
                                    </a>
                                </TooltipTrigger>
                                <TooltipContent>
                                    <p>ติดต่อทางอีเมล</p>
                                </TooltipContent>
                            </Tooltip>
                        </TooltipProvider>

                        <TooltipProvider v-if="person.profile?.phone">
                            <Tooltip>
                                <TooltipTrigger as-child>
                                    <a :href="`tel:${person.profile.phone}`">
                                        <Button class="cursor-pointer" variant="outline" size="icon">
                                            <Smartphone class="size-5" />
                                        </Button>
                                    </a>
                                </TooltipTrigger>
                                <TooltipContent>
                                    <p>{{ person.profile.phone }}</p>
                                </TooltipContent>
                            </Tooltip>
                        </TooltipProvider>

                        <TooltipProvider v-if="person.profile?.git">
                            <Tooltip>
                                <TooltipTrigger as-child>
                                    <a :href="person.profile.git" target="_blank">
                                        <Button class="cursor-pointer" variant="outline" size="icon">
                                            <Github class="size-5" />
                                        </Button>
                                    </a>
                                </TooltipTrigger>
                                <TooltipContent>
                                    <p>GitHub Profile</p>
                                </TooltipContent>
                            </Tooltip>
                        </TooltipProvider>

                    </div>


                    <Separator class="my-6" />

                    <div>
                        <h2 class="text-lg font-semibold mb-3">ตำแหน่งทั้งหมด</h2>
                        <ul class="space-y-2 text-sm">
                            <li v-for="pos in person.positions" :key="pos.id" class="flex items-start gap-3">
                                <CheckCircle2 class="size-4 mt-0.5 text-sky-600 shrink-0" />
                                <span class="text-gray-700 dark:text-gray-300">{{ pos.nameTh }}</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </aside>

            <main class="lg:col-span-8 xl:col-span-9">
                <Tabs default-value="info" class="w-full">
                    <TabsList class="grid w-full grid-cols-2 bg-gray-100 dark:bg-gray-800">
                        <TabsTrigger value="info">
                            <Info class="size-4 mr-2" />ข้อมูลทั่วไป
                        </TabsTrigger>
                        <TabsTrigger value="education">
                            <GraduationCap class="size-4 mr-2" />วุฒิการศึกษา
                        </TabsTrigger>
                    </TabsList>

                    <TabsContent value="info" class="mt-6">
                        <div
                            class="p-6 bg-white dark:bg-gray-800/50 rounded-2xl border border-gray-200 dark:border-gray-700 shadow-sm">

                            <h3 class="text-xl font-semibold text-gray-800 dark:text-white mb-6">ข้อมูลส่วนตัว</h3>
                            <dl class="space-y-4">
                                <div class="grid grid-cols-1 sm:grid-cols-3 gap-x-4 gap-y-1">
                                    <dt
                                        class="text-sm font-medium text-gray-500 dark:text-gray-400 flex items-center gap-2">
                                        <User class="size-4" /> ชื่อ-สกุล
                                    </dt>
                                    <dd class="sm:col-span-2 text-sm text-gray-900 dark:text-white">{{
                                        person.profile.nameTh }} ({{ person.profile?.nameEn }})</dd>
                                </div>
                                <div class="grid grid-cols-1 sm:grid-cols-3 gap-x-4 gap-y-1">
                                    <dt
                                        class="text-sm font-medium text-gray-500 dark:text-gray-400 flex items-center gap-2">
                                        <Landmark class="size-4" /> ตำแหน่งทางวิชาการ
                                    </dt>
                                    <dd class="sm:col-span-2 text-sm text-gray-900 dark:text-white">{{
                                        person.profile?.title.nameTh }}</dd>
                                </div>
                                <div class="grid grid-cols-1 sm:grid-cols-3 gap-x-4 gap-y-1">
                                    <dt
                                        class="text-sm font-medium text-gray-500 dark:text-gray-400 flex items-center gap-2">
                                        <Briefcase class="size-4" /> สาขาวิชาหลัก
                                    </dt>
                                    <dd class="sm:col-span-2 text-sm text-gray-900 dark:text-white">{{
                                        person.profile.major }}</dd>
                                </div>
                                <div class="grid grid-cols-1 sm:grid-cols-3 gap-x-4 gap-y-1">
                                    <dt
                                        class="text-sm font-medium text-gray-500 dark:text-gray-400 flex items-center gap-2">
                                        <Calendar class="size-4" /> วันเกิด
                                    </dt>
                                    <dd class="sm:col-span-2 text-sm text-gray-900 dark:text-white">{{ formattedBirthday
                                        }}</dd>
                                </div>
                            </dl>

                            <Separator class="my-8" />

                            <h3 class="text-xl font-semibold text-gray-800 dark:text-white mb-6">ข้อมูลติดต่อ</h3>
                            <dl class="space-y-4">
                                <div class="grid grid-cols-1 sm:grid-cols-3 gap-x-4 gap-y-1">
                                    <dt
                                        class="text-sm font-medium text-gray-500 dark:text-gray-400 flex items-center gap-2">
                                        <Mail class="size-4" /> อีเมล
                                    </dt>
                                    <dd class="sm:col-span-2 text-sm text-gray-900 dark:text-white">{{ person.email }}
                                    </dd>
                                </div>
                                <div class="grid grid-cols-1 sm:grid-cols-3 gap-x-4 gap-y-1">
                                    <dt
                                        class="text-sm font-medium text-gray-500 dark:text-gray-400 flex items-center gap-2">
                                        <Smartphone class="size-4" /> เบอร์ติดต่อ
                                    </dt>
                                    <dd class="sm:col-span-2 text-sm text-gray-900 dark:text-white">{{
                                        person.profile?.phone || '-' }}</dd>
                                </div>
                                <div class="grid grid-cols-1 sm:grid-cols-3 gap-x-4 gap-y-1">
                                    <dt
                                        class="text-sm font-medium text-gray-500 dark:text-gray-400 flex items-center gap-2">
                                        <Github class="size-4" /> Git Repository
                                    </dt>
                                    <dd class="sm:col-span-2 text-sm text-gray-900 dark:text-white hover:text-sky-600">
                                        <a :href="person.profile?.git" target="_blank">{{ person.profile?.git || '-'
                                            }}</a>
                                    </dd>
                                </div>
                            </dl>

                        </div>
                    </TabsContent>

                    <TabsContent value="education" class="mt-6">
                        <div
                            class="p-6 bg-white dark:bg-gray-800/50 rounded-2xl border border-gray-200 dark:border-gray-700 shadow-sm">
                            <div class="flex items-center justify-between mb-6">
                                <h3 class="text-xl font-semibold">ประวัติการศึกษา</h3>
                                <Dialog>
                                    <DialogTrigger as-child>
                                        <Button class="gap-2">
                                            <Plus class="size-4" /> เพิ่มประวัติ
                                        </Button>
                                    </DialogTrigger>
                                    <DialogContent class="sm:max-w-lg">
                                        <DialogHeader>
                                            <DialogTitle>เพิ่มประวัติการศึกษา</DialogTitle>
                                            <DialogDescription>กรอกข้อมูลวุฒิการศึกษาให้ครบถ้วน</DialogDescription>
                                        </DialogHeader>
                                        <div class="grid gap-4 py-4">
                                            <div class="grid grid-cols-4 items-center gap-4">
                                                <Label for="degree" class="text-right">ระดับการศึกษา</Label>
                                                <Select v-model="qualification.degree">
                                                    <SelectTrigger class="col-span-3">
                                                        <SelectValue placeholder="-- เลือกระดับ --" />
                                                    </SelectTrigger>
                                                    <SelectContent>
                                                        <SelectItem v-for="degree in degrees" :key="degree.id"
                                                            :value="degree">{{ degree.nameTh }}</SelectItem>
                                                    </SelectContent>
                                                </Select>
                                            </div>
                                            <div class="grid grid-cols-4 items-center gap-4">
                                                <Label for="university" class="text-right">มหาวิทยาลัย</Label>
                                                <Input id="university" v-model="qualification.university"
                                                    class="col-span-3" />
                                            </div>
                                            <div class="grid grid-cols-4 items-center gap-4">
                                                <Label for="major" class="text-right">สาขาวิชา</Label>
                                                <Input id="major" v-model="qualification.major" class="col-span-3" />
                                            </div>
                                            <div class="grid grid-cols-4 items-center gap-4">
                                                <Label for="year" class="text-right">ปีที่จบ (ค.ศ.)</Label>
                                                <Input id="year" v-model="qualification.yearGraduation" type="number"
                                                    class="col-span-3" />
                                            </div>
                                        </div>
                                        <DialogFooter>
                                            <Button type="submit" @click="addQualification(qualification)"
                                                :disabled="isSubmitting">
                                                <Loader2 v-if="isSubmitting" class="mr-2 h-4 w-4 animate-spin" />
                                                {{ isSubmitting ? 'กำลังบันทึก...' : 'เพิ่มข้อมูล' }}
                                            </Button>
                                        </DialogFooter>
                                    </DialogContent>
                                </Dialog>
                            </div>

                            <div class="space-y-4">
                                <div v-for="q in person.qualifications" :key="q.id"
                                    class="flex items-center justify-between p-4 rounded-lg bg-gray-50 dark:bg-gray-900/50 border border-gray-200 dark:border-gray-700">
                                    <div class="flex items-center space-x-4">
                                        <div class="flex-shrink-0 text-sky-500">
                                            <GraduationCap class="w-6 h-6" />
                                        </div>
                                        <div>
                                            <h4 class="font-semibold text-gray-800 dark:text-gray-100">{{
                                                q.degree.nameTh }} - {{ q.major }}</h4>
                                            <p class="text-sm text-gray-600 dark:text-gray-300">{{ q.university }} •
                                                จบปี {{ q.yearGraduation }}</p>
                                        </div>
                                    </div>

                                    <Dialog>
                                        <DialogTrigger as-child>
                                            <Button variant="ghost" size="icon"
                                                class="text-red-500 hover:bg-red-100 dark:hover:bg-red-900/50 cursor-pointer">
                                                <Trash2 class="size-4" />
                                            </Button>
                                        </DialogTrigger>
                                        <DialogContent>
                                            <DialogHeader>
                                                <DialogTitle>ยืนยันการลบข้อมูล?</DialogTitle>
                                                <DialogDescription>
                                                    คุณต้องการลบข้อมูลประวัติการศึกษา '{{ q.degree.nameTh }} - {{
                                                    q.major }}' ใช่หรือไม่? การกระทำนี้ไม่สามารถย้อนกลับได้
                                                </DialogDescription>
                                            </DialogHeader>
                                            <DialogClose as-child>
                                                <DialogFooter>
                                                    <Button @click="removeQualification(q)"
                                                        class="bg-red-600 hover:bg-red-700 cursor-pointer">ยืนยันการลบ</Button>
                                                </DialogFooter>
                                            </DialogClose>

                                        </DialogContent>
                                    </Dialog>
                                </div>

                                <div v-if="person.qualifications.length === 0"
                                    class="text-center py-10 px-6 border-2 border-dashed rounded-lg text-gray-500 dark:text-gray-400">
                                    <GraduationCap class="mx-auto h-12 w-12 text-gray-400" />
                                    <h3 class="mt-2 text-sm font-semibold">ไม่มีข้อมูลวุฒิการศึกษา</h3>
                                    <p class="mt-1 text-sm">กดปุ่ม "เพิ่มประวัติ" เพื่อเริ่มเพิ่มข้อมูล</p>
                                </div>
                            </div>
                        </div>
                    </TabsContent>
                </Tabs>
            </main>
        </div>
    </div>
</template>