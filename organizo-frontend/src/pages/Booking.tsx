import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const Booking: React.FC = () => {
  const { salonId, serviceId } = useParams();
  const [data, setData] = useState('');
  const agendar = () => {
    axios.post(`${import.meta.env.VITE_API_URL}/agendamentos`, {
      clienteId: 1, // teste
      profissionalId: 1,
      servicoId: Number(serviceId),
      dataHoraAgendada: data
    }).then(() => alert('Agendado!'));
  };
  return (
    <div>
      <h1>Agendar Serviço</h1>
      <p>Salão {salonId} — Serviço {serviceId}</p>
      <input
        type="datetime-local"
        value={data}
        onChange={e => setData(e.target.value)}
      />
      <button onClick={agendar}>Agendar</button>
    </div>
  );
};

export default Booking;
