const MyPageView = () => import("@/patient/views/MyPageView.vue");
const RegisterView = () => import("@/patient/views/RegisterView.vue");

export const patientRoutes = [
  {
    path: '/patient/register',
    name: 'patientRegister',
    component: RegisterView
  },
  {
    path: '/mypage',
    name: 'mypage',
    component: MyPageView
  }
];
