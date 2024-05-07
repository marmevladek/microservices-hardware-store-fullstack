import React from "react";
import { Routes, Route } from "react-router-dom";

import Home from "./components/Home"
import Login from "./components/Login"
import Register from "./components/Register";
import About from "./components/About"
import Contacts from "./components/Contacts"

const AppRoutes = () => {
    return (
        <Routes>
            
            <Route path="/" element={<Home />} />

            <Route path="/home" element={<Home />} />

            <Route path="/login" element={<Login />} />

            <Route path="/signup" element={<Register />} />

            <Route path="/about" element={<About />} />

            <Route path="/contacts" element={<Contacts />} />


        </Routes>
    )
}

export default AppRoutes;