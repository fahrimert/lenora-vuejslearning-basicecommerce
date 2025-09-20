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
        {{ product.name }}
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
 
 </template>
 
 <script setup lang="ts">
 defineProps<{
    product: Product
 }>()
 import { ref, computed } from 'vue'
import { Product } from '../ProductManagementPage.vue'
 
 const openDeleteDialog = ref(false)
 
 </script>
 
 <style lang="scss" scoped>
 
 </style>