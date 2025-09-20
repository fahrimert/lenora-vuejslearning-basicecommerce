<template>
  <div class="flex w-full h-screen">
    <SidebarWrapper />
    <div class="flex-1 p-4">
      <slot />
    </div>
  </div>
</template>

<script setup lang="ts">
import SidebarWrapper from '@/components/SidebarComponents/SidebarWrapper.vue'

</script>

<style lang="scss" scoped>

</style>