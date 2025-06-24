// https://nuxt.com/docs/api/configuration/nuxt-config

import tailwindcss from "@tailwindcss/vite";
import Lara from '@primeuix/themes/lara'

export default defineNuxtConfig({
  compatibilityDate: '2025-05-15',
  devtools: { enabled: true },

  css: [
    '~/assets/css/main.css',
  ],

  vite: {
    plugins: [
      tailwindcss(),
    ],
  },

  modules: [
    '@nuxt/content',
    '@nuxt/eslint',
    '@nuxt/fonts',
    '@nuxt/icon',
    '@nuxt/image',
    '@nuxt/scripts',
    '@nuxt/test-utils',
    'shadcn-nuxt',
    '@vueuse/motion/nuxt',
    '@primevue/nuxt-module',
    '@vueuse/nuxt',
  ],

  image: {
    provider: 'ipx',
    domains: ['localhost:8080'],
    formats: ['webp', 'jpeg', 'svg'], // ตรวจสอบว่า svg ถูกรวมใน format ที่รองรับ
  },
  
  googleFonts: {
    display: 'swap',
    download: true,
    families: {
      'Noto+Sans+Thai': [100, 300, 400, 500, 700, 900], // เลือกระดับความหนา
    },
  },

  htmlAttrs: {
    lang: 'th',
  },

  shadcn: {
    /**
     * Prefix for all the imported component
     */
    prefix: '',
    /**
     * Directory that the component lives in.
     * @default "./components/ui"
     */
    componentDir: './components/ui'
  },

  runtimeConfig:{
    public:{
      apiBase: process.env.NUXT_PUBLIC_API_BASE || '',
    }
  },

  primevue: {
    /* ปิด auto-import แล้วเจาะจง component ที่ต้องการ
       จะช่วยลด bundle ถ้าใช้แค่ไม่กี่ตัว */
    autoImport: false,
    components: {
      include: ['OrganizationChart']       // หรือ ['OrgChart'] ถ้าใช้ alias ใหม่นับจาก 4.3
    },

    /* ตัวอย่าง option เพิ่มธีม-Aura และเปิด ripple */
    options: {
      ripple: true,
      theme: { preset: Lara }
    }
  }

})