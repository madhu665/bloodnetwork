import React from "react";

function LoadingSpinner({ text = "Loading..." }) {
  return (
    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center", padding: "40px" }}>
      <div 
        style={{
          width: "40px",
          height: "40px",
          border: "4px solid #f3f3f3",
          borderTop: "4px solid #ed1c24",
          borderRadius: "50%",
          animation: "spin 1s linear infinite"
        }}
      />
      <p style={{ marginTop: "16px", color: "#666", fontWeight: "500" }}>{text}</p>
      <style>
        {`
          @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
          }
        `}
      </style>
    </div>
  );
}

export default LoadingSpinner;
