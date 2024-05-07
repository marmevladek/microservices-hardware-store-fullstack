import React from "react";

import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import Header from "./components/Header";
import Footer from "./components/Footer";

import AppRoutes from "./Routes";


const App = () => {
  
  return (

    <>
      <Header />
        <AppRoutes />
      <Footer />
    </>
    
    

  );
};

export default App;
