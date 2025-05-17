// src/pages/SalonList.tsx
import React, { useEffect, useState } from 'react';
import { getSalons } from '../api';
import { Card } from '../components/Card';

/**
 * Lista os salões disponíveis para agendamento.
 */
export default function SalonList() {
  const [salons, setSalons] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getSalons()
      .then(setSalons)
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Carregando salões...</p>;

  return (
    <main className="container salons">
      <h1>Salões Próximos</h1>
      {salons.map(s => (
        <Card key={s.id} title={s.nome}>
          <p><strong>Endereço:</strong> {s.endereco}</p>
          <p><strong>Telefone:</strong> {s.telefone}</p>
          <Button variant="primary" onClick={() =>
            window.location.href = `/book/${s.id}/${s.servicos[0]?.id}`
          }>
            Ver Serviços
          </Button>
        </Card>
      ))}
    </main>
  );
}
