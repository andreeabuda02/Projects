import React, { useEffect, useState } from "react";
import {
    parolaFieldAutentificare,
    emailFieldAutentificare,
    parolaLabelAutentificare,
    emailLabelAutentificare,
    loginBox,
    bodyAutentificare,
    buttonLogin,
    buttonBackAutentificare,
    containerAutentificare,
    selectLimbaAutentificare,
    titluAutentificare,
    buttonContainerAutentificare
} from "./Autentificare.style"; import { Button } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { useTranslation } from "react-i18next";


export const Autentificare = (): JSX.Element => {

    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [errorMessage, setErrorMessage] = useState<string>("");

    const navigate = useNavigate();
    const location = useLocation();

    const onChangeEmail = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setEmail(event.target.value);
        setErrorMessage("");
    };

    const onChangePassword = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPassword(event.target.value);
        setErrorMessage("");
    };

    const conectare = async () => {
        try {
            const rez1 = await axios.get("http://localhost:8080/Angajat/cautaDupaEmailSiParola/" + email + "/" + password);
            if (rez1.status === 200) {
                navigate("/Angajat", { state: { lb: limba } });
            }
        }
        catch (errorMessage) {
            try {
                const rez2 = await axios.get("http://localhost:8080/Administrator/cautaDupaEmailSiParola/" + email + "/" + password);
                if (rez2.status === 200) {
                    navigate("/Administrator", { state: { lb: limba } });
                }
            }
            catch (errorMessage) {
                alert(t(limba + ".Eroare logare"));
            }
        }
    }

    const handleLogin = () => {
        if (email === "" || password === "") {
            alert(t(limba + ".Completati campurile libere!"));
        }
        else {
            conectare();
        }
    };

    const handleBack = () => {
        navigate('/', { state: { lb: limba } });
    };

    const [t, i18n] = useTranslation("global");
    const [limba, setLimba] = useState("romana");

    const changeLanguageHandler = (languageValue: string) => {
        i18n.changeLanguage(languageValue);
        setLimba(languageValue);
    }

    useEffect(() => {
        i18n.changeLanguage(location.state.lb.toString());
        setLimba(location.state.lb.toString());
    }, []);

    return (
        <div style={bodyAutentificare}>
            <select value={limba} style={selectLimbaAutentificare} onChange={e => changeLanguageHandler(e.target.value.toString())}>
                <option value="romana">Română</option>
                <option value="spaniola">Spaniolă</option>
                <option value="engleza">Engleză</option>
            </select>
            <div style={titluAutentificare}>
                {t(limba + ".Mesaj inregistrare")}
            </div>
            <div>
                <div style={containerAutentificare}> {/* Adaugă clasa "container" */}
                    <div style={loginBox}>
                        <label style={emailLabelAutentificare}>
                            {t(limba + ".Email a")}
                        </label>
                        <input
                            type="email"
                            className="emailFieldAutentificare"
                            value={email}
                            onChange={onChangeEmail}
                            style={emailFieldAutentificare}
                        />

                        <label style={parolaLabelAutentificare}>
                            {t(limba + ".Parola a")}
                        </label>
                        <input
                            type="password"
                            className="parolaFieldAutentificare"
                            value={password}
                            onChange={onChangePassword}
                            style={parolaFieldAutentificare}
                        />

                    </div >
                    <div style={buttonContainerAutentificare}>
                        <Button style={buttonLogin} onClick={handleLogin}>{t(limba + ".Autentificare1")}</Button>
                        <Button style={buttonBackAutentificare} onClick={handleBack}>{t(limba + ".Inapoi1")}</Button>
                    </div>
                </div>
            </div>

        </div>

    );
}

