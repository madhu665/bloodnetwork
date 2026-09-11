import React, { useState, useEffect } from "react";
import { FaSearch, FaMapMarkerAlt, FaTint, FaCalendarCheck } from "react-icons/fa";
import api from "../services/api";
import StatusBadge from "../components/common/StatusBadge";
import LoadingSpinner from "../components/common/LoadingSpinner";
import EmptyState from "../components/common/EmptyState";

function FindDonors() {
  const [bloodGroup, setBloodGroup] = useState("");
  const [city, setCity] = useState("");
  const [donors, setDonors] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const fetchDonors = async () => {
    setLoading(true);
    setError("");
    try {
      const params = {};
      if (bloodGroup) params.bloodGroup = bloodGroup.replace("+", "%2B");
      if (city) params.city = city;

      const response = await api.get("/donors/search", { params });
      setDonors(response.data);
    } catch (err) {
      setError("Failed to load donors. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDonors();
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    fetchDonors();
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
          <FaSearch />
          <span>DONOR REGISTRY</span>
        </div>
        <h1 style={{ fontSize: "36px", fontWeight: "700", color: "#151515" }}>
          Find Compatible <span style={{ color: "#ed1c24" }}>Blood Donors</span>
        </h1>
        <p style={{ color: "#666", fontSize: "16px", maxWidth: "600px", margin: "10px auto 0" }}>
          Search for eligible, verified voluntary donors in your locality by blood group and city.
        </p>
      </div>

      {/* SEARCH CARD */}
      <form
        onSubmit={handleSearch}
        style={{
          background: "#ffffff",
          padding: "24px",
          borderRadius: "16px",
          border: "1px solid #eeeeee",
          boxShadow: "0 6px 20px rgba(0,0,0,0.05)",
          marginBottom: "40px",
        }}
      >
        <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))", gap: "16px" }}>
          <div>
            <label style={{ fontSize: "13px", fontWeight: "600", color: "#444", marginBottom: "6px", display: "block" }}>
              Blood Group
            </label>
            <select
              value={bloodGroup}
              onChange={(e) => setBloodGroup(e.target.value)}
              style={{
                width: "100%",
                padding: "12px 14px",
                borderRadius: "8px",
                border: "1px solid #ccc",
                fontSize: "14px",
              }}
            >
              <option value="">All Blood Groups</option>
              {["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"].map((bg) => (
                <option key={bg} value={bg}>{bg}</option>
              ))}
            </select>
          </div>

          <div>
            <label style={{ fontSize: "13px", fontWeight: "600", color: "#444", marginBottom: "6px", display: "block" }}>
              City / Location
            </label>
            <input
              type="text"
              value={city}
              onChange={(e) => setCity(e.target.value)}
              placeholder="e.g. Hyderabad, Mumbai, Delhi"
              style={{
                width: "100%",
                padding: "12px 14px",
                borderRadius: "8px",
                border: "1px solid #ccc",
                fontSize: "14px",
              }}
            />
          </div>

          <div style={{ display: "flex", alignItems: "flex-end" }}>
            <button
              type="submit"
              style={{
                width: "100%",
                padding: "12px 20px",
                backgroundColor: "#ed1c24",
                color: "#ffffff",
                border: "none",
                borderRadius: "8px",
                fontWeight: "600",
                fontSize: "14px",
                cursor: "pointer",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                gap: "8px",
              }}
            >
              <FaSearch /> Search Donors
            </button>
          </div>
        </div>
      </form>

      {/* RESULTS SECTION */}
      {loading ? (
        <LoadingSpinner text="Searching for donors..." />
      ) : error ? (
        <div style={{ padding: "20px", background: "#fee", color: "red", borderRadius: "8px", textAlign: "center" }}>
          {error}
        </div>
      ) : donors.length === 0 ? (
        <EmptyState
          icon={<FaSearch />}
          title="No donors found"
          message="Try adjusting your filters to see more results."
        />
      ) : (
        <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill, minmax(300px, 1fr))", gap: "20px" }}>
          {donors.map((donor) => (
            <div
              key={donor.id}
              style={{
                background: "#fff",
                border: "1px solid #eee",
                borderRadius: "12px",
                padding: "20px",
                boxShadow: "0 2px 8px rgba(0,0,0,0.04)",
              }}
            >
              <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start", marginBottom: "15px" }}>
                <div>
                  <h3 style={{ margin: "0 0 5px 0", fontSize: "18px", color: "#222" }}>{donor.fullName}</h3>
                  <p style={{ margin: 0, fontSize: "13px", color: "#777", display: "flex", alignItems: "center", gap: "4px" }}>
                    <FaMapMarkerAlt /> {donor.city}
                  </p>
                </div>
                <div
                  style={{
                    background: "#fff0f0",
                    color: "#ed1c24",
                    fontWeight: "bold",
                    fontSize: "16px",
                    width: "45px",
                    height: "45px",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    borderRadius: "50%",
                  }}
                >
                  {donor.bloodGroup.replace("_POS", "+").replace("_NEG", "-")}
                </div>
              </div>

              <div style={{ display: "flex", flexDirection: "column", gap: "8px", marginBottom: "15px" }}>
                <div style={{ display: "flex", alignItems: "center", gap: "8px", fontSize: "14px", color: "#555" }}>
                  <FaTint style={{ color: "#ed1c24" }} /> Total Donations: {donor.totalDonations}
                </div>
                <div style={{ display: "flex", alignItems: "center", gap: "8px", fontSize: "14px", color: "#555" }}>
                  <FaCalendarCheck style={{ color: donor.eligible ? "green" : "orange" }} />
                  Status: {donor.eligible ? "Eligible to Donate" : `Eligible from ${donor.nextEligibleDate}`}
                </div>
              </div>

              <div style={{ paddingTop: "15px", borderTop: "1px solid #eee" }}>
                <StatusBadge
                  status={donor.isAvailable ? "Available" : "Unavailable"}
                  type={donor.isAvailable ? "success" : "warning"}
                />
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default FindDonors;
