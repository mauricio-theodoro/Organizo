import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getSalons } from '../api';           // chamada à API já criada
import { Card } from '../components/Card';    // nosso card genérico
import '../styles/Dashboard.css';             // estilos compartilhados

interface Salon {
  id: number;
  nome: string;
  endereco: string;
  telefone: string;
}

/**
 * Dashboard do Cliente:
 * - mostra lista de salões (com loading)
 * - cada card leva ao fluxo de escolha de serviço
 */
export default function ClienteDashboard() {
  const [salons, setSalons] = useState<Salon[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getSalons()
      .then(setSalons)
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Carregando salões...</p>;

  return (
    <main className="container dashboard">
      <h1>Olá, Cliente!</h1>
      <p>Escolha um salão para agendar:</p>
      <div className="dashboard-grid">
        {salons.map(s => (
          <Card key={s.id} title={s.nome}>
            <p><strong>Endereço:</strong> {s.endereco}</p>
            <p><strong>Telefone:</strong> {s.telefone}</p>
            <Link to={`/book/${s.id}/${s.servicos?.[0]?.id}`} className="btn btn-primary">
              Ver Serviços
            </Link>
          </Card>
        ))}
      </div>
    </main>
  );
}
