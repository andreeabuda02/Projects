export const mainDivStyle: React.CSSProperties = {
    backgroundImage: "url(/BackgroundPhoto.jpg)",
    display: "flex",
    justifyContent: "center",     
    alignItems: "center",          
    height: "100vh",
    backgroundSize: "cover",
    backgroundPosition: "center",
  };
  
  export const parentDivStyle: React.CSSProperties = {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    width: "100%",
    maxWidth: "500px",             
    padding: "20px",
  };    
  
  export const buttonContainerStyle: React.CSSProperties = {
    display: "flex",
    justifyContent: "center",       
    marginTop: "20px",
    width: "100%",
  };
  
  export const formContainerStyle: React.CSSProperties = {
    backgroundColor: "rgba(255, 255, 255, 0.8)",
    padding: "40px",
    borderRadius: "8px",
    boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.2)",
    width: "100%",
    maxWidth: "500px",
  };
  
  export const errorMessageStyle: React.CSSProperties = {
    color: "red",
    textAlign: "center",
    marginTop: "15px",
    fontSize: "0.9rem",
  };
  
  export const registerButtonStyle: React.CSSProperties = {
    padding: "8px 16px",
    fontSize: "0.9rem",
    backgroundColor: "#4a90e2",
    color: "white",
    borderRadius: "8px",
    border: "none",
    cursor: "pointer",
    transition: "transform 0.2s ease-in-out",
    width: "45%",
  };

  export const backButtonStyle: React.CSSProperties = {
    display: "flex",
    justifyContent: "flex-start",
    paddingTop: "6px",
    paddingBottom: "6px"
  };

  export const backButtonContainerStyle: React.CSSProperties = {
    position: "absolute",
    top: "20px",
    left: "20px",
  };