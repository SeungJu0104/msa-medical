import {computed, reactive, ref} from 'vue'
import {customFetch} from "@/util/customFetch.js";
import {ENDPOINTS} from "@/util/endpoints.js";
import { useRouter } from "vue-router";
import {omit} from "lodash";
import {common} from "@/util/common.js";
import dayjs from "dayjs";

export const patientMethods = {
    generateTimeSlots : (reservationList, {reservationDate, isToday}) => {

        console.log("예약 데이터 처리 부분");
        console.log(reservationList);

        const alreadyReservatedSlots = new Set(
            reservationList.value.map(item => {
                const date = new Date(item.reservationDate);
                const hours = date.getHours().toString().padStart(2, '0');
                const minutes = date.getMinutes().toString().padStart(2, '0');
                return `${hours}:${minutes}`;
            })
        );

        let start = reservationDate;
        let end = reservationDate;
        const availableSlots = new Set();

        console.log("당일 여부 : ", isToday);
        console.log("start : ", start);

        if(isToday) {
            start = start.minute(Math.floor(start.minute() / 10) * 10).second(0); // 초와 밀리초도 0으로 초기화
        } else {
            start = start.hour(9).minute(0).second(0); // 9:00 AM
        }

        console.log("시작 시각 : ", start);

        end = end.hour(18).minute(0).second(0); // 6:00 PM

        while (start <= end) {

            const hours = start.hour().toString().padStart(2, '0');
            const minutes = start.minute().toString().padStart(2, '0');

            availableSlots.add(`${hours}:${minutes}`);
            start = start.minute(start.minute() + 10);

        }

        console.log("예약된 시간 목록", [...alreadyReservatedSlots]);
        console.log("전체 가능한 시간 목록", [...availableSlots]);


        const diff = new Set(
            [...availableSlots].filter(slot => {
                const [hh] = slot.split(':');
                return !alreadyReservatedSlots.has(slot) || hh !== '12';
            })
        );

        console.log("최종 목록 ", diff);

        return diff;

    },
    reservationTime : async (selectedVal) => {

        try{
            console.log("fetch 전송");
            const response = await customFetch(
                ENDPOINTS.reservation.reservationList(selectedVal),
            );

            if(response.status === 200) {

                console.log(response.data?.reservationList);
                return response.data?.reservationList;

            }

        } catch(err) {
            common.errMsg(err);
        }

    },
    reservation : (selectedVal) => {

        console.log(selectedVal);

        customFetch(
            ENDPOINTS.reservation.reservation,
            {
                data: selectedVal
            }
        )
        .then(
            response => {
                if(response.status === 200) {
                    alert(response.data?.message);
                }
            }
        )
        .catch(
            err => {
                common.errMsg(err);
            }
        )
    },
    getDoctorList : async () => {

        try {

            const response = await customFetch(ENDPOINTS.doctor.list); // 일반함수와 비동기 함수 완료 타이밍이 다르기 때문에 async와 await 추가

            if (response.status === 200) {
                return response.data?.list;
            }

        } catch (err) {
            common.errMsg(err);
            await useRouter().push({name: 'home'});
        }
    },

    getReservationTime: async(selectedVal) => {

        const today = dayjs(new Date());
        const reservationList = ref([]);
        let reservationDate = dayjs(selectedVal.reservationDate);

        // selectedVal.reservationDate = dayjs(selectedVal.reservationDate);

        console.log("fetch에 전송하는 일시 : " + reservationDate);
        // 오늘 날짜인지 검증해
        // 오늘 날짜가 아니면 그냥 그대로 보내기
        if(reservationDate.isSame(today, 'day')) {

            selectedVal.isToday = true;

            // 오늘 날짜면 현재 시간 기준 1시간 뒤로 시간 데이터 수정해서
            // reservationTime 함수 실행.
            if(reservationDate.hour() > 8  && reservationDate.hour() < 17) {

                reservationDate = reservationDate.add(1, 'hour');
                // selectedValO.reservationDate.setHours(selectedValO.reservationDate.getHours() + 1);
                // selectedValO.reservationDate = dayjs(selectedValO.reservationDate).format('YYYY-MM-DDTHH:mm:00');
                console.log("당일 1시간 뒤 시간 : ", reservationDate);

            }
            
        }else {

            selectedVal.isToday = false; // 다른 날짜 선택하면 false로 변경

        }

        console.log("시간 : ", reservationDate);
        console.log("인코딩 후 시간 : ", reservationDate);

        const response = await patientMethods.reservationTime({
            patientUuid : '550e8400-e29b-41d4-a716-446655440020', // 테스트용 환자 아이디
            ...omit(selectedVal, ['reservationDate', 'time', 'name']), // date와 time, name 속성을 제외한 나머지 속성들을 복사
            dateTime:
                `${dayjs(reservationDate).format('YYYY-MM-DDTHH:mm:00')}`
            // ISO8601 형식으로 안정적인 전송
        });

        reservationList.value = Array.isArray(response) ? response : [];
        
        return patientMethods.generateTimeSlots(
            reservationList, {reservationDate : reservationDate, isToday: selectedVal.isToday}
        );

    }
}