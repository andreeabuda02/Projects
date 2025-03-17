import React, { useEffect, useState } from "react";
import { bodyMeniuPrincipal, buttonAutentificare, buttonVizitare, containerMeniuPrincipal, selectLimbaMeniuPrincipal, titluMeniuPrincipal } from "./MeniuPrincipal.style";
import { Button } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";

export const MeniuPrincipal = (): JSX.Element => {

    const [t, i18n] = useTranslation("global");
    const [limba, setLimba] = useState("romana");

    const changeLanguageHandler = (languageValue: string) => {
        i18n.changeLanguage(languageValue);
        setLimba(languageValue);
    }

    useEffect(() => {
        if (location.state !== null) {
            console.log(location.state.lb.toString());
            i18n.changeLanguage(location.state.lb.toString());
            setLimba(location.state.lb.toString());
        }
        else {
            i18n.changeLanguage(limba);
        }
    }, []);

    const navigate = useNavigate();
    const location = useLocation();

    const goToAutentificare = (event: React.MouseEvent<HTMLButtonElement>): void => {
        navigate("/Autentificare", { state: { lb: limba } });
    };

    const goToClient = (event: React.MouseEvent<HTMLButtonElement>): void => {
        navigate("/Client", { state: { lb: limba, p: 0} });
    };

    return (
        <div style={bodyMeniuPrincipal}>
            <select value={limba} style={selectLimbaMeniuPrincipal} onChange={e => changeLanguageHandler(e.target.value.toString())}>
                <option value="romana">Română</option>
                <option value="spaniola">Spaniolă</option>
                <option value="engleza">Engleză</option>
            </select>
            <div style={titluMeniuPrincipal}>
                {t(limba + ".Titlu")}
            </div>
            <div>
                <div style={containerMeniuPrincipal}>
                    <Button style={buttonAutentificare} onClick={goToAutentificare}>{t(limba + ".Autentificare")}</Button>
                    <Button style={buttonVizitare} onClick={goToClient}>{t(limba + ".Intrare ca vizitator")}</Button>
                </div>
            </div>

        </div>

    );
}

