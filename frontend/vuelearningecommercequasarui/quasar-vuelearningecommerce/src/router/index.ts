import { defineRouter } from '#q-app/wrappers';
import {
  createRouter,
  createWebHistory,
} from 'vue-router';
import routes from './routes';
import  { accessToken, logout } from 'src/boot/keycloak';


export default defineRouter(function (/* { store, ssrContext } */) {

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    routes,
    history: createWebHistory(),
  });
Router.beforeEach((to, from, next) => {
  if (to.query.code || to.query.state) {
    const clean = { ...to, query: {} }
    next(clean) // URL’yi temizle
  } 

if (accessToken.value) {
  const tokenParts = accessToken.value.split('.')
  if (tokenParts.length === 3) {
    const payload = JSON.parse(atob(tokenParts[1]!))
    const isExpired = payload.exp * 1000 < Date.now();
  
    if (isExpired) {
                console.log('Token expired, logging out...');

         logout()
         return;
    }
  
  }
}
      next();
});

  return Router;
});
