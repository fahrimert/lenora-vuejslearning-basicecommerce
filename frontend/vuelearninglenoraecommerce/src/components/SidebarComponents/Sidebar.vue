<template>
   <div className="flex h-screen w-full bg-white">
      <div
      :class="cn(
        'flex h-full flex-col border-r border-gray-200 bg-[#faf8f6] transition-all duration-300',
        isCollapsed ? 'w-16' : 'w-60 md:w-[260px]'
      )"
    >
      <div class="flex items-center justify-between px-2 md:px-4 mb-4">
        <span v-if="!isCollapsed" class="font-bold text-gray-800 text-lg">Panel</span>
        <button class="h-8 w-8" @click="isCollapsed = !isCollapsed">
          <component :is="isCollapsed ? PanelRightClose : PanelLeftClose" class="h-5 w-5" />
        </button>
      </div>

    <div v-if="!isCollapsed" class="mb-6 hidden md:flex items-center gap-3 px-2">
        <div class="flex h-10 w-10 items-center justify-center rounded-lg bg-[#ec4404] text-white">
        </div>
      <div>
    <p v-if="!user">Henüz giriş yapılmadı</p>
<!--     <p v-else>Kullanıcı: {{ user.preferred_username }}</p>
 -->  </div>
      </div>

      <div class="flex flex-1 flex-col gap-1">
        <router-link
          v-for="item in filteredMenus"
          :key="item.to"
          :to="item.to"
          :class="cn(
            'flex items-center gap-3 rounded-lg px-3 py-3 text-[15px] font-medium text-gray-700 hover:bg-[#ec4404] hover:text-white',
            route.path === item.to && 'bg-[#ec4404] font-semibold text-white',
            isCollapsed && 'justify-center'
          )"
        >
          <span v-if="!isCollapsed" class="hidden md:inline">{{ item.label }}</span>
        </router-link>
      </div>
           <div class="mt-auto border-t pt-4">
        <button
          @click="handleLogout"
          :class="cn(
            'flex w-full items-center gap-2 px-3 text-[15px] font-medium text-gray-700 hover:bg-[#ec4404] hover:text-white',
            isCollapsed ? 'justify-center' : 'justify-start'
          )"
        >
          <HiOutlineLogout class="w-5 h-5" />
          <span v-if="!isCollapsed">Çıkış Yapın</span>
        </button>
      </div>
      </div>

      <div className="flex flex-1 flex-col overflow-y-auto">{children}</div>
    </div>
   <div class="flex flex-col w-60 border-r border-gray-200 bg-[#faf8f6]">
    <div v-for="item in filteredMenus" :key="item.to">
      <router-link :to="item.to" class="px-3 py-2 flex items-center gap-2">
        <span>{{ item.label }}</span>
      </router-link>
    </div>
  </div>
     <div className="flex h-screen w-full bg-white">
      <div
      :class="cn(
        'flex h-full flex-col border-r border-gray-200 bg-[#faf8f6] transition-all duration-300',
        isCollapsed ? 'w-16' : 'w-60 md:w-[260px]'
      )"
    >
      <div class="flex items-center justify-between px-2 md:px-4 mb-4">
        <span v-if="!isCollapsed" class="font-bold text-gray-800 text-lg">Panel</span>
        <button class="h-8 w-8" @click="isCollapsed = !isCollapsed">
          <component :is="isCollapsed ? PanelRightClose : PanelLeftClose" class="h-5 w-5" />
        </button>
      </div>

    <div v-if="!isCollapsed" class="mb-6 hidden md:flex items-center gap-3 px-2">
        <div class="flex h-10 w-10 items-center justify-center rounded-lg bg-[#ec4404] text-white">
        </div>
      <div>
    <p v-if="!user">Henüz giriş yapılmadı</p>
<!--     <p v-else>Kullanıcı: {{ user.preferred_username }}</p>
 -->  </div>
      </div>

      <div class="flex flex-1 flex-col gap-1">
        <router-link
          v-for="item in filteredMenus"
          :key="item.to"
          :to="item.to"
          :class="cn(
            'flex items-center gap-3 rounded-lg px-3 py-3 text-[15px] font-medium text-gray-700 hover:bg-[#ec4404] hover:text-white',
            route.path === item.to && 'bg-[#ec4404] font-semibold text-white',
            isCollapsed && 'justify-center'
          )"
        >
          <span v-if="!isCollapsed" class="hidden md:inline">{{ item.label }}</span>
        </router-link>
      </div>
           <div class="mt-auto border-t pt-4">
        <button
          @click="handleLogout"
          :class="cn(
            'flex w-full items-center gap-2 px-3 text-[15px] font-medium text-gray-700 hover:bg-[#ec4404] hover:text-white',
            isCollapsed ? 'justify-center' : 'justify-start'
          )"
        >
          <HiOutlineLogout class="w-5 h-5" />
          <span v-if="!isCollapsed">Çıkış Yapın</span>
        </button>
      </div>
      </div>

      <div className="flex flex-1 flex-col overflow-y-auto">{children}</div>
    </div>
   <div class="flex flex-col w-60 border-r border-gray-200 bg-[#faf8f6]">
    <div v-for="item in filteredMenus" :key="item.to">
      <router-link :to="item.to" class="px-3 py-2 flex items-center gap-2">
        <span>{{ item.label }}</span>
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import{ref,computed} from 'vue'
import { useRoute } from 'vue-router';
import type { Ref } from 'vue';
import { cn } from '@/lib/utils';
import { LogOut, PanelLeftClose, PanelRightClose } from 'lucide-vue-next';

interface MenuItem {
  to: string;
  label: string;
  roles: string[];
}

const props = defineProps<{ user: any }>();
const isCollapsed = ref(false);
const route = useRoute();
const menus : MenuItem[] = [
    { to: "/", label: "Anasayfa",  roles: ["ecommerceadmin"] },

    { to: "/product-management", label: "Ürün Yönetimi",  roles: ["ecommerceadmin"] },
]
function handleLogout() {
  console.log('Logout işlemi burada yapılacak');
}
 const filteredMenus = computed(() => {
  console.log(props);
    if (!props.user || !props.user.realm_access) return []

    console.log(props);
     const realmAdminRole = props.user.realm_access.roles[1] || ""
     console.log(realmAdminRole);
    return menus.filter((item) => item.roles.includes(realmAdminRole))
})

</script>

<style lang="scss" scoped>

</style>