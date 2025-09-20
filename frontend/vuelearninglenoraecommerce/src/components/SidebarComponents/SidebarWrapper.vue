<template>
    <div class="relative w-full h-fit flex flex-row max-xl:flex-col max-xl:p-0 justify-between items-start">
      
        <Sidebar    :user="userr" />
        <div class="flex justify-end items-center w-full px-4 py-2 border-b bg-white">
            <slot/>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, watchEffect } from "vue";
import {userr} from '../../keyCloak'
import Sidebar from './Sidebar.vue';
import Keycloak from 'keycloak-js';

const user = ref<any>(null);



</script>

<style lang="scss" scoped>

</style>