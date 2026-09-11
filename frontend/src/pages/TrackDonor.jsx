import React, { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import {
  FaMapMarkerAlt,
  FaPhoneAlt,
  FaHospital,
  FaTint,
  FaLocationArrow,
  FaRoute,
  FaExternalLinkAlt,
  FaSyncAlt,
  FaPlay,
  FaCheckCircle,
  FaExclamationTriangle
} from "react-icons/fa";
import api from "../services/api";
import LoadingSpinner from "../components/common/LoadingSpinner";
import StatusBadge from "../components/common/StatusBadge";

function TrackDonor() {
  const { requestId } = useParams();
  const navigate = useNavigate();

  const [activeRequestId, setActiveRequestId] = useState(requestId || "2");
  const [requestDetails, setRequestDetails] = useState(null);
  const [trackingData, setTrackingData] = useState(null);
  const [allRequests, setAllRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [isSimulating, setIsSimulating] = useState(false);
  const [lastRefreshed, setLastRefreshed] = useState(new Date());

  // Load all requests with donors for quick selector
  useEffect(() => {
    const loadRequests = async () => {
      try {
        const res = await api.get("/blood-requests");
        setAllRequests(res.data);
      } catch (err) {
        console.error("Error loading requests", err);
      }
    };
    loadRequests();
  }, []);

  // Fetch tracking data for current active request
  const fetchTracking = async (reqId) => {
    const idToFetch = reqId || activeRequestId;
    if (!idToFetch) return;

    try {
      setError("");
      // Fetch request details
      const reqRes = await api.get(`/blood-requests/${idToFetch}`);
      setRequestDetails(reqRes.data);

      // Fetch live tracking coordinates
      const trackRes = await api.get(`/blood-requests/${idToFetch}/live-tracking`);
      setTrackingData(trackRes.data);
      setLastRefreshed(new Date());
    } catch (err) {
      console.warn("Tracking fetch warning:", err);
      setError(
        err.response?.data?.message ||
        "No donor has been assigned to this request yet, or coordinates are not yet available."
      );
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (requestId) {
      setActiveRequestId(requestId);
      fetchTracking(requestId);
    } else {
      fetchTracking(activeRequestId);
    }

    // Auto-poll coordinates every 10 seconds
    const interval = setInterval(() => {
      fetchTracking(activeRequestId);
    }, 10000);

    return () => clearInterval(interval);
  }, [requestId, activeRequestId]);

  const handleSelectRequest = (e) => {
    const newId = e.target.value;
    setActiveRequestId(newId);
    navigate(`/track/${newId}`);
  };

  // Simulate movement step towards hospital for live testing
  const handleSimulateMovement = async () => {
    if (!trackingData || !trackingData.latitude) return;
    setIsSimulating(true);

    try {
      // Offset donor coordinates slightly closer (step simulation)
      const newLat = trackingData.latitude + (Math.random() - 0.5) * 0.003;
      const newLng = trackingData.longitude + (Math.random() - 0.5) * 0.003;

      // Update in backend
      await api.put("/donors/me/location", {
        latitude: newLat,
        longitude: newLng,
      });

      // Refresh map view
      await fetchTracking(activeRequestId);
    } catch (err) {
      // If not logged in as donor, simulate locally on frontend view
      setTrackingData((prev) => ({
        ...prev,
        latitude: prev.latitude + 0.0015,
        longitude: prev.longitude + 0.0015,
        lastUpdate: new Date().toISOString(),
      }));
    } finally {
      setIsSimulating(false);
    }
  };

  const donorLat = trackingData?.latitude || 17.4321;
  const donorLng = trackingData?.longitude || 78.4321;

  // Google Maps directions external link
  const googleMapsDirectionsUrl = `https://www.google.com/maps/dir/?api=1&destination=${encodeURIComponent(
    requestDetails?.hospitalName || "Hospital"
  )}+${encodeURIComponent(requestDetails?.city || "Hyderabad")}&origin=${donorLat},${donorLng}`;

  return (
    <div style={{ maxWidth: "1200px", margin: "0 auto", padding: "40px 24px" }}>
      {/* HEADER */}
      <div style={{ textAlign: "center", marginBottom: "35px" }}>
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
          <FaLocationArrow />
          <span>LIVE GPS DONOR TRACKING</span>
        </div>
        <h1 style={{ fontSize: "36px", fontWeight: "700", color: "#151515" }}>
          Google Maps <span style={{ color: "#ed1c24" }}>Donor Tracker</span>
        </h1>
        <p style={{ color: "#666", fontSize: "16px", maxWidth: "620px", margin: "10px auto 0" }}>
          Real-time GPS dispatch tracking for emergency blood donors en route to medical centers.
        </p>
      </div>

      {/* CONTROLS BAR */}
      <div
        style={{
          background: "#ffffff",
          padding: "20px 24px",
          borderRadius: "14px",
          border: "1px solid #eee",
          boxShadow: "0 4px 15px rgba(0,0,0,0.04)",
          display: "flex",
          flexWrap: "wrap",
          alignItems: "center",
          justifyContent: "space-between",
          gap: "16px",
          marginBottom: "25px",
        }}
      >
        <div style={{ display: "flex", alignItems: "center", gap: "12px", flex: 1, minWidth: "260px" }}>
          <label style={{ fontSize: "14px", fontWeight: "600", color: "#333", whiteSpace: "nowrap" }}>
            Select Request:
          </label>
          <select
            value={activeRequestId}
            onChange={handleSelectRequest}
            style={{
              padding: "10px 14px",
              borderRadius: "8px",
              border: "1px solid #ccc",
              fontSize: "14px",
              flex: 1,
              maxWidth: "360px",
            }}
          >
            {allRequests.map((r) => (
              <option key={r.id} value={r.id}>
                #{r.id} - {r.patientName} ({r.bloodGroup?.replace("_POS", "+")?.replace("_NEG", "-")}) - {r.hospitalName} ({r.status})
              </option>
            ))}
            {allRequests.length === 0 && (
              <option value="2">#2 - Ananya Sen (A+) - Care Hospitals (DONOR_FOUND)</option>
            )}
          </select>
        </div>

        <div style={{ display: "flex", gap: "10px" }}>
          <button
            onClick={() => fetchTracking(activeRequestId)}
            style={{
              display: "flex",
              alignItems: "center",
              gap: "8px",
              padding: "10px 18px",
              borderRadius: "8px",
              border: "1px solid #ddd",
              background: "#fff",
              color: "#333",
              fontWeight: "600",
              fontSize: "13px",
              cursor: "pointer",
            }}
          >
            <FaSyncAlt /> Refresh GPS
          </button>

          <button
            onClick={handleSimulateMovement}
            disabled={isSimulating}
            style={{
              display: "flex",
              alignItems: "center",
              gap: "8px",
              padding: "10px 18px",
              borderRadius: "8px",
              border: "none",
              background: "#28a745",
              color: "#fff",
              fontWeight: "600",
              fontSize: "13px",
              cursor: "pointer",
            }}
          >
            <FaPlay /> {isSimulating ? "Moving..." : "Test Movement"}
          </button>
        </div>
      </div>

      {loading && !trackingData ? (
        <LoadingSpinner text="Connecting to donor GPS signal..." />
      ) : (
        <div style={{ display: "grid", gridTemplateColumns: "1fr 340px", gap: "24px" }}>
          {/* MAP DISPLAY PANEL */}
          <div
            style={{
              background: "#ffffff",
              borderRadius: "16px",
              border: "1px solid #eee",
              overflow: "hidden",
              boxShadow: "0 6px 20px rgba(0,0,0,0.05)",
            }}
          >
            {/* MAP TOOLBAR */}
            <div
              style={{
                padding: "16px 20px",
                background: "#fdfdfd",
                borderBottom: "1px solid #eee",
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                flexWrap: "wrap",
                gap: "10px",
              }}
            >
              <div style={{ display: "flex", alignItems: "center", gap: "10px" }}>
                <span
                  style={{
                    display: "inline-block",
                    width: "12px",
                    height: "12px",
                    borderRadius: "50%",
                    background: "#28a745",
                    boxShadow: "0 0 8px #28a745",
                  }}
                />
                <span style={{ fontWeight: "700", color: "#222", fontSize: "15px" }}>
                  Active Live Signal
                </span>
                <span style={{ fontSize: "12px", color: "#777" }}>
                  (Lat: {donorLat.toFixed(4)}, Lng: {donorLng.toFixed(4)})
                </span>
              </div>

              <a
                href={googleMapsDirectionsUrl}
                target="_blank"
                rel="noopener noreferrer"
                style={{
                  display: "inline-flex",
                  alignItems: "center",
                  gap: "6px",
                  padding: "6px 14px",
                  background: "#4285F4",
                  color: "#ffffff",
                  borderRadius: "6px",
                  fontSize: "12px",
                  fontWeight: "600",
                  textDecoration: "none",
                }}
              >
                <FaExternalLinkAlt /> Open in Google Maps
              </a>
            </div>

            {/* INTERACTIVE MAP CONTAINER */}
            <div style={{ position: "relative", width: "100%", height: "540px", background: "#e5e3df" }}>
              {/* Google Maps View via Embed */}
              <iframe
                title="Google Maps Donor Tracking"
                width="100%"
                height="100%"
                style={{ border: 0 }}
                loading="lazy"
                src={`https://maps.google.com/maps?q=${donorLat},${donorLng}&hl=en&z=15&output=embed`}
              />

              {/* OVERLAY STATUS PILL */}
              <div
                style={{
                  position: "absolute",
                  bottom: "20px",
                  left: "20px",
                  background: "rgba(255, 255, 255, 0.95)",
                  padding: "12px 18px",
                  borderRadius: "10px",
                  boxShadow: "0 4px 15px rgba(0,0,0,0.15)",
                  display: "flex",
                  alignItems: "center",
                  gap: "12px",
                  backdropFilter: "blur(6px)",
                }}
              >
                <div
                  style={{
                    width: "36px",
                    height: "36px",
                    borderRadius: "50%",
                    background: "#fff0f0",
                    color: "#ed1c24",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    fontSize: "16px",
                  }}
                >
                  <FaRoute />
                </div>
                <div>
                  <div style={{ fontSize: "13px", fontWeight: "700", color: "#222" }}>
                    Status: En Route to Destination
                  </div>
                  <div style={{ fontSize: "11px", color: "#666" }}>
                    Estimated arrival in ~15 mins (Traffic: Normal)
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* SIDEBAR DISPATCH DETAILS */}
          <div style={{ display: "flex", flexDirection: "column", gap: "20px" }}>
            {/* DONOR CARD */}
            <div
              style={{
                background: "#ffffff",
                padding: "24px",
                borderRadius: "14px",
                border: "1px solid #eee",
                boxShadow: "0 4px 15px rgba(0,0,0,0.04)",
              }}
            >
              <h3 style={{ margin: "0 0 16px 0", fontSize: "17px", color: "#111", borderBottom: "1px solid #f0f0f0", paddingBottom: "10px" }}>
                Assigned Donor
              </h3>

              {error && (
                <div
                  style={{
                    padding: "12px",
                    background: "#fff3cd",
                    color: "#856404",
                    borderRadius: "8px",
                    fontSize: "13px",
                    marginBottom: "14px",
                  }}
                >
                  <FaExclamationTriangle style={{ marginRight: "6px" }} />
                  {error}
                </div>
              )}

              <div style={{ display: "flex", alignItems: "center", gap: "12px", marginBottom: "16px" }}>
                <div
                  style={{
                    width: "48px",
                    height: "48px",
                    borderRadius: "50%",
                    background: "#fff0f0",
                    color: "#ed1c24",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    fontSize: "18px",
                    fontWeight: "bold",
                  }}
                >
                  {requestDetails?.bloodGroup?.replace("_POS", "+")?.replace("_NEG", "-") || "A+"}
                </div>
                <div>
                  <div style={{ fontWeight: "700", fontSize: "16px", color: "#222" }}>
                    {trackingData?.donorName || "Sneha Reddy (Verified)"}
                  </div>
                  <div style={{ fontSize: "13px", color: "#28a745", display: "flex", alignItems: "center", gap: "4px" }}>
                    <FaCheckCircle /> Voluntary Hero Donor
                  </div>
                </div>
              </div>

              <div style={{ display: "flex", flexDirection: "column", gap: "10px", fontSize: "14px", color: "#555" }}>
                <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
                  <FaPhoneAlt style={{ color: "#ed1c24" }} />
                  <span>{trackingData?.contactPhone || "+91 91234 56781"}</span>
                </div>
                <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
                  <FaMapMarkerAlt style={{ color: "#ed1c24" }} />
                  <span>Current GPS: Hyderabad Sector</span>
                </div>
                <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
                  <FaSyncAlt style={{ color: "#666" }} />
                  <span style={{ fontSize: "12px", color: "#888" }}>
                    Last ping: {lastRefreshed.toLocaleTimeString()}
                  </span>
                </div>
              </div>
            </div>

            {/* HOSPITAL & PATIENT CARD */}
            <div
              style={{
                background: "#ffffff",
                padding: "24px",
                borderRadius: "14px",
                border: "1px solid #eee",
                boxShadow: "0 4px 15px rgba(0,0,0,0.04)",
              }}
            >
              <h3 style={{ margin: "0 0 16px 0", fontSize: "17px", color: "#111", borderBottom: "1px solid #f0f0f0", paddingBottom: "10px" }}>
                Destination & Patient
              </h3>

              <div style={{ display: "flex", flexDirection: "column", gap: "12px", fontSize: "14px" }}>
                <div>
                  <span style={{ color: "#888", fontSize: "12px", display: "block" }}>PATIENT</span>
                  <strong style={{ color: "#222", fontSize: "15px" }}>
                    {requestDetails?.patientName || "Ananya Sen"}
                  </strong>
                </div>

                <div>
                  <span style={{ color: "#888", fontSize: "12px", display: "block" }}>HOSPITAL</span>
                  <div style={{ display: "flex", alignItems: "flex-start", gap: "6px", color: "#333", marginTop: "2px" }}>
                    <FaHospital style={{ color: "#ed1c24", marginTop: "3px" }} />
                    <span>
                      {requestDetails?.hospitalName || "Care Hospitals Banjara Hills"}
                      <br />
                      <small style={{ color: "#777" }}>
                        {requestDetails?.hospitalAddress || "Road No. 1, Banjara Hills"}, {requestDetails?.city || "Hyderabad"}
                      </small>
                    </span>
                  </div>
                </div>

                <div>
                  <span style={{ color: "#888", fontSize: "12px", display: "block" }}>REQUIRED BLOOD</span>
                  <span style={{ fontWeight: "700", color: "#ed1c24", fontSize: "16px" }}>
                    {requestDetails?.unitsRequired || 2} Units ({requestDetails?.bloodGroup?.replace("_POS", "+")?.replace("_NEG", "-") || "A+"})
                  </span>
                </div>

                <div style={{ paddingTop: "10px", borderTop: "1px solid #eee" }}>
                  <StatusBadge status={requestDetails?.status || "DONOR_FOUND"} type="success" />
                </div>
              </div>
            </div>

            <Link
              to="/requests"
              style={{
                display: "block",
                textAlign: "center",
                padding: "12px",
                background: "#f8f9fa",
                color: "#333",
                borderRadius: "8px",
                fontWeight: "600",
                fontSize: "14px",
                textDecoration: "none",
                border: "1px solid #ddd",
              }}
            >
              Browse All Requests
            </Link>
          </div>
        </div>
      )}
    </div>
  );
}

export default TrackDonor;
