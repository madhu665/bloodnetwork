import React from "react";
import { Link } from "react-router-dom";
import {
  FaTint,
  FaPhoneAlt,
  FaEnvelope,
  FaMapMarkerAlt,
  FaHeart,
  FaShieldAlt,
} from "react-icons/fa";
import "./Footer.css";

function Footer() {
  return (
    <footer className="app-footer">
      <div className="footer-top-container">
        {/* BRAND COLUMN */}
        <div className="footer-brand-col">
          <Link to="/" className="footer-brand">
            <div className="footer-brand-icon">
              <FaTint />
            </div>
            <div className="footer-brand-name">
              Blood <span>Response</span> Network
            </div>
          </Link>
          <p className="footer-tagline">
            A real-time emergency blood network connecting verified blood donors,
            patients in urgent need, certified hospitals, and regional blood banks.
          </p>
          <div className="emergency-contact-pill">
            <FaPhoneAlt className="pill-icon" />
            <div>
              <span className="pill-title">24/7 Emergency Blood Helpline</span>
              <strong className="pill-number">1800-BLOOD-HELP (256-634)</strong>
            </div>
          </div>
        </div>

        {/* PLATFORM LINKS */}
        <div className="footer-nav-col">
          <h4 className="footer-col-title">Platform</h4>
          <ul className="footer-link-list">
            <li>
              <Link to="/donors">Find Donors</Link>
            </li>
            <li>
              <Link to="/request-blood">Request Blood</Link>
            </li>
            <li>
              <Link to="/blood-banks">Blood Banks Directory</Link>
            </li>
            <li>
              <Link to="/about">About Our Mission</Link>
            </li>
            <li>
              <Link to="/contact">Contact Support</Link>
            </li>
          </ul>
        </div>

        {/* ACCOUNT PORTALS */}
        <div className="footer-nav-col">
          <h4 className="footer-col-title">Portals & Access</h4>
          <ul className="footer-link-list">
            <li>
              <Link to="/login">Sign In</Link>
            </li>
            <li>
              <Link to="/register">Donor Registration</Link>
            </li>
            <li>
              <Link to="/register">Hospital Registration</Link>
            </li>
            <li>
              <Link to="/register">Blood Bank Registration</Link>
            </li>
          </ul>
        </div>

        {/* LOCATION & MEDICAL DISCLAIMER */}
        <div className="footer-nav-col">
          <h4 className="footer-col-title">Headquarters</h4>
          <div className="footer-info-item">
            <FaMapMarkerAlt className="info-icon" />
            <span>Healthcare Hub, Hitec City, Hyderabad, India</span>
          </div>
          <div className="footer-info-item">
            <FaEnvelope className="info-icon" />
            <span>emergency@bloodresponsenetwork.org</span>
          </div>
          <div className="footer-safety-badge">
            <FaShieldAlt className="safety-icon" />
            <small>
              All emergency transfusion coordination is verified in strict compliance
              with certified medical standards.
            </small>
          </div>
        </div>
      </div>

      {/* FOOTER BOTTOM */}
      <div className="footer-bottom-container">
        <div className="footer-bottom-content">
          <span>
            © {new Date().getFullYear()} Blood Response Network. All rights reserved.
          </span>
          <span className="footer-heart">
            Saving lives through technology and community compassion{" "}
            <FaHeart className="heart-icon-small" />
          </span>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
