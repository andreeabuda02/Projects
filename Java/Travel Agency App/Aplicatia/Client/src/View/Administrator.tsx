import React, { useState, useEffect } from "react";
import { Navigate, useLocation, useNavigate } from "react-router-dom";
import {
    bodyAdmin,
    selectLimbaAdmin,
    principalTextAdmin,
    adminContainer,
    butoanePrincipaleAdmin,
    principalViewAngajatiButtonAdmin,
    principalModifyAngajatiButtonAdmin,
    principalModifyAdminiButtonAdmin,
    principalViewUtilizatoriButtonAdmin,
    backButtonAdmin,
    butoaneSecundareModificareAngajatiAdmin,
    addAngajatButtonAdmin,
    deleteAngajatButtonAdmin,
    editAngajatButtonAdmin,
    viewAngajatiButtonAdmin,
    angajatActiuniButtonAdmin,
    idAngajatFieldAdmin,
    angajatiScrollPaneAdmin,
    administratoriScrollPaneAdmin,
    butoaneSecundareModificareAdminiAdmin,
    viewAdminiButtonAdmin,
    addAdminButtonAdmin,
    deleteAdminButtonAdmin,
    editAdminButtonAdmin,
    divbackButtonAdmin,
    divAddAngajatButtonAdmin,
    divAddAdminiButtonAdmin,
    idAdminFieldAdmin,
    divbackAdButtonAdmin,
    mesajAplicatieStyle,
    tipUtilizatoriStyle,
    utilizatoriScrollPaneStyle,
    adminInfoStyle
} from "./Administrator.style";

import { Button, Divider, TextField } from "@mui/material";
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

export const Administrator = (): JSX.Element => {

    const location = useLocation();

    const [t, i18n] = useTranslation("global");
    const [limba, setLimba] = useState("romana");

    const changeLanguageHandler = (languageValue: string) => {
        i18n.changeLanguage(languageValue);
        setLimba(languageValue);
    }

    useEffect(() => {
        i18n.changeLanguage(location.state.lb.toString());
        setLimba(location.state.lb.toString());
        primaAfisare();

    }, []);

    const idDiv = ["divPrincipalModifyAngajati", "divPrincipalModifyAdministratori"];
    const [isDisabled, setIsDisabled] = useState(false);

    const idDiv1 = ["comboBoxMesaj", "comboBoxUser", "utilizatoriTabel"];

    const handleRol = (event: React.ChangeEvent<HTMLSelectElement>): void =>{
        getCombinedList(event.target.value);
    }

    const primaAfisare = () => {
        idDiv1.forEach((id) => {
            const element = document.getElementById(id);
            if (element) {
                element.style.display = "none";
            }
        });
    };

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
        var x = document.getElementById("divAddAngajatButtonAdmin");
        if (x != null) {
            var y = document.getElementById("comboBoxAngajati");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("campuriText");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("executare");
            if (y != null) {
                y.style.display = "none";
            }

            y = document.getElementById("tabelAngajati");
            if (y != null) {
                y.style.display = "none";
            }
        }
    }

    function ascundeSecundare2(id: string) {
        var x = document.getElementById("divAddAdminiButtonAdmin");
        if (x != null) {
            var y = document.getElementById("comboBoxAdmini");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("campuriTextAdmini");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("executareAdmini");
            if (y != null) {
                y.style.display = "none";
            }

            y = document.getElementById("tabelAdmini");
            if (y != null) {
                y.style.display = "none";
            }
        }
    }

    function ascundeSecundar3(id: string) {
        var x = document.getElementById("divPrincipalVizualizeazaUtilizatori");
        if (x != null) {
            var y = document.getElementById("comboBoxMesaj");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("comboBoxUser");
            if (y != null) {
                y.style.display = "none";
            }
            y = document.getElementById("utilizatoriTabel");
            if (y != null) {
                y.style.display = "none";
            }
        }
    }

    function afisareSecundar3(id: string) {
        var x = document.getElementById("divPrincipalVizualizeazaUtilizatori");
        if (x != null) {
            var y = document.getElementById("comboBoxMesaj");
            if (y != null) {
                y.style.display = "flex";
            }
            y = document.getElementById("comboBoxUser");
            if (y != null) {
                y.style.display = "flex";
            }
            y = document.getElementById("utilizatoriTabel");
            if (y != null) {
                y.style.display = "flex";
            }
        }
    }



    function vizivilitateModificareAngajati(operatie: number) {
        setButon(operatie);
        var x = document.getElementById("divAddAngajatButtonAdmin");
        if (x != null) {
            x.style.display = "flex";
            if (operatie === 0) {
                setIsDisabled(false);
                var y = document.getElementById("comboBoxAngajati");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("campuriText");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("executare");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("tabelAngajati");
                if (y != null) {
                    y.style.display = "none";
                }

                y = document.getElementById("divBack");
                if (y != null) {
                    y.style.flex = "0.1";
                }
            }

            if (operatie === 1) {
                setIsDisabled(true);
                var y = document.getElementById("comboBoxAngajati");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("campuriText");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("executare");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("tabelAngajati");
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
                var y = document.getElementById("comboBoxAngajati");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("campuriText");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("executare");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("tabelAngajati");
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
                var y = document.getElementById("comboBoxAngajati");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("campuriText");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("executare");
                if (y != null) {
                    y.style.display = "none";
                }

                y = document.getElementById("tabelAngajati");
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

    function vizivilitateModificareAdministratori(operatie: number) {
        setButon1(operatie);
        var x = document.getElementById("divAddAdminiButtonAdmin");
        if (x != null) {
            x.style.display = "flex";
            if (operatie === 0) {
                setIsDisabled(false);
                var y = document.getElementById("comboBoxAdmini");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("campuriTextAdmini");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("executareAdmini");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("tabelAdmini");
                if (y != null) {
                    y.style.display = "none";
                }

                y = document.getElementById("divBackAdmini");
                if (y != null) {
                    y.style.flex = "1";
                }
            }

            if (operatie === 1) {
                setIsDisabled(true);
                var y = document.getElementById("comboBoxAdmini");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("campuriTextAdmini");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("executareAdmini");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("tabelAdmini");
                if (y != null) {
                    y.style.display = "none";
                }

                y = document.getElementById("divBackAdmini");
                if (y != null) {
                    y.style.flex = "1";
                }
            }

            if (operatie === 2) {
                setIsDisabled(false);
                var y = document.getElementById("comboBoxAdmini");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("campuriTextAdmini");
                if (y != null) {
                    y.style.display = "flex";
                }
                y = document.getElementById("executareAdmini");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("tabelAdmini");
                if (y != null) {
                    y.style.display = "none";
                }

                y = document.getElementById("divBackAdmini");
                if (y != null) {
                    y.style.flex = "0.5";
                }
            }

            if (operatie === 3) {
                setIsDisabled(false);
                var y = document.getElementById("comboBoxAdmini");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("campuriTextAdmini");
                if (y != null) {
                    y.style.display = "none";
                }
                y = document.getElementById("executareAdmini");
                if (y != null) {
                    y.style.display = "none";
                }

                y = document.getElementById("tabelAdmini");
                if (y != null) {
                    y.style.display = "flex";
                }

                y = document.getElementById("divBackAdmini");
                if (y != null) {
                    y.style.flex = "0.5";
                }
            }
        }
    }

    const navigate = useNavigate();

    const [angajatiList, setAngajatiList] = useState<any[]>([]);
    const [administratoriList, setAdministratoriList] = useState<any[]>([]);
    const [utilizatoriList, setUtilizatoriList] = useState<any[]>([]);

    useEffect(() => {
        cautaAngajati();
        cautaAdministratori();
    }, []);

    useEffect(() => {
        if (angajatiList.length > 0 && administratoriList.length > 0) {
            getCombinedList("-");
        }
    }, [angajatiList, administratoriList]);

    const getCombinedList = (rolSel : string) => {
        if(rolSel === "-"){
            console.log("1");
            setUtilizatoriList([...angajatiList, ...administratoriList]);
        }
        else{
            if(rolSel === "Angajat"){
                console.log("2");
                setUtilizatoriList([...angajatiList]);
            }
            else{
                console.log("3");
                setUtilizatoriList([...administratoriList]);
            }
        }
    };

    const cautaAngajati = async () => {
        try {
            const lista = await axios.get("http://localhost:8080/Angajat/vizualizeazaToti");
            if (lista.status === 200) {
                setAngajatiList(lista.data);
            }
        }
        catch (errorMessage) {

        }
    };

    const cautaAdministratori = async () => {
        try {
            const lista = await axios.get("http://localhost:8080/Administrator/vizualizeazaToti");
            if (lista.status === 200) {
                setAdministratoriList(lista.data);
            }
        }
        catch (errorMessage) {

        }
    };

    const [buton, setButon] = useState(0);
    const [buton1, setButon1] = useState(0);

    const [idA, setidA] = useState(0);
    const [numeA, setNumeA] = useState("");
    const [prenumeA, setPrenumeA] = useState("");
    const [emailA, setEmailA] = useState("");
    const [telefonA, setTelefonA] = useState("");
    const [parolaA, setParolaA] = useState("");
    const [postA, setPostA] = useState("");

    const [idAD, setidAD] = useState(0);
    const [numeAD, setNumeAD] = useState("");
    const [prenumeAD, setPrenumeAD] = useState("");
    const [emailAD, setEmailAD] = useState("");
    const [telefonAD, setTelefonAD] = useState("");
    const [parolaAD, setParolaAD] = useState("");

    const onChangeNumeA = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setNumeA(event.target.value);
    };

    const onChangePrenumeA = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPrenumeA(event.target.value);
    };

    const onChangeEmailA = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setEmailA(event.target.value);
    };

    const onChangeTelefonA = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setTelefonA(event.target.value);
    };

    const onChangeParolaA = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setParolaA(event.target.value);
    };

    const onChangePostA = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPostA(event.target.value);
    };

    const onChangeNumeAD = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setNumeAD(event.target.value);
    };

    const onChangePrenumeAD = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPrenumeAD(event.target.value);
    };

    const onChangeEmailAD = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setEmailAD(event.target.value);
    };

    const onChangeTelefonAD = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setTelefonAD(event.target.value);
    };

    const onChangeParolaAD = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setParolaAD(event.target.value);
    };

    const onChangeAngajatSelectat = (event: React.ChangeEvent<HTMLSelectElement>): void => {
        setidA(parseInt(event.target.value));
        for (let i = 0; i < angajatiList.length; i++) {
            if (angajatiList[i].id === parseInt(event.target.value)) {
                setNumeA(angajatiList[i].nume);
                setPrenumeA(angajatiList[i].prenume);
                setEmailA(angajatiList[i].email);
                setTelefonA(angajatiList[i].telefon);
                setParolaA(angajatiList[i].parola);
                setPostA(angajatiList[i].post);
            }
        }
    }

    const onChangeAdminSelectat = (event: React.ChangeEvent<HTMLSelectElement>): void => {
        setidAD(parseInt(event.target.value));
        for (let i = 0; i < administratoriList.length; i++) {
            if (administratoriList[i].id === parseInt(event.target.value)) {
                setNumeAD(administratoriList[i].nume);
                setPrenumeAD(administratoriList[i].prenume);
                setEmailAD(administratoriList[i].email);
                setTelefonAD(administratoriList[i].telefon);
                setParolaAD(administratoriList[i].parola);
            }
        }
    }


    {/*CRUD angajati*/ }

    const creazaAngajat = async () => {
        try {
            await axios.post("http://localhost:8080/Angajat/creaza", {
                nume: numeA,
                prenume: prenumeA,
                post: postA,
                email: emailA,
                parola: parolaA,
                telefon: telefonA
            });
            alert(t(limba + ".Angajat creat"));
            setNumeA("");
            setPrenumeA("");
            setEmailA("");
            setParolaA("");
            setTelefonA("");
            setPostA("");
            cautaAngajati();
        }
        catch (errorMessage) {
            alert(t(limba + ".Eroare creare angajat"));
        }
    }

    const stergeAngajat = async () => {
        try {
            await axios.delete("http://localhost:8080/Angajat/sterge/" + idA);
            alert(t(limba + ".Angajat sters"));
            setidA(0);
            setNumeA("");
            setPrenumeA("");
            setEmailA("");
            setParolaA("");
            setTelefonA("");
            setPostA("");
            cautaAngajati();
        }
        catch (errorMessage) {
            alert(t(limba + ".Eroare stergere angajat"));
        }
    }

    const editeazaAngajat = async () => {
        try {
            await axios.put("http://localhost:8080/Angajat/editeaza/" + idA, {
                nume: numeA,
                prenume: prenumeA,
                post: postA,
                email: emailA,
                parola: parolaA,
                telefon: telefonA
            });
            alert(t(limba + ".Angajat actualizat"));
            setidA(0);
            setNumeA("");
            setPrenumeA("");
            setEmailA("");
            setParolaA("");
            setTelefonA("");
            setPostA("");
            cautaAngajati();
        }
        catch (errorMessage) {
            alert(t(limba + ".Eroare actualizare angajat"));
        }
    }

    {/*CRUD angajati*/ }

    const creazaAdministrator = async () => {
        try {
            await axios.post("http://localhost:8080/Administrator/creaza", {
                nume: numeAD,
                prenume: prenumeAD,
                email: emailAD,
                parola: parolaAD,
                telefon: telefonAD
            });
            alert(t(limba + ".Administrator creat"));
            setNumeAD("");
            setPrenumeAD("");
            setEmailAD("");
            setParolaAD("");
            setTelefonAD("");
            cautaAdministratori();
        }
        catch (errorMessage) {
            alert(t(limba + ".Eroare creare administrator"));
        }
    }

    const stergeAdministrator = async () => {
        try {
            await axios.delete("http://localhost:8080/Administrator/sterge/" + idAD);
            alert(t(limba + ".Admini sters"));
            setidAD(0);
            setNumeAD("");
            setPrenumeAD("");
            setEmailAD("");
            setParolaAD("");
            setTelefonAD("");
            cautaAdministratori();
        }
        catch (errorMessage) {
            alert(t(limba + ".Eroare stergere admin"));
        }
    }

    const editeazaAdministrator = async () => {
        try {
            await axios.put("http://localhost:8080/Administrator/editeaza/" + idAD, {
                nume: numeAD,
                prenume: prenumeAD,
                email: emailAD,
                parola: parolaAD,
                telefon: telefonAD
            });
            alert(t(limba + ".Admin actualizat"));
            setidAD(0);
            setNumeAD("");
            setPrenumeAD("");
            setEmailAD("");
            setParolaAD("");
            setTelefonAD("");
            cautaAdministratori();
        }
        catch (errorMessage) {
            alert(t(limba + ".Eroare actualizare admin"));
        }
    }

    {/*click-uri*/ }

    const clickExecuta = (operatie: number) => {
        if (operatie === 0) {
            if (numeA === "" || prenumeA === "" || emailA === "" || parolaA === "" || telefonA === "" || postA === "") {
                alert(t(limba + ".Completeaza campuri admin angajat"));
            }
            else {
                creazaAngajat();
            }
        }
        else {
            if (operatie === 1) {
                if (idA === 0) {
                    alert(t(limba + ".Selectie angajat"));
                }
                else {
                    stergeAngajat();
                }
            }
            else {
                if (idA === 0) {
                    alert(t(limba + ".Selectie angajat2"));
                }
                else {
                    if (numeA === "" || prenumeA === "" || emailA === "" || parolaA === "" || telefonA === "" || postA === "") {
                        alert(t(limba + ".Completeaza campuri admin admin"));
                    }
                    else {
                        editeazaAngajat();
                    }
                }
            }
        }
    }


    const clickExecuta1 = (operatie: number) => {
        if (operatie === 0) {
            if (numeAD === "" || prenumeAD === "" || emailAD === "" || parolaAD === "" || telefonAD === "") {
                alert(t(limba + ".Completare campuri 2"));
            }
            else {
                creazaAdministrator();
            }
        }
        else {
            if (operatie === 1) {
                if (idAD === 0) {
                    alert(t(limba + ".Selectie admin"));
                }
                else {
                    stergeAdministrator();
                }
            }
            else {
                if (idAD === 0) {
                    alert(t(limba + ".Selectie admin"));
                }
                else {
                    if (numeAD === "" || prenumeAD === "" || emailAD === "" || parolaAD === "" || telefonAD === "") {
                        alert(t(limba + ".Completare campuri 2 a"));
                    }
                    else {
                        editeazaAdministrator();
                    }
                }
            }
        }
    }

    const handleClientPachte = () => {
        navigate('/Client', { state: { lb: limba, p: 2 } });
    }

    const handleBack = () => {
        navigate('/Autentificare', { state: { lb: limba } });
    };

    return (
        <div style={bodyAdmin}>
            <select style={selectLimbaAdmin} value={limba} onChange={e => changeLanguageHandler(e.target.value.toString())}>
                <option value="romana">Română</option>
                <option value="spaniola">Spaniolă</option>
                <option value="engleza">Engleză</option>
            </select>

            <div style={principalTextAdmin}>
                {t(limba + ".Text principal")}
            </div>
            <div id="divBackAdmini" style={divbackAdButtonAdmin}>
                <Button style={backButtonAdmin} onClick={handleBack}>{t(limba + ".ÎNAPOI")}</Button>
            </div>

            <div style={adminContainer}>
                <div style={butoanePrincipaleAdmin}>
                    <Button style={principalViewAngajatiButtonAdmin} onClick={handleClientPachte}>{t(limba + ".Vizualizare pachete turistice")}</Button>
                    <Button style={principalModifyAngajatiButtonAdmin} onClick={() => { toggleVisibility("divPrincipalModifyAngajati"); ascundeSecundare1("divAddAdministratoriButtonAngajat"); ascundeSecundare2("divAddAngajatiButtonAngajat"); ascundeSecundar3("divPrincipalVizualizeazaUtilizatori") }}>{t(limba + ".Modificare date angajați")}</Button>
                    <Button style={principalModifyAdminiButtonAdmin} onClick={() => { toggleVisibility("divPrincipalModifyAdministratori"); ascundeSecundare2("divAddAngajatiButtonAngajat"); ascundeSecundare1("divAddAdministratoriButtonAngajat"); ascundeSecundar3("divPrincipalVizualizeazaUtilizatori") }}>{t(limba + ".Modificare date admini")}</Button>
                    <Button style={principalViewUtilizatoriButtonAdmin} onClick={() => { toggleVisibility("divPrincipalVizualizeazaUtilizatori"); ascundeSecundare1("divAddAdministratoriButtonAngajat"); ascundeSecundare2("divAddAngajatiButtonAngajat"); afisareSecundar3("divPrincipalVizualizeazaUtilizatori") }}>{t(limba + ".Vizualizare utilizatori")}</Button>
                </div>

                <div id="divPrincipalModifyAngajati" style={butoaneSecundareModificareAngajatiAdmin}>
                    <Button style={addAngajatButtonAdmin} onClick={() => { vizivilitateModificareAngajati(0) }}>{t(limba + ".Adaugă un angajat")}</Button>
                    <Button style={deleteAngajatButtonAdmin} onClick={() => { vizivilitateModificareAngajati(1) }}>{t(limba + ".Șterge un angajat")}</Button>
                    <Button style={editAngajatButtonAdmin} onClick={() => { vizivilitateModificareAngajati(2) }}>{t(limba + ".Editează un angajat")}</Button>
                    <Button style={viewAngajatiButtonAdmin} onClick={() => { vizivilitateModificareAngajati(3) }}>{t(limba + ".Vizualizare angajați")}</Button>
                </div>

                <div id="divPrincipalModifyAdministratori" style={butoaneSecundareModificareAdminiAdmin}>
                    <Button style={addAdminButtonAdmin} onClick={() => { vizivilitateModificareAdministratori(0) }}>{t(limba + ".Adaugă un administrator")}</Button>
                    <Button style={deleteAdminButtonAdmin} onClick={() => { vizivilitateModificareAdministratori(1) }}>{t(limba + ".Șterge un administrator")}</Button>
                    <Button style={editAdminButtonAdmin} onClick={() => { vizivilitateModificareAdministratori(2) }}>{t(limba + ".Editează un administrator")}</Button>
                    <Button style={viewAdminiButtonAdmin} onClick={() => { vizivilitateModificareAdministratori(3) }}>{t(limba + ".Vizualizare administratori")}</Button>
                </div>

                <div id="divPrincipalVizualizeazaUtilizatori" style={{ display: "flex", flexDirection: "column", alignItems: "center", }}>
                    <div style={{ display: "flex", flexDirection: "row", alignItems: "center" }}>
                        <select id="comboBoxUser" style={tipUtilizatoriStyle} onChange={handleRol}>
                            <option value="-">-</option>
                            <option value="Angajat">Angajat</option>
                            <option value="Admin">Admin</option>
                        </select>
                    </div>

                    <div style={utilizatoriScrollPaneStyle}>
                        <div id="utilizatoriTabel" style={utilizatoriScrollPaneStyle}>
                            <TableContainer sx={{ maxHeight: 300 }}>
                                <Table stickyHeader aria-label="sticky table">
                                    <TableHead>
                                        <TableRow>
                                            <StyledTableCell>{t(limba + ".Nume1")}</StyledTableCell>
                                            <StyledTableCell>{t(limba + ".Prenume1")}</StyledTableCell>
                                            <StyledTableCell>{t(limba + ".Email1")}</StyledTableCell>
                                            <StyledTableCell>{t(limba + ".Telefon1")}</StyledTableCell>
                                            <StyledTableCell>{t(limba + ".Parola1")}</StyledTableCell>
                                            <StyledTableCell>{t(limba + ".Post1")}</StyledTableCell>
                                        </TableRow>
                                    </TableHead>

                                    <TableBody>
                                        {utilizatoriList.map((utilizator) => (
                                            <TableRow key={utilizator.id}>
                                                <StyledTableCell component="th" scope="row">
                                                    {utilizator.nume}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {utilizator.prenume}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {utilizator.email}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {utilizator.telefon}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {utilizator.parola}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {utilizator.post}
                                                </StyledTableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </div>
                    </div>
                </div>

                {/* CRUD Angajati */}

                <div id="divAddAngajatButtonAdmin" style={divAddAngajatButtonAdmin}>
                    <select id="comboBoxAngajati" value={idA} style={idAngajatFieldAdmin} onChange={onChangeAngajatSelectat}>
                        <option value="0">--ID--</option>
                        {angajatiList.map((angajat) => (<option value={angajat.id}>{angajat.id}</option>))}
                    </select>
                    <div id="campuriText" style={{ display: "none", flexDirection: "column", gap: "1px" }}>
                        <TextField disabled={isDisabled} value={numeA} label={t(limba + ".l1")} variant="standard" onChange={onChangeNumeA} />
                        <TextField disabled={isDisabled} value={prenumeA} label={t(limba + ".l2")} variant="standard" onChange={onChangePrenumeA} />
                        <TextField disabled={isDisabled} value={emailA} label={t(limba + ".l3")} variant="standard" onChange={onChangeEmailA} />
                        <TextField disabled={isDisabled} value={telefonA} label={t(limba + ".l4")} variant="standard" onChange={onChangeTelefonA} />
                        <TextField disabled={isDisabled} value={parolaA} label={t(limba + ".l5")} variant="standard" onChange={onChangeParolaA} />
                        <TextField disabled={isDisabled} value={postA} label={t(limba + ".l6")} variant="standard" onChange={onChangePostA} />
                    </div>

                    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", gap: "0.1px" }}>
                        <div id="tabelAngajati" style={angajatiScrollPaneAdmin}>
                            <TableContainer sx={{ maxHeight: 300 }}>
                                <Table stickyHeader aria-label="sticky table">
                                    <TableHead>
                                        <TableRow>
                                            <StyledTableCell>{t(limba + ".Nume2")}</StyledTableCell>
                                            <StyledTableCell>{t(limba + ".Prenume2")}</StyledTableCell>
                                            <StyledTableCell>{t(limba + ".Email2")}</StyledTableCell>
                                            <StyledTableCell>{t(limba + ".Telefon2")}</StyledTableCell>
                                            <StyledTableCell>{t(limba + ".Parola2")}</StyledTableCell>
                                            <StyledTableCell>{t(limba + ".Post2")}</StyledTableCell>
                                        </TableRow>
                                    </TableHead>

                                    <TableBody>
                                        {angajatiList.map((angajat) => (
                                            <TableRow key={angajat.id}>
                                                <StyledTableCell component="th" scope="row">
                                                    {angajat.nume}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {angajat.prenume}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {angajat.email}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {angajat.telefon}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {angajat.parola}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {angajat.post}
                                                </StyledTableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </div>

                        <Button id="executare" style={angajatActiuniButtonAdmin} onClick={() => clickExecuta(buton)}>{t(limba + ".Executare11")}</Button>
                    </div>

                </div>

                {/* CRUD Administratori */}

                <div id="divAddAdminiButtonAdmin" style={divAddAdminiButtonAdmin}>
                    <select id="comboBoxAdmini" value={idAD} style={idAdminFieldAdmin} onChange={onChangeAdminSelectat}>
                        <option value="0">--ID--</option>
                        {administratoriList.map((admin) => (<option value={admin.id}>{admin.id}</option>))}
                    </select>
                    <div id="campuriTextAdmini" style={{ display: "none", flexDirection: "column", gap: "1px" }}>
                        <TextField disabled={isDisabled} value={numeAD} label={t(limba + ".l11")} variant="standard" onChange={onChangeNumeAD} />
                        <TextField disabled={isDisabled} value={prenumeAD} label={t(limba + ".l21")} variant="standard" onChange={onChangePrenumeAD} />
                        <TextField disabled={isDisabled} value={emailAD} label={t(limba + ".l31")} variant="standard" onChange={onChangeEmailAD} />
                        <TextField disabled={isDisabled} value={telefonAD} label={t(limba + ".l41")} variant="standard" onChange={onChangeTelefonAD} />
                        <TextField disabled={isDisabled} value={parolaAD} label={t(limba + ".l51")} variant="standard" onChange={onChangeParolaAD} />
                    </div>

                    <div style={{ display: "flex", flexDirection: "column", gap: "20px" }}>
                        <div id="tabelAdmini" style={administratoriScrollPaneAdmin}>
                            <TableContainer sx={{ maxHeight: 300 }}>
                                <Table stickyHeader aria-label="sticky table">
                                    <TableHead>
                                        <TableRow>
                                            <StyledTableCell>Nume</StyledTableCell>
                                            <StyledTableCell>Prenume</StyledTableCell>
                                            <StyledTableCell>Email</StyledTableCell>
                                            <StyledTableCell>Telefon</StyledTableCell>
                                            <StyledTableCell>Parola</StyledTableCell>
                                        </TableRow>
                                    </TableHead>

                                    <TableBody>
                                        {administratoriList.map((admin) => (
                                            <TableRow key={admin.id}>
                                                <StyledTableCell component="th" scope="row">
                                                    {admin.nume}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {admin.prenume}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {admin.email}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {admin.telefon}
                                                </StyledTableCell>
                                                <StyledTableCell align="right">
                                                    {admin.parola}
                                                </StyledTableCell>

                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </div>

                        <Button id="executareAdmini" style={angajatActiuniButtonAdmin}onClick={() => clickExecuta1(buton1)}>{t(limba + ".Executare")}</Button>
                    </div>
                </div>
            </div>
        </div>
    );
}
