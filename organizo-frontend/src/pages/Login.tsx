import React, { useState, FormEvent, useContext } from 'react';
import { AuthContext } from '../contexts/AuthContext';
import { Button } from '../components/Button';

/**
 * Página de login: solicita e-mail e senha,
 * chama AuthContext.login() e trata erros.
 */
export default function Login() {
  const { login } = useContext(AuthContext);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string| null>(null);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError(null);
    try {
      await login(email, password);
      // redirecionamento automático pelo contexto/roteador
    } catch (err: any) {
      setError(err.message || 'Erro ao autenticar');
    }
  };

  return (
    <main className="container auth-container">
      <h1>Login</h1>
      <form onSubmit={handleSubmit} className="auth-form">
        {error && <div className="auth-form__error">{error}</div>}
        <label>
          E‑mail
          <input
            type="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            required
            placeholder="seu@exemplo.com"
          />
        </label>
        <label>
          Senha
          <input
            type="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            required
            minLength={6}
            placeholder="••••••"
          />
        </label>
        <Button type="submit" variant="primary">
          Entrar
        </Button>
      </form>
    </main>
  );
}
