import React from "react";
import { FaPhoneAlt, FaEnvelope, FaMapMarkerAlt, FaClock } from "react-icons/fa";

function Contact() {
  return (
    <div style={{ maxWidth: "1200px", margin: "0 auto", padding: "50px 24px" }}>
      <div style={{ textAlign: "center", marginBottom: "40px" }}>
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
            marginBottom: "14px",
          }}
        >
          <FaPhoneAlt />
          <span>GET IN TOUCH</span>
        </div>
        <h1 style={{ fontSize: "36px", fontWeight: "700", color: "#151515" }}>
          Contact <span style={{ color: "#ed1c24" }}>Support & Emergency</span>
        </h1>
        <p style={{ color: "#666", fontSize: "16px", maxWidth: "600px", margin: "10px auto 0" }}>
          Have an inquiry, technical question, or emergency coordination requirement? Reach out to our team.
        </p>
      </div>

      <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(280px, 1fr))", gap: "24px" }}>
        <div
          style={{
            backgroundColor: "#ffffff",
            padding: "30px 24px",
            borderRadius: "14px",
            border: "1px solid #eeeeee",
            boxShadow: "0 4px 16px rgba(0,0,0,0.04)",
          }}
        >
          <div
            style={{
              width: "48px",
              height: "48px",
              borderRadius: "10px",
              backgroundColor: "#fff0f0",
              color: "#ed1c24",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              fontSize: "20px",
              marginBottom: "16px",
            }}
          >
            <FaPhoneAlt />
          </div>
          <h3 style={{ fontSize: "18px", fontWeight: "700", marginBottom: "6px" }}>Emergency Helpline</h3>
          <p style={{ color: "#666", fontSize: "14px", margin: 0 }}>Toll-Free: 1800-BLOOD-HELP</p>
          <p style={{ color: "#666", fontSize: "14px", margin: 0 }}>Direct: +91 40 2345 6789</p>
        </div>

        <div
          style={{
            backgroundColor: "#ffffff",
            padding: "30px 24px",
            borderRadius: "14px",
            border: "1px solid #eeeeee",
            boxShadow: "0 4px 16px rgba(0,0,0,0.04)",
          }}
        >
          <div
            style={{
              width: "48px",
              height: "48px",
              borderRadius: "10px",
              backgroundColor: "#fff0f0",
              color: "#ed1c24",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              fontSize: "20px",
              marginBottom: "16px",
            }}
          >
            <FaEnvelope />
          </div>
          <h3 style={{ fontSize: "18px", fontWeight: "700", marginBottom: "6px" }}>Email Support</h3>
          <p style={{ color: "#666", fontSize: "14px", margin: 0 }}>emergency@bloodresponsenetwork.org</p>
          <p style={{ color: "#666", fontSize: "14px", margin: 0 }}>support@bloodresponsenetwork.org</p>
        </div>

        <div
          style={{
            backgroundColor: "#ffffff",
            padding: "30px 24px",
            borderRadius: "14px",
            border: "1px solid #eeeeee",
            boxShadow: "0 4px 16px rgba(0,0,0,0.04)",
          }}
        >
          <div
            style={{
              width: "48px",
              height: "48px",
              borderRadius: "10px",
              backgroundColor: "#fff0f0",
              color: "#ed1c24",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              fontSize: "20px",
              marginBottom: "16px",
            }}
          >
            <FaClock />
          </div>
          <h3 style={{ fontSize: "18px", fontWeight: "700", marginBottom: "6px" }}>Operating Hours</h3>
          <p style={{ color: "#666", fontSize: "14px", margin: 0 }}>Emergency Network: 24/7 / 365</p>
          <p style={{ color: "#666", fontSize: "14px", margin: 0 }}>Support Desk: 8:00 AM - 10:00 PM</p>
        </div>
      </div>
    </div>
  );
}

export default Contact;
