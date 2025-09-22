<template>
  <q-dialog ref="dialogRef" @hide="onDialogHide">
<q-card
  class="q-dialog-plugin bg-white"
  style="width: 600px; max-width: 90vw; border-radius: 16px; box-shadow: 0 4px 20px rgba(0,0,0,0.1);"
>

<q-card-section class="bg-grey-1 q-pa-md border-b">
        <div class="text-h6 text-dark">Kategori Güncelle</div>
      </q-card-section>
<q-card-section>

 
    <form @submit.prevent="handleSubmit(onSubmit)()" class="column q-gutter-md">
        <div class="q-gutter-md  column full-width " > 
<q-select
  v-model="categoryId"
  :options="categories"          
  option-value="id"           
  option-label="name"        
  filled
  label="Kategori Seçiniz"
  emit-value                   
  map-options                 
  use-chips                   
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
<!-- 
        <q-input
          v-model="brand"
          label="ürün Markası"
     :error="!!brandError"
  :error-message="brandError"
          dense
                      outlined
        /> -->
<!-- 
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
        /> -->
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
import { accessToken, userr } from 'src/boot/keycloak'
import { Category } from 'src/pages/CategoryManagementPage.vue'
const schema = z.object({
  name: z.string().min(3, "En az 3 karakter"),
})
const { handleSubmit } = useForm({
  validationSchema: toFormValidator(schema)
})
const props = defineProps<{ categories: Ref<Category[]> }>()

const { value: name, errorMessage: nameError } = useField<string | undefined>('name')



const $q = useQuasar()

console.log(userr.value);
console.log(accessToken.value);

const categories = ref<Category[]>([])   // array olması daha doğru
const error = ref<string | null>(null)
// Veri çekme
const loading = ref(false)
const categoryId = ref("")


async function fetchCategories() {
  loading.value = true
  try {
    const res = await axios.get('http://localhost:8082/api/v1/categories/category/get-all-categories' , 
        { headers: { Authorization: `Bearer ${accessToken.value}` } }
    )
    
    console.log(res.data.data);
    categories.value = res.data.data 



    // backend response'a göre ayarla
  } catch (err: any) {
    error.value = err.message || 'Veri çekilemedi'
  } finally {
    loading.value = false
  }
}

// sayfa açılınca yükle
onMounted(() => {
  fetchCategories()
})






const onSubmit = async (formData: any) => {
  console.log(formData);
  try {
     console.log('✅ Form Data:', formData)
  const form = new FormData()

Object.entries(formData).forEach(([key,value]) => form.append(key,value ))
    console.log(formData);
    console.log(accessToken.value);
    console.log(categoryId.value);
  const res=  await axios.put(`http://localhost:8082/api/v1/categories/category/${categoryId.value}/update`, {
      name:formData.name,
    } ,
        { headers: { Authorization: `Bearer ${accessToken.value}` } }

    )
     const updatedCategory = res.data.data
const index = props.categories.value.findIndex(c => c.id === updatedCategory.id)
if (index !== -1) {
  props.categories.value[index] = updatedCategory
} else {
  props.categories.value.push(updatedCategory)
}
    onDialogOK() 

  } catch (err: any) {
    console.log(err);
  }

}
defineEmits([
  // REQUIRED; need to specify some events that your
  // component will emit through useDialogPluginComponent()
  ...useDialogPluginComponent.emits
])
const { dialogRef, onDialogHide, onDialogOK, onDialogCancel } = useDialogPluginComponent()
// dialogRef      - Vue ref to be applied to QDialog
// onDialogHide   - Function to be used as handler for @hide on QDialog
// onDialogOK     - Function to call to settle dialog with "ok" outcome
//                    example: onDialogOK() - no payload
//                    example: onDialogOK({ /*...*/ }) - with payload
// onDialogCancel - Function to call to settle dialog with "cancel" outcome

// this is part of our example (so not required)

</script>