const MainView = () => import('@/patient/views/MainView.vue');
const MyPageView = () => import("@/patient/views/MyPageView.vue");
const PatientLayout = () => import('@/patient/layouts/PatientLayout.vue');
const RegisterView = () => import("@/patient/views/RegisterView.vue");
const UpdateProfile = () => import("@/patient/views/UpdateProfile.vue");
const LoginView = () => import('@/patient/views/LoginView.vue');
const AcceptPatientByStaff = () => import("@/reception/views/AcceptPatientByStaff.vue");
const RegReservation = () => import('@/reservation/views/RegReservationByPatient.vue')
const ReservationListByPatient = () => import('@/reservation/views/ReservationListByPatient.vue')

export const patientRoutes = [
  {
    path: '/',
    component: PatientLayout,
    children: [
      {
        path: '/',
        name: 'home',
        component: MainView
      },
      {
        path: '/login',
        name: 'loginView',
        component: LoginView
      },
      {
        path: '/register',
        name: 'patientRegister',
        component: RegisterView
      },
      {
        path: '/mypage',
        name: 'mypage',
        component: MyPageView
      },
      {
        path: '/profile/update',
        name: 'updateProfile',
        component: UpdateProfile
      },
      {
        path: 'acceptPatientByStaff',
        name: 'acceptPatientByStaff',
        component: AcceptPatientByStaff
      },
      {
        path: 'regReservation',
        name: 'regReservation',
        component: RegReservation,
      },
      {
        path: 'reservationListByPatient',
        name: 'reservationListByPatient',
        component: ReservationListByPatient
      },
    ]
  },
];
