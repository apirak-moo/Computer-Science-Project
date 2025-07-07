<script setup lang="ts">

import type { PaginatedResponse } from '~/types/PaginatedResponse';
import type { Position } from '~/types/Position';
import type { Professor } from '~/types/Professor';

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const major = 'เทคโนโลยีสารสนเทศ'

const { data: professors } = await useAsyncData<PaginatedResponse<Professor>>('major', () => {
    return $fetch(`${apiBase}/professor?major=${major}`);
});

console.log(professors.value);

const advisor = professors.value?.content.find(prof =>
    prof.positions?.some((position: Position) => position.name === 'ประธานสาขาวิชา')
);

const children = professors.value?.content.filter(prof =>
    prof.positions?.some((position: Position) => position.name === 'อาจารย์ประจำสาขาวิชา')
);

const data = ref({
    key: '0',
    type: 'person',
    data: advisor, // เป็น object Professor
    children: children?.map((professor, index) => ({
        key: `0_${index}`,
        type: 'person',
        data: professor
    }))
});
const selection = ref({});
</script>

<template>
    <div class="overflow-x-auto p-4">
        <h1 class="text-3xl font-bold text-sky-700 mb-10 text-center">อาจารย์ประจำสาขาวิทยาการคอมพิวเตอร์</h1>
        <OrganizationChart v-if="professors?.content.length" v-model:selectionKeys="selection" :value="data" collapsible
            selectionMode="multiple">
            <template #person="slotProps">
                <div class="flex flex-col p-2 min-w-60">
                    <div class="flex flex-col items-center">
                        <NuxtImg v-if="slotProps.node.data?.profile?.image" :alt="slotProps.node.data.profile.name"
                            :src="`${apiBase}/images/${slotProps.node.data.profile.image}`"
                            class="object-cover mb-2 rounded-full aspect-[1/1]" width="150" height="150" />
                        <span class="font-bold text-sm text-center whitespace-nowrap">
                            {{ slotProps.node.data.profile.name }}
                        </span>
                        <span class="text-xs text-center whitespace-nowrap">
                            {{ slotProps.node.data.email }}
                        </span>
                    </div>
                </div>
            </template>

            <template #default="slotProps">
                <span>{{ slotProps.node.label }}</span>
            </template>
        </OrganizationChart>
    </div>
</template>

<style></style>