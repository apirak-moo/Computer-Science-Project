<script setup lang="ts">
import Header from '~/components/Header.vue';
</script>

<template>
    <div>
        <Header/>
        <main>
            <slot/>
        </main>
    </div>
</template>