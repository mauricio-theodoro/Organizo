import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/Dashboard.css';

/**
 * Dashboard do Profissional:
 * - resumo do salão vinculado
 * - link para gerenciar disponibilidade
 */
export default function ProfissionalDashboard() {
  return (
    <main className="container dashboard">
      <h1>Olá, Profissional!</h1>
      <p>Gerencie sua disponibilidade:</p>
      <div className="dashboard-actions">
        <Link to="/profissional/agenda" className="btn btn-primary">
          Minha Agenda
        </Link>
      </div>
    </main>
  );
}
