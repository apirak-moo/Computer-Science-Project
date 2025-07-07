<script setup lang="ts">

import { NuxtImg } from '#components';
import FileUploader from '~/components/FileUploader.vue';
import CardContent from '~/components/ui/card/CardContent.vue';
import type { News } from '~/types/News';

import { Save, Trash2 } from 'lucide-vue-next';

// definePageMeta({
//   middleware: 'auth',
//   roles: ['Administrator', 'Associate Dean of the Faculty of Information Technology']
// })

const token = useCookie('token')

const route = useRoute()

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const { data: news , refresh } = await useFetch<News>(`${apiBase}/news/${route.params.id}`)

const ImageFile = ref<File[] | null>(null)

async function submitForm() {
    const formData = new FormData()
    formData.append('id', String(news.value?.id))
    formData.append('title', news.value?.title ?? '')
    formData.append('linkName', news.value?.linkName ?? '')
    formData.append('link', news.value?.link ?? '')
    formData.append('detail', news.value?.detail ?? '')

    if (Array.isArray(ImageFile.value) && ImageFile.value.length > 0) {
        ImageFile.value.forEach((file) => {
            formData.append('images', file);
        });
    }

    await $fetch(`${apiBase}/news/${news.value?.id}`,{
        method: 'PUT',
        body: formData,
        headers:{
            Authorization: `Bearer ${token.value}`
        },
    })

    ImageFile.value = null

    refresh()
    
}

async function deleteForm() {
    await $fetch(`${apiBase}/news/${news.value?.id}`,{
        method: 'DELETE',
        headers:{
            Authorization: `Bearer ${token.value}`
        },
    })
    await navigateTo('/admin/news')
}

</script>

<template>
    <div class="container mx-auto p-4">

        <Card class="mb-4">
            <CardHeader class="flex items-center justify-between">
                <div>
                    <CardTitle>ระบบจัดการข่าวสาร</CardTitle>
                    <CardDescription>ข่าวสารของคณะเทคโนโลยีสารสนเทศ</CardDescription>
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
                                    คุณต้องเปลี่ยนแปลงข้อมูลข่าวนี้หรือไม่
                                </DialogDescription>
                            </DialogHeader>
                            <DialogClose as-child>
                                <DialogFooter>
                                    <Button type="submit" @click="submitForm">ยืนยัน</Button>
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
                                <DialogTitle>ลบข่าวสาร</DialogTitle>
                                <DialogDescription>
                                    คุณต้องการลบข่าวสารนี้หรือไม่
                                </DialogDescription>
                            </DialogHeader>
                            <DialogClose as-child>
                                <DialogFooter>
                                    <Button type="submit" @click="deleteForm">ยืนยัน</Button>
                                </DialogFooter>
                            </DialogClose>
                        </DialogContent>
                    </Dialog>

                </div>
            </CardHeader>
        </Card>

        <Carousel class="relative w-full">
            <CarouselContent v-if="news?.newsImages.length > 3">
                <CarouselItem class="basis-2/3 md:basis-1/3" v-for="image in news?.newsImages" :key="image.id">
                    <NuxtImg class="rounded-xl" :src="`${apiBase}/images/${image.path}`" :alt="`${image.id}`"
                        width="1920" height="1080" />
                </CarouselItem>
            </CarouselContent>
            <CarouselContent v-else>
                <CarouselItem class="flex justify-center" v-for="image in news?.newsImages" :key="image.id">
                    <NuxtImg class="rounded-xl" :src="`${apiBase}/images/${image.path}`" :alt="`${image.id}`"
                        width="1280" height="720" />
                </CarouselItem>
            </CarouselContent>
            <CarouselPrevious v-if="news?.newsImages.length > 0" />
            <CarouselNext v-if="news?.newsImages.length > 0" />
        </Carousel>

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