import React, { useEffect, useState } from "react";

import productService from "../services/product.service";
import { useParams } from "react-router-dom";




const Product = () => {
    const [product, setProduct] = useState("");

    const {id} = useParams();
    
    useEffect(() => {
        productService.getProductById(id)
            .then(response => {
                setProduct(response.data)
            })
            .catch(error => {
                console.log("sadfasdf")
                console.error("Error fetch product: ", error)
            })
    },[])
    
    // console.log(product.image[0])
    // const { user: currentUser } = useSelector((state) => state.auth);
    





    return(
        <>
            <main id="main-container" class="main-container">
        <div class="container">
            <div class="row mt-5">

                <div class="col-12">
                    <div class="product-details">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="product-gallery-box m-b-60">
                                    <div class="product-image--large overflow-hidden">
                                        {product.images &&
                                        product.images.map(img =>
                                            <img class="img-fluid" id="img-zoom" src={img.url + "/" + img.name} alt=""/>
                                        )}
                                        
                                    </div>
                                    </div>
                            </div>
                            <div class="col-md-6">
                                <div class="product-details-box">
                                    <h5 class="section-content__title">{product.productName}</h5>
                                    <div class="review-box">
                                        <ul class="product__review m-t-10 m-b-15">
                                            <li class="product__review--fill"><i class="icon-star"></i></li>
                                            <li class="product__review--fill"><i class="icon-star"></i></li>
                                            <li class="product__review--fill"><i class="icon-star"></i></li>
                                            <li class="product__review--fill"><i class="icon-star"></i></li>
                                            <li class="product__review--blank"><i class="icon-star"></i></li>
                                        </ul>
                                    </div>
                                    <div class="product__price">

                                        <span class="product__price-reg">{product.price} ₽</span>
                                    </div>
                                    <div class="product__desc m-t-25 m-b-30">
                                        <p>{product.description}</p>
                                    </div>
                                    <br />
                                    <div class="product-var p-t-30">
                                        <div class="product-quantity product-var__item">
                                            <span class="product-var__text">Количество (в наличии: {product.quantity})</span>
                                            <div class="product-quantity-box">
                                                <div class="quantity">
                                                    <input type="number" min="1" max="10"/>
                                                    <a href="#modalAddCart" data-toggle="modal" data-dismiss="modal" class="btn btn--box btn--small btn--blue btn--uppercase btn--weight m-l-50">В корзину</a>
                                                </div>
                                                
                                            </div>
                                           
                                        </div>
                                    </div>
                                    <br />
                                    <div class="product-links ">
                                        <a href="wishlist.html" class="link--gray link--icon-left shop__wishlist-icon m-b-5"><i class="icon-heart"></i>Add To Wishlist</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> 
                </div>
                

                <div class="col-12">
                    <div class="product product--1 border-around product-details-tab-area">
                        <div class="row">
                            <div class="col-12">
                                <div class="section-content--border">
                                    <ul class="tablist tablist--style-black tablist--style-title tablist--style-gap-70 nav">
                                        <li><a class="nav-link active" data-toggle="tab" href="#product-desc">Description</a></li>
                                        <li><a class="nav-link" data-toggle="tab" href="#product-dis">Product Details</a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-md-12">
                            <div class="product-details-tab-box m-t-50">
                                <div class="tab-content">

                                    <div class="tab-pane show active" id="product-desc">
                                        <div class="para__content">
                                            <p class="para__text">{product.description}</p>
                                        </div>    
                                    </div>  


                                    <div class="tab-pane" id="product-dis">
                                        <div class="product-dis__content">
                                            <a href="#" class="product-dis__img m-b-30"><img src="assets/img/logo/another-logo.jpg" alt=""/></a>
                                            <div class="table-responsive-md">
                                                <table class="product-dis__list table table-bordered">
                                                    <tbody>
                                                        <tr>
                                                            <td class="product-dis__title">Weight</td>
                                                            <td class="product-dis__text">400 g</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="product-dis__title">Materials</td>
                                                            <td class="product-dis__text">60% cotton, 40% polyester</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="product-dis__title">Dimensions</td>
                                                            <td class="product-dis__text">10 x 10 x 15 cm</td>
                                                        </tr>
                                                        <tr> 
                                                            <td class="product-dis__title">Other Info</td>
                                                            <td class="product-dis__text">American heirloom jean shorts pug seitan letterpress</td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div> 
                                     
                                    
                                </div>
                            </div>
                        </div>
                        </div>
                    </div>
                </div>  

            
                </div>
            </div>

        </main>
    </>
    )
}

export default Product;