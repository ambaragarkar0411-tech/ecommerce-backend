import { useState, useEffect } from "react";
import axios from "axios";
import { Routes, Route, useNavigate } from "react-router-dom";
 import Wishlist from "./components/Wishlist";
// import Product from "./temp/Product";
 import ProtectedRoute from "./ProtectedRoute";
 import Cart from "./components/Cart";
import Profile from "./components/Profile";
 import Checkout from "./components/Checkout";
import OrderStatus from "./components/OrderStatus"
// import CategoryPage from "./temp/CategoryPage";

import Product from "./pages/Product";
import CategoryPage from "./pages/CategoryPage";

function App() {

  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  useEffect(() => {

    const token = localStorage.getItem("token");

    if (token) {
      navigate("/products");
    }

  }, []);

  const handleLogin = async () => {

    try {

      const response = await axios.post(
        "http://localhost:8080/auth/login",
        {
          username,
          password
        }
      );

      localStorage.setItem("token", response.data);

      navigate("/products");

    } catch (error) {

      console.log(error);
      alert("Invalid credentials");
    }
  };

  return (

    <Routes>

      {/* Login */}
      <Route
        path="/"
        element={
          <div style={{ textAlign: "center", marginTop: "100px" }}>

            <h2>Login</h2>

            <input
              type="text"
              placeholder="Username"
              onChange={(e) => setUsername(e.target.value)}
            />

            <br /><br />

            <input
              type="password"
              placeholder="Password"
              onChange={(e) => setPassword(e.target.value)}
            />

            <br /><br />

            <button onClick={handleLogin}>
              Login
            </button>

          </div>
        }
      />

      {/* Products
      <Route
        path="/products"
        element={
          <ProtectedRoute>
            <Product />
          </ProtectedRoute>
        }
      /> */}

      {/* Products */}
      <Route
        path="/products"
        element={
          <ProtectedRoute>
            <Product />
          </ProtectedRoute>
        }
      />

      Cart
      <Route
        path="/cart"
        element={
          <ProtectedRoute>
            <Cart />
          </ProtectedRoute>
        } 
        
       />

Profile
<Route
   path="/profile"
   element={
      <ProtectedRoute>
         <Profile />
      </ProtectedRoute>
   }
/>
{/* Checkout * */}
 <Route
        path="/checkout"
        element={
            <ProtectedRoute>
                <Checkout />
            </ProtectedRoute>
        }
    /> 

{/* * Order Status */}

<Route
path="/order-status"
element={
  <ProtectedRoute>
    <OrderStatus/>
  </ProtectedRoute>
}/>
<Route
path="/wishlist"
element={
        <ProtectedRoute>
            <Wishlist />
        </ProtectedRoute>
    }
/>

<Route
    path="/category/:category"
    element={
    <ProtectedRoute>
      <CategoryPage />
    </ProtectedRoute>}
/>

    </Routes>
  );
}

export default App;