import React, { useState, useContext } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import {
  FaTint,
  FaBars,
  FaTimes,
  FaSearch,
  FaExclamationCircle,
  FaSignInAlt,
  FaUserPlus,
  FaSignOutAlt,
  FaUserCircle
} from "react-icons/fa";
import { AuthContext } from "../context/AuthContext";
import NotificationBell from "./NotificationBell";
import "./Navbar.css";

function Navbar() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const closeMenu = () => setMobileMenuOpen(false);

  const handleLogout = () => {
    logout();
    closeMenu();
    navigate("/");
  };

  return (
    <header className="app-navbar">
      <div className="navbar-container">
        {/* BRAND LOGO */}
        <Link to="/" className="navbar-brand" onClick={closeMenu}>
          <div className="navbar-brand-icon">
            <FaTint />
          </div>
          <div className="navbar-brand-text">
            Blood <span>Response</span> Network
          </div>
        </Link>

        {/* DESKTOP NAVIGATION LINKS */}
        <nav className="desktop-nav-links">
          <NavLink to="/" end className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>Home</NavLink>
          <NavLink to="/about" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>About Us</NavLink>
          <NavLink to="/donors" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>Find Donors</NavLink>
          <NavLink to="/requests" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>Requests</NavLink>
          <NavLink to="/track" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"} style={{ color: "#ed1c24", fontWeight: "700" }}>
            📍 Track Donor
          </NavLink>
          <NavLink to="/blood-banks" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>Blood Banks</NavLink>
          <NavLink to="/contact" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>Contact</NavLink>
        </nav>

        {/* AUTH ACTIONS */}
        <div className="navbar-actions">
          {user && <NotificationBell />}
          
          {user ? (
            <>
              <Link to="/dashboard" className="btn-nav-login" style={{ background: "transparent", border: "1px solid #ccc", color: "#333" }}>
                <FaUserCircle className="btn-icon" /> Dashboard
              </Link>
              <button onClick={handleLogout} className="btn-nav-register" style={{ background: "#333", border: "none" }}>
                <FaSignOutAlt className="btn-icon" /> Logout
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="btn-nav-login">
                <FaSignInAlt className="btn-icon" /> Login
              </Link>
              <Link to="/register" className="btn-nav-register">
                <FaUserPlus className="btn-icon" /> Register
              </Link>
            </>
          )}

          {/* MOBILE TOGGLE BUTTON */}
          <button
            className="mobile-toggle-btn"
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            aria-label="Toggle Navigation Menu"
          >
            {mobileMenuOpen ? <FaTimes /> : <FaBars />}
          </button>
        </div>
      </div>

      {/* MOBILE MENU DRAWER */}
      {mobileMenuOpen && (
        <div className="mobile-nav-drawer">
          <nav className="mobile-nav-links">
            <NavLink to="/" end className={({ isActive }) => isActive ? "mobile-item active" : "mobile-item"} onClick={closeMenu}>Home</NavLink>
            <NavLink to="/about" className={({ isActive }) => isActive ? "mobile-item active" : "mobile-item"} onClick={closeMenu}>About Us</NavLink>
            <NavLink to="/donors" className={({ isActive }) => isActive ? "mobile-item active" : "mobile-item"} onClick={closeMenu}>Find Donors</NavLink>
            <NavLink to="/requests" className={({ isActive }) => isActive ? "mobile-item active" : "mobile-item"} onClick={closeMenu}>Requests</NavLink>
            <NavLink to="/track" className={({ isActive }) => isActive ? "mobile-item active" : "mobile-item"} onClick={closeMenu} style={{ color: "#ed1c24", fontWeight: "700" }}>📍 Track Donor</NavLink>
            <NavLink to="/request-blood" className={({ isActive }) => isActive ? "mobile-item active" : "mobile-item"} onClick={closeMenu}>Request Blood</NavLink>
            <NavLink to="/blood-banks" className={({ isActive }) => isActive ? "mobile-item active" : "mobile-item"} onClick={closeMenu}>Blood Banks</NavLink>
            <NavLink to="/contact" className={({ isActive }) => isActive ? "mobile-item active" : "mobile-item"} onClick={closeMenu}>Contact</NavLink>

            <div className="mobile-auth-buttons">
              {user ? (
                <>
                  <Link to="/dashboard" className="btn-nav-login w-100 text-center" onClick={closeMenu}>Dashboard</Link>
                  <button onClick={handleLogout} className="btn-nav-register w-100 text-center" style={{ background: "#333", border: "none" }}>Logout</button>
                </>
              ) : (
                <>
                  <Link to="/login" className="btn-nav-login w-100 text-center" onClick={closeMenu}>Login</Link>
                  <Link to="/register" className="btn-nav-register w-100 text-center" onClick={closeMenu}>Register</Link>
                </>
              )}
            </div>
          </nav>
        </div>
      )}
    </header>
  );
}

export default Navbar;
