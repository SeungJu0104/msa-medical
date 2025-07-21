import { common } from "@/util/common.js";
import { customFetch } from "@/util/customFetch.js";
import { ENDPOINTS } from "@/util/endpoints.js";

export const reception = {
    getDoctorList: async () => {

        try {

            const response = await customFetch(ENDPOINTS.doctor.list);

            if(response?.status === 200) {
                return response.data?.list;
            }

        } catch(err) {

            common.errMsg(err);

        }

    },
    getWaitingList: async (uuid) => {

        try {

            const response = await customFetch(ENDPOINTS.reception.getWaitingList(uuid));

            if(response?.status === 200) {
                return response.data?.waitingList;
            }

        } catch(err) {

            common.errMsg(err);
        }

    },
    updateReceptionStatus : async (patient) => {

        try {

            const response = await customFetch(
                ENDPOINTS.reception.updateReceptionStatus,
                {
                    data: patient
                }

            );

            if(response?.status === 200) {

                if(response.data?.message !== undefined) {

                    common.alertError(response.data?.message);

                }

            }

        } catch(err) {

            common.errMsg(err);

        }

    }
}