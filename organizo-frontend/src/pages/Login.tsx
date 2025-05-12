import React, { useContext, useState } from 'react';
import { AuthContext } from '../contexts/AuthContext';

const Login: React.FC = () => {
  const { login } = useContext(AuthContext);
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  return (
    <div>
      <h1>Login</h1>
      <input
        type="email"
        placeholder="E-mail"
        value={email}
        onChange={e => setEmail(e.target.value)}
      />
      <input
        type="password"
        placeholder="Senha"
        value={senha}
        onChange={e => setSenha(e.target.value)}
      />
      <button onClick={() => login(email, senha)}>Entrar</button>
    </div>
  );
};

export default Login;
