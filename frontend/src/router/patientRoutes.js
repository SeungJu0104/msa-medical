const SignupView = () => import("@/views/patient/SignupView.vue");

export const patientRoutes = [
  {
    path: '/patient/singup',
    name: 'patientSignup',
    component: SignupView
  }
];
