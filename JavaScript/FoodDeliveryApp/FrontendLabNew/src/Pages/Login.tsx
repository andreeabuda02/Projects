import { Button, TextField, Select, MenuItem } from "@mui/material";
import { loginButtonStyle, parentDivStyle } from "./Login.styles";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { SelectChangeEvent } from '@mui/material/Select';
import axios from 'axios';

export const Login = (): JSX.Element => {
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [userType, setUserType] = useState<string>("user"); 
  const [errorMessage, setErrorMessage] = useState<string>("");  // Adaugat pentru gestionarea mesajului de eroare
  const navigate = useNavigate();

  const onChangeEmail = (event: React.ChangeEvent<HTMLInputElement>): void => {
    setEmail(event.target.value);
    setErrorMessage("");
  };

  const onChangePassword = (event: React.ChangeEvent<HTMLInputElement>): void => {
    setPassword(event.target.value);
    setErrorMessage("");
  };
  

  const onChangeUserType = (event: SelectChangeEvent<string>): void => {
    setUserType(event.target.value);
  };

  const login = async (event: React.MouseEvent<HTMLButtonElement>) => {

    try{
        if (userType === "user") {
          const client = await axios.get("http://localhost:8080/ClientByEmailAndParola/" + email + "/" + password);
          if(client.status === 200) {
            const clientId = client.data.clientId;
            const prenumeClient = client.data.prenume;
            navigate("/Home", {state:{clientId: clientId, prenume: prenumeClient}});//parametrii pe care ii primeste pagina
          }
        } else if (userType === "admin") {
          const admin = await axios.get("http://localhost:8080/AdminByEmailAndParola/" + email + "/" + password);
          if(admin.status === 200) {
            navigate("/AdminPage");
          }
        }
    }
    catch(error){
      setErrorMessage("Email sau parolă incorectă!");
    }  
  };

  const creareCont = (event: React.MouseEvent<HTMLButtonElement>): void => {
    navigate("/Register");
  };

  return (
    <div style={parentDivStyle}>
      <div style={{ marginTop: 20 }}>
        <TextField label="Email" variant="standard" onChange={onChangeEmail} />
      </div>
      <div style={{ marginTop: 20 }}>
        <TextField label="Password" variant="standard" type="password" onChange={onChangePassword} />
      </div>
      <div style={{ marginTop: 20 }}>
        <Select value={userType} onChange={onChangeUserType}>
          <MenuItem value="user">User</MenuItem>
          <MenuItem value="admin">Administrator</MenuItem>
        </Select>
      </div>
      <div>
        <Button style={loginButtonStyle} onClick={creareCont} variant="contained">
          Register
        </Button>
        <Button style={loginButtonStyle} onClick={login} variant="contained">
          Login
        </Button>
      </div>
      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>} {}
    </div>
  );
};