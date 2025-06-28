import { useUserStore } from "@/stores/userStore";
import { customFetch } from "./customFetch";
import { ENDPOINTS } from "./endpoints";

export async function getMe() {
  const userStore = useUserStore();
  const response = await customFetch(ENDPOINTS.member.me);
  userStore.setUser(response.data);
}
