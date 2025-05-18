
import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getServicesBySalon } from '../api';
import { Card } from '../components/Card';
import { Button } from '../components/Button';

export default function ServiceList() {
  const { salonId } = useParams<{ salonId: string }>();
  const [services, setServices] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    getServicesBySalon(Number(salonId))
      .then(setServices)
      .finally(() => setLoading(false));
  }, [salonId]);

  if (loading) return <p>Carregando serviços…</p>;

  return (
    <main className="container">
      <h1>Serviços</h1>
      {services.map(s => (
        <Card key={s.id} title={s.nome}>
          <p>Preço: R$ {s.preco.toFixed(2)}</p>
          <Button onClick={() =>
            navigate(`/salons/${salonId}/services/${s.id}/professionals`)
          }>
            Escolher
          </Button>
        </Card>
      ))}
    </main>
  );
}
