
<template>
  <q-item >

    <!--     <q-item-section v-if="icon" avatar>
      <q-icon :name="icon" />
    </q-item-section> -->

    <q-item-section>
             <router-link
           :key="to"
          :to="to"
      
        >
          <span >{{ label }}</span>
        </router-link>
      <q-item-label></q-item-label>
    </q-item-section>
  </q-item>
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
