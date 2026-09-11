import React from "react";

function EmptyState({ icon, title, message }) {
  return (
    <div style={{ textAlign: "center", padding: "60px 20px", background: "#fcfcfc", borderRadius: "12px", border: "1px dashed #ddd" }}>
      <div style={{ fontSize: "48px", color: "#ccc", marginBottom: "16px" }}>
        {icon}
      </div>
      <h3 style={{ margin: "0 0 8px 0", color: "#333", fontSize: "20px" }}>{title}</h3>
      <p style={{ margin: 0, color: "#777", fontSize: "15px" }}>{message}</p>
    </div>
  );
}

export default EmptyState;
