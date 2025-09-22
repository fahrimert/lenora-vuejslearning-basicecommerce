  <template>
  <q-layout view="lHh Lpr lFf" class="shadow-2 rounded-borders  ">
<!--     <q-header elevated>
      <q-toolbar>
        <q-btn flat dense round icon="menu" aria-label="Menu" @click="toggleLeftDrawer" />

        <q-toolbar-title> Quasar App </q-toolbar-title>

        <div>Quasar v{{ $q.version }}</div>
      </q-toolbar>
    </q-header>
 -->
    <q-drawer v-model="drawer" show-if-above bordered
    behavior="desktop"
     :width="isCollapsed ? 60: 260"
    class=" transition-all duration-300 full-height    justify-between column   "   style=" background-color:#faf8f6 ; width: fit-content;"
    >
    <div >

     <div class="flex items-center justify-between q-pa-md q-py-md  full-width">
      <span v-if="!isCollapsed"  class="  font-bold text-gray-800 text-lg" style="font-size: 18px;">Panel</span>
      <q-btn
        flat
        dense
        round
        icon="menu"
        @click="isCollapsed = !isCollapsed"
        class="h-8 w-8"
      />
    </div>
    
          <q-item class="row full-width q-pa-sm q-ma-none "
      >

       <q-chip class="full-width" style="height: 50px;">
                  <q-avatar color="red" style="text-uppercase" text-color="white">{{user?.preferred_username[0]}}</q-avatar>
                  <h6 class="full-width" v-if="!isCollapsed">

                    {{user?.preferred_username}}
                  </h6>
      </q-chip>
      </q-item>
    <q-list class="column ">
      <q-item 
      class="q-pa-none q-ma-none  items-center justify-center"
        v-for="link in menus"
        :key="link.label"
      >
          <router-link :to="link.to" class="full-width  text-weight-bolder q-pa-none q-ma-none items-center justify-center 
"> 
            <q-item clickable v-ripple class="q-pa-sm q-ma-none  items-center justify-start">
        <q-item-section avatar>
          <q-avatar color="teal" text-color="white" icon="category" />
        </q-item-section>

          
          <h2  v-if="!isCollapsed"   class="  text-body2 text-grey-8 rounded-borders text-center"
         style="font-weight: 500;  border-radius: 8px; font-size: 15px;"
       
  v-ripple>{{link.label}}</h2>
      </q-item class="items-center justify-center"> 
          </router-link>
      </q-item>
    </q-list>
    
  </div>
        <div class="flex  items-start q-mb-xl full-width" style="font-size: 15px;">
          
          <q-btn
          no-caps
            flat
            dense
            icon="logout"
            @click="logoutFromKeyCloak"
            class="full-width   q-pa-lg  q-m-lg "
          >
        
 <span v-if="!isCollapsed"  class="  font-bold text-gray-800 text-lg"  no-caps style="font-size: 18px;">Çıkış Yapın</span>
        </q-btn>

</div>

  

    </q-drawer>

    <div class="q-pa-md">

      <router-view  /> 
    </div>
  </q-layout>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
import  { logout, userr  } from 'src/boot/keycloak';
const drawer = ref(true)
const isCollapsed = ref(false)
const user = userr.value

interface MenuItem {
  to: string;
  label: string;
  roles: string[];
}
const menus : MenuItem[] = [
  { to: "/category-management", label: "Kategori Yönetimi",  roles: ["ecommerceadmin"] },
    { to: "/product-management", label: "Ürün Yönetimi",  roles: ["ecommerceadmin"] },
  { to: "/productList", label: "Ürün Listesi", roles: ["ecommerceadmin"] }

  ]
const filteredMenus = computed(() => {
  const roles = user?.realm_access?.roles || []
  return menus.filter(menu => menu.roles.some(role => roles.includes(role)))
})
async function logoutFromKeyCloak (){

      await logout()
      } 
  function handleLogout() {
  console.log('Logout')
  // Keycloak logout veya yönlendirme işlemi burada
}
/* function handleLogout() {
  console.log('Logout işlemi burada yapılacak');
} */
/*  const filteredMenus = computed(() => {
  console.log(userr);
    if (!userr || userr.realm_access) return []

    console.log(userr);
     const realmAdminRole = userr.realm_access.roles[1] || ""
     console.log(realmAdminRole);
    return menus.filter((item) => item.roles.includes(realmAdminRole))
}) */
const leftDrawerOpen = ref(false);

/* function toggleLeftDrawer() {
  leftDrawerOpen.value = !leftDrawerOpen.value;
} */
</script>
