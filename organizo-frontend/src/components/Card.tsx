import React from 'react';

interface CardProps {
  title?: string;
  children: React.ReactNode;
}

/**
 * Card genérico com título opcional e conteúdo.
 */
export const Card: React.FC<CardProps> = ({ title, children }) => {
  return (
    <div className="card">
      {title && <h2 className="card__title">{title}</h2>}
      <div className="card__body">
        {children}
      </div>
    </div>
  );
};
