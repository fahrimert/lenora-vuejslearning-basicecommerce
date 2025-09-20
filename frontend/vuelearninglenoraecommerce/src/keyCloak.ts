import Keycloak from 'keycloak-js';
import { ref } from 'vue';

const keycloak = new Keycloak({
  url: "http://localhost:8080",
  realm: "lenora-ecommerce-realm",
  clientId: "vuefrontend"
});

export const userr = ref<any>(null);

export async function initKeycloak() {
  try {
    const authenticated = await keycloak.init({ onLoad: 'login-required', pkceMethod: 'S256' });
    if (authenticated) {
      userr.value = keycloak.tokenParsed;
      console.log("Authenticated user:", userr.value);
    } else {
      keycloak.login();
    }
  } catch (err) {
    console.error("Keycloak initialization failed:", err);
  }
}

export { keycloak };
