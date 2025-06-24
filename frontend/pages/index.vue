<script setup lang="ts">

// Import Swiper Vue components
import { Swiper, SwiperSlide } from 'swiper/vue'

// Import Swiper styles
import 'swiper/css'
import { Navigation, Pagination as SwiperPagination, Autoplay } from 'swiper/modules'

// Import Date Formate Library
import { format } from 'date-fns'

import {
    Pagination,
    PaginationContent,
    PaginationEllipsis,
    PaginationItem,
    PaginationNext,
    PaginationPrevious,
} from '@/components/ui/pagination'
import type { News } from '~/types/News'
import type { PaginatedResponse } from '~/types/PaginatedResponse'
import { Skeleton } from '@/components/ui/skeleton'

useHead({
    title: 'คณะเทคโนโลยีสารสนเทศ มรภ.เพชรบุรี',
    htmlAttrs: {
        lang: 'th'
    },
    meta: [
        { name: 'description', content: 'รายละเอียดหลักสูตรวิทยาการคอมพิวเตอร์ ระดับปริญญาตรี ระดับปริญญาโท ระดับปริญญาเอก มหาวิทยาลัยราชภัฏเพชรบุรี' },
        { name: 'keywords', content: 'วิทยาการคอมพิวเตอร์, เทคโนโลยีสารสนเทศ, มรภ.เพชรบุรี, IT PBRU, Computer Science' },
        { property: 'og:title', content: 'IT PBRU' },
        { property: 'og:description', content: 'ศึกษาหลักสูตรวิทยาการคอมพิวเตอร์ที่คณะเทคโนโลยีสารสนเทศ มรภ.เพชรบุรี' },
        { property: 'og:image', content: '/logoIT.png' },
        { property: 'og:type', content: 'website' }
    ],
    link: [
        { rel: 'canonical', href: 'https://it.pbru.ac.th/programs/computer-science' }
    ]
})

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const currentPage = ref<number>(1);
const itemsPerPage: number = 4;

watch([currentPage], () => {
    refresh();
});

const { data: news, status, refresh } = await useFetch<PaginatedResponse<News>>(`${apiBase}/news`, {
    method: 'GET',
    query: computed(() => ({
        page: currentPage.value - 1,
        size: itemsPerPage,
        sort: 'createdAt,desc'
    }))
})

</script>

<template>
    <div class="container mx-auto p-5">

        <div class="flex items-center justify-center bg-white">

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6 md:gap-20 w-full max-w-7xl px-6">

                <!-- Text Content -->
                <div class="flex flex-col justify-center items-start text-center md:text-left">
                    <h1 class="text-4xl md:text-7xl font-bold mb-4 leading-tight text-gray-800">
                        ยินดีต้อนรับนักศึกษา
                    </h1>
                    <p class="md:text-2xl text-gray-600">
                        ปลดปล่อยศักยภาพทางเทคโนโลยีของคุณ
                    </p>
                    <p class="md:text-2xl text-gray-600">
                        ไปกับหลักสูตรที่ทันสมัยและตอบโจทย์ทุกความสนใจ
                    </p>
                </div>

                <!-- Swiper Images -->
                <ClientOnly>
                    <div class="flex items-center justify-center">
                        <Swiper class="w-full max-w-lg" :modules="[Navigation, SwiperPagination, Autoplay]"
                            :slides-per-view="1" :space-between="30" :loop="true"
                            :autoplay="{ delay: 2500, disableOnInteraction: false }" :pagination="{ clickable: true }"
                            :navigation="true" :lazy="true" :breakpoints="{
                                640: { slidesPerView: 1 },
                                768: { slidesPerView: 1 },
                                1024: { slidesPerView: 1 }
                            }" a11y>
                            <SwiperSlide class="flex justify-center">
                                <NuxtImg width="400" height="400" src="/269257865_11242840.svg" format="webp"
                                    densities="x1 x2" alt="Image 1" class="swiper-lazy" />
                            </SwiperSlide>

                            <SwiperSlide class="flex justify-center">
                                <NuxtImg width="400" height="400" src="/12980818_5102165.png" format="webp"
                                    densities="x1 x2" alt="Image 2" class="swiper-lazy" />
                            </SwiperSlide>

                            <SwiperSlide class="flex justify-center">
                                <NuxtImg width="400" height="400" src="/11436087_4709727.svg" format="webp"
                                    densities="x1 x2" alt="Image 3" class="swiper-lazy" />
                            </SwiperSlide>

                            <SwiperSlide class="flex justify-center">
                                <NuxtImg width="400" height="400" src="/11435030_4706237.svg" format="webp"
                                    densities="x1 x2" alt="Image 4" class="swiper-lazy"  />
                            </SwiperSlide>
                        </Swiper>
                    </div>
                </ClientOnly>

            </div>

        </div>

        <!-- แนะนำหลักสูตร -->
        <div class="mt-16 md:mt-24 px-4">
            <div class="text-center text-3xl md:text-4xl font-bold text-gray-800 mb-12">หลักสูตร</div>
            <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
                <NuxtLink to="/degree"
                    class="flex flex-col items-start p-6 bg-white shadow-lg rounded-2xl hover:shadow-xl hover:ring-2 hover:ring-sky-300 transition-all duration-300 group">
                    <div class="flex items-center justify-between w-full mb-4">
                        <div class="text-sky-500 bg-sky-100 p-3 rounded-full">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                class="h-6 w-6 stroke-current transition-transform duration-300 group-hover:scale-110">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M4.26 10.147a60.438 60.438 0 0 0-.491 6.347A48.62 48.62 0 0 1 12 20.904a48.62 48.62 0 0 1 8.232-4.41 60.46 60.46 0 0 0-.491-6.347m-15.482 0a50.636 50.636 0 0 0-2.658-.813A59.906 59.906 0 0 1 12 3.493a59.903 59.903 0 0 1 10.399 5.84c-.896.248-1.783.52-2.658.814m-15.482 0A50.717 50.717 0 0 1 12 13.489a50.702 50.702 0 0 1 7.74-3.342M6.75 15a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5Zm0 0v-3.675A55.378 55.378 0 0 1 12 8.443m-7.007 11.55A5.981 5.981 0 0 0 6.75 15.75v-1.5" />
                            </svg>
                        </div>
                        <span class="text-gray-600 text-sm">หลักสูตร</span>
                    </div>
                    <h1 class="text-2xl font-semibold text-gray-800 group-hover:text-sky-500">4</h1>
                    <p class="text-sm text-gray-600 mt-1">หลักสูตรมาตรฐาน</p>
                </NuxtLink>

                <NuxtLink to="/major"
                    class="flex flex-col items-start p-6 bg-white shadow-lg rounded-2xl hover:shadow-xl hover:ring-2 hover:ring-sky-300 transition-all duration-300 group">
                    <div class="flex items-center justify-between w-full mb-4">
                        <div class="text-sky-500 bg-sky-100 p-3 rounded-full">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                class="h-6 w-6 stroke-current transition-transform duration-300 group-hover:scale-110">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M9.813 15.904 9 18.75l-.813-2.846a4.5 4.5 0 0 0-3.09-3.09L2.25 12l2.846-.813a4.5 4.5 0 0 0 3.09-3.09L9 5.25l.813 2.846a4.5 4.5 0 0 0 3.09 3.09L15.75 12l-2.846.813a4.5 4.5 0 0 0-3.09 3.09ZM18.259 8.715 18 9.75l-.259-1.035a3.375 3.375 0 0 0-2.455-2.456L14.25 6l1.036-.259a3.375 3.375 0 0 0 2.455-2.456L18 2.25l.259 1.035a3.375 3.375 0 0 0 2.456 2.456L21.75 6l-1.035.259a3.375 3.375 0 0 0-2.456 2.456ZM16.894 20.567 16.5 21.75l-.394-1.183a2.25 2.25 0 0 0-1.423-1.423L13.5 18.75l1.183-.394a2.25 2.25 0 0 0 1.423-1.423l.394-1.183.394 1.183a2.25 2.25 0 0 0 1.423 1.423l1.183.394-1.183.394a2.25 2.25 0 0 0-1.423 1.423Z" />
                            </svg>
                        </div>
                        <span class="text-gray-600 text-sm">สาขาวิชา</span>
                    </div>
                    <h1 class="text-2xl font-semibold text-gray-800 group-hover:text-sky-500">3</h1>
                    <p class="text-sm text-gray-600 mt-1">ตอบโจทย์ตลาดแรงงานความต้องการสูง</p>
                </NuxtLink>

                <NuxtLink to="/branch"
                    class="flex flex-col items-start p-6 bg-white shadow-lg rounded-2xl hover:shadow-xl hover:ring-2 hover:ring-sky-300 transition-all duration-300 group">
                    <div class="flex items-center justify-between w-full mb-4">
                        <div class="text-sky-500 bg-sky-100 p-3 rounded-full">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                class="h-6 w-6 stroke-current transition-transform duration-300 group-hover:scale-110">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M14.25 6.087c0-.355.186-.676.401-.959.221-.29.349-.634.349-1.003 0-1.036-1.007-1.875-2.25-1.875s-2.25.84-2.25 1.875c0 .369.128.713.349 1.003.215.283.401.604.401.959v0a.64.64 0 0 1-.657.643 48.39 48.39 0 0 1-4.163-.3c.186 1.613.293 3.25.315 4.907a.656.656 0 0 1-.658.663v0c-.355 0-.676-.186-.959-.401a1.647 1.647 0 0 0-1.003-.349c-1.036 0-1.875 1.007-1.875 2.25s.84 2.25 1.875 2.25c.369 0 .713-.128 1.003-.349.283-.215.604-.401.959-.401v0c.31 0 .555.26.532.57a48.039 48.039 0 0 1-.642 5.056c1.518.19 3.058.309 4.616.354a.64.64 0 0 0 .657-.643v0c0-.355-.186-.676-.401-.959a1.647 1.647 0 0 1-.349-1.003c0-1.035 1.008-1.875 2.25-1.875 1.243 0 2.25.84 2.25 1.875 0 .369-.128.713-.349 1.003-.215.283-.4.604-.4.959v0c0 .333.277.599.61.58a48.1 48.1 0 0 0 5.427-.63 48.05 48.05 0 0 0 .582-4.717.532.532 0 0 0-.533-.57v0c-.355 0-.676.186-.959.401-.29.221-.634.349-1.003.349-1.035 0-1.875-1.007-1.875-2.25s.84-2.25 1.875-2.25c.37 0 .713.128 1.003.349.283.215.604.401.96.401v0a.656.656 0 0 0 .658-.663 48.422 48.422 0 0 0-.37-5.36c-1.886.342-3.81.574-5.766.689a.578.578 0 0 1-.61-.58v0Z" />
                            </svg>
                        </div>
                        <span class="text-gray-600 text-sm">แขนงวิชา</span>
                    </div>
                    <h1 class="text-2xl font-semibold text-gray-800 group-hover:text-sky-500">6</h1>
                    <p class="text-sm text-gray-600 mt-1">แขนงวิชาที่เป็นตัวคุณ</p>
                </NuxtLink>
            </div>
        </div>

        <div class="mt-16 md:mt-24 px-4">

            <div class="text-center text-3xl md:text-4xl font-bold text-gray-800 mb-12">
                ข่าวสาร
            </div>

            <div class="flex justify-items-center">
                <div v-if="!status" class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                    <!-- Skeleton placeholders จำนวน 12 ชิ้น -->
                    <Skeleton v-for="n in itemsPerPage" :key="n"
                        class="bg-white rounded-2xl shadow-md overflow-hidden flex flex-col animate-pulse">
                        <!-- ภาพ placeholder -->
                        <div class="h-48 w-full bg-gray-300 rounded-t-2xl"></div>
                        <!-- ข้อความ placeholder -->
                        <div class="p-4 flex flex-col justify-between flex-1 space-y-2">
                            <div>
                                <div class="h-6 bg-gray-300 rounded w-3/4 mb-2"></div>
                                <div class="h-4 bg-gray-300 rounded w-full"></div>
                                <div class="h-4 bg-gray-300 rounded w-5/6"></div>
                            </div>
                            <div class="h-4 bg-gray-300 rounded w-1/2 mt-4"></div>
                        </div>
                    </Skeleton>
                </div>

                <div v-else class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-2 lg:grid-cols-4 gap-6">
                    <NuxtLink v-for="(item, index) in news?.content" :key="index" :to="`/news/${item.id}`"
                        class="bg-white rounded-2xl shadow-md hover:shadow-sky-300/60 hover:ring-2 hover:ring-sky-300 hover:-translate-y-1 transition-all duration-300 ease-in-out overflow-hidden group flex flex-col">
                        <template v-if="item.newsImages && item.newsImages.length > 0 && item.newsImages[0]?.path">
                            <NuxtImg :src="`${apiBase}/images/${item.newsImages[0].path}`" :alt="`image_${item.id}`"
                                width="400" height="300" placeholder densities="x1 x2" quality="50"
                                format="webp" loading="lazy"
                                class="w-full h-48 object-cover transition-transform duration-300 ease-in-out group-hover:scale-105" />
                        </template>
                        <template v-else>
                            <div class="w-full h-48 bg-gray-50 flex items-center justify-center text-gray-500">
                                ไม่มีรูปภาพ
                            </div>
                        </template>

                        <div class="p-4 flex flex-col justify-between flex-1">
                            <div>
                                <h1 class="text-lg font-semibold text-sky-700 group-hover:underline line-clamp-2">
                                    {{ item.title }}
                                </h1>
                                <p class="text-gray-600 mt-2 line-clamp-3">
                                    {{ item.title }}
                                </p>
                            </div>
                            <p class="text-sm text-gray-600 mt-4">
                                ลงวันที่ <small>{{ format(new Date(item.createdAt), 'dd/MM/yyyy HH:mm') }}</small>
                            </p>
                        </div>
                    </NuxtLink>
                </div>
            </div>

        </div>

        <div v-if="status" class="flex justify-center my-10">

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
                    <PaginationEllipsis :index="10" />
                    <PaginationNext />
                </PaginationContent>
            </Pagination>

        </div>

    </div>
</template>