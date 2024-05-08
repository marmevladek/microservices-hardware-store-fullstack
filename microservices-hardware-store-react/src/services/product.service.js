import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8081/product/"


const addProduct = (name, price, description, quantity, file, onUploadProgress) => {
    let formData = new FormData();
    formData.append("file", file)
    
    formData.append("name", name)
    formData.append("price", price)
    formData.append("description", description)
    formData.append("quantity", quantity)

    return axios.post(API_URL + "add", formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            ...authHeader()
        },
        onUploadProgress
    })
}

const getProductById = (id) => {
    return axios.get(API_URL + `${id}`)
}

const getAllProducts = () => {
    return axios.get(API_URL + "all")

}

export default {
    addProduct,
    getAllProducts,
    getProductById
}