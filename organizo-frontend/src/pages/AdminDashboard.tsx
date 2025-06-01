import React from 'react';

/**
 * Dashboard do Administrador:
 * - Visão geral do sistema e acesso a funcionalidades de gerenciamento.
 * - Utiliza classes globais de estilo definidas em `index.css`.
 * - TODO: Implementar busca de dados e lógica para cada seção.
 */
export default function AdminDashboard() {
  // TODO: Buscar dados resumidos (contagens, etc.)
  const stats = {
    totalUsuarios: 150, // Exemplo
    totalSaloes: 25,    // Exemplo
    totalProfissionais: 80, // Exemplo
    agendamentosHoje: 15,  // Exemplo
  };

  // TODO: Buscar listas de usuários, salões, agendamentos
  const usuarios = [ { id: 1, nome: 'Admin User', email: 'admin@example.com', role: 'ADMIN' } ]; // Exemplo
  const saloes = [ { id: 1, nome: 'Salão Exemplo', owner: 'Dono Exemplo', cidade: 'Exemplo City' } ]; // Exemplo
  const agendamentos = [ { id: 1, cliente: 'Cliente A', profissional: 'Profissional B', servico: 'Corte', data: '02/06/2025 10:00', status: 'Confirmado' } ]; // Exemplo

  return (
    <main className="container dashboard">
      {/* Cabeçalho do Dashboard */}
      <div className="dashboard__header">
        <h1 className="dashboard__title">Dashboard do Administrador</h1>
        <p className="dashboard__subtitle">Visão geral e gerenciamento do sistema Organizo.</p>
      </div>

      {/* Seção 1: Visão Geral (Cards) */}
      <section className="mb-xl">
        <h2 className="text-lg font-semibold mb-md">Visão Geral</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-lg">
          {/* Card Total Usuários */}
          <div className="card">
            <div className="card__body">
              <h3 className="text-muted text-sm font-medium mb-xs">Total de Usuários</h3>
              <p className="text-2xl font-bold">{stats.totalUsuarios}</p>
            </div>
          </div>
          {/* Card Total Salões */}
          <div className="card">
            <div className="card__body">
              <h3 className="text-muted text-sm font-medium mb-xs">Salões Ativos</h3>
              <p className="text-2xl font-bold">{stats.totalSaloes}</p>
            </div>
          </div>
          {/* Card Total Profissionais */}
          <div className="card">
            <div className="card__body">
              <h3 className="text-muted text-sm font-medium mb-xs">Profissionais</h3>
              <p className="text-2xl font-bold">{stats.totalProfissionais}</p>
            </div>
          </div>
          {/* Card Agendamentos Hoje */}
          <div className="card">
            <div className="card__body">
              <h3 className="text-muted text-sm font-medium mb-xs">Agendamentos Hoje</h3>
              <p className="text-2xl font-bold">{stats.agendamentosHoje}</p>
            </div>
          </div>
        </div>
      </section>

      {/* Seção 2: Gerenciamento de Usuários */}
      <section className="mb-xl">
        <h2 className="text-lg font-semibold mb-md">Gerenciamento de Usuários</h2>
        {/* TODO: Implementar tabela/lista de usuários com busca/filtros e ações */}
        <div className="card">
          <div className="card__body">
            <p className="text-muted">Tabela de usuários aqui...</p>
            {/* Exemplo de como poderia ser a tabela (usar biblioteca ou componente) */}
            <ul>
              {usuarios.map(u => <li key={u.id}>{u.nome} ({u.email}) - {u.role}</li>)}
            </ul>
          </div>
        </div>
      </section>

      {/* Seção 3: Gerenciamento de Salões */}
      <section className="mb-xl">
        <h2 className="text-lg font-semibold mb-md">Gerenciamento de Salões</h2>
        {/* TODO: Implementar tabela/lista de salões com busca/filtros e ações */}
        <div className="card">
          <div className="card__body">
            <p className="text-muted">Tabela de salões aqui...</p>
             <ul>
              {saloes.map(s => <li key={s.id}>{s.nome} ({s.cidade}) - Dono: {s.owner}</li>)}
            </ul>
          </div>
        </div>
      </section>

      {/* Seção 4: Gerenciamento de Agendamentos */}
      <section>
        <h2 className="text-lg font-semibold mb-md">Gerenciamento de Agendamentos</h2>
        {/* TODO: Implementar tabela/lista de TODOS agendamentos com busca/filtros */}
        <div className="card">
          <div className="card__body">
            <p className="text-muted">Tabela de agendamentos aqui...</p>
             <ul>
              {agendamentos.map(a => <li key={a.id}>{a.data} - {a.servico} com {a.profissional} para {a.cliente} ({a.status})</li>)}
            </ul>
          </div>
        </div>
      </section>

    </main>
  );
}