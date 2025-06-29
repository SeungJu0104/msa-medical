import { getAccessToken, setAccessToken } from '@/auth/accessToken';
import { getRefreshToken } from '@/auth/refreshToken';
import axios from 'axios';
import { ENDPOINTS } from './endpoints';

const instance = axios.create({
    baseURL: '/api'
});

instance.interceptors.request.use(
  config => {
    const token = getAccessToken();
    if (token) {
      config.headers = config.headers || {};
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => Promise.reject(error)
);

instance.interceptors.response.use(
  response => response,
  async error => {
    const originalRequest = error.config;

    if (error.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshToken = getRefreshToken();
        if (!refreshToken) {
          return Promise.reject(error);
        }
        const res = await customFetch(ENDPOINTS.auth.refreshToken, {
          data: { refreshToken }
        });
        setAccessToken(res.data.accessToken);
        return instance(originalRequest);
      } catch (err) {
        return Promise.reject(err);
      } 
    }

    return Promise.reject(error);
  }
);

/**
 * Axios 인스턴스를 이용한 커스텀 Fetch 함수
 *
 * @template T
 * @param {Object} endpoint - 요청할 엔드포인트 정보
 * @param {import('axios').Method} endpoint.method - HTTP 메서드 (e.g. 'get', 'post')
 * @param {string} endpoint.url - 요청할 URL
 * @param {import('axios').AxiosRequestConfig} [config] - 추가 Axios 요청 설정
 * @returns {Promise<{
 *   data: T;
 *   headers: import('axios').AxiosResponseHeaders;
 *   status: number;
 * }>} 응답 데이터, 헤더, 상태코드를 포함한 객체
 */
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
