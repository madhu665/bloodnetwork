import React, { useState, useEffect } from "react";
import { FaHospitalAlt, FaSearch, FaMapMarkerAlt, FaPhoneAlt } from "react-icons/fa";
import api from "../services/api";
import LoadingSpinner from "../components/common/LoadingSpinner";
import EmptyState from "../components/common/EmptyState";
import { Link } from "react-router-dom";

function BloodBanks() {
  const [city, setCity] = useState("");
  const [bloodBanks, setBloodBanks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const fetchBloodBanks = async () => {
    setLoading(true);
    setError("");
    try {
      const params = {};
      if (city) params.city = city;
      const response = await api.get("/blood-banks", { params });
      setBloodBanks(response.data);
    } catch (err) {
      setError("Failed to load blood banks.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBloodBanks();
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    fetchBloodBanks();
  };

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
          <FaHospitalAlt />
          <span>REGIONAL BLOOD BANKS</span>
        </div>
        <h1 style={{ fontSize: "36px", fontWeight: "700", color: "#151515" }}>
          Blood Bank <span style={{ color: "#ed1c24" }}>Directory</span>
        </h1>
        <p style={{ color: "#666", fontSize: "16px", maxWidth: "600px", margin: "10px auto 0" }}>
          Locate certified government and private blood banks, check live inventory availability, and connect directly.
        </p>
      </div>

      <form
        onSubmit={handleSearch}
        style={{
          background: "#ffffff",
          padding: "24px",
          borderRadius: "16px",
          border: "1px solid #eeeeee",
          boxShadow: "0 6px 20px rgba(0,0,0,0.05)",
          marginBottom: "40px",
          display: "flex",
          gap: "16px",
          maxWidth: "600px",
          margin: "0 auto 40px auto"
        }}
      >
        <div style={{ flex: 1 }}>
          <input
            type="text"
            value={city}
            onChange={(e) => setCity(e.target.value)}
            placeholder="Search by City (e.g. Hyderabad)"
            style={{
              width: "100%",
              padding: "12px 14px",
              borderRadius: "8px",
              border: "1px solid #ccc",
              fontSize: "14px",
            }}
          />
        </div>
        <button
          type="submit"
          style={{
            padding: "12px 24px",
            backgroundColor: "#ed1c24",
            color: "#ffffff",
            border: "none",
            borderRadius: "8px",
            fontWeight: "600",
            fontSize: "14px",
            cursor: "pointer",
            display: "flex",
            alignItems: "center",
            gap: "8px",
          }}
        >
          <FaSearch /> Search
        </button>
      </form>

      {loading ? (
        <LoadingSpinner text="Loading blood banks..." />
      ) : error ? (
        <div style={{ padding: "20px", background: "#fee", color: "red", borderRadius: "8px", textAlign: "center" }}>
          {error}
        </div>
      ) : bloodBanks.length === 0 ? (
        <EmptyState
          icon={<FaHospitalAlt />}
          title="No Blood Banks Found"
          message="We couldn't find any blood banks matching your search."
        />
      ) : (
        <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill, minmax(320px, 1fr))", gap: "24px" }}>
          {bloodBanks.map((bank) => (
            <div
              key={bank.id}
              style={{
                background: "#fff",
                border: "1px solid #eee",
                borderRadius: "12px",
                padding: "24px",
                boxShadow: "0 2px 8px rgba(0,0,0,0.04)",
              }}
            >
              <h3 style={{ margin: "0 0 10px 0", fontSize: "18px", color: "#222" }}>{bank.bloodBankName}</h3>
              
              <div style={{ display: "flex", flexDirection: "column", gap: "8px", marginBottom: "20px" }}>
                <div style={{ display: "flex", alignItems: "flex-start", gap: "8px", fontSize: "14px", color: "#555" }}>
                  <FaMapMarkerAlt style={{ color: "#ed1c24", marginTop: "3px" }} /> 
                  <span>{bank.address}<br/>{bank.city}</span>
                </div>
                <div style={{ display: "flex", alignItems: "center", gap: "8px", fontSize: "14px", color: "#555" }}>
                  <FaPhoneAlt style={{ color: "#ed1c24" }} /> {bank.phone}
                </div>
              </div>

              <Link
                to={`/blood-banks/${bank.id}`}
                style={{
                  display: "block",
                  textAlign: "center",
                  padding: "10px",
                  background: "#f8f9fa",
                  color: "#333",
                  textDecoration: "none",
                  borderRadius: "6px",
                  fontWeight: "600",
                  fontSize: "14px",
                  border: "1px solid #ddd"
                }}
              >
                View Live Inventory
              </Link>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default BloodBanks;
