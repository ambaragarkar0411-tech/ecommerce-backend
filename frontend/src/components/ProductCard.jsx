import { useNavigate } from "react-router-dom";
function ProductCard({
product,
    pageType,
    removeFromWishlist,
role,
handleDelete,
handleEdit,
addToCart,
addToWishlist})
{
const navigate=useNavigate();

return(
<div  className="product-card" key={product.id}>
    <img
    src={`http://localhost:8080/uploads/${product.imageUrl}`}
    alt={product.name}
    className="product-image"/>

        <h4>{product.name}</h4>
        <p>
            Rs {product.price}
        </p>

        <p
        style={{
            color:product.stock > 10? "green": "red",
            fontWeight: "bold"
        }} >
        Stocks Left:{product.stock}
        </p>

                <button onClick={() =>addToWishlist(product.id)}>
                    ❤️ Wishlist
                </button>

                <button onClick={() =>addToCart(product.id)}>
                    Add to Cart
                </button>

                <button onClick={() =>navigate("/checkout", {state: {product}})}>
                    Buy Now
                </button>


                        {role === "ROLE_ADMIN" && 
                        (<div>
                            <button onClick={() =>handleEdit(product)}>
                                Edit
                            </button>

                            <button onClick={() =>handleDelete(product.id)}
                            style={{
                              color: "red",
                              marginLeft: "10px"
                            }}>
                                Delete
                            </button>
                        </div>
                      )}

                    </div>
    );
}
export default ProductCard;