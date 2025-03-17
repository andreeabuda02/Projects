import React from "react";

export const bodyMeniuPrincipal: React.CSSProperties= {
    backgroundImage: "url(Imagini/MeniuPrincipalPoza.jpeg)", /* Imaginea de fundal */
    backgroundSize: "cover",
    backgroundPosition: "center",
    height: "100vh",
    overflow: "hidden" 
}

export const containerMeniuPrincipal: React.CSSProperties= {
    paddingTop: "50px", /* Poziționare la 130px de marginea de sus */
    display:"flex",
    flexDirection:"row",
    justifyContent: "center",
    minHeight:"100%",
    height:"100%",
    width:"100%",
}

export const titluMeniuPrincipal :React.CSSProperties={
    color: "#333", /* Culoare text */
    fontSize: "60px", /* Dimensiune font */
    marginBottom: "60px",
    textAlign: "center",
    fontWeight: "bold"
}

export const selectLimbaMeniuPrincipal  :React.CSSProperties={
    position: "absolute", /* Poziționare absolută pentru element */
    top: "20px", /* Poziționare la 10px de marginea de sus */
    left: "20px", /* Poziționare la 10px de marginea stângă */
    fontSize: "16px", /* Dimensiune font */
    padding:"10px" /* Spațierea între interiorul elementului și marginea sa */
}

export const buttonAutentificare  :React.CSSProperties={
    marginRight:"350px",
    width: "300px", /* Lățime element */
    height: "150px", /* Înălțime element */
    fontSize: "20px", /* Dimensiune font */
    backgroundColor: "#713baa", /* Culoare de fundal */
    color: "#fff", /* Culoare text */
    borderRadius: "20px", /* Rotunjire margini */
    cursor: "pointer", /* Cursor de mouse */
    fontFamily: "Arial",
    fontStyle: "italic"
}

export const buttonVizitare  :React.CSSProperties={
    margin: "10px", /* Adaugă spațiu în jurul elementului */
    width: "300px", /* Lățime element */
    height: "150px", /* Înălțime element */
    fontSize: "20px", /* Dimensiune font */
    backgroundColor: "#713baa", /* Culoare de fundal */
    color: "#fff", /* Culoare text */
    borderRadius: "20px", /* Rotunjire margini */
    cursor: "pointer",/* Cursor de mouse */
    fontFamily: "Arial",
    fontStyle: "italic"
}

