import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";
import "../App.css";

import productService from "../services/product.service";


const Home = () => {
  const [products, setProducts] = useState()

  useEffect(() => {
    productService.getAllProducts()
      .then(response => {
        setProducts(response.data);
      })
      .catch(error => {
        console.error("Error fetching products:", error);
      });
  }, []);


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
                <form class="header__search-form" action="#">
                  <div class="header__search-input">
                    <input type="search" placeholder="Поиск" />
                    <button class="btn btn--submit btn-dark btn--uppercase btn--weight " type="submit">Найти</button>
                  </div>
                </form>
                
            </div>

        </nav>
        
        <br/>
        <div class="product-counter-slider-1grid overflow-hidden m-t-50">
    <div class="swiper-wrapper d-flex flex-row justify-content-between">
        {products && products.map(product =>
            <div class="product__box product__box--default product__box--border-hover swiper-slide text-center swiper-slide-active" key={product.productId}>
                <div class="product__img-box">
                    <li className="product__img--link">
                      <Link to={`/product/${product.productId}`}>
                      <img class="product__img" src={product.images[0].url + "/" + product.images[0].name} alt={product.images[0].name} height="250px" width="250px"/>
                      </Link>
                        
                    </li>
                    <a href="#modalAddCart" data-toggle="modal" class="btn btn--box btn--small btn--gray btn--uppercase btn--weight btn--hover-zoom product__upper-btn">В корзину</a>
                    <a href="wishlist.html" class="product__wishlist-icon"><i class="icon-heart"></i></a>
                </div>
                <div class="product__price m-t-10">
                    <span class="product__price-reg">{product.price} ₽</span>
                </div>
                <a href="single-1.html" class="product__link product__link--weight-regular m-t-15">
                    {product.productName}
                </a>
            </div>
        )}
    </div>
    <div class="swiper-buttons">
        <div class="swiper-button-next default__nav default__nav--next"><i class="fal fa-chevron-right"></i></div>
        <div class="swiper-button-prev default__nav default__nav--prev"><i class="fal fa-chevron-left"></i></div>
    </div>
</div>



            </div>
            

      

    </div>
  );
};

export default Home;