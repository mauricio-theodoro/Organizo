import React, { useContext } from 'react'
import { Routes, Route, Navigate } from 'react-router-dom'
import { AuthContext } from '../contexts/AuthContext'

// layout e páginas
import Layout from '../components/Layout'
import LandingPage from '../pages/LandingPage'
import Login from '../pages/Login'
import Home from '../pages/Home'
import SalonList from '../pages/SalonList'
import Booking from '../pages/Booking'
import Booking from '../pages/Booking'
import ClienteDashboard from '../pages/ClienteDashboard';
import ProfissionalDashboard from '../pages/ProfissionalDashboard';
import OwnerDashboard from '../pages/OwnerDashboard';

export default function AppRoutes() {
  const { token, loading } = useContext(AuthContext)

  if (loading) {
    return <p>Carregando...</p>
  }

    return (
       <Routes>
         <Route path="/" element={<LandingPage />} />
         <Route path="/login" element={<Login />} />

         {token ? (
           <>
             <Route path="/cliente/dashboard" element={<ClienteDashboard />} />
             <Route path="/profissional/dashboard" element={<ProfissionalDashboard />} />
             <Route path="/owner/dashboard" element={<OwnerDashboard />} />

             {/* fluxos de cliente, profissional e dono */}
             <Route path="/salons" element={<SalonList />} />
             <Route path="/book/:salonId/:serviceId" element={<Booking />} />
             {/* etc... */}

             <Route path="*" element={<Navigate to={determineBasePath()} replace />} />
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
