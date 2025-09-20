
<script setup>
import { onMounted } from 'vue';
import { vueKeycloak } from '@josempgon/vue-keycloak'



onMounted(async () => {
    const keyCloak = vueKeycloak.keyCloak
    if (!keyCloak.authenticated) {
        await keyCloak.login()
        
    }
      if (keycloak.authenticated) {
    user.value = keycloak.tokenParsed;
    console.log("Authenticated user:", user.value);
  } else {
    console.log("User not authenticated yet");
  }
})

</script>

<template>
    <div>
        Redirected to login...
    </div>
</template>

<style lang="scss" scoped>

</style>