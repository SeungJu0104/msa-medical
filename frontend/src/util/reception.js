import {ENDPOINTS} from "@/util/endpoints.js";
import {customFetch} from "@/util/customFetch.js";
import {common} from "@/util/common.js";

export const reception = {
    getWaitingList: async () => {
        console.log("b");
        try {
            console.log("c");
            const response = await customFetch(ENDPOINTS.reception.getWaitingList);

            if(response?.status === 200) {
                return response.data?.waitingList;
            }

        } catch(err) {
            console.log("f");
            common.errMsg(err);
        }


    },
    getDoctorName : async () => {

        try {
            
            const response = await customFetch(ENDPOINTS.doctor.name('bbf7cf49-971c-47be-82cd-f613c633aa57')); // 테스트용 의사 번호 데이터
                // await customFetch(ENDPOINTS.doctor.name(localStorage.getItem('uuid')));

            if(response?.status === 200) {

                return response.data?.name;

            }

        } catch(err) {

            common.errMsg(err);

        }

    }

}