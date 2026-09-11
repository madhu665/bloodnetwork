import React, { useState, useContext } from "react";
import { FaExclamationTriangle, FaHospital, FaPhoneAlt, FaTint, FaCalendarAlt } from "react-icons/fa";
import api from "../services/api";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

function RequestBlood() {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    patientName: "",
    bloodGroup: "O+",
    unitsRequired: 1,
    urgency: "URGENT",
    hospitalName: "",
    hospitalAddress: "",
    city: "",
    requiredDate: "",
    contactPhone: "",
    additionalNotes: "",
  });

  const [status, setStatus] = useState({ loading: false, error: "", success: "" });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!user) {
      navigate("/login");
      return;
    }

    setStatus({ loading: true, error: "", success: "" });
    try {
      // Ensure backend enum format (e.g. O+ to O_POS)
      const submitData = {
        ...formData,
        bloodGroup: formData.bloodGroup.replace("+", "_POS").replace("-", "_NEG"),
      };
      
      const response = await api.post("/blood-requests", submitData);
      setStatus({ loading: false, error: "", success: "Blood request submitted successfully! Donors are being notified." });
      
      // Reset form after success
      setTimeout(() => {
        navigate("/dashboard");
      }, 3000);
      
    } catch (err) {
      setStatus({
        loading: false,
        error: err.response?.data?.message || "Failed to submit request. Please try again.",
        success: ""
      });
    }
  };

  return (
    <div style={{ maxWidth: "800px", margin: "0 auto", padding: "50px 20px" }}>
      <div style={{ textAlign: "center", marginBottom: "35px" }}>
        <div
          style={{
            display: "inline-flex",
            alignItems: "center",
            gap: "8px",
            padding: "8px 18px",
            borderRadius: "30px",
            backgroundColor: "#ffebee",
            color: "#c62828",
            fontSize: "13px",
            fontWeight: "700",
            marginBottom: "14px",
          }}
        >
          <FaExclamationTriangle />
          <span>URGENT MEDICAL DISPATCH</span>
        </div>
        <h1 style={{ fontSize: "36px", fontWeight: "700", color: "#151515" }}>
          Submit Blood <span style={{ color: "#ed1c24" }}>Request</span>
        </h1>
        <p style={{ color: "#666", fontSize: "16px", maxWidth: "580px", margin: "10px auto 0" }}>
          Submit an immediate or scheduled blood requirement to mobilize nearby verified donors and affiliated blood banks.
        </p>
      </div>

      <div
        style={{
          background: "#ffffff",
          padding: "36px 30px",
          borderRadius: "16px",
          border: "1px solid #eeeeee",
          boxShadow: "0 6px 25px rgba(0,0,0,0.06)",
        }}
      >
        {!user && (
          <div style={{ padding: "15px", marginBottom: "20px", background: "#fff3cd", color: "#856404", borderRadius: "8px", textAlign: "center" }}>
            You must be logged in to submit a blood request. <a href="/login" style={{ color: "#ed1c24", fontWeight: "bold" }}>Login here</a>.
          </div>
        )}

        {status.error && (
          <div style={{ padding: "15px", marginBottom: "20px", background: "#f8d7da", color: "#721c24", borderRadius: "8px" }}>
            {status.error}
          </div>
        )}

        {status.success && (
          <div style={{ padding: "15px", marginBottom: "20px", background: "#d4edda", color: "#155724", borderRadius: "8px" }}>
            {status.success}
          </div>
        )}

        <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "20px" }}>
          
          <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "20px" }}>
            <div>
              <label style={{ display: "block", marginBottom: "8px", fontWeight: "600", fontSize: "14px" }}>Patient Name *</label>
              <input type="text" name="patientName" required value={formData.patientName} onChange={handleChange} style={inputStyle} />
            </div>
            <div>
              <label style={{ display: "block", marginBottom: "8px", fontWeight: "600", fontSize: "14px" }}>Blood Group *</label>
              <select name="bloodGroup" value={formData.bloodGroup} onChange={handleChange} style={inputStyle}>
                {["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"].map((bg) => (
                  <option key={bg} value={bg}>{bg}</option>
                ))}
              </select>
            </div>
          </div>

          <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "20px" }}>
            <div>
              <label style={{ display: "block", marginBottom: "8px", fontWeight: "600", fontSize: "14px" }}>Units Required *</label>
              <input type="number" min="1" name="unitsRequired" required value={formData.unitsRequired} onChange={handleChange} style={inputStyle} />
            </div>
            <div>
              <label style={{ display: "block", marginBottom: "8px", fontWeight: "600", fontSize: "14px" }}>Urgency Level *</label>
              <select name="urgency" value={formData.urgency} onChange={handleChange} style={inputStyle}>
                <option value="ROUTINE">Routine</option>
                <option value="URGENT">Urgent</option>
                <option value="CRITICAL">Critical</option>
              </select>
            </div>
          </div>

          <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "20px" }}>
            <div>
              <label style={{ display: "block", marginBottom: "8px", fontWeight: "600", fontSize: "14px" }}><FaHospital /> Hospital Name *</label>
              <input type="text" name="hospitalName" required value={formData.hospitalName} onChange={handleChange} style={inputStyle} />
            </div>
            <div>
              <label style={{ display: "block", marginBottom: "8px", fontWeight: "600", fontSize: "14px" }}>City *</label>
              <input type="text" name="city" required value={formData.city} onChange={handleChange} style={inputStyle} />
            </div>
          </div>

          <div>
            <label style={{ display: "block", marginBottom: "8px", fontWeight: "600", fontSize: "14px" }}>Hospital Address *</label>
            <input type="text" name="hospitalAddress" required value={formData.hospitalAddress} onChange={handleChange} style={inputStyle} />
          </div>

          <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "20px" }}>
            <div>
              <label style={{ display: "block", marginBottom: "8px", fontWeight: "600", fontSize: "14px" }}><FaCalendarAlt /> Required By Date *</label>
              <input type="date" name="requiredDate" required value={formData.requiredDate} onChange={handleChange} style={inputStyle} />
            </div>
            <div>
              <label style={{ display: "block", marginBottom: "8px", fontWeight: "600", fontSize: "14px" }}><FaPhoneAlt /> Contact Phone *</label>
              <input type="text" name="contactPhone" required value={formData.contactPhone} onChange={handleChange} style={inputStyle} />
            </div>
          </div>

          <div>
            <label style={{ display: "block", marginBottom: "8px", fontWeight: "600", fontSize: "14px" }}>Additional Notes</label>
            <textarea name="additionalNotes" rows="3" value={formData.additionalNotes} onChange={handleChange} style={{...inputStyle, resize: "vertical"}}></textarea>
          </div>

          <button
            type="submit"
            disabled={status.loading || !user}
            style={{
              padding: "15px",
              backgroundColor: (status.loading || !user) ? "#ccc" : "#ed1c24",
              color: "white",
              border: "none",
              borderRadius: "8px",
              fontWeight: "bold",
              fontSize: "16px",
              cursor: (status.loading || !user) ? "not-allowed" : "pointer",
              marginTop: "10px"
            }}
          >
            {status.loading ? "Submitting..." : "Submit Blood Request"}
          </button>
        </form>
      </div>
    </div>
  );
}

const inputStyle = {
  width: "100%",
  padding: "12px",
  borderRadius: "8px",
  border: "1px solid #ddd",
  fontSize: "14px",
  boxSizing: "border-box"
};

export default RequestBlood;
