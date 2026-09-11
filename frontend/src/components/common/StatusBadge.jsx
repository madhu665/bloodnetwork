import React from "react";

function StatusBadge({ status, type = "info" }) {
  let bgColor, textColor;

  switch (type) {
    case "success":
      bgColor = "#d4edda";
      textColor = "#155724";
      break;
    case "warning":
      bgColor = "#fff3cd";
      textColor = "#856404";
      break;
    case "danger":
      bgColor = "#f8d7da";
      textColor = "#721c24";
      break;
    default:
      bgColor = "#e2e3e5";
      textColor = "#383d41";
      break;
  }

  return (
    <span
      style={{
        display: "inline-block",
        padding: "4px 10px",
        borderRadius: "20px",
        backgroundColor: bgColor,
        color: textColor,
        fontSize: "12px",
        fontWeight: "bold",
        textTransform: "uppercase",
        letterSpacing: "0.5px"
      }}
    >
      {status}
    </span>
  );
}

export default StatusBadge;
