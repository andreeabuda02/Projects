import {
    clientContainer,
    principalTextClient,
    buttonCautareClient,
    selectLimbaClient,
    scrollPaneTabelPacheteClient,
    buttonInapoiClient,
    bodyClient,
    destinatieLabelClient,
    perioadaLabelClient,
    pretLabelClient,
    destinatieFieldClient,
    perioadaFieldClient,
    pretFieldClient,
    fielduriClient

} from "./Client.style";
import { useLocation, useNavigate } from "react-router-dom";
import { Button, Popover } from "@mui/material";
import React, { useEffect, useState } from 'react';
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


function createData(
    id: number,
    destinatie: String,
    perioada: Date,
    pret: number,
) {
    return { id, destinatie, perioada, pret };
}

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

export const Client = (): JSX.Element => {
    const navigate = useNavigate();

    const [t, i18n] = useTranslation("global");
    const [limba, setLimba] = useState("romana");

    const changeLanguageHandler = (languageValue: string) => {
        i18n.changeLanguage(languageValue);
        setLimba(languageValue);
    }

    const location = useLocation();

    const [anchorEl, setAnchorEl] = useState<HTMLElement | null>(null);

    const [destinatie, setDestinatie] = useState("");
    const [perioada, setPerioada] = useState("");
    const [pret, setPret] = useState("");

    type AnchorElsType = {
        [key: string]: HTMLElement | null;
    };
    const [anchorEls, setAnchorEls] = useState<AnchorElsType>({});

    const handlePopoverOpen = (event: React.MouseEvent<HTMLTableRowElement, MouseEvent>, id: any) => {
        setAnchorEls({ ...anchorEls, [id]: event.currentTarget });
    };

    const handlePopoverClose = (id: any) => {
        setAnchorEls({ ...anchorEls, [id]: null });
    };

    const isOpen = (id: string | number) => Boolean(anchorEls[id]);

    const [pacheteList, setPacheteList] = useState<any[]>([]);


    useEffect(() => {
        i18n.changeLanguage(location.state.lb.toString());
        setLimba(location.state.lb.toString());
        cautaPachete();
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

    const cautaPacheteCriteriu = async (destinatie: string, perioada: string, pret: number) => {
        try {
            const lista = await axios.get("http://localhost:8080/PachetTuristic/filtreazaDupaCriteriu/" + destinatie + "/" + perioada + "/" + pret);
            if (lista.status === 200) {
                setPacheteList(lista.data);
            }
        }
        catch (errorMessage) {
            alert(t(limba + ".Pachet necorespunzator criteriilor"));
        }
    };

    const handleCautaPachete = (event: React.MouseEvent<HTMLElement>) => {
        var dst, prd, pr;
        if (destinatie === "") {
            dst = "-";
        }
        else {
            dst = destinatie;
        }

        if (perioada === "") {
            prd = "1000-10-10";
        }
        else {
            prd = perioada;
        }

        if (pret === "") {
            pr = -1;
        }
        else {
            pr = parseFloat(pret);
        }

        cautaPacheteCriteriu(dst, prd, pr);
    };

    const handleBack = () => {
        if (location.state.p === 0) {
            navigate('/', { state: { lb: limba } });
        }
        else {
            if (location.state.p === 1) {
                navigate('/Angajat', { state: { lb: limba } });
            }
            else {
                navigate('/Administrator', { state: { lb: limba } });
            }
        }
    };

    const onChangeDestinatie = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setDestinatie(event.target.value);
    };

    const onChangePerioada = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPerioada(event.target.value);
    };

    const onChangePret = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPret(event.target.value);
    };

    return (
        <div style={bodyClient}>
            <select value={limba} style={selectLimbaClient} onChange={e => changeLanguageHandler(e.target.value.toString())}>
                <option value="romana">Română</option>
                <option value="spaniola">Spaniolă</option>
                <option value="engleza">Engleză</option>
            </select>
            <div style={principalTextClient}>
                {t(limba + ".Cauta un pachet")}
            </div>
            <div>
                <div style={clientContainer}>
                    <div style={fielduriClient}>
                        <label style={destinatieLabelClient}>
                            {t(limba + ".Destinatie client GUI")}
                        </label>
                        <input
                            className="destinatieFieldClient"
                            style={destinatieFieldClient}
                            onChange={onChangeDestinatie}
                        />

                        <label style={perioadaLabelClient}>
                            {t(limba + ".Perioada client GUI")}
                        </label>
                        <input
                            className="perioadaFieldClient"
                            style={perioadaFieldClient}
                            onChange={onChangePerioada}
                        />

                        <label style={pretLabelClient}>
                            {t(limba + ".Pret client GUI")}
                        </label>
                        <input
                            className="pretFieldClient"
                            style={pretFieldClient}
                            onChange={onChangePret}
                            type="number"
                        />
                        <Button style={buttonCautareClient} onClick={handleCautaPachete} >{t(limba + ".Cauta")}</Button>
                    </div>

                    <div style={scrollPaneTabelPacheteClient}>
                        <TableContainer component={Paper} sx={{ maxHeight: 440 }}>
                            <Table stickyHeader aria-label="sticky table">
                                <TableHead>
                                    <TableRow>
                                        <StyledTableCell>{t(limba + ".C1")}</StyledTableCell>
                                        <StyledTableCell align="right">{t(limba + ".C2")}</StyledTableCell>
                                        <StyledTableCell align="right">{t(limba + ".C3")}</StyledTableCell>
                                    </TableRow>
                                </TableHead>

                                <TableBody>
                                    {pacheteList.map((pachet) => (
                                        <TableRow key={pachet.id} aria-owns={isOpen(pachet.idPachet) ? 'mouse-over-popover' : undefined}
                                            aria-haspopup="true"
                                            onMouseEnter={(event) => handlePopoverOpen(event, pachet.idPachet)}
                                            onMouseLeave={() => handlePopoverClose(pachet.idPachet)}>
                                            <TableCell component="th" scope="row">
                                                {pachet.destinatie}
                                            </TableCell>
                                            <TableCell align="right">{pachet.perioada.toString()}</TableCell>
                                            <TableCell align="right">{pachet.pret}</TableCell>
                                            <Popover id="mouse-over-popover" open={isOpen(pachet.idPachet)}
                                                anchorEl={anchorEl}
                                                anchorOrigin={{
                                                    vertical: 'bottom',
                                                    horizontal: 'center',
                                                }}
                                                transformOrigin={{
                                                    vertical: 'top',
                                                    horizontal: 'center',
                                                }}
                                                onClose={() => handlePopoverClose(pachet.id)}
                                                disableRestoreFocus>
                                                <img src={'data:image/jpg;base64,' + pachet.imagine} alt="Pachet" style={{ width: '200px', height: '200px' }}></img>
                                            </Popover>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </div>
                    <Button style={buttonInapoiClient} onClick={handleBack} >{t(limba + ".InapoiC")}</Button>
                </div>
            </div>
        </div>
    );
}
