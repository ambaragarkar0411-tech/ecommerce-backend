import { useEffect, useState } from "react";
import Navbar from "./Navbar";

function Profile() {

    const [profile, setProfile] = useState({

        id: "",

        name: "",

        mobile: "",

        email: "",

        address: "",

        pincode: ""
    });

    // Handle Input Change
    const handleChange = (e) => {

        setProfile({

            ...profile,

            [e.target.name]: e.target.value
        });
    };

    // Save Profile
    const saveProfile = () => {

        const token = localStorage.getItem("token");

        fetch(`http://localhost:8080/profile/${profile.id}`, {

            method: "PUT",

            headers: {

                "Content-Type": "application/json",

                "Authorization": `Bearer ${token}`
            },

            body: JSON.stringify(profile)

        })

        .then(res => {

            if (!res.ok) {
                throw new Error("Failed to save profile");
            }

            return res.json();
        })

        .then((data) => {

            console.log("PROFILE SAVED:", data);

            // Update State
            setProfile(data);

            // Save To LocalStorage
            localStorage.setItem(
                "profile",
                JSON.stringify(data)
            );

            alert("Profile Saved Successfully");

        })

        .catch(err => {

            console.error("SAVE ERROR:", err);

            alert("Failed to save profile");
        });
    };

    // Load Profile Automatically
    const loadProfile = () => {

        const token = localStorage.getItem("token");

        fetch(`http://localhost:8080/profile`, {

            method: "GET",

            headers: {

                "Content-Type": "application/json",

                "Authorization": `Bearer ${token}`
            }

        })

        .then(res => {

            if (!res.ok) {
                throw new Error("Failed to load profile");
            }

            return res.json();
        })

        .then(data => {

            console.log("PROFILE LOADED:", data);

            // If backend returns null/empty
            if (!data) {
                return;
            }

            // Set State
            setProfile({

                id: data.id || "",

                name: data.name || "",

                mobile: data.mobile || "",

                email: data.email || "",

                address: data.address || "",

                pincode: data.pincode || ""
            });

            // Save To LocalStorage
            localStorage.setItem(
                "profile",
                JSON.stringify(data)
            );

        })

        .catch(err => {

            console.error("LOAD ERROR:", err);
        });
    };

    // Load On Page Open
    useEffect(() => {

        loadProfile();

    }, []);

    return (

        <div style={{ padding: "20px" }}>

            <Navbar />

            <div>

                <h2>Manage Profile</h2>

                <input
                    type="text"
                    name="name"
                    placeholder="Enter Name"
                    value={profile.name}
                    onChange={handleChange}
                />

                <br /><br />

                <input
                    type="text"
                    name="mobile"
                    placeholder="Enter Mobile"
                    value={profile.mobile}
                    onChange={handleChange}
                />

                <br /><br />

                <input
                    type="email"
                    name="email"
                    placeholder="Enter Email"
                    value={profile.email}
                    onChange={handleChange}
                />

                <br /><br />

                <textarea
                    name="address"
                    placeholder="Enter Address"
                    value={profile.address}
                    onChange={handleChange}
                    rows="4"
                    cols="40"
                />

                <br /><br />

                <input
                    type="text"
                    name="pincode"
                    placeholder="Enter Pincode"
                    value={profile.pincode}
                    onChange={handleChange}
                />

                <br /><br />

                <button onClick={saveProfile}>
                    Save Profile
                </button>

            </div>

        </div>
    );
}

export default Profile;