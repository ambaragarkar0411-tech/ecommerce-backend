import { useParams } from "react-router-dom";
import Navbar from "../components/Navbar";
import ProductListing from "../components/ProductListing";
import CategoryButton from "../components/CategoryButton";
import "../Product.css";
import { useState } from "react";

function Product() {

    const [searchKeyword, setSearchKeyword] = useState("");

    
        
   

  return(
    <>
    {/* <Navbar/> */}

            <Navbar
                searchKeyword={searchKeyword}
                setSearchKeyword={setSearchKeyword}
            />

            
        
    <CategoryButton/>

   

    {/* <ProductListing
    apiUrl={"http://localhost:8080/api/products"}
    /> */}
    
<ProductListing
                apiUrl="http://localhost:8080/api/products"
                searchKeyword={searchKeyword}
                setSearchKeyword={setSearchKeyword}
            />
             
    </>
  );
}
export default Product;