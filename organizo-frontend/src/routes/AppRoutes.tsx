// src/routes/AppRoutes.tsx
import React, { useContext } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';

// dashboards
import ClienteDashboard from '../pages/ClienteDashboard';
import ProfissionalDashboard from '../pages/ProfissionalDashboard';
import OwnerDashboard from '../pages/OwnerDashboard';

// outras páginas
import LandingPage from '../pages/LandingPage';
import Login from '../pages/Login';
import SalonList from '../pages/SalonList';
import Booking from '../pages/Booking';
import Layout from '../components/Layout';

export default function AppRoutes() {
  const { token, role, loading } = useContext(AuthContext);

  if (loading) return <p>Carregando...</p>;

  return (
    <Routes>
      {/* pública */}
      <Route path="/" element={<LandingPage />} />
      <Route path="/login" element={<Login />} />

      {/* privadas */}
      {token ? (
        <>
          {/* redireciona para o dashboard correspondente */}
          <Route path="/dashboard" element={
            role === 'CLIENTE' ? <ClienteDashboard />
            : role === 'PROFISSIONAL' ? <ProfissionalDashboard />
            : <OwnerDashboard />
          } />

          {/* rotas filhas dentro de <Layout> */}
          <Route element={<Layout />}>
            {/* cliente */}
            {role === 'CLIENTE' && (
              <>
                <Route path="/salons" element={<SalonList />} />
                <Route path="/book/:salonId/:serviceId" element={<Booking />} />
              </>
            )}
            {/* profissional */}
            {role === 'PROFISSIONAL' && (
              <Route path="/profissional/agenda" element={<ProfissionalAgenda />} />
            )}
            {/* dono */}
            {role === 'DONO_SALAO' && (
              <>
                <Route path="/owner/servicos" element={<ServicosManager />} />
                <Route path="/owner/profissionais" element={<ProfissionaisManager />} />
              </>
            )}
          </Route>

          {/* fallback: se acessar algo inválido, manda ao seu dashboard */}
          <Route path="*" element={<Navigate to="/dashboard" replace />} />
        </>
      ) : (
        <Route path="*" element={<Navigate to="/" replace />} />
      )}
    </Routes>
  );
}


  /**
   * Opcional: função para decidir o base path de cada role
   */
  function determineBasePath(): string {
    const role = localStorage.getItem('role');
    if (role === 'CLIENTE')   return 'cliente/dashboard';
    if (role === 'PROFISSIONAL') return 'profissional/dashboard';
    if (role === 'DONO_SALAO') return 'owner/dashboard';
    return '';
  }
