import React, { useState, useEffect, useCallback } from "react";
import { Routes, Route, Link, useLocation } from "react-router-dom";
import UserService from "../services/user.service";
import { useDispatch, useSelector } from "react-redux";
import "bootstrap/dist/css/bootstrap.min.css";
import "../App.css";

// import Login from "../components/Login";
// import Register from "../components/Register";
// import Profile from "./components/Profile";
// import BoardUser from "./components/BoardUser";
// import BoardModerator from "./components/BoardModerator";
// import BoardAdmin from "./components/BoardAdmin";

import { logout } from "../actions/auth";
import { clearMessage } from "../actions/message";

// import AuthVerify from "./common/AuthVerify";
import EventBus from "../common/EventBus";

const Home = () => {
  
  return (    
    <div>
      <div className="container mt-5">
        <div className="row">
            <div className="col-md-12">
                <h1 className="text-center">Добро пожаловать в ХозБытМаркет - магазин хозяйственных товаров</h1>
                <p className="lead text-center">Здесь вы можете найти все необходимые товары для дома и быта.</p>
            </div>
        </div>
        <nav className="navbar navbar-expand-lg navbar-light bg-light">

            <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>

            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav mr-auto">
                    <li className="nav-item dropdown">
                        <a className="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Каталог товаров
                        </a>
                        <div className="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a className="dropdown-item" href="#">Уборка</a>
                            <a className="dropdown-item" href="#">Кухня</a>
                            <a className="dropdown-item" href="#">Сад и огород</a>

                        </div>
                    </li>
                </ul>
                <form className="form-inline my-2 my-lg-0">
                    <input className="form-control mr-sm-2" type="search" placeholder="Поиск товаров" aria-label="Search"/>
                    <button className="btn btn-outline-success my-2 my-sm-0" type="submit"><img src="https://image.flaticon.com/icons/png/512/25/25694.png" alt="Поиск" style={{width: "20px"}}/></button>
                </form>
            </div>

        </nav>
        
        <br/>
        <div className="row mb-5">

              <div className="col-sm-6 col-lg-4 mb-4" data-aos="fade-up">
                <div className="block-4 text-center border">
                  <figure className="block-4-image">
                    <a href="shop-single.html"><img src="https://via.placeholder.com/300" className="card-img-top" alt="Товар"/></a>
                  </figure>
                  <div className="block-4-text p-4">
                    <h3><a href="shop-single.html">Tank Top</a></h3>
                    <p className="mb-0">Finding perfect t-shirt</p>
                    <p className="text-primary font-weight-bold">$50</p>
                  </div>
                </div>
              </div>
              <div className="col-sm-6 col-lg-4 mb-4" data-aos="fade-up">
                <div className="block-4 text-center border">
                  <figure className="block-4-image">
                    <a href="shop-single.html"><img src="https://via.placeholder.com/300" className="card-img-top" alt="Товар"/></a>
                  </figure>
                  <div className="block-4-text p-4">
                    <h3><a href="shop-single.html">Corater</a></h3>
                    <p className="mb-0">Finding perfect products</p>
                    <p className="text-primary font-weight-bold">$50</p>
                  </div>
                </div>
              </div>
              <div className="col-sm-6 col-lg-4 mb-4" data-aos="fade-up">
                <div className="block-4 text-center border">
                  <figure className="block-4-image">
                    <a href="shop-single.html"><img src="https://via.placeholder.com/300" className="card-img-top" alt="Товар"/></a>
                  </figure>
                  <div className="block-4-text p-4">
                    <h3><a href="shop-single.html">Polo Shirt</a></h3>
                    <p className="mb-0">Finding perfect products</p>
                    <p className="text-primary font-weight-bold">$50</p>
                  </div>
                </div>
              </div>

              <div className="col-sm-6 col-lg-4 mb-4" data-aos="fade-up">
                <div className="block-4 text-center border">
                  <figure className="block-4-image">
                    <a href="shop-single.html"><img src="https://via.placeholder.com/300" className="card-img-top" alt="Товар"/></a>
                  </figure>
                  <div className="block-4-text p-4">
                    <h3><a href="shop-single.html">T-Shirt Mockup</a></h3>
                    <p className="mb-0">Finding perfect products</p>
                    <p className="text-primary font-weight-bold">$50</p>
                  </div>
                </div>
              </div>
            </div>
      </div>
      

    </div>
  );
};

export default Home;
