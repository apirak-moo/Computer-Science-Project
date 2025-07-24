<script setup lang="ts">
import { computed } from 'vue'
import type { News } from '~/types/News'

// Import components for UI enhancement
import { Badge } from '@/components/ui/badge'
import { Skeleton } from '@/components/ui/skeleton'
import { Icon } from '#components' // Assuming you have an Icon component or use lucide-vue-next

const route = useRoute()
const config = useRuntimeConfig()
const apiBase = config.public.apiBase

// Use `pending` state from useFetch to handle loading
const { data: news, pending } = await useFetch<News>(`${apiBase}/news/${route.params.id}`)

// Format the date for better display
const formattedDate = computed(() => {
  if (news.value?.createdAt) {
    return new Date(news.value.createdAt).toLocaleDateString('th-TH', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
    })
  }
  return ''
})
</script>

<template>
  <div class="container mx-auto max-w-6xl px-4 py-8 md:py-12">
    <div v-if="pending" class="space-y-6">
      <Skeleton class="h-10 w-3/4" />
      <Skeleton class="h-6 w-1/4" />
      <Skeleton class="aspect-video w-full rounded-xl" />
      <div class="space-y-4">
        <Skeleton class="h-4 w-full" />
        <Skeleton class="h-4 w-full" />
        <Skeleton class="h-4 w-5/6" />
      </div>
    </div>

    <article v-else-if="news" class="space-y-6">
      <div class="space-y-3">
        <h1 class="text-3xl font-extrabold tracking-tight text-foreground sm:text-4xl md:text-5xl">
          {{ news.title }}
        </h1>
        <Badge v-if="formattedDate" variant="outline">
          {{ formattedDate }}
        </Badge>
      </div>

      <div v-if="news.newsImages && news.newsImages.length > 0">
        <Carousel class="relative w-full rounded-xl overflow-hidden">
          <CarouselContent>
            <CarouselItem v-for="image in news.newsImages" :key="image.id">
              <div class="aspect-video bg-muted">
                <NuxtImg
                  class="w-full h-full object-cover"
                  :src="`${apiBase}/images/${image.path}`"
                  :alt="news.title"
                  width="1280"
                  height="720"
                  format="webp"
                  quality="80"
                  loading="lazy"
                />
              </div>
            </CarouselItem>
          </CarouselContent>
          <CarouselPrevious class="absolute left-4" />
          <CarouselNext class="absolute right-4" />
        </Carousel>
      </div>

      <div v-if="news.detail && news.detail !== 'ไม่มี'" class="prose prose-lg max-w-none text-foreground/80">
        <p class="leading-relaxed">{{ news.detail }}</p>
      </div>

      <div v-if="news.link && news.linkName !== 'ไม่มี'">
        <a
          :href="news.link"
          target="_blank"
          rel="noopener noreferrer"
          class="inline-flex items-center gap-2 text-sm font-medium text-primary hover:text-primary/80 transition-colors"
        >
          <Icon name="lucide:link" class="h-4 w-4" />
          <span>{{ news.linkName }}</span>
        </a>
      </div>
    </article>

    <div v-else class="text-center">
      <h2 class="text-2xl font-semibold">ไม่พบข่าวสาร</h2>
      <p class="text-muted-foreground">ขออภัย ไม่พบข้อมูลข่าวสารที่คุณต้องการ</p>
    </div>
  </div>
</template>