export const ENDPOINTS = {
    auth: {
        register: {
            patient: {
                method: 'post',
                url: '/auth/register/patient'
            }
        },
        checkId: {
            method: 'get',
            url: '/auth/check-id'
        },
        login: {
            method: 'post',
            url: '/auth/login'
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
    },
    patient: {
        register: {
            method: 'post',
            url: '/patient'
        },
        search: (searchValue) => ({
            method: 'get',
            url: `/patient/search?searchValue=${searchValue}`
        }),
    },
    doctor: {
        list: {
            method: 'get',
            url: '/doctor/list'
        }
    },
    staff: {
        list: (uuid) => ({
            method: 'get',
            url: `/staff/list/${uuid}`
        }),
    },
    reception: {
        acceptPatientByStaff: {
            method: 'post',
            url: '/reception'
        },
        getDoctorName : {
            method: 'get',
            url: `/reception/getDoctorName/${localStorage.getItem('uuid')}/${localStorage.getItem('role')}`
        },
        getWaitingList: (doctorUuid) => ({
            method: 'get',
            url: `/reception/getWaitingList/${doctorUuid}`
        }),
    },
    chat: {
        loadMemberList: (uuid) => ({
            method: 'get',
            url: `/member/memberList/${uuid}`
        })
        ,
        createChatRoom: {
            method: 'post',
            url: `/chatroom/createChatRoom`
        },
        readtime : {
            method:'post',
            url:`/chatread/readtime`
        },
        messageList: (roomId) => ({
            method:'get',
            url:`/chatmessage/messageList/${roomId}`,
        }),
        loadChatName:(roomId) =>({
            method:'get',
            url:`/chatroom/loadChatName/${roomId}`,
        }),
        updateJoinTime:{
            method:'put',
            url:`/chatjoin/updateJoinTime`,
        },
        joinreadtime:{
            method:'post',
            url:`/chatread/joinreadtime`,
        },
        updateOutTime:{
            method:'put',
            url:`/chatjoin/updateOutTime`,
        },

        chatReadList:(uuid) => ({
            method:'get',
            url:`/chatread/chatReadList/${uuid}`
        }),
        chatRoomList:(uuid) => ({
            method:'get',
            url: `/chatroom/chatRoomList/${uuid}`,
        })
    },
    reservation: {
        reservation: {
            method: 'post',
            url: '/reservation',
            // 추후 JWT에서 환자 UUID 포함해 전송.
            // 추후 팀장님과 얘기해서 patient 속성쪽으로 이동할지 결정.
        },
        reservationList: (selectedVal) => ({
            method: 'get',
            url: `/reservation/getReservationList/${selectedVal.doctorUuid}/${selectedVal.dateTime}`
        }),
        reservationHold: {
            method: 'post',
            url: '/reservation/hold'
        },
        cancelHoldingReservation: {
            method: 'put',
            url: '/reservation/cancelHoldingReservation'
        }
    }
}
