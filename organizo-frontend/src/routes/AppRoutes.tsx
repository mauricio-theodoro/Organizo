import React, { useContext } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';

// Dashboards por perfil
import ClienteDashboard from '../pages/ClienteDashboard';
import ProfissionalDashboard from '../pages/ProfissionalDashboard';
import OwnerDashboard from '../pages/OwnerDashboard';

// Fluxo de agendamento
import ServiceList from '../pages/ServiceList';
import ProfessionalList from '../pages/ProfessionalList';
import BookingCalendar from '../pages/BookingCalendar';
import BookingReview from '../pages/BookingReview';

// Outras
import LandingPage from '../pages/LandingPage';
import Login from '../pages/Login';
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
          {/* dashboards */}
          <Route
            path="/dashboard/cliente"
            element={
              role === 'CLIENTE'
                ? <ClienteDashboard />
                : <Navigate to="/" replace />
            }
          />
          <Route
            path="/dashboard/profissional"
            element={
              role === 'PROFISSIONAL'
                ? <ProfissionalDashboard />
                : <Navigate to="/" replace />
            }
          />
          <Route
            path="/dashboard/owner"
            element={
              role === 'DONO_SALAO'
                ? <OwnerDashboard />
                : <Navigate to="/" replace />
            }
          />

          {/* todas as rotas filhas compartilham Layout */}
          <Route element={<Layout />}>
            {role === 'CLIENTE' && (
              <>
                <Route path="/salons/:salonId/services" element={<ServiceList />} />
                <Route
                  path="/salons/:salonId/services/:serviceId/professionals"
                  element={<ProfessionalList />}
                />
                <Route
                  path="/book/:salonId/:serviceId/:professionalId/calendar"
                  element={<BookingCalendar />}
                />
                <Route path="/book/review" element={<BookingReview />} />
              </>
            )}
            {role === 'PROFISSIONAL' && (
              <Route path="/profissional/agenda" element={<ProfissionalDashboard />} />
            )}
            {role === 'DONO_SALAO' && (
              <>
                <Route path="/owner/servicos" element={<OwnerDashboard />} />
                <Route path="/owner/profissionais" element={<OwnerDashboard />} />
              </>
            )}
          </Route>

          {/* fallback para dashboard */}
          <Route path="*" element={<Navigate to={`/dashboard/${role?.toLowerCase()}`} replace />} />
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
