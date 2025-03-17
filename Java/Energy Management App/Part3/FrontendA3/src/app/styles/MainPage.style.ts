import React from "react";

export const mainDivStyle: React.CSSProperties = {
    backgroundImage: "url(/MainPagePhoto.jpg)", 
    backgroundSize: "cover",
    backgroundPosition: "center",
    display: "flex", 
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "space-between", 
    height: "100vh",
    paddingTop: "50px", 
    paddingBottom: "50px" 
};

export const parentDivStyle: React.CSSProperties = {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    gap: "30px",
    marginTop: "5vh"
};

export const titleStyle: React.CSSProperties = {
    fontSize: "3rem", 
    color: "#ffffff",
    textAlign: "center",
    fontWeight: "bold", 
    marginBottom: "18vh" 
};

export const buttonStyle: React.CSSProperties = {
    fontSize: "2.0rem", 
    padding: "20px 60px", 
    backgroundColor: "gray",
    color: "white",
    borderRadius: "30px", 
    border: "none", 
    boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.3)", 
    transition: "transform 0.2s ease-in-out", 
    cursor: "pointer"
};

export const buttonHoverStyle: React.CSSProperties = {
    transform: "scale(1.05)", 
    backgroundColor: "#4a4a4a" 
};
