import React, {
  createContext,
  useState,
  useEffect,
  ReactNode
} from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'

interface AuthContextData {
  token: string | null
  loading: boolean
  login: (email: string, senha: string) => Promise<void>
  logout: () => void
}

export const AuthContext = createContext<AuthContextData>({
  token: null,
  loading: false,
  login: async () => {},
  logout: () => {}
})

interface Props { children: ReactNode }

export const AuthProvider: React.FC<Props> = ({ children }) => {
  const navigate = useNavigate()
  const [token, setToken] = useState<string | null>(() => localStorage.getItem('token'))
  const [loading, setLoading] = useState(true)

  // Quando o token mudar, configuramos axios e persistimos
  useEffect(() => {
    if (token) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
      localStorage.setItem('token', token)
    } else {
      delete axios.defaults.headers.common['Authorization']
      localStorage.removeItem('token')
    }
    setLoading(false)
  }, [token])

  const login = async (email: string, senha: string) => {
    setLoading(true);
    try {
      const resp = await axios.post<{ token: string; role: string }>(
        `${import.meta.env.VITE_API_URL}/auth/login`,
        { email, senha }
      );
      const { token, role } = resp.data;
      setToken(token);

      //  ↙︎ INSIRA AQUI A LÓGICA DE REDIRECIONAMENTO POR ROLE:
      if (role === 'CLIENTE') {
        navigate('/cliente/dashboard', { replace: true });
      } else if (role === 'PROFISSIONAL') {
        navigate('/profissional/dashboard', { replace: true });
      } else if (role === 'DONO_SALAO') {
        navigate('/owner/dashboard', { replace: true });
      } else {
        // fallback genérico
        navigate('/', { replace: true });
      }

    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    setToken(null)
    navigate('/login', { replace: true })
  }

  return (
    <AuthContext.Provider value={{ token, loading, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}
