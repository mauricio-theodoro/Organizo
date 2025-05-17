import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import AppRoutes from './routes/AppRoutes';

export default function App() {
  return (
    // <-- aqui fica o único BrowserRouter da aplicação
    <BrowserRouter>
      <AppRoutes />
    </BrowserRouter>
  );
}
