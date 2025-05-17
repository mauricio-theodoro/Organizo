import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
});

// buscar salões
export const getSalons = async () => {
  const { data } = await api.get('/saloes');
  return data.content; // ajusta conforme seu DTO de paginação
};

// buscar um salão específico
export const getSalonById = async (id: number) => {
  const { data } = await api.get(`/saloes/${id}`);
  return data; // SalaoDTO
};

// buscar serviço específico
export const getServiceById = async (id: number) => {
  const { data } = await api.get(`/servicos/${id}`);
  return data; // ServicoDTO
};

// criar agendamento
export interface BookingRequest {
  clienteId: number;
  profissionalId: number;
  servicoId: number;
  dataHoraAgendada: string; // ISO
}
export const createBooking = async (payload: BookingRequest) => {
  const { data } = await api.post('/agendamentos', payload);
  return data; // AgendamentoDTO
};
