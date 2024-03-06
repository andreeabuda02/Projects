import { RouteObject, createBrowserRouter } from "react-router-dom";
import { Login } from "../Pages/Login";
import React from 'react';


const routes: RouteObject[] = [
    {
        path: "/",
        element: <div>First Page</div>
    },
    {
        path: "/Home",
        element: <div>Prima componenta</div>
    },
    {
        path: "/Login",
        element: <Login />
    }

];

export const router = createBrowserRouter(routes)