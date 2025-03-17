import React, { useState, useEffect } from "react";
import { Button, TextField } from "@material-ui/core";
import { useNavigate } from "react-router-dom";
import {
  mainDivStyle,
  parentDivStyle,
  buttonContainerStyle,
  formContainerStyle,
  errorMessageStyle,
  registerButtonStyle,
  backButtonContainerStyle,
  backButtonStyle,
} from "../styles/RegisterAccountPage.style";
import axios from "axios";

import { USERS_HOST } from "./hosts";

export const RegisterAccountPage = (): JSX.Element => {
  // Endpoints pentru API
  const endpoint = { user: "user/" };
  const endpoint2 = { user: "user/email/" };

  // State-uri pentru gestionarea valorilor din formular si a mesajelor de eroare
  const [name, setName] = useState<string>("");
  const [role, setRole] = useState<string>("client"); // Rol implicit: client
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [confirmPassword, setConfirmPassword] = useState<string>("");
  const [errorMessage, setErrorMessage] = useState<string>("");

  const navigate = useNavigate();

  // Functie pentru validarea email-ului
  const isEmail = (): boolean =>
    /[a-zA-Z0-9_\-]+@([a-zA-Z0-9]+).+(com|ro|es|org)/i.test(email);

  // Functie pentru validarea parolei
  const isPassword = (): boolean =>
    /((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\W]).{6,20})/i.test(password);

  // Functie pentru validarea numelui
  const isName = (): boolean =>
    /^[A-Za-z]+([ '-][A-Za-z]+)*$/i.test(name);

  // Functie pentru revenirea la pagina de autentificare
  const goBack = (): void => {
    navigate("/authentication");
  };

  // Functie pentru crearea unui cont nou
  const createAccount = async () => {
    // Validarea campurilor obligatorii
    if (!name || !role || !email || !password || !confirmPassword) {
      setErrorMessage("Please fill in all fields.");
      return;
    }
    if (password !== confirmPassword) {
      setErrorMessage("Passwords do not match.");
      return;
    }
    if (!isEmail()) {
      setErrorMessage("Wrong email");
      return;
    }
    if (!isPassword()) {
      setErrorMessage("Wrong password");
      return;
    }
    if (!isName()) {
      setErrorMessage("Wrong name!");
      return;
    }

    try {
      // Verificare daca email-ul exista deja
      const emailExistent = await axios.get(USERS_HOST.backend_api + endpoint2.user + email);
      if (emailExistent.status === 200) {
        alert("An account with this email already exists");
        return;
      }
    } catch (error) {
      // Creare cont nou daca email-ul nu exista
      try {
        await axios.post(USERS_HOST.backend_api + endpoint.user, {
          name: name,
          role: role,
          email: email,
          password: password,
        });
        alert("The account has been created!");
        // Resetarea campurilor dupa inregistrare
        setName("");
        setEmail("");
        setPassword("");
        setConfirmPassword("");
      } catch (error) {
        alert("This account cannot be created!");
      }
    }
  };

  // Functie pentru eliminarea mesajelor de eroare dupa 5 secunde
  useEffect(() => {
    if (errorMessage) {
      const timer = setTimeout(() => setErrorMessage(""), 5000);
      return () => clearTimeout(timer);
    }
  }, [errorMessage]);

  return (
    <div style={mainDivStyle}>
      {/* Buton pentru revenirea la autentificare */}
      <div style={backButtonContainerStyle}>
        <Button style={backButtonStyle} onClick={goBack} variant="contained">
          Back
        </Button>
      </div>

      {/* Formularul de inregistrare */}
      <div style={parentDivStyle}>
        <h1>Create an Account</h1>
        <div style={formContainerStyle}>
          <TextField
            label="Name"
            variant="outlined"
            fullWidth
            onChange={(e) => setName(e.target.value)}
            value={name}
            style={{ marginBottom: "20px" }}
          />
          <TextField
            label="Role"
            variant="outlined"
            fullWidth
            onChange={(e) => setRole(e.target.value)}
            value={role}
            style={{ marginBottom: "20px" }}
            disabled
          />
          <TextField
            label="Email"
            variant="outlined"
            type="email"
            fullWidth
            onChange={(e) => setEmail(e.target.value)}
            value={email}
            style={{ marginBottom: "20px" }}
          />
          <TextField
            label="Password"
            variant="outlined"
            type="password"
            fullWidth
            onChange={(e) => setPassword(e.target.value)}
            value={password}
            style={{ marginBottom: "20px" }}
          />
          <TextField
            label="Confirm Password"
            variant="outlined"
            type="password"
            fullWidth
            onChange={(e) => setConfirmPassword(e.target.value)}
            value={confirmPassword}
            style={{ marginBottom: "20px" }}
          />
          {/* Afisarea mesajelor de eroare */}
          {errorMessage && <p style={errorMessageStyle}>{errorMessage}</p>}
        </div>

        {/* Buton pentru inregistrare */}
        <div style={buttonContainerStyle}>
          <Button onClick={createAccount} style={registerButtonStyle} variant="contained">
            Register
          </Button>
        </div>
      </div>
    </div>
  );
};
