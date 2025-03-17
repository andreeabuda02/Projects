import React, { useEffect, useState } from "react";
import {
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextField,
} from "@material-ui/core";
import { useNavigate, useLocation } from "react-router-dom";
import {
  ResponsiveContainer,
  LineChart,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  Line,
} from "recharts";
import axios from "axios";
import {
  parentDivStyle,
  backButtonStyle,
  titleStyle,
  tableContainerStyle,
  tableHeaderCellStyle,
  backButtonContainerStyle,
  errorMessageStyle,
  tableBodyCellStyle,
} from "../styles/ClientPage.style";
import { USERS_HOST, DEVICES_HOST, MONITORING_HOST, WEBSOCKET_HOST } from "./hosts";

import SockJS from "sockjs-client";
import { Client } from '@stomp/stompjs';

export const ClientPage = (): JSX.Element => {
  // Endpoints utilizate pentru API-uri
  const endpoint = { user: "user/email/" };
  const endpointd1 = { device: "device/user/" };
  const endpointMonitoring = { energy_usage: "energy_usage/" };
  const endpointWebsocket={ websocket: "/topic/consumeOverflow"};

  // State-uri pentru gestionarea datelor si starii UI
  const [devicesList, setDevicesList] = useState<any[]>([]);
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [userName, setUserName] = useState<string>("");
  const [userEmail, setUserEmail] = useState<string>("");
  const [selectedDeviceId, setSelectedDeviceId] = useState<string>("");
  const [selectedDate, setSelectedDate] = useState<string>("");
  const [graphData, setGraphData] = useState<any[]>([
    {energy_usage: 0, hour: 0}
  ]);

  const [view, setView] = useState<"none" | "table" | "graph">("none");

  const socket = new SockJS(WEBSOCKET_HOST.backend_api);
    const stompClient = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000
    });

  const navigate = useNavigate();
  const location = useLocation();

  // Functie pentru a aduce datele de consum ale unui dispozitiv
  const fetchEnergyUsageData = async () => {
    try {
      if (!selectedDeviceId || !selectedDate) {
        alert("Please select a device and date!");
        return;
      }
      const response = await axios.get(
        MONITORING_HOST.backend_api +
          endpointMonitoring.energy_usage +
          selectedDeviceId +
          "/" +
          selectedDate
      );
      setGraphData(
        response.data.map((value: any, index: number) => ({
          hour: index,
          energy_usage: parseFloat(value).toFixed(2),
        }))
      );
    } catch (error) {
      alert("Failed to load energy usage data!");
    }
  };

  // Functie pentru a aduce datele utilizatorului si dispozitivele asociate
  const fetchUserDataAndDevices = async (email: string) => {
    try {
      const userResponse = await axios.get(
        USERS_HOST.backend_api + endpoint.user + email
      );
      const userId = userResponse.data.id_u;
      setUserName(userResponse.data.name);

      const devicesResponse = await axios.get(
        DEVICES_HOST.backend_api + endpointd1.device + userId
      );
      setDevicesList(devicesResponse.data);
    } catch (error) {
      setErrorMessage(
        "Unable to fetch user data or devices. Please try again."
      );
    }
  };

  const createStompClient = (email: string) => {
    stompClient.onConnect = (frame) => {
        console.log('Connected succesfull: ' + frame);

        stompClient.subscribe(endpointWebsocket.websocket, async (message) => {
            const messageReceived = message.body;
            try {
              const userResponse = await axios.get(
                USERS_HOST.backend_api + endpoint.user + email
              );
              const userId = await userResponse.data.id_u;

              let deviceList = []
              const devicesResponse = await axios.get(
                DEVICES_HOST.backend_api + endpointd1.device + userId
              );
              if(devicesResponse.status === 200) {
                  deviceList = await devicesResponse.data
              }
              const idDeviceBegin = messageReceived.indexOf(":") + 2;
              const idDevice = messageReceived.substring(idDeviceBegin, (idDeviceBegin + 36))

              for(var i = 0; i<deviceList.length;i+=1){
                if(String(deviceList.at(i).id_d) === idDevice){
                  alert(messageReceived)
                  break
                }
              }
              
            } catch(error) {
              console.log(error);
            }
        });
    };

    stompClient.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };
}

  // useEffect pentru a verifica daca email-ul utilizatorului este disponibil în locaatie si a aduce datele acestuia
  useEffect(() => {
    if ((location.state as any)?.email) {
      const email = (location.state as any).email;
      setUserEmail(email);
      fetchUserDataAndDevices(email);
      createStompClient(email);
      stompClient.activate();
    } else {
      navigate("/authentication");
      setErrorMessage("Email not provided. Unable to fetch data.");
    }
  }, [location.state?.email]);

  // useEffect pentru a reseta mesajul de eroare dupa o perioada
  useEffect(() => {
    if (errorMessage) {
      const timer = setTimeout(() => setErrorMessage(""), 5000);
      return () => clearTimeout(timer);
    }
  }, [errorMessage]);

  // Functie pentru a reveni la pagina de autentificare
  const handleBack = () => navigate("/authentication");

  return (
    <div style={parentDivStyle}>
      <div style={backButtonContainerStyle}>
        <Button style={backButtonStyle} onClick={handleBack} variant="contained">
          Back
        </Button>
      </div>
      <h1 style={titleStyle}>Hello, {userName}!</h1>
      <h2 style={titleStyle}>Choose an option:</h2>

      <div style={{ marginBottom: '20px'}}>
        <Button
          variant="contained"
          onClick={() => setView("table")}
          style={{ marginRight: 10 }}
        >
          Table
        </Button>
        <Button
          variant="contained"
          onClick={() => setView("graph")}
        >
          Graph
        </Button>
      </div>

      {view === "table" && (
      <>
      {/* Tabel cu lista dispozitivelor */}
      <h2 style={titleStyle}>Your Devices and Their energy usage:</h2>
      <TableContainer component={Paper} style={tableContainerStyle}>
        <Table stickyHeader>
          <TableHead>
            <TableRow>
              <TableCell style={tableHeaderCellStyle}>ID</TableCell>
              <TableCell style={tableHeaderCellStyle}>Address</TableCell>
              <TableCell style={tableHeaderCellStyle}>Description</TableCell>
              <TableCell style={tableHeaderCellStyle}>
                Max Hourly Energy energy usage
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {devicesList.map((device) => (
              <TableRow key={device.id_d}>
                <TableCell style={tableBodyCellStyle}>{device.id_d}</TableCell>
                <TableCell style={tableBodyCellStyle}>{device.address}</TableCell>
                <TableCell style={tableBodyCellStyle}>{device.description}</TableCell>
                <TableCell style={tableBodyCellStyle}>
                  {device.maximum_hourly_energy_consumption}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      </>
      )}
      {errorMessage && <p style={errorMessageStyle}>{errorMessage}</p>}

      {view === "graph" && (
        <>
      {/* Form pentru a selecta dispozitivul si data */}
      <div>
        <FormControl fullWidth margin="normal">
          <InputLabel id="device-select-label">Select Device</InputLabel>
          <Select
            labelId="device-select-label"
            value={selectedDeviceId}
            onChange={(e) => setSelectedDeviceId(e.target.value as string)}
          >
            <MenuItem value="">None</MenuItem>
            {devicesList.map((device) => (
              <MenuItem key={device.id_d} value={device.id_d}>
                {device.address}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <TextField
          label="Select Date"
          type="date"
          InputLabelProps={{ shrink: true }}
          value={selectedDate}
          onChange={(e) => setSelectedDate(e.target.value)}
          fullWidth
        />
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', marginTop: '20px' }}>
          <Button
            variant="contained"
            color="primary"
            onClick={fetchEnergyUsageData}
            style={{ marginTop: '-20px' }} 
          >
            Show Graph
          </Button>
        </div>
      </div>

      {/* Grafic pentru consumul de energie */}
      <ResponsiveContainer width="95%" height={350}>
        <LineChart data={graphData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis
            dataKey="hour"
            tickFormatter={(tick) => (tick < 10 ? `0${tick}` : tick)}
          />
          <YAxis />
          <Tooltip />
          <Legend />
          <Line type="monotone" dataKey="energy_usage" stroke="#8884d8" />
        </LineChart>
      </ResponsiveContainer>
      </>
      )}
    </div>
  );
};

export default ClientPage;
