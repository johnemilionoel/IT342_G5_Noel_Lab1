import { useState } from "react";
import { registerUser } from "../services/authService";

function Register() {
  const [form, setForm] = useState({
    userEmail: "",
    userPassword: "",
    userFirstName: "",
    userLastName: ""
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await registerUser(form);
      alert("Registered successfully!");
      window.location.href = "/login";
    } catch (err) {
      alert("Registration failed");
    }
  };

  return (
    <div>
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <input name="userEmail" placeholder="Email" onChange={handleChange} />
        <input name="userPassword" type="password" placeholder="Password" onChange={handleChange} />
        <input name="userFirstName" placeholder="First Name" onChange={handleChange} />
        <input name="userLastName" placeholder="Last Name" onChange={handleChange} />
        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default Register;
