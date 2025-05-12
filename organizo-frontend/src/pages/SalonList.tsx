import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

interface Salon { id: number; nome: string; endereco: string; }

const SalonList: React.FC = () => {
  const [lista, setLista] = useState<Salon[]>([]);
  const nav = useNavigate();

  useEffect(() => {
    axios.get(`${import.meta.env.VITE_API_URL}/saloes`)
      .then(r => setLista(r.data.content))
      .catch(console.error);
  }, []);

  return (
    <div>
      <h1>Salões</h1>
      <ul>
        {lista.map(s =>
          <li key={s.id}>
            <strong>{s.nome}</strong><br/>
            {s.endereco}<br/>
            <button onClick={() => nav(`/book/${s.id}/1`)}>Agendar serviço 1</button>
          </li>
        )}
      </ul>
    </div>
  );
};

export default SalonList;
