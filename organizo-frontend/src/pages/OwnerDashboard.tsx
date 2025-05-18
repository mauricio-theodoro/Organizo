import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/Dashboard.css';

/**
 * Dashboard do Dono do Salão:
 * - overview de agendamentos
 * - links para CRUD de serviços e equipe
 */
export default function OwnerDashboard() {
  return (
    <main className="container dashboard">
      <h1>Bem‑vindo, Dono do Salão!</h1>
      <p>O que deseja fazer hoje?</p>
      <div className="dashboard-actions">
        <Link to="/owner/servicos" className="btn btn-primary">
          Meus Serviços
        </Link>
        <Link to="/owner/profissionais" className="btn btn-secondary">
          Equipe
        </Link>
      </div>
    </main>
  );
}
