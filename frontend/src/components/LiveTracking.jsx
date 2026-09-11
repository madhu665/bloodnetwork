import React, { useState, useEffect } from "react";
import { GoogleMap, LoadScript, Marker } from "@react-google-maps/api";
import api from "../services/api";

const containerStyle = {
  width: "100%",
  height: "500px",
  borderRadius: "12px",
};

function LiveTracking({ requestId }) {
  const [donorLocation, setDonorLocation] = useState(null);
  const [hospitalLocation, setHospitalLocation] = useState(null); // Assuming hospital knows its own location
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchLiveLocation = async () => {
    try {
      const response = await api.get(`/blood-requests/${requestId}/live-tracking`);
      if (response.data && response.data.latitude && response.data.longitude) {
        setDonorLocation({
          lat: response.data.latitude,
          lng: response.data.longitude,
          name: response.data.donorName,
          phone: response.data.contactPhone,
          lastUpdate: response.data.lastUpdate
        });
      }
    } catch (err) {
      setError("Failed to fetch donor live location. They may not have shared it yet.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    // Fetch immediately
    fetchLiveLocation();
    
    // Then poll every 15 seconds
    const intervalId = setInterval(() => {
      fetchLiveLocation();
    }, 15000);

    return () => clearInterval(intervalId);
  }, [requestId]);

  if (loading && !donorLocation) return <div>Loading live map...</div>;
  if (error) return <div style={{ color: "red", padding: "20px" }}>{error}</div>;

  const defaultCenter = donorLocation || { lat: 17.3850, lng: 78.4867 }; // Default Hyderabad

  return (
    <div style={{ border: "1px solid #eee", borderRadius: "12px", overflow: "hidden", background: "#fff" }}>
      <div style={{ padding: "15px", background: "#f8f9fa", borderBottom: "1px solid #eee", display: "flex", justifyContent: "space-between" }}>
        <h3 style={{ margin: 0, fontSize: "16px", color: "#ed1c24" }}>Live Donor Tracking</h3>
        {donorLocation && (
          <span style={{ fontSize: "12px", color: "#666" }}>
            Last updated: {new Date(donorLocation.lastUpdate).toLocaleTimeString()}
          </span>
        )}
      </div>
      
      {/* 
        NOTE: Google Maps API key is required here. 
        For demo purposes in this project without an actual key, 
        you will see the "Development purposes only" watermark.
      */}
      <LoadScript googleMapsApiKey="YOUR_GOOGLE_MAPS_API_KEY_HERE">
        <GoogleMap
          mapContainerStyle={containerStyle}
          center={defaultCenter}
          zoom={14}
        >
          {donorLocation && (
            <Marker
              position={{ lat: donorLocation.lat, lng: donorLocation.lng }}
              label="Donor"
              title={`${donorLocation.name} - ${donorLocation.phone}`}
            />
          )}
        </GoogleMap>
      </LoadScript>
      
      {donorLocation && (
        <div style={{ padding: "15px" }}>
          <strong>Donor:</strong> {donorLocation.name} <br/>
          <strong>Phone:</strong> {donorLocation.phone}
        </div>
      )}
    </div>
  );
}

export default LiveTracking;
