// src/api/index.ts
import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
});

// ✅ Esta função precisa existir!
export const getSalons = async () => {
  const { data } = await api.get('/saloes');
  return data.content; // ou simplesmente: return data;
};
