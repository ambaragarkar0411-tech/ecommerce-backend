import{useNavigate} from 'react-router-dom';
function OrderStatus(){
const navigate=useNavigate();
    return(
<div style={{
                textAlign: "center",
                marginTop: "100px"
            }}>
            <h1>Order Confirmed Successfully </h1>
            <p>Your order has been placed successfully.</p>
            <button onClick={() => navigate("/products")}>
                Continue Shopping
            </button>
</div>

    );
}
export default OrderStatus;