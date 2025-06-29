const AcceptPatientByStaff = () => import("@/views/reception/AcceptPatientByStaff.vue");

export const receptionRoutes = [
    {
        path: '/acceptPatientByStaff',
        name: 'acceptPatientByStaff',
        component: AcceptPatientByStaff
    },
]