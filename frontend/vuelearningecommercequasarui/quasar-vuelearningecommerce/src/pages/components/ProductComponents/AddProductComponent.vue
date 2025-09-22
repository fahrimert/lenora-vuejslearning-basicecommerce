<template>
  <q-dialog ref="dialogRef" @hide="onDialogHide">
<q-card
  class="q-dialog-plugin bg-white"
  style="width: 600px; max-width: 90vw; border-radius: 16px; box-shadow: 0 4px 20px rgba(0,0,0,0.1);"
>

<q-card-section class="bg-grey-1 q-pa-md border-b">
        <div class="text-h6 text-dark">Ürün Yönetimi</div>
      </q-card-section>
<q-card-section>

     
    <form @submit.prevent="handleSubmit(onSubmit)()" class="column q-gutter-md">
 <q-input
          v-model="name"
          label="Ürün İsmi"
       :error="!!nameError"
  :error-message="nameError"
            style="border-radius: 12px;"

          dense
               outlined
        />

        <q-input
          v-model="brand"
          label="ürün Markası"
     :error="!!brandError"
  :error-message="brandError"
          dense
                      outlined
        />
     <q-select
  v-model="categoryId"
  :options="categories"          
  option-value="id"           
  option-label="name"        
  filled
   :error="!!categoryError"
  :error-message="categoryError"
  label="Kategori Seçiniz"
  emit-value                   
  map-options                 
  use-chips                   
  class="w-full rounded-xl"
/>


          <q-input
          v-model="description"
          label="Ürün Tanımı"
    :error="!!descriptionError"
  :error-message="descriptionError"
          dense
                      outlined
        />
<q-input          v-model="price"
          label="Ürün Fiyatı"
     :error="!!priceError"
  :error-message="priceError"
          dense
                      outlined
        />

<q-input          v-model="inventory"
          label="Ürün Stok Miktarı"
     :error="!!inventoryError"
  :error-message="inventoryError"
          dense
                      outlined
        />
      

   
<q-btn label="Kaydet" type="submit" rounded  unelevated class="q-px-lg q-py-sm rounded-borders" style="width: fit-content;" color="primary" />



      


    </form>

      </q-card-section>


    </q-card>
  </q-dialog>
</template>

<script  setup lang="ts">
import {useField, useForm } from 'vee-validate'
import { toFormValidator } from '@vee-validate/zod'
import { z } from 'zod'
import { useDialogPluginComponent, useQuasar } from 'quasar'
import { onMounted, Ref, ref } from 'vue'
import axios from 'axios'
import { accessToken } from 'src/boot/keycloak'
import { Category } from 'src/pages/CategoryManagementPage.vue'
import { Product } from 'src/pages/ProductManagementPage.vue'


const schema = z.object({
  name: z.string().min(3, "En az 3 karakter"),
  brand: z.string().min(3, "En az 3 karakter"),
  price: z.string().min(3, "Geçerli email girin"),
  inventory: z.string().min(2 , "Geçerli Sayı giriniz "),
  description: z.string().min(1, "Rol Seçilmeli"),
  categoryId:z.number().min(3,"Category Id İs necessary")
})
const { handleSubmit } = useForm({
  validationSchema: toFormValidator(schema)
})
const categories = ref<Category[]>([])   

const { value: name, errorMessage: nameError } = useField<string | undefined>('name')
const { value: brand, errorMessage: brandError } = useField<string | undefined>('brand')
const { value: inventory, errorMessage: inventoryError } = useField<number | undefined>('inventory')
const { value: price, errorMessage: priceError } = useField<number | undefined>('price')
const { value: description, errorMessage: descriptionError } = useField<string | undefined>('description')
const { value: categoryId , errorMessage: categoryError } = useField<string | undefined>('categoryId')


const error = ref<string | null>(null)
const loading = ref(false)
const props = defineProps<{ products: Ref<Product[]> }>()

async function fetchCategories() {
  loading.value = true
  try {
    const res = await axios.get('http://localhost:8082/api/v1/categories/category/get-all-categories' , 
        { headers: { Authorization: `Bearer ${accessToken.value}` } }
    )
    console.log(res.data.data);
    categories.value = res.data.data 
console.log(categories.value);
    // backend response'a göre ayarla
  } catch (err: any) {
    error.value = err.message || 'Veri çekilemedi'
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchCategories()
})

const $q = useQuasar()

const onSubmit = async (formData: any) => {
  try {

  const form = new FormData()

Object.entries(formData).forEach(([key,value]) => form.append(key,value ))
 const res = await axios.post('http://localhost:8082/api/v1/products/create-products', {
      name:formData.name,
      brand:formData.brand,
    price:formData.price,
      inventory:formData.inventory,
      description:formData.description,
      categoryId:formData.categoryId.toString()
    } ,
        { headers: { Authorization: `Bearer ${accessToken.value}` } }

    )
        props.products.value.push(res.data.data)
    onDialogOK() 
     } catch (err: any) {
    console.log(err);
    $q.notify({ type:'negative', message: err.response?.data?.message || err.message })
  }
}
defineEmits([

  ...useDialogPluginComponent.emits
])
const { dialogRef, onDialogHide , onDialogOK
} = useDialogPluginComponent()

</script>