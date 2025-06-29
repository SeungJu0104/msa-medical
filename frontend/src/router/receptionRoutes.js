const WaitingListPatientList = () => import("@/views/reception/WaitingListPatientList.vue")
const AcceptPatientByStaff = () => import("@/views/reception/AcceptPatientByStaff.vue");

export const receptionRoutes = [
    {
        path: '/acceptPatientByStaff',
        name: 'acceptPatientByStaff',
        component: AcceptPatientByStaff
    },
    {
        path: 'waitingPatientList',
        name: 'waitingPatientList',
        component: WaitingListPatientList
    }
]