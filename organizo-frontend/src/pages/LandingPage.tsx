// src/pages/LandingPage.tsx
import React from 'react';
import { Button } from '../components/Button';

/**
 * Página inicial de apresentação do Organizô.
 */
export default function LandingPage() {
  return (
    <main className="container landing">
      <h1>Bem‑vindo ao Organizô</h1>
      <p>Gerencie seus agendamentos de forma simples e eficiente.</p>
      <div className="landing__actions">
        <Button variant="primary" onClick={() => window.location.href = '/login'}>
          Área do Cliente
        </Button>
        <Button variant="secondary" onClick={() => window.location.href = '/owner'}>
          Área do Dono do Salão
        </Button>
      </div>
    </main>
  );
}
