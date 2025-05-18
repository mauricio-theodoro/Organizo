import React, {
  createContext,
  useState,
  useEffect,
  ReactNode
} from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

interface AuthContextData {
  token: string | null;
  role: 'CLIENTE' | 'PROFISSIONAL' | 'DONO_SALAO' | null;
  loading: boolean;
  login: (email: string, senha: string) => Promise<void>;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextData>({
  token: null,
  role: null,
  loading: false,
  login: async () => {},
  logout: () => {}
});

interface Props { children: ReactNode; }

export const AuthProvider: React.FC<Props> = ({ children }) => {
  const navigate = useNavigate();
  const [token, setToken] = useState<string | null>(
    () => localStorage.getItem('token')
  );
  const [role, setRole] = useState<AuthContextData['role']>(
    () => (localStorage.getItem('role') as AuthContextData['role']) || null
  );
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (token && role) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      localStorage.setItem('token', token);
      localStorage.setItem('role', role);
    } else {
      delete axios.defaults.headers.common['Authorization'];
      localStorage.removeItem('token');
      localStorage.removeItem('role');
    }
    setLoading(false);
  }, [token, role]);

  const login = async (email: string, senha: string) => {
    setLoading(true);
    try {
      const resp = await axios.post<{
        token: string;
        role: AuthContextData['role'];
      }>(
        `${import.meta.env.VITE_API_URL}/auth/login`,
        { email, senha },
        { headers: { 'Content-Type': 'application/json' } }
      );
      setToken(resp.data.token);
      setRole(resp.data.role);

      // redireciona de acordo com a role
      if (resp.data.role === 'CLIENTE') {
        navigate('/dashboard/cliente', { replace: true });
      } else if (resp.data.role === 'PROFISSIONAL') {
        navigate('/dashboard/profissional', { replace: true });
      } else {
        navigate('/dashboard/owner', { replace: true });
      }
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    setToken(null);
    setRole(null);
    navigate('/login', { replace: true });
  };

  return (
    <AuthContext.Provider value={{ token, role, loading, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
