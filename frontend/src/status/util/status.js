import {customFetch} from "@/util/customFetch.js";
import {ENDPOINTS} from "@/util/endpoints.js";
import {common} from "@/util/common.js";

export const status = {

    getReceptionStatusList : async () =>  {

        try {

            const response = await customFetch(ENDPOINTS.status.getReceptionStatusList);

            if(response?.status === 200) {

                return response.data?.statusList;

            }

        } catch(err) {

            common.errMsg(err);

        }

    },
    getReservationStatusList: async() => {

        try {

            const response = await customFetch(ENDPOINTS.status.getReservationStatusList);

            if(response?.status === 200) {

                return response.data?.statusList;

            }

        } catch(err) {

            return common.errMsg(err);

        }

    },

}