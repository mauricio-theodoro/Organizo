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

export default function AppRoutes() {
  const { token, loading } = useContext(AuthContext)

  if (loading) {
    return <p>Carregando...</p>
  }

  return (
    <Routes>
      {/* Rotas públicas */}
      <Route path="/" element={<LandingPage />} />
      <Route path="/login" element={<Login />} />

      {/* Rotas privadas */}
      {token ? (
        <Route element={<Layout />}>
          <Route path="/home" element={<Home />} />
          <Route path="/salons" element={<SalonList />} />
          <Route path="/book/:salonId/:serviceId" element={<Booking />} />
          <Route path="*" element={<Navigate to="/home" replace />} />
        </Route>
      ) : (
        <Route path="*" element={<Navigate to="/" replace />} />
      )}
    </Routes>
  )
}
