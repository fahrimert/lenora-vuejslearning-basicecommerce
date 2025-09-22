<template>

  <q-page-container class="column full-width  m-pa-lg " style=" background-color:#faf8f6"  >
      <q-card flat  class= "column full-width q-pa-lg  q-pa-sm--mobile border-b " style=" height: fit-content; background-color:#faf8f6; border-color: #e5e7eb; box-shadow: 0 1px 2px rgba(0,0,0,0.05); background-color:white">
                <q-separator inset />

     <h2   text-subtitle1--mobile border-b style="height: fit-content; font-size: 18px; font-weight: 400; color: #1f2937; letter-spacing: -0.015em"  >
          Kategori Yönetimi
        </h2>
<div class="row  ">
  <div >
       <q-card class=" row  q-pa-lg">
        <q-tabs
          dense
          class="text-grey  row"
          active-color="primary"
          indicator-color="primary"
          align="justify"
          narrow-indicator
        >
        <q-btn outline rounded  color="primary" @click="categoryAddTrigger" no-caps>
Kategori Ekle
    </q-btn>
    <q-tab>        
    <q-btn outline rounded color="primary" @click="categoryUpdateTrigger"  no-caps >
Kategori Güncelle
    </q-btn>
    </q-tab>
          </q-tabs>

        <q-separator />

      </q-card>

 
  </div>


</div>

   

      </q-card>
<div class="column col-grow q-pa-md  q-pa-lg m-pa-lg">
  <div class="column items-start justify-between q-mb-md q-mb-lg">
    <h2 class="text-h6 text-md-h5 text-dark">
      Kategoriler
    </h2>

    <span v-if="loading" class="text-caption text-grey">
      Yükleniyor...
    </span>

    <div v-if="error" class="text-caption text-negative">
      {{ error }}
    </div>
  </div>

  <q-list  separator v-if="categories.length" style="grid ">
 <div class="row q-col-gutter-md justify-between items-stretch">

    <div
      v-for="category in categories"
      :key = "category.id"
      class="col-xs-12 col-sm-6 col-lg-4"
    >
      <q-card
 bordered
        flat
        class="bg-grey-1 cursor-pointer rounded-borders q-pa-md flex justify-end column shadow-box shadow-2"
      >
      <SingleCategoryComponen
      :categories = "categories"
      :category = "category"
  @delete="(id) => { categories = categories.filter(c => c.id.toString() !== id) }"
      />

      </q-card>
    </div>

  </div>
  </q-list>

  <div v-else-if="!loading && !error" class="text-grey">
    Henüz ürün bulunamadı.
  </div>
</div>
 
    </q-page-container >
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useQuasar } from 'quasar'
import axios from 'axios'
import { accessToken } from 'src/boot/keycloak'
import AddCategoryComponent from './components/CategoryComponents/AddCategoryComponent.vue'
import UpdateCategoryComponent from './components/CategoryComponents/UpdateCategoryComponent.vue'
import SingleCategoryComponen from './components/CategoryComponents/SingleCategoryComponen.vue'

const $q = useQuasar()

function categoryAddTrigger () {
  $q.dialog({
    component: AddCategoryComponent,
    componentProps:{
      categories
    }
  }).onOk((v) => {
    fetchCategories()
  })
}

function categoryUpdateTrigger () {
  $q.dialog({
    component: UpdateCategoryComponent,
      componentProps:{
      categories
    }
  }).onOk(() => {
    fetchCategories() 
  })
}

export type Category = {
      id: number,
      name: string  | null    
}

const loading = ref(false)
const categories = ref<Category[]>([]) 
const error = ref<string | null>(null)


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


</script>

<style lang="scss" scoped>

</style>