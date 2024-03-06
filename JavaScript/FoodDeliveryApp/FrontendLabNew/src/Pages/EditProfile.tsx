import React, { useEffect, useState } from "react";
import { Button, TextField} from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import { mainDivStyle, optionsDivStyle, parentDivStyle } from "./EditProfile.style";
import axios from 'axios';

export const EditProfile = (): JSX.Element => {
    const navigate = useNavigate();
    const location = useLocation();
    const [client, setClient] = useState<any>(location.state.client);
    const [adresa, setAdresa] = useState<any>(location.state.adresa);

    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [phone, setPhone] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [confirmPassword, setConfirmPassword] = useState<string>("");

    const [oras, setOras] = useState<string>("");
    const [judet, setJudet] = useState<string>("");
    const [tara, setTara] = useState<string>("");
    const [strada, setStrada] = useState<string>("");
    const [numar, setNumar] = useState<string>("");

    useEffect(() => {
        cautaClient();
        completeazaCampuri();
    }, []);

    const cautaClient = async () => {
        try {
            const clientId = (location.state.client.clientId).toString();
            const clientCautat = await axios.get("http://localhost:8080/Client/" + clientId);

            if(clientCautat.status === 200) {
                setClient(clientCautat.data);
            }

            const adresaId = (location.state.adresa.adresaId).toString();
            const adresaCautata = await axios.get("http://localhost:8080/Adresa/" + adresaId);

            if(adresaCautata.status === 200) {
                setAdresa(adresaCautata.data);
            }
        }
        catch(error) {
            
        }
    };

    const completeazaCampuri = () => {
        setFirstName(client.prenume);
        setLastName(client.nume);
        setPhone(client.telefon);
        setEmail(client.email);
        setPassword(client.parola);
        setConfirmPassword(client.parola);

        setTara(adresa.tara);
        setJudet(adresa.judetul);
        setOras(adresa.orasul);
        setStrada(adresa.strada);
        setNumar(adresa.numarul);
    };


    const goBack = (event: React.MouseEvent<HTMLButtonElement>): void => {
        navigate("/Home", {state:{clientId: client.clientId, prenume: client.prenume}});
    };

    const onChangeEmail = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setEmail(event.target.value);
    };

    const onChangePhone = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPhone(event.target.value);
    };

    const onChangeFistName = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setFirstName(event.target.value);
    };

    const onChangeLastName = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setLastName(event.target.value);
    };

    const onChangePassword = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPassword(event.target.value);
    };

    const onChangeConfirmPassword = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setConfirmPassword(event.target.value);
    };

    const updateGeneralData = async () => {
        try {
            const emailExistent = await axios.get("http://localhost:8080/ClientEmail/" + email);
            if(emailExistent.status === 200 && (emailExistent.data.clientId !== client.clientId)) {
                alert("An account with this email already exists");
            }
            else {
                if(emailExistent.status === 200 && (emailExistent.data.clientId === client.clientId)) {
                    throw new Error("Modifica date cont");
                }
            }
        }
        catch(error) {
            try {
                const clientActualizat = await axios.put("http://localhost:8080/Client/" + client.clientId, {
                    clientId: client.clientId,
                    nume: lastName,
                    prenume: firstName,
                    telefon: phone,
                    email: email,
                    parola: client.parola,
                    adresaId: client.adresaId
                });
                alert("Genral data updated succesfully");
                setClient(clientActualizat.data);
            }
            catch(error) {
                console.log("Nu");
            }
        }
    };

    const checkFieldGeneralData = (event: React.MouseEvent<HTMLButtonElement>): void => {
        if(firstName === "" || lastName === "" || email === "" || phone === "") {
            alert("Fill all fields to update your general data");
        }
        else {
            updateGeneralData();
        }
    };


    const onChangeTara = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setTara(event.target.value);
    };

    const onChangeJudet = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setJudet(event.target.value);
    };

    const onChangeOras = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setOras(event.target.value);
    };

    const onChangeStrada = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setStrada(event.target.value);
    };

    const onChangeNumar = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setNumar(event.target.value);
    };


    const updatePassword = async () => {
        try {
            const clientActualizat = await axios.put("http://localhost:8080/Client/" + client.clientId, {
                clientId: client.clientId,
                nume: client.nume,
                prenume: client.prenume,
                telefon: client.telefon,
                email: client.email,
                parola: password,
                adresaId: client.adresaId
            });
            alert("Password updated succesfully");
            setClient(clientActualizat.data);
        }
        catch(error) {

        }
    };

    const checkFieldsPassword = (event: React.MouseEvent<HTMLButtonElement>):void => {
        if(password === "" || confirmPassword === "") {
            alert("Fill all fields to update your password");
        }
        else {
            if(password !== confirmPassword) {
                alert("Passwords do not match");
            }
            else {
                if(password.length < 8) {
                    alert("Passwords must be at least 8 caracters long");
                }
                else {
                    updatePassword();
                }
            }
        }
    };


    const updateAddress = async () => {
        try {
            const adresa = await axios.get("http://localhost:8080/AdresaByTaraAndJudetulAndOrasulAndStradaAndNumarul/" + tara + "/" + judet + "/" + oras + "/" + strada + "/" + numar);

            if(adresa.status === 200) {
                const clientActualizat = await axios.put("http://localhost:8080/Client/" + client.clientId, {
                    clientId: client.clientId,
                    nume: client.nume,
                    prenume: client.prenume,
                    telefon: client.telefon,
                    email: client.email,
                    parola: client.parola,
                    adresaId: adresa.data.adresaId
                });
                alert("Address updated succesfully");
                setClient(clientActualizat.data);
            }
        }
        catch(error) {
            await axios.post("http://localhost:8080/Adresa", {
                tara: tara,
                judetul: judet,
                orasul: oras,
                strada: strada,
                numarul: numar
            });

            const adresa = await axios.get("http://localhost:8080/AdresaByTaraAndJudetulAndOrasulAndStradaAndNumarul/" + tara + "/" + judet + "/" + oras + "/" + strada + "/" + numar);
            
            const clientActualizat = await axios.put("http://localhost:8080/Client/" + client.clientId, {
                clientId: client.clientId,
                nume: client.nume,
                prenume: client.prenume,
                telefon: client.telefon,
                email: client.email,
                parola: client.parola,
                adresaId: adresa.data.adresaId
            });
            alert("Address updated succesfully");
            setClient(clientActualizat.data);
        }
    };

    const checkFieldsAddress = (event: React.MouseEvent<HTMLButtonElement>): void => {
        if(tara === "" || judet === "" || oras === "" || strada === "" || numar === "") {
            alert("Fill all fields to update your address");
        }
        else {
            updateAddress();
        }
    };


    return (
        <div style={mainDivStyle}>
            <div>
                <Button onClick={goBack} variant="contained">
                    Back
                </Button>
            </div>

            <div style={parentDivStyle}>
                <h1>Edit account</h1>
            </div>

            <div style={optionsDivStyle}>
                <div style={parentDivStyle}>
                    <h1>General data</h1>

                    <div>
                        <TextField style={{ margin: 20 }} label="First name" variant="standard" onChange={onChangeFistName} value={firstName}/>
                        <TextField style={{ margin: 20 }} label="Last name" variant="standard" onChange={onChangeLastName} value={lastName}/>
                    </div>

                    <div>
                        <TextField style={{ margin: 20 }} label="Email" variant="standard" type="email" onChange={onChangeEmail} value={email}/>
                        <TextField style={{ margin: 20 }} label="Phone" variant="standard" type="number" onChange={onChangePhone} value={phone}/>
                    </div>

                    <Button onClick={checkFieldGeneralData} style={{margin: 20}} variant="contained">
                        Update general data
                    </Button>
                </div>

                <div style={parentDivStyle}>
                    <h1>Change password</h1>

                    <div>
                        <TextField style={{ margin: 20 }} label="Password" variant="standard" type="password" onChange={onChangePassword} value={password}/>
                        <TextField style={{ margin: 20 }} label="Confirm password" variant="standard" type="password" onChange={onChangeConfirmPassword} value={confirmPassword}/>
                    </div>

                    <Button style={{margin: 20}} onClick={checkFieldsPassword} variant="contained">
                        Update password
                    </Button>
                </div>
            </div>

            <div style={optionsDivStyle}>
                

                <div style={parentDivStyle}>
                    <h1>Change address</h1>

                    <div>
                        <TextField style={{ margin: 20 }} label="Country" variant="standard" onChange={onChangeTara} value={tara}/>
                        <TextField style={{ margin: 20 }} label="Region" variant="standard" onChange={onChangeJudet} value={judet}/>
                        <TextField style={{ margin: 20 }} label="City" variant="standard" onChange={onChangeOras} value={oras}/>
                    </div>

                    <div>
                        <TextField style={{ margin: 20 }} label="Street" variant="standard" onChange={onChangeStrada} value={strada}/>
                        <TextField style={{ margin: 20 }} label="Number" variant="standard" type="number" onChange={onChangeNumar} value={numar}/>
                    </div>

                    <Button style={{margin: 20}} onClick={checkFieldsAddress} variant="contained">
                        Update address
                    </Button>
                </div>
            </div>
            
        </div>
    );
};