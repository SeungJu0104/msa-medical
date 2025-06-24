import { createRouter, createWebHistory } from 'vue-router'
import { patientRoutes } from './patientRoutes';

const HomeView = () => import('@/views/HomeView.vue');
const MainView = () => import('@/views/home/MainView.vue');
const OtherView = () => import('@/views/other/OtherView.vue');
const acceptPatientByStaff = () => import('@/views/acceptPatientByStaff.vue')
const regReservationByPatient = () => import('@/views/regReservationByPatient.vue')

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: HomeView,
      children: [
        {
          path: '/',
          name: 'home',
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
    },
    {
      path: '/acceptPatientByStaff',
      name: 'acceptPatientByStaff',
      component: acceptPatientByStaff
    }
  ],
})

export default router
