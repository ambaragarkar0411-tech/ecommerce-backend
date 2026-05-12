import { useNavigate, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import "./Product.css";
import bgImage from "./assets/gallery/shop.jpg";
import Navbar from "./Navbar";

function Product() {

  const navigate = useNavigate();
  const location = useLocation();

  // =========================
  // STATES
  // =========================
const [searchKeyword, setSearchKeyword] = useState("");
  const [products, setProducts] = useState([]);

  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const [stock, setStock] = useState("");

  const [editId, setEditId] = useState(null);

  const [keyword, setKeyword] = useState("");

  const [filterMinPrice, setFilterMinPrice] = useState("");
  const [filterMaxPrice, setFilterMaxPrice] = useState("");
  const [filterStock, setFilterStock] = useState("");

  const [sortBy, setSortBy] = useState("id");

  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const [loading, setLoading] = useState(true);

  const [role, setRole] = useState(null);

  const [isOpen, setIsOpen] = useState(false);

  const [cartCount, setCartCount] = useState(0);

  // =========================
  // GET USER ROLE
  // =========================

  const getUserRole = () => {

    const token = localStorage.getItem("token");

    if (!token) return null;

    try {

      const payload =
        JSON.parse(atob(token.split(".")[1]));

      return payload.role;

    } catch (err) {

      console.log("Invalid token");

      return null;
    }
  };

  // =========================
  // LOGOUT
  // =========================

  const handleLogout = () => {

    localStorage.removeItem("token");

    navigate("/");
  };

  // =========================
  // FETCH PRODUCTS
  // =========================

  const fetchProducts = () => {

    const token = localStorage.getItem("token");

    let url = "";

    const isFiltering =
      filterMinPrice !== "" ||
      filterMaxPrice !== "" ||
      filterStock !== "";

   const isSearching =
      searchKeyword &&
      searchKeyword.trim() !== "";

    let sortParam = "";

    if (sortBy === "price") {

      sortParam = "&sortBy=price";

    } else if (sortBy === "price,desc") {

      sortParam = "&sortBy=price,desc";

    } else {

      sortParam = "&sortBy=id";
    }

    if (isFiltering) {

      url =
        `http://localhost:8080/api/products/filter?minPrice=${filterMinPrice || 0}&maxPrice=${filterMaxPrice || 999999}&stock=${filterStock || 0}&page=${page}&size=8${sortParam}`;

    } else if (isSearching) {

      url =
`http://localhost:8080/api/products/search?keyword=${searchKeyword}&page=${page}&size=8${sortParam}`;

    } else {

      url =
        `http://localhost:8080/api/products?page=${page}&size=8${sortParam}`;
    }

    fetch(url, {

      headers: {
        Authorization: `Bearer ${token}`,
      },

    })
      .then(res => res.json())
      .then(data => {

        setProducts(data.content);

        setTotalPages(data.totalPages);
      })
      .catch(err => console.log(err));
  };

  // =========================
  // LOAD CART COUNT
  // =========================

  const loadCartCount = () => {

    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/cart/count", {

      headers: {
        Authorization: `Bearer ${token}`
      }

    })
      .then(res => res.json())
      .then(data => {

        setCartCount(data);
      })
      .catch(err => console.error(err));
  };

  // =========================
  // ADD PRODUCT
  // =========================

  const handleAddProduct = () => {

    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/api/products", {

      method: "POST",

      headers: {

        "Content-Type": "application/json",

        Authorization: `Bearer ${token}`,
      },

      body: JSON.stringify({

        name,
        price,
        stock
      })

    })
      .then(res => res.json())
      .then(() => {

        setName("");
        setPrice("");
        setStock("");

        fetchProducts();
      })
      .catch(err => console.log(err));
  };

  // =========================
  // DELETE PRODUCT
  // =========================

  const handleDelete = (id) => {

    const token = localStorage.getItem("token");

    fetch(`http://localhost:8080/api/products/${id}`, {

      method: "DELETE",

      headers: {
        Authorization: `Bearer ${token}`,
      }

    })
      .then(() => {

        setProducts(prev =>
          prev.filter(p => p.id !== id)
        );
      })
      .catch(err => console.log(err));
  };

  // =========================
  // UPDATE PRODUCT
  // =========================

  const handleUpdateProduct = () => {

    const token = localStorage.getItem("token");

    fetch(`http://localhost:8080/api/products/${editId}`, {

      method: "PUT",

      headers: {

        "Content-Type": "application/json",

        Authorization: `Bearer ${token}`,
      },

      body: JSON.stringify({

        name,
        price,
        stock
      })

    })
      .then(() => {

        setName("");
        setPrice("");
        setStock("");

        setEditId(null);

        fetchProducts();
      })
      .catch(err => console.log(err));
  };

  // =========================
  // EDIT PRODUCT
  // =========================

  const handleEdit = (product) => {

    setName(product.name);

    setPrice(product.price);

    setStock(product.stock);

    setEditId(product.id);
  };

  // =========================
  // ADD TO CART
  // =========================

  const addToCart = (productId) => {

    const token = localStorage.getItem("token");

    fetch(`http://localhost:8080/cart/add/${productId}`, {

      method: "POST",

      headers: {
        Authorization: `Bearer ${token}`
      }

    })
      .then(() => {

        alert("Added to cart");

        loadCartCount();

        fetchProducts();
      })
      .catch(err => console.error(err));
  };

  // =========================
  // ADD TO WISHLIST
  // =========================

  const addToWishlist = (productId) => {

    const token = localStorage.getItem("token");

    fetch(`http://localhost:8080/wishlist/add/${productId}`, {

      method: "POST",

      headers: {
        Authorization: `Bearer ${token}`
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
      .then(() => {

        alert("Added to Wishlist ❤️");
      })
      .catch(err => {

        alert(err.message);
      });
  };

  // =========================
  // URL SEARCH PARAM
  // =========================

  useEffect(() => {

    const queryParams =
        new URLSearchParams(location.search);

    const search =
        queryParams.get("search");

    if (search) {

        setSearchKeyword(search);

    } else {

        setSearchKeyword("");
    }

    setPage(0);

}, [location.search]);

  // =========================
  // MAIN USE EFFECT
  // =========================

  useEffect(() => {

    const token = localStorage.getItem("token");

    if (!token) {

        navigate("/");

        return;
    }

    setLoading(true);

    fetchProducts();

    loadCartCount();

    const userRole = getUserRole();

    setRole(userRole);

    setLoading(false);

}, [
    page,
    searchKeyword,
    filterMinPrice,
    filterMaxPrice,
    filterStock,
    sortBy
]);

  // =========================
  // UI
  // =========================

  return (

    <>
      <Navbar />

      <div
        style={{
          minHeight: "100vh",
          backgroundImage: `url(${bgImage})`,
          backgroundSize: "cover",
          backgroundPosition: "center"
        }}
      >
        <br/><br/>
{/* 🧑 ADMIN FORM */} {role === "ROLE_ADMIN" && 
( <div className="admin-form"> 
<h3>Add Product</h3> 
<input placeholder="Product Name" value={name} onChange={(e) => setName(e.target.value)} /> 
<br /><br /> <input placeholder="Price" value={price} onChange={(e) => setPrice(e.target.value)} />
 <br /><br /> <input placeholder="Stock" value={stock} onChange={(e) => setStock(e.target.value)} />
  <br /><br /> <button onClick={editId ? handleUpdateProduct : handleAddProduct}> {editId ? "Update Product" : "Add Product"} </button> 
  {editId && ( <button onClick={() => { setEditId(null); setName(""); setPrice(""); setStock(""); }}> Cancel </button> )} 
  </div> )}
        <div className="main-container">

          {/* FILTER */}

          {isOpen &&
            <div
              className="drawer-overlay"
              onClick={() => setIsOpen(false)}
            />
          }

          <div className={`filter-container ${isOpen ? "open" : ""}`}>

            <div className="filter-drawer">

              <h3>Filters</h3>

              <input
                type="number"
                placeholder="Min Price"
                value={filterMinPrice}
                onChange={(e) =>
                  setFilterMinPrice(e.target.value)
                }
              />

              <input
                type="number"
                placeholder="Max Price"
                value={filterMaxPrice}
                onChange={(e) =>
                  setFilterMaxPrice(e.target.value)
                }
              />

              <input
                type="number"
                placeholder="Stock"
                value={filterStock}
                onChange={(e) =>
                  setFilterStock(e.target.value)
                }
              />

            </div>

            <button
              className="filter-handle-btn"
              onClick={() => setIsOpen(!isOpen)}
            >
              {isOpen ? "✕" : "Filters"}
            </button>
          </div>

          {/* CONTENT */}

          <div className="content">

            <div className="sort-container">

              <label>Sort By:</label>

              <select
                value={sortBy}
                onChange={(e) => {

                  setSortBy(e.target.value);

                  setPage(0);
                }}
              >

                <option value="id">
                  Default
                </option>

                <option value="price">
                  Price Low → High
                </option>

                <option value="price,desc">
                  Price High → Low
                </option>

              </select>

            </div>

            <h2 >PRODUCT LIST</h2>

            {loading ? (

              <p>Loading...</p>

            ) : products.length === 0 ? (

              <p>No products available</p>

            ) : (

              <>
                <div className="product-grid">

                  {products.map((product) => (

                    <div
                      className="product-card"
                      key={product.id}
                    >

                      <h4>{product.name}</h4>

                      <p>
                        Rs {product.price}
                      </p>

                      <p
                        style={{
                          color:
                            product.stock > 10
                              ? "green"
                              : "red",

                          fontWeight: "bold"
                        }}
                      >
                        Stocks Left:
                        {product.stock}
                      </p>

                      <button
                        onClick={() =>
                          addToWishlist(product.id)
                        }
                      >
                        ❤️ Wishlist
                      </button>

                      <button
                        onClick={() =>
                          addToCart(product.id)
                        }
                      >
                        Add to Cart
                      </button>

                      <button
                        onClick={() =>
                          navigate("/checkout", {
                            state: { product }
                          })
                        }
                      >
                        Buy Now
                      </button>

                      {role === "ROLE_ADMIN" && (

                        <div>

                          <button
                            onClick={() =>
                              handleEdit(product)
                            }
                          >
                            Edit
                          </button>

                          <button
                            onClick={() =>
                              handleDelete(product.id)
                            }
                            style={{
                              color: "red",
                              marginLeft: "10px"
                            }}
                          >
                            Delete
                          </button>

                        </div>
                      )}

                    </div>
                  ))}

                </div>

                {/* PAGINATION */}

                <div
                  style={{
                    marginTop: "20px"
                  }}
                >

                  <button
                    onClick={() =>
                      setPage(page - 1)
                    }
                    disabled={page === 0}
                  >
                    Previous
                  </button>

                  <span
                    style={{
                      margin: "0 10px"
                    }}
                  >
                    Page {page + 1}
                    {" "}of{" "}
                    {totalPages}
                  </span>

                  <button
                    onClick={() =>
                      setPage(page + 1)
                    }
                    disabled={
                      page >= totalPages - 1
                    }
                  >
                    Next
                  </button>

                </div>

              </>
            )}

          </div>

        </div>

      </div>

    </>
  );
}

export default Product;