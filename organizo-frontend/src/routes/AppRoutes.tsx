import React, { useContext } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';

// Dashboards por perfil
import ClienteDashboard from '../pages/ClienteDashboard';
import ProfissionalDashboard from '../pages/ProfissionalDashboard';
import OwnerDashboard from '../pages/OwnerDashboard';
import AdminDashboard from '../pages/AdminDashboard';

// Fluxo de agendamento
import ServiceList from '../pages/ServiceList';
import ProfessionalList from '../pages/ProfessionalList';
import BookingCalendar from '../pages/BookingCalendar';
import BookingReview from '../pages/BookingReview';

// Outras
import LandingPage from '../pages/LandingPage';
import Login from '../pages/Login';
import Layout from '../components/Layout'; // Layout compartilhado

/**
 * Componente principal de roteamento da aplicação.
 * Define rotas públicas e privadas, controlando o acesso baseado no token e no perfil (role) do usuário.
 * Garante que as rotas só sejam renderizadas após o carregamento inicial do AuthContext.
 */
export default function AppRoutes() {
  // Obtém o estado de autenticação do contexto
  const { token, role, loading } = useContext(AuthContext);

  /**
   * Função auxiliar para determinar o path do dashboard padrão baseado no perfil do usuário.
   * Usada para redirecionamentos.
   */
  const getDefaultDashboardPath = (userRole: string | null | undefined): string => {
    switch (userRole) {
      case 'CLIENTE': return '/dashboard/cliente';
      case 'PROFISSIONAL': return '/dashboard/profissional';
      case 'DONO_SALAO': return '/dashboard/owner';
      case 'ADMIN': return '/dashboard/admin';
      default: return '/'; // Fallback para landing page
    }
  };

  // Exibe um indicador de carregamento global enquanto o AuthContext verifica o estado inicial
  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        {/* Idealmente, usar um componente Spinner aqui */}
        <p className="text-lg text-muted">Carregando aplicação...</p>
      </div>
    );
  }

  // Após o carregamento, renderiza as rotas apropriadas baseado na existência do token
  return (
    <Routes>
      {/* ==================================================================
          GRUPO DE ROTAS PÚBLICAS (Renderizadas apenas se NÃO houver token)
          ================================================================== */}
      {!token && (
        <>
          <Route path="/" element={<LandingPage />} />
          <Route path="/login" element={<Login />} />
          {/* Qualquer outra rota pública não definida redireciona para a Landing Page */}
          <Route path="*" element={<Navigate to="/" replace />} />
        </>
      )}

      {/* ==================================================================
          GRUPO DE ROTAS PRIVADAS (Renderizadas apenas se HOUVER token)
          ================================================================== */}
      {token && (
        <>
          {/* --- Dashboards Principais (Acesso direto por Role) --- */}
          <Route
            path="/dashboard/cliente"
            element={role === 'CLIENTE' ? <ClienteDashboard /> : <Navigate to={getDefaultDashboardPath(role)} replace />}
          />
          <Route
            path="/dashboard/profissional"
            element={role === 'PROFISSIONAL' ? <ProfissionalDashboard /> : <Navigate to={getDefaultDashboardPath(role)} replace />}
          />
          <Route
            path="/dashboard/owner"
            element={role === 'DONO_SALAO' ? <OwnerDashboard /> : <Navigate to={getDefaultDashboardPath(role)} replace />}
          />
          <Route
            path="/dashboard/admin"
            element={role === 'ADMIN' ? <AdminDashboard /> : <Navigate to={getDefaultDashboardPath(role)} replace />}
          />

          {/* --- Rotas Aninhadas com Layout Compartilhado --- */}
          {/* TODO: Avaliar se o Admin usará este Layout ou terá um específico */}
          <Route element={<Layout />}>
            {/* Rotas específicas do Cliente */}
            {role === 'CLIENTE' && (
              <>
                <Route path="/salons/:salonId/services" element={<ServiceList />} />
                <Route path="/salons/:salonId/services/:serviceId/professionals" element={<ProfessionalList />} />
                <Route path="/book/:salonId/:serviceId/:professionalId/calendar" element={<BookingCalendar />} />
                <Route path="/book/review" element={<BookingReview />} />
                {/* Outras rotas do cliente aqui */}
              </>
            )}
            {/* Rotas específicas do Profissional */}
            {role === 'PROFISSIONAL' && (
              <>
                {/* Outras rotas do profissional aqui */}
              </>
            )}
            {/* Rotas específicas do Dono do Salão */}
            {role === 'DONO_SALAO' && (
              <>
                {/* Outras rotas do dono aqui */}
              </>
            )}
            {/* Rotas específicas do Admin (se usar Layout) */}
            {/* {role === 'ADMIN' && (<> </>)} */}
          </Route>

          {/* --- Fallback para Rotas Privadas --- */}
          {/* Se logado, qualquer rota não encontrada redireciona para o dashboard padrão */}
          <Route path="*" element={<Navigate to={getDefaultDashboardPath(role)} replace />} />
        </>
      )}
    </Routes>
  );
}

