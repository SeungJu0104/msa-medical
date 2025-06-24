import { method } from "lodash";

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
    paitent: {
        register: {
            method: 'post',
            url: '/patient'
        },
        search: (searchValue) => ({
            method: 'get',
            url: `/patient/search?searchValue=${searchValue}`
        })
    },
    paitent: {
        register: {
            method: 'post',
            url: '/patient'
        },
        search: (searchValue) => ({
            method: 'get',
            url: `/patient/search?searchValue=${searchValue}`
        }),
        reservationList: (selectedVal) => ({
                method: 'get',
                url: `/reservation/getReservationList/${selectedVal.doctorUuid}/${selectedVal.dateTime}`
        })
    },
    doctor: {
        list: {
            method: 'get',
            url: '/doctor/list'
        }
    },
    staff: {
        list: {
            method: 'get',
            url: '/staff/list'
        }
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
    }

    
}
