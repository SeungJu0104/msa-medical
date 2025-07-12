const Search = () => import ('@/staff/views/Search.vue');
const RegPatientByStaff = () => import ("@/staff/views/RegPatientByStaff.vue");
const StaffMainView = () => import("@/staff/views/StaffMainView.vue");
const RegReservation = () => import('@/shared/views/RegReservation.vue')

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