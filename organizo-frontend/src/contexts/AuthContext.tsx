import React, { createContext, useState, useEffect } from 'react'

export interface AuthContextType {
  token: string | null
  login: (token: string) => void
  logout: () => void
}

export const AuthContext = createContext<AuthContextType>({
  token: null,
  login: () => {},
  logout: () => {},
})

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [token, setToken] = useState<string | null>(null)

  // Ao montar, busca token no localStorage (se existir)
  useEffect(() => {
    const saved = localStorage.getItem('organizo_token')
    if (saved) setToken(saved)
  }, [])

  const login = (newToken: string) => {
    localStorage.setItem('organizo_token', newToken)
    setToken(newToken)
  }

  const logout = () => {
    localStorage.removeItem('organizo_token')
    setToken(null)
  }

  return (
    <AuthContext.Provider value={{ token, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}
