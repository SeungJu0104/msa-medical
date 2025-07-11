import { createRouter, createWebHistory } from 'vue-router'
import ChatRooms from '@/components/chat/ChatRooms.vue'
import ChatRoom from '@/components/chat/ChatRoom.vue'
import { patientRoutes } from './patientRoutes';
import { adminRoutes } from './adminRoutes';
import MedicalTreatment from '@/components/diagnosis/MedicalTreatment.vue';
import {staffRoutes} from "@/router/staffRoutes.js";
import PaymentList from '@/components/payment/PaymentList.vue';

const OtherView = () => import('@/views/other/OtherView.vue');
const Reception = () => import('@/reception/views/WaitingList.vue')
const StaffLayOut = () => import('@/views/staff/StaffMainView.vue');

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...patientRoutes,
    ...adminRoutes,
    {
      path: '/other',
      name: 'other',
      component: OtherView
    },
    
    
    {
      path: '/medicalTreatment',
      name : 'medicalTreatment',
      component:MedicalTreatment
    },
    {
      path: '/staff',
      children: [
        ...staffRoutes
      ]
    },
    {
      path:`/paymentList`,
      component:PaymentList
    }
  ],
})

export default router
