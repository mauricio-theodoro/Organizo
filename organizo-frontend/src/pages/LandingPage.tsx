import React from 'react'
import { Button } from '../components/Button'
import { useNavigate } from 'react-router-dom'

/**
 * Página inicial de apresentação do Organizô.
 */
export default function LandingPage() {
  const navigate = useNavigate()

  return (
    <main className="container landing">
      <h1>Bem‑vindo ao Organizô</h1>
      <p>Gerencie seus agendamentos de forma simples e eficiente.</p>
      <div className="landing__actions">
        <Button variant="primary" onClick={() => navigate('/login')}>
          Área do Cliente
        </Button>
        <Button variant="secondary" onClick={() => navigate('/home')}>
          Área do Dono do Salão
        </Button>
      </div>
    </main>
  )
}
