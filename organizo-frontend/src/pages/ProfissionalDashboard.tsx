import React from 'react';
/**
 * Dashboard do Profissional:
 * - exibe dados do salão vinculado
 * - link para gerenciar agenda
 */
export default function ProfissionalDashboard() {
  return (
    <main className="container dashboard">
      <h1>Olá, Profissional!</h1>
      <p>Gerencie sua agenda:</p>
      <Link to="/profissional/agenda" className="btn btn-primary">
        Minha Agenda
      </Link>
    </main>
  );
}
