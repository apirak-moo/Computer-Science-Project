<script setup lang="ts">
import type { PaginatedResponse } from '~/types/PaginatedResponse';
import type { Professor } from '~/types/Professor';


const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const { data: professors , status: professorStatus , refresh } = await useFetch<PaginatedResponse<Professor>>(`${apiBase}/professor`);
console.log(professors.value)

const currentPage = ref<number>(1);
const itemsPerPage: number = 12;
const search = ref('')

watch([currentPage, search], () => {
    refresh();
});

</script>

<template>
    <div class="container mx-auto p-4">

        <Card class="mb-4">
            <CardHeader>
                <CardTitle>ระบบจัดการบุคลากร</CardTitle>
                <CardDescription>บุคลากรคณะเทคโนโลยีสารสนเทศ</CardDescription>
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

                <div v-if="professorStatus === 'pending'">
                    Loading ...
                </div>

                <NuxtLink v-else v-for="professor in professors?.content" :key="professor.id" :to="`/admin/professor/${professor.id}`"
                    class="group block rounded-xl overflow-hidden bg-white dark:bg-gray-800 shadow-md hover:shadow-xl hover:-translate-y-1 transition-all duration-300">
                    <div class="aspect-w-16 aspect-h-9">
                        <NuxtImg
                            class="w-full aspect-[6/3] object-cover transition-transform duration-300 ease-in-out group-hover:scale-105"
                            :src="`${apiBase}/images/${professor.profile?.image ?? ''}`" width="600" height="400"
                            format="webp" quality="80" :alt="professor.profile?.nameTh" :placeholder="'/dfimage.svg'"
                            loading="lazy" />
                    </div>

                    <div class="p-4 sm:p-5">
                        <h1
                            class="text-base sm:text-lg font-semibold text-gray-900 dark:text-white group-hover:text-sky-600 transition-colors duration-300 line-clamp-2">
                            {{ professor.profile?.nameTh }}
                        </h1>
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