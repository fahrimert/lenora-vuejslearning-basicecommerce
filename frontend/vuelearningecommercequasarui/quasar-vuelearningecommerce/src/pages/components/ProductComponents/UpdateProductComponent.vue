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

          <q-scroll-area class=" bg-white p-4 rounded-lg shadow-md"
          style="height: 500px;"
          >
    <form @submit.prevent="handleSubmit(onSubmit)()" class="column q-gutter-md">
        <div class="q-gutter-md  column full-width " > 
                <q-select
  v-model="productId"
:options="products" 
  option-value="id"           
  option-label="name"        
  filled
  label="Ürün Seçiniz"
       :error="!!productError"
  :error-message="productError"
  emit-value                   
  map-options                 
  use-chips        
   :loading="loading"
  :disable="loading"           
  class="w-full rounded-xl"
/>
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
     :error="!!categoryError"
  :error-message="categoryError"
  filled
  label="Kategori Seçiniz"
  emit-value                   
  map-options                 
  use-chips        
   :loading="loading"
  :disable="loading"           
  class="w-full rounded-xl"
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
        <q-input
          v-model="description"
          label="Ürün Tanımı"
    :error="!!descriptionError"
  :error-message="descriptionError"
          dense
                      outlined
        />

     
<q-btn label="Kaydet" type="submit" unelevated class="q-px-lg q-py-sm rounded-borders" color="primary" />


<!-- 
        <q-select
          v-model="category"
          :options="categoryOptions"
          label="Rol Seçiniz"
          :error="errors.category"
          dense
        /> -->

      
        </div>


    </form>

          </q-scroll-area>
      </q-card-section>


    </q-card>
  </q-dialog>
</template>

<script  setup lang="ts">
import {useField, useForm } from 'vee-validate'
import { toFormValidator } from '@vee-validate/zod'
import { z } from 'zod'
import { useDialogPluginComponent } from 'quasar'
import { onMounted, ref } from 'vue'
import axios from 'axios'
import { accessToken, userr } from 'src/boot/keycloak'
import { Product } from 'src/pages/ProductManagementPage.vue'
const schema = z.object({
  name: z.string().min(3, "En az 3 karakter"),
  brand: z.string().min(3, "En az 3 karakter"),
  price: z.string().min(3, "Geçerli email girin"),
  inventory: z.string().min(2 , "Geçerli Sayı giriniz "),
  description: z.string().min(1, "Rol Seçilmeli"),
  productId:z.number().min(3,"Product Id İs necessary"),
  categoryId:z.number().min(3,"Category Id İs necessary")

})
const { handleSubmit } = useForm({
  validationSchema: toFormValidator(schema)
})

const { value: name, errorMessage: nameError } = useField<string>('name')
const { value: brand, errorMessage: brandError } = useField<string>('brand')
const { value: inventory, errorMessage: inventoryError } = useField<number>('inventory')
const { value: price, errorMessage: priceError } = useField<number>('price')

const { value: description, errorMessage: descriptionError } = useField<string>('description')
const { value: productId , errorMessage:productError } = useField<string>('productId')

const { value: categoryId, errorMessage:categoryError } = useField<string>('categoryId')



console.log(userr.value);
console.log(accessToken.value);
const error = ref<string | null>(null)
const loading = ref(false)
const products = ref<Product[]>([])   



async function fetchProducts() {
  loading.value = true
  try {
    const res = await axios.get('http://localhost:8082/api/v1/products' , 
        { headers: { Authorization: `Bearer ${accessToken.value}` } }
    )
    console.log(res.data.data);
    console.log(res.data.data);
    console.log(accessToken);
      const updatedCategory = res.data.data
const index  = products.value.findIndex(c => c.id === updatedCategory.id)
if (index !== -1) {
  products.value[index] = updatedCategory
} else {
  products.value.push(updatedCategory)
} 
console.log(res.data.data);
    products.value = res.data.data 
console.log(products.value);
    console.log(res);
  } catch (err: any) {
    console.log(err);
    error.value = err.message || 'Veri çekilemedi'
  } finally {
    loading.value = false
  }
}

console.log(products.value);
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

// sayfa açılınca yükle
onMounted( async () => {
  fetchProducts()
  fetchCategories()
})
const onSubmit = async (formData: FormData) => {
  console.log(formData);
  try {
  const form = new FormData()

Object.entries(formData).forEach(([key,value]) => form.append(key,value ))
    console.log(formData);
    console.log(accessToken.value);
  const res = await axios.put(`http://localhost:8082/api/v1/products/${productId.value}/update `, {
      name:formData.name,
      brand:formData.brand,
      price:formData.price,
      inventory:formData.inventory,
      description:formData.description,
        categoryId:formData.categoryId.toString()
    } ,
        { headers: { Authorization: `Bearer ${accessToken.value}` } }

    )
      const updatedProduct = res.data.data
const index = products.value.findIndex(c => c.id === updatedProduct.id)
if (index !== -1) {
  products.value[index] = updatedProduct
} else {
  products.value.push(updatedProduct)
}
    onDialogOK() 
  } 
  catch (err) {
    console.log(err);

  }
}
defineEmits([
  // REQUIRED; need to specify some events that your
  // component will emit through useDialogPluginComponent()
  ...useDialogPluginComponent.emits
])
const { dialogRef, onDialogHide , onDialogOK  } = useDialogPluginComponent()
// dialogRef      - Vue ref to be applied to QDialog
// onDialogHide   - Function to be used as handler for @hide on QDialog
// onDialogOK     - Function to call to settle dialog with "ok" outcome
//                    example: onDialogOK() - no payload
//                    example: onDialogOK({ /*...*/ }) - with payload
// onDialogCancel - Function to call to settle dialog with "cancel" outcome

// this is part of our example (so not required)

</script>