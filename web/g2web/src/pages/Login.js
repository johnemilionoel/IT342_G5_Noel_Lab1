import { useState } from "react";
import { loginUser } from "../services/authService";

function Login() {
  const [form, setForm] = useState({
    userEmail: "",
    userPassword: ""
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await loginUser(form);

      // save userId locally
      localStorage.setItem("userId", res.data.userID);

      window.location.href = "/home";
    } catch (err) {
      alert("Invalid login");
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <input name="userEmail" placeholder="Email" onChange={handleChange} />
        <input name="userPassword" type="password" placeholder="Password" onChange={handleChange} />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}

export default Login;
