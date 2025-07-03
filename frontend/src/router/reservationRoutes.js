const ReservationList = () => import('@/reservation/views/ReservationList.vue')
const RegReservationByPatient = () => import('@/reservation/views/RegReservationByPatient.vue')

export const reservationRoutes = [
    {
        path: 'list',
        name: 'reservationList',
        component: ReservationList
    },
    {
        path: 'regReservationByPatient',
        name: 'regReservationByPatient',
        component: RegReservationByPatient,
    },
]