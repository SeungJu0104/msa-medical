import {defineStore} from "pinia";
import {useUserStore} from "@/stores/userStore.js";
import {patientMethods} from "@/reservation/util/reservation.js";
import {errorMessage} from "@/util/errorMessage.js";
import {common} from "@/util/common.js";
import {computed, ref} from "vue";
import {reception} from "@/reception/util/reception.js";
import {status} from "@/status/util/status.js";

export const useReservationListStore = defineStore('reservation', () =>  {

    const userInfo = computed(() => useUserStore().user);
    const doctorList = ref();
    const reservationStatusList = ref();
    const reservationList = ref();

    const getReservationStatusList = async () => {

        reservationStatusList.value = await status.getReservationStatusList();

    }

    const roleChk = async () => {

        console.log(userInfo.value.role);

        if(userInfo.value.role === 'DOCTOR') {

            doctorList.value = [{
                name: userInfo.value.name
            }]

        } else {

            doctorList.value = await reception.getDoctorList();

        }

    }

    const nullChk = async () => {

        if(doctorList.value == null) {

            common.alertError(errorMessage.common.retry);

        }

    }

    const promiseAll = async (date) => {

        await roleChk();

        await nullChk();

        reservationList.value = await Promise.all(

            doctorList.value.map(async (doctor) => {

                const list = await patientMethods.getFullReservationList(doctor.uuid, date);

                return {
                    doctor,
                    patientList: list
                }

            })
        );
    }

    return {
        promiseAll,
        getReservationStatusList,
        reservationList,
        reservationStatusList
    };

});