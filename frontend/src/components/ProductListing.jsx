import { useEffect,useState } from "react";
import Pagination from "./Pagination";
import ProductCard from "./ProductCard";
import FilterBar from "./FilterBar";
import shopBg from "../assets/gallery/shop.jpg"

function ProductListing({apiUrl,searchKeyword,
    setSearchKeyword,pageType}){
    const [products,setProducts]=useState([]);

   // const [searchKeyword,setSearchKeyword]=useState("");

    const[filterMinPrice,setFilterMinPrice]=useState("");
    const[filterMaxPrice,setFilterMaxPrice]=useState("");
    const[filterStock,setFilterStock]=useState("");

    const[sortBy,setSortBy]=useState("id");

    const[page,setPage]=useState(0);
    const[totalPages,setTotalPages]=useState(0);

    const[loading,setLoading]=useState(true);

    const[role,setRole]=useState(null);

    const [isOpen, setIsOpen] = useState(false);

    const [name, setName] = useState("");
    const [price, setPrice] = useState("");
    const [stock, setStock] = useState("");
    const [category, setCategory] = useState("");

    const[editId,setEditId]=useState(null);

    const[image,setImage]=useState(null);

    const getUserRole=()=>{
        const token=localStorage.getItem("token");
        if(!token) return null;
        try{
            const payload=JSON.parse(atob(token.split(".")[1]));
            return payload.role;
        }
        catch(err){
            console.log("Invalid Token");
            return null;
        }
    };

    const fetchProducts=()=>{
        const token=localStorage.getItem("token");

        let url="";

        const isFiltering=
        filterMinPrice!==""||
        filterMaxPrice!==""||
        filterStock!=="";

        const isSearching=
        searchKeyword &&
        searchKeyword.trim()!=="";

        let sortParam="";

        if(sortBy==="price"){
            sortParam="&sortBy=price";
        }
        else if(sortBy==="price,desc"){
            sortParam="&sortBy=price,desc";
        }
        else{
            sortParam="&sortBy=id";
        }

        if(isFiltering){
            url=`http://localhost:8080/api/products/filter?minPrice=${filterMinPrice || 0}&maxPrice=${filterMaxPrice || 999999}&stock=${filterStock || 0}&page=${page}&size=8${sortParam}`;
        }
        else if(isSearching){
            url=`http://localhost:8080/api/products/search?keyword=${searchKeyword}&page=${page}&size=8${sortParam}`;
        }
        else {
            url =`${apiUrl}?page=${page}&size=8${sortParam}`;
        }

        fetch(url,{
            headers:{
                Authorization:`Bearer ${token}`
            },
        })
        .then(res=>res.json())
        .then(data=>{
            setProducts(data.content || data);
            if(data.totalPages){
                setTotalPages(data.totalPages);
            }
        })
        .catch(err=>console.log(err));
    };

    const addToCart=(productId)=>{
        const token=localStorage.getItem("token");

        fetch(`http://localhost:8080/cart/add/${productId}`,
            {
                method:"POST",
                headers:{
                    Authorization:`Bearer ${token}`
                }
            }
        )
        .then(()=>{
            alert("Added to cart");
            fetchProducts();
        })
        .catch(err=>console.error(err));
    };

    const addToWishlist=(productId)=>{
        const token=localStorage.getItem("token");
        fetch(`http://localhost:8080/wishlist/add/${productId}`,
            {method:"POST",
                headers:{
                    Authorization:`Bearer ${token}`}}
        )
        .then(res=>{
            if(!res.ok){
                throw new error("Already added to wishlist");
            }
            return res.json();
        })
        .then(()=>{
            alert("Added to wishlist");
        })
        .catch(err=>{
            alert(err.message);
        });
    };

    const handleDelete=(id)=>{
        const token=localStorage.getItem("token");

        fetch(`http://localhost:8080/api/products/${id}`,{
            method:"DELETE",
            headers:{
                Authorization: `Bearer ${token}`,
            }
        })
        .then(()=>{
            setProducts(prev=>prev.filter(p=>p.id!==id));
        })
        .catch(err=>console.log(err));
    };

    const handleEdit=(product)=>{
        setName(product.name);
        setPrice(product.price);
        setStock(product.stock);
        setCategory(product.category);
        setEditId(product.id);

    };

    // =========================
  // ADD PRODUCT
  // =========================

//   const handleAddProduct = () => {

//     const token = localStorage.getItem("token");

//     fetch("http://localhost:8080/api/products", {

//       method: "POST",

//       headers: {

//         "Content-Type": "application/json",

//         Authorization: `Bearer ${token}`,
//       },

//       body: JSON.stringify({

//         name,
//         price,
//         stock,
//         category
//       })

//     })
//       .then(res => res.json())
//       .then(() => {

//         setName("");
//         setPrice("");
//         setStock("");
//         setCategory("");

//         fetchProducts();
//       })
//       .catch(err => console.log(err));
//   };

const handleAddProduct=()=>{
    const token=localStorage.getItem("token");
    const formData=new FormData();
    formData.append("name",name);
    formData.append("price",price);
    formData.append("stock",stock);
    formData.append("category",category);
    formData.append("image",image);
    
    fetch("http://localhost:8080/api/products",{
        method:"POST",
        headers:{
            Authorization: `Bearer ${token}`
        },
        body:formData
    })
    .then(res=>res.json())
    .then(()=>{
        setName("");
        setPrice("");
        setStock("");
        setCategory("");
        setImage(null);

        fetchProducts();
    })
    .catch(err=>console.log(err));
};


const handleUpdateProduct = () => {

    const token = localStorage.getItem("token");

    const formData = new FormData();

    formData.append("name", name);
    formData.append("price", price);
    formData.append("stock", stock);
    formData.append("category", category);

    if (image) {
        formData.append("image", image);
    }

    fetch(`http://localhost:8080/api/products/${editId}`, {

    method: "PUT",

    headers: {
        Authorization: `Bearer ${token}`,
    },

    body: formData,
})

.then(res => {

    if (!res.ok) {
        throw new Error("Update failed");
    }

    alert("Product Updated");

    setEditId(null);

    setName("");
    setPrice("");
    setStock("");
    setCategory("");
    setImage(null);

    fetchProducts();
})

.catch(err => console.log(err));
};
    useEffect(()=>{
        setLoading(true);
        fetchProducts();
        const userRole=getUserRole();
        setRole(userRole);
        setLoading(false);
    },
[
    page,
    searchKeyword,
    filterMinPrice,
    filterMaxPrice,
    filterStock,
    sortBy,
    apiUrl
]);
    return(
    <>
    <div
      style={{
        backgroundImage: `url(${shopBg})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
        minHeight: "100vh"
      }}
    >
    {/* <div className="search-bar">
        <input
        type="text"
        placeholder="Search Products"
        value={searchKeyword}
        onChange={
            (e)=>{
                setSearchKeyword(e.target.value);
                setPage(0);
            }
        } />
    </div> */}

    <FilterBar
    isOpen={isOpen}
    setIsOpen={setIsOpen}
    filterMinPrice={filterMinPrice}
    setFilterMinPrice={setFilterMinPrice}
    filterMaxPrice={filterMaxPrice}
    setFilterMaxPrice={setFilterMaxPrice}
    filterStock={filterStock}
    setFilterStock={setFilterStock}
    sortBy={sortBy}
    setSortBy={setSortBy}
    setPage={setPage}
    />
<div
        style={{
          minHeight: "10vh",
          backgroundSize: "cover",
          backgroundPosition: "center"
        }}
      >
        
{/* 🧑 ADMIN FORM */} {role === "ROLE_ADMIN" && 
( <div className="admin-form"> 
<h3>Add Product</h3> 
<input placeholder="Product Name" value={name} onChange={(e) => setName(e.target.value)} /> 
<br/> <input placeholder="Price" value={price} onChange={(e) => setPrice(e.target.value)} />
 <br /> <input placeholder="Stock" value={stock} onChange={(e) => setStock(e.target.value)} />
 <br /> <input placeholder="Category" value={category} onChange={(e) => setCategory(e.target.value)} />
 <br/><input type="file" accept="image/*" onChange={(e) => setImage(e.target.files[0])}/>
  <br /> <button onClick={editId ? handleUpdateProduct : handleAddProduct}> {editId ? "Update Product" : "Add Product"} </button> 
  {editId && ( <button onClick={() => { setEditId(null); setName(""); setPrice(""); setStock("");setCategory(""); }}> Cancel </button> )} 
  </div> )}
</div>
<h2 style={{textAlign: "center"}}>Products List</h2>
    {loading?(<p>Loading...</p>)
    :products.length===0?(
        <p>No products available</p>)
        :(
            <>
            <div className="product-grid">
                {
                    products.map((product)=>(
                        <ProductCard
                        key={product.id}
                        product={product}
                        role={role}
                        handleDelete={handleDelete}
                        handleEdit={handleEdit}
                        addToCart={addToCart}
                        addToWishlist={addToWishlist}
                        />
                    ))
                }

            </div>

            <Pagination
                        page={page}
                        totalPages={totalPages}
                        setPage={setPage}
                    />
            </>
    )
}

</div>

    </>);
}
export default ProductListing;