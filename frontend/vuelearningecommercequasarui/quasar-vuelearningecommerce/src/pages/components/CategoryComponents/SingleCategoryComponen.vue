 <template>
 <q-card 
 flat
     class="column q-pa-lg bg-white rounded-borders shadow-2  cursor-pointer transition-all"
     style="max-width: 480px; width: 100%"
     >
     
       <div class="text-h6 text-dark q-mb-md" >Kategori Detayları</div>
              
      <div class="text-dark q-mb-md row" style="font-size: 16px;">
        Kategori İsmi:
        {{ category?.name }}
      </div>
 </q-card>
 <div class="row justify-end " style="background-color: white;">
    <q-btn 
      class="glossy" 
      rounded 
      color="red" 
      style="width: fit-content;"  
      @click="handleOpenDialog(category.id.toString())"
    >
      Sil
    </q-btn>
  </div>

   <q-dialog v-model="openDeleteDialog">
      <q-card style="width: 400; border-radius: 16px; box-shadow: 0 4px 20px  rgba(0,0,0,0.1);" class="bg-white">
        <q-card-section class="text-h6 q-pt-md q-pb-none">
          <div class="text-h6">      Kategoriyi silmeye emin misiniz?
</div>
        </q-card-section>

        <q-card-section class="q-pt-none q-pb-md text-subtitle2 text-grey-8">
      Bu işlem geri alınamaz. Kategoriyi silmek istediğinizden emin olun.
        </q-card-section>

        <q-card-actions align="right">
         <q-btn
        flat
        label="İptal"
        color="secondary"
        @click="handleCloseDialog"
      />
      <q-btn
        unelevated
        color="negative"
        label="Sil"
        @click="() => { deleteCategory(categoryId) }"
      />
   </q-card-actions>
      </q-card>
    </q-dialog>
 </template>
 
 <script setup lang="ts">

const props = defineProps<{
    category: Category
    categories :Category[]
 }>()
import axios from 'axios';
import { accessToken } from 'src/boot/keycloak';
import { Category } from 'src/pages/CategoryManagementPage.vue';
 import { ref } from 'vue'
 const openDeleteDialog = ref(false)
 const categoryId = ref<string>("")
  async function handleOpenDialog(id:string) {
  try {
    categoryId.value = id
    console.log(categoryId);
    console.log(openDeleteDialog.value);
    openDeleteDialog.value = true
    // backend response'a göre ayarla
  } catch (err: any) {
    console.log(err.message);
  } 
}
const emit = defineEmits<{
  (e: 'delete', id: string): void
}>()

function handleCloseDialog() {
  openDeleteDialog.value = false; 
}
async function deleteCategory(id:string) {
  try {
   const res = await axios.delete(`http://localhost:8082/api/v1/categories/category/${id}/delete` , 
        { headers: { Authorization: `Bearer ${accessToken.value}` } }
    )

    emit('delete', id) 
       handleCloseDialog()

    // backend response'a göre ayarla
  } catch (err: any) {
    console.log(err.message);
  } 
}

 </script>
 
 <style lang="scss" scoped>
 
 </style>