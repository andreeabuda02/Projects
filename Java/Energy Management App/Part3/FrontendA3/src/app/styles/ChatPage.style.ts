export const parentDivStyle: React.CSSProperties = {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "flex-start", 
    height: "100vh",
    backgroundImage: "url('/BackgroundPhoto.jpg')",
    backgroundSize: "cover",
    backgroundPosition: "center",
    backgroundRepeat: "no-repeat",
    padding: "0 10px",
    backgroundColor: "#f5f5f5",
    position: "relative",
    overflow: "hidden"
  };

  export const buttonContainerStyle: React.CSSProperties = {
    display: "flex",
    justifyContent: "space-between",
    width: "95%",
    padding: "20px",
    position: "absolute",
    top: "20px", 
    right: "60px",
    left: "20px"
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

export const closeButtonStyle: React.CSSProperties = {
    position: "absolute",
    top: "10px",      
    right: "10px",    
    alignItems: "right", 
    padding: "6px 12px",
    fontSize: "14px",
    backgroundColor: "#4a90e2",
    color: "#fff",
    borderRadius: "8px",
    border: "none",
    cursor: "pointer",
};

export const chatModalStyle: React.CSSProperties = {
    width: '500px',
    backgroundColor: '#d6eaf8',
    borderRadius: '15px',
    boxShadow: '5px 5px 20px rgba(0,0,0,0.5)',
    padding: '25px',
    display: 'flex',
    flexDirection: 'column',
    gap: '20px',
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
  };
  

export const chatContainerStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection: 'column',
    gap: '15px',
};

export const chatLogStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection: 'column-reverse',
    justifyContent: 'flex-start', 
    maxHeight: '400px',
    overflowY: 'auto',
    padding: '15px',
    border: '1px solid #ddd',
    borderRadius: '10px',
};

export const messageItemStyle: React.CSSProperties = {
    marginBottom: '12px',
    padding: '12px',
    borderRadius: '10px',
    maxWidth: '75%',
};

export const messageRightStyle: React.CSSProperties = {
    backgroundColor: '#d1f7c4',
    alignSelf: 'flex-end',
};

export const messageLeftStyle: React.CSSProperties = {
    backgroundColor: '#e1f5fe',
    alignSelf: 'flex-start',
};

export const messageTextStyle: React.CSSProperties = {
    margin: 0,
    padding: '8px',
    fontSize: '16px',
};

export const messageStatusStyle: React.CSSProperties = {
    fontSize: '14px',
    marginLeft: '5px',
    color: '#666',
};

export const typingIndicatorStyle: React.CSSProperties = {
    fontStyle: 'italic',
    color: '#888',
    marginTop: '5px',
    fontSize: '16px',
};

export const messageInputSectionStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    gap: '15px',
    marginTop: '15px',
};

export const messageInputStyle: React.CSSProperties = {
    flex: 1,
    padding: '12px',
    borderRadius: '10px',
    border: '1px solid #ccc',
    fontSize: '16px',
};

export const sendButtonStyle: React.CSSProperties = {
    padding: '12px 20px',
    backgroundColor: '#4caf50',
    border: 'none',
    borderRadius: '8px',
    color: 'white',
    cursor: 'pointer',
    fontSize: '16px',
};

export const searchSectionStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: "center",
    alignItems: 'center',
    gap: '15px',
    marginBottom: '20px',
};

export const searchButtonStyle: React.CSSProperties = {
    padding: '12px 20px',
    backgroundColor: '#2196f3',
    color: 'white',
    border: 'none',
    borderRadius: '8px',
    cursor: 'pointer',
    fontSize: '16px',
};

export const contactListStyle: React.CSSProperties = {
    maxHeight: '250px',
    overflowY: 'auto',
    border: '1px solid #ddd',
    borderRadius: '10px',
    padding: '15px',
    backgroundColor: '#f5f5f5',
};

export const contactItemStyle: React.CSSProperties = {
    padding: '12px',
    borderBottom: '1px solid #ccc',
    cursor: 'pointer',
    fontSize: '16px',
    transition: 'background-color 0.3s ease, transform 0.2s ease',
    borderRadius: '8px',
    backgroundColor: '#fff',
    marginBottom: '10px',
    boxShadow: '0 2px 5px rgba(0,0,0,0.1)',
    transform: 'scale(1)',
  };
  
  export const hoverStyle: React.CSSProperties = {
    backgroundColor: '#e3f2fd !important',
    transform: 'scale(1.02) !important',
  };
  

export const newMessageStyle: React.CSSProperties = {
    fontWeight: 'bold',
    color: '#f44336',
    fontSize: '18px',
    textShadow: '0px 0px 5px rgba(244,67,54,0.5)',
};

