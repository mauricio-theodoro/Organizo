import React from 'react';
import './Card.css';

interface CardProps {
  /** Título principal do cartão */
  title: string;
  /** Conteúdo interno (pode ser texto ou JSX) */
  children: React.ReactNode;
  /** Ação opcional ao clicar no cartão */
  onClick?: () => void;
}

/**
 * Componente de cartão genérico.
 * Pode exibir título, conteúdo e responder a cliques.
 */
export const Card: React.FC<CardProps> = ({ title, children, onClick }) => {
  return (
    <div className="card" onClick={onClick}>
      <h3 className="card__title">{title}</h3>
      <div className="card__content">{children}</div>
    </div>
  );
};

export default Card;
