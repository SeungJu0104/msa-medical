import {ENDPOINTS} from "@/util/endpoints.js";
import {customFetch} from "@/util/customFetch.js";
import {common} from "@/util/common.js";

export const reception = {
    getWaitingList: async (doctorUuid) => {
        console.log("b");
        try {
            console.log("c");
            const response = await customFetch(ENDPOINTS.reception.getWaitingList(doctorUuid));

            if(response?.status === 200) {
                console.log("d");
                alert("a");
                const res = response.data?.waitingList;
                console.log(res);
                return res;
                // return response.data?.waitingList;
            }

        } catch(err) {
            console.log("f");
            common.errMsg(err);
        }


    },
    
    // 여기서 처리? 팀장님과 상의하기
    getDoctorName : async () => {

        try {

            const response = await customFetch(ENDPOINTS.reception.getDoctorName);

            if(response?.status === 200) {

                return response.data?.name;

            }

        } catch(err) {

            common.errMsg(err);

        }

    }

}