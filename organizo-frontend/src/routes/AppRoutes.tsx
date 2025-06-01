import React from 'react';

/**
 * Dashboard do Administrador:
 * - Visão geral do sistema e acesso a funcionalidades de gerenciamento.
 * - Utiliza classes globais de estilo e utilitários definidos em `index.css`.
 * - TODO: Implementar busca de dados reais (API calls) e lógica interativa (filtros, paginação, ações).
 */
export default function AdminDashboard() {
  // --- DADOS MOCKADOS (Substituir por chamadas de API) ---
  // TODO: Buscar dados resumidos (contagens, etc.) via API
  const stats = {
    totalUsuarios: 150, // Exemplo
    totalSaloes: 25,    // Exemplo
    totalProfissionais: 80, // Exemplo
    agendamentosHoje: 15,  // Exemplo
  };

  // TODO: Buscar listas paginadas/filtradas de usuários, salões, agendamentos via API
  const usuarios = [
    { id: 1, nome: 'Admin User', email: 'admin@example.com', role: 'ADMIN', status: 'Ativo' },
    { id: 2, nome: 'Dono Salão A', email: 'dono@salao.com', role: 'DONO_SALAO', status: 'Ativo' },
    { id: 3, nome: 'Cliente Teste', email: 'cliente@teste.com', role: 'CLIENTE', status: 'Inativo' },
  ]; // Exemplo
  const saloes = [
    { id: 1, nome: 'Salão Glamour', owner: 'Dono Salão A', cidade: 'São Paulo', status: 'Ativo' },
    { id: 2, nome: 'Beleza Pura', owner: 'Outro Dono', cidade: 'Rio de Janeiro', status: 'Ativo' },
  ]; // Exemplo
  const agendamentos = [
    { id: 1, cliente: 'Cliente A', profissional: 'Profissional B', servico: 'Corte', data: '02/06/2025 10:00', status: 'Confirmado', salao: 'Salão Glamour' },
    { id: 2, cliente: 'Cliente C', profissional: 'Profissional D', servico: 'Manicure', data: '02/06/2025 11:30', status: 'Pendente', salao: 'Beleza Pura' },
  ]; // Exemplo
  // --- FIM DADOS MOCKADOS ---

  return (
    // Container principal com padding padrão do dashboard
    <main className="container py-lg md:py-xl"> {/* Adicionado padding vertical responsivo */}

      {/* Cabeçalho do Dashboard */}
      <div className="dashboard__header mb-xl"> {/* Usando classe existente e margem utilitária */}
        <h1 className="text-2xl font-bold mb-xs">Dashboard do Administrador</h1> {/* Ajuste de tamanho/peso */}
        <p className="text-muted">Visão geral e gerenciamento do sistema Organizo.</p>
      </div>

      {/* Seção 1: Visão Geral (Cards) */}
      <section className="mb-xl">
        <h2 className="text-xl font-semibold mb-lg">Visão Geral</h2> {/* Ajuste de tamanho/margem */}
        {/* Grid responsivo para os cards de estatísticas */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-lg">
          {/* Card Total Usuários */}
          <div className="card">
            <div className="card__body flex flex-col items-start"> {/* Flex para alinhar */}
              <h3 className="text-muted text-sm font-medium mb-sm">Total de Usuários</h3>
              <p className="text-3xl font-bold text-text">{stats.totalUsuarios}</p> {/* Tamanho maior */}
            </div>
          </div>
          {/* Card Total Salões */}
          <div className="card">
            <div className="card__body flex flex-col items-start">
              <h3 className="text-muted text-sm font-medium mb-sm">Salões Ativos</h3>
              <p className="text-3xl font-bold text-text">{stats.totalSaloes}</p>
            </div>
          </div>
          {/* Card Total Profissionais */}
          <div className="card">
            <div className="card__body flex flex-col items-start">
              <h3 className="text-muted text-sm font-medium mb-sm">Profissionais</h3>
              <p className="text-3xl font-bold text-text">{stats.totalProfissionais}</p>
            </div>
          </div>
          {/* Card Agendamentos Hoje */}
          <div className="card">
            <div className="card__body flex flex-col items-start">
              <h3 className="text-muted text-sm font-medium mb-sm">Agendamentos Hoje</h3>
              <p className="text-3xl font-bold text-accent">{stats.agendamentosHoje}</p> {/* Cor de destaque */}
            </div>
          </div>
        </div>
      </section>

      {/* --- Seções de Gerenciamento (Estrutura de Tabela Simples) --- */}
      {/* TODO: Substituir estas seções por componentes de Tabela reutilizáveis com paginação, filtros e ações */}

      {/* Seção 2: Gerenciamento de Usuários */}
      <section className="mb-xl">
        <div className="flex justify-between items-center mb-lg"> {/* Título e botão lado a lado */}
          <h2 className="text-xl font-semibold">Gerenciamento de Usuários</h2>
          <button className="btn btn--primary btn--sm">Novo Usuário</button> {/* TODO: Adicionar funcionalidade */}
        </div>
        <div className="card overflow-x-auto"> {/* Card para conter a tabela, overflow para mobile */}
          {/* Tabela Simples - Idealmente usar um componente <Table> */}
          <table className="w-full text-left">
            <thead>
              <tr className="border-b border-border">
                <th className="p-md font-semibold text-muted text-sm">Nome</th>
                <th className="p-md font-semibold text-muted text-sm">Email</th>
                <th className="p-md font-semibold text-muted text-sm">Perfil</th>
                <th className="p-md font-semibold text-muted text-sm">Status</th>
                <th className="p-md font-semibold text-muted text-sm">Ações</th>
              </tr>
            </thead>
            <tbody>
              {usuarios.map((u) => (
                <tr key={u.id} className="border-b border-border hover:bg-gray-50"> {/* Efeito hover sutil */}
                  <td className="p-md">{u.nome}</td>
                  <td className="p-md">{u.email}</td>
                  <td className="p-md"><span className={`px-2 py-1 text-xs rounded font-medium ${u.role === 'ADMIN' ? 'bg-red-100 text-red-700' : 'bg-blue-100 text-blue-700'}`}>{u.role}</span></td> {/* Badge simples */} 
                  <td className="p-md"><span className={`px-2 py-1 text-xs rounded font-medium ${u.status === 'Ativo' ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-700'}`}>{u.status}</span></td>
                  <td className="p-md">
                    <button className="text-accent hover:text-accent-hover text-sm">Editar</button> {/* TODO: Adicionar funcionalidade */}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {/* TODO: Adicionar Paginação */}
        </div>
      </section>

      {/* Seção 3: Gerenciamento de Salões */}
      <section className="mb-xl">
         <div className="flex justify-between items-center mb-lg">
          <h2 className="text-xl font-semibold">Gerenciamento de Salões</h2>
           {/* <button className="btn btn--primary btn--sm">Novo Salão</button> */}{/* Admin não cria salão diretamente? */} 
        </div>
        <div className="card overflow-x-auto">
          <table className="w-full text-left">
             <thead>
              <tr className="border-b border-border">
                <th className="p-md font-semibold text-muted text-sm">Nome</th>
                <th className="p-md font-semibold text-muted text-sm">Dono</th>
                <th className="p-md font-semibold text-muted text-sm">Cidade</th>
                <th className="p-md font-semibold text-muted text-sm">Status</th>
                <th className="p-md font-semibold text-muted text-sm">Ações</th>
              </tr>
            </thead>
             <tbody>
              {saloes.map((s) => (
                <tr key={s.id} className="border-b border-border hover:bg-gray-50">
                  <td className="p-md">{s.nome}</td>
                  <td className="p-md">{s.owner}</td>
                  <td className="p-md">{s.cidade}</td>
                   <td className="p-md"><span className={`px-2 py-1 text-xs rounded font-medium ${s.status === 'Ativo' ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-700'}`}>{s.status}</span></td>
                  <td className="p-md">
                    <button className="text-accent hover:text-accent-hover text-sm">Ver Detalhes</button> {/* TODO: Adicionar funcionalidade */}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
           {/* TODO: Adicionar Paginação */}
        </div>
      </section>

      {/* Seção 4: Gerenciamento de Agendamentos */}
      <section>
         <div className="flex justify-between items-center mb-lg">
          <h2 className="text-xl font-semibold">Gerenciamento de Agendamentos</h2>
           {/* Filtros podem vir aqui */} 
        </div>
        <div className="card overflow-x-auto">
          <table className="w-full text-left">
             <thead>
              <tr className="border-b border-border">
                <th className="p-md font-semibold text-muted text-sm">Data/Hora</th>
                <th className="p-md font-semibold text-muted text-sm">Cliente</th>
                <th className="p-md font-semibold text-muted text-sm">Profissional</th>
                <th className="p-md font-semibold text-muted text-sm">Serviço</th>
                <th className="p-md font-semibold text-muted text-sm">Salão</th>
                <th className="p-md font-semibold text-muted text-sm">Status</th>
                <th className="p-md font-semibold text-muted text-sm">Ações</th>
              </tr>
            </thead>
             <tbody>
              {agendamentos.map((a) => (
                <tr key={a.id} className="border-b border-border hover:bg-gray-50">
                  <td className="p-md">{a.data}</td>
                  <td className="p-md">{a.cliente}</td>
                  <td className="p-md">{a.profissional}</td>
                  <td className="p-md">{a.servico}</td>
                  <td className="p-md">{a.salao}</td>
                   <td className="p-md"><span className={`px-2 py-1 text-xs rounded font-medium 
                    ${a.status === 'Confirmado' ? 'bg-green-100 text-green-700' : 
                      a.status === 'Pendente' ? 'bg-yellow-100 text-yellow-700' : 
                      'bg-gray-100 text-gray-700'}`}>{a.status}</span></td>
                  <td className="p-md">
                    <button className="text-accent hover:text-accent-hover text-sm">Ver Detalhes</button> {/* TODO: Adicionar funcionalidade */}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
           {/* TODO: Adicionar Paginação e Filtros */}
        </div>
      </section>

    </main>
  );
}

