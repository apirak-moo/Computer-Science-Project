<script setup>
import { Button } from '@/components/ui/button'
import { Card, CardHeader, CardTitle } from '@/components/ui/card'
import { Accordion, AccordionContent, AccordionItem, AccordionTrigger } from '@/components/ui/accordion'

const programs = [
    { name: 'วิทยาการข้อมูลและปัญญาประดิษฐ์', icon: '🧠' },
    { name: 'ความมั่นคงปลอดภัยไซเบอร์', icon: '🛡️' },
    { name: 'เทคโนโลยีมัลติมีเดีย', icon: '🎨' },
    { name: 'วิศวกรรมซอฟต์แวร์', icon: '💻' }
]

const faqItems = [
    { id: 'item-1', question: 'คณะเปิดรับสมัครช่วงไหน?', answer: 'โดยปกติจะเปิดรับสมัครในช่วงเดือนตุลาคม - มกราคมของทุกปี สามารถติดตามประกาศได้ทางหน้าเว็บไซต์ของมหาวิทยาลัย' },
    { id: 'item-2', question: 'ใช้คะแนนอะไรบ้างในการยื่นสมัคร?', answer: 'สำหรับรอบ Portfolio จะพิจารณาจากผลงานและเกรดเฉลี่ย ส่วนรอบ Admission จะใช้คะแนน TGAT/TPAT และ A-Level ตามที่มหาวิทยาลัยกำหนด' },
    { id: 'item-3', question: 'ค่าเทอมเท่าไหร่?', answer: 'ค่าเทอมจะแตกต่างกันไปในแต่ละหลักสูตร โดยเฉลี่ยอยู่ที่ประมาณ 35,000 - 45,000 บาทต่อภาคการศึกษา' }
]

</script>

<template>
    <div class="container mx-auto px-4 text-slate-800 font-sans">

        <div
            class="relative h-[60vh] md:h-[80vh] flex items-center justify-center text-center text-white overflow-hidden">
            <div class="absolute inset-0">
                <NuxtImg src="/DSC_0552.jpg" alt="Faculty Building" class="w-full h-full object-cover rounded-2xl " />
                <div class="absolute inset-0 bg-black/50 rounded-2xl"></div>
            </div>
            <div class="relative z-10 px-4">
                <h2 class="text-4xl md:text-6xl lg:text-7xl font-extrabold leading-tight">Innovate the Future</h2>
                <p class="mt-4 text-lg md:text-xl max-w-2xl mx-auto text-slate-200">
                    ร่วมเป็นส่วนหนึ่งในการสร้างสรรค์เทคโนโลยีแห่งอนาคตกับคณะเทคโนโลยีสารสนเทศ
                </p>
                <Button size="lg" class="mt-8 bg-sky-500 hover:bg-sky-600 text-lg px-8 py-6">
                    ค้นหาหลักสูตรของคุณ
                </Button>
            </div>
        </div>

        <main class="container mx-auto p-4 py-16 md:py-24 space-y-24">

            <section class="grid md:grid-cols-2 gap-12 items-center">
                <div class="order-2 md:order-1">
                    <h3 class="text-sm font-bold uppercase text-sky-500 tracking-wider">เกี่ยวกับเรา</h3>
                    <h4 class="text-3xl md:text-4xl font-bold mt-2 text-slate-900">ผู้นำด้านนวัตกรรมและเทคโนโลยี</h4>
                    <p class="mt-4 text-slate-600 leading-relaxed">
                        เรามุ่งมั่นในการผลิตบัณฑิตที่มีคุณภาพ พร้อมด้วยทักษะที่จำเป็นสำหรับศตวรรษที่ 21
                        คณาจารย์ผู้ทรงคุณวุฒิและห้องปฏิบัติการที่ทันสมัยของเรา
                        พร้อมสนับสนุนให้นักศึกษาทุกคนบรรลุเป้าหมายสูงสุด
                    </p>
                </div>
                <div class="order-1 md:order-2 flex justify-center rounded-lg">
                    <Carousel class="relative w-full max-w-md shadow-xl h-auto">
                        <CarouselContent>
                            <CarouselItem>
                                <NuxtImg src="/R1.jpg" alt="Modern Classroom" class="rounded-lg" />
                            </CarouselItem>
                            <CarouselItem>
                                <NuxtImg src="/R2.jpg" alt="Modern Classroom" class="rounded-lg" />
                            </CarouselItem>
                            <CarouselItem>
                                <NuxtImg src="/R3.jpg" alt="Modern Classroom" class="rounded-lg" />
                            </CarouselItem>
                            <CarouselItem>
                                <NuxtImg src="/R4.jpg" alt="Modern Classroom" class="rounded-lg" />
                            </CarouselItem>
                            <CarouselItem>
                                <NuxtImg src="/R5.jpg" alt="Modern Classroom" class="rounded-lg" />
                            </CarouselItem>
                        </CarouselContent>
                        <CarouselPrevious />
                        <CarouselNext />
                    </Carousel>
                </div>
            </section>

            <section>
                <div class="text-center">
                    <h3 class="text-sm font-bold uppercase text-sky-500 tracking-wider">หลักสูตรของเรา</h3>
                    <h4 class="text-3xl md:text-4xl font-bold mt-2 text-slate-900">เส้นทางสู่อาชีพในฝัน</h4>
                </div>
                <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mt-12">
                    <Card v-for="program in programs" :key="program.name"
                        class="bg-white hover:shadow-xl hover:-translate-y-2 transition-all duration-300">
                        <CardHeader>
                            <div class="text-4xl">{{ program.icon }}</div>
                            <CardTitle class="pt-4 text-lg">{{ program.name }}</CardTitle>
                        </CardHeader>
                    </Card>
                </div>
            </section>

            <section class="max-w-3xl mx-auto">
                <div class="text-center">
                    <h3 class="text-sm font-bold uppercase text-sky-500 tracking-wider">คำถามที่พบบ่อย</h3>
                    <h4 class="text-3xl md:text-4xl font-bold mt-2 text-slate-900">เคลียร์ทุกข้อสงสัย</h4>
                </div>
                <Accordion type="single" class="w-full mt-12" collapsible>
                    <AccordionItem v-for="faq in faqItems" :key="faq.id" :value="faq.id">
                        <AccordionTrigger class="text-lg font-medium text-left">{{ faq.question }}</AccordionTrigger>
                        <AccordionContent class="text-slate-600">
                            {{ faq.answer }}
                        </AccordionContent>
                    </AccordionItem>
                </Accordion>
            </section>

        </main>

    </div>
</template>

<style>
/* Import Font */
@import url('https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&display=swap');

body {
    font-family: 'Plus Jakarta Sans', sans-serif;
    scroll-behavior: smooth;
}

/* Optional: Add simple fade-in animations on scroll */
/* For a full solution, consider a library like 'v-motion' or Intersection Observer API */
@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(20px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* You would need JavaScript to apply this class as sections enter the viewport */
.fade-in-section {
    animation: fadeIn 0.8s ease-out forwards;
}
</style>