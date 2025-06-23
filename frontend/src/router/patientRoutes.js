const RegisterView = () => import("@/views/patient/RegisterView.vue");

export const patientRoutes = [
  {
    path: '/patient/register',
    name: 'patientRegister',
    component: RegisterView
  }
];
