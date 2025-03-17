import React, { useEffect, useState } from "react";
import { Button, TextField } from "@material-ui/core";
import {
  loginBoxStyle,
  parentDivStyle,
  loginButtonStyle,
  errorMessageStyle,
  backButtonStyle,
  titleStyle,
  buttonContainerStyle,
  backButtonContainerStyle,
} from "../styles/AuthenticationPage.style";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import { USERS_HOST } from "./hosts";

export const AuthenticationPage = (): JSX.Element => {
  // State-uri pentru gestionarea email-ului, parolei si mesajelor de eroare
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [errorMessage, setErrorMessage] = useState<string>("");

  const endpoint = { user: "user/" };
  const navigate = useNavigate();

  // Functie pentru a actualiza email-ul
  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>): void => {
    setEmail(event.target.value);
    setErrorMessage("");
  };

  // Functie pentru a actualiza parola
  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>): void => {
    setPassword(event.target.value);
    setErrorMessage("");
  };

  // Functie pentru autentificare
  const handleLogin = async () => {
    try {
      const response = await axios.get(USERS_HOST.backend_api + endpoint.user + email + "/" + password);
      if (response.status === 200) {
        setEmail("");
        setPassword("");
        const userRole = response.data.role; // Verificam rolul utilizatorului
        if (userRole === "admin") {
          navigate("/AdminPage", { state: { email } });
        } else if (userRole === "client") {
          navigate("/ClientPage", { state: { email } });
        } else {
          setErrorMessage("Unknown role. Please contact support.");
        }
      }
    } catch (error) {
      setErrorMessage("Invalid email or password.");
    }
  };

  // Functie pentru navigare la pagina de inregistrare
  const handleRegister = () => {
    setEmail("");
    setPassword("");
    navigate("/register");
  };

  // Functie pentru a naviga inapoi
  const handleBack = () => {
    setEmail("");
    setPassword("");
    navigate("/");
  };

  // Efect pentru a reseta mesajele de eroare dupa 5 secunde
  useEffect(() => {
    if (errorMessage) {
      const timer = setTimeout(() => setErrorMessage(""), 5000);
      return () => clearTimeout(timer);
    }
  }, [errorMessage]);

  return (
    <div style={parentDivStyle}>
      {/* Buton de navigare inapoi */}
      <div style={backButtonContainerStyle}>
        <Button style={backButtonStyle} onClick={handleBack} variant="contained">
          Back
        </Button>
      </div>

      {/* Titlul paginii */}
      <h2 style={titleStyle}>Login</h2>

      {/* Form-ul pentru email si parola */}
      {renderLoginForm(email, handleEmailChange, password, handlePasswordChange)}

      {/* Butoanele pentru login si register */}
      {renderActionButtons(handleRegister, handleLogin)}

      {/* Mesajele de eroare */}
      {errorMessage && <p style={errorMessageStyle}>{errorMessage}</p>}
    </div>
  );
};

// Functie pentru a randa formularul de login
const renderLoginForm = (
  email: string,
  handleEmailChange: (event: React.ChangeEvent<HTMLInputElement>) => void,
  password: string,
  handlePasswordChange: (event: React.ChangeEvent<HTMLInputElement>) => void
) => (
  <div style={loginBoxStyle}>
    <TextField
      label="Email"
      variant="outlined"
      onChange={handleEmailChange}
      fullWidth
      value={email}
      style={{ marginBottom: "20px" }}
    />
    <TextField
      label="Password"
      variant="outlined"
      type="password"
      onChange={handlePasswordChange}
      fullWidth
      value={password}
      style={{ marginBottom: "20px" }}
    />
  </div>
);

// Functie pentru a randa butoanele de login si register
const renderActionButtons = (handleRegister: () => void, handleLogin: () => void) => (
  <div style={buttonContainerStyle}>
    <Button style={loginButtonStyle} onClick={handleRegister} variant="contained">
      Register
    </Button>
    <Button style={loginButtonStyle} onClick={handleLogin} variant="contained">
      Login
    </Button>
  </div>
);

export default AuthenticationPage;
