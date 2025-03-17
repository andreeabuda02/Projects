// Stil pentru containerul principal, în care sunt incluse toate secțiunile paginii
export const parentDivStyle: React.CSSProperties = {
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  justifyContent: "flex-start", 
  minHeight: "100vh",
  backgroundImage: "url('/BackgroundPhoto.jpg')",
  backgroundSize: "cover",
  backgroundPosition: "center",
  padding: "20px",
  backgroundColor: "#f5f5f5",
  position: "relative",
  overflow: "hidden"
};

export const cbButtonContainerStyle: React.CSSProperties = {
  display: "flex",
  justifyContent: "space-between",
  width: "95%",
  padding: "20px",
  position: "absolute",
  top: "20px", 
  right: "80px",
  left: "20px",
};

export const backButtonStyle: React.CSSProperties = {
  padding: "6px 12px",
  fontSize: "14px",
  backgroundColor: "#4a90e2",
  color: "#fff",
  borderRadius: "8px",
  display: "flex",
  justifyContent: "flex-start",
  paddingTop: "6px",
  paddingBottom: "6px"
};

export const chatButtonStyle: React.CSSProperties = {
  padding: "6px 12px",
  fontSize: "14px",
  backgroundColor: "#4a90e2",
  color: "#fff",
  borderRadius: "8px",
};

// Stil pentru titlul principal
export const titleStyle: React.CSSProperties = {
  fontSize: "2rem",
  color: "#333",
  marginBottom: "20px",
  marginTop: "20px", // Mai mult spațiu în partea de sus
  textAlign: "center",
};

// Stil pentru containerul butoanelor din nivelul 2 și 3
export const buttonContainerStyle: React.CSSProperties = {
  display: "flex",
  justifyContent: "center",
  gap: "20px",
  marginTop: "10px",
  flexWrap: "wrap", 
};

// Stil pentru butoanele principale și secundare
export const mainButtonStyle: React.CSSProperties = {
  padding: "10px 20px",
  fontSize: "16px",
  backgroundColor: "#713baa",
  color: "#fff",
  borderRadius: "10px",
  cursor: "pointer",
  fontFamily: "Arial, sans-serif",
  fontStyle: "italic",
};

// Stil pentru formularul de acțiune (Add, Update, Delete) centrat pe pagină
export const formContainerStyle: React.CSSProperties = {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    backgroundColor: "rgba(255, 255, 255, 0.9)",
    padding: "20px", 
    borderRadius: "8px",
    boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.2)",
    maxWidth: "800px", 
    width: "100%",
    maxHeight: "500px", 
    overflowY: "auto", 
    marginTop: "20px",
  };
  

// Stil pentru mesajele de eroare
export const errorMessageStyle: React.CSSProperties = {
  color: "red",
  textAlign: "center",
  marginTop: "10px",
  fontSize: "0.9rem",
};
