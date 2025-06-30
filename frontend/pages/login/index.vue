<script setup lang="ts">

const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const email = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()

const submitForm = async () => {
  try {
    const response = await $fetch<{ token: string }>(`${apiBase}/auth/login`, {
      method: 'POST',
      body: {
        email: email.value,
        password: password.value
      }
    })

    // ใช้ useCookie เพื่อเก็บ token ฝั่ง client
    const token = useCookie('token', {
      maxAge: 60 * 60 * 24 * 7, // 7 วัน
      secure: true,             // ถ้าใช้ HTTPS
      sameSite: 'lax'           // ป้องกันบางกรณีของ CSRF
    })

    token.value = response.token

    await router.push("/admin")
  } catch (err) {
    error.value = 'Invalid email or password'
    console.error('Login failed:', err)
  }
}


</script>

<template>
  <div class="container mx-auto p-4 flex justify-center">
    <form @submit.prevent="submitForm" class="mt-32 w-5/6 md:w-3/4">
      <div>
        <Label class="my-3">Email</Label>
        <Input v-model="email" type="email" required class="border px-4 py-2 w-full" />
      </div>
      <div class="mt-4">
        <Label class="my-3">Password</Label>
        <Input v-model="password" type="password" required class="border px-4 py-2 w-full" />
      </div>
      <div class="mt-6">
        <Button type="submit"
          class="bg-sky-800 font-bold text-white px-4 py-2 w-full cursor-pointer">เข้าสู่ระบบ</Button>
      </div>
      <div v-if="error" class="text-red-500 mt-2">{{ error }}</div>
    </form>
  </div>
</template>
