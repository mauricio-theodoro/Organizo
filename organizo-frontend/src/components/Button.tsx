import React from 'react';

export interface ButtonProps {
  variant?: 'primary' | 'secondary';
  onClick: () => void;
  children: React.ReactNode;
}

/**
 * Botão reutilizável com variantes de estilo.
 */
export const Button: React.FC<ButtonProps> = ({
  variant = 'primary',
  onClick,
  children,
}) => {
  return (
    <button
      className={`btn btn--${variant}`}
      onClick={onClick}
      type="button"
    >
      {children}
    </button>
  );
};
