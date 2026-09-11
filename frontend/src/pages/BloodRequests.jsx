import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  FaExclamationTriangle,
  FaSearch,
  FaMapMarkerAlt,
  FaHospital,
  FaCalendarAlt,
  FaTint,
  FaCheck,
  FaLocationArrow,
  FaPlusCircle
} from "react-icons/fa";
import api from "../services/api";
import { useAuth } from "../context/AuthContext";
import LoadingSpinner from "../components/common/LoadingSpinner";
import StatusBadge from "../components/common/StatusBadge";
import EmptyState from "../components/common/EmptyState";

function BloodRequests() {
  const { user } = useAuth();
  const navigate = useNavigate();

  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [bloodGroupFilter, setBloodGroupFilter] = useState("");
  const [cityFilter, setCityFilter] = useState("");
  const [acceptingId, setAcceptingId] = useState(null);
  const [feedback, setFeedback] = useState({ msg: "", isError: false });

  const fetchRequests = async () => {
    try {
      setLoading(true);
      const params = {};
      if (bloodGroupFilter) params.bloodGroup = bloodGroupFilter.replace("+", "%2B");
      if (cityFilter) params.city = cityFilter;

      const response = await api.get("/blood-requests", { params });
      setRequests(response.data);
    } catch (err) {
      setError("Failed to fetch blood requests. Please check connection.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRequests();
  }, [bloodGroupFilter, cityFilter]);

  const handleAcceptRequest = async (id) => {
    if (!user) {
      navigate("/login");
      return;
    }

    setAcceptingId(id);
    setFeedback({ msg: "", isError: false });

    try {
      await api.put(`/blood-requests/${id}/accept`);
      setFeedback({
        msg: "Request accepted successfully! Live tracking is now active.",
        isError: false,
      });
      // Refresh list
      await fetchRequests();
      // Navigate to tracking
      setTimeout(() => {
        navigate(`/track/${id}`);
      }, 1500);
    } catch (err) {
      setFeedback({
        msg: err.response?.data?.message || "Failed to accept request or already accepted.",
        isError: true,
      });
    } finally {
      setAcceptingId(null);
    }
  };

  return (
    <div style={{ maxWidth: "1200px", margin: "0 auto", padding: "50px 24px" }}>
      {/* HEADER */}
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-end", flexWrap: "wrap", gap: "20px", marginBottom: "35px" }}>
        <div>
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
              marginBottom: "12px",
            }}
          >
            <FaExclamationTriangle />
            <span>EMERGENCY BLOOD DISPATCH</span>
          </div>
          <h1 style={{ fontSize: "36px", fontWeight: "700", color: "#151515", margin: 0 }}>
            Active Blood <span style={{ color: "#ed1c24" }}>Requests</span>
          </h1>
          <p style={{ color: "#666", fontSize: "16px", margin: "8px 0 0" }}>
            Urgent patient transfusion requirements across verified healthcare centers.
          </p>
        </div>

        <Link
          to="/request-blood"
          style={{
            display: "inline-flex",
            alignItems: "center",
            gap: "8px",
            padding: "12px 24px",
            backgroundColor: "#ed1c24",
            color: "#ffffff",
            borderRadius: "8px",
            fontWeight: "600",
            fontSize: "15px",
            textDecoration: "none",
            boxShadow: "0 4px 12px rgba(237, 28, 36, 0.25)",
          }}
        >
          <FaPlusCircle /> Submit New Request
        </Link>
      </div>

      {feedback.msg && (
        <div
          style={{
            padding: "16px",
            borderRadius: "10px",
            marginBottom: "25px",
            backgroundColor: feedback.isError ? "#f8d7da" : "#d4edda",
            color: feedback.isError ? "#721c24" : "#155724",
            fontWeight: "500",
          }}
        >
          {feedback.msg}
        </div>
      )}

      {/* FILTER BAR */}
      <div
        style={{
          background: "#ffffff",
          padding: "20px 24px",
          borderRadius: "14px",
          border: "1px solid #eee",
          boxShadow: "0 4px 15px rgba(0,0,0,0.04)",
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
          gap: "16px",
          marginBottom: "35px",
        }}
      >
        <div>
          <label style={{ fontSize: "13px", fontWeight: "600", color: "#555", marginBottom: "6px", display: "block" }}>
            Filter by Blood Group
          </label>
          <select
            value={bloodGroupFilter}
            onChange={(e) => setBloodGroupFilter(e.target.value)}
            style={{
              width: "100%",
              padding: "10px 14px",
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
          <label style={{ fontSize: "13px", fontWeight: "600", color: "#555", marginBottom: "6px", display: "block" }}>
            Filter by City
          </label>
          <input
            type="text"
            value={cityFilter}
            onChange={(e) => setCityFilter(e.target.value)}
            placeholder="e.g. Hyderabad"
            style={{
              width: "100%",
              padding: "10px 14px",
              borderRadius: "8px",
              border: "1px solid #ccc",
              fontSize: "14px",
            }}
          />
        </div>
      </div>

      {/* REQUESTS LIST */}
      {loading ? (
        <LoadingSpinner text="Fetching real-time blood requests..." />
      ) : error ? (
        <div style={{ textAlign: "center", padding: "40px", color: "red" }}>{error}</div>
      ) : requests.length === 0 ? (
        <EmptyState
          icon={<FaExclamationTriangle />}
          title="No Active Requests"
          message="No blood requests match your selected filters."
        />
      ) : (
        <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill, minmax(360px, 1fr))", gap: "24px" }}>
          {requests.map((req) => {
            const hasDonor = req.status === "DONOR_FOUND" || req.status === "COMPLETED";
            const urgencyColor =
              req.urgency === "CRITICAL" ? "#c62828" : req.urgency === "URGENT" ? "#e65100" : "#2e7d32";

            return (
              <div
                key={req.id}
                style={{
                  background: "#ffffff",
                  borderRadius: "14px",
                  border: "1px solid #eee",
                  padding: "24px",
                  boxShadow: "0 4px 15px rgba(0,0,0,0.04)",
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "space-between",
                }}
              >
                <div>
                  <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start", marginBottom: "14px" }}>
                    <div>
                      <span
                        style={{
                          display: "inline-block",
                          padding: "3px 10px",
                          borderRadius: "15px",
                          backgroundColor: `${urgencyColor}15`,
                          color: urgencyColor,
                          fontSize: "11px",
                          fontWeight: "700",
                          textTransform: "uppercase",
                          marginBottom: "8px",
                        }}
                      >
                        {req.urgency} REQUIREMENT
                      </span>
                      <h3 style={{ margin: "0 0 4px 0", fontSize: "19px", color: "#151515" }}>
                        Patient: {req.patientName}
                      </h3>
                      <div style={{ fontSize: "12px", color: "#777" }}>
                        Request ID: #{req.id} • Posted: {new Date(req.createdAt).toLocaleDateString()}
                      </div>
                    </div>

                    <div
                      style={{
                        width: "52px",
                        height: "52px",
                        borderRadius: "50%",
                        backgroundColor: "#fff0f0",
                        color: "#ed1c24",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        fontSize: "20px",
                        fontWeight: "800",
                        boxShadow: "0 2px 8px rgba(237, 28, 36, 0.15)",
                      }}
                    >
                      {req.bloodGroup?.replace("_POS", "+")?.replace("_NEG", "-")}
                    </div>
                  </div>

                  <div style={{ display: "flex", flexDirection: "column", gap: "10px", margin: "16px 0", fontSize: "14px", color: "#444" }}>
                    <div style={{ display: "flex", alignItems: "flex-start", gap: "8px" }}>
                      <FaHospital style={{ color: "#ed1c24", marginTop: "3px" }} />
                      <div>
                        <strong>{req.hospitalName}</strong>
                        <div style={{ color: "#666", fontSize: "12px" }}>
                          {req.hospitalAddress}, {req.city}
                        </div>
                      </div>
                    </div>

                    <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
                      <FaTint style={{ color: "#ed1c24" }} />
                      <span>Units Required: <strong>{req.unitsRequired} Units</strong></span>
                    </div>

                    <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
                      <FaCalendarAlt style={{ color: "#555" }} />
                      <span>Required By: <strong>{req.requiredDate}</strong></span>
                    </div>

                    {req.additionalNotes && (
                      <div style={{ padding: "10px", background: "#fdf8f8", borderRadius: "8px", fontSize: "12px", color: "#555", fontStyle: "italic" }}>
                        "{req.additionalNotes}"
                      </div>
                    )}
                  </div>
                </div>

                <div style={{ paddingTop: "16px", borderTop: "1px solid #eee", display: "flex", flexDirection: "column", gap: "10px" }}>
                  <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                    <StatusBadge
                      status={req.status}
                      type={hasDonor ? "success" : req.status === "MATCHING" ? "warning" : "info"}
                    />
                    <span style={{ fontSize: "12px", color: "#777" }}>
                      {req.matchedDonorCount} Donors Matched
                    </span>
                  </div>

                  {/* LIVE GOOGLE MAPS TRACK BUTTON */}
                  {hasDonor ? (
                    <Link
                      to={`/track/${req.id}`}
                      style={{
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        gap: "8px",
                        padding: "12px",
                        backgroundColor: "#28a745",
                        color: "#ffffff",
                        borderRadius: "8px",
                        fontWeight: "700",
                        fontSize: "14px",
                        textDecoration: "none",
                        boxShadow: "0 3px 10px rgba(40, 167, 69, 0.2)",
                      }}
                    >
                      <FaLocationArrow /> Track Donor on Google Maps
                    </Link>
                  ) : (
                    <button
                      onClick={() => handleAcceptRequest(req.id)}
                      disabled={acceptingId === req.id}
                      style={{
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        gap: "8px",
                        padding: "12px",
                        backgroundColor: "#ed1c24",
                        color: "#ffffff",
                        border: "none",
                        borderRadius: "8px",
                        fontWeight: "700",
                        fontSize: "14px",
                        cursor: "pointer",
                        boxShadow: "0 3px 10px rgba(237, 28, 36, 0.2)",
                      }}
                    >
                      <FaCheck /> {acceptingId === req.id ? "Accepting..." : "Accept Request (As Donor)"}
                    </button>
                  )}
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}

export default BloodRequests;
