export const ENDPOINTS = {
    auth: {
        register: {
            patient: {
                method: 'post',
                url: '/auth/register/patient'
            }
        }
    },
    member: {
        detail: (memberId) => ({
            method: 'get',
            url: `/member/${memberId}`
        }),
        register: {
            method: 'post',
            url: '/member'
        },
        reservation: {
            method: 'post',
            url: '/reservation',
            // 추후 JWT에서 환자 UUID 포함해 전송.
            // 추후 팀장님과 얘기해서 patient 속성쪽으로 이동할지 결정.
        }
    },
    doctor: {
        list: {
            method: 'get',
            url: '/doctor/list'
        }
    },
    patient: {
        reservationList: (selectedVal) => ({
                method: 'get',
                url: `/reservation/getReservationList/${selectedVal.doctorUuid}/${selectedVal.dateTime}`
        })
    }
}
