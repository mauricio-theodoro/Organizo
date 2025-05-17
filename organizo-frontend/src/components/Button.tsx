import React from 'react';

export type ButtonVariant = 'primary' | 'secondary';

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: ButtonVariant;
}

/**
 * Botão genérico com duas variações: primary e secondary.
 */
export const Button: React.FC<ButtonProps> = ({
  variant = 'primary',
  children,
  ...rest
}) => {
  return (
    <button
      className={`btn btn--${variant}`}
      {...rest}
    >
      {children}
    </button>
  );
};
