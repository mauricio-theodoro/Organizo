import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Button } from '../components/Button';
import { createBooking, BookingRequest } from '../api';

export default function BookingReview() {
  const { salonId, serviceId, professionalId, date, clientId } = useLocation().state as
    salonId: number;
    serviceId: number;
    professionalId: number;
    date: string;
    clientId: number;
  };
  const navigate = useNavigate();

  const handleConfirm = async () => {
    const payload: BookingRequest = {
        clienteId: clientId,
        profissionalId: professionalId,
        servicoId: serviceId,
        dataHoraAgendada: date,
        };
    await createBooking(payload);
    navigate('/dashboard', { replace: true });
  };

  return (
    <main className="container">
      <h1>Confirmar Agendamento</h1>
      <p><strong>Salão:</strong> {salonId}</p>
      <p><strong>Serviço:</strong> {serviceId}</p>
      <p><strong>Profissional:</strong> {professionalId}</p>
      <p>Data: {new Date(state.date).toLocaleString()}</p>
      <p><strong>Data:</strong> {new Date(date).toLocaleString()}</p>
      <Button onClick={handleConfirm}>Confirmar</Button>
    </main>
  );
}
