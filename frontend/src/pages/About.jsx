import React from "react";
import { FaHeart, FaShieldAlt, FaUsers, FaHospital, FaTint } from "react-icons/fa";

function About() {
  return (
    <div style={{ maxWidth: "1200px", margin: "0 auto", padding: "60px 24px" }}>
      <div style={{ textAlign: "center", marginBottom: "50px" }}>
        <div
          style={{
            display: "inline-flex",
            alignItems: "center",
            gap: "8px",
            padding: "8px 18px",
            borderRadius: "30px",
            backgroundColor: "#fff0f0",
            color: "#ed1c24",
            fontSize: "13px",
            fontWeight: "700",
            marginBottom: "16px",
          }}
        >
          <FaTint />
          <span>ABOUT OUR MISSION</span>
        </div>
        <h1 style={{ fontSize: "38px", fontWeight: "700", color: "#151515", marginBottom: "16px" }}>
          Empowering Communities to <span style={{ color: "#ed1c24" }}>Save Lives</span>
        </h1>
        <p style={{ color: "#666666", fontSize: "17px", maxWidth: "700px", margin: "0 auto", lineHeight: "1.7" }}>
          Blood Response Network is a technology-driven emergency response ecosystem designed
          to bridge the critical gap between blood donors, hospitals, blood banks, and patients in urgent need.
        </p>
      </div>

      {/* MISSION & VISION CARDS */}
      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(320px, 1fr))",
          gap: "30px",
          marginBottom: "60px",
        }}
      >
        <div
          style={{
            backgroundColor: "#ffffff",
            padding: "40px 30px",
            borderRadius: "16px",
            border: "1px solid #eeeeee",
            boxShadow: "0 6px 20px rgba(0,0,0,0.04)",
          }}
        >
          <div
            style={{
              width: "56px",
              height: "56px",
              borderRadius: "12px",
              backgroundColor: "#fff0f0",
              color: "#ed1c24",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              fontSize: "24px",
              marginBottom: "20px",
            }}
          >
            <FaHeart />
          </div>
          <h2 style={{ fontSize: "22px", fontWeight: "700", marginBottom: "14px", color: "#111" }}>
            Our Mission
          </h2>
          <p style={{ color: "#666", fontSize: "15px", lineHeight: "1.7", margin: 0 }}>
            To eliminate preventable deaths caused by blood shortages by providing an instantaneous,
            reliable, and verified digital response channel for patients, hospitals, and voluntary donors.
          </p>
        </div>

        <div
          style={{
            backgroundColor: "#ffffff",
            padding: "40px 30px",
            borderRadius: "16px",
            border: "1px solid #eeeeee",
            boxShadow: "0 6px 20px rgba(0,0,0,0.04)",
          }}
        >
          <div
            style={{
              width: "56px",
              height: "56px",
              borderRadius: "12px",
              backgroundColor: "#fff0f0",
              color: "#ed1c24",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              fontSize: "24px",
              marginBottom: "20px",
            }}
          >
            <FaShieldAlt />
          </div>
          <h2 style={{ fontSize: "22px", fontWeight: "700", marginBottom: "14px", color: "#111" }}>
            Safety & Standards
          </h2>
          <p style={{ color: "#666", fontSize: "15px", lineHeight: "1.7", margin: 0 }}>
            We strictly enforce medical privacy guidelines. Personal contact details are protected,
            and all final transfusion procedures are performed through authorized medical centers and blood banks.
          </p>
        </div>
      </div>
    </div>
  );
}

export default About;
