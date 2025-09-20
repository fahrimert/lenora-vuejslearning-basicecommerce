import App from './App.vue'
import Toast from "vue-toastification"
import "vue-toastification/dist/index.css"
import './assets/main.css'
import router from './router'
import { vueKeycloak } from '@josempgon/vue-keycloak'
import { createApp } from 'vue'
import Keycloak from 'keycloak-js';
import { initKeycloak } from './keyCloak'

async function bootstrap() {
  console.log(">>> Bootstrap başladı")

  const app = createApp(App)
  await initKeycloak();

  console.log(">>> Vue app oluşturuldu")

  app.use(Toast, {
    position: "top-right",
    timeout: 3000,
    closeOnClick: true,
    pauseOnHover: true,
  })
  console.log(">>> Toast plugin yüklendi")

  app.use(router)
  console.log(">>> Router yüklendi")

  try {
    console.log(">>> Keycloak plugin yükleniyor...")
    const keycloak = new Keycloak({
  url: "http://localhost:8080", 
    realm: "lenora-ecommerce-realm", 
    clientId: "vuefrontend" 
});

try {
    const authenticated = await keycloak.init();
    console.log(authenticated);
    if (authenticated) {
        console.log('User is authenticated');
    } else {
        console.log('User is not authenticated');
    }
} catch (error) {
    console.error('Failed to initialize adapter:', error);
}
 
    console.log(">>> Keycloak plugin yüklendi")
  } catch (error) {
    console.log(">>> Keycloak plugin yüklenirken hata:", error)
  }

  app.mount('#app')
  console.log(">>> Vue app mount edildi")
}

bootstrap()
