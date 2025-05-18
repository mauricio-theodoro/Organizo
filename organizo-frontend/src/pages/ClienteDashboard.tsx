import React from 'react';
import { Link } from 'react-router-dom';

/**
 * Dashboard do Cliente:
 * - lista salões paginados
 * - link para seleção de serviços
 */
export default function ClienteDashboard() {
  return (
    <main className="container dashboard">
      <h1>Bem‑vindo, Cliente!</h1>
      <p>Selecione abaixo o salão para agendar:</p>
      <Link to="/salons" className="btn btn-primary">
        Ver Salões Disponíveis
      </Link>
    </main>
  );
}
