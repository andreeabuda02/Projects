import React, { useState, useEffect } from "react";
import { useNavigate, useLocation  } from "react-router-dom";
import { Button, TextField, MenuItem, Select, FormControl, InputLabel, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from "@material-ui/core";
import axios from "axios";
import {
  parentDivStyle,
  backButtonStyle,
  titleStyle,
  buttonContainerStyle,
  formContainerStyle,
  backButtonContainerStyle,
  errorMessageStyle,
} from "../styles/AdminPage.style";

import { USERS_HOST, DEVICES_HOST } from "./hosts";

export const AdminPage = (): JSX.Element => {
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

  const endpoint={user:'user/'};
  const endpoint1={user:'user'};
  const endpoint2={user:'user/email/'};
  const endpointd={device:'device'};
  const endpointd1={device:'device/'};

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
    userId: ""
  });

  const navigate = useNavigate();
  const location = useLocation();
  
  useEffect(() => {
    if ((location.state as { email?: string })?.email) {
      const email = (location.state as { email: string }).email;
      setUserEmail(email);

      axios.get(USERS_HOST.backend_api+endpoint2.user+email)//`http://localhost:8080/user/email/${email}`
        .then((response) => {
          setAdminName(response.data.name); // Setează numele adminului
        })
        .catch(() => {
          setErrorMessage("Unable to fetch admin details.");
        });
    } else {
      navigate("/authentication");
      setErrorMessage("Email not provided. Unable to fetch admin details.");
    }
  }, [location.state?.email]);

  useEffect(() => {
    if (errorMessage) {
      const timer = setTimeout(() => setErrorMessage(""), 2000);
      return () => clearTimeout(timer);
    }
  }, [errorMessage]);

  useEffect(() => {
    if (userAction === "update" || userAction === "delete") {
      axios.get(USERS_HOST.backend_api+endpoint1.user)//"http://localhost:8080/user"
        .then((response) => setUserIdOptions(response.data))
        .catch(() => setErrorMessage("Failed to fetch user IDs"));
    }
  }, [userAction]);

  useEffect(() => {
    axios.get(USERS_HOST.backend_api+endpoint1.user)//"http://localhost:8080/user"
        .then((response) => setUserIdOptions(response.data))
        .catch(() => setErrorMessage("Failed to fetch user IDs"));
    if (deviceAction === "update" || deviceAction === "delete") {
      axios.get(DEVICES_HOST.backend_api+endpointd.device)//"http://localhost:8081/device"
        .then((response) => setDeviceIdOptions(response.data))
        .catch(() => setErrorMessage("Failed to fetch device IDs"));
    }
  }, [deviceAction]);

  const obtainUsersList = async () => {
    try {
      const response = await axios.get(USERS_HOST.backend_api+endpoint1.user);//"http://localhost:8080/user"
      if (response.status === 200) {
        setUsersList(response.data);
      } else {
        setErrorMessage("Something went wrong when loading users!");
      }
    } catch (error) {
      setErrorMessage("Something went wrong when loading users!");
    }
  };

  const obtainDevicesList = async () => {
    try {
      const response = await axios.get(DEVICES_HOST.backend_api+endpointd.device);//"http://localhost:8081/device"
      if (response.status === 200) {
        setDevicesList(response.data);
      } else {
        setErrorMessage("Something went wrong when loading devices!");
      }
    } catch (error) {
      setErrorMessage("Something went wrong when loading devices!");
    }
  };

  const handleBack = () => {
    navigate("/authentication");
  };

  const handleOperationsWithDevices = () => {
    setShowDeviceButtons(true);
    setShowUserButtons(false);
    setUserAction(null);
    setDeviceAction(null);
  };

  const handleOperationsWithUsers = () => {
    setShowDeviceButtons(false);
    setShowUserButtons(true);
    setUserAction(null);
    setDeviceAction(null);
  };

  const handleUserAction = (action: string) => {
    setUserAction(action);
    setFormData({ id: "", name: "", role: "client", email: "", password: "" });
    if (action === "seeAll") {
      obtainUsersList(); 
    }
  };

  const handleDeviceAction = (action: string) => {
    setDeviceAction(action);
    setDeviceData({ id: "", address: "", description: "", maxEnergy: "", userId: "" });
    if (action === "seeAll") {
      obtainDevicesList(); // Apelează funcția pentru a obține dispozitivele
    }
  };

  const handleFormChange = (field: string, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }));

    if(field==="id"){
        for(var i=0; i<userIdOptions.length;i++){
            if(String(userIdOptions[i].id_u) === value){
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

  const handleDeviceChange = (field: string, value: string) => {
    setDeviceData((prev) => ({ ...prev, [field]: value }));
  };

  const handleUserSubmit = () => {
    if (userAction === "add") {
      axios.post(USERS_HOST.backend_api+endpoint1.user, formData)//"http://localhost:8080/user"
        .then(() => {
          alert("User created successfully");
          setFormData({ id: "", name: "", role: "client", email: "", password: "" });
        })
        .catch(() => setErrorMessage("Failed to create user"));
    } else if (userAction === "update") {
      axios.put(USERS_HOST.backend_api+endpoint.user+formData.id, formData)//`http://localhost:8080/user/${formData.id}`
        .then(() =>{ alert("User updated successfully");
          setFormData({ id: "", name: "", role: "client", email: "", password: "" });
        })
        .catch(() => setErrorMessage("Failed to update user"));
    } else if (userAction === "delete") {
      axios.delete(USERS_HOST.backend_api+endpoint.user+formData.id)//`http://localhost:8080/user/${formData.id}`
        .then(() =>{ alert("User deleted successfully");
          setFormData({ id: "", name: "", role: "client", email: "", password: "" });
        })
        .catch(() => setErrorMessage("Failed to delete user"));
    }
  };

  const handleDeviceSubmit = () => {
    if (deviceAction === "add") {
        let user_id="";
        for(var i=0; i<userIdOptions.length;i++){
            if(String(userIdOptions[i].id_u) === deviceData.userId){
                user_id = deviceData.userId;
            }
        }
       //"http://localhost:8081/device/addDevice"
       if(user_id === ""){
        axios.post(DEVICES_HOST.backend_api+endpointd.device, {
          address: deviceData.address,
          description: deviceData.description,
          maximum_hourly_energy_consumption: deviceData.maxEnergy,
          userDeviceMapping: null
        })
          .then(() =>{ alert("Device created successfully");
            setDeviceData({ id: "", address: "", description: "", maxEnergy: "", userId: "" });
          })
          .catch(() => setErrorMessage("Failed to create device"));
       }
       else{
        axios.post(DEVICES_HOST.backend_api+endpointd.device, {
          address: deviceData.address,
          description: deviceData.description,
          maximum_hourly_energy_consumption: deviceData.maxEnergy,
          userDeviceMapping: {
              userId: user_id,
              devicesList: []
          }
        })
          .then(() => {alert("Device created successfully");
            setDeviceData({ id: "", address: "", description: "", maxEnergy: "", userId: "" });
          })
          .catch(() => setErrorMessage("Failed to create device"));
       }
      
    } else if (deviceAction === "update") {
        let user_id="";
        for(var i=0; i<userIdOptions.length;i++){
            if(String(userIdOptions[i].id_u) === deviceData.userId){
                user_id = deviceData.userId;
            }
        }
        
        //`http://localhost:8081/device/updateDevice/${deviceData.id}`
        if(user_id === ""){
          axios.put(DEVICES_HOST.backend_api+endpointd1.device+deviceData.id, {
            id_d: deviceData.id,
            address: deviceData.address,
            description: deviceData.description,
            maximum_hourly_energy_consumption: deviceData.maxEnergy,
            userDeviceMapping: null
          })
            .then(() => {alert("Device updated successfully");
              setDeviceData({ id: "", address: "", description: "", maxEnergy: "", userId: "" });
            })
            .catch(() => setErrorMessage("Failed to update device"));
        }
        else{
          let devices=[];
          for(var i=0;i<deviceIdOptions.length;i++){
            if(deviceIdOptions[i].userDeviceMapping !== null){
              if(String(deviceIdOptions[i].userDeviceMapping.userId) === deviceData.userId){
                devices = deviceIdOptions[i].userDeviceMapping.devicesList;
              }
            }
          }
          axios.put(DEVICES_HOST.backend_api+endpointd1.device+deviceData.id, {
            id_d: deviceData.id,
            address: deviceData.address,
            description: deviceData.description,
            maximum_hourly_energy_consumption: deviceData.maxEnergy,
            userDeviceMapping: {
                userId: user_id,
                devicesList: devices
            }
          })
            .then(() =>{ alert("Device updated successfully");
              setDeviceData({ id: "", address: "", description: "", maxEnergy: "", userId: "" });
            })
            .catch(() => setErrorMessage("Failed to update device"));
        }

    } else if (deviceAction === "delete") {
      axios.delete(DEVICES_HOST.backend_api+endpointd1.device+deviceData.id)//`http://localhost:8081/device/deleteDevice/${deviceData.id}`
        .then(() => {alert("Device deleted successfully");
          setDeviceData({ id: "", address: "", description: "", maxEnergy: "", userId: "" });
        })
        .catch(() => setErrorMessage("Failed to delete device"));
    }
  };

  const handleDeviceIdChange = async (id: string) => {
    setDeviceData((prev) => ({ ...prev, id }));
    if (deviceAction === "update" || deviceAction === "delete") {
      try {
        const response = await axios.get(DEVICES_HOST.backend_api+endpointd1.device+id);//`http://localhost:8081/device/${id}`
        if(response.data.userDeviceMapping === null){
          setDeviceData({
            id,
            address: response.data.address,
            description: response.data.description,
            maxEnergy: response.data.maximum_hourly_energy_consumption,
            userId: ""
          });
        }
        else{
          setDeviceData({
            id,
            address: response.data.address,
            description: response.data.description,
            maxEnergy: response.data.maximum_hourly_energy_consumption,
            userId: response.data.userDeviceMapping.userId
          });
        }
        
      } catch {
        setErrorMessage("Failed to fetch device details");
      }
    }
  };

  return (
    <div style={parentDivStyle}>
      <div style={backButtonContainerStyle}>
        <Button style={backButtonStyle} onClick={handleBack} variant="contained">
          Back
        </Button>
      </div>
      <h1 style={titleStyle}>Welcome to Admin Page, {adminName}!</h1>

      <div style={buttonContainerStyle}>
        <Button variant="contained" onClick={handleOperationsWithDevices} style={{ marginRight: "20px" }}>
          Operations with Devices
        </Button>
        <Button variant="contained" onClick={handleOperationsWithUsers}>
          Operations with Users
        </Button>
      </div>

      {showUserButtons && (
        <div style={buttonContainerStyle}>
          <Button variant="outlined" onClick={() => handleUserAction("add")} style={{ margin: "10px" }}>Add User</Button>
          <Button variant="outlined" onClick={() => handleUserAction("update")} style={{ margin: "10px" }}>Update User</Button>
          <Button variant="outlined" onClick={() => handleUserAction("delete")} style={{ margin: "10px" }}>Delete User</Button>
          <Button variant="outlined" onClick={() => handleUserAction("seeAll")} style={{ margin: "10px" }}>See all Users</Button>
        </div>
      )}

      {showDeviceButtons && (
        <div style={buttonContainerStyle}>
          <Button variant="outlined" onClick={() => handleDeviceAction("add")} style={{ margin: "10px" }}>Add Device</Button>
          <Button variant="outlined" onClick={() => handleDeviceAction("update")} style={{ margin: "10px" }}>Update Device</Button>
          <Button variant="outlined" onClick={() => handleDeviceAction("delete")} style={{ margin: "10px" }}>Delete Device</Button>
          <Button variant="outlined" onClick={() => handleDeviceAction("seeAll")} style={{ margin: "10px" }}>See all Devices</Button>
        </div>
      )}

      {(userAction === "add" || userAction === "update" || userAction === "delete") && (
        <div style={formContainerStyle}>
          {/* Form fields for user actions */}
          {(userAction === "update" || userAction === "delete") && (

            <FormControl fullWidth margin="normal" variant="outlined">
            <InputLabel id="idUser-label">Select User ID</InputLabel>
            <Select
            labelId="idUser-label"
            value={formData.id} 
            onChange={(e) => handleFormChange("id", (e.target.value as string))}
            label="User ID"
            >
          {userIdOptions.map((user) => (
          <MenuItem key={user.id_u} value={user.id_u}>{user.id_u}</MenuItem>
        ))}
            </Select>
            </FormControl>
            
          )}
          <TextField label="Name" value={formData.name} onChange={(e) => handleFormChange("name", e.target.value)} disabled={userAction === "delete"} fullWidth margin="normal" />
          <FormControl fullWidth margin="normal" variant="outlined">
            <InputLabel id="role-label">Role</InputLabel>
            <Select
                labelId="role-label"
                value={formData.role}
                onChange={(e) => handleFormChange("role", e.target.value as string)}
                label="Role" 
                disabled={userAction === "delete"}
            >
                <MenuItem value="client">Client</MenuItem>
                <MenuItem value="admin">Admin</MenuItem>
            </Select>
          </FormControl>
          <TextField label="Email" value={formData.email} onChange={(e) => handleFormChange("email", e.target.value)} disabled={userAction === "delete"} fullWidth margin="normal" />
          <TextField label="Password" value={formData.password} onChange={(e) => handleFormChange("password", e.target.value)} disabled={userAction === "delete"} fullWidth margin="normal" />
          <Button onClick={handleUserSubmit} variant="contained" style={{ marginTop: "20px" }}>{userAction === "add" ? "CREATE User" : userAction === "update" ? "UPDATE User" : "DELETE User"}</Button>
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
