import { useNavigate } from "react-router-dom";
import { useState } from "react";

function Navbar({searchKeyword,
    setSearchKeyword}) {

    const navigate = useNavigate();

   // const [search, setSearch] = useState("");
   

    // =========================
    // LOGOUT
    // =========================

    const logout = () => {

        localStorage.removeItem("token");

        navigate("/");
    };

    // =========================
    // SEARCH
    // =========================

    // const handleSearch = () => {

    //     if (!search.trim()) {

    //         navigate("/products");

    //         return;
    //     }

    //     navigate(
    //         `/products?search=${encodeURIComponent(search)}`
    //     );
    // };

    // =========================
    // ENTER KEY SEARCH
    // =========================

    // const handleKeyDown = (e) => {

    //     if (e.key === "Enter") {

    //         handleSearch();
    //     }
    // };

    return (

        <div
            style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                padding: "15px 25px",
                backgroundColor: "#131921",
                color: "white",
                flexWrap: "wrap",
                position: "sticky",
                top: "0",
                zIndex: "1000"
            }}
        >

            {/* LOGO */}

            <h2
                style={{
                    cursor: "pointer",
                    margin: "0"
                }}
                onClick={() => navigate("/products")}
            >
                MyShop
            </h2>

            {/* SEARCH */}

            <div
                style={{
                    display: "flex",
                    alignItems: "center",
                    gap: "10px"
                }}
            >

                {/* <input
                    type="text"
                    placeholder="Search products..."
                    value={search}
                    onChange={(e) =>
                        setSearch(e.target.value)
                    }
                    onKeyDown={handleKeyDown}
                    style={{
                        padding: "10px",
                        width: "300px",
                        borderRadius: "5px",
                        border: "none"
                    }}
                /> */}
                <input
    type="text"
    placeholder="Search Products"
    value={searchKeyword}
    onChange={(e) => setSearchKeyword(e.target.value)}
/>

                {/* <button
                    onClick={handleSearch}
                    style={{
                        padding: "10px 15px",
                        cursor: "pointer"
                    }}
                >
                    Search
                </button> */}

            </div>

            {/* NAVIGATION BUTTONS */}

            <div
                style={{
                    display: "flex",
                    gap: "15px",
                    alignItems: "center"
                }}
            >

                <button
                    onClick={() => navigate("/products")}
                >
                    Home
                </button>

                <button
                    onClick={() => navigate("/cart")}
                >
                    Cart
                </button>

                <button
                    onClick={() => navigate("/wishlist")}
                >
                    Wishlist
                </button>

                <button
                    onClick={() => navigate("/profile")}
                >
                    Manage Profile
                </button>

                <button
                    onClick={logout}
                    style={{
                        backgroundColor: "red",
                        color: "white",
                        border: "none",
                        padding: "8px 12px",
                        cursor: "pointer",
                        borderRadius: "5px"
                    }}
                >
                    Logout
                </button>

            </div>

        </div>
    );
}

export default Navbar;