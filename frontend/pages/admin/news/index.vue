<script setup lang="ts">
import type { News } from '~/types/News';
import type { PaginatedResponse } from '~/types/PaginatedResponse';
import { format } from 'date-fns'

import {
    Card,
    CardDescription,
    CardHeader,
    CardContent,
    CardTitle,
} from '@/components/ui/card'

// definePageMeta({
//   middleware: 'auth',
//   roles: ['Administrator', 'Associate Dean of the Faculty of Information Technology']
// })

const token = useCookie('token')

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const search = ref('')

const currentPage = ref<number>(1);
const itemsPerPage: number = 12;

watch([currentPage, search], () => {
    refresh();
});

const { data: news, status: newsStatus, refresh } = await useFetch<PaginatedResponse<News>>(`${apiBase}/news`, {
    query: computed(() => ({
        search: search.value,
        page: currentPage.value - 1,
        size: itemsPerPage,
        sort: 'createdAt,desc'
    }))
})

useHead(() => ({
    htmlAttrs: {
        lang: 'th'
    },
    title: `ระบบจัดการข่าวสารและกิจกรรม${currentPage.value > 1 ? ` - หน้า ${currentPage.value}` : ''} | คณะเทคโนโลยีสารสนเทศ ม.ราชภัฏเพชรบุรี`,
    meta: [
        { name: 'description', content: 'ติดตามข่าวสารล่าสุด กิจกรรม ประกาศสำหรับนักศึกษาและบุคลากร จากคณะเทคโนโลยีสารสนเทศ มหาวิทยาลัยราชภัฏเพชรบุรี' },
        // Open Graph (สำหรับ Facebook, Line)
        { property: 'og:title', content: `ข่าวสารและกิจกรรม | คณะเทคโนโลยีสารสนเทศ ม.ราชภัฏเพชรบุรี` },
        { property: 'og:description', content: 'ติดตามข่าวสารล่าสุด กิจกรรม และประกาศต่างๆ ของคณะเทคโนโลยีสารสนเทศ ม.ราชภัฏเพชรบุรี' },
        { property: 'og:type', content: 'website' },
        { property: 'og:url', content: `${apiBase}/news` }, // URL จริงของหน้านี้
        { property: 'og:image', content: `${apiBase}/images/og-image-it-pbru.jpg` }, // รูป Default ของคณะสำหรับแชร์
        // Twitter Card
        { name: 'twitter:card', content: 'summary_large_image' },
        { name: 'twitter:title', content: `ข่าวสารและกิจกรรม | คณะเทคโนโลยีสารสนเทศ ม.ราชภัฏเพชรบุรี` },
        { name: 'twitter:description', content: 'ติดตามข่าวสารล่าสุด กิจกรรม และประกาศต่างๆ ของคณะเทคโนโลยีสารสนเทศ ม.ราชภัฏเพชรบุรี' },
        { name: 'twitter:image', content: `${apiBase}/images/og-image-it-pbru.jpg` }, // รูป Default ของคณะสำหรับแชร์
    ],
    link: [
        // Pagination SEO
        { rel: 'canonical', href: `${apiBase}/news` },
        ...(currentPage.value > 1 ? [{ rel: 'prev', href: `${apiBase}/news?page=${currentPage.value - 1}` }] : []),
        ...(news.value && currentPage.value < news.value.totalPages ? [{ rel: 'next', href: `${apiBase}/news?page=${currentPage.value + 1}` }] : []),
    ],

}))

</script>

<template>
    <div class="container mx-auto p-4">

        <Card class="mb-4">
            <CardHeader>
                <CardTitle>ระบบจัดการข่าวสาร</CardTitle>
                <CardDescription>ข่าวสารของคณะเทคโนโลยีสารสนเทศ</CardDescription>
            </CardHeader>
        </Card>

        <Card class="mt-4">
            <CardContent class="grid grid-cols-6 gap-5">
                <Input class="col-span-4 md:col-span-5" type="text" v-model="search" placeholder="ค้นหาสาขาวิชา" />
                <NuxtLink to="/admin/news/add">
                    <Button class="col-span-2 w-full md:col-span-1 bg-sky-700 hover:bg-sky-800 cursor-pointer">
                        เพิ่มข่าวสาร
                    </Button>
                </NuxtLink>
            </CardContent>

            <CardContent class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 p-4 bg-gray-50 dark:bg-gray-900">

                <div v-if="newsStatus === 'pending'">
                    Loading ...
                </div>

                <NuxtLink v-else v-for="newsItem in news?.content" :key="newsItem.id" :to="`/admin/news/${newsItem.id}`"
                    class="group block rounded-xl overflow-hidden bg-white dark:bg-gray-800 shadow-md hover:shadow-xl hover:-translate-y-1 transition-all duration-300">
                    <div class="aspect-w-16 aspect-h-9">
                        <NuxtImg
                            class="w-full aspect-[6/3] object-cover transition-transform duration-300 ease-in-out group-hover:scale-105"
                            :src="`${apiBase}/images/${newsItem.newsImages[0]?.path ?? ''}`" width="600" height="400"
                            format="webp" quality="80" :alt="newsItem.linkName" :placeholder="'/dfimage.svg'"
                            loading="lazy" />
                    </div>

                    <div class="p-4 sm:p-5">
                        <h1
                            class="text-base sm:text-lg font-semibold text-gray-900 dark:text-white group-hover:text-sky-600 transition-colors duration-300 line-clamp-2">
                            {{ newsItem.title }}
                        </h1>

                        <p class="text-sm text-gray-600 dark:text-gray-400 mt-2 line-clamp-2"
                            v-text="newsItem.detail" />

                        <p class="text-xs text-gray-500 dark:text-gray-400 text-left mt-4">
                            {{ format(new Date(newsItem.createdAt), 'dd/MM/yyyy HH:mm') }}
                        </p>
                    </div>
                </NuxtLink>

            </CardContent>

            <Pagination v-slot="{ page }" v-model:page="currentPage" :items-per-page="itemsPerPage"
                :total="news?.totalElements ?? 0">
                <PaginationContent v-slot="{ items }">
                    <PaginationPrevious />
                    <template v-for="(item, index) in items" :key="index">
                        <PaginationItem v-if="item.type === 'page'" :value="item.value"
                            :is-active="item.value === page">
                            {{ item.value }}
                        </PaginationItem>
                    </template>
                    <PaginationEllipsis :index="4" />
                    <PaginationNext />
                </PaginationContent>
            </Pagination>

        </Card>

    </div>
</template>