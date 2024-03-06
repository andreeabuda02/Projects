import { Button, TextField } from "@mui/material";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { mainDivStyle, buttonsDivStyle, parentDivStyle, optionsDivStyle, mainDivStyle2} from "./AdminPage.style";
import axios from 'axios';

export const AdminPage = (): JSX.Element => {
    const navigate = useNavigate();

    const [restaurante, setRestaurante] = useState<any[]>([]);
    const [restaurantSelectat, setRestaurantSelectat] = useState<number>(-1);

    const [produse, setProduse] = useState<any[]>([]);
    const [produsSelectat, setProdusSelectat] = useState<number>(-1);
    const [produsSelectat2, setProdusSelectat2] = useState<number>(-1);

    const [numeRestaurant, setNumeRestaurant] = useState<string>("");
    const [oraDeschidereRestaurant, setOraDeschidereRestaurant] = useState<string>("");
    const [oraInchidereRestaurant, setOraInchidereRestaurant] = useState<string>("");

    const [adrese, setAdrese] = useState<any[]>([]);
    const [adresaSelectata, setAdresaSelectata] = useState<number>(-1);
    const [adresaSelectata2, setAdresaSelectata2] = useState<number>(-1);

    const [numeProdus, setNumeProdus] = useState<string>("");
    const [pretProdus, setPretProdus] = useState<string>("");

    const [numeProdus2, setNumeProdus2] = useState<string>("");
    const [pretProdus2, setPretProdus2] = useState<string>("");

    const [oras, setOras] = useState<string>("");
    const [judet, setJudet] = useState<string>("");
    const [tara, setTara] = useState<string>("");
    const [strada, setStrada] = useState<string>("");
    const [numar, setNumar] = useState<string>("");

    const [clienti, setClienti] = useState<any[]>([]);
    const [clientSelectat, setClientSelectat] = useState<number>(-1);

    useEffect(() => {
        cautaRestaurante();
        cautaAdrese();
        cautaProduse();
        cautaClienti();
    }, []);

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

    const cautaAdrese = async () => {
        try {
            const listaAdrese = await axios.get("http://localhost:8080/Adrese");

            if(listaAdrese.status === 200) {
                setAdrese(listaAdrese.data);
            }
        }
        catch(error) {

        }
    };

    const cautaProduse = async () => {
        try {
            const listaProduse = await axios.get("http://localhost:8080/Produse");

            if(listaProduse.status === 200) {
                setProduse(listaProduse.data);
            }
        }
        catch(error) {

        }
    };

    const cautaClienti = async () => {
        try {
            const listaClienti = await axios.get("http://localhost:8080/Clients");

            if(listaClienti.status === 200) {
                setClienti(listaClienti.data);
            }
        }
        catch(error) {

        }
    };

    const goBack = (event: React.MouseEvent<HTMLButtonElement>): void => {
        navigate("/");
    };

    const deleteRestaurant = async (event: React.MouseEvent<HTMLButtonElement>) => {
        if(restaurantSelectat !== -1) {
            try {
                await axios.delete("http://localhost:8080/Restaurant/" + restaurantSelectat);
                cautaRestaurante();
                alert("Restaurant deleted succesfully");
            }
            catch(error) {
                alert("Something went wrong");
            }
        }
        else {
            alert("Select a restaurant to delete first");
        }
    };


    const onChangeRestaurantName = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setNumeRestaurant(event.target.value);
    };

    const onChangeRestaurantOpenningHour = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setOraDeschidereRestaurant(event.target.value);
    };

    const onChangeRestaurantClosingHour = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setOraInchidereRestaurant(event.target.value);
    };

    const createRestaurant = async () => {
        try {
            await axios.post("http://localhost:8080/Restaurant", {
                nume: numeRestaurant,
                oraDeschidere: oraDeschidereRestaurant,
                oraInchidere: oraInchidereRestaurant,
                adresaId: adresaSelectata
            });
            cautaRestaurante();
            alert("Restaurant created succsesfully");
        }
        catch(error) {
            alert("Something went wrong");
        }
    };

    const checkFieldsRestaurant = (event: React.MouseEvent<HTMLButtonElement>) => {
        if(numeRestaurant === "" || oraDeschidereRestaurant === "" || oraInchidereRestaurant === "" || adresaSelectata === -1) {
            alert("Fill all fields to create a new restaurant");
        }
        else {
            createRestaurant();
        }
    };


    const onChangeProductName = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setNumeProdus(event.target.value);
    };

    const onChangeProductPrice = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPretProdus(event.target.value);
    };

    const onChangeProductName2 = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setNumeProdus2(event.target.value);
    };

    const onChangeProductPrice2 = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setPretProdus2(event.target.value);
    };

    const createProduct = async () => {
        try {
            await axios.post("http://localhost:8080/Produs", {
                nume: numeProdus,
                pret: parseFloat(pretProdus.toString())
            });
            cautaProduse();
            alert("Product created succsesfully");
        }
        catch(error) {
            alert("Something went wrong");
        }
    };

    const checkFieldsProduct = (event: React.MouseEvent<HTMLButtonElement>) => {
        if(numeProdus === "" || pretProdus === "") {
            alert("Fill all fields to create a new product");
        }
        else {
            if(parseFloat(pretProdus.toString()) < 0.0) {
                alert("The price can not be negative");
            }
            else {
                createProduct();
            }
        }
    };


    const deleteProduct = async (event: React.MouseEvent<HTMLButtonElement>) => {
        if(produsSelectat !== -1) {
            try {
                await axios.delete("http://localhost:8080/Produs/" + produsSelectat);
                cautaProduse();
                alert("Product deleted succesfully");
            }
            catch(error) {
                alert("Something went wrong");
            }
        }
        else {
            alert("Select a product to delete first");
        }
    };



    const onChangeTara = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setTara(event.target.value);
    };

    const onChangeJudet = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setJudet(event.target.value);
    };

    const onChangeOras = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setOras(event.target.value);
    };

    const onChangeStrada = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setStrada(event.target.value);
    };

    const onChangeNumar = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setNumar(event.target.value);
    };

    const createAddress = async () => {
        try {
            await axios.post("http://localhost:8080/Adresa", {
                tara: tara,
                judetul: judet,
                orasul: oras,
                strada: strada,
                numarul: numar
            });
            cautaAdrese();
            alert("Address created succsesfully");
        }
        catch(error) {
            alert("Something went wrong");
        }
        
    };

    const checkFieldsAddress = () => {
        if(oras === "" || judet === "" || tara === "" || strada === "" || numar === "") {
            alert("Fill all fields to create a new address");
        }
        else {
            createAddress();
        }
    };


    const deleteAddress = async (event: React.MouseEvent<HTMLButtonElement>) => {
        if(adresaSelectata2 !== -1) {
            try {
                await axios.delete("http://localhost:8080/Adresa/" + adresaSelectata2);
                cautaAdrese();
                alert("Address deleted succesfully");
            }
            catch(error) {
                alert("Something went wrong");
            }
        }
        else {
            alert("Select an address to delete first");
        }
    };


    const deleteClient = async (event: React.MouseEvent<HTMLButtonElement>) => {
        if(clientSelectat !== -1) {
            try {
                await axios.delete("http://localhost:8080/Client/" + clientSelectat);
                cautaClienti();
                alert("Client deleted succesfully");
            }
            catch(error) {
                alert("Something went wrong");
            }
        }
        else {
            alert("Select a client to delete first");
        }
    };

    const updateProduct = async () => {
        if(produsSelectat2 === -1) {
            alert("Select a product to update first");
        }
        else {
            if(numeProdus2 === "" || pretProdus2 === "") {
                alert("Fill all fields to create a new product");
            }
            else {
                if(parseFloat(pretProdus2.toString()) < 0.0) {
                    alert("The price can not be negative");
                }
                else {
                    await axios.put("http://localhost:8080/Produs/" + produsSelectat2, {
                        produsId: produsSelectat2,
                        nume: numeProdus2,
                        pret: parseFloat(pretProdus2.toString())
                    });
                    alert("Product updated succesfully");
                    cautaProduse();
                }
            }
        }
    };

    const updateFieldsProduct = async () => {
        if(produsSelectat2 !== -1) {
            try {
                const produs = await axios.get("http://localhost:8080/Produs/" + produsSelectat2);
                if(produs.status === 200) {
                    setNumeProdus2(produs.data.nume);
                    setPretProdus2(produs.data.pret);
                }
            }
            catch(error) {
                alert("Something went wrong");
            }
        }
    };


    return (
        <div>
            <div style={mainDivStyle}>
                <div style={buttonsDivStyle}>
                    <Button onClick={goBack} variant="contained">
                        Back
                    </Button>
                </div>


                {/* sterge si creeaza restaurante */}
                <div style={optionsDivStyle}>
                    {/* creeaza restaurant */}
                    <div style={parentDivStyle}>
                        <h1 style={{marginLeft: 150}}>Create restaurants</h1>

                        <div>
                            <TextField style={{ marginRight: 40, marginTop: 5 }} id="standard-basic" label="Restaurant name" variant="standard" onChange={onChangeRestaurantName}/>
                            <select value={adresaSelectata} onChange={e => setAdresaSelectata(parseInt(e.target.value))} style={{marginTop: 35}}>
                                <option value={"-1"}>Select an address</option>
                                {adrese.map((item) => (<option value={item.adresaId}>{item.tara} {item.judetul} {item.orasul} {item.strada} {item.numarul}</option>))}
                            </select>
                        </div>

                        <div>
                            <TextField style={{ marginRight: 40, marginTop: 15 }} id="standard-basic" label="Openning hour" variant="standard" onChange={onChangeRestaurantOpenningHour}/>
                            <TextField style={{ marginRight: 40, marginTop: 15 }} id="standard-basic" label="Closing hour" variant="standard" onChange={onChangeRestaurantClosingHour}/>
                        </div>

                        <div>
                            <Button style={{marginTop: 30, marginLeft: 150}} variant="contained" onClick={checkFieldsRestaurant}>
                                Create restaurant
                            </Button>
                        </div>
                    </div>

                    {/* sterge restaurant */}
                    <div style={parentDivStyle}>
                        <h1>Delete restaurants</h1>

                        <select style={{marginLeft: 60}} value={restaurantSelectat} onChange={e => setRestaurantSelectat(parseInt(e.target.value))}>
                            <option value={"-1"}>Select a restaurant</option>
                            {restaurante.map((item) => (<option value={item.restaurantId}>{item.nume}</option>))}
                        </select>
                        
                        <h1 style={{marginLeft: 80}}>
                            <Button onClick={deleteRestaurant} variant="contained">
                                Delete
                            </Button>
                        </h1>
                    </div>
                </div>


                {/* sterge, actualizeaza si creeaza produse */}
                <div style={optionsDivStyle}>
                    {/* creeaza produs */}
                    <div style={parentDivStyle}>
                        <h1 style={{marginLeft: 80}}>Create products</h1>

                        <div>
                            <TextField style={{ marginRight: 40, marginTop: 15 }} id="standard-basic" label="Product name" variant="standard" onChange={onChangeProductName}/>
                            <TextField style={{ marginRight: 40, marginTop: 15 }} id="standard-basic" label="Product price" variant="standard" type="number" onChange={onChangeProductPrice}/>
                        </div>

                        <div>
                            <Button style={{marginTop: 30, marginLeft: 150}} variant="contained" onClick={checkFieldsProduct}>
                                Create product
                            </Button>
                        </div>
                    </div>

                    {/* sterge produs */}
                    <div style={parentDivStyle}>
                        <h1>Delete products</h1>

                        <select style={{marginLeft: 60}} value={produsSelectat} onChange={e => setProdusSelectat(parseInt(e.target.value))}>
                            <option value={"-1"}>Select a product</option>
                            {produse.map((item) => (<option value={item.produsId}>{item.nume}</option>))}
                        </select>

                        <h1 style={{marginLeft: 75}}>
                            <Button onClick={deleteProduct} variant="contained">
                                Delete
                            </Button>
                        </h1>
                    </div>

                    {/* actualizeaza produs */}
                    <div style={parentDivStyle}>
                        <h1 style={{marginLeft: 80}}>Update product</h1>

                        <select style={{marginLeft: 140}} value={produsSelectat2} onChange={e => setProdusSelectat2(parseInt(e.target.value))} onClick={updateFieldsProduct}>
                            <option value={"-1"}>Select a product</option>
                            {produse.map((item) => (<option value={item.produsId}>{item.nume}</option>))}
                        </select>

                        <div>
                            <TextField style={{ marginRight: 40, marginTop: 15 }} id="standard-basic" label="Product name" variant="standard" onChange={onChangeProductName2} value={numeProdus2}/>
                            <TextField style={{ marginRight: 40, marginTop: 15 }} id="standard-basic" label="Product price" variant="standard" onChange={onChangeProductPrice2} value={pretProdus2}/>
                        </div>

                        <h1 style={{marginLeft: 150}}>
                            <Button onClick={updateProduct} variant="contained"> 
                                Update
                            </Button>
                        </h1>
                    </div>
                </div>


                {/* sterge si creeaza adrese */}
                <div style={optionsDivStyle}>
                    {/* creeaza adresa */}
                    <div style={parentDivStyle}>
                        <h1 style={{marginLeft: 230}}>Create addresses</h1>

                        <div>
                            <TextField style={{ marginRight: 40, marginTop: 15 }} id="standard-basic" label="Country" variant="standard" onChange={onChangeTara}/>
                            <TextField style={{ marginRight: 40, marginTop: 15 }} id="standard-basic" label="Region" variant="standard" onChange={onChangeJudet}/>
                            <TextField style={{ marginRight: 40, marginTop: 15 }} id="standard-basic" label="City" variant="standard" onChange={onChangeOras}/>
                        </div>

                        <div>
                            <TextField style={{ marginRight: 40, marginTop: 15 }} id="standard-basic" label="Street" variant="standard" onChange={onChangeStrada}/>
                            <TextField style={{ marginRight: 40, marginTop: 15 }} id="standard-basic" label="Number" variant="standard" type="number" onChange={onChangeNumar}/>
                            <Button style={{marginTop: 30, marginLeft: 40}} onClick={checkFieldsAddress} variant="contained">
                                Create
                            </Button>
                        </div>
                    </div>

                    {/* sterge adresa */}
                    <div style={parentDivStyle}>
                        <h1 style={{marginLeft: 50}}>Delete addresses</h1>

                        <select value={adresaSelectata2} onChange={e => setAdresaSelectata2(parseInt(e.target.value))} style={{marginTop: 35}}>
                            <option value={"-1"}>Select an address</option>
                            {adrese.map((item) => (<option value={item.adresaId}>{item.tara} {item.judetul} {item.orasul} {item.strada} {item.numarul}</option>))}
                        </select>

                        <h1 style={{marginLeft: 110}}>
                            <Button onClick={deleteAddress} variant="contained">
                                Delete
                            </Button>
                        </h1>
                    </div>
                </div>
            </div>

            <div style={mainDivStyle2}>
                {/* sterge client */}
                <div style={optionsDivStyle}>
                    <div style={parentDivStyle}>
                        <h1 style={{marginLeft: 30}}>Delete clients</h1>

                        <select value={clientSelectat} onChange={e => setClientSelectat(parseInt(e.target.value))} style={{marginTop: 35}}>
                            <option value={"-1"}>Select a client</option>
                            {clienti.map((item) => (<option value={item.adresaId}>{item.nume} {item.prenume} {item.email}</option>))}
                        </select>

                        <h1 style={{marginLeft: 80}}>
                            <Button onClick={deleteClient} variant="contained">
                                Delete
                            </Button>
                        </h1>
                    </div>
                </div>
            </div>
        </div>
        
    );
};