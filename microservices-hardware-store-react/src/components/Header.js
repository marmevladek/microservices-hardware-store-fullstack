import React, { useState, useEffect, useCallback } from "react";
import { Link, useLocation } from "react-router-dom";

import { useDispatch, useSelector } from "react-redux";
import "bootstrap/dist/css/bootstrap.min.css";
import "../App.css";
import { logout } from "../actions/auth";
import { clearMessage } from "../actions/message";


import EventBus from "../common/EventBus";

const Header = () => {
    const [showModeratorBoard, setShowModeratorBoard] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);

  const { user: currentUser } = useSelector((state) => state.auth);
  const dispatch = useDispatch();

  let location = useLocation();

  useEffect(() => {
    if (["/login", "/register"].includes(location.pathname)) {
      dispatch(clearMessage()); // clear message when changing location
    }
  }, [dispatch, location]);

  const logOut = useCallback(() => {
    dispatch(logout());
  }, [dispatch]);

  useEffect(() => {
    if (currentUser) {
      setShowModeratorBoard(currentUser.roles.includes("ROLE_MODERATOR"));
      setShowAdminBoard(currentUser.roles.includes("ROLE_ADMIN"));
    } else {
      setShowModeratorBoard(false);
      setShowAdminBoard(false);
    }

    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, [currentUser, logOut]);

  return (
    <>
      <header className="bg-dark text-light py-2">
      <div className="container">
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <Link to={"/"} className="navbar-brand text-light" style={{fontSize:25}}>
                  ХозБытМаркет
          </Link>
          <div className="navbar-nav mr-auto">
              <li className="nav-item">
                <Link to={"/about"} className="nav-link">
                  О нас
                </Link>
              </li>

              <li className="nav-item">
              <Link to={"/contacts"} className="nav-link">
                Контакты
              </Link>
              </li>
              
            </div>  
              {currentUser ? (
                
                  
                  <div className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link to={"/cart"} className="btn text-light">
                      Корзина
                    </Link>
                  </li>
                  

                  <li className="nav-item">
                    <Link to={"/home"} className="btn text-light">
                      {currentUser.username}
                    </Link>
                  </li>
                  

                  <li className="nav-item">
                    <a href="/" className="nav-link" onClick={logOut}>
                        Выйти
                    </a>
                  </li>
                  
                </div>

              ) : (
                <div className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link to={"/login"} className="nav-link">
                      Войти
                    </Link>
                  </li>
      
                  <li className="nav-item">
                    <Link to={"/signup"} className="nav-link">
                      Зарегистрироваться
                    </Link>
                  </li>
                </div>
              )}
        </nav>
        </div>
      </header>
      </>
  )
}

export default Header;