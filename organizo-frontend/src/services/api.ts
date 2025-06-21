import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // ajuste se necessário
});

// Interceptor para adicionar o token JWT automaticamente nas requisições
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Interceptor para tratar respostas e erros de autenticação
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    // Se receber 401 (Unauthorized), limpar o token e redirecionar para login
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('role');
      // Recarregar a página para atualizar o estado de autenticação
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;
