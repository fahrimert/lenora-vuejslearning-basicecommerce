import {boot} from 'quasar/wrappers'
import Keycloak from 'keycloak-js'
import { ref } from 'vue';

let keycloak
export const userr = ref(null);
const accessToken = ref(null)
export default boot(async () => {
      const keycloak = new Keycloak({
  url: "http://localhost:8080", 
    realm: "lenora-ecommerce-realm", 
    clientId: "vuefrontend" 
});

 console.log(keycloak);
try {
    const authenticated = await keycloak.init({ onLoad: 'login-required', pkceMethod: 'S256'
       
     });
    console.log(authenticated);
    console.log(keycloak.tokenParsed);
    if (authenticated) {
      userr.value = keycloak.tokenParsed;
 accessToken.value = keycloak.token

        console.log('User is authenticated');
    } else {
        console.log('User is not authenticated');
    }
} catch (error) {
    console.error('Failed to initialize adapter:', error);
}}
)

export {keycloak, accessToken}