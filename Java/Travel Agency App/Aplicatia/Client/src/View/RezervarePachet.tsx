import React, { useState, useEffect } from "react";
import { Navigate, useLocation, useNavigate } from "react-router-dom";

import {
    bodyRezervare,
    rezervareContainer,
    principalTextRezervare,
    selectLimbaRezervare,
    backButtonRezervare,
    idPachetFieldRezervare,
    idClientFieldRezervare,
    rezButtonAngajat,
    rezervariScroll,
    rezervariPerioadaScroll
} from "./RezervarePachet.style";

import { Button, Chip, Divider, TextField } from "@mui/material";
import { destinatieFieldClient } from "./Client.style";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { TableCellProps } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow, { TableRowOwnProps } from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { JSX } from "react/jsx-runtime";
import axios from 'axios';
import { isDisabled } from "@testing-library/user-event/dist/utils";
import { idPachetFieldAngajat } from "./Angajat.style";
import { displayPartsToString } from "typescript";
import { useTranslation } from "react-i18next";

const StyledTableCell = (props: JSX.IntrinsicAttributes & TableCellProps) => (
    <TableCell
        {...props}
        sx={{
            // Stilurile pentru celulele de antet
            '&.MuiTableCell-head': {
                backgroundColor: "#902FFF",
                color: "#FFFFFF",
                textAlign: "center"
            },
            // Stilurile pentru celulele din corpul tabelului
            '&.MuiTableCell-body': {
                fontSize: 14,
                textAlign: "center",
            },
        }}
    />
);

export const RezervarePachet = (): JSX.Element => {

    const navigate = useNavigate();


    const [t, i18n] = useTranslation("global");
    const [limba, setLimba] = useState("romana");

    const changeLanguageHandler = (languageValue: string) => {
        i18n.changeLanguage(languageValue);
        setLimba(languageValue);
    }

    const location = useLocation();
    useEffect(() => {
        i18n.changeLanguage(location.state.lb.toString());
        setLimba(location.state.lb.toString());
    }, []);

    const handleBack = () => {
        navigate('/Angajat', { state: { lb: limba } });
    };

    const creazaRez = async () => {
        try {
            const data = new Date();
            const luna = data.getMonth();
            var lunaC = luna.toString();
            if (luna < 10) {
                lunaC = "0" + luna;
            }
            const dataCurenta = data.getFullYear() + "-" + lunaC + "-" + data.getDate();
            await axios.post("http://localhost:8080/RezervarePachet/creaza", {
                idPachet: idP,
                idClient: idC,
                dataRezervare: dataCurenta
            });
            cautaRezervari();
            alert(t(limba + ".reazlizarecomanda"));
        } catch (errorMessage) {
            alert(t(limba + ".eoareR"));
        }
    }

    const handleRezervare = () => {
        if (idP !== 0 && idC !== 0) {
            creazaRez();
        }
        else {
            alert(t(limba + ".eoareRR"));
        }
    }

    const cautaRez = async () => {
        try {
            const lista = await axios.get("http://localhost:8080/RezervarePachet/cautaDupaPerioada/" + cp);
            if (lista.status === 200) {
                setPacheteListPerioada(lista.data);
                console.log(lista.data);
            }

        } catch (errorMessage) {
            alert(t(limba + ".eoareR"));
        }
    }

    const handleCautaRez = () => {
        if (cp !== "") {
            cautaRez();
        }

    }

    const [isDisabled, setIsDisabled] = useState(false);

    const [pacheteList, setPacheteList] = useState<any[]>([]);
    const [clientiList, setClientiList] = useState<any[]>([]);
    const [rezervariList, setRezervariList] = useState<any[]>([]);
    const [pacheteListPerioada, setPacheteListPerioada] = useState<any[]>([]);


    const [idP, setidP] = useState(0);
    const [idC, setidC] = useState(0);

    const [nume, setNume] = useState("");
    const [prenume, setPrenume] = useState("");

    const [destinatie, setDestinatie] = useState("");
    const [perioada, setPerioada] = useState("");
    const [pret, setPret] = useState("");
    const [cp, setCp] = useState("");

    const onChangeDestinatie = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setDestinatie(event.target.value);
    };

    const onChangePerioada = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPerioada(event.target.value);
    };

    const onChangePerioada1 = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setCp(event.target.value);
    };

    const onChangePret = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPret(event.target.value);
    };

    const onChangeNume = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setNume(event.target.value);
    };

    const onChangePrenume = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPrenume(event.target.value);
    };

    const onChangePachetSelectat = (event: React.ChangeEvent<HTMLSelectElement>): void => {
        setidP(parseInt(event.target.value));
        if (event.target.value === "0") {
            setDestinatie("");
            setPerioada("");
            setPret("");
        } else {
            for (let i = 0; i < pacheteList.length; i++) {
                if (pacheteList[i].idPachet === parseInt(event.target.value)) {
                    setDestinatie(pacheteList[i].destinatie);
                    setPerioada(pacheteList[i].perioada);
                    setPret(pacheteList[i].pret);
                }
            }
        }

    }


    const onChangeClientSelectat = (event: React.ChangeEvent<HTMLSelectElement>): void => {
        setidC(parseInt(event.target.value));
        if (event.target.value === "0") {
            setNume("");
            setPrenume("");
        }
        else {
            for (let i = 0; i < clientiList.length; i++) {
                if (clientiList[i].id === parseInt(event.target.value)) {
                    setNume(clientiList[i].nume);
                    setPrenume(clientiList[i].prenume);
                }
            }
        }
    }


    useEffect(() => {
        cautaPachete();
        cautaClienti();
        cautaRezervari();
    }, []);

    const cautaRezervari = async () => {
        try {
            const lista = await axios.get("http://localhost:8080/RezervarePachet/vizualizeazaToate");
            if (lista.status === 200) {
                setRezervariList(lista.data);
            }
        }
        catch (errorMessage) {

        }
    };

    const cautaPachete = async () => {
        try {
            const lista = await axios.get("http://localhost:8080/PachetTuristic/vizualizeazaToate");
            if (lista.status === 200) {
                setPacheteList(lista.data);
            }
        }
        catch (errorMessage) {

        }
    };

    const cautaClienti = async () => {
        try {
            const lista = await axios.get("http://localhost:8080/Client/vizualizeazaToti");
            if (lista.status === 200) {
                setClientiList(lista.data);
            }
        }
        catch (errorMessage) {

        }
    };

    return (
        <div style={bodyRezervare}>
            <select style={selectLimbaRezervare} value={limba} onChange={e => changeLanguageHandler(e.target.value.toString())}>
                <option value="romana">Română</option>
                <option value="spaniola">Spaniolă</option>
                <option value="engleza">Engleză</option>
            </select>

            <div style={principalTextRezervare}>
                {t(limba + ".Efectueaza rezervare")}
            </div>

            <div style={{ display: "flex", flexDirection: "row", alignItems: "center", alignContent: "center", justifyContent: "center", }}>
                <div id="divBack">
                    <Button style={backButtonRezervare} onClick={handleBack} >{t(limba + ".InapoiRez")}</Button>
                </div>

                <div id="rezervare">
                    <Button style={rezButtonAngajat} onClick={handleRezervare}>{t(limba + ".Rezerva")}</Button>
                </div>
                <div id="cautare">
                    <Button style={rezButtonAngajat} onClick={handleCautaRez}>{t(limba + ".CCC")}</Button>
                </div>
            </div>


            <div style={rezervareContainer}>
                <div style={{}}>
                    <Divider>
                        <Chip label={t(limba + ".Alege pachet")} size="small" />
                    </Divider>


                    <div id="campuriTextPachet" style={{ display: "flex", flexDirection: "row", gap: "40px" }}>
                        <select id="comboBoxPachete" value={idP} onChange={onChangePachetSelectat} style={idPachetFieldRezervare}>
                            <option value="0">--ID--</option>
                            {pacheteList.map((pachet) => (<option value={pachet.idPachet}>{pachet.idPachet}</option>))}
                        </select>
                        <TextField disabled={isDisabled} value={destinatie} label={t(limba + ".Destinatie r")} variant="standard" onChange={onChangeDestinatie} />
                        <TextField disabled={isDisabled} value={pret} label={t(limba + ".Pret r")} variant="standard" onChange={onChangePerioada} />
                        <TextField disabled={isDisabled} value={perioada} label={t(limba + ".Perioada r")} variant="standard" onChange={onChangePret} />
                    </div>
                </div>

                <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <Divider>
                        <Chip label={t(limba + ".Alege client")} size="small" />
                    </Divider>
                    <div id="campuriTextClient" style={{ display: "flex", flexDirection: "row", gap: "40px" }}>
                        <select id="comboBoxClienti" style={idClientFieldRezervare} value={idC} onChange={onChangeClientSelectat}>
                            <option value="0">--ID--</option>
                            {clientiList.map((client) => (<option value={client.is}>{client.id}</option>))}
                        </select>
                        <TextField disabled={isDisabled} value={nume} label={t(limba + ".Nume rr")} variant="standard" onChange={onChangeNume} />
                        <TextField disabled={isDisabled} value={prenume} label={t(limba + ".Prenume rr")} variant="standard" onChange={onChangePrenume} />
                    </div>
                </div>

                <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <Divider>
                        <Chip label={t(limba + ".Dați perioada pentru pachetele rezervate")} size="small" />
                    </Divider>
                    <div id="perioadaCamp" style={{ display: "flex", flexDirection: "row", gap: "40px" }}>
                        <TextField label={t(limba + ".Introduceti perioada")} value={cp} disabled={isDisabled} variant="standard" onChange={onChangePerioada1} />
                    </div>
                </div>

                <div style={{ display: "flex", flexDirection: "row", alignItems: "center", gap: "50px" }}>
                    <div id="tabelRezervari" style={rezervariScroll}>
                        <TableContainer component={Paper} sx={{ maxHeight: 200 }}>
                            <Table stickyHeader aria-label="sticky table">
                                <TableHead>
                                    <TableRow>
                                        <StyledTableCell>{t(limba + ".ID rezervare")}</StyledTableCell>
                                        <StyledTableCell align="right">{t(limba + ".idd1 pachett")}</StyledTableCell>
                                        <StyledTableCell align="right">{t(limba + ".ID Client")}</StyledTableCell>
                                        <StyledTableCell align="right">{t(limba + ".Data Rezervare")}</StyledTableCell>
                                    </TableRow>
                                </TableHead>

                                <TableBody>
                                    {rezervariList.map((rez) => (
                                        <TableRow key={rez.id}>
                                            <StyledTableCell component="th" scope="row">
                                                {rez.idRezervare}
                                            </StyledTableCell >
                                            <StyledTableCell align="right">{rez.idPachet}</StyledTableCell >
                                            <StyledTableCell align="right">{rez.idClient}</StyledTableCell >
                                            <StyledTableCell align="right">{rez.dataRezervare.toString()}</StyledTableCell >

                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </div>

                    <div id="tabelRezervariPePerioada" style={rezervariPerioadaScroll}>
                        <TableContainer component={Paper} sx={{ maxHeight: 200 }}>
                            <Table stickyHeader aria-label="sticky table">
                                <TableHead>
                                    <TableRow>
                                        <StyledTableCell >{t(limba + ".pachetIIIIDDDD")}</StyledTableCell>
                                        <StyledTableCell align="right">{t(limba + ".Destinatie pachet")}</StyledTableCell>
                                    </TableRow>
                                </TableHead>

                                <TableBody>
                                    {pacheteListPerioada.map((rezP) => (
                                        <TableRow key={rezP.id}>
                                            <StyledTableCell component="th" scope="row">
                                                {rezP.idPachet}
                                            </StyledTableCell >
                                            <StyledTableCell align="right">{rezP.destinatie}</StyledTableCell >
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>

                    </div>

                </div>

            </div>


        </div>
    );
}

