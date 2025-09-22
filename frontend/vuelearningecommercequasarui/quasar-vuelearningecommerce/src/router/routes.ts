import type { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [{ path: '', component: () => import('pages/IndexPage.vue')  },
    {
    path: 'product-management',
    component: () => import('pages/ProductManagementPage.vue'),
  },
   {
    path: 'category-management',
    component: () => import('pages/CategoryManagementPage.vue'),
  },
  {
    path: 'productList',
    component: () => import('pages/ProductListPage.vue'),
  },
     ],
  },

  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue'),
  },
];

export default routes;
