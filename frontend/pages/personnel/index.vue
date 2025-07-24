<script setup lang="ts">
const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const { data: major } = await useAsyncData<string[]>('majorDistinct', () => 
    $fetch<string[]>(`${apiBase}/major/distinct/nameTh`)
);
</script>

<template>
  <div class="container mx-auto p-4">
    <h1 class="text-3xl font-bold text-sky-700 mb-10 text-center">
      เลือกดูอาจารย์ประจำสาขา
    </h1>

    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
      <NuxtLink
        v-for="m in major"
        :key="m"
        :to="`/personnel/${m}`"
        class="block p-6 rounded-xl shadow hover:shadow-md transition duration-300 bg-white border border-gray-200 hover:bg-sky-50 text-center text-lg font-medium text-gray-800 hover:text-sky-700"
      >
        {{ m }}
      </NuxtLink>
    </div>
  </div>
</template>

<style>
/* Optional: Add background if needed */
/* body {
  background-color: #f9fafb;
} */
</style>
