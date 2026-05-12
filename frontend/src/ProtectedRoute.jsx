import {Navigate} from "react-router-dom";

function ProtectedRoute({children}){
    const token=localStorage.getItem("token");

    //if no token, go to login
    if(!token){
        return <Navigate to="/"/>;
    }

    //if token exist show page
    return children;
}

export default ProtectedRoute;