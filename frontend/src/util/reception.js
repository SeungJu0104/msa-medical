import {ENDPOINTS} from "@/util/endpoints.js";
import {customFetch} from "@/util/customFetch.js";

export const reception = {

    getDoctorName : () => {
        customFetch(ENDPOINTS.reception.getDoctorName);
    }

}