import { useEffect, useState } from "react";
import { getProfile } from "../services/authService";

function Dashboard() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const userId = localStorage.getItem("userId");

    if (!userId) {
      window.location.href = "/login";
      return;
    }

    getProfile(userId)
      .then((res) => setUser(res.data))
      .catch(() => {
        alert("Failed to load profile");
      });
  }, []);

  if (!user) return <h3>Loading...</h3>;

  return (
    <div>
      <h2>Dashboard</h2>
      <p>Email: {user.userEmail}</p>
      <p>Name: {user.userFirstName} {user.userLastName}</p>
    </div>
  );
}

export default Dashboard;
