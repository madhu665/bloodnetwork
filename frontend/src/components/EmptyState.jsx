import React from "react";
import { FaSearch } from "react-icons/fa";

function EmptyState({
  icon: Icon = FaSearch,
  title = "No records found",
  description = "There are no matching items to display at this moment.",
  actionText,
  onAction,
}) {
  return (
    <div
      style={{
        textAlign: "center",
        padding: "60px 24px",
        background: "#ffffff",
        borderRadius: "16px",
        border: "1px dashed #dddddd",
        maxWidth: "540px",
        margin: "30px auto",
      }}
    >
      <div
        style={{
          width: "68px",
          height: "68px",
          borderRadius: "50%",
          backgroundColor: "#fff0f0",
          color: "#ed1c24",
          display: "inline-flex",
          alignItems: "center",
          justifyContent: "center",
          fontSize: "26px",
          marginBottom: "18px",
        }}
      >
        <Icon />
      </div>
      <h3
        style={{
          fontSize: "20px",
          fontWeight: "700",
          color: "#1a1a1a",
          marginBottom: "8px",
        }}
      >
        {title}
      </h3>
      <p
        style={{
          color: "#666666",
          fontSize: "14.5px",
          lineHeight: "1.6",
          maxWidth: "420px",
          margin: "0 auto 20px",
        }}
      >
        {description}
      </p>
      {actionText && onAction && (
        <button
          onClick={onAction}
          style={{
            backgroundColor: "#ed1c24",
            color: "#ffffff",
            border: "none",
            padding: "10px 24px",
            borderRadius: "8px",
            fontWeight: "600",
            fontSize: "14px",
            cursor: "pointer",
            boxShadow: "0 4px 12px rgba(237, 28, 36, 0.2)",
          }}
        >
          {actionText}
        </button>
      )}
    </div>
  );
}

export default EmptyState;
