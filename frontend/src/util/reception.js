import {ENDPOINTS} from "@/util/endpoints.js";
import {customFetch} from "@/util/customFetch.js";
import {common} from "@/util/common.js";

export const reception = {

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