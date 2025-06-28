import AcceptPatientByStaff from "@/views/reception/AcceptPatientByStaff.vue";
import WaitingListPatientList from '@/views/reception/WaitingListPatientList.vue'
import WaitingListDoctorName from "@/views/reception/WaitingListDoctorName.vue";
import WaitingList from "@/views/reception/WaitingList.vue";

export const receptionRoutes = [
    {
        path: '/',
        name: 'reception',
        component: AcceptPatientByStaff
    },
    {
        path: '/waitingListPatientList',
        name: 'waitingListPatientList',
        component: WaitingListPatientList
    },
    {
        path: '/waitingListDoctorName',
        name: 'waitingListDoctorName',
        component: WaitingListDoctorName
    },
    {
        path: '/waitingList',
        name: 'waitingList',
        component: WaitingList
    }
]