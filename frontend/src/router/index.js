import { createRouter, createWebHistory } from 'vue-router'
import regReservationByPatient from '@/views/regReservationByPatient.vue'
import { patientRoutes } from './patientRoutes';

const HomeView = () => import('@/views/HomeView.vue');
const MainView = () => import('@/views/home/MainView.vue');
const OtherView = () => import('@/views/other/OtherView.vue');

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      children: [
        {
          path: '/',
          name: 'mainView',
          component: MainView
        },
        ...patientRoutes
      ]
    },
    {
      path: '/other',
      name: 'other',
      component: OtherView
    },
    {
      path: '/regReservationByPatient',
      name: 'regReservationByPatient',
      component: regReservationByPatient,
    }
  ],
})

export default router
