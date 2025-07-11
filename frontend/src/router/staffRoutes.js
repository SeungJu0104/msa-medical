const Search = () => import ('@/views/staff/Search.vue');
const RegPatientByStaff = () => import ("@/views/staff/RegPatientByStaff.vue");
const StaffMainView = () => import("@/views/staff/StaffMainView.vue");

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
        path: 'regPatientByStaff',
        name: 'regPatientByStaff',
        component: RegPatientByStaff
    },
]