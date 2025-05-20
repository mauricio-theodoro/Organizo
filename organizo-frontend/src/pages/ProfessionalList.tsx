import React, { useState, useEffect } from 'react';
import { ProfessionalCard, Professional } from '../components/ProfessionalCard';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import Spinner from '../components/ui/Spinner';

interface Paginated<T> {
  content: T[];
  number: number;
  totalPages: number;
}

export default function ProfessionalList() {
  const { salonId, serviceId } = useParams<{ salonId: string; serviceId: string }>();
  const navigate = useNavigate();
  const [data, setData] = useState<Paginated<Professional> | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get<Paginated<Professional>>(
      `${import.meta.env.VITE_API_URL}/saloes/${salonId}/servicos/${serviceId}/profissionais`,
      { params: { page: 0, size: 6 } }
    )
    .then(resp => setData(resp.data))
    .catch(() => {/* TODO: Toast de erro */})
    .finally(() => setLoading(false));
  }, [salonId, serviceId]);

  if (loading) return <Spinner />;

  return (
    <main className="container py-6">
      <h1 className="text-2xl font-bold mb-4">Profissionais</h1>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {data?.content.map(p => (
          <ProfessionalCard
            key={p.id}
            professional={p}
            onSelect={id => navigate(`/book/${salonId}/${serviceId}/${id}/calendar`)}
          />
        ))}
      </div>
    <div className="mt-6 flex justify-center space-x-2">
        <button
          disabled={page === 0}
          onClick={() => setPage(p => p - 1)}
          className="px-3 py-1 bg-gray-200 rounded disabled:opacity-50"
        >Anterior</button>
        {[...Array(data?.totalPages || 0)].map((_, i) => (
          <button
            key={i}
            onClick={() => setPage(i)}
            className={`px-3 py-1 rounded ${
              i === page ? 'bg-primary text-white' : 'bg-gray-200'
            }`}
          >{i + 1}</button>
        ))}
        <button
          disabled={page + 1 >= (data?.totalPages || 0)}
          onClick={() => setPage(p => p + 1)}
          className="px-3 py-1 bg-gray-200 rounded disabled:opacity-50"
        >Próxima</button>
      </div>
    </main>
  );
}
