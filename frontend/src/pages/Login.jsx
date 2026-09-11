import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  FaTint,
  FaEnvelope,
  FaLock,
  FaEye,
  FaEyeSlash,
  FaArrowRight,
  FaShieldAlt,
  FaUserPlus,
  FaExclamationCircle,
  FaCheckCircle,
} from "react-icons/fa";
import { authApi } from "../services/api";
import { useAuth } from "../context/AuthContext";
import "./Login.css";

function Login() {
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");

  const navigate = useNavigate();
  const { login } = useAuth();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
    remember: false,
  });

  const handleChange = (event) => {
    const { name, value, type, checked } = event.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value,
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setErrorMsg("");
    setSuccessMsg("");
    setLoading(true);

    try {
      const response = await authApi.login({
        email: formData.email,
        password: formData.password,
      });

      const authData = response.data;
      login(authData);
      setSuccessMsg("Signed in successfully! Redirecting...");

      setTimeout(() => {
        navigate("/");
      }, 1200);
    } catch (err) {
      console.error("Login error:", err);
      const backendMessage =
        err.response?.data?.message ||
        "Invalid email or password. Please verify your credentials.";
      setErrorMsg(backendMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">
      {/* LEFT SIDE */}
      <section className="login-left">
        <Link to="/" className="login-brand" style={{ textDecoration: "none" }}>
          <div className="login-brand-icon">
            <FaTint />
          </div>
          <div>
            <span>Blood</span>
            <strong> Response</strong>
            <span> Network</span>
          </div>
        </Link>

        <div className="login-message">
          <div className="login-badge">
            <FaShieldAlt />
            Secure Blood Response Platform
          </div>

          <h1>
            Welcome
            <br />
            <span>Back.</span>
          </h1>

          <p>
            Sign in to connect with blood donors, hospitals,
            patients and blood banks when it matters most.
          </p>

          <div className="login-benefits">
            <div className="benefit">
              <div className="benefit-icon">
                <FaTint />
              </div>
              <div>
                <strong>Find Blood Faster</strong>
                <p>
                  Search for compatible blood donors and
                  available blood resources in real time.
                </p>
              </div>
            </div>

            <div className="benefit">
              <div className="benefit-icon">
                <FaShieldAlt />
              </div>
              <div>
                <strong>Secure Healthcare Platform</strong>
                <p>
                  Your account information and medical requests
                  are strictly safeguarded.
                </p>
              </div>
            </div>
          </div>
        </div>

        <div className="login-left-footer">
          © {new Date().getFullYear()} Blood Response Network. All rights reserved.
        </div>
      </section>

      {/* RIGHT SIDE */}
      <section className="login-right">
        <div className="login-card">
          <Link to="/" className="mobile-logo" style={{ textDecoration: "none" }}>
            <div className="login-brand-icon">
              <FaTint />
            </div>
            <span>
              Blood <strong>Response</strong> Network
            </span>
          </Link>

          <div className="login-header">
            <h2>Sign in to your account</h2>
            <p>Enter your credentials to access your dashboard.</p>
          </div>

          {errorMsg && (
            <div
              style={{
                backgroundColor: "#ffebee",
                color: "#c62828",
                border: "1px solid #ffcdd2",
                padding: "12px 16px",
                borderRadius: "10px",
                fontSize: "13.5px",
                marginBottom: "20px",
                display: "flex",
                alignItems: "center",
                gap: "10px",
              }}
            >
              <FaExclamationCircle />
              <span>{errorMsg}</span>
            </div>
          )}

          {successMsg && (
            <div
              style={{
                backgroundColor: "#e8f5e9",
                color: "#2e7d32",
                border: "1px solid #c8e6c9",
                padding: "12px 16px",
                borderRadius: "10px",
                fontSize: "13.5px",
                marginBottom: "20px",
                display: "flex",
                alignItems: "center",
                gap: "10px",
              }}
            >
              <FaCheckCircle />
              <span>{successMsg}</span>
            </div>
          )}

          <form onSubmit={handleSubmit}>
            {/* EMAIL */}
            <div className="form-group">
              <label htmlFor="email">Email Address</label>
              <div className="input-wrapper">
                <FaEnvelope />
                <input
                  id="email"
                  name="email"
                  type="email"
                  placeholder="you@example.com"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            {/* PASSWORD */}
            <div className="form-group">
              <div className="password-label">
                <label htmlFor="password">Password</label>
                <button
                  type="button"
                  className="forgot-password"
                  onClick={() =>
                    alert("Password recovery service will be available soon.")
                  }
                >
                  Forgot password?
                </button>
              </div>

              <div className="input-wrapper">
                <FaLock />
                <input
                  id="password"
                  name="password"
                  type={showPassword ? "text" : "password"}
                  placeholder="Enter your password"
                  value={formData.password}
                  onChange={handleChange}
                  required
                />
                <button
                  type="button"
                  className="password-toggle"
                  onClick={() => setShowPassword(!showPassword)}
                  aria-label="Show or hide password"
                >
                  {showPassword ? <FaEyeSlash /> : <FaEye />}
                </button>
              </div>
            </div>

            {/* REMEMBER */}
            <div className="login-options">
              <label className="remember-me">
                <input
                  type="checkbox"
                  name="remember"
                  checked={formData.remember}
                  onChange={handleChange}
                />
                <span>Remember me</span>
              </label>
            </div>

            {/* LOGIN BUTTON */}
            <button
              type="submit"
              className="login-submit"
              disabled={loading}
              style={{ opacity: loading ? 0.7 : 1 }}
            >
              {loading ? "Signing in..." : "Sign In"}
              <FaArrowRight />
            </button>
          </form>

          {/* REGISTER */}
          <div className="register-section">
            <span>Don't have an account?</span>
            <Link to="/register" className="create-account">
              <FaUserPlus />
              Create Account
            </Link>
          </div>

          {/* SECURITY */}
          <div className="security-note">
            <FaShieldAlt />
            <span>
              Your information is protected with medical-grade security.
            </span>
          </div>
        </div>
      </section>
    </div>
  );
}

export default Login;