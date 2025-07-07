import { getAccessToken, setAccessToken } from '@/auth/accessToken'
import { getRefreshToken } from '@/auth/refreshToken'
import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import { customFetch } from './customFetch'
import { ENDPOINTS } from './endpoints'
import { renewAccessToken } from '@/auth/renewAccessToken'

let stompClient = null
let reconnectTimer = null
let prepareToken = null;
export function getStompClient(uuid,onConnectedCallback) {
  const token = getAccessToken();
  if (stompClient && stompClient.connected){
    if (onConnectedCallback) onConnectedCallback(stompClient)
      return stompClient
    }

  stompClient = new Client({
    webSocketFactory: () => new SockJS('/ws'),
    connectHeaders: {
      sender: uuid,
      Authorization: `Bearer ${token}`,
    },
    onConnect: () => {
      if (onConnectedCallback) onConnectedCallback(stompClient)
      prepareAccessToken(token);
    },
    onStompError: async (error) => {
      if (error.headers.message.includes("만료된 토큰")) {
        await stompClient.deactivate();
        stompClient = null;
        try {
          const token = prepareToken;
          prepareToken = null
          getStompClient(uuid, onConnectedCallback)
          console.log("연결 성공")
        } catch (e) {
          console.error("재접속  실패", e)
        }
      }
    },
  })

  stompClient.activate()
  return stompClient
}

function getTokenExpirationTime(token) {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]))
    return payload.exp * 1000
  } catch {
    return 0
  }
}

async function prepareAccessToken(token) {
  if (reconnectTimer) {
    clearTimeout(reconnectTimer);
    reconnectTimer = null;
  }

  const expTime = getTokenExpirationTime(token);
  const now = Date.now();
  const delay = expTime - now - 60 * 1000  // 1분 전

  reconnectTimer = setTimeout(async () => {
    try {
      prepareToken = await getNewAccessToken();
    } catch (e) {
      console.error("미리 갱신 실패 ",e)
    }
  }, delay);
}

async function getNewAccessToken() {
  await renewAccessToken();
  const newAccessToken = getAccessToken();
  return newAccessToken;
}

export function subscribeChannel(client, destination, callback) {
    try {
      return client.subscribe(destination, (message) => {
        try {
          const payload = JSON.parse(message.body);
          callback(payload);
        } catch (e) {
          console.error("메시지 파싱 오류:", e);
        }
      });
    } catch (err) {
      console.error(`구독 실패 (${destination}):`, err);
      return null;
    }
  }
  

  export function sendMsg(client, destination, Object) {
    try {
      client.publish({
        destination,
        body: JSON.stringify(Object),
      });
    } catch (err) {
      console.error(`메시지 전송 실패 (${destination}):`, err);
    }
  }
  