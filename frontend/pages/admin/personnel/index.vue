<script setup lang="ts">
import type { PaginatedResponse } from '~/types/PaginatedResponse';
import type { Professor } from '~/types/Professor';
import PersonnelCard from '~/components/PersonnelCard.vue';

// definePageMeta({
//     middleware: 'auth',
//     roles: ['Administrator', 'Dean of the Faculty of Information Technology']
// })

const token = useCookie('token')

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const currentPage = ref<number>(1);
const itemsPerPage: number = 8;
const search = ref('')

const { data: professors, status: professorStatus, refresh } = await useFetch<PaginatedResponse<Professor>>(`${apiBase}/professor`,{
    query: computed(() => ({
        page: currentPage.value - 1,
        size: itemsPerPage,
        name: search.value,
        sort: 'profile.name,asc'
    }))
});
console.log(professors.value)

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
                <NuxtLink to="/admin/personnel/add">
                    <Button class="col-span-2 w-full md:col-span-1 bg-sky-700 hover:bg-sky-800 cursor-pointer">
                        เพิ่มข่าวสาร
                    </Button>
                </NuxtLink>
            </CardContent>

            <CardContent class="grid grid-cols-1 gap-6 p-4 sm:grid-cols-2 md:p-6 lg:grid-cols-3 xl:grid-cols-4">
                <div v-for="professor in professors?.content" :key="professor.id">
                    <PersonnelCard class="h-full" :id="professor.id" :name="professor.profile?.name"
                        :major="professor.profile?.major" :positions="professor.positions"
                        :image-url="professor.profile?.image" />
                </div>
            </CardContent>


            <Pagination v-slot="{ page }" v-model:page="currentPage" :items-per-page="itemsPerPage"
                :total="professors?.totalElements ?? 0">
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