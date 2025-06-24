<template>
  <div class="container mx-auto px-4 py-8">

    <!-- หัวเรื่อง -->
    <h1 class="text-3xl font-bold text-sky-700 mb-6 text-center">ข่าวสาร / ข่าวประชาสัมพันธ์</h1>

    <!-- ช่องค้นหา -->
    <div class="flex justify-center mb-6">
      <input type="text" v-model="searchTerm" placeholder="ค้นหาข่าว เช่น AI, รับสมัคร"
        class="w-full max-w-md px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-sky-400 focus:outline-none" />
    </div>

    <div v-if="!status" class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
      <!-- Skeleton placeholders จำนวน 12 ชิ้น -->
      <Skeleton v-for="n in itemsPerPage || []" :key="n"
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

    <div v-else class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
      <!-- ข่าวจริง -->
      <NuxtLink v-for="(item, index) in news?.content" :key="index" :to="`/news/${item.id}`"
        class="bg-white rounded-2xl shadow-md hover:shadow-sky-300/60 hover:ring-2 hover:ring-sky-300 hover:-translate-y-1 transition-all duration-300 ease-in-out overflow-hidden group flex flex-col">
        <template v-if="item.newsImages && item.newsImages.length > 0 && item.newsImages[0]?.path">
          <NuxtImg :src="`${apiBase}/images/${item.newsImages[0].path}`" :alt="`image_${item.id}`" width="400"
            height="300" placeholder densities="x1 x2" quality="50" format="avif,webp,jpeg,svg" loading="lazy"
            class="w-full h-48 object-cover transition-transform duration-300 ease-in-out group-hover:scale-105" />
        </template>
        <template v-else>
          <div class="w-full h-48 bg-gray-200 flex items-center justify-center text-gray-700">
            ไม่มีรูปภาพ
          </div>
        </template>

        <div class="p-4 flex flex-col justify-between flex-1">
          <div>
            <h1 class="text-lg font-semibold text-sky-700 group-hover:underline line-clamp-2">
              {{ item.title }}
            </h1>
            <p class="text-gray-600 mt-2 line-clamp-3">
              {{ item.detail }}
            </p>
          </div>
          <p class="text-sm text-gray-600 mt-4">
            ลงวันที่ <small>{{ format(new Date(item.createdAt), 'dd/MM/yyyy HH:mm') }}</small>
          </p>
        </div>
      </NuxtLink>
    </div>


    <div class="flex justify-center my-10">

      <Pagination v-slot="{ page }" v-model:page="currentPage" :items-per-page="itemsPerPage"
        :total="news?.totalElements ?? 0">
        <PaginationContent v-slot="{ items }">
          <PaginationPrevious />
          <template v-for="(item, index) in items" :key="index">
            <PaginationItem v-if="item.type === 'page'" :value="item.value" :is-active="item.value === page">
              {{ item.value }}
            </PaginationItem>
          </template>
          <PaginationEllipsis :index="4" />
          <PaginationNext />
        </PaginationContent>
      </Pagination>


    </div>

  </div>
</template>

<script setup lang="ts">
import { format } from 'date-fns'
import type { News } from '~/types/News'
import type { PaginatedResponse } from '~/types/PaginatedResponse'
import { Skeleton } from '@/components/ui/skeleton'

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const searchTerm = ref('')

const currentPage = ref<number>(1);
const itemsPerPage: number = 12;

const paramsString = computed(() => {
  const url = new URLSearchParams()
  url.append('page', (currentPage.value - 1).toString()) // Backend Pageable เป็น 0-based
  url.append('size', itemsPerPage.toString())
  if (searchTerm.value) {
    url.append('search', searchTerm.value)
  }
  return url.toString()
})
// ให้ useFetch ฟัง paramsString
const { data: news, status, error } = useFetch<PaginatedResponse<News>>(
  () => `${apiBase}/news?${paramsString.value}`
)

if (error) {
  console.log(error.value)
}

useHead({
  title: 'ข่าวสาร / ข่าวประชาสัมพันธ์ - คณะเทคโนโลยีสารสนเทศ / มหาวิทยาลัยราชภัฏเพชรบุรี',
  htmlAttrs: {
        lang: 'th'
    },
  meta: [
    {
      name: 'description',
      content: 'ติดตามข่าวสารล่าสุด และข่าวประชาสัมพันธ์จาก คณะเทคโนโลยีสารสนเทศ มหาวิทยาลัยราชภัฏเพชรบุรี ครอบคลุมทุกหัวข้อที่น่าสนใจ เช่น AI, การรับสมัคร, กิจกรรม และอื่นๆ',
    },
    {
      name: 'keywords',
      content: 'ข่าว, ข่าวสาร, ข่าวประชาสัมพันธ์, AI, รับสมัคร, ประกาศ, องค์กร, กิจกรรม, เทคโนโลยี',
    },
    {
      property: 'og:title',
      content: 'ข่าวสาร / ข่าวประชาสัมพันธ์ - คณะเทคโนโลยีสารสนเทศ มหาวิทยาลัยราชภัฏเพชรบุรี',
    },
    {
      property: 'og:description',
      content: 'รวมข่าวสารและประกาศอย่างเป็นทางการจาก คณะเทคโนโลยีสารสนเทศ มหาวิทยาลัยราชภัฏเพชรบุรี',
    },
    {
      property: 'og:type',
      content: 'website',
    },
  ]
})


</script>
