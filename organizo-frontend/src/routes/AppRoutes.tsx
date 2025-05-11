import React, { useContext } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';
import Login from '../pages/Login';
import Home from '../pages/Home';
import SalonList from '../pages/SalonList';
import Booking from '../pages/Booking';

export const AppRoutes: React.FC = () => {
  const auth = useContext(AuthContext);

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        {auth?.token ? (
          <>
            <Route path="/" element={<Home />} />
            <Route path="/salons" element={<SalonList />} />
            <Route path="/book/:salonId/:serviceId" element={<Booking />} />
          </>
        ) : (
          <Route path="*" element={<Navigate to="/login" replace />} />
        )}
      </Routes>
    </BrowserRouter>
  );
};
