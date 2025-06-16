import axios from 'axios';

const instance = axios.create({
    baseURL: '/api'
});

export async function customFetch(endpoint, config) {
    const response = await instance({
        ...endpoint,
        ...config
    });

    return {
        data: response.data,
        headers: response.headers,
        status: response.status
    };
}
