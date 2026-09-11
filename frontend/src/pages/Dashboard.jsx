import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  FaUserCircle,
  FaTint,
  FaHeartbeat,
  FaCalendarCheck,
  FaLocationArrow,
  FaHospital,
  FaShieldAlt,
  FaExchangeAlt,
  FaPlusCircle,
  FaCheck,
  FaTimes,
  FaSyncAlt,
  FaEye,
  FaMapMarkerAlt
} from "react-icons/fa";
import api from "../services/api";
import { useAuth } from "../context/AuthContext";
import LoadingSpinner from "../components/common/LoadingSpinner";
import StatusBadge from "../components/common/StatusBadge";
import EmptyState from "../components/common/EmptyState";

function Dashboard() {
  const { user } = useAuth();
  const navigate = useNavigate();

  const [donorStats, setDonorStats] = useState(null);
  const [donorProfile, setDonorProfile] = useState(null);
  const [myRequests, setMyRequests] = useState([]);
  const [adminStats, setAdminStats] = useState(null);
  const [adminUsers, setAdminUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [isBroadcastingLocation, setIsBroadcastingLocation] = useState(false);
  const [gpsStatus, setGpsStatus] = useState("GPS Standby");

  useEffect(() => {
    if (!user) {
      navigate("/login");
      return;
    }

    const loadDashboardData = async () => {
      try {
        setLoading(true);
        if (user.role === "ROLE_DONOR") {
          const statsRes = await api.get("/donors/me/dashboard");
          setDonorStats(statsRes.data);
          const profRes = await api.get("/donors/me");
          setDonorProfile(profRes.data);
        } else if (user.role === "ROLE_HOSPITAL" || user.role === "ROLE_PATIENT") {
          const reqRes = await api.get("/blood-requests/me");
          setMyRequests(reqRes.data);
        } else if (user.role === "ROLE_ADMIN") {
          const adminStatsRes = await api.get("/admin/stats");
          setAdminStats(adminStatsRes.data);
          const usersRes = await api.get("/admin/users");
          setAdminUsers(usersRes.data);
        }
      } catch (err) {
        console.warn("Dashboard data load error:", err);
        setError("Unable to load full dashboard metrics.");
      } finally {
        setLoading(false);
      }
    };

    loadDashboardData();
  }, [user]);

  // Toggle availability for donor
  const handleToggleAvailability = async () => {
    if (!donorStats) return;
    const newStatus = !donorStats.isAvailable;
    try {
      await api.put("/donors/me/availability", { isAvailable: newStatus });
      setDonorStats({ ...donorStats, isAvailable: newStatus });
    } catch (err) {
      console.error("Failed to toggle availability", err);
    }
  };

  // Broadcast GPS location
  const toggleLocationBroadcast = () => {
    if (isBroadcastingLocation) {
      setIsBroadcastingLocation(false);
      setGpsStatus("Location broadcast stopped.");
      return;
    }

    if (!("geolocation" in navigator)) {
      setGpsStatus("Geolocation not supported by this browser.");
      return;
    }

    setIsBroadcastingLocation(true);
    setGpsStatus("Broadcasting live GPS coordinates...");

    navigator.geolocation.getCurrentPosition(
      async (pos) => {
        try {
          await api.put("/donors/me/location", {
            latitude: pos.coords.latitude,
            longitude: pos.coords.longitude,
          });
          setGpsStatus(`Coordinates shared: ${pos.coords.latitude.toFixed(4)}, ${pos.coords.longitude.toFixed(4)}`);
        } catch (err) {
          setGpsStatus("Failed to send GPS coordinates to server.");
        }
      },
      (err) => {
        setGpsStatus(`GPS error: ${err.message}. Using default coordinates.`);
        // Fallback demo coordinates
        api.put("/donors/me/location", {
          latitude: 17.4321,
          longitude: 78.4321,
        });
      }
    );
  };

  if (!user) return null;

  return (
    <div style={{ maxWidth: "1200px", margin: "0 auto", padding: "40px 24px" }}>
      {/* USER PROFILE SUMMARY HEADER */}
      <div
        style={{
          background: "#ffffff",
          padding: "30px",
          borderRadius: "16px",
          border: "1px solid #eee",
          boxShadow: "0 4px 15px rgba(0,0,0,0.04)",
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          flexWrap: "wrap",
          gap: "20px",
          marginBottom: "35px",
        }}
      >
        <div style={{ display: "flex", alignItems: "center", gap: "18px" }}>
          <div
            style={{
              width: "64px",
              height: "64px",
              borderRadius: "50%",
              backgroundColor: "#fff0f0",
              color: "#ed1c24",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              fontSize: "32px",
            }}
          >
            <FaUserCircle />
          </div>
          <div>
            <div style={{ display: "flex", alignItems: "center", gap: "10px" }}>
              <h1 style={{ margin: 0, fontSize: "26px", color: "#151515" }}>{user.fullName}</h1>
              <span
                style={{
                  padding: "4px 10px",
                  borderRadius: "12px",
                  backgroundColor: "#f0f0f0",
                  color: "#555",
                  fontSize: "12px",
                  fontWeight: "bold",
                }}
              >
                {user.role?.replace("ROLE_", "")}
              </span>
            </div>
            <p style={{ margin: "6px 0 0", color: "#666", fontSize: "14px" }}>
              {user.email} • {user.city || "Location not set"}
            </p>
          </div>
        </div>

        <div style={{ display: "flex", gap: "12px", flexWrap: "wrap" }}>
          <Link
            to="/requests"
            style={{
              padding: "10px 18px",
              background: "#f8f9fa",
              color: "#333",
              border: "1px solid #ddd",
              borderRadius: "8px",
              fontWeight: "600",
              fontSize: "14px",
              textDecoration: "none",
            }}
          >
            Browse Requests
          </Link>
          <Link
            to="/track"
            style={{
              display: "flex",
              alignItems: "center",
              gap: "8px",
              padding: "10px 18px",
              background: "#ed1c24",
              color: "#fff",
              borderRadius: "8px",
              fontWeight: "600",
              fontSize: "14px",
              textDecoration: "none",
            }}
          >
            <FaLocationArrow /> Live Donor Tracker
          </Link>
        </div>
      </div>

      {loading ? (
        <LoadingSpinner text="Loading dashboard analytics..." />
      ) : (
        <>
          {/* DONOR DASHBOARD VIEW */}
          {user.role === "ROLE_DONOR" && (
            <div>
              {/* STATS CARDS */}
              <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(240px, 1fr))", gap: "20px", marginBottom: "35px" }}>
                <div style={statCardStyle}>
                  <div style={{ ...iconCircleStyle, background: "#fff0f0", color: "#ed1c24" }}>
                    <FaTint />
                  </div>
                  <div>
                    <span style={statLabelStyle}>Blood Group</span>
                    <strong style={{ fontSize: "24px", color: "#ed1c24" }}>
                      {donorStats?.bloodGroup?.replace("_POS", "+")?.replace("_NEG", "-") || "A+"}
                    </strong>
                  </div>
                </div>

                <div style={statCardStyle}>
                  <div style={{ ...iconCircleStyle, background: "#e8f5e9", color: "#2e7d32" }}>
                    <FaHeartbeat />
                  </div>
                  <div>
                    <span style={statLabelStyle}>Total Donations</span>
                    <strong style={{ fontSize: "24px", color: "#222" }}>
                      {donorStats?.totalDonations || 0} Times
                    </strong>
                  </div>
                </div>

                <div style={statCardStyle}>
                  <div style={{ ...iconCircleStyle, background: "#e3f2fd", color: "#1976d2" }}>
                    <FaShieldAlt />
                  </div>
                  <div>
                    <span style={statLabelStyle}>Estimated Lives Saved</span>
                    <strong style={{ fontSize: "24px", color: "#1976d2" }}>
                      {donorStats?.livesSaved || 0} Lives
                    </strong>
                  </div>
                </div>

                <div style={statCardStyle}>
                  <div style={{ ...iconCircleStyle, background: "#fff8e1", color: "#f57f17" }}>
                    <FaCalendarCheck />
                  </div>
                  <div>
                    <span style={statLabelStyle}>Donation Status</span>
                    <strong style={{ fontSize: "16px", color: donorStats?.eligible ? "#28a745" : "#e65100" }}>
                      {donorStats?.eligible ? "Eligible Now" : `Eligible from ${donorStats?.nextEligibleDate}`}
                    </strong>
                  </div>
                </div>
              </div>

              {/* ACTION PANELS */}
              <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "24px" }}>
                {/* AVAILABILITY TOGGLE */}
                <div style={panelStyle}>
                  <h3 style={{ margin: "0 0 12px 0", fontSize: "18px" }}>Emergency Availability</h3>
                  <p style={{ color: "#666", fontSize: "14px", lineHeight: "1.5" }}>
                    Toggle your status so hospitals and patients in your area know whether you are ready to respond.
                  </p>
                  <div style={{ display: "flex", alignItems: "center", justifyContent: "space-between", marginTop: "20px" }}>
                    <StatusBadge
                      status={donorStats?.isAvailable ? "AVAILABLE TO DONATE" : "CURRENTLY OFFLINE"}
                      type={donorStats?.isAvailable ? "success" : "warning"}
                    />
                    <button
                      onClick={handleToggleAvailability}
                      style={{
                        padding: "10px 20px",
                        backgroundColor: donorStats?.isAvailable ? "#dc3545" : "#28a745",
                        color: "#fff",
                        border: "none",
                        borderRadius: "8px",
                        fontWeight: "600",
                        cursor: "pointer",
                      }}
                    >
                      {donorStats?.isAvailable ? "Set Unavailable" : "Set Available"}
                    </button>
                  </div>
                </div>

                {/* GPS BROADCAST CONTROL */}
                <div style={panelStyle}>
                  <h3 style={{ margin: "0 0 12px 0", fontSize: "18px", display: "flex", alignItems: "center", gap: "8px" }}>
                    <FaLocationArrow style={{ color: "#ed1c24" }} /> Live GPS Tracker
                  </h3>
                  <p style={{ color: "#666", fontSize: "14px", lineHeight: "1.5" }}>
                    Broadcast your live coordinates so the medical center can track your transit on Google Maps.
                  </p>
                  <div style={{ marginTop: "15px" }}>
                    <div style={{ fontSize: "13px", color: "#555", marginBottom: "12px", background: "#f8f9fa", padding: "8px 12px", borderRadius: "6px" }}>
                      Status: <strong>{gpsStatus}</strong>
                    </div>
                    <button
                      onClick={toggleLocationBroadcast}
                      style={{
                        width: "100%",
                        padding: "12px",
                        backgroundColor: isBroadcastingLocation ? "#c62828" : "#28a745",
                        color: "#fff",
                        border: "none",
                        borderRadius: "8px",
                        fontWeight: "bold",
                        cursor: "pointer",
                      }}
                    >
                      {isBroadcastingLocation ? "Stop Broadcasting Location" : "Share / Broadcast Live GPS"}
                    </button>
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* HOSPITAL DASHBOARD VIEW */}
          {(user.role === "ROLE_HOSPITAL" || user.role === "ROLE_PATIENT") && (
            <div>
              <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "20px" }}>
                <h2 style={{ fontSize: "22px", margin: 0 }}>My Blood Requests</h2>
                <Link
                  to="/request-blood"
                  style={{
                    display: "flex",
                    alignItems: "center",
                    gap: "8px",
                    padding: "10px 18px",
                    background: "#ed1c24",
                    color: "#fff",
                    borderRadius: "8px",
                    fontWeight: "600",
                    textDecoration: "none",
                  }}
                >
                  <FaPlusCircle /> New Emergency Request
                </Link>
              </div>

              {myRequests.length === 0 ? (
                <EmptyState
                  icon={<FaHospital />}
                  title="No Requests Posted"
                  message="You have not submitted any blood requirements yet."
                />
              ) : (
                <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill, minmax(350px, 1fr))", gap: "20px" }}>
                  {myRequests.map((r) => (
                    <div key={r.id} style={panelStyle}>
                      <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "12px" }}>
                        <div>
                          <strong style={{ fontSize: "17px" }}>{r.patientName}</strong>
                          <div style={{ fontSize: "12px", color: "#777" }}>Request #{r.id}</div>
                        </div>
                        <span style={{ fontSize: "18px", fontWeight: "bold", color: "#ed1c24" }}>
                          {r.bloodGroup?.replace("_POS", "+")?.replace("_NEG", "-")}
                        </span>
                      </div>

                      <div style={{ fontSize: "14px", color: "#555", marginBottom: "16px" }}>
                        <div>Hospital: {r.hospitalName}</div>
                        <div>Units: {r.unitsRequired} Units Required</div>
                        <div>Date: {r.requiredDate}</div>
                      </div>

                      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", paddingTop: "12px", borderTop: "1px solid #eee" }}>
                        <StatusBadge status={r.status} type={r.status === "DONOR_FOUND" ? "success" : "warning"} />
                        {r.status === "DONOR_FOUND" && (
                          <Link
                            to={`/track/${r.id}`}
                            style={{
                              display: "inline-flex",
                              alignItems: "center",
                              gap: "6px",
                              padding: "8px 14px",
                              background: "#28a745",
                              color: "#fff",
                              borderRadius: "6px",
                              fontWeight: "bold",
                              fontSize: "13px",
                              textDecoration: "none",
                            }}
                          >
                            <FaLocationArrow /> Track on Google Maps
                          </Link>
                        )}
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}

          {/* ADMIN DASHBOARD VIEW */}
          {user.role === "ROLE_ADMIN" && (
            <div>
              <h2 style={{ fontSize: "22px", marginBottom: "20px" }}>System Management & Verification</h2>
              <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))", gap: "20px", marginBottom: "30px" }}>
                <div style={statCardStyle}>
                  <div>
                    <span style={statLabelStyle}>Total Users</span>
                    <strong style={{ fontSize: "24px" }}>{adminStats?.totalUsers || adminUsers.length}</strong>
                  </div>
                </div>
                <div style={statCardStyle}>
                  <div>
                    <span style={statLabelStyle}>Total Donors</span>
                    <strong style={{ fontSize: "24px", color: "#28a745" }}>{adminStats?.totalDonors || 5}</strong>
                  </div>
                </div>
                <div style={statCardStyle}>
                  <div>
                    <span style={statLabelStyle}>Active Requests</span>
                    <strong style={{ fontSize: "24px", color: "#ed1c24" }}>{adminStats?.activeRequests || 2}</strong>
                  </div>
                </div>
              </div>

              <div style={panelStyle}>
                <h3 style={{ margin: "0 0 15px 0" }}>User Accounts & Verification</h3>
                <div style={{ overflowX: "auto" }}>
                  <table style={{ width: "100%", borderCollapse: "collapse", fontSize: "14px" }}>
                    <thead>
                      <tr style={{ borderBottom: "2px solid #eee", textAlign: "left" }}>
                        <th style={{ padding: "10px" }}>Name</th>
                        <th style={{ padding: "10px" }}>Email</th>
                        <th style={{ padding: "10px" }}>Role</th>
                        <th style={{ padding: "10px" }}>City</th>
                        <th style={{ padding: "10px" }}>Status</th>
                      </tr>
                    </thead>
                    <tbody>
                      {adminUsers.map((u) => (
                        <tr key={u.id} style={{ borderBottom: "1px solid #eee" }}>
                          <td style={{ padding: "10px", fontWeight: "600" }}>{u.fullName}</td>
                          <td style={{ padding: "10px", color: "#555" }}>{u.email}</td>
                          <td style={{ padding: "10px" }}>{u.role?.replace("ROLE_", "")}</td>
                          <td style={{ padding: "10px" }}>{u.city}</td>
                          <td style={{ padding: "10px" }}>
                            <StatusBadge status={u.verified ? "VERIFIED" : "PENDING"} type={u.verified ? "success" : "warning"} />
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          )}
        </>
      )}
    </div>
  );
}

const statCardStyle = {
  background: "#ffffff",
  padding: "20px",
  borderRadius: "14px",
  border: "1px solid #eee",
  display: "flex",
  alignItems: "center",
  gap: "16px",
  boxShadow: "0 2px 10px rgba(0,0,0,0.03)",
};

const iconCircleStyle = {
  width: "48px",
  height: "48px",
  borderRadius: "12px",
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  fontSize: "22px",
};

const statLabelStyle = {
  display: "block",
  fontSize: "12px",
  color: "#777",
  marginBottom: "4px",
  fontWeight: "600",
};

const panelStyle = {
  background: "#ffffff",
  padding: "24px",
  borderRadius: "14px",
  border: "1px solid #eee",
  boxShadow: "0 2px 10px rgba(0,0,0,0.03)",
};

export default Dashboard;
