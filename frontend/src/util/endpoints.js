export const ENDPOINTS = {
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
    patient: {
        signup: {
            method: 'post',
            url: '/patient/signup'
        }
    },
    doctor: {
        list: {
            method: 'get',
            url: '/doctor/list'
        }
    }
}
