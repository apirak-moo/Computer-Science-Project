<script setup lang="ts">
import { Card, CardContent } from '@/components/ui/card'
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
import { Badge } from '@/components/ui/badge'
import { Separator } from '@/components/ui/separator'
import { ArrowUpRight, CheckCircle2 } from 'lucide-vue-next'

// กำหนด Props ใหม่ โดย `position` เป็น Array ของ string
const props = defineProps({
  id: {
    type: [String, Number],
    required: true,
  },
  name: {
    type: String,
    default: 'ไม่มีชื่อ',
  },
  // ปรับ position ให้เป็น Array ของ string
  positions: {
    type: Array as () => string[],
    default: () => ['ไม่มีตำแหน่ง'],
  },
  imageUrl: {
    type: String,
    default: '/dfimage.svg', // รูปภาพเริ่มต้น
  },
  specialties: {
    type: Array as () => string[],
    default: () => [],
  },
})

// ดึงชื่อย่อสำหรับ Avatar Fallback
const nameFallback = computed(() => {
  return props.name.split(' ').map(n => n[0]).join('').toUpperCase()
})

// --- ส่วนที่เพิ่มเข้ามา ---
// 1. เลือกแสดงตำแหน่งรองแค่ 1 ตำแหน่งแรก (ตำแหน่งที่ 2 ใน array ทั้งหมด)
const displayedSecondaryPositions = computed(() => props.positions.slice(1, 2));

// 2. นับจำนวนตำแหน่งที่เหลือที่ไม่ได้แสดง
const remainingPositionsCount = computed(() => {
  // จำนวนตำแหน่งทั้งหมด ลบด้วยตำแหน่งหลัก (1) และตำแหน่งรองที่แสดง (1)
  const remaining = props.positions.length - 2;
  return remaining > 0 ? remaining : 0;
});
// --- สิ้นสุดส่วนที่เพิ่มเข้ามา ---

</script>

<template>
  <NuxtLink
    :to="`/admin/personnel/${id}`"
    class="group block rounded-2xl focus:outline-none focus:ring-2 focus:ring-sky-500 focus:ring-offset-2"
  >
    <Card class="h-full overflow-hidden rounded-2xl bg-white dark:bg-gray-800/50 shadow-md transition-all duration-300 ease-in-out hover:shadow-xl hover:-translate-y-1.5 hover:shadow-sky-200/50 dark:hover:shadow-sky-800/20">
      <CardContent class="p-6 text-center flex flex-col h-full">
        <div class="flex-grow">
          <Avatar class="w-28 h-28 mx-auto mb-4 border-4 border-white dark:border-gray-700 shadow-lg">
            <AvatarImage :src="imageUrl" :alt="name" class="object-cover" />
            <AvatarFallback class="bg-gray-200 dark:bg-gray-600 text-2xl font-semibold text-gray-600 dark:text-gray-300">
              {{ nameFallback }}
            </AvatarFallback>
          </Avatar>

          <h3 class="text-xl font-bold text-gray-900 dark:text-white transition-colors group-hover:text-sky-700 dark:group-hover:text-sky-500">
            {{ name }}
          </h3>

          <p v-if="positions.length > 0" class="mt-1 text-sm font-medium text-sky-800 dark:text-sky-400">
            {{ positions[0] }}
          </p>

          <ul v-if="displayedSecondaryPositions.length > 0" class="mt-2 text-xs text-gray-500 dark:text-gray-400 space-y-1">
            <li v-for="pos in displayedSecondaryPositions" :key="pos" class="flex items-center justify-center gap-1.5">
              <CheckCircle2 class="size-3 text-gray-400 dark:text-gray-500" />
              <span>{{ pos }}</span>
            </li>
          </ul>

          <div v-if="remainingPositionsCount > 0" class="mt-1.5 text-xs text-gray-400 dark:text-gray-500">
            (+{{ remainingPositionsCount }} ตำแหน่งอื่น)
          </div>

          <Separator class="my-5" />

          <div v-if="specialties.length > 0" class="flex flex-wrap justify-center gap-2">
            <Badge
              v-for="tag in specialties" :key="tag"
              variant="secondary"
              class="cursor-pointer"
            >
              {{ tag }}
            </Badge>
          </div>
          <div v-else class="text-xs text-gray-400 dark:text-gray-500">
            ไม่มีข้อมูลความเชี่ยวชาญ
          </div>
        </div>

        <div class="mt-6 -mx-6 -mb-6 p-4 bg-gray-50 dark:bg-gray-800/70 rounded-b-2xl transition-colors group-hover:bg-sky-50 dark:group-hover:bg-sky-900/50">
          <p class="flex items-center justify-center gap-2 text-sm font-semibold text-sky-800 dark:text-sky-400">
            <span>ดูโปรไฟล์</span>
            <ArrowUpRight class="size-4 transition-transform group-hover:translate-x-0.5 group-hover:-translate-y-0.5" />
          </p>
        </div>
      </CardContent>
    </Card>
  </NuxtLink>
</template>