<template>
  <q-dialog ref="dialogRef" @hide="onDialogHide">
<q-card
  class="q-dialog-plugin bg-white"
  style="width: 600px; max-width: 90vw; border-radius: 16px; box-shadow: 0 4px 20px rgba(0,0,0,0.1);"
>

<q-card-section class="bg-grey-1 q-pa-md border-b">
        <div class="text-h6 text-dark">Kullanıcı Yönetimi</div>
      </q-card-section>
<q-card-section>

          <q-scroll-area class=" bg-white p-4 rounded-lg shadow-md"
          style="height: 500px;"
          >
    <form @submit.prevent="handleSubmit(onSubmit)()" class="column q-gutter-md">
        <div class="q-gutter-md  column full-width " > 
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
import { useDialogPluginComponent, useQuasar } from 'quasar'
import { reactive } from 'vue'
import axios from 'axios'
import { accessToken, userr } from 'src/boot/keycloak'
const schema = z.object({
  name: z.string().min(3, "En az 3 karakter"),
  brand: z.string().min(3, "En az 3 karakter"),
  price: z.string().min(3, "Geçerli email girin"),
  inventory: z.string().min(2 , "Geçerli Sayı giriniz "),
  description: z.string().min(1, "Rol Seçilmeli"),
})
const { handleSubmit } = useForm({
  validationSchema: toFormValidator(schema)
})

const { value: name, errorMessage: nameError } = useField('name')
const { value: brand, errorMessage: brandError } = useField('brand')
const { value: inventory, errorMessage: inventoryError } = useField('inventory')
const { value: price, errorMessage: priceError } = useField('price')

const { value: description, errorMessage: descriptionError } = useField('description')



const $q = useQuasar()

console.log(userr.value);
console.log(accessToken.value);
const onSubmit = async (formData: any) => {
  console.log(formData);
  try {
     console.log('✅ Form Data:', formData)
  const form = new FormData()

Object.entries(formData).forEach(([key,value]) => form.append(key,value ))
    console.log(formData);
    console.log(accessToken.value);
    await axios.post('http://localhost:8082/api/v1/products/create-products', {
      name:formData.name,
      brand:formData.brand,
      price:formData.price,
      inventory:formData.inventory,
      description:formData.description,

    } ,
        { headers: { Authorization: `Bearer ${accessToken.value}` } }

    )
  } catch (err: any) {
    console.log(err);
    $q.notify({ type:'negative', message: err.response?.data?.message || err.message })
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