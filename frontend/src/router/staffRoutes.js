import { useUserStore } from "@/stores/userStore";
import { roles } from "@/util/roles"; 

const RegPatientByStaff = () => import ("@/staff/views/RegPatientByStaff.vue");
const StaffMainView = () => import("@/staff/views/StaffMainView.vue");
const RegReservationByStaff = () => import('@/reservation/views/RegReservationByStaff.vue')
const AcceptPatientByStaff = () => import('@/staff/views/AcceptPatientByStaff.vue')
const StaffLayOut = () => import('@/staff/layouts/StaffLayOut.vue')

export const staffRoutes = [
    {
        path: '/staff',
        component: StaffLayOut,
        beforeEnter: (to, from, next) => {
          const role = useUserStore().user?.role;
          if ([roles.PATIENT].includes(role) || role === null || role === undefined) {
            return next({ name: 'home' });
          }
        //   if(roles.ADMIN === role) {
        //     return next({ name: 'adminMain' });
        //   }
          next();
        },
        children: [
            {
                path: '',
                name: 'staffMain',
                component: StaffMainView,
            },
            {
                path: 'regReservationByStaff',
                name: 'regReservationByStaff',
                component: RegReservationByStaff,
            },
            {
                path: 'regPatientByStaff',
                name: 'regPatientByStaff',
                component: RegPatientByStaff
            },
            {
                path: 'acceptPatientByStaff',
                name: 'acceptPatientByStaff',
                component: AcceptPatientByStaff
            }
        ]
    },
]