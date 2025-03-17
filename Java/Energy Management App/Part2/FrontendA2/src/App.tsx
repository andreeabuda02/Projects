import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { MainPage } from "./app/pages/MainPage";
import { AuthenticationPage } from "./app/pages/AuthenticationPage";
import {RegisterAccountPage} from "./app/pages/RegisterAccountPage";
import AdminPage from "./app/pages/AdminPage";
import ClientPage from "./app/pages/ClientPage";

const App = (): JSX.Element => {
  return (
    <Router>
      <Routes>
        <Route path="/" Component={() => <MainPage />} />
        <Route path="/authentication" Component={() =><AuthenticationPage />} />
        <Route path="/register" Component={() =><RegisterAccountPage />} />
        <Route path="/adminpage" Component={() =><AdminPage />} />
        <Route path="/clientpage" Component={() =><ClientPage />} />
      </Routes>
    </Router>
  );
};

export default App;
