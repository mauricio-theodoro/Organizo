import React, { useContext } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';
import './Navbar.css';

/**
 * Barra de navegação superior, com links principais e logout.
 */
const Navbar: React.FC = () => {
  const { logout } = useContext(AuthContext);
  const nav = useNavigate();

  const handleLogout = () => {
    logout();
    nav('/login', { replace: true });
  };

  return (
    <nav className="navbar">
      <div className="navbar__brand">Organizô</div>
      <ul className="navbar__links">
        <li><NavLink to="/" end>Home</NavLink></li>
        <li><NavLink to="/salons">Salões</NavLink></li>
        <li><NavLink to="/book">Agendamentos</NavLink></li>
      </ul>
      <button className="navbar__btn-logout" onClick={handleLogout}>
        Sair
      </button>
    </nav>
  );
};

export default Navbar;
