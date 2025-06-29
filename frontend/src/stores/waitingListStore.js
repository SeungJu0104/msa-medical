import {defineStore} from "pinia";
import {ref} from "vue";
import {reception} from "@/util/reception.js";
import {errorMessage} from "@/util/errorMessage.js";
import {common} from "@/util/common.js";
import {useUserStore} from "@/stores/userStore.js";

export const useWaitingListStore = defineStore('waitingList', () => {

    const user = useUserStore();
    const userInfo = user.user;
    const doctorList = ref();
    const waitingList = ref();

    const roleChk = async () => {

        console.log(userInfo.role);

        if(userInfo.role === 'DOCTOR') {

            doctorList.value = [{
                name: userInfo.name
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

    const promiseAll = async () => {

        await roleChk();

        await nullChk();

        waitingList.value = await Promise.all(

            doctorList.value.map(async (doctor) => {

                const list = await reception.getWaitingList(doctor.uuid);

                return {
                    doctor,
                    patientList: list
                }

            })
        );
    }

    return {
        promiseAll,
        waitingList
    };

})