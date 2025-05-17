
import React from 'react';
import './Button.css';

export type ButtonVariant = 'primary' | 'secondary';

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  /** Variação visual do botão */
  variant?: ButtonVariant;
}

/**
 * Componente de botão reutilizável.
 * @param variant – define esquema de cores.
 * @param rest – demais props de <button>.
 */
export const Button: React.FC<ButtonProps> = ({
  variant = 'primary',
  children,
  ...rest
}) => {
  return (
    <button className={`btn btn--${variant}`} {...rest}>
      {children}
    </button>
  );
};

export default Button;
