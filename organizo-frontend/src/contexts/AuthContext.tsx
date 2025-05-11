import React, { createContext, useState, useEffect } from 'react';
import api from '../api';

interface AuthContextType {
  token: string | null;
  login(email: string, senha: string): Promise<void>;
  logout(): void;
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC = ({ children }) => {
  const [token, setToken] = useState<string | null>(null);

  useEffect(() => {
    const saved = localStorage.getItem('token');
    if (saved) setToken(saved);
  }, []);

  const login = async (email: string, senha: string) => {
    const resp = await api.post('/auth/login', { email, senha });
    const jwt = resp.data.token;
    localStorage.setItem('token', jwt);
    setToken(jwt);
  };

  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
  };

  return (
    <AuthContext.Provider value={{ token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
