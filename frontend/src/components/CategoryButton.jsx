import { useNavigate } from "react-router-dom";
function CategoryButton(){
    const navigate=useNavigate();
    const categories = [
        "Furniture",
        "Appliances",
        "Mobiles",
        "Clothes",
        "Sports",
        "Toys",
        "Grocery",
        "Electronics",
        "Beauty",
        "Books"
    ];
    return(
        <div className="categories">
            {
                categories.map((category)=>(
                    <button key={category}
                    onClick={()=>navigate(`/category/${category}`)}>
                    {category}
                    </button>
                ))
            }
        </div>
    );
}
export default CategoryButton;