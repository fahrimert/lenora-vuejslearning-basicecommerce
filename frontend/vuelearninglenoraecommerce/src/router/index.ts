import Authpage from "@/pages/Authpage.vue"
import HelloPage from "@/pages/HelloPage.vue"
import ProductManagementPage from "@/pages/ProductManagementPage.vue"
import {  , createRouter, createWebHistory, RouteRecordRaw } from "vue-router"
import { vueKeycloak } from '@josempgon/vue-keycloak'
import { computed } from "vue"
import { useKeycloak } from "@josempgon/vue-keycloak"

/* import {adminGuard} from './guards'
 */
const { hasRoles } = useKeycloak()


const routes:RouteRecordRaw[] =  [
{
        path:"/authentication",
        beforeEnter:async(to,from,next) => {
      const keycloak = vueKeycloak.keycloak
          if (!keycloak.authenticated) {
            await keycloak.login()
            
          }else{
            next()
          }
        },
        component:Authpage
},
{
        path:"/product-management",
        beforeEnter:async(to,from,next) => {

          const access = computed(() => hasRoles(['ecommerceadmin']))
      const keycloak = vueKeycloak.keycloak;
          if (!keycloak.authenticated) {
            await keycloak.login()
            console.log(keycloak);
          }else{
            console.log(keycloak);
            if (!access) {
          next("/unauthorized");
        } else {
          next();
        }
          }
        },
        component:ProductManagementPage
},
{
        path:"/hello",
        component:HelloPage
},

/*   {
    path: "/:pathMatch(.*)*",
    component: NotFoundPage
  } */
]

const router = createRouter({
    history:createWebHistory(),
    routes
})

export default router