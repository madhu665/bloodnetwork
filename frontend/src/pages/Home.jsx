import React from "react";
import { Link } from "react-router-dom";
import {
  FaTint,
  FaUsers,
  FaHospital,
  FaHeart,
  FaUserPlus,
  FaSearch,
  FaBell,
  FaHandHoldingHeart,
  FaArrowRight,
  FaMapMarkerAlt,
  FaUser,
  FaHospitalAlt,
} from "react-icons/fa";
import "./Home.css";

function Home() {
  return (
    <div className="home-page">
      {/* ================= HERO ================= */}
      <section className="hero">
        <div className="hero-container">
          {/* LEFT CONTENT */}
          <div className="hero-content">
            <div className="emergency-badge">
              <span className="status-dot"></span>
              Emergency blood support available
            </div>

            <h1>
              Donate Blood,
              <br />
              <span>Save Lives</span>
            </h1>

            <p>
              Join our mission to connect blood donors, hospitals,
              blood banks, and people in need. Your one donation
              can become someone's second chance at life.
            </p>

            <div className="hero-buttons">
              <Link to="/donors" className="primary-button">
                <FaTint />
                Donate Now
                <FaArrowRight />
              </Link>

              <Link to="/request-blood" className="secondary-button">
                <FaSearch />
                Request Blood
              </Link>

              <Link to="/track" className="secondary-button" style={{ backgroundColor: "#28a745", color: "#ffffff", borderColor: "#28a745" }}>
                <FaMapMarkerAlt />
                Track Donor
              </Link>
            </div>

            <div className="location-info">
              <FaMapMarkerAlt />
              <span>Connecting blood donors near you</span>
            </div>
          </div>

          {/* RIGHT VISUAL */}
          <div className="hero-visual">
            <div className="hero-circle">
              <div className="blood-drop">
                <FaTint />
                <div className="plus-symbol">+</div>
              </div>
            </div>

            {/* ACTIVE DONORS CARD */}
            <div className="floating-card donors-card">
              <div className="floating-icon">
                <FaUsers />
              </div>
              <div>
                <strong>8,750+</strong>
                <span>Active Donors</span>
              </div>
            </div>

            {/* LIVES SAVED CARD */}
            <div className="floating-card lives-card">
              <div className="floating-icon heart-icon">
                <FaHeart />
              </div>
              <div>
                <strong>25,000+</strong>
                <span>Lives Saved</span>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* ================= STATISTICS ================= */}
      <section className="statistics">
        <div className="statistics-container">
          <div className="stat-item">
            <FaTint className="stat-icon" />
            <div>
              <strong>12,540+</strong>
              <span>Units Donated</span>
            </div>
          </div>

          <div className="stat-item">
            <FaUsers className="stat-icon" />
            <div>
              <strong>8,750+</strong>
              <span>Registered Donors</span>
            </div>
          </div>

          <div className="stat-item">
            <FaHospital className="stat-icon" />
            <div>
              <strong>1,250+</strong>
              <span>Hospitals Connected</span>
            </div>
          </div>

          <div className="stat-item">
            <FaHeart className="stat-icon" />
            <div>
              <strong>25,000+</strong>
              <span>Lives Saved</span>
            </div>
          </div>
        </div>
      </section>

      {/* ================= HOW IT WORKS ================= */}
      <section className="how-section" id="how-it-works">
        <div className="section-heading">
          <div className="section-label">
            <span></span>
            HOW IT WORKS
          </div>

          <h2>
            We Make Blood Donation
            <span> Easy</span>
          </h2>

          <div className="heading-line"></div>

          <p>
            A simple and reliable way to connect people who can
            donate with people who urgently need blood.
          </p>
        </div>

        <div className="steps-container">
          {/* STEP 1 */}
          <div className="step-card">
            <div className="step-icon">
              <FaUserPlus />
            </div>
            <div className="step-number">01</div>
            <h3>Register</h3>
            <p>
              Create your account as a donor, patient,
              hospital, or blood bank.
            </p>
          </div>

          {/* STEP 2 */}
          <div className="step-card">
            <div className="step-icon">
              <FaSearch />
            </div>
            <div className="step-number">02</div>
            <h3>Search / Request</h3>
            <p>
              Search for compatible blood donors or
              submit an emergency blood request.
            </p>
          </div>

          {/* STEP 3 */}
          <div className="step-card">
            <div className="step-icon">
              <FaBell />
            </div>
            <div className="step-number">03</div>
            <h3>Get Notified</h3>
            <p>
              Nearby eligible donors receive notifications
              when their blood type is needed.
            </p>
          </div>

          {/* STEP 4 */}
          <div className="step-card">
            <div className="step-icon">
              <FaHandHoldingHeart />
            </div>
            <div className="step-number">04</div>
            <h3>Save Lives</h3>
            <p>
              Connect, donate blood, and help someone
              get the treatment they urgently need.
            </p>
          </div>
        </div>
      </section>

      {/* ================= WHO CAN USE ================= */}
      <section className="users-section" id="ecosystem">
        <div className="section-heading">
          <div className="section-label">
            <span></span>
            WHO CAN USE
          </div>

          <h2>
            Built for <span>Everyone</span>
          </h2>

          <div className="heading-line"></div>

          <p>
            One platform connecting the complete blood
            donation ecosystem.
          </p>
        </div>

        <div className="user-cards">
          {/* DONOR */}
          <div className="user-card">
            <div className="user-icon">
              <FaUsers />
            </div>
            <h3>Blood Donors</h3>
            <p>
              Register your blood group, location,
              availability, and help patients nearby.
            </p>
            <Link to="/register">
              Become a Donor
              <FaArrowRight />
            </Link>
          </div>

          {/* PATIENT */}
          <div className="user-card">
            <div className="user-icon">
              <FaUser />
            </div>
            <h3>Patients</h3>
            <p>
              Request blood during emergencies and
              find compatible donors quickly.
            </p>
            <Link to="/request-blood">
              Request Blood
              <FaArrowRight />
            </Link>
          </div>

          {/* HOSPITAL */}
          <div className="user-card">
            <div className="user-icon">
              <FaHospitalAlt />
            </div>
            <h3>Hospitals</h3>
            <p>
              Manage blood requests, connect with donors,
              and coordinate emergency requirements.
            </p>
            <Link to="/register">
              Register Hospital
              <FaArrowRight />
            </Link>
          </div>

          {/* BLOOD BANK */}
          <div className="user-card">
            <div className="user-icon">
              <FaTint />
            </div>
            <h3>Blood Banks</h3>
            <p>
              Maintain blood inventory and share
              availability with hospitals and patients.
            </p>
            <Link to="/blood-banks">
              Manage Inventory
              <FaArrowRight />
            </Link>
          </div>
        </div>
      </section>

      {/* ================= EMERGENCY CTA ================= */}
      <section className="emergency-section" id="emergency-cta">
        <div className="emergency-container">
          <div>
            <div className="emergency-small">EMERGENCY BLOOD REQUEST</div>
            <h2>
              Someone needs blood
              <br />
              <span>right now.</span>
            </h2>
            <p>
              Submit an urgent blood request and connect
              with compatible donors in your area.
            </p>
          </div>

          <div className="emergency-actions">
            <Link to="/request-blood" className="emergency-button">
              Request Blood
              <FaArrowRight />
            </Link>
            <Link to="/donors" className="outline-button">
              Donate Blood
            </Link>
          </div>
        </div>
      </section>
    </div>
  );
}

export default Home;