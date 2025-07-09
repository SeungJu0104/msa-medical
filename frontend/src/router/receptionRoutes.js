const AcceptPatientByStaff = () => import("@/reception/views/AcceptPatientByStaff.vue");
const Reception = () => import('@/reception/views/WaitingList.vue');

export const receptionRoutes = [
    {
        path: '',
        name: 'reception',
        component: Reception,
    },
    {
        path: 'acceptPatientByStaff',
        name: 'acceptPatientByStaff',
        component: AcceptPatientByStaff
    },
]