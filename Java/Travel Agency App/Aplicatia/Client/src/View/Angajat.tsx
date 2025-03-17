import React, { useState, useEffect, useRef } from "react";
import { Navigate, useLocation, useNavigate } from "react-router-dom";
import {
    bodyAngajat,
    selectLimbaAngajat,
    principalTextAngajat,
    angajatContainer,
    butoanePrincipaleAngajat,
    principalViewPacheteButtonAngajat,
    principalModifyPacheteButtonAngajat,
    principalModifyClientiButtonAngajat,
    principalRealizeazaComandaButtonAngajat,
    backButtonAngajat,
    butoaneSecundareModificarePacheteAngajat,
    addPachetButtonAngajat,
    deletePachetButtonAngajat,
    editPachetButtonAngajat,
    viewPachetButtonAngajat,
    adaugaImagineButonAngajat,
    pachetActiuniButtonAngajat,
    idPachetFieldAngajat,
    pachetActiuniEditButtonAngajat,
    pacheteScrollPaneAngajat,
    clientiScrollPaneAngajat,
    saveAsButtonAngajat,
    vizualizareStatisticiButtonAngajat,
    butoaneSecundareModificareClientiAngajat,
    viewClientiButtonAngajat,
    addClientButtonAngajat,
    deleteClientButtonAngajat,
    editClientButtonAngajat,
    divbackButtonAngajat,
    divAddPachetButtonAngajat,
    divAddClientiButtonAngajat,
    idClientFieldAngajat,
    backCButtonAngajat
} from "./Angajat.style";

import { Button, Divider, TextField } from "@mui/material";
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


export const Angajat = (): JSX.Element => {
    const idDiv = ["divPrincipalModifyClienti", "divPrincipalModifyPachete"];
    const [isDisabled, setIsDisabled] = useState(false);


    function toggleVisibility(elementId: string) {
        var x = document.getElementById(elementId);
        if (x != null) {
            if (x.style.display === "none") {
                x.style.display = "block";
                for (const e of idDiv) {
                    if (e.toString() !== elementId) {
                        var y = document.getElementById(e);
                        if (y != null) {
                            y.style.display = "none";
                        }
                    }
                }
            }
            var y = document.getElementById("divBack");
            if (y != null) {
                y.style.flex = "0.6";
            }
        }
    }

    function ascundeSecundare1(id: string) {
        var x = document.getElementById("divAddPachetButtonAngajat");
        if (x != null) {
            var y = document.getElementById("comboBoxPachete");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("campuriText");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("butonAddImg");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("executare");
            if (y != null) {
                y.style.display = "none";
            }

            y = document.getElementById("tabelPachete");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("selectFile");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("salvareButonText");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("statisticiButinText");
            if (y != null) {
                y.style.display = "none";
            }
        }
    }

    function ascundeSecundare2(id: string) {
        var x = document.getElementById("divAddClientiButtonAngajat");
        if (x != null) {
            var y = document.getElementById("comboBoxClienti");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("campuriTextClienti");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("executareClienti");
            if (y != null) {
                y.style.display = "none";
            }

            y = document.getElementById("tabelClienti");
            if (y != null) {
                y.style.display = "none";
            }
        }
    }


    function vizivilitateModificarePachete(operatie: number) {
        setButon(operatie);
        var x = document.getElementById("divAddPachetButtonAngajat");
        if (x != null) {
            x.style.display = "flex";
            if (operatie === 0) {
                setIsDisabled(false);
                var y = document.getElementById("comboBoxPachete");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("campuriText");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("butonAddImg");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("executare");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("tabelPachete");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("selectFile");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("salvareButonText");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("statisticiButinText");
                if (y != null) {
                    y.style.display = "none";
                }


                y = document.getElementById("divBack");
                if (y != null) {
                    y.style.flex = "0.5";
                }
            }

            if (operatie === 1) {
                setIsDisabled(true);
                var y = document.getElementById("comboBoxPachete");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("campuriText");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("butonAddImg");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("executare");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("tabelPachete");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("selectFile");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("salvareButonText");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("statisticiButinText");
                if (y != null) {
                    y.style.display = "none";
                }


                y = document.getElementById("divBack");
                if (y != null) {
                    y.style.flex = "0.5";
                }
            }

            if (operatie === 2) {
                setIsDisabled(false);
                var y = document.getElementById("comboBoxPachete");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("campuriText");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("butonAddImg");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("executare");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("tabelPachete");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("selectFile");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("salvareButonText");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("statisticiButinText");
                if (y != null) {
                    y.style.display = "none";
                }


                y = document.getElementById("divBack");
                if (y != null) {
                    y.style.flex = "0.5";
                }
            }

            if (operatie === 3) {
                setIsDisabled(false);
                var y = document.getElementById("comboBoxPachete");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("campuriText");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("butonAddImg");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("executare");
                if (y != null) {
                    y.style.display = "none";
                }


                y = document.getElementById("tabelPachete");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("selectFile");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("salvareButonText");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("statisticiButinText");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("divBack");
                if (y != null) {
                    y.style.flex = "0.1";
                }
            }
        }
    }

    function vizivilitateModificareClienti(operatie: number) {
        setButon1(operatie);
        var x = document.getElementById("divAddClientiButtonAngajat");
        if (x != null) {
            x.style.display = "flex";
            if (operatie === 0) {
                setIsDisabled(false);
                var y = document.getElementById("comboBoxClienti");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("campuriTextClienti");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("executareClienti");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("tabelClienti");
                if (y != null) {
                    y.style.display = "none";
                }

                y = document.getElementById("divBackClienti");
                if (y != null) {
                    y.style.flex = "1";
                }
            }

            if (operatie === 1) {
                setIsDisabled(true);
                var y = document.getElementById("comboBoxClienti");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("campuriTextClienti");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("executareClienti");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("tabelClienti");
                if (y != null) {
                    y.style.display = "none";
                }

                y = document.getElementById("divBackClienti");
                if (y != null) {
                    y.style.flex = "1";
                }
            }

            if (operatie === 2) {
                setIsDisabled(false);
                var y = document.getElementById("comboBoxClienti");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("campuriTextClienti");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("executareClienti");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("tabelClienti");
                if (y != null) {
                    y.style.display = "none";
                }

                y = document.getElementById("divBackClienti");
                if (y != null) {
                    y.style.flex = "0.5";
                }
            }

            if (operatie === 3) {
                setIsDisabled(false);
                var y = document.getElementById("comboBoxClienti");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("campuriTextClienti");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("executareClienti");
                if (y != null) {
                    y.style.display = "none";
                }

                y = document.getElementById("tabelClienti");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("divBackClienti");
                if (y != null) {
                    y.style.flex = "0.5";
                }
            }
        }
    }

    const navigate = useNavigate();
    const location = useLocation();

    const [pacheteList, setPacheteList] = useState<any[]>([]);
    const [clientiList, setClientiList] = useState<any[]>([]);

    const [limba, setLimba] = useState("romana");
    const [t, i18n] = useTranslation("global");

    const changeLanguageHandler = (languageValue: string) => {
        i18n.changeLanguage(languageValue);
        setLimba(languageValue);
    }

    useEffect(() => {
        i18n.changeLanguage(location.state.lb.toString());
        setLimba(location.state.lb.toString());
    }, []);


    {/*CRUD Pachete*/ }
    const [format, setFormat] = useState(t(limba + ".Alege format"));

    const [idP, setidP] = useState(0);
    const [idC, setidC] = useState(0);

    const [nume, setNume] = useState("");
    const [prenume, setPrenume] = useState("");

    const [destinatie, setDestinatie] = useState("");
    const [perioada, setPerioada] = useState("");
    const [pret, setPret] = useState("");
    const [img, setImg] = useState<string | null>(null);
    const [buton, setButon] = useState(0);
    const [buton1, setButon1] = useState(0);

    const [base64String, setBase64String] = useState<string | null>(null);

    const onChangeDestinatie = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setDestinatie(event.target.value);
    };

    const onChangePerioada = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPerioada(event.target.value);
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
        if(event.target.value === "0"){
            setDestinatie("");
            setPerioada("");
            setPret("");
        }else{
            for (let i = 0; i < pacheteList.length; i++) {
                if (pacheteList[i].idPachet === parseInt(event.target.value)) {
                    setDestinatie(pacheteList[i].destinatie);
                    setPerioada(pacheteList[i].perioada);
                    setPret(pacheteList[i].pret);
                    setImg(pacheteList[i].imagine);
                }
            }
        }
    }

    const onChangeClientSelectat = (event: React.ChangeEvent<HTMLSelectElement>): void => {
        setidC(parseInt(event.target.value));
        if(event.target.value === "0"){
            setNume("");
            setPrenume("");
        }
        else{
            for (let i = 0; i < clientiList.length; i++) {
                if (clientiList[i].id === parseInt(event.target.value)) {
                    setNume(clientiList[i].nume);
                    setPrenume(clientiList[i].prenume);
                }
            }
        }
    }

    const onChangeFormatSelectat = (event: React.ChangeEvent<HTMLSelectElement>): void => {
        setFormat(event.target.value);
    }

    ///Fisier/
    const salveazaFisier = async () => {
        try {
            await axios.get("http://localhost:8080/Fisier/" + format);
        } catch (errorMessage) {
            alert(t(limba + ".Eroare logare"));
        }
    }

    const saveAsF = () => {
        if (format === "--Alegeti un format--") {
            alert(t(limba + ".Alegeti un format"));
        }
        else {
            salveazaFisier();
            alert(t(limba + ".Fisier salvat cu succes"));
        }
    }

    const creazaPachet = async () => {
        try {
            await axios.post("http://localhost:8080/PachetTuristic/creaza", {
                destinatie: destinatie,
                perioada: perioada,
                pret: parseFloat(pret),
                imagine: base64String
            });
            alert(t(limba + ".Pachet creat"));
            setDestinatie("");
            setPerioada("");
            setPret("");
            setBase64String(null);
            cautaPachete();
        }
        catch (errorMessage) {
            alert(t(limba + ".Eroare creare pachet"));
        }
    }

    const stergePachet = async () => {
        try {
            await axios.delete("http://localhost:8080/PachetTuristic/sterge/" + idP);
            alert(t(limba + ".Pachet sters"));
            setidP(0);
            setDestinatie("");
            setPerioada("");
            setPret("");
            setBase64String(null);

            cautaPachete();

        }
        catch (errorMessage) {
            alert(t(limba + ".Eroare stergere pachet"));
        }
    }

    const editeazaPachet = async () => {
        try {
            await axios.put("http://localhost:8080/PachetTuristic/editeaza/" + idP, {
                destinatie: destinatie,
                perioada: perioada,
                pret: parseFloat(pret),
                imagine: img
            });
            alert(t(limba + ".Pachet actualizat"));
            setidP(0);
            setDestinatie("");
            setPerioada("");
            setPret("");
            setBase64String(null);
            setImg(null);

            cautaPachete();
        }
        catch (errorMessage) {
            alert(t(limba + ".Eroare actualizare pachet"));
        }
    }


    const imagineSelectata = useRef<HTMLInputElement>(null);


    const adaugaImagine = () => {
        if (imagineSelectata.current !== null) {
            imagineSelectata.current.click();
        }

    }

    const onChangeFile = (event: React.ChangeEvent<HTMLInputElement>): void => {
        if (imagineSelectata.current !== null) {
            const file = imagineSelectata.current.files && imagineSelectata.current.files[0];
            let reader = new FileReader();
            if (file) {
                reader.readAsArrayBuffer(file);
                reader.onloadend = () => {
                    if (reader.result) {
                        const arrayBuffer = reader.result as ArrayBuffer;
                        const byteArray = new Uint8Array(arrayBuffer);
                        const base64String = btoa(String.fromCharCode.apply(null, Array.from(byteArray)));

                        setBase64String(base64String);
                    }
                };
            }
        }
    }


    const clickExecuta = (operatie: number) => {
        if (operatie === 0) {
            if (destinatie === "" || perioada === "" || pret === "") {
                alert(t(limba + ".Completati campurile libere"));
            }
            else {
                if (base64String === null) {
                    alert(t(limba + ".Adauga imagine"));
                }
                else {
                    creazaPachet();
                }
            }
        }
        else {
            if (operatie === 1) {
                if (idP === 0) {
                    alert(t(limba + ".Selectie pachet"));
                }
                else {
                    stergePachet();
                }
            }
            else {
                if (idP === 0) {
                    alert(t(limba + ".Selectie pachet"));
                }
                else {
                    if (destinatie === "" || perioada === "" || pret === "") {
                        alert(t(limba + ".Completati campurile libere"));
                    }
                    else {
                        editeazaPachet();
                    }
                }
            }
        }
    }

    {/*CRUD Clienti*/ }

    const creazaClient = async () => {
        try {
            await axios.post("http://localhost:8080/Client/creaza", {
                nume: nume,
                prenume: prenume
            });
            alert(t(limba + ".Client creat"));
            setNume("");
            setPrenume("");
            cautaClienti();
        }
        catch (errorMessage) {
            alert(t(limba + ".Eroare creare client"));
        }
    }

    const stergeClient = async () => {
        try {
            await axios.delete("http://localhost:8080/Client/sterge/" + idC);
            alert(t(limba + ".Client sters"));
            setidC(0);
            setNume("");
            setPrenume("");
            cautaClienti();
        }
        catch (errorMessage) {
            alert(t(limba + ".Eroare stergere client"));
        }
    }

    const editeazaClient = async () => {
        try {
            await axios.put("http://localhost:8080/Client/editeaza/" + idC, {
                nume: nume,
                prenume: prenume
            });
            alert(t(limba + ".Client actualizat"));
            setidC(0);
            setNume("");
            setPrenume("");
            cautaClienti();
        }
        catch (errorMessage) {
            alert(t(limba + ".Eroare actualizare client"));
        }
    }

    const clickExecuta1 = (operatie: number) => {
        if (operatie === 0) {
            if (nume === "" || prenume === "") {
                alert(t(limba + ".Completati campurile libere"));
            }
            else {
                creazaClient();
            }
        }
        else {
            if (operatie === 1) {
                if (idC === 0) {
                    alert(t(limba + ".Selectie client"));
                }
                else {
                    stergeClient();
                }
            }
            else {
                if (idC === 0) {
                    alert(t(limba + ".Selectie client"));
                }
                else {
                    if (nume === "" || prenume === "") {
                        alert(t(limba + ".Completati campurile libere"));
                    }
                    else {
                        editeazaClient();
                    }
                }
            }
        }
    }

    useEffect(() => {
        cautaPachete();
        cautaClienti();
    }, []);


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

    const handleClientPachte = () => {
        navigate('/Client', { state: { lb: limba, p: 1 } });
    }

    const handleRezervare = () => {
        navigate('/RezervarePachet', { state: { lb: limba } });
    }

    const handleBack = () => {
        navigate('/Autentificare', { state: { lb: limba } });
    };

    return (
        <div style={bodyAngajat}>
            <select style={selectLimbaAngajat} value={limba} onChange={e => changeLanguageHandler(e.target.value.toString())}>
                <option value="romana">Română</option>
                <option value="spaniola">Spaniolă</option>
                <option value="engleza">Engleză</option>
            </select>

            <div style={principalTextAngajat}>
                {t(limba + ".Text principal angajat")}
            </div>

            <div id="divBack" style={divbackButtonAngajat}>
                <Button style={backButtonAngajat} onClick={handleBack}>{t(limba + ".Go Back")}</Button>
            </div>

            <div style={angajatContainer}>
                <div style={butoanePrincipaleAngajat}>
                    <Button style={principalViewPacheteButtonAngajat} onClick={handleClientPachte}>{t(limba + ".Vizualizare pachete")}</Button>
                    <Button style={principalModifyPacheteButtonAngajat} onClick={() => { toggleVisibility("divPrincipalModifyPachete"); ascundeSecundare1("divAddClientiButtonAngajat"); ascundeSecundare2("divAddPachetButtonAngajat") }}>{t(limba + ".Modificare pachete turistice")}</Button>
                    <Button style={principalModifyClientiButtonAngajat} onClick={() => { toggleVisibility("divPrincipalModifyClienti"); ascundeSecundare2("divAddPachetButtonAngajat"); ascundeSecundare1("divAddClientiButtonAngajat") }}>{t(limba + ".Modificare clienti")}</Button>
                    <Button style={principalRealizeazaComandaButtonAngajat} onClick={handleRezervare}>{t(limba + ".Realizare comenzi")}</Button>
                </div>

                <div id="divPrincipalModifyPachete" style={butoaneSecundareModificarePacheteAngajat}>
                    <Button style={addPachetButtonAngajat} onClick={() => { vizivilitateModificarePachete(0) }}>{t(limba + ".Adaugă un pachet")}</Button>
                    <Button style={deletePachetButtonAngajat} onClick={() => { vizivilitateModificarePachete(1) }}>{t(limba + ".Șterge un pachet")}</Button>
                    <Button style={editPachetButtonAngajat} onClick={() => { vizivilitateModificarePachete(2) }}>{t(limba + ".Editează un pachet")}</Button>
                    <Button style={viewPachetButtonAngajat} onClick={() => { vizivilitateModificarePachete(3) }}>{t(limba + ".Vizualizare pachete1")}</Button>
                </div>

                <div id="divPrincipalModifyClienti" style={butoaneSecundareModificareClientiAngajat}>
                    <Button style={addClientButtonAngajat} onClick={() => { vizivilitateModificareClienti(0) }}>{t(limba + ".Adaugă un client")}</Button>
                    <Button style={deleteClientButtonAngajat} onClick={() => { vizivilitateModificareClienti(1) }}>{t(limba + ".Șterge un client")}</Button>
                    <Button style={editClientButtonAngajat} onClick={() => { vizivilitateModificareClienti(2) }}>{t(limba + ".Editează un client")}</Button>
                    <Button style={viewClientiButtonAngajat} onClick={() => { vizivilitateModificareClienti(3) }}>{t(limba + ".Vizualizare clienti")}</Button>
                </div>

                {/* CRUD Pachete */}

                <div id="divAddPachetButtonAngajat" style={divAddPachetButtonAngajat}>
                    <select id="comboBoxPachete" value={idP} style={idPachetFieldAngajat} onChange={onChangePachetSelectat}>
                        <option value="0">--ID--</option>
                        {pacheteList.map((pachet) => (<option value={pachet.idPachet}>{pachet.idPachet}</option>))}
                    </select>

                    <div id="campuriText" style={{ display: "none", flexDirection: "column", gap: "40px" }}>
                        <TextField disabled={isDisabled} value={destinatie} label={t(limba + ".Destinatie")} variant="standard" onChange={onChangeDestinatie} />
                        <TextField disabled={isDisabled} value={perioada} label={t(limba + ".Perioada")} variant="standard" onChange={onChangePerioada} />
                        <TextField disabled={isDisabled} value={pret} type="number" label={t(limba + ".Pret")} variant="standard" onChange={onChangePret} />
                    </div>

                    <input
                        style={{ display: 'none' }}
                        ref={imagineSelectata}
                        type="file"
                        onChange={onChangeFile}
                    />
                    <Button id="butonAddImg" style={adaugaImagineButonAngajat} onClick={adaugaImagine}>{t(limba + ".Adauga imagine")}</Button>

                    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", gap: "10px" }}>
                        <div id="tabelPachete" style={pacheteScrollPaneAngajat}>
                            <TableContainer component={Paper} sx={{ maxHeight: 200 }}>
                                <Table stickyHeader aria-label="sticky table">
                                    <TableHead>
                                        <TableRow>
                                            <StyledTableCell>{t(limba + ".Destinatie")}</StyledTableCell>
                                            <StyledTableCell align="right">{t(limba + ".Perioada")}</StyledTableCell>
                                            <StyledTableCell align="right">{t(limba + ".Pret")}</StyledTableCell>
                                        </TableRow>
                                    </TableHead>

                                    <TableBody>
                                        {pacheteList.map((pachet) => (
                                            <TableRow key={pachet.id}>
                                                <StyledTableCell component="th" scope="row">
                                                    {pachet.destinatie}
                                                </StyledTableCell >
                                                <StyledTableCell align="right">{pachet.perioada.toString()}</StyledTableCell >
                                                <StyledTableCell align="right">{pachet.pret}</StyledTableCell >
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </div>

                        <select id="selectFile" style={{ padding: "5px" }} onChange={onChangeFormatSelectat}>
                            <option>{t(limba + ".Alege format")}</option>
                            <option>CSV</option>
                            <option>DOC</option>
                            <option>JSON</option>
                            <option>XML</option>
                        </select>
                        <Button id="salvareButonText" style={saveAsButtonAngajat} onClick={saveAsF}>{t(limba + ".Salvare")}</Button>

                        <Button id="statisticiButinText" style={vizualizareStatisticiButtonAngajat} onClick={() => navigate('/Statistici', { state: { lb: limba } })}>{t(limba + ".Statistici")}</Button>

                        <Button id="executare" style={pachetActiuniButtonAngajat} onClick={() => clickExecuta(buton)}>{t(limba + ".Executare")}</Button>
                    </div>

                </div>

                {/* CRUD Clienti */}

                <div id="divAddClientiButtonAngajat" style={divAddClientiButtonAngajat}>
                    <select id="comboBoxClienti" style={idClientFieldAngajat} value={idC} onChange={onChangeClientSelectat}>
                        <option value="0">--ID--</option>
                        {clientiList.map((client) => (<option value={client.is}>{client.id}</option>))}
                    </select>
                    <div id="campuriTextClienti" style={{ display: "none", flexDirection: "column", gap: "40px" }}>
                        <TextField disabled={isDisabled} value={nume} label={t(limba + ".NumeClient")} variant="standard" onChange={onChangeNume} />
                        <TextField disabled={isDisabled} value={prenume} label={t(limba + ".PrenumeClient")} variant="standard" onChange={onChangePrenume} />
                    </div>

                    <div style={{ display: "flex", flexDirection: "column", gap: "20px" }}>
                        <div id="tabelClienti" style={clientiScrollPaneAngajat}>
                            <TableContainer sx={{ maxHeight: 300 }}>
                                <Table stickyHeader aria-label="sticky table">
                                    <TableHead>
                                        <TableRow>
                                            <StyledTableCell>{t(limba + ".NumeClient")}</StyledTableCell>
                                            <StyledTableCell>{t(limba + ".PrenumeClient")}</StyledTableCell>
                                        </TableRow>
                                    </TableHead>

                                    <TableBody>
                                        {clientiList.map((client) => (
                                            <TableRow key={client.id}>
                                                <StyledTableCell component="th" scope="row">
                                                    {client.nume}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {client.prenume}
                                                </StyledTableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </div>

                        <Button id="executareClienti" style={pachetActiuniButtonAngajat} onClick={() => clickExecuta1(buton1)}>{t(limba + ".Executare")}</Button>
                    </div>

                </div>


            </div>
        </div>
    );
}
