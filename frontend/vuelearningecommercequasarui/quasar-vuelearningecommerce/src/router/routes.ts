import type { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    
    component: () => import('layouts/MainLayout.vue'),
    children: [{ path: '', component: () => import('pages/IndexPage.vue')  }
       
      ,
         
    {
    path: 'product-management',
    component: () => import('pages/ProductManagementPage.vue'),
  },
   {
    /* 20.09 buradan devam et  */
    path: 'product-.anagement',
    component: () => import('pages/ProductManagementPage.vue'),
  },
     ],
  },



  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue'),
  },
];

export default routes;
