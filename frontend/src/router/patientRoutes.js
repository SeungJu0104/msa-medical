const RegisterView = () => import("@/patient/views/RegisterView.vue");

export const patientRoutes = [
  {
    path: '/patient/register',
    name: 'patientRegister',
    component: RegisterView
  }
];
