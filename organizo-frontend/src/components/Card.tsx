import React from 'react';

export interface CardProps {
  children: React.ReactNode;
  className?: string;
  title?: string; // Adiciona a propriedade title
}

/**
 * Card genérico para agrupar conteúdo com sombra e padding.
 */
export const Card: React.FC<CardProps> = ({ children, className = '', title }) => (
  <div className={`card ${className}`.trim()}>
    {title && <h2 className="text-lg font-semibold mb-2">{title}</h2>} {/* Renderiza o título, se fornecido */}
    {children}
  </div>
);