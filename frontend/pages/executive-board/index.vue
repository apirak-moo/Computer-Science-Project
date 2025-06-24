<template>
    <div class="overflow-x-auto p-4">
        <h1 class="text-3xl font-bold text-sky-700 mb-10 text-center">คณะผู้บริหารคณะเทคโนโลยีสารสนเทศ</h1>
        <OrganizationChart :value="data" collapsible v-model:selectionKeys="selection" electionMode="multiple" >
            <template #person="slotProps" >
                <NuxtLink :to="`/personnel/${slotProps.node.data.id}`" class="flex flex-col p-2 min-w-60 cursor-pointer hover:scale-105 transition duration-500 ease-in-out">
                    <div class="flex flex-col items-center">
                        <NuxtImg :alt="slotProps.node.data.name" :src="slotProps.node.data.image"
                            class="object-cover mb-2 rounded-full aspect-[1/1]" width="150" height="150" /> <span
                            class="font-bold text-xl text-center whitespace-nowrap">{{ slotProps.node.data.name
                            }}</span>

                        <span class="text-sm text-center whitespace-nowrap">{{ slotProps.node.data.title }}</span>
                    </div>
                </NuxtLink>
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

const selection = ref({});

const data = ref({
    key: '0',
    type: 'person',
    styleClass: '!bg-sky-100 !text-sky-800 !rounded-xl',
    data: {
        id: 'dfhd56b5d5d',
        image: 'http://www.it.pbru.ac.th/wp-content/uploads/2020/06/DSC_8417-300x232.jpg',
        name: 'ดร.พีรศุษย์ บุญมาธรรม',
        title: 'คณะบดีคณะเทคโนโลยีสารสนเทศ'
    },
    children: [
        {
            key: '0_0',
            type: 'person',
            styleClass: '!bg-sky-100 !text-sky-800 !rounded-xl',
            data: {
                image: 'http://www.it.pbru.ac.th/wp-content/uploads/2020/06/2-e1650525361509-300x283.jpg',
                name: 'อาจารย์ กรกรต เจริญผล',
                title: 'รองคณบดีฝ่ายบริหารและประกันคุณภาพ'
            },
        },
        {
            key: '0_1',
            type: 'person',
            styleClass: '!bg-sky-100 !text-sky-900 !rounded-xl',
            data: {
                image: 'http://www.it.pbru.ac.th/wp-content/uploads/2020/06/DSC_8424-300x276.jpg',
                name: 'ดร.กฤษดา ด่านประสิทธิ์พร',
                title: 'รองคณบดีฝ่ายวิชาการและวิจัย'
            },
        },
        {
            key: '0_2',
            type: 'person',
            styleClass: '!bg-sky-100 !text-sky-800 !rounded-xl',
            data: {
                image: 'http://www.it.pbru.ac.th/wp-content/uploads/2020/06/DSC_8455-300x282.jpg',
                name: 'ดร.ปราโมทย์ ตงฉิน ',
                title: 'รองคณบดีฝ่ายกิจการนักศึกษาและบริการวิชาการ'
            },
        }
    ]
});
</script>

<style>

</style>