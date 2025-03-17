import React, { useState } from "react";
import { mainDivStyle, parentDivStyle, buttonStyle, titleStyle, buttonHoverStyle } from "../styles/MainPage.style";
import { Button } from "@material-ui/core";
import { useNavigate } from "react-router-dom";

export const MainPage = (): JSX.Element => {
    const navigate = useNavigate();
    const [isHovered, setIsHovered] = useState(false); // State pentru a gestiona hover-ul butonului

    // Functie pentru a naviga la pagina de autentificare
    const goToAuthentication = (): void => {
        navigate("/authentication");
    };

    // Functie pentru a obtine stilul butonului, in functie de hover
    const getButtonStyle = (): React.CSSProperties => {
        return isHovered ? { ...buttonStyle, ...buttonHoverStyle } : buttonStyle;
    };

    return (
        <div style={mainDivStyle}>
            <div style={parentDivStyle}>
                {/* Titlu principal */}
                <h1 style={titleStyle}>Welcome to the Energy Management System</h1>

                {/* Buton pentru navigare */}
                <Button
                    onClick={goToAuthentication}
                    variant="contained"
                    size="large"
                    style={getButtonStyle()}
                    onMouseEnter={() => setIsHovered(true)} // Activeaza hover-ul
                    onMouseLeave={() => setIsHovered(false)} // Dezactiveaza hover-ul
                >
                    START
                </Button>
            </div>
        </div>
    );
};
