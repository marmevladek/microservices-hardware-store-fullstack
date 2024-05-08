import React from "react";
import { Routes, Route } from "react-router-dom";

import Home from "./components/Home"
import Login from "./components/Login"
import Register from "./components/Register";
import About from "./components/About"
import Contacts from "./components/Contacts"

import ImageTest from "./components/ImageTest";
import Product from "./components/Product";

const AppRoutes = () => {
    return (
        <Routes>
            
            <Route path="/" element={<Home />} />

            <Route path="/home" element={<Home />} />

            <Route path="/login" element={<Login />} />

            <Route path="/signup" element={<Register />} />

            <Route path="/about" element={<About />} />

            <Route path="/contacts" element={<Contacts />} />

            <Route path="/product/add" element={<ImageTest />} />

            <Route path="/product/:id" element={<Product />} />
        </Routes>
    )
}

export default AppRoutes;