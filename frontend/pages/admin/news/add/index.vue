<script setup lang="ts">

import FileUploader from '~/components/FileUploader.vue';
import CardContent from '~/components/ui/card/CardContent.vue';

import { Save } from 'lucide-vue-next';
import type { NewsRequest } from '~/types/NewsRequest';

definePageMeta({
  middleware: 'auth',
  roles: ['ผู้ดูแล','รองคณะบดีคณะเทคโนโลยีสารสนเทศ']
})

const token = useCookie('token')

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const news = ref<NewsRequest>({
    title: '',
    detail: '',
    linkName: '',
    link: '',
    images: []
})

const ImageFile = ref<File[] | null>(null)

async function submitForm() {
    
    const formData = new FormData()
    formData.append('title', news.value?.title ?? '')
    formData.append('linkName', news.value?.linkName ?? '')
    formData.append('link', news.value?.link ?? '')
    formData.append('detail', news.value?.detail ?? '')

    if (Array.isArray(ImageFile.value) && ImageFile.value.length > 0) {
        ImageFile.value.forEach((file) => {
            formData.append('images', file);
        });
    }

    await $fetch(`${apiBase}/news`,{
        method: 'POST',
        body: formData,
        headers:{
            Authorization: `Bearer ${token.value}`
        },
    })

    ImageFile.value = null

    await navigateTo('/admin/news')
    
}

</script>

<template>
    <div class="container mx-auto p-4">

        <Card class="mb-4">
            <CardHeader class="flex items-center justify-between">
                <div>
                    <CardTitle>ระบบจัดการข่าวสาร</CardTitle>
                    <CardDescription>เพิ่มข่าวสารของคณะเทคโนโลยีสารสนเทศ</CardDescription>
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
                                <DialogTitle>เพิ่มข้อมูลข่าวสาร</DialogTitle>
                                <DialogDescription>
                                    คุณต้องการเพิ่มข้อมูลข่าวสารนี้หรือไม่
                                </DialogDescription>
                            </DialogHeader>
                            <DialogClose as-child>
                                <DialogFooter>
                                    <Button type="submit" @click="submitForm">ยืนยัน</Button>
                                </DialogFooter>
                            </DialogClose>
                        </DialogContent>
                    </Dialog>

                </div>
            </CardHeader>
        </Card>

        <Card class="mt-4">
            <CardHeader>
                <Label class="text-xl font-bold">รูปภาพใหม่</Label>
            </CardHeader>
            <CardContent>
                <ClientOnly>
                    <FileUploader v-model="ImageFile" multiple />
                </ClientOnly>
            </CardContent>
        </Card>

        <Card class="mt-4">
            <CardHeader>
                <CardTitle class="flex flex-col gap-3">
                    <Label class="text-xl font-bold">หัวข้อข่าวสาร</Label>
                    <Input type="text" class="font-light" v-model="news.title" />
                </CardTitle>
            </CardHeader>
            <CardContent class="flex flex-col gap-3">
                <Label class="text-xl font-bold">เนื้อหาข่าวสาร</Label>
                <Textarea v-model="news.detail" />
            </CardContent>
            <CardFooter class="grid grid-cols-1 md:grid-cols-2 gap-3">
                <div class="flex flex-col gap-3">
                    <Label class="text-xl font-bold">ชื่อที่มาข่าวสาร</Label>
                    <Input type="text" class="font-light" v-model="news.linkName" />
                </div>
                <div class="flex flex-col gap-3">
                    <Label class="text-xl font-bold">ลิงค์ข่าวสาร</Label>
                    <Input type="text" class="font-light" v-model="news.link" />
                </div>
            </CardFooter>
        </Card>

    </div>
</template>