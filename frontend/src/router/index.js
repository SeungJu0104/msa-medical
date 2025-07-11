import { createRouter, createWebHistory } from 'vue-router'
import { patientRoutes } from './patientRoutes';
import { adminRoutes } from './adminRoutes';
import { staffRoutes } from './staffRoutes';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...patientRoutes,
    ...adminRoutes,
    {
      path: '/staff',
      children: [
        ...staffRoutes
      ]
    },
  ],
})

export default router
