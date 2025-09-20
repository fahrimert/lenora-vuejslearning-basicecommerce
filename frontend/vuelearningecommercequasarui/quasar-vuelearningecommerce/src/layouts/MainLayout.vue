  <template>
  <q-layout view="lHh Lpr lFf">
<!--     <q-header elevated>
      <q-toolbar>
        <q-btn flat dense round icon="menu" aria-label="Menu" @click="toggleLeftDrawer" />

        <q-toolbar-title> Quasar App </q-toolbar-title>

        <div>Quasar v{{ $q.version }}</div>
      </q-toolbar>
    </q-header>
 -->
    <q-drawer v-model="leftDrawerOpen" show-if-above bordered>
      <q-list>
        <q-item-label style="font-size: 18px;"> Ürün Yönetim Uygulaması </q-item-label>

        <EssentialLink v-for="link in menus" :key="link.label" v-bind="link" />
      </q-list>
    </q-drawer>
      <router-view /> 
  </q-layout>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import EssentialLink from 'components/EssentialLink.vue';
import { userr  } from 'src/boot/keycloak';

interface MenuItem {
  to: string;
  label: string;
  roles: string[];
}
const menus : MenuItem[] = [
  { to: "/category-management", label: "Kategori Yönetimi",  roles: ["ecommerceadmin"] },
    { to: "/product-management", label: "Ürün Yönetimi",  roles: ["ecommerceadmin"] },

  ]
console.log(userr);
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
