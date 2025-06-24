<script setup lang="ts">

import {
    NavigationMenu,
    NavigationMenuContent,
    NavigationMenuItem,
    NavigationMenuLink,
    NavigationMenuList,
    NavigationMenuTrigger,
    navigationMenuTriggerStyle,
} from '@/components/ui/navigation-menu'
import type { Degree } from '~/types/Degree'

const components: { title: string, href: string, description: string }[] = [
    {
        title: 'วิทยาการคอมพิวเตอร์',
        href: 'cs',
        description:
            'A modal dialog that interrupts the user with important content and expects a response.',
    },
    {
        title: 'คอมพิวเตอร์ประยุกต์',
        href: 'ca',
        description:
            'For sighted users to preview content available behind a link.',
    },
    {
        title: 'คอมพิวเตอร์ (ค.บ.)',
        href: 'cedu',
        description:
            'Displays an indicator showing the completion progress of a task, typically displayed as a progress bar.',
    },
    {
        title: 'นวัตกรรมดิจิทัล',
        href: 'di',
        description: 'Visually or semantically separates content.',
    },
]

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const { data: degrees } = await useFetch<Degree[]>(`${apiBase}/degree`)
</script>
<template>
    <header class="container mx-auto px-4 bg-white p-3">
            <div class="flex items-center justify-between px-6 py-4 shadow-sm rounded-2xl">
                <div class="flex items-center gap-3">
                    <NuxtLink to="/">
                        <NuxtImg src="/logoIT.png" alt="LogoIT" class="h-10 w-10" />
                    </NuxtLink>
                    <NuxtLink to="/"><span class="text-xl font-bold text-sky-700">คณะเทคโนโลยีสารสนเทศ</span></NuxtLink>
                </div>
                <NavigationMenu class="hidden lg:block">
                    <NavigationMenuList>
                        <NavigationMenuItem>
                            <NavigationMenuTrigger>เกี่ยวกับคณะ</NavigationMenuTrigger>
                            <NavigationMenuContent>
                                <ul
                                    class="grid gap-3 p-3 md:w-[400px] lg:w-[500px] lg:grid-cols-[minmax(0,.75fr)_minmax(0,1fr)]">
                                    <li class="row-span-3">
                                        <NavigationMenuLink as-child>
                                            <a class="flex h-full w-full select-none flex-col justify-end rounded-md bg-gradient-to-b from-muted/50 to-muted p-6 no-underline outline-none focus:shadow-md"
                                                href="https://www.pbru.ac.th/">
                                                <div class="m-auto">
                                                    <NuxtImg src="/logoPBRU.png" sizes="50vw sm:50vw md:100vw"
                                                        class="object-contain" />
                                                </div>
                                                <p class="mb-2 text-center text-lg font-medium">
                                                    มหาวิทยาลัยราชภัฏเพชรบุรี
                                                </p>
                                                <p class="text-sm text-center leading-tight text-muted-foreground">
                                                    มหาวิทยาลัยที่ได้รับการยอมรับด้านอาหาร ท่องเที่ยว วิทยาการสุขภาพ
                                                    และเป็นมหาวิทยาลัยดิจิทัล
                                                </p>
                                            </a>
                                        </NavigationMenuLink>
                                    </li>

                                    <li>
                                        <NavigationMenuLink as-child>
                                            <NuxtLink to="/history"
                                                class="block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground">
                                                <div class="text-sm font-medium leading-none">ประวัติคณะ</div>
                                                <p class="line-clamp-2 text-sm leading-snug text-muted-foreground">
                                                    ผลิตนวตกรรมทางด้านเทคโนโลยีสารสนเทศ เพื่อพัฒนาท้องถิ่นและสังคม
                                                </p>
                                            </NuxtLink>
                                        </NavigationMenuLink>
                                    </li>
                                    <li>
                                        <NavigationMenuLink as-child>
                                            <NuxtLink to="/structure"
                                                class="block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground">
                                                <div class="text-sm font-medium leading-none">โครงสร้างการบริหาร</div>
                                                <p class="line-clamp-2 text-sm leading-snug text-muted-foreground">
                                                    กระบวนการทำงานการบริหารคณะ
                                                </p>
                                            </NuxtLink>
                                        </NavigationMenuLink>
                                    </li>
                                    <li>
                                        <NavigationMenuLink as-child>
                                            <NuxtLink to="/executive-board"
                                                class="block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground">
                                                <div class="text-sm font-medium leading-none">คณะกรรมการบริหาร</div>
                                                <p class="line-clamp-2 text-sm leading-snug text-muted-foreground">
                                                    ผู้บริหารคณะเทคโนโลยีสารสนเทศ
                                                </p>
                                            </NuxtLink>
                                        </NavigationMenuLink>
                                    </li>
                                </ul>
                            </NavigationMenuContent>
                        </NavigationMenuItem>
                        <NavigationMenuItem>
                            <NavigationMenuTrigger>หลักสูตร</NavigationMenuTrigger>
                            <NavigationMenuContent>
                                <ul class="grid w-[400px] gap-3 p-4 md:w-[500px] md:grid-cols-2 lg:w-[600px] ">
                                    <li v-for="degree in degrees" :key="degree.id">
                                        <NavigationMenuLink as-child>
                                            <NuxtLink :to="`/degree/${degree.id}`"
                                                class="block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground">
                                                <div class="text-sm font-medium leading-none">{{ degree.nameTh }}
                                                </div>
                                            </NuxtLink>
                                        </NavigationMenuLink>
                                    </li>
                                </ul>
                            </NavigationMenuContent>
                        </NavigationMenuItem>
                        <NavigationMenuItem>
                            <NavigationMenuTrigger>สาขาวิชา</NavigationMenuTrigger>
                            <NavigationMenuContent>
                                <ul class="grid w-[400px] gap-3 p-4 md:w-[500px] md:grid-cols-2 lg:w-[600px] ">
                                    <li v-for="component in components" :key="component.title">
                                        <NavigationMenuLink as-child>
                                            <NuxtLink :to="`/major/${component.href}`"
                                                class="block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground">
                                                <div class="text-sm font-medium leading-none">{{ component.title }}
                                                </div>
                                                <p class="line-clamp-2 text-sm leading-snug text-muted-foreground">
                                                    {{ component.description }}
                                                </p>
                                            </NuxtLink>
                                        </NavigationMenuLink>
                                    </li>
                                </ul>
                            </NavigationMenuContent>
                        </NavigationMenuItem>
                        <NavigationMenuItem>
                            <NuxtLink to="/news" :class="navigationMenuTriggerStyle()">
                                ข่าวสาร
                            </NuxtLink>
                        </NavigationMenuItem>
                        <NavigationMenuItem>
                            <NavigationMenuLink href="/tuition-fees" :class="navigationMenuTriggerStyle()">
                                ค่าธรรมเนียมการศึกษา
                            </NavigationMenuLink>
                        </NavigationMenuItem>
                        <NavigationMenuItem>
                            <NavigationMenuLink href="/personnel" :class="navigationMenuTriggerStyle()">
                                บุคลากร
                            </NavigationMenuLink>
                        </NavigationMenuItem>
                    </NavigationMenuList>
                </NavigationMenu>
            </div>
        </header>
</template>