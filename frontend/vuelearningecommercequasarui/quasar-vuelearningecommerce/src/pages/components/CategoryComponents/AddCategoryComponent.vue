<template>
  <q-dialog ref="dialogRef" @hide="onDialogHide">
<q-card
  class="q-dialog-plugin bg-white"
  style="width: 600px; max-width: 90vw; border-radius: 16px; box-shadow: 0 4px 20px rgba(0,0,0,0.1);"
>

<q-card-section class="bg-grey-1 q-pa-md border-b">
        <div class="text-h6 text-dark">Kategori Ekle</div>
      </q-card-section>
<q-card-section>

       
    <form @submit.prevent="handleSubmit(onSubmit)()" class="column q-gutter-md">
        <div class="q-gutter-md  column full-width " > 
 <q-input
          v-model="name"
          label="Kategori İsmi"
       :error="!!nameError"
  :error-message="nameError"
            style="border-radius: 12px;"

          dense
               outlined
        />



  
<q-btn label="Kaydet" type="submit" rounded  unelevated class="q-px-lg q-py-sm rounded-borders" style="width: fit-content;" color="primary" />


      
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
import {  Ref } from 'vue'
import axios from 'axios'
import { accessToken, userr } from 'src/boot/keycloak'
import { Category } from 'src/pages/CategoryManagementPage.vue'
const schema = z.object({
  name: z.string().min(3, "En az 3 karakter"),
})
const { handleSubmit } = useForm({
  validationSchema: toFormValidator(schema)
})

const { value: name, errorMessage: nameError } = useField<string>('name')

const props = defineProps<{ categories: Ref<Category[]> }>()



const onSubmit = async (formData: any) => {
  console.log(formData);
  try {
  const form = new FormData()

Object.entries(formData).forEach(([key,value]) => form.append(key,value ))
    const res  = await axios.post('http://localhost:8082/api/v1/categories/category/add-category', {
      name:formData.name,

    } ,
        { headers: { Authorization: `Bearer ${accessToken.value}` } }
    )
    props.categories.value.push(res.data.data)
    onDialogOK() 
  } catch (err: any) {
    console.log(err);
  }
}
defineEmits([

  ...useDialogPluginComponent.emits
])
const { dialogRef, onDialogHide, onDialogOK, onDialogCancel } = useDialogPluginComponent()


</script>