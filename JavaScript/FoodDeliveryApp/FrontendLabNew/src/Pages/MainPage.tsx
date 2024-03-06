import React from "react";
import { mainDivStyle, parentDivStyle } from "./MainPage.style";
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

export const MainPage = (): JSX.Element => {
    const navigate = useNavigate();

    const goToLogin = (event: React.MouseEvent<HTMLButtonElement>): void => {
        navigate("/Login");
    };

    return (
        <div style={mainDivStyle}>
            <div style={parentDivStyle}>
                <Button onClick={goToLogin} variant="contained" size="large">
                    Explore
                </Button>
            </div>
        </div>
    );
};