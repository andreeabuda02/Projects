import React, { useState } from "react";
import { mainDivStyle, parentDivStyle, buttonStyle, titleStyle, buttonHoverStyle } from "../styles/MainPage.style";
import { Button } from "@material-ui/core";
import { useNavigate } from "react-router-dom";

export const MainPage = (): JSX.Element => {
    const navigate = useNavigate();
    const [isHovered, setIsHovered] = useState(false); 

    const goToAuthentication = (event: React.MouseEvent<HTMLButtonElement>): void => {
        navigate("/authentication");
    };

    return (
        <div style={mainDivStyle}>
            <div style={parentDivStyle}>
                <h1 style={titleStyle}>Welcome to the Energy Management System</h1>
                <Button
                    onClick={goToAuthentication}
                    variant="contained"
                    size="large"
                    style={isHovered ? { ...buttonStyle, ...buttonHoverStyle } : buttonStyle}
                    onMouseEnter={() => setIsHovered(true)} 
                    onMouseLeave={() => setIsHovered(false)} 
                >
                    START
                </Button>
            </div>
        </div>
    );
};
