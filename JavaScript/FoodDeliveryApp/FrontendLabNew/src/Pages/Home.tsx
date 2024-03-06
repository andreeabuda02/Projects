import React, {useState, useEffect} from "react";
import { Button} from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import { parentDivStyle, buttonsDivStyle, mainDivStyle, restaurantNameStyle} from "./Home.styles";
import axios from 'axios';

export const Home = (): JSX.Element => {
    const navigate = useNavigate();
    const location = useLocation();

    const [restaurante, setRestaurante] = useState<any[]>([]);
    const [client, setClient] = useState<any>();
    const [adresa, setAdresa] = useState<any>();

    useEffect(() => {
        cautaRestaurante();
        cautaClientSiAdresa();
    }, []);

    const cautaClientSiAdresa = async () => {
        try {
            const clientId = (location.state.clientId).toString();
            const clientCautat = await axios.get("http://localhost:8080/Client/" + clientId);

            if(clientCautat.status === 200) {
                setClient(clientCautat.data);
                try {
                    const adresaId = (clientCautat.data.adresaId);
                    const adresaCautata = await axios.get("http://localhost:8080/Adresa/" + adresaId);
                    
                    if(adresaCautata.status === 200) {
                        setAdresa(adresaCautata.data);
                    }
                }
                catch(error) {

                }
            }
        }
        catch(error) {
            
        }
    };

    const cautaRestaurante = async () => {
        try {
            const listaRestaurante = await axios.get("http://localhost:8080/Restaurante");

            if(listaRestaurante.status === 200) {
                setRestaurante(listaRestaurante.data);
            }
        }
        catch(error) {

        }
    };

    const goBack = (event: React.MouseEvent<HTMLButtonElement>): void => {
        navigate("/");
    };

    const editProfile = (event: React.MouseEvent<HTMLButtonElement>): void => {
        navigate("/Home/EditProfile", {state:{client: client, adresa: adresa}});
    };

    const goToRestaurant = (_restaurant: any) => {
        navigate("/Home/Restaurant", {state:{restaurant: _restaurant, client: client}});
    };

    return (
        <div style={mainDivStyle}>
            <div style={buttonsDivStyle}>
                <Button onClick={goBack} variant="contained">
                    Back
                </Button>

                <Button onClick={editProfile} variant="contained">
                    Edit profile
                </Button>
            </div>
            <div style={parentDivStyle}>
                <h1>Welcome {location.state.prenume}! Here are our partnerships restaurants</h1>

                <ul className="list-group">
                    {restaurante.map((item) => (<li className="list_group-item" 
                                                    key={item.restaurantId} 
                                                >
                                                    <Button onClick={() => goToRestaurant(item)} style={restaurantNameStyle} >{item.nume}
                                                    
                                                    </Button>
                                                </li>
                                                ))}
                </ul>
            </div>
        </div>
    );
};