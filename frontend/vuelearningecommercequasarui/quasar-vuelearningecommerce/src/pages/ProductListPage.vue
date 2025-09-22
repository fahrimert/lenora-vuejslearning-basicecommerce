<template>

  <q-page-container class="column full-width  m-pa-lg " style=" background-color:#faf8f6"  >
      <q-card flat  class= "column full-width q-pa-lg  q-pa-sm--mobile border-b " style=" height: fit-content; background-color:#faf8f6; border-color: #e5e7eb; box-shadow: 0 1px 2px rgba(0,0,0,0.05); background-color:white">
                <q-separator inset />
   
                
                    <h2    text-subtitle1--mobile border-b style="height: fit-content; font-size: 18px; font-weight: 400; color: #1f2937; letter-spacing: -0.015em"  >
                          Ürün Listesi
                        </h2>
   <div class="column col-grow  ">
  <div >
 <span v-if="loading" class="text-caption text-grey">
      Yükleniyor...
    </span>

    <!-- error -->
    <div v-if="error" class="text-caption text-negative">
      {{ error }}
    </div>
  </div>



</div>
   

      </q-card>
<div class="column col-grow q-pa-md q-pa-md-lg">
  <div class="column items-start justify-between q-mb-md q-mb-lg">
    <h2 class="text-h5 text-md-h5 text-dark">
      Ürünler Listesi
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

 <q-table
  title="Ürünler"
  :rows="filteredProducts"
  :columns="columns"
  row-key="id"
  :filter="filter"
>
  <template v-slot:top>
    <q-input
      dense
      debounce="300"
      v-model="filter"
      placeholder="Ara..."
      clearable
    />
<q-select
  v-model="selectedCategory"
  :options="categories.map(c => ({ label: c.name, value: c.id }))"
  label="Kategori Seçiniz"
  clearable
/>
  </template>
</q-table>

</div>
 
    </q-page-container >
</template>
<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useQuasar } from 'quasar'
import axios from 'axios'
import { accessToken } from 'src/boot/keycloak'
import UpdateProductComponent from './components/ProductComponents/UpdateProductComponent.vue'
import AddProductComponent from './components/ProductComponents/AddProductComponent.vue'
import { Category } from './CategoryManagementPage.vue'


const columns = [ 
  { name: 'id', label: 'ID', field: 'id', sortable: true },
  { name: 'name', label: 'Ürün Adı', field: 'name', sortable: true },
  { name: 'brand', label: 'Marka', field: 'brand', sortable: true },
  { name: 'price', label: 'Fiyat', field: 'price', sortable: true },
  { name: 'category.name', label: 'Fiyat', field: 'price', sortable: true },


]
const filter = ref('')  // bu input ile bağlanacak
const productId = ref('')  // bu input ile bağlanacak

// Quasar instance
const $q = useQuasar()

// Dialog açma
function producAddTrigger () {
  $q.dialog({
    component: AddProductComponent,
    componentProps:{
      products
    }
  }).onOk(  () => {
    fetchProducts()
  })
}

function updateProductTrigger () {
  $q.dialog({
    component: UpdateProductComponent,
            componentProps:{
      products
    }
  }).onOk(  () => {
     fetchProducts() 
  }

)
}

export type Category = {
      id: number,
      name: string  | null    
}



async function fetchCategories() {
  loading.value = true
  try {
    const res = await axios.get('http://localhost:8082/api/v1/categories/category/get-all-categories' , 
        { headers: { Authorization: `Bearer ${accessToken.value}` } }
    )
    categories.value = res.data.data 
  } catch (err: any) {
    error.value = err.message || 'Veri çekilemedi'
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchCategories()
})


const selectedCategory = ref<number | null>(null)



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

const loading = ref(false)
const products = ref<Product[]>([])   
const categories = ref<Category[]>([])   

console.log(products.value);
const error = ref<string | null>(null)

async function fetchProducts() {
  loading.value = true
  try {
    const res = await axios.get('http://localhost:8082/api/v1/products' , 
        { headers: { Authorization: `Bearer ${accessToken.value}` } }
    )
    products.value = res.data.data 
    console.log(res);
  } catch (err: any) {
    error.value = err.message || 'Veri çekilemedi'
  } finally {
    loading.value = false
  }
}
const filteredProducts = computed(() => {
  console.log(selectedCategory.value);
  if (!selectedCategory.value) return products.value;
  console.log(products.value.map(p => p.category));
  return products.value.filter(p => p.category.id == selectedCategory.value.value )

})
// sayfa açılınca yükle
onMounted(async () => {
  await fetchProducts()
})
</script>

<style lang="scss" scoped>

</style>