
<template>
             <router-link
           :key="to"
          :to="to"
        >
          <span >{{ label }}</span>
        </router-link>
</template>

<script setup lang="ts">
export interface EssentialLinkProps {
  to: string;
  label: string;
  roles: string[]
}

withDefaults(defineProps<EssentialLinkProps>(), {
  to: '',
  label: '#',
  roles: () => [] 
});
</script>
