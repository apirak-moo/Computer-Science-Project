<script setup lang="ts">

import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from '@/components/ui/breadcrumb'
import type { News } from '~/types/News'

const route = useRoute()

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const { data: news } = await useAsyncData<News>('news', () => {
  return $fetch(`${apiBase}/news/${route.params.id}`)
})

</script>

<template>
  <div>
    <Breadcrumb>
    <BreadcrumbList>

      <BreadcrumbItem>
        <NuxtLink to="/">หน้าแรก</NuxtLink>
      </BreadcrumbItem>

      <BreadcrumbSeparator />

      <BreadcrumbItem>
        <BreadcrumbPage>ข่าวสาร</BreadcrumbPage>
      </BreadcrumbItem>

      <BreadcrumbSeparator />

      <BreadcrumbItem>
        <BreadcrumbPage>{{ news?.title }}</BreadcrumbPage>
      </BreadcrumbItem>

    </BreadcrumbList>
  </Breadcrumb>
  </div>
</template>