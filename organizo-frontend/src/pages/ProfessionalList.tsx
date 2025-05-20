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
  const { salonId, serviceId } = useParams<{
    salonId: string;
    serviceId: string;
  }>();
  const navigate = useNavigate();
  const [data, setData] = useState<Paginated<Professional> | null>(null);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    axios
      .get<Paginated<Professional>>(
        `${import.meta.env.VITE_API_URL}/saloes/${salonId}/servicos/${serviceId}/profissionais`,
        { params: { page, size: 6 } }
      )
      .then((resp) => setData(resp.data))
      .catch(() => {
        /* TODO: mostrar toast de erro */
      })
      .finally(() => setLoading(false));
  }, [salonId, serviceId, page]);

  if (loading) return <Spinner />;

  return (
    <main className="container">
      <h1 className="dashboard__title">Profissionais</h1>

      {/* grade de cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {data?.content.map((p) => (
          <ProfessionalCard
            key={p.id}
            professional={p}
            onSelect={(id) =>
              navigate(`/book/${salonId}/${serviceId}/${id}/calendar`)
            }
          />
        ))}
      </div>

      {/* paginação */}
      <div className="mt-6 flex justify-center space-x-2">
        <button
          className="btn"
          disabled={page === 0}
          onClick={() => setPage((p) => p - 1)}
        >
          Anterior
        </button>

        {[...Array(data?.totalPages || 0)].map((_, i) => (
          <button
            key={i}
            className={`btn ${
              i === page ? 'btn--primary' : ''
            }`}
            onClick={() => setPage(i)}
          >
            {i + 1}
          </button>
        ))}

        <button
          className="btn"
          disabled={page + 1 >= (data?.totalPages || 0)}
          onClick={() => setPage((p) => p + 1)}
        >
          Próxima
        </button>
      </div>
    </main>
  );
}
