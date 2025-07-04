const MyPageView = () => import("@/patient/views/MyPageView.vue");
const RegisterView = () => import("@/patient/views/RegisterView.vue");
const UpdateProfile = () => import("@/patient/views/UpdateProfile.vue");

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
  },
  {
    path: '/patient/update',
    name: 'updateProfile',
    component: UpdateProfile
  },
];
