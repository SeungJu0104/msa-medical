const AdminView = () => import('@/views/admin/AdminView.vue');

export const adminRoutes = [
    {
      path: '/admin',
      name: 'adminView',
      component: AdminView
    },
]
