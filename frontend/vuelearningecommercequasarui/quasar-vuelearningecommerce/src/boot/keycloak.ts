import {boot} from 'quasar/wrappers'
import Keycloak, { KeycloakInstance } from 'keycloak-js'
import { ref } from 'vue';

let keycloak :KeycloakInstance | null = null  ;
export const userr = ref<any>(null);
const accessToken = ref<string | null>('')

//burada boot kısımlarını kullandık boot vuejs projelerinde bu axios toast vesaire için galiba utils gibi düşünülebilecek birşey diye düşünüyorum 
export default boot(async () => {
       keycloak = new Keycloak({
  url: "http://localhost:8080", 
    realm: "lenora-ecommerce-realm", 
    clientId: "vuefrontend" 
});

try {
    //bu bize genel olarak eğer kullanıcı authenticated değilse login sayfasına yönlendirme esnekliği veriyor fazladan bir middleware yazmak yerine.


    const authenticated = await keycloak.init({ onLoad: 'login-required', pkceMethod: 'S256'
       
     });
    if (authenticated) {
    /* keycloak.tokenparsed birçok bilgiye de sahip şekilde ayrıca. */
      userr.value = keycloak.tokenParsed;
 accessToken.value = keycloak.token ?? null
    } else {
        console.log('User is not authenticated');
    }
} catch (error) {
    console.error('Failed to initialize adapter:', error);
}}
)
 function logout() {
  keycloak?.logout({ redirectUri: "/authentication" });
}
export {keycloak, accessToken , logout}