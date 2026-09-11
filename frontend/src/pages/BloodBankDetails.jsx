import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { FaHospitalAlt, FaMapMarkerAlt, FaPhoneAlt, FaArrowLeft, FaTint } from "react-icons/fa";
import api from "../services/api";
import LoadingSpinner from "../components/common/LoadingSpinner";
import StatusBadge from "../components/common/StatusBadge";

function BloodBankDetails() {
  const { id } = useParams();
  const [bloodBank, setBloodBank] = useState(null);
  const [inventory, setInventory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchDetails = async () => {
      try {
        setLoading(true);
        // Fetch blood bank info
        const bankRes = await api.get(`/blood-banks/${id}`);
        setBloodBank(bankRes.data);

        // Fetch live inventory
        const invRes = await api.get(`/blood-banks/${id}/inventory`);
        setInventory(invRes.data);
      } catch (err) {
        setError("Failed to load blood bank details.");
      } finally {
        setLoading(false);
      }
    };

    fetchDetails();
  }, [id]);

  if (loading) return <div style={{ padding: "100px 0" }}><LoadingSpinner text="Loading Live Inventory..." /></div>;
  if (error) return <div style={{ padding: "50px", textAlign: "center", color: "red" }}>{error}</div>;
  if (!bloodBank) return null;

  return (
    <div style={{ maxWidth: "1000px", margin: "0 auto", padding: "40px 24px" }}>
      <Link to="/blood-banks" style={{ display: "inline-flex", alignItems: "center", gap: "8px", color: "#666", textDecoration: "none", marginBottom: "20px", fontWeight: "600" }}>
        <FaArrowLeft /> Back to Directory
      </Link>

      <div style={{ background: "#fff", padding: "30px", borderRadius: "16px", border: "1px solid #eee", boxShadow: "0 4px 15px rgba(0,0,0,0.05)", marginBottom: "30px" }}>
        <h1 style={{ margin: "0 0 15px 0", fontSize: "28px", color: "#151515", display: "flex", alignItems: "center", gap: "10px" }}>
          <FaHospitalAlt style={{ color: "#ed1c24" }} />
          {bloodBank.bloodBankName}
        </h1>
        
        <div style={{ display: "flex", flexWrap: "wrap", gap: "20px", color: "#555", fontSize: "15px" }}>
          <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
            <FaMapMarkerAlt style={{ color: "#ed1c24" }} /> {bloodBank.address}, {bloodBank.city}
          </div>
          <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
            <FaPhoneAlt style={{ color: "#ed1c24" }} /> {bloodBank.phone}
          </div>
        </div>
      </div>

      <h2 style={{ fontSize: "22px", marginBottom: "20px", color: "#333" }}>Live Blood Inventory</h2>

      <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill, minmax(200px, 1fr))", gap: "20px" }}>
        {inventory.map((item) => (
          <div key={item.bloodGroup} style={{ background: "#fff", padding: "20px", borderRadius: "12px", border: "1px solid #eee", textAlign: "center", boxShadow: "0 2px 8px rgba(0,0,0,0.03)" }}>
            <div style={{ fontSize: "28px", fontWeight: "bold", color: "#ed1c24", marginBottom: "10px" }}>
              {item.bloodGroup.replace("_POS", "+").replace("_NEG", "-")}
            </div>
            
            <div style={{ fontSize: "36px", fontWeight: "800", color: "#222", marginBottom: "15px", display: "flex", justifyContent: "center", alignItems: "center", gap: "5px" }}>
              {item.unitsAvailable} <span style={{ fontSize: "14px", fontWeight: "normal", color: "#777" }}>Units</span>
            </div>

            <StatusBadge 
              status={item.unitsAvailable > 10 ? "Optimal" : item.unitsAvailable > 0 ? "Low Stock" : "Out of Stock"} 
              type={item.unitsAvailable > 10 ? "success" : item.unitsAvailable > 0 ? "warning" : "danger"} 
            />
            
            <div style={{ fontSize: "11px", color: "#999", marginTop: "15px" }}>
              Last updated: {new Date(item.lastUpdated).toLocaleString()}
            </div>
          </div>
        ))}
        {inventory.length === 0 && (
          <div style={{ gridColumn: "1 / -1", padding: "30px", textAlign: "center", color: "#777", background: "#f9f9f9", borderRadius: "12px" }}>
            No inventory data available for this blood bank.
          </div>
        )}
      </div>
    </div>
  );
}

export default BloodBankDetails;
