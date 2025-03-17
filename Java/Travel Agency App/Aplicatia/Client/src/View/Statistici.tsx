import React, { useState, useEffect } from 'react';
import axios from 'axios';

import { BarChart, LineChart, PieChart, axisClasses } from '@mui/x-charts';
import { Button } from '@mui/material';
import { buttonInapoiClient } from './Client.style';
import { useTranslation } from 'react-i18next';
import { useNavigate, useLocation } from 'react-router-dom';


const chartSetting = {
    width: 300,
    height: 500,
    sx: {
        [`.${axisClasses.left} .${axisClasses.label}`]: {
            transform: 'translate(-20px, 0)',
        },
    },
};


export const Statistici = (): JSX.Element => {

    const navigate = useNavigate();


    const [t, i18n] = useTranslation("global");
    const [limba, setLimba] = useState("romana");

    const changeLanguageHandler = (languageValue: string) => {
        i18n.changeLanguage(languageValue);
        setLimba(languageValue);
    }

    const location = useLocation();

    useEffect(() => {
        i18n.changeLanguage(location.state.lb.toString());
        setLimba(location.state.lb.toString());
    }, []);

    const handleBack = () => {
        navigate('/Angajat', { state: { lb: limba } });
    };


    const [pacheteList, setPacheteList] = useState<any[]>([]);

    useEffect(() => {
        cautaPachete();
    }, []);

    const cautaPachete = async () => {
        try {
            const lista = await axios.get("http://localhost:8080/PachetTuristic/vizualizeazaToate");
            if (lista.status === 200) {
                setPacheteList(lista.data);
            }
        } catch (errorMessage) {
        }
    };

    {/* PRIMUL CHART*/ }

    const generateDataSet1 = () => {
        const destinationsList: string[] = [];
        const dataSet1: { destinatie: string; count: number }[] = [];

        pacheteList.forEach(pachet => {
            const destination = pachet.destinatie;
            if (!destinationsList.includes(destination)) {
                destinationsList.push(destination);
            }
        });

        destinationsList.forEach(destination => {
            const count = pacheteList.filter(pachet => pachet.destinatie === destination).length;
            dataSet1.push({ destinatie: destination, count: count });
        });

        return dataSet1;
    };

    const dataSet1 = generateDataSet1();

    {/* AL DOILEA CHART*/ }

    const generateDataSet3 = () => {
        const periodList: string[] = [];
        const dataSet3: { label: string; value: number }[] = [];

        pacheteList.forEach(pachet => {
            const period = pachet.perioada.toString();
            if (!periodList.includes(period)) {
                periodList.push(period);
            }
        });

        periodList.forEach(period => {
            const count = pacheteList.filter(pachet => pachet.perioada.toString() === period).length;
            dataSet3.push({ label: period, value: count });
        });

        return dataSet3;
    };

    const dataSet3 = generateDataSet3();


    {/* AL TREILEA CHART*/ }

    const generateDataSet2 = () => {
        const dataSet2 = [
            { label: "mai mult", value: 0 },
            { label: "mai puțin", value: 0 }
        ];

        pacheteList.forEach(pachet => {
            const pret = parseFloat(pachet.pret);
            if (pret <= 500) {
                dataSet2[1].value++;
            } else {
                dataSet2[0].value++;
            }
        });

        return dataSet2;
    };

    const dataSet2 = generateDataSet2();


    const valueFormatter = (value: number | null) => `${value}mm`;

    return (
        <div style={{ display: "flex", flexDirection: "row", justifyContent: "center", gap: "50px" }}>
            <select style={{position: "absolute",top: "20px",left: "5px",fontSize: "16px",padding:"10px"}} value={limba} onChange={e => changeLanguageHandler(e.target.value.toString())}>
                <option value="romana">Română</option>
                <option value="spaniola">Spaniolă</option>
                <option value="engleza">Engleză</option>
            </select>

            <div>
                <h3>{t(limba + ".Pachetele din diferite destinatii")}</h3>
                <BarChart
                    dataset={dataSet1}
                    yAxis={[{ scaleType: 'band', dataKey: 'destinatie', label: 'Destinație' }]}
                    xAxis={[{ label: 'Numărul pachetelor' }]}
                    series={[{ dataKey: 'count', valueFormatter }]}
                    layout="horizontal"
                    grid={{ vertical: true }}
                    {...chartSetting}
                />
            </div>

            <div style={{ width: '300px', height: '500px' }}>
                <h3>{t(limba + ".Datele în care pachete sunt disponibile")}</h3>
                <PieChart
                    series={[
                        {
                            data: dataSet3,
                            highlightScope: { faded: 'global', highlighted: 'item' },
                            faded: { innerRadius: 30, additionalRadius: -30, color: 'gray' },
                            cornerRadius: 5,
                            paddingAngle: 5,
                            startAngle: -90,
                            endAngle: 180,
                        },
                    ]}
                    height={500}
                />
            </div>

            <div style={{ width: '300px', height: '500px' }}>
                <h3>{t(limba + ".Pretul pachetelor")}</h3>
                <BarChart
                    xAxis={[{ scaleType: 'band', data: ["Sub 500", "Peste 500"] }]}
                    series={[{ data: dataSet2.map(item => item.value) }]}
                    width={500}
                    height={300}
                    barLabel="value"
                />

            </div>
            <div>
                <Button style={{
                    margin: "10px",
                    width: "80px",
                    height: "40px",
                    fontSize: "15px",
                    backgroundColor: "#713baa",
                    color: "#fff",
                    borderRadius: "20px",
                    cursor: "pointer",
                    fontFamily: "Arial",
                    fontStyle: "italic",
                }} onClick={handleBack}>{t(limba + ".Mergi inapoi st")}</Button>
            </div>
        </div>

    );
}