
const Search = () => import ('@/views/staff/Search.vue');
const RegPatientByStaff = () => import ("@/views/staff/RegPatientByStaff.vue");

export const staffRoutes = [

    {
        path: 'search',
        name: 'search',
        component: Search
    },
    {
        path: 'regPatientByStaff',
        name: 'regPatientByStaff',
        component: RegPatientByStaff
    }

]