import type { UserResponse } from '~/types/UserResponse'

export default defineNuxtRouteMiddleware(async (to, from) => {
  const config = useRuntimeConfig()
  const apiBase = config.public.apiBase
  const token = useCookie('token')

  if (!token.value) {
    return navigateTo('/login')
  }

  try {
    const res = await $fetch<UserResponse>(`${apiBase}/auth/check`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${token.value}`
      }
    })

    const userRoles = Array.isArray(res.user.roles)
      ? res.user.roles
      : [res.user.roles]

    const requiredRoles = to.meta.roles as string[] | undefined

    if (requiredRoles && !requiredRoles.some(role => userRoles.includes(role))) {
      return navigateTo('/') // กลับหน้าแรกหรือหน้า "Unauthorized"
    }

  } catch (error) {
    console.error('Auth check failed:', error)
    return navigateTo('/login')
  }
})
