import React, { useEffect, useState } from "react";
import { Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from "@material-ui/core";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";
import {
  parentDivStyle,
  backButtonStyle,
  titleStyle,
  tableContainerStyle,
  tableHeaderCellStyle,
  backButtonContainerStyle,
  errorMessageStyle,
  tableBodyCellStyle
} from "../styles/ClientPage.style";

import { USERS_HOST, DEVICES_HOST } from "./hosts";


export const ClientPage = (): JSX.Element => {

  const endpoint={user:'user/email/'};
  const endpointd1={device:'device/user/'};

  const [devicesList, setDevicesList] = useState<any[]>([]);
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [userName, setUserName] = useState<string>("");
  const [userEmail, setUserEmail] = useState<string>("");
  interface LocationState {
    email?: string;
  }  

  const navigate = useNavigate();
  const location = useLocation();

  const fetchUserDataAndDevices = async (email: string) => {
    try {
      const userResponse = await axios.get(USERS_HOST.backend_api+endpoint.user+email);
      const userId = userResponse.data.id_u;
      setUserName(userResponse.data.name);

      const devicesResponse = await axios.get(DEVICES_HOST.backend_api+endpointd1.device+userId);//http://localhost:8081/device/user/${userId}
      setDevicesList(devicesResponse.data);
    } catch (error) {
      setErrorMessage("Unable to fetch user data or devices. Please try again.");
    }
  };

  useEffect(() => {
    console.log("Location state:", location.state); 
    if (location.state?.email) {
      const email = location.state.email;
      setUserEmail(email);
      fetchUserDataAndDevices(email);
    } else {
      navigate("/authentication");
      setErrorMessage("Email not provided. Unable to fetch data.");
    }
  }, [location.state?.email]);
  

  useEffect(() => {
    if (errorMessage) {
      const timer = setTimeout(() => setErrorMessage(""), 5000);
      return () => clearTimeout(timer);
    }
  }, [errorMessage]);

  const handleBack = () => navigate("/authentication");

  return (
    <div style={parentDivStyle}>
      <div style={backButtonContainerStyle}>
        <Button style={backButtonStyle} onClick={handleBack} variant="contained">
          Back
        </Button>
      </div>
      <h1 style={titleStyle}>Hello, {userName}!</h1>
      <h2 style={titleStyle}>Your Devices and Their Consumption:</h2>
      <TableContainer component={Paper} style={tableContainerStyle}>
        <Table stickyHeader>
          <TableHead>
            <TableRow>
              <TableCell style={tableHeaderCellStyle}>ID</TableCell>
              <TableCell style={tableHeaderCellStyle}>Address</TableCell>
              <TableCell style={tableHeaderCellStyle}>Description</TableCell>
              <TableCell style={tableHeaderCellStyle}>Max Hourly Energy Consumption</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {devicesList.map((device) => (
              <TableRow key={device.id_d}>
                <TableCell style={tableBodyCellStyle}>{device.id_d}</TableCell>
                <TableCell style={tableBodyCellStyle}>{device.address}</TableCell>
                <TableCell style={tableBodyCellStyle}>{device.description}</TableCell>
                <TableCell style={tableBodyCellStyle}>{device.maximum_hourly_energy_consumption}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      {errorMessage && <p style={errorMessageStyle}>{errorMessage}</p>}
    </div>
  );
};

export default ClientPage;
