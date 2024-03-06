import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { buttonsDivStyle, listDivStyle, mainDivStyle, parentDivStyle } from "./RestaurantPage.style";
import { Button } from "@mui/material";
import axios from "axios";
import { optionsDivStyle } from "./EditProfile.style";

export const RestaurantPage = (): JSX.Element => {
    const navigate = useNavigate();
    const location = useLocation();

    const client = location.state.client;
    const restaurant = location.state.restaurant;

    const [produse, setProduse] = useState<any[]>([]);
    const [produseSelectate, setProduseSelectate] = useState<any[]>([]);
    const [numeProduseSelectate, setNumeProduseSelectate] = useState<string[]>([]);

    useEffect(() => {
        cautaCuprins();
    }, []);

    const cautaCuprins = async() => {
        try {  //caut dupa id-ul restaurantului
            const cuprins = await axios.get("http://localhost:8080/CuprinsuriByRestaurant/" + restaurant.restaurantId);
            const listaProduse = [];

            if(cuprins.status === 200) {
                const dateCuprins = cuprins.data;
                for(var i=0; i < dateCuprins.length; i++) {  
                    try { //cauta produsele din cuprinsul restaurantului
                        const produs = await axios.get("http://localhost:8080/Produs/" + dateCuprins[i].produsId);

                        if(produs.status === 200) {
                            listaProduse.push(produs.data);
                        }
                    }
                    catch(error) {
                        console.log("Nu");
                    }
                }

                setProduse(listaProduse);
            }
        }
        catch(error) {
            
        }
    };

    const goBack = (event: React.MouseEvent<HTMLButtonElement>): void => {
        navigate("/Home", {state:{clientId: client.clientId, prenume: client.prenume}});
    };

    //adaug la lista existenta inca un element
    const adaugaProdusInLista = (_produs: any) => {
        setProduseSelectate([...produseSelectate, _produs]);
        setNumeProduseSelectate([...numeProduseSelectate, _produs.nume]);
    };


    const placeOrder = async () => {
        if(produseSelectate.length > 0) {
            let listaIdProduse = "";
            let pretTotalProduse = 0;

            const data = new Date();
            const dataCurenta = data.getDate() + ":" + (data.getMonth()+1) + ":" + data.getFullYear();

            const statusComanda = "Procesare";

            for(var i=0; i < produseSelectate.length; i++) { //am ales macar un produs
                listaIdProduse = listaIdProduse + ((produseSelectate[i].produsId).toString());  
                pretTotalProduse = pretTotalProduse + produseSelectate[i].pret;
                if(i < produseSelectate.length - 1) {
                    listaIdProduse = listaIdProduse + "_";
                }
            }

            try {
                await axios.post("http://localhost:8080/Comanda", {
                    tipPlata: "cash", 
                    pretTotal: pretTotalProduse.toFixed(2),
                    data: dataCurenta,
                    tarifTara: 10,
                    tarifJudet: 6,
                    tarifOras: 3,
                    tarifStrada: 1,
                    tarifNumar: 1,
                    status: statusComanda,
                    restaurantId: restaurant.restaurantId,
                    clientId: client.clientId,
                    produseId: listaIdProduse
                });

                alert("Order placed succesfully");
                
                //dupa ce am comandat se sterge cosul
                setProduseSelectate([]);
                setNumeProduseSelectate([]);
            }
            catch(error) {
                alert("Something went wrong. We could not place your order");
            }
        }
        else {
            alert("Put at least one product in your cart to be able to order");
        }
    }


    return (
        <div style={mainDivStyle}>
            <div style={buttonsDivStyle}>
                <Button onClick={goBack} variant="contained">
                    Back
                </Button>
            </div>

            <div style={optionsDivStyle}>
                <h1>{restaurant.nume}</h1>
            </div>

            <div style={optionsDivStyle}>
                <div style={parentDivStyle}>
                    <ul className="list-group">
                        {produse.map((item) => (
                            <li className="list-group-item"
                                key={item.produsId}
                                style={{margin: 20}}
                            >
                                <Button style={{backgroundColor: "red", color: "gold"}} onClick={() => adaugaProdusInLista(item)} variant="outlined">{item.nume}</Button>
                            </li>
                        ))}
                    </ul>
                </div>

                <div style={listDivStyle}>
                    <h1>Shopping cart</h1>

                    <ul className="list-group">
                        {numeProduseSelectate.map((item, index) => (
                            <li className="list-group-item"
                                key={index}
                                style={{backgroundColor: "gray", margin: 5}}
                            >
                                {item}
                            </li>
                        ))}
                    </ul>
                </div>
            </div>

            <div style={optionsDivStyle}>
                <Button onClick={placeOrder} variant="contained">
                    Place order
                </Button>
            </div>
        </div>
    );
}