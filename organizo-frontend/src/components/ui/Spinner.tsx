// src/components/ui/Spinner.tsx
import React from 'react';

export function Spinner() {
  return (
    <div role="status" className="spinner">
      <svg
        className="animate-spin h-8 w-8 text-organizo"
        xmlns="http://www.w3.org/2000/svg"
        fill="none" viewBox="0 0 24 24"
      >
        <circle
          className="opacity-25" cx="12" cy="12" r="10"
          stroke="currentColor" strokeWidth="4"
        />
        <path
          className="opacity-75" fill="currentColor"
          d="M4 12a8 8 0 018-8v4l3.5-3.5-3.5-3.5V4a10 10 0 00-10 10h4z"
        />
      </svg>
      <span className="sr-only">Loading...</span>
    </div>
  );
}
