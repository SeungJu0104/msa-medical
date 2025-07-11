const Search = () => import ('@/views/staff/Search.vue');
const RegPatientByStaff = () => import ("@/views/staff/RegPatientByStaff.vue");
const StaffMainView = () => import("@/views/staff/StaffMainView.vue");
const RegReservation = () => import('@/reservation/views/RegReservationByStaff.vue')

export const staffRoutes = [

    {
        path: '',
        name: 'staffMain',
        component: StaffMainView,
    },
    {
        path: 'search',
        name: 'search',
        component: Search
    },
    {
        path: 'regReservation',
        name: 'regReservation',
        component: RegReservation,
    },
    {
        path: 'regPatientByStaff',
        name: 'regPatientByStaff',
        components : {
            default: StaffMainView,
            modal: RegPatientByStaff
        }
    },
]