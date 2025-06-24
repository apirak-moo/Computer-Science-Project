<script setup lang="ts">
import { History, Info, Palette, Code, Presentation, Network, Computer } from 'lucide-vue-next'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from '@/components/ui/carousel'

// ข้อมูลสำหรับห้อง Lab ต่างๆ
const labs = ref([
    {
        title: 'ห้อง Lab เขียนโปรแกรม (Programming Lab)',
        icon: Code,
        images: [
            'https://placehold.co/800x450/1e293b/ffffff?text=Programming+Lab+1',
            'https://placehold.co/800x450/1e293b/ffffff?text=Modern+Workstations',
            'https://placehold.co/800x450/1e293b/ffffff?text=Collaborative+Space',
            'https://placehold.co/800x450/1e293b/ffffff?text=Dual+Monitors',
        ]
    },
    {
        title: 'ห้อง Lab เรียนทฤษฎี/บรรยาย (Lecture Hall)',
        icon: Presentation,
        images: [
            'https://placehold.co/800x450/0f172a/ffffff?text=Lecture+Hall+1',
            'https://placehold.co/800x450/0f172a/ffffff?text=Smart+Board',
            'https://placehold.co/800x450/0f172a/ffffff?text=Comfortable+Seating',
            'https://placehold.co/800x450/0f172a/ffffff?text=Projector+System',
        ]
    },
    {
        title: 'ห้อง Lab Network (Networking Lab)',
        icon: Network,
        images: [
            'https://placehold.co/800x450/374151/ffffff?text=Network+Lab',
            'https://placehold.co/800x450/374151/ffffff?text=Servers+and+Racks',
            'https://placehold.co/800x450/374151/ffffff?text=Routing+Equipment',
            'https://placehold.co/800x450/374151/ffffff?text=Patch+Panels',
        ]
    },
    {
        title: 'ห้อง Lab คอมพิวเตอร์ทั่วไป (General Computer Lab)',
        icon: Computer,
        images: [
            'https://placehold.co/800x450/4b5563/ffffff?text=General+Lab+A',
            'https://placehold.co/800x450/4b5563/ffffff?text=All-in-One+PCs',
            'https://placehold.co/800x450/4b5563/ffffff?text=Student+Access',
            'https://placehold.co/800x450/4b5563/ffffff?text=Printing+Services',
        ]
    }
])

useHead({
    title: 'เกี่ยวกับคณะของเรา | Faculty of Innovation',
    meta: [
        { name: 'description', content: 'เรียนรู้เพิ่มเติมเกี่ยวกับประวัติ วิสัยทัศน์ และห้องปฏิบัติการที่ทันสมัยของคณะเรา' }
    ]
})
</script>

<template>
    <div class="bg-slate-50 dark:bg-slate-900 text-slate-800 dark:text-slate-200">
        <div class="container mx-auto px-4 py-8 sm:px-6 sm:py-12 lg:px-8 lg:py-16">

            <section
                class="relative mb-14 w-full overflow-hidden rounded-2xl shadow-xl aspect-[16/9] sm:aspect-[2/1] md:aspect-[16/7] lg:aspect-[16/6]">
                <NuxtImg src="/DSC_0552.jpg" format="webp" width="1920" height="1080" alt="ภาพถ่ายคณะ"
                    class="absolute inset-0 h-full w-full object-cover object-center" />
                <div class="absolute inset-0 bg-gradient-to-t from-black/70 via-black/40 to-transparent" />
                <div
                    class="relative z-10 flex h-full flex-col items-start justify-end px-4 py-6 sm:px-6 md:px-12 lg:px-16">
                    <h1 class="text-3xl sm:text-4xl lg:text-5xl xl:text-6xl font-extrabold text-white drop-shadow-lg">
                        คณะเทคโนโลยีสารสนเทศ
                    </h1>
                    <p
                        class="mt-3 text-base sm:text-lg md:text-xl text-slate-200 drop-shadow-md max-w-md sm:max-w-lg md:max-w-xl">
                        แหล่งรวมความรู้ สร้างสรรค์อนาคตด้วยเทคโนโลยีและนวัตกรรมที่ล้ำสมัย
                    </p>
                </div>
            </section>


            <section class="mb-20 grid grid-cols-1 gap-8 lg:grid-cols-5">
                <div class="lg:col-span-3">
                    <Card
                        class="h-full bg-white dark:bg-slate-800/50 shadow-sm hover:shadow-lg transition-shadow duration-300">
                        <CardHeader>
                            <CardTitle class="flex items-center text-2xl font-bold text-sky-600 dark:text-sky-400">
                                <History class="mr-3 h-7 w-7" />
                                ประวัติคณะ
                            </CardTitle>
                        </CardHeader>
                        <CardContent class="text-base leading-relaxed text-slate-600 dark:text-slate-300">
                            <p>
                                คณะเทคโนโลยีและนวัตกรรมก่อตั้งขึ้นเมื่อปี พ.ศ. 25XX
                                โดยมีวิสัยทัศน์ที่จะเป็นผู้นำด้านการศึกษาและการวิจัยในสาขาวิทยาการคอมพิวเตอร์และเทคโนโลยีดิจิทัล
                                เรามุ่งมั่นในการผลิตบัณฑิตที่มีคุณภาพและมีความพร้อมในการเผชิญกับความท้าทายในโลกยุคใหม่...
                                (เพิ่มเนื้อหาประวัติคณะที่นี่)
                            </p>
                            <p class="mt-4">
                                ตลอดระยะเวลาที่ผ่านมา
                                คณะของเราได้พัฒนาหลักสูตรและสร้างความร่วมมือกับภาคอุตสาหกรรมอย่างต่อเนื่อง
                                เพื่อให้นักศึกษาได้รับประสบการณ์จริงและทันต่อการเปลี่ยนแปลงของเทคโนโลยี
                            </p>
                        </CardContent>
                    </Card>
                </div>

                <div class="lg:col-span-2 space-y-8">
                    <Card
                        class="bg-white dark:bg-slate-800/50 shadow-sm hover:shadow-lg transition-shadow duration-300">
                        <CardHeader>
                            <CardTitle class="flex items-center text-2xl font-bold text-sky-600 dark:text-sky-400">
                                <Info class="mr-3 h-7 w-7" />
                                เกี่ยวกับคณะ
                            </CardTitle>
                        </CardHeader>
                        <CardContent class="text-base text-slate-600 dark:text-slate-300">
                            <p>
                                เรามุ่งเน้นการเรียนการสอนที่ผสมผสานระหว่างทฤษฎีและการปฏิบัติจริง
                                เพื่อสร้างบัณฑิตที่สามารถคิดวิเคราะห์ แก้ปัญหา และสร้างสรรค์นวัตกรรมใหม่ๆ ได้
                            </p>
                        </CardContent>
                    </Card>
                    <Card
                        class="bg-white dark:bg-slate-800/50 shadow-sm hover:shadow-lg transition-shadow duration-300">
                        <CardHeader>
                            <CardTitle class="flex items-center text-2xl font-bold text-sky-600 dark:text-sky-400">
                                <Palette class="mr-3 h-7 w-7" />
                                สีประจำคณะ
                            </CardTitle>
                        </CardHeader>
                        <CardContent class="space-y-3">
                            <p class="text-base text-slate-600 dark:text-slate-300">สีประจำคณะคือ **สีฟ้าเทคโนโลยี**</p>
                            <div class="h-10 w-full rounded-md bg-sky-600" />
                            <p class="text-sm text-slate-500 dark:text-slate-400">
                                ซึ่งหมายถึงความรู้ที่กว้างไกลเหมือนท้องฟ้า และเทคโนโลยีที่ล้ำสมัยไม่มีที่สิ้นสุด
                            </p>
                        </CardContent>
                    </Card>
                </div>
            </section>

            <section>
                <div class="text-center mb-12">
                    <h2 class="text-3xl font-extrabold sm:text-4xl text-slate-900 dark:text-white">
                        ห้องปฏิบัติการ (Labs)
                    </h2>
                    <p class="mt-3 max-w-2xl mx-auto text-lg text-slate-500 dark:text-slate-400">
                        พื้นที่แห่งการเรียนรู้และลงมือปฏิบัติจริงด้วยเครื่องมือที่ทันสมัย
                    </p>
                </div>

                <div class="space-y-16">
                    <div v-for="lab in labs" :key="lab.title">
                        <h3 class="flex items-center gap-3 text-2xl font-bold mb-6 text-slate-800 dark:text-slate-100">
                            <component :is="lab.icon" class="h-8 w-8 text-sky-500" />
                            {{ lab.title }}
                        </h3>
                        <Carousel class="w-full relative">
                            <CarouselContent>
                                <CarouselItem v-for="(image, index) in lab.images" :key="index"
                                    class="md:basis-1/2 lg:basis-1/3">
                                    <div class="p-1">
                                        <Card class="overflow-hidden border-0 shadow-lg">
                                            <CardContent class="flex aspect-video items-center justify-center p-0">
                                                <img :src="image" :alt="`${lab.title} image ${index + 1}`"
                                                    class="h-full w-full object-cover transition-transform duration-300 hover:scale-105">
                                            </CardContent>
                                        </Card>
                                    </div>
                                </CarouselItem>
                            </CarouselContent>
                            <CarouselPrevious
                                class="absolute left-[-1rem] top-1/2 -translate-y-1/2 z-10 hidden sm:flex" />
                            <CarouselNext class="absolute right-[-1rem] top-1/2 -translate-y-1/2 z-10 hidden sm:flex" />
                        </Carousel>
                    </div>
                </div>
            </section>

        </div>
    </div>
</template>

<style scoped>
/* เพิ่มสไตล์เล็กน้อยเพื่อความสวยงาม */
.drop-shadow-lg {
    filter: drop-shadow(0 4px 6px rgba(0, 0, 0, 0.4));
}

.drop-shadow-md {
    filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.3));
}
</style>