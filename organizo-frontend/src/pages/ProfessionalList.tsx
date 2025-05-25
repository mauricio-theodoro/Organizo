import React, { useState, useEffect } from 'react';
import { ProfessionalCard, Professional } from '../components/ProfessionalCard';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../api/axiosConfig';            // nosso axios configurado
import { toast } from 'react-hot-toast';
import { Spinner } from '../components/ui/Spinner';

interface Paginated<T> {
  content: T[];
  number: number;
  totalPages: number;
}

export default function ProfessionalList() {
  const { salonId, serviceId } = useParams<{
    salonId: string;
    serviceId: string;
  }>();
  const navigate = useNavigate();

  const [data, setData]       = useState<Paginated<Professional> | null>(null);
  const [page, setPage]       = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    api.get<Paginated<Professional>>(
      `/saloes/${salonId}/servicos/${serviceId}/profissionais`,
      { params: { page, size: 6 } }
    )
    .then(resp => setData(resp.data))
    .catch(err => {
      toast.error('Erro ao carregar profissionais');
      console.error(err);
    })
    .finally(() => setLoading(false));
  }, [salonId, serviceId, page]);

  if (loading) {
    return (
      <div className="flex justify-center py-12">
        <Spinner />
      </div>
    );
  }

  return (
    <main className="container py-6">
      <h1 className="text-2xl font-bold mb-4">Profissionais</h1>

      {/* Grid responsivo de cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {data?.content.map(p => (
          <ProfessionalCard
            key={p.id}
            professional={p}
            onSelect={id =>
              navigate(`/book/${salonId}/${serviceId}/${id}/calendar`)
            }
          />
        ))}
      </div>

      {/* Paginação */}
      <div className="mt-6 flex justify-center space-x-2">
        <button
          className="btn btn--secondary"
          disabled={page === 0}
          onClick={() => setPage(p => p - 1)}
        >
          Anterior
        </button>
        {[...Array(data?.totalPages || 0)].map((_, i) => (
          <button
            key={i}
            className={`btn ${
              i === page ? 'btn--primary' : 'btn--secondary'
            }`}
            onClick={() => setPage(i)}
          >
            {i + 1}
          </button>
        ))}
        <button
          className="btn btn--secondary"
          disabled={page + 1 >= (data?.totalPages || 0)}
          onClick={() => setPage(p => p + 1)}
        >
          Próxima
        </button>
      </div>
    </main>
  );
}
