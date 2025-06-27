import router from "@/router/index.js";
import dayjs from "dayjs";

export const common =  {

    goHome : () => {
        return router.push({name: 'home'});
    },
    goBack : () => {
        return router.back();
    },
    errMsg : (err) => {
        err.response?.data?.message ?
            alert(err.response?.data?.message) :
            alert("오류가 발생했습니다. 다시 실행해주세요.");
    },
    alertError : (err) => {
        alert(err);
    },
    dateFormatter : (date, format) => {
        return dayjs(date).format(format);
    }

}