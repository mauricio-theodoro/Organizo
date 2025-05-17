import React, { useContext } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';

// Layout e páginas
import Layout from '../components/Layout';
import LandingPage from '../pages/LandingPage';
import Login from '../pages/Login';
import Home from '../pages/Home';
import SalonList from '../pages/SalonList';
import Booking from '../pages/Booking';

const AppRoutes: React.FC = () => {
  const { token } = useContext(AuthContext);

  return (
    <Routes>
      {/* 1) Landing pública */}
      <Route path="/" element={<LandingPage />} />

      {/* 2) Login público */}
      <Route path="/login" element={<Login />} />

      {/* 3) Rotas privadas, todas dentro do Layout */}
      {token ? (
        <Route element={<Layout />}>
          <Route path="/home" element={<Home />} />
          <Route path="/salons" element={<SalonList />} />
          <Route path="/book/:salonId/:serviceId" element={<Booking />} />
          {/* qualquer outra rota não encontrada redireciona para /home */}
          <Route path="*" element={<Navigate to="/home" replace />} />
        </Route>
      ) : (
        /* 4) Se não estiver logado, qualquer rota privada volta para a Landing */
        <Route path="*" element={<Navigate to="/" replace />} />
      )}
    </Routes>
  );
};

export default AppRoutes;
