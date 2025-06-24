<template>
    <div class="overflow-x-auto p-4">
        <h1 class="text-3xl font-bold text-sky-700 mb-10 text-center">อาจารย์ประจำสาขาวิทยาการคอมพิวเตอร์</h1>
        <OrganizationChart v-model:selectionKeys="selection" :value="data" collapsible selectionMode="multiple">
            <template #person="slotProps" >
                <div class="flex flex-col p-2 min-w-60">
                    <div class="flex flex-col items-center">
                        <NuxtImg :alt="slotProps.node.data.name" :src="slotProps.node.data.image"
                            class="object-cover mb-2 rounded-full aspect-[1/1]" width="150" height="150" /> <span
                            class="font-bold text-sm text-center whitespace-nowrap">{{ slotProps.node.data.name
                            }}</span>

                        <span class="text-xs text-center whitespace-nowrap">{{ slotProps.node.data.title }}</span>
                    </div>
                </div>
            </template>
            <template #default="slotProps">
                <span>{{ slotProps.node.label }}</span>
            </template>
        </OrganizationChart>
    </div>
</template>

<script setup>
// ... script ของคุณเหมือนเดิม ...
import { ref } from 'vue';

const data = ref({
    key: '0',
    type: 'person',
    data: {
        image: 'http://www.it.pbru.ac.th/wp-content/uploads/2020/06/DSC_8437-300x248.jpg',
        name: 'ผู้ช่วยศาสตราจารย์ ปณิธิ แก้วสวัสดิ์',
        title: 'ประธานสาขาวิทยาการคอมพิวเตอร์'
    },
    children: [
        {
            key: '0_0',
            type: 'person',
            data: {
                image: 'http://www.it.pbru.ac.th/wp-content/uploads/2020/06/1591001720139.jpg',
                name: 'ดร.ชนิตร์นาถ วิเชียรประดิษฐ์',
                title: 'อาจารย์ประจำหลักสูตร'
            },
        },
        {
            key: '0_1',
            type: 'person',
            data: {
                image: 'http://www.it.pbru.ac.th/wp-content/uploads/2020/06/DSC_8521-300x281.jpg',
                name: 'อาจารย์ ศิริพร อ่วมศิริ',
                title: 'อาจารย์ประจำหลักสูตร'
            },
        },
        {
            key: '0_2',
            type: 'person',
            data: {
                image: 'http://www.it.pbru.ac.th/wp-content/uploads/2020/06/DSC_8528-300x276.jpg',
                name: 'อาจารย์ สุกัญชลิกา บุญมาธรรม',
                title: 'อาจารย์ประจำหลักสูตร'
            },
        }
    ]
});
const selection = ref({});
</script>

<style>

</style>