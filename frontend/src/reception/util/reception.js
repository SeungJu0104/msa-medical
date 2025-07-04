import {ENDPOINTS} from "@/util/endpoints.js";
import {customFetch} from "@/util/customFetch.js";
import {common} from "@/util/common.js";
import {successMessage} from "@/util/successMessage.js";

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
            console.log("f");
            common.errMsg(err);
        }


    },
    getReceptionStatusList : async () =>  {

        try {

            const response = await customFetch(ENDPOINTS.reception.getReceptionStatusList());

            if(response?.status === 200) {
                return response.data?.statusList;
            }

        } catch(err) {

            common.errMsg(err);

        }

    },
    updateReceptionStatus : async ({uuid, updateStatus}) => {

        try {

            const response = await customFetch(ENDPOINTS.reception.updateReceptionStatus({patient, updateStatus}));

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