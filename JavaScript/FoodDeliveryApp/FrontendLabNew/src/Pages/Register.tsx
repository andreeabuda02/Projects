import { Button, TextField } from "@mui/material";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { mainDivStyle ,parentDivStyle, buttonsDivStyle } from "./Register.styles";
import axios from 'axios';

export const Register = (): JSX.Element => {
    const navigate = useNavigate();

    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [confirmPassword, setConfirmPassword] = useState<string>("");
    const [phone, setPhone] = useState<string>("");


    const [oras, setOras] = useState<string>("");
    const [judet, setJudet] = useState<string>("");
    const [tara, setTara] = useState<string>("");
    const [strada, setStrada] = useState<string>("");
    const [numar, setNumar] = useState<string>("");


    const goBack = (event: React.MouseEvent<HTMLButtonElement>): void => {
        navigate("/Login");
    };


    const onChangeEmail = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setEmail(event.target.value);
    };
    
    const onChangePassword = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPassword(event.target.value);
    };

    const onChangeConfirmPassword = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setConfirmPassword(event.target.value);
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

    const createAccount = async () => {
        try {
            const emailExistent = await axios.get("http://localhost:8080/ClientEmail/" + email);
            if(emailExistent.status === 200) {
                alert("An account with this email already exists");
            }
        }
        catch(error) {
            try {
                const adresa = await axios.get("http://localhost:8080/AdresaByTaraAndJudetulAndOrasulAndStradaAndNumarul/" + tara + "/" + judet + "/" + oras + "/" + strada + "/" + numar);
                
                if(adresa.status === 200) {
                    try {
                        await axios.post("http://localhost:8080/Client", {
                            nume: lastName,
                            prenume: firstName,
                            telefon: phone,
                            email: email,
                            parola: password,
                            adresaId: adresa.data.adresaId
                        });
                        alert("Account created succesfully");
                    }
                   catch(error) {
                        alert("Something went wrong");
                   }
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
                
                try {
                    await axios.post("http://localhost:8080/Client", {
                        nume: lastName,
                        prenume: firstName,
                        telefon: phone,
                        email: email,
                        parola: password,
                        adresaId: adresa.data.adresaId
                    });
                    alert("Account created succesfully");
                }
               catch(error) {
                    alert("Something went wrong");
               }
            }
        }
    };

    const checkFields = () => {
        if(firstName === "" || lastName === "" || email === "" || password === "" || confirmPassword === "" || phone === "" || oras === "" || judet === "" || tara === "" || strada === "" || numar === "") {
            alert("You must complete all the fields");
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
                    createAccount();
                }
            }
        }
    };

    return(
        <div style={mainDivStyle}>
            <div style={buttonsDivStyle}>
                <Button onClick={goBack} variant="contained">
                    Back
                </Button>
            </div>
            <div style={parentDivStyle}>
                <h1>General data</h1>

                <div>
                    <TextField style={{ margin: 20 }} id="standard-basic" label="First name" variant="standard" onChange={onChangeFistName} />
                    <TextField style={{ margin: 20 }} id="standard-basic" label="Last name" variant="standard" onChange={onChangeLastName} />
                </div>
                <div>
                    <TextField style={{ margin: 20 }} id="standard-basic" label="Email" variant="standard" type="email" onChange={onChangeEmail} />
                    <TextField style={{ margin: 20 }} id="standard-basic" label="Phone" variant="standard" type="number" onChange={onChangePhone} />
                </div>
                <div>
                    <TextField style={{ margin: 20 }} id="standard-basic" label="Password" variant="standard" type="password" onChange={onChangePassword} />
                    <TextField style={{ margin: 20 }} id="standard-basic" label="Confirm password" variant="standard" type="password" onChange={onChangeConfirmPassword} />
                </div>

                <h1>Address</h1>

                <div>
                    <TextField style={{ margin: 20 }} id="standard-basic" label="Country" variant="standard" onChange={onChangeTara} />
                    <TextField style={{ margin: 20 }} id="standard-basic" label="Region" variant="standard" onChange={onChangeJudet} />
                    <TextField style={{ margin: 20 }} id="standard-basic" label="City" variant="standard" onChange={onChangeOras} />
                </div>
                <div>
                    <TextField style={{ margin: 20 }} id="standard-basic" label="Street" variant="standard" onChange={onChangeStrada} />
                    <TextField style={{ margin: 20 }} id="standard-basic" label="Number" variant="standard" type="number" onChange={onChangeNumar} />
                </div>

                <div>
                    <Button style={buttonsDivStyle} onClick={checkFields} variant="contained">
                        Create account
                    </Button>
                </div>
            </div>
        </div>
    );
};