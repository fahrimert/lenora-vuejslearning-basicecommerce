<template>

  <q-page-container class="row full-width" style=" background-color:#faf8f6"  >
      <q-card flat  class= "column full-width q-pa-lg  q-pa-sm--mobile border-b " style=" height: fit-content; border-color: #e5e7eb; box-shadow: 0 1px 2px rgba(0,0,0,0.05); background-color:white">
   
   <div class="q-pa-lg">
     <h2   text-subtitle1--mobile border-b style="height: fit-content; font-size: 18px; font-weight: 600; color: #1f2937; letter-spacing: -0.015em"  >
          Kullanıcı Yönetimi
        </h2>
<div class="row g-gutter-md ">
  <div class=" q-gutter-sm row">
    <q-btn color="primary" @click="producAddTrigger" >
Ürün Ekle
    </q-btn>
  </div>

    <div class=" q-gutter-sm">
    <q-btn color="primary" @click="producAddTrigger" >
Ürün Güncelle
    </q-btn>
  </div>
</div>

</div>
   

      </q-card>
<div class="column col-grow q-pa-md q-pa-md-lg">
  <div class="column items-start justify-between q-mb-md q-mb-lg">
    <h2 class="text-h6 text-md-h5 text-dark">
      Ürünler
    </h2>

    <!-- loading -->
    <span v-if="loading" class="text-caption text-grey">
      Yükleniyor...
    </span>

    <!-- error -->
    <div v-if="error" class="text-caption text-negative">
      {{ error }}
    </div>
  </div>

  <!-- ürün listesi -->
  <q-list bordered separator v-if="products.length" style="grid grid-column: re;">
 <div class="row q-col-gutter-md justify-center items-stretch">

    <div
      v-for="product in products"
      :key="product.id"
      class="col-xs-12 col-sm-6 col-lg-4"
    >
      <q-card
        flat
        bordered
        class="bg-grey-1 cursor-pointer rounded-borders q-pa-md flex flex-center column"
      >
      <SingleProductComponen
      :product = "product"
      />
  <!--       <SingleBrandComponent
          :brand="brand"
          :handleDelete="handleDelete"
          @update="onUpdate"
        /> -->
      </q-card>
    </div>

  </div>
  </q-list>

  <!-- hiç ürün yoksa -->
  <div v-else-if="!loading && !error" class="text-grey">
    Henüz ürün bulunamadı.
  </div>
</div>
 
    </q-page-container >
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { useQuasar } from 'quasar'
import axios from 'axios'
import { accessToken } from 'src/boot/keycloak'
import SingleProductComponen from './components/ProductComponents/SingleProductComponen.vue'
import UpdateProductComponent from './components/ProductComponents/UpdateProductComponent.vue'
import AddProductComponent from './components/ProductComponents/AddProductComponent.vue'

// Quasar instance
const $q = useQuasar()

// Dialog açma
function producAddTrigger () {
  $q.dialog({
    component: AddProductComponent,
  }).onOk(() => {
    fetchProducts() // ✅ yeni ürün eklendikten sonra listeyi yenile
  })
}

function updateProductTrigger () {
  $q.dialog({
    component: UpdateProductComponent,
  }).onOk(() => {
    fetchProducts() // ✅ yeni ürün eklendikten sonra listeyi yenile
  })
}


// Tipler
export type Product = {
  id: number
  name: string
  brand: string
  description: string
  price: number
  inventory: number
  category: {
    id: number
    name: string | null
  }
  imagess: []
}

const route = useRoute()
const loading = ref(false)
const products = ref<Product[]>([])   // array olması daha doğru
const error = ref<string | null>(null)

// Veri çekme
async function fetchProducts() {
  loading.value = true
  try {
    const res = await axios.get('http://localhost:8082/api/v1/products' , 
        { headers: { Authorization: `Bearer ${accessToken.value}` } }
    )
    products.value = res.data.data // backend response'a göre ayarla
    console.log(res);
  } catch (err: any) {
    error.value = err.message || 'Veri çekilemedi'
  } finally {
    loading.value = false
  }
}

// sayfa açılınca yükle
onMounted(() => {
  fetchProducts()
})
</script>

<style lang="scss" scoped>

</style>