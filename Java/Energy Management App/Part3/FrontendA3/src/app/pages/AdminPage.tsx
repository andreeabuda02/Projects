import React, { useState, useRef, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Button, TextField, MenuItem, Select, FormControl, InputLabel, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, setRef } from "@material-ui/core";
import axios from "axios";
import {
  parentDivStyle,
  backButtonStyle,
  titleStyle,
  buttonContainerStyle,
  formContainerStyle,
  cbButtonContainerStyle,
  errorMessageStyle,
} from "../styles/AdminPage.style";

import SockJS from "sockjs-client";
import { Client } from '@stomp/stompjs';

import { USERS_HOST, DEVICES_HOST, WEBSOCKET_MESSAGE_HOST, BROKER_MESSAGE_HOST, MESSAGING_HOST } from "./hosts";

export const AdminPage = (): JSX.Element => {
  // State-uri pentru gestionarea erorilor si actiunilor
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [showDeviceButtons, setShowDeviceButtons] = useState<boolean>(false);
  const [showUserButtons, setShowUserButtons] = useState<boolean>(false);
  const [userAction, setUserAction] = useState<string | null>(null);
  const [deviceAction, setDeviceAction] = useState<string | null>(null);
  const [userIdOptions, setUserIdOptions] = useState<any[]>([]);
  const [deviceIdOptions, setDeviceIdOptions] = useState<any[]>([]);
  const [devicesList, setDevicesList] = useState<any[]>([]);
  const [usersList, setUsersList] = useState<any[]>([]);
  const [adminName, setAdminName] = useState<string>("");
  const [userEmail, setUserEmail] = useState<string>("");
  const [userRole, setUserRole] = useState<string>("");

  // Endpoints pentru request-uri
  const endpoint = { user: "user/" };
  const endpoint1 = { user: "user" };
  const endpoint2 = { user: "user/email/" };
  const endpointd = { device: "device" };
  const endpointd1 = { device: "device/" };

  // State-uri pentru formulare
  const [formData, setFormData] = useState({
    id: "",
    name: "",
    role: "client",
    email: "",
    password: "",
  });

  const [deviceData, setDeviceData] = useState({
    id: "",
    address: "",
    description: "",
    maxEnergy: "",
    userId: "",
  });


  const navigate = useNavigate();
  const location = useLocation();
  
// Functie pentru a obtine detalii despre admin
  useEffect(() => {
    if ((location.state as { email?: string })?.email) {
      const email = (location.state as { email: string }).email;
      console.log(location.state.token);
      setUserEmail(email);

      axios.get(USERS_HOST.backend_api + endpoint2.user + email, {headers: {'Authorization': "Bearer " + location.state.token}}) //`http://localhost:8080/user/email/${email}`
        .then((response) => {
          setAdminName(response.data.name); // Seteaza numele adminului
          setUserRole(response.data.role); 
        })
        .catch(() => {
          setErrorMessage("Unable to fetch admin details.");
        });
    } else {
      navigate("/authentication");
      setErrorMessage("Email not provided. Unable to fetch admin details.");
    }
  }, [location.state?.email]);

  // Functie pentru a sterge mesajul de eroare dupa un timp
  useEffect(() => {
    if (errorMessage) {
      const timer = setTimeout(() => setErrorMessage(""), 2000);
      return () => clearTimeout(timer);
    }
  }, [errorMessage]);

  // Functie pentru a popula lista de utilizatori in cazul actiunilor update/delete
  useEffect(() => {
    if (userAction === "update" || userAction === "delete") {
      axios.get(USERS_HOST.backend_api + endpoint1.user, {headers: {'Authorization': "Bearer " + location.state.token}}) //"http://localhost:8080/user"
        .then((response) => setUserIdOptions(response.data))
        .catch(() => setErrorMessage("Failed to fetch user IDs"));
    }
  }, [userAction]);

  // Functie pentru a popula lista de utilizatori si dispozitive cand se schimba actiunea dispozitivelor
  useEffect(() => {
    axios.get(USERS_HOST.backend_api + endpoint1.user, {headers: {'Authorization': "Bearer " + location.state.token}}) //"http://localhost:8080/user"
      .then((response) => setUserIdOptions(response.data))
      .catch(() => setErrorMessage("Failed to fetch user IDs"));
    if (deviceAction === "update" || deviceAction === "delete") {
      axios.get(DEVICES_HOST.backend_api + endpointd.device, {headers: {'Authorization': "Bearer " + location.state.token}}) //"http://localhost:8081/device"
        .then((response) => setDeviceIdOptions(response.data))
        .catch(() => setErrorMessage("Failed to fetch device IDs"));
    }
  }, [deviceAction]);

  // Functie pentru a obtine lista de utilizatori
  const obtainUsersList = async () => {
    try {
      const response = await axios.get(USERS_HOST.backend_api + endpoint1.user, {headers: {'Authorization': "Bearer " + location.state.token}}); //"http://localhost:8080/user"
      if (response.status === 200) {
        setUsersList(response.data);
      } else {
        setErrorMessage("Something went wrong when loading users!");
      }
    } catch (error) {
      setErrorMessage("Something went wrong when loading users!");
    }
  };

  // Functie pentru a obtine lista de dispozitive
  const obtainDevicesList = async () => {
    try {
      const response = await axios.get(DEVICES_HOST.backend_api + endpointd.device, {headers: {'Authorization': "Bearer " + location.state.token}}); //"http://localhost:8081/device"
      if (response.status === 200) {
        setDevicesList(response.data);
      } else {
        setErrorMessage("Something went wrong when loading devices!");
      }
    } catch (error) {
      setErrorMessage("Something went wrong when loading devices!");
    }
  };

  // Functie pentru a merge inapoi la autentificare
  const handleBack = () => {
    navigate("/authentication");
  };

  // Functie pentru a seta vizibilitatea butoanelor pentru dispozitive
  const handleOperationsWithDevices = () => {
    setShowDeviceButtons(true);
    setShowUserButtons(false);
    setUserAction(null);
    setDeviceAction(null);
  };

  // Functie pentru a seta vizibilitatea butoanelor pentru utilizatori
  const handleOperationsWithUsers = () => {
    setShowDeviceButtons(false);
    setShowUserButtons(true);
    setUserAction(null);
    setDeviceAction(null);
  };

  // Functie pentru a gestiona actiunile utilizatorilor
  const handleUserAction = (action: string) => {
    setUserAction(action);
    setFormData({ id: "", name: "", role: "client", email: "", password: "" });
    if (action === "seeAll") {
      obtainUsersList();
    }
  };

  // Functie pentru a gestiona actiunile dispozitivelor
  const handleDeviceAction = (action: string) => {
    setDeviceAction(action);
    setDeviceData({ id: "", address: "", description: "", maxEnergy: "", userId: "" });
    if (action === "seeAll") {
      obtainDevicesList();
    }
  };

  // Functie pentru a gestiona schimbarile in formularul utilizatorilor
  const handleFormChange = (field: string, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }));

    if (field === "id") {
      for (let i = 0; i < userIdOptions.length; i++) {
        if (String(userIdOptions[i].id_u) === value) {
          setFormData({
            id: userIdOptions[i].id_u,
            name: userIdOptions[i].name,
            role: userIdOptions[i].role,
            email: userIdOptions[i].email,
            password: userIdOptions[i].password,
          });
        }
      }
    }
  };

  // Functie pentru a gestiona schimbarile in formularul dispozitivelor
  const handleDeviceChange = (field: string, value: string) => {
    setDeviceData((prev) => ({ ...prev, [field]: value }));
  };

  // Functie pentru a trimite datele din formularul utilizatorului
  const handleUserSubmit = () => {
    if (userAction === "add") {
      axios.post(USERS_HOST.backend_api + endpoint1.user, formData, {headers: {'Authorization': "Bearer " + location.state.token}}) //"http://localhost:8080/user"
        .then(() => {
          alert("User created successfully");
          setFormData({ id: "", name: "", role: "client", email: "", password: "" });
        })
        .catch(() => setErrorMessage("Failed to create user"));
    } else if (userAction === "update") {
      axios.put(USERS_HOST.backend_api + endpoint.user + formData.id, formData, {headers: {'Authorization': "Bearer " + location.state.token}}) //`http://localhost:8080/user/${formData.id}`
        .then(() => {
          alert("User updated successfully");
          setFormData({ id: "", name: "", role: "client", email: "", password: "" });
        })
        .catch(() => setErrorMessage("Failed to update user"));
    } else if (userAction === "delete") {
      axios.delete(USERS_HOST.backend_api + endpoint.user + formData.id, {headers: {'Authorization': "Bearer " + location.state.token}}) //`http://localhost:8080/user/${formData.id}`
        .then(() => {
          alert("User deleted successfully");
          setFormData({ id: "", name: "", role: "client", email: "", password: "" });
        })
        .catch(() => setErrorMessage("Failed to delete user"));
    }
  };

  // Functie pentru a trimite datele din formularul dispozitivelor
  const handleDeviceSubmit = () => {
    if (deviceAction === "add") {
      let user_id = "";
      for (let i = 0; i < userIdOptions.length; i++) {
        if (String(userIdOptions[i].id_u) === deviceData.userId) {
          user_id = deviceData.userId;
        }
      }
      //"http://localhost:8081/device/addDevice"
      if (user_id === "") {
        axios.post(DEVICES_HOST.backend_api + endpointd.device, {
          address: deviceData.address,
          description: deviceData.description,
          maximum_hourly_energy_consumption: deviceData.maxEnergy,
          userDeviceMapping: null,
        }, {headers: {'Authorization': "Bearer " + location.state.token}})
          .then(() => {
            alert("Device created successfully");
            setDeviceData({ id: "", address: "", description: "", maxEnergy: "", userId: "" });
          })
          .catch(() => setErrorMessage("Failed to create device"));
      } else {
        axios.post(DEVICES_HOST.backend_api + endpointd.device, {
          address: deviceData.address,
          description: deviceData.description,
          maximum_hourly_energy_consumption: deviceData.maxEnergy,
          userDeviceMapping: {
            userId: user_id,
            devicesList: [],
          },
        }, {headers: {'Authorization': "Bearer " + location.state.token}})
          .then(() => {
            alert("Device created successfully");
            setDeviceData({ id: "", address: "", description: "", maxEnergy: "", userId: "" });
          })
          .catch(() => setErrorMessage("Failed to create device"));
      }
    } else if (deviceAction === "update") {
      let user_id = "";
      for (let i = 0; i < userIdOptions.length; i++) {
        if (String(userIdOptions[i].id_u) === deviceData.userId) {
          user_id = deviceData.userId;
        }
      }
      //"http://localhost:8081/device/updateDevice/${deviceData.id}"
      if (user_id === "") {
        axios.put(DEVICES_HOST.backend_api + endpointd1.device + deviceData.id, {
          id_d: deviceData.id,
          address: deviceData.address,
          description: deviceData.description,
          maximum_hourly_energy_consumption: deviceData.maxEnergy,
          userDeviceMapping: null,
        }, {headers: {'Authorization': "Bearer " + location.state.token}})
          .then(() => {
            alert("Device updated successfully");
            setDeviceData({ id: "", address: "", description: "", maxEnergy: "", userId: "" });
          })
          .catch(() => setErrorMessage("Failed to update device"));
      } else {
        let devices = [];
        for (let i = 0; i < deviceIdOptions.length; i++) {
          if (deviceIdOptions[i].userDeviceMapping !== null) {
            if (String(deviceIdOptions[i].userDeviceMapping.userId) === deviceData.userId) {
              devices = deviceIdOptions[i].userDeviceMapping.devicesList;
            }
          }
        }
        axios.put(DEVICES_HOST.backend_api + endpointd1.device + deviceData.id, {
          id_d: deviceData.id,
          address: deviceData.address,
          description: deviceData.description,
          maximum_hourly_energy_consumption: deviceData.maxEnergy,
          userDeviceMapping: {
            userId: user_id,
            devicesList: devices,
          },
        }, {headers: {'Authorization': "Bearer " + location.state.token}})
          .then(() => {
            alert("Device updated successfully");
            setDeviceData({ id: "", address: "", description: "", maxEnergy: "", userId: "" });
          })
          .catch(() => setErrorMessage("Failed to update device"));
      }
    } else if (deviceAction === "delete") {
      axios.delete(DEVICES_HOST.backend_api + endpointd1.device + deviceData.id, {headers: {'Authorization': "Bearer " + location.state.token}}) //"http://localhost:8081/device/deleteDevice/${deviceData.id}"
        .then(() => {
          alert("Device deleted successfully");
          setDeviceData({ id: "", address: "", description: "", maxEnergy: "", userId: "" });
        })
        .catch(() => setErrorMessage("Failed to delete device"));
    }
  };

  // Functie pentru a schimba ID-ul dispozitivului in formular
  const handleDeviceIdChange = async (id: string) => {
    setDeviceData((prev) => ({ ...prev, id }));
    if (deviceAction === "update" || deviceAction === "delete") {
      try {
        const response = await axios.get(DEVICES_HOST.backend_api + endpointd1.device + id, {headers: {'Authorization': "Bearer " + location.state.token}}); //"http://localhost:8081/device/${id}"
        if (response.data.userDeviceMapping === null) {
          setDeviceData({
            id,
            address: response.data.address,
            description: response.data.description,
            maxEnergy: response.data.maximum_hourly_energy_consumption,
            userId: "",
          });
        } else {
          setDeviceData({
            id,
            address: response.data.address,
            description: response.data.description,
            maxEnergy: response.data.maximum_hourly_energy_consumption,
            userId: response.data.userDeviceMapping.userId,
          });
        }
      } catch {
        setErrorMessage("Failed to fetch device details");
      }
    }
  };

  const redirectToChatPage = () => {
    navigate("/chatpage", {
      state: {
        email: location.state.email,
        token: location.state.token,
        userId: location.state.userId,
        userRole: userRole
      }
    });
  };

  return (
    <div style={parentDivStyle}>
      {/* Buton pentru a reveni la pagina de autentificare */}
      <div style={cbButtonContainerStyle}>
        <Button style={backButtonStyle} onClick={handleBack} variant="contained">
          Back
        </Button>

        {/* Button for Redirecting to the ChatPage */}
                <Button
                  style={backButtonStyle}
                  onClick={redirectToChatPage}
                  variant="contained"
                >
                  Chat
                </Button>
      </div>


      {/* Mesaj de bun venit pentru admin */}
      <h1 style={titleStyle}>Welcome to Admin Page, {adminName}!</h1>
  
      {/* Butoane principale pentru operatiuni cu utilizatori si dispozitive */}
      <div style={buttonContainerStyle}>
        <Button variant="contained" onClick={handleOperationsWithDevices} style={{ marginRight: "20px" }}>
          Operations with Devices
        </Button>
        <Button variant="contained" onClick={handleOperationsWithUsers}>
          Operations with Users
        </Button>
      </div>
  
      {/* Sectiunea pentru operatiuni cu utilizatori */}
      {showUserButtons && (
        <div style={buttonContainerStyle}>
          <Button variant="outlined" onClick={() => handleUserAction("add")} style={{ margin: "10px" }}>
            Add User
          </Button>
          <Button variant="outlined" onClick={() => handleUserAction("update")} style={{ margin: "10px" }}>
            Update User
          </Button>
          <Button variant="outlined" onClick={() => handleUserAction("delete")} style={{ margin: "10px" }}>
            Delete User
          </Button>
          <Button variant="outlined" onClick={() => handleUserAction("seeAll")} style={{ margin: "10px" }}>
            See all Users
          </Button>
        </div>
      )}
  
      {/* Sectiunea pentru operatiuni cu dispozitive */}
      {showDeviceButtons && (
        <div style={buttonContainerStyle}>
          <Button variant="outlined" onClick={() => handleDeviceAction("add")} style={{ margin: "10px" }}>
            Add Device
          </Button>
          <Button variant="outlined" onClick={() => handleDeviceAction("update")} style={{ margin: "10px" }}>
            Update Device
          </Button>
          <Button variant="outlined" onClick={() => handleDeviceAction("delete")} style={{ margin: "10px" }}>
            Delete Device
          </Button>
          <Button variant="outlined" onClick={() => handleDeviceAction("seeAll")} style={{ margin: "10px" }}>
            See all Devices
          </Button>
        </div>
      )}  

      {(userAction === "add" || userAction === "update" || userAction === "delete") && (
        <div style={formContainerStyle}>
          {/* Formular pentru operatiuni cu utilizatori */}
          {(userAction === "update" || userAction === "delete") && (
            // Selectare ID utilizator pentru actiunile de update sau delete
            <FormControl fullWidth margin="normal" variant="outlined">
              <InputLabel id="idUser-label">Select User ID</InputLabel>
              <Select
                labelId="idUser-label"
                value={formData.id}
                onChange={(e) => handleFormChange("id", e.target.value as string)}
                label="User ID"
              >
                {userIdOptions.map((user) => (
                  <MenuItem key={user.id_u} value={user.id_u}>
                    {user.id_u}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          )}
          {/* Campuri pentru datele utilizatorului */}
          <TextField
            label="Name"
            value={formData.name}
            onChange={(e) => handleFormChange("name", e.target.value)}
            disabled={userAction === "delete"} // Camp dezactivat pentru delete
            fullWidth
            margin="normal"
          />
          <FormControl fullWidth margin="normal" variant="outlined">
            <InputLabel id="role-label">Role</InputLabel>
            <Select
              labelId="role-label"
              value={formData.role}
              onChange={(e) => handleFormChange("role", e.target.value as string)}
              label="Role"
              disabled={userAction === "delete"} // Camp dezactivat pentru delete
            >
              <MenuItem value="client">Client</MenuItem>
              <MenuItem value="admin">Admin</MenuItem>
            </Select>
          </FormControl>
          <TextField
            label="Email"
            value={formData.email}
            onChange={(e) => handleFormChange("email", e.target.value)}
            disabled={userAction === "delete"} // Camp dezactivat pentru delete
            fullWidth
            margin="normal"
          />
          <TextField
            label="Password"
            value={formData.password}
            onChange={(e) => handleFormChange("password", e.target.value)}
            disabled={userAction === "delete"} // Camp dezactivat pentru delete
            fullWidth
            margin="normal"
          />
          <Button
            onClick={handleUserSubmit}
            variant="contained"
            style={{ marginTop: "20px" }}
          >
            {userAction === "add"
              ? "CREATE User"
              : userAction === "update"
              ? "UPDATE User"
              : "DELETE User"}
          </Button>
        </div>
      )}


      {(deviceAction === "add" || deviceAction === "update" || deviceAction === "delete") && (
        <div style={formContainerStyle}>
          {/* Form fields for device actions */}
          {(deviceAction === "update" || deviceAction === "delete") && (
            <FormControl fullWidth margin="normal" variant="outlined">
              <InputLabel id="idDevice-label">Select Device ID</InputLabel>
              <Select
                labelId="idDevice-label"
                value={deviceData.id}
          
                onChange={(e) => handleDeviceIdChange(e.target.value as string)}

                label="Select Device ID"
              >
                {deviceIdOptions.map((device) => (
                  <MenuItem key={device.id_d} value={device.id_d}>{device.id_d}</MenuItem>
                ))}
              </Select>
            </FormControl>
          )}
          
          <TextField 
            label="Address" 
            value={deviceData.address} 
            onChange={(e) => handleDeviceChange("address", e.target.value)} 
            disabled={deviceAction === "delete"} 
            fullWidth 
            margin="normal" 
          />
          <TextField 
            label="Description" 
            value={deviceData.description} 
            onChange={(e) => handleDeviceChange("description", e.target.value)} 
            disabled={deviceAction === "delete"} 
            fullWidth 
            margin="normal" 
          />
          <TextField 
            label="Max Hourly Energy Consumption" 
            value={deviceData.maxEnergy} 
            onChange={(e) => handleDeviceChange("maxEnergy", e.target.value)} 
            disabled={deviceAction === "delete"} 
            fullWidth 
            margin="normal" 
          />

          <FormControl fullWidth margin="normal" variant="outlined">
              <InputLabel id="idUser-label">User ID</InputLabel>
              <Select
                labelId="idUser-label"
                value={deviceData.userId} 

                onChange={(e) => handleDeviceChange("userId", (e.target.value as string))}

                disabled={deviceAction === "delete"}
                label="User ID"
              >
                <MenuItem value={""}>No user</MenuItem>
                {userIdOptions.map((user) => (
                <MenuItem key={user.id_u} value={user.id_u}>{user.id_u}</MenuItem>
              ))}
              </Select>
            </FormControl>
          
          <Button 
            onClick={handleDeviceSubmit} 
            variant="contained" 
            style={{ marginTop: "20px" }}
          >
            {deviceAction === "add" ? "CREATE Device" : deviceAction === "update" ? "UPDATE Device" : "DELETE Device"}
          </Button>
        </div>
      )}

      {userAction === "seeAll" && (
          <div style={{ ...formContainerStyle, overflowY: "auto",  maxHeight: "400px" }}>
            <TableContainer component={Paper}>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>ID</TableCell>
                    <TableCell>Name</TableCell>
                    <TableCell>Role</TableCell>
                    <TableCell>Email</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {usersList.map((user) => (
                    <TableRow key={user.id_u}>
                      <TableCell>{user.id_u}</TableCell>
                      <TableCell>{user.name}</TableCell>
                      <TableCell>{user.role}</TableCell>
                      <TableCell>{user.email}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </div>
        )}

        {deviceAction === "seeAll" && (
          <div style={{ ...formContainerStyle, overflowY: "auto", maxHeight: "400px" }}>
            <TableContainer component={Paper}>
              <Table stickyHeader>
                <TableHead>
                  <TableRow>
                    <TableCell>ID</TableCell>
                    <TableCell>Address</TableCell>
                    <TableCell>Description</TableCell>
                    <TableCell>Max Hourly Energy Consumption</TableCell>
                    <TableCell>User ID</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {devicesList.map((device) => (
                    <TableRow key={device.id}>
                      <TableCell>{device.id_d}</TableCell>
                      <TableCell>{device.address}</TableCell>
                      <TableCell>{device.description}</TableCell>
                      <TableCell>{device.maximum_hourly_energy_consumption}</TableCell>
                      <TableCell>{device.userDeviceMapping === null? "No user" : device.userDeviceMapping.userId}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </div>
        )}

            {errorMessage && <p style={errorMessageStyle}>{errorMessage}</p>}
          </div>
        );
};

export default AdminPage;
