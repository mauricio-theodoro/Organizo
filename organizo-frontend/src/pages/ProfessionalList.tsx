import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getProfessionalsByService } from '../api';
import { Card } from '../components/Card';
import { Button } from '../components/Button';

export default function ProfessionalList() {
  const { salonId, serviceId } = useParams<{ salonId: string; serviceId: string }>();
  const [pros, setPros] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    getProfessionalsByService(Number(salonId), Number(serviceId))
      .then(setPros)
      .finally(() => setLoading(false));
  }, [salonId, serviceId]);

  if (loading) return <p>Carregando profissionais…</p>;

  return (
    <main className="container">
      <h1>Profissionais</h1>
      {pros.map(p => (
        <Card key={p.id} title={`${p.nome} ${p.sobrenome}`}>
          <Button onClick={() =>
            navigate(`/book/${salonId}/${serviceId}/${p.id}/calendar`)
          }>
            Agendar
          </Button>
        </Card>
      ))}
    </main>
  );
}
