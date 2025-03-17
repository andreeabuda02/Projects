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
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [errorMessage, setErrorMessage] = useState<string>("");

  const endpoint={user:'user/'};

  const navigate = useNavigate();

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>): void => {
    setEmail(event.target.value);
    setErrorMessage("");
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>): void => {
    setPassword(event.target.value);
    setErrorMessage("");
  };

  const handleLogin = async () => {
    try {
      const response = await axios.get(USERS_HOST.backend_api+endpoint.user+email+'/'+password);
      
      if (response.status === 200) {
        setEmail("");
        setPassword("");
        const userRole = response.data.role; 
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

  const handleRegister = () => {
    setEmail("");
    setPassword("");
    navigate("/register");
  };

  const handleBack = () => {
    setEmail("");
    setPassword("");
    navigate("/");
  };

  useEffect(() => {
    if (errorMessage) {
      const timer = setTimeout(() => setErrorMessage(""), 5000);
      return () => clearTimeout(timer); 
    }
  }, [errorMessage]);

  return (
    <div style={parentDivStyle}>
      <div style={backButtonContainerStyle}>
        <Button style={backButtonStyle} onClick={handleBack} variant="contained">
          Back
        </Button>
      </div>
      <h2 style={titleStyle}>Login</h2>
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
      <div style={buttonContainerStyle}>
        <Button style={loginButtonStyle} onClick={handleRegister} variant="contained">
          Register
        </Button>
        <Button style={loginButtonStyle} onClick={handleLogin} variant="contained">
          Login
        </Button>
      </div>
      {errorMessage && <p style={errorMessageStyle}>{errorMessage}</p>}
    </div>
  );
};
