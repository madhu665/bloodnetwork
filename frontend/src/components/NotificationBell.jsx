import React, { useState, useEffect, useContext } from "react";
import { FaBell } from "react-icons/fa";
import api from "../services/api";
import { AuthContext } from "../context/AuthContext";

import { Link } from "react-router-dom";

function NotificationBell() {
  const { user } = useContext(AuthContext);
  const [unreadCount, setUnreadCount] = useState(0);

  useEffect(() => {
    if (!user) return;

    const fetchUnreadCount = async () => {
      try {
        const res = await api.get("/notifications/unread-count");
        setUnreadCount(res.data.unreadCount || 0);
      } catch (err) {
        console.error("Failed to fetch notifications count");
      }
    };

    fetchUnreadCount();
    
    // Poll every 15 seconds
    const interval = setInterval(fetchUnreadCount, 15000);
    return () => clearInterval(interval);
  }, [user]);

  if (!user) return null;

  return (
    <Link to="/notifications" style={{ position: "relative", cursor: "pointer", marginRight: "20px", display: "inline-flex", alignItems: "center", textDecoration: "none" }}>
      <FaBell size={22} color="#555" />
      {unreadCount > 0 && (
        <span
          style={{
            position: "absolute",
            top: "-6px",
            right: "-8px",
            background: "#ed1c24",
            color: "white",
            fontSize: "10px",
            fontWeight: "bold",
            padding: "2px 6px",
            borderRadius: "50%",
          }}
        >
          {unreadCount > 99 ? "99+" : unreadCount}
        </span>
      )}
    </Link>
  );
}

export default NotificationBell;
