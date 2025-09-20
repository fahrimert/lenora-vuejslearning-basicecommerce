import { defineRouter } from '#q-app/wrappers';
import {
  createRouter,
  createWebHistory,
} from 'vue-router';
import routes from './routes';


export default defineRouter(function (/* { store, ssrContext } */) {

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    routes,

    // Leave this as is and make changes in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath
    history: createWebHistory(),
  });
Router.beforeEach((to, from, next) => {
  if (to.query.code || to.query.state) {
    const clean = { ...to, query: {} }
    next(clean) // URL’yi temizle
  } else {
    next()
  }
})
  return Router;
});
