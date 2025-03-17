import React from 'react';
import './App.css';
import { MeniuPrincipal } from './View/MeniuPrincipal';
import { Autentificare } from './View/Autentificare';
import { Administrator } from './View/Administrator';
import { Angajat } from './View/Angajat';
import { Client } from './View/Client';
import { RezervarePachet } from './View/RezervarePachet';
import { Statistici } from './View/Statistici';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';


export const App = (): JSX.Element => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MeniuPrincipal />} />
        <Route path='/Autentificare' element={<Autentificare />} />
        <Route path='/Client' element={<Client />} />
        <Route path='/Angajat' element={<Angajat />} />
        <Route path='/Administrator' element={<Administrator />} />
        <Route path='/RezervarePachet' element={<RezervarePachet />} />
        <Route path='/Statistici' element={<Statistici />} />
      </Routes>
    </Router>
  );
}

export default App;
