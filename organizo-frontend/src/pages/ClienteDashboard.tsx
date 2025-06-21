import React, { useEffect, useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';
import { Card } from '../components/Card';
import { Button } from '../components/Button';
import { Spinner } from '../components/ui/Spinner';
import { toast } from 'react-hot-toast';
import { AuthContext } from '../contexts/AuthContext';

interface Salon {
  id: number;
  nome: string;
  cnpj: string;
  endereco: string | null;
  telefone: string | null;
  criadoEm: string;
  atualizadoEm: string;
  ownerId: number;
}

interface SalonResponse {
  content: Salon[];
  totalPages: number;
  totalElements: number;
  pageable: {
    pageNumber: number;
    pageSize: number;
    offset: number;
  };
  number: number;
  numberOfElements: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

export default function ClienteDashboard() {
  const { role } = useContext(AuthContext);
  const [saloes, setSaloes] = useState<Salon[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [page, setPage] = useState(0);

  useEffect(() => {
    setLoading(true);
    setError(null);
    api.get<SalonResponse>(`/api/saloes?page=${page}&size=10`)
      .then(response => {
        console.log(response.data);
        setSaloes(response.data.content);
      })
      .catch(err => {
        console.error("Erro ao buscar salões:", err);
        let errorMessage = 'Não foi possível carregar os salões. Tente novamente mais tarde.';
        if (err.response?.status === 500) {
          errorMessage = 'Erro interno no servidor. Por favor, contate o suporte.';
        }
        setError(errorMessage);
        toast.error(errorMessage);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [page]);

  if (loading) {
    return (
      <div className="flex justify-center items-center py-xl">
        <Spinner />
      </div>
    );
  }

  if (error) {
    return (
      <div className="container py-lg text-center text-red-600">
        <p>{error}</p>
      </div>
    );
  }

  return (
    <main className="container py-lg md:py-xl">
      <div className="dashboard__header mb-lg">
        <h1 className="text-2xl font-bold mb-xs">Bem-vindo(a)!</h1>
        <p className="text-muted">Escolha um salão abaixo para ver os serviços e agendar.</p>
      </div>
      {saloes.length > 0 ? (
        <>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-lg">
            {saloes.map(s => (
              <Card key={s.id} title={s.nome} className="flex flex-col">
                <div className="flex-grow mb-md">
                  <p className="text-sm text-muted mb-xs">{s.endereco || 'Endereço não informado'}</p>
                  <p className="text-sm text-muted">{s.telefone || 'Telefone não informado'}</p>
                </div>
                <Link to={`/salons/${s.id}/services`} className="mt-auto">
                  <Button variant="primary" className="w-full">
                    Ver Serviços
                  </Button>
                </Link>
              </Card>
            ))}
          </div>
          <div className="flex justify-between mt-lg">
            <Button
              variant="secondary"
              disabled={page === 0}
              onClick={() => setPage(page - 1)}
            >
              Página Anterior
            </Button>
            <Button
              variant="secondary"
              disabled={saloes.length < 10}
              onClick={() => setPage(page + 1)}
            >
              Próxima Página
            </Button>
          </div>
        </>
      ) : (
        <div className="text-center py-xl text-muted">
          <p>Nenhum salão encontrado no momento.</p>
        </div>
      )}
    </main>
  );
}