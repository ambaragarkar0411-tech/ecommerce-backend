import { useEffect,useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "./Navbar";
import ProductListing from "./ProductListing";

function Wishlist(){
const[wishlistItems,setWishlistItems]=useState([]);
const navigate=useNavigate();
const loadWishlist=()=>{
    const token=localStorage.getItem("token");

    fetch("http://localhost:8080/wishlist",{
        headers:{
            "Authoriztion":`Bearer ${token}`
        }
    })
    .then(res => {

    if (!res.ok) {

        throw new Error(
            "Already added to wishlist"
        );
    }

    return res.json();
})
    .then(data=>{
        setWishlistItems(data);
    })
    .catch(err=>console.error(err));
};
useEffect(()=>{
loadWishlist();
},[])

const removeWishlist = (wishlistId) => {

    const token = localStorage.getItem("token");

    fetch(`http://localhost:8080/wishlist/${wishlistId}`, {

        method: "DELETE",

        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
    .then(() => {

        loadWishlist();
    })
    .catch(err => console.error(err));
};
    return(
        <div style={{padding:"20px"}}>
            <>
            <Navbar />

            <div>
            <button onClick={()=>navigate("/products")}>
                Home Page
            </button>
            <h2>My Wishlist ❤️</h2>
               {
                wishlistItems.length===0?(
                    <p>No wishlists items</p>
                ):(
                    wishlistItems.map((item) => (<div
                            key={item.id}
                            style={{
                                border: "1px solid #ccc",
                                padding: "15px",
                                marginBottom: "10px",
                                borderRadius: "10px"
                            }}
                        >

                            <h3>{item.product.name}</h3>

                            <p>Price: Rs {item.product.price}</p>

                            <p>
                                Stock Left: {item.product.stock}
                            </p>

                            <button
                                onClick={() => removeWishlist(item.id)}
                            >
                                Remove
                            </button>

                        </div>)
                ))
            }   
            

            </div>
        </>
             </div> 
    );

}
export default Wishlist;