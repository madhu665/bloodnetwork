import React, { useEffect, useState } from "react";
import api from "../services/api";

function DonorLocationSharer({ isTrackingEnabled }) {
  const [status, setStatus] = useState("Idle");

  useEffect(() => {
    let watchId;

    if (isTrackingEnabled && "geolocation" in navigator) {
      setStatus("Tracking active...");
      
      watchId = navigator.geolocation.watchPosition(
        async (position) => {
          const { latitude, longitude } = position.coords;
          try {
            await api.put("/donors/me/location", { latitude, longitude });
            setStatus(`Location updated: ${new Date().toLocaleTimeString()}`);
          } catch (err) {
            console.error("Failed to send location to server", err);
          }
        },
        (err) => {
          console.error("Location error:", err);
          setStatus("Location access denied or unavailable.");
        },
        { enableHighAccuracy: true, maximumAge: 10000, timeout: 5000 }
      );
    } else {
      setStatus("Tracking paused.");
    }

    return () => {
      if (watchId) navigator.geolocation.clearWatch(watchId);
    };
  }, [isTrackingEnabled]);

  return (
    <div style={{ padding: "10px", fontSize: "12px", color: isTrackingEnabled ? "#28a745" : "#666" }}>
      {status}
    </div>
  );
}

export default DonorLocationSharer;
