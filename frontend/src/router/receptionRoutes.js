const AcceptPatientByStaff = () => import("@/views/reception/AcceptPatientByStaff.vue");
const WaitingListPatientList = () => import('@/views/reception/WaitingListPatientList.vue');
const WaitingListDoctorName = () => import("@/views/reception/WaitingListDoctorName.vue");
const WaitingListPerDoctor = () => import('@/views/reception/WaitingListPerDoctor.vue');

export const receptionRoutes = [
    {
        path: '/acceptPatientByStaff',
        name: 'acceptPatientByStaff',
        component: AcceptPatientByStaff
    },
]