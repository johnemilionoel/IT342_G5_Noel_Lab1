import { useEffect } from "react";

function HomePage() {

  useEffect(() => {
    const userId = localStorage.getItem("userId");

    if (!userId) {
      window.location.href = "/login";
    }
  }, []);

  const goToDashboard = () => {
    window.location.href = "/dashboard";
  };

  const handleLogout = () => {
    localStorage.removeItem("userId");
    window.location.href = "/login";
  };

  return (
    <div>
      <h2>Home Page</h2>

      <button onClick={goToDashboard}>Go to Dashboard</button>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
}

export default HomePage;
