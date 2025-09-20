import { boot } from 'quasar/wrappers'
import Toast from "vue-toastification"
import "vue-toastification/dist/index.css"

export default boot(({ app }) => {
  app.use(Toast, {
    position: "top-right",
    timeout: 3000,
    closeOnClick: true,
    pauseOnHover: true,
  })
})
