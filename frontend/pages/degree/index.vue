<script setup lang="ts">

const courses = [
    {
        degree: "ปริญญาตรี",
        description: "ก้าวแรกสู่โลกอาชีพ! สร้างฐานความรู้แน่น พร้อมทักษะที่จำเป็น เรียนจบคว้างาน หรือต่อยอดสู่ความเชี่ยวชาญ!",
        path: "2",
        image: "2151492161.png"
    },
    {
        degree: "ปริญญาโท",
        description: "ก้าวแรกสู่โลกอาชีพ! สร้างฐานความรู้แน่น พร้อมทักษะที่จำเป็น เรียนจบคว้างาน หรือต่อยอดสู่ความเชี่ยวชาญ!",
        path: "3",
        image: "2151151768.png"
    },
    {
        degree: "ปริญญาเอก",
        description: "สุดยอดแห่งปัญญา! ค้นคว้าวิจัย สร้างสรรค์องค์ความรู้ใหม่ เป็นผู้นำทางวิชาการและผู้เชี่ยวชาญระดับสูง!",
        path: "4",
        image: "2151151839.png"
    },
    {
        degree: "หลักสูตรนานาชาติ",
        description: "เปิดโลกทัศน์ไร้พรมแดน! เรียนรู้ในสภาพแวดล้อมสากล พัฒนาภาษา คว้าโอกาสการทำงานทั่วโลก!",
        path: "International",
        image: "2151492125.png"
    },
]

</script>

<template>
    <div class="container mx-auto p-4">
        <div class="">
            <Breadcrumb>
                <BreadcrumbList>
                    <BreadcrumbItem>
                        <BreadcrumbLink as-child>
                            <RouterLink to="/">
                                Home
                            </RouterLink>
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                    <!--  -->

                    <BreadcrumbSeparator />

                    <BreadcrumbItem>
                        <BreadcrumbPage>หลักสูตร</BreadcrumbPage>
                    </BreadcrumbItem>
                    <!--  -->
                </BreadcrumbList>
            </Breadcrumb>
        </div>
        <h1 class="text-3xl my-5 font-bold text-sky-700 mb-6 text-center">หลักสูตร</h1>
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
            <NuxtLink v-for="(item, index) in courses" :key="index" :to="`/degree/${item.path}`"
                class="bg-white rounded-2xl shadow-md hover:shadow-sky-300/60 hover:ring-2 hover:ring-sky-300 transition-all duration-300 ease-in-out overflow-hidden group flex flex-col">
                <NuxtImg :src="`/${item.image}`" width="600" height="300" format="webp" quality="80" :alt="item.path" densities="x1 x2" placeholder loading="lazy"
                    class="object-cover transition-transform duration-300 ease-in-out group-hover:scale-105" />
                <div class="p-4 flex flex-col justify-between flex-1">
                    <div>
                        <h3 class="text-lg font-semibold text-sky-700 group-hover:underline">
                            {{ item.degree }}
                        </h3>
                        <div>
                            
                        </div>
                        <p class="text-gray-600 mt-2 line-clamp-3">
                            {{ item.description }}
                        </p>
                    </div>
                </div>
            </NuxtLink>
        </div>
    </div>
</template>