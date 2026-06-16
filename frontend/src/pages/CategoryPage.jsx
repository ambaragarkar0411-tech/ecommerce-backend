import { useParams } from "react-router-dom";

import Navbar from "../components/Navbar";
import ProductListing from "../components/ProductListing";
import "../Product.css";

function CategoryPage(){
    const {category}=useParams();
    return(
        <>
        <Navbar/>
        <h2>{category} Products</h2>
        <ProductListing
                apiUrl={
                    `http://localhost:8080/api/products/category/${category}`
                }
            />
        </>
    );
}
export default CategoryPage;