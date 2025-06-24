<script setup lang="ts">

import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from '@/components/ui/breadcrumb'
import type { Major } from '~/types/Major'
import type { PaginatedResponse } from '~/types/PaginatedResponse'

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const { data: majors, status } = await useFetch<PaginatedResponse<Major>>(`${apiBase}/major?status=true`)

const search = ref('')

const filteredMajors = computed(() => {
  if (!majors.value?.content) return []

  const keyword = search.value.toLowerCase().trim()

  return majors.value.content.filter(m =>
    m.nameTh.toLowerCase().includes(keyword) ||
    m.nameEn.toLowerCase().includes(keyword)
  )
})

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

useHead({
  title: 'สาขาวิชา | คณะเทคโนโลยีสารสนเทศ มหาวิทยาลัยราชภัฏเพชรบุรี',
  htmlAttrs: {
    lang: 'th'
  },
  meta: [
    {
      name: 'description',
      content: 'สำรวจสาขาวิชาทั้งหมดในคณะเทคโนโลยีสารสนเทศ มหาวิทยาลัยราชภัฏเพชรบุรี ครอบคลุมหลักสูตรภาษาไทย นานาชาติ และสองภาษา พร้อมข้อมูลระดับปริญญา และคุณสมบัติผู้เรียน',
    },
    {
      name: 'keywords',
      content: 'คณะเทคโนโลยีสารสนเทศ, สาขาวิชา, มหาวิทยาลัยราชภัฏเพชรบุรี, หลักสูตรปริญญาตรี, หลักสูตรนานาชาติ, IT, เทคโนโลยี, สารสนเทศ, วิทยาการคอมพิวเตอร์, ซอฟต์แวร์, ระบบสารสนเทศ',
    },
    {
      property: 'og:title',
      content: 'รายการสาขาวิชา | คณะเทคโนโลยีสารสนเทศ มรภ.เพชรบุรี',
    },
    {
      property: 'og:description',
      content: 'ดูข้อมูลสาขาวิชาที่เปิดสอนในคณะเทคโนโลยีสารสนเทศ มหาวิทยาลัยราชภัฏเพชรบุรี พร้อมระบบค้นหาและกรองตามภาษาและระดับปริญญา',
    },
    {
      property: 'og:type',
      content: 'website',
    },
    {
      property: 'og:url',
      content: 'http://localhost:3000/major', // เปลี่ยนเป็น URL จริง
    },
    {
      property: 'og:image',
      content: 'logoIT.png', // ใส่ภาพหน้าปก (ขนาด 1200x630 px)
    },
    {
      name: 'twitter:card',
      content: 'summary_large_image',
    },
    {
      name: 'twitter:title',
      content: 'สาขาวิชาในคณะเทคโนโลยีสารสนเทศ | มรภ.เพชรบุรี',
    },
    {
      name: 'twitter:description',
      content: 'รวมข้อมูลหลักสูตรสาขาวิชาต่าง ๆ ของคณะเทคโนโลยีสารสนเทศ ที่ มหาวิทยาลัยราชภัฏเพชรบุรี',
    },
    {
      name: 'twitter:image',
      content: 'logoIT.png',
    },
  ],
  link: [
    {
      rel: 'canonical',
      href: 'http://localhost:3000/major', // ปรับให้เป็น canonical URL ที่แท้จริง
    },
  ],
})


</script>

<template>
  <div class="container mx-auto p-4 space-y-8">
    <Breadcrumb>
      <BreadcrumbList>

        <BreadcrumbItem>
          <NuxtLink to="/">หน้าแรก</NuxtLink>
        </BreadcrumbItem>

        <BreadcrumbSeparator />

        <BreadcrumbItem>
          <BreadcrumbPage>สาขาวิชา</BreadcrumbPage>
        </BreadcrumbItem>

      </BreadcrumbList>
    </Breadcrumb>

    <h1 class="text-3xl my-5 font-bold text-sky-700 mb-6 text-center">สาขาวิชา</h1>

    <!-- ช่องค้นหา -->
    <div class="flex justify-center mb-6">
      <input type="text" v-model="search" placeholder="ค้นหาสาขาวิชา"
        class="w-full max-w-md px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-sky-400 focus:outline-none" />
    </div>

    <div v-if="majors?.content.length"
      class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
      <NuxtLink v-for="major in filteredMajors" :key="major.id" :to="`/major/${major.id}`"
        class="bg-white rounded-2xl shadow-md hover:shadow-sky-300/60 hover:ring-2 hover:ring-sky-300 transition-all duration-300 ease-in-out overflow-hidden group flex flex-col h-full justify-between">
        <NuxtImg :src="`${apiBase}/images/${major.image}`" width="600" height="400" format="webp" quality="80"
          :alt="major.nameTh" placeholder loading="lazy"
          class="w-full aspect-[6/3] object-cover transition-transform duration-300 ease-in-out group-hover:scale-105" />

        <div class="p-4 flex flex-col justify-between flex-1 gap-3">
          <!-- ชื่อสาขา -->
          <div class="flex flex-col gap-0.5">
            <h3 class="text-base sm:text-lg font-semibold text-sky-700 group-hover:underline line-clamp-1">
              {{ major.nameTh }}
            </h3>
            <h3 class="text-sm sm:text-md font-semibold text-sky-500 group-hover:underline line-clamp-1">
              {{ major.nameEn }}
            </h3>
          </div>

          <!-- Badges -->
          <div class="flex flex-wrap gap-2">
            <Badge
              class="bg-sky-100 text-sky-800 dark:bg-sky-800 dark:text-sky-100 text-xs sm:text-sm px-3 py-1 rounded-full">
              {{ major.program.degree.nameTh }}
            </Badge>
            <Badge
              class="bg-gray-100 text-gray-800 dark:bg-gray-800 dark:text-gray-100 text-xs sm:text-sm px-3 py-1 rounded-full">
              {{ toLanguage(major.language) }}
            </Badge>
          </div>
        </div>
      </NuxtLink>

    </div>
    <div v-else
      class="flex flex-col items-center justify-center min-h-[80vh] bg-white dark:bg-gray-950 py-16 px-4 sm:px-6 lg:px-8 transition-colors duration-300">
      <div class="max-w-md w-full text-center space-y-6">
        <div class="flex justify-center mb-4">
          <svg class="h-20 w-20 text-gray-500 dark:text-gray-400 opacity-80" fill="none" viewBox="0 0 24 24"
            stroke="currentColor" stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round"
              d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
        </div>

        <h2 class="text-3xl font-extrabold text-gray-900 dark:text-white leading-tight">
          หลักสูตรนี้ยังไม่เปิดให้บริการในขณะนี้
        </h2>

        <p class="mt-3 text-lg text-gray-600 dark:text-gray-400">
          ขออภัยในความไม่สะดวก หลักสูตรที่คุณกำลังค้นหาอาจอยู่ในระหว่างการปรับปรุง
          หรือยังไม่พร้อมให้บริการ
        </p>

        <div class="mt-8">
          <NuxtLink to="/courses"
            class="inline-flex items-center px-8 py-3 border border-transparent text-lg font-medium rounded-full shadow-lg text-white bg-sky-700 hover:bg-sky-800 focus:outline-none focus:ring-4 focus:ring-sky-600 focus:ring-opacity-50 transition-all duration-300 ease-in-out transform hover:scale-105">
            <svg class="w-6 h-6 mr-2 -ml-1" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
              <path stroke-linecap="round" stroke-linejoin="round"
                d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M18 12a2 2 0 012 2V7a2 2 0 00-2-2h-2m-3 7a3 3 0 11-6 0 3 3 0 016 0z" />
            </svg>
            ดูหลักสูตรทั้งหมด
          </NuxtLink>
        </div>
      </div>
    </div>

  </div>
</template>