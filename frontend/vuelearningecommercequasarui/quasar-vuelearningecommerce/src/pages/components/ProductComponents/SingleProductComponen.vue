 <template>
 <q-card 
 flat
 bordered
     class="flex flex-col q-pa-lg bg-white rounded-borders shadow-2 cursor-pointer transition-all"
     style="max-width: 480px; width: 100%"
     >
     
     <q-card-section>
       <div class="text-h4 text-dark q-mb-md">Ürün Detayları</div>
     </q-card-section>
       <q-card-section>
      <div class="text-h6 text-dark q-mb-md">
        Ürün İsmi: {{ product.name }}
      </div>
    </q-card-section>
      <q-card-section>
      <slot name="details" :product="product">
        <!-- default içerik (slot verilmezse) -->
         <div class="row justify-between items-center q-pb-xs q-mb-sm" 
             style="border-bottom: 1px solid #e5e7eb">
             
          <span class="text-body2 text-grey-7">Marka</span>
          <span class="text-body2 text-dark">{{ product.brand }} </span>
        </div>
           <div class="row justify-between items-center q-pb-xs q-mb-sm" 
             style="border-bottom: 1px solid #e5e7eb">
             
          <span class="text-body2 text-grey-7">Tanım</span>
          <span class="text-body2 text-dark">{{ product.description }} </span>
        </div>
        <div class="row justify-between items-center q-pb-xs q-mb-sm" 
             style="border-bottom: 1px solid #e5e7eb">
             
          <span class="text-body2 text-grey-7">Fiyat</span>
          <span class="text-body2 text-dark">{{ product.price }} ₺</span>
        </div>
      </slot>
    </q-card-section>


 </q-card>

   <q-btn class="h-8 w-8" @click="handleOpenDialog(product.id.toString())">

Sil
   </q-btn>



   <q-dialog v-model="openDeleteDialog">
      <q-card style="width: 400; border-radius: 16px; box-shadow: 0 4px 20px  rgba(0,0,0,0.1);" class="bg-white">
        <q-card-section class="text-h6 q-pt-md q-pb-none">
          <div class="text-h6">      Ürünü silmeye emin misiniz?
</div>
        </q-card-section>

        <q-card-section class="q-pt-none q-pb-md text-subtitle2 text-grey-8">
      Bu işlem geri alınamaz. Ürünü silmek istediğinizden emin olun.
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
        @click="() => { deleteProduct(productId); handleCloseDialog(); }"
      />
   </q-card-actions>
      </q-card>
    </q-dialog>

 </template>
 
 <script setup lang="ts">
 const props = defineProps<{  
    product: Product
 }>()
import axios from 'axios';
import { accessToken } from 'src/boot/keycloak';
import { Product } from 'src/pages/ProductManagementPage.vue';
 import { ref } from 'vue'
 
 const openDeleteDialog = ref(false)
 const productId = ref<String>("")

const emit = defineEmits<{
  (e: 'delete', id: String): void
}>()
 async function handleOpenDialog(id:String) {
  try {
    productId.value = id
    console.log(productId);
    console.log(openDeleteDialog.value);
    openDeleteDialog.value = true
    // backend response'a göre ayarla
  } catch (err: any) {
    console.log(err.message);
  } 
}
function handleCloseDialog() {
  openDeleteDialog.value = false; // direkt kapat
}
async function deleteProduct(id:String) {
  try {
    const res = await axios.delete(`http://localhost:8082/api/v1/products/product/${id}/delete` , 
        { headers: { Authorization: `Bearer ${accessToken.value}` } }
    )

    
    emit('delete', id) 
       handleCloseDialog()
    console.log(res.data.data);
    // backend response'a göre ayarla
  } catch (err: any) {
    console.log(err.message);
  } 
}

 </script>
 
 <style lang="scss" scoped>
 
 </style>