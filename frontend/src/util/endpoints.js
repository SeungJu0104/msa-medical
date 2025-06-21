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
            method: 'get',
            url: `/patient/${memberId}`
        }
    },
    doctor: {
        list: {
            method: 'get',
            url: '/doctor/list'
        }
    }
}
