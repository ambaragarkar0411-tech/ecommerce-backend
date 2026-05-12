import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

function Checkout() {

    const location = useLocation();

    const navigate = useNavigate();

    const product = location.state.product;

    // Profile State
    const [profile, setProfile] = useState({});

    // Payment Method
    const [paymentMethod, setPaymentMethod] = useState("");

    // Address
    const [address, setAddress] = useState("");

    // Load Profile From LocalStorage
    useEffect(() => {

        const savedProfile = JSON.parse(
            localStorage.getItem("profile")
        );

        if (savedProfile) {

            setProfile(savedProfile);

            // Auto fill address
            setAddress(savedProfile.address || "");
        }

    }, []);

    // Place Order
    const placeOrder = () => {

        const token = localStorage.getItem("token");

        // Validation
        if (!profile.email) {

            alert("Please complete profile first");

            navigate("/profile");

            return;
        }

        if (!paymentMethod) {

            alert("Please select payment method");

            return;
        }

        // Order Data
        const orderData = {

            customerName: profile.name || "",

            email: profile.email || "",

            productName: product.name,

            price: product.price,

            quantity: 1,

            totalPrice: product.price,

            paymentMethod: paymentMethod,

            address: address || profile.address || ""
        };

        console.log(orderData);

        // API Call
        fetch(`http://localhost:8080/orders`, {

            method: "POST",

            headers: {

                "Content-Type": "application/json",

                "Authorization": `Bearer ${token}`
            },

            body: JSON.stringify(orderData)
        })

        .then(res => res.json())

        .then((data) => {

            console.log(data);

            alert("Order Placed Successfully");

            navigate("/order-status");
        })

        .catch(err => console.error(err));
    };

    return (

        <div style={{ padding: "20px" }}>

            <h2>Checkout</h2>

            <h3>{product.name}</h3>

            <p>Price: Rs {product.price}</p>

            <h3>Select Payment Method</h3>

            <div>

                <input
                    type="radio"
                    name="payment"
                    value="Credit/Debit Card"
                    onChange={(e) =>
                        setPaymentMethod(e.target.value)
                    }
                />

                Credit/Debit Card

                <br /><br />

                <input
                    type="radio"
                    name="payment"
                    value="UPI Payment"
                    onChange={(e) =>
                        setPaymentMethod(e.target.value)
                    }
                />

                UPI Payment

                <br /><br />

                <input
                    type="radio"
                    name="payment"
                    value="Cash On Delivery"
                    onChange={(e) =>
                        setPaymentMethod(e.target.value)
                    }
                />

                Cash On Delivery

                <br /><br />

                <textarea
                    placeholder="Enter Delivery Address"
                    value={address}
                    onChange={(e) =>
                        setAddress(e.target.value)
                    }
                    rows="4"
                    cols="40"
                />

                <br /><br />

                <button onClick={placeOrder}>
                    Place Order
                </button>

            </div>

        </div>
    );
}

export default Checkout;