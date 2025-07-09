
const Search = () => import ('@/views/staff/Search.vue');
const RegPatientByStaff = () => import ("@/views/staff/RegPatientByStaff.vue");
const Staff = () => import('@/views/staff/StaffMainView.vue');

export const staffRoutes = [

    {
        path: '',
        name: 'staff',
        component: Staff,
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
    }

]