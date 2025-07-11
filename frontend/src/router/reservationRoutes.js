const ReservationList = () => import('@/reservation/views/ReservationList.vue')
const RegReservationByPatient = () => import('@/reservation/views/RegReservationByPatient.vue')
const ReservationListByPatient = () => import('@/reservation/views/ReservationListByPatient.vue')

export const reservationRoutes = [
    {
        path: 'reservationList',
        name: 'reservationList',
        component: ReservationList
    },
    {
        path: 'regReservationByPatient',
        name: 'regReservationByPatient',
        component: RegReservationByPatient,
    },
    {
        path: 'reservationListByPatient',
        name: 'reservationListByPatient',
        component: ReservationListByPatient
    },
]