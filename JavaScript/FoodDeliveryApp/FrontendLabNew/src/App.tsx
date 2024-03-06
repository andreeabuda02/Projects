import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import React from 'react';
import MenuPage from './Menu/MenuPage';
import { Login } from './Pages/Login';
import { Home } from './Pages/Home';
import { EditProfile } from './Pages/EditProfile';
import { Register } from './Pages/Register';
import { AdminPage } from './Pages/AdminPage';
import { MainPage } from './Pages/MainPage';
import { RestaurantPage } from './Pages/RestaurantPage';

export const App = (): JSX.Element => {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/Login" element={<Login />} />
        <Route path="/menu/:restaurantId" element={<MenuPage />} />
        <Route path="/Home" element={<Home/>} />
        <Route path="/Home/EditProfile" element={<EditProfile/>}/>
        <Route path="/Register" element={<Register/>}/>
        <Route path="/AdminPage" element={<AdminPage/>}/>
        <Route path="/Home/Restaurant" element={<RestaurantPage/>}/>
      </Routes>
    </Router>
  );
}

export default App;
