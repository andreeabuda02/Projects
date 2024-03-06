// MainApp.tsx
import React from 'react';
import { AuthProvider } from './Context/AuthContext';
import App from './App';

const MainApp: React.FC = () => {
  return (
    <AuthProvider>
      <App />
    </AuthProvider>
  );
};

export default MainApp;
