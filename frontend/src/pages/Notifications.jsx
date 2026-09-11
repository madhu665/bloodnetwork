import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import {
  FaBell,
  FaCheckDouble,
  FaExclamationTriangle,
  FaInfoCircle,
  FaCheckCircle,
  FaLocationArrow,
  FaTrashAlt
} from "react-icons/fa";
import api from "../services/api";
import { useAuth } from "../context/AuthContext";
import LoadingSpinner from "../components/common/LoadingSpinner";
import EmptyState from "../components/common/EmptyState";

function Notifications() {
  const { user } = useAuth();
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchNotifications = async () => {
    try {
      setLoading(true);
      const res = await api.get("/notifications");
      setNotifications(res.data);
    } catch (err) {
      setError("Failed to fetch notifications. Please make sure you are logged in.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (user) {
      fetchNotifications();
    } else {
      setLoading(false);
    }
  }, [user]);

  const handleMarkAsRead = async (id) => {
    try {
      await api.put(`/notifications/${id}/read`);
      setNotifications((prev) =>
        prev.map((n) => (n.id === id ? { ...n, isRead: true } : n))
      );
    } catch (err) {
      console.error("Failed to mark notification as read", err);
    }
  };

  const handleMarkAllAsRead = async () => {
    try {
      await api.put("/notifications/read-all");
      setNotifications((prev) => prev.map((n) => ({ ...n, isRead: true })));
    } catch (err) {
      console.error("Failed to mark all as read", err);
    }
  };

  if (!user) {
    return (
      <div style={{ maxWidth: "600px", margin: "80px auto", textAlign: "center", padding: "40px", background: "#fff", borderRadius: "16px", border: "1px solid #eee" }}>
        <FaBell size={48} color="#ed1c24" style={{ marginBottom: "16px" }} />
        <h2>Sign In to View Notifications</h2>
        <p style={{ color: "#666", marginBottom: "20px" }}>
          You need an active donor, hospital, or recipient account to receive real-time emergency dispatch alerts.
        </p>
        <Link
          to="/login"
          style={{
            padding: "12px 24px",
            background: "#ed1c24",
            color: "#fff",
            textDecoration: "none",
            borderRadius: "8px",
            fontWeight: "bold",
          }}
        >
          Login Now
        </Link>
      </div>
    );
  }

  return (
    <div style={{ maxWidth: "900px", margin: "0 auto", padding: "50px 24px" }}>
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "30px", flexWrap: "wrap", gap: "15px" }}>
        <div>
          <div
            style={{
              display: "inline-flex",
              alignItems: "center",
              gap: "8px",
              padding: "6px 16px",
              borderRadius: "30px",
              backgroundColor: "#fff0f0",
              color: "#ed1c24",
              fontSize: "13px",
              fontWeight: "700",
              marginBottom: "8px",
            }}
          >
            <FaBell />
            <span>ALERTS & DISPATCH</span>
          </div>
          <h1 style={{ fontSize: "32px", fontWeight: "700", color: "#151515", margin: 0 }}>
            Notifications Center
          </h1>
        </div>

        {notifications.some((n) => !n.isRead) && (
          <button
            onClick={handleMarkAllAsRead}
            style={{
              display: "flex",
              alignItems: "center",
              gap: "8px",
              padding: "10px 18px",
              backgroundColor: "#f8f9fa",
              color: "#333",
              border: "1px solid #ddd",
              borderRadius: "8px",
              fontWeight: "600",
              fontSize: "13px",
              cursor: "pointer",
            }}
          >
            <FaCheckDouble /> Mark All as Read
          </button>
        )}
      </div>

      {loading ? (
        <LoadingSpinner text="Loading your notifications..." />
      ) : error ? (
        <div style={{ textAlign: "center", padding: "30px", color: "red" }}>{error}</div>
      ) : notifications.length === 0 ? (
        <EmptyState
          icon={<FaBell />}
          title="No Notifications Yet"
          message="You're all caught up! You'll receive instant alerts here when new compatible requests arise."
        />
      ) : (
        <div style={{ display: "flex", flexDirection: "column", gap: "16px" }}>
          {notifications.map((notif) => {
            const isEmergency = notif.type === "EMERGENCY_REQUEST";

            return (
              <div
                key={notif.id}
                style={{
                  background: notif.isRead ? "#ffffff" : "#fff8f8",
                  borderRadius: "12px",
                  border: notif.isRead ? "1px solid #eee" : "1px solid #ffccd0",
                  padding: "20px",
                  display: "flex",
                  gap: "16px",
                  alignItems: "flex-start",
                  boxShadow: notif.isRead ? "none" : "0 4px 12px rgba(237, 28, 36, 0.08)",
                  transition: "all 0.2s ease",
                }}
              >
                <div
                  style={{
                    width: "42px",
                    height: "42px",
                    borderRadius: "50%",
                    backgroundColor: isEmergency ? "#fff0f0" : "#e8f4fd",
                    color: isEmergency ? "#ed1c24" : "#1976d2",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    fontSize: "18px",
                    flexShrink: 0,
                  }}
                >
                  {isEmergency ? <FaExclamationTriangle /> : <FaInfoCircle />}
                </div>

                <div style={{ flex: 1 }}>
                  <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start", gap: "10px" }}>
                    <h3 style={{ margin: "0 0 6px 0", fontSize: "16px", color: "#222" }}>
                      {notif.title}
                    </h3>
                    <span style={{ fontSize: "12px", color: "#888", whiteSpace: "nowrap" }}>
                      {new Date(notif.createdAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                    </span>
                  </div>

                  <p style={{ margin: "0 0 12px 0", fontSize: "14px", color: "#555", lineHeight: "1.5" }}>
                    {notif.message}
                  </p>

                  <div style={{ display: "flex", alignItems: "center", gap: "12px", flexWrap: "wrap" }}>
                    {notif.relatedRequestId && (
                      <Link
                        to={`/track/${notif.relatedRequestId}`}
                        style={{
                          display: "inline-flex",
                          alignItems: "center",
                          gap: "6px",
                          fontSize: "13px",
                          color: "#ed1c24",
                          fontWeight: "700",
                          textDecoration: "none",
                        }}
                      >
                        <FaLocationArrow /> View / Track Request #{notif.relatedRequestId}
                      </Link>
                    )}

                    {!notif.isRead && (
                      <button
                        onClick={() => handleMarkAsRead(notif.id)}
                        style={{
                          border: "none",
                          background: "transparent",
                          color: "#666",
                          fontSize: "12px",
                          cursor: "pointer",
                          textDecoration: "underline",
                          padding: 0,
                        }}
                      >
                        Mark read
                      </button>
                    )}
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}

export default Notifications;
