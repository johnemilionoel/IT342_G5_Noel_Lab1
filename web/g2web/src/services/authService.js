import axios from "axios";

const API_BASE = "http://localhost:8081/api";

export const registerUser = (data) => {
  return axios.post(`${API_BASE}/auth/register`, data);
};

export const loginUser = (data) => {
  return axios.post(`${API_BASE}/auth/login`, data);
};

export const getProfile = (userId) => {
  return axios.get(`${API_BASE}/user/me?userId=${userId}`);
};
