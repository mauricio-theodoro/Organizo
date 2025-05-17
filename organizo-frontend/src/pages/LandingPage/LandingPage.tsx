import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './LandingPage.module.css';

const LandingPage: React.FC = () => {
  const nav = useNavigate();

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Bem-vindo ao Organizô</h1>
      <p className={styles.subtitle}>
        O sistema de agendamento online que conecta clientes e salões de beleza de forma rápida e segura.
      </p>
      <div className={styles.buttons}>
        <button
          className={styles.button}
          onClick={() => nav('/login')}
        >
          Área do Cliente
        </button>
        <button
          className={styles.button}
          onClick={() => nav('/login')}
        >
          Área do Dono do Salão
        </button>
      </div>
    </div>
  );
};

export default LandingPage;
