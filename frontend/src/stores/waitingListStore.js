import {defineStore} from "pinia";
import {ref} from "vue";
import {reception} from "@/util/reception.js";
import {errorMessage} from "@/util/errorMessage.js";
import {common} from "@/util/common.js";

export const useWaitingListStore = defineStore('waitingList', () => {

    const doctorList = ref();
    // const waitingList = ref();
    // const doctorName = reactive({
    //     name: null
    // });

    const waitingListByNurse = async () => {

        doctorList.value = await reception.getDoctorList();

        console.log(doctorList.value);

        if(doctorList.value == null) {
            common.alertError(errorMessage.common.retry);
        }

        return await Promise.all(
            doctorList.value.map(async (doctor) => {

                const list = await reception.getWaitingList(doctor.uuid);

                return {
                    doctor,
                    patientList: list
                }

            })
        );

    }

    const waitingListByDoctor = async () => {

        doctorList.value = [{
            name: await reception.getDoctorName()
        }]

        return await Promise.all(
            doctorList.value.map(async (doctor) => {

                const list = await reception.getWaitingList('550e8400-e29b-41d4-a716-446655440000'); // 더미 데이터
                // await reception.getWaitingList(localStorage.getItem('uuid'));

                return {
                    doctor,
                    patientList: list
                }

            })
        );

    }

    return {
        waitingListByNurse,
        waitingListByDoctor
    };

})