const SignupView = () => import("@/views/patient/SignupView.vue");

export const patientRoutes = [
  {
    path: '/patient/signup',
    name: 'patientSignup',
    component: SignupView
  }
];
