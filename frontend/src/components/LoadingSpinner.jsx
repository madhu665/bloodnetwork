import React from "react";
import { FaTint } from "react-icons/fa";

function LoadingSpinner({ message = "Loading data...", fullPage = false }) {
  const containerStyle = fullPage
    ? {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        minHeight: "60vh",
        padding: "40px 20px",
      }
    : {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        padding: "30px 20px",
      };

  return (
    <div style={containerStyle}>
      <div
        style={{
          position: "relative",
          width: "56px",
          height: "56px",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <div
          style={{
            position: "absolute",
            width: "100%",
            height: "100%",
            border: "4px solid #fff0f0",
            borderTop: "4px solid #ed1c24",
            borderRadius: "50%",
            animation: "spin 0.9s linear infinite",
          }}
        />
        <FaTint style={{ color: "#ed1c24", fontSize: "20px" }} />
      </div>
      {message && (
        <p
          style={{
            marginTop: "16px",
            color: "#666666",
            fontSize: "14px",
            fontWeight: "500",
          }}
        >
          {message}
        </p>
      )}
      <style>{`
        @keyframes spin {
          0% { transform: rotate(0deg); }
          100% { transform: rotate(360deg); }
        }
      `}</style>
    </div>
  );
}

export default LoadingSpinner;
