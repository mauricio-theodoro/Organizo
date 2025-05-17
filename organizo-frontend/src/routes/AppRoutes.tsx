import React, { useContext } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';
import LandingPage from '../pages/LandingPage/LandingPage';
import Login from '../pages/Login';
import Home from '../pages/Home';
import SalonList from '../pages/SalonList';
import Booking from '../pages/Booking';

const AppRoutes: React.FC = () => {
  const { token } = useContext(AuthContext);

  return (
    <Routes>
      {/* Landing public */}
      <Route path="/" element={<LandingPage />} />

      {/* Login */}
      <Route path="/login" element={<Login />} />

      {/* Rotas privadas (cliente ou dono) */}
      {token ? (
        <>
          <Route path="/home" element={<Home />} />
          <Route path="/salons" element={<SalonList />} />
          <Route path="/book/:salonId/:serviceId" element={<Booking />} />
          <Route path="*" element={<Navigate to="/home" replace />} />
        </>
      ) : (
        <Route path="*" element={<Navigate to="/" replace />} />
      )}
    </Routes>
  );
};

export default AppRoutes;
