import React, { useEffect, useState } from "react";
import {useNavigate } from "react-router-dom";
import Navbar from "./Navbar";

function Cart() {
const navigate = useNavigate();
    const [cartItems, setCartItems] = useState([]);

    const loadCart = () => {

    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/cart", {

        headers: {
            "Authorization": `Bearer ${token}`
        }

    })
    .then(res => {

        if (!res.ok) {
            throw new Error("Failed to load cart");
        }

        return res.json();
    })
    .then(data => {
        setCartItems(data);
    })
    .catch(err => console.error(err));
};

const removeFromCart=(cartId)=>{
    const token=localStorage.getItem("token");

    fetch(`http://localhost:8080/cart/${cartId}`,{
        method:"DELETE",
        headers:{
            "Authorization":`Bearer ${token}`
        }
    })
    .then(res=>{
        if (!res.ok) {
            throw new Error("Failed to remove item");
        }

        return res.text();
    })
    .then(()=>{
    alert("Item Removed");
    loadCart(); //refresh cart
})
.catch(err => console.error(err));
}

//increase function

const increaseQuantity=(cartId)=>{
    const token=localStorage.getItem("token");
    fetch(`http://localhost:8080/cart/increase/${cartId}`,{
        method:"PUT",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })

    .then(res=>{
        if(!res.ok){
            throw new Error("Failed");
        }
        return res.json();
    })
    .then(()=>{loadCart();})
    .catch(err=>console.error(err));
};

const decreaseQuantity = (cartId) => {

    const token = localStorage.getItem("token");

    fetch(`http://localhost:8080/cart/decrease/${cartId}`, {

        method: "PUT",

        headers: {
            "Authorization": `Bearer ${token}`
        }

    })
    .then(res => {

        if (!res.ok) {
            throw new Error("Failed");
        }

        return res.text();

    })
    .then(() => {

        loadCart();

    })
    .catch(err => console.error(err));
};

//adding total prize

const totalPrice=cartItems.reduce(
    (total,item)=>total+(item.product.price * item.quantity),
    0
)

//adding count

const totalItems=cartItems.reduce(
    (total,item)=>total+item.quantity,
    0
)

    useEffect(() => {
        loadCart();
    }, []);

    return (

        <div style={{ padding: "20px" }}>
<button onClick={() => navigate("/products")}> Home Page </button>
 <>
            <Navbar />

            <div>
            <h2>My Cart</h2>

            {cartItems.length === 0 ? (
                <p>Cart is empty</p>
            ) : (

                cartItems.map((item) => (

                    <div
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

 <div style={{ display: "flex", gap: "10px", alignItems: "center" }}>

                <button onClick={() => decreaseQuantity(item.id)}>
                    -
                </button>

                <span>{item.quantity}</span>

                <button onClick={() => increaseQuantity(item.id)}>
                    +
                </button>

</div>
                        <button
                        onClick={()=>removeFromCart(item.id)}
                        style={{
                            backgroundColor: "red",
                            color: "white",
                            border: "none",
                            padding: "8px",
                            borderRadius: "5px",
                            cursor: "pointer"
                        }}> Remove</button>

                    </div>
                ))
            )}
<h2>Total Price: Rs {totalPrice}</h2>
       <h3>Total Item in Basket:{totalItems}</h3> </div></></div>
    );
}

export default Cart;