import {reservationRoutes} from "@/router/reservationRoutes.js";
import {receptionRoutes} from "@/router/receptionRoutes.js";

const Search = () => import ('@/views/staff/Search.vue');
const RegPatientByStaff = () => import ("@/views/staff/RegPatientByStaff.vue");
const WaitingReservationParent = () => import('@/views/staff/components/WaitingReservationParent.vue')

export const staffRoutes = [

    {
        path: 'main',
        name: 'staff',
        components: {
            waitingReservationParent: WaitingReservationParent,
        },
        children: [
            ...reservationRoutes,
            ...receptionRoutes,
        ]
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