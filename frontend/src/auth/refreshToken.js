export function getRefreshToken() {
  return localStorage.getItem("refreshToken");
}

export function setRefreshToken(refreshToken) {
  localStorage.setItem("refreshToken", refreshToken);
}

export function removeRefreshToken() {
  localStorage.removeItem("refreshToken");
}