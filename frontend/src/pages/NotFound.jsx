import React from "react";
import { Link } from "react-router-dom";
import { FaHeartBroken, FaHome } from "react-icons/fa";

function NotFound() {
  return (
    <div
      style={{
        minHeight: "70vh",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        padding: "40px 20px",
        textAlign: "center",
      }}
    >
      <div
        style={{
          width: "80px",
          height: "80px",
          borderRadius: "50%",
          backgroundColor: "#fff0f0",
          color: "#ed1c24",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          fontSize: "36px",
          marginBottom: "24px",
        }}
      >
        <FaHeartBroken />
      </div>
      <h1
        style={{
          fontSize: "52px",
          fontWeight: "800",
          color: "#151515",
          margin: "0 0 10px",
        }}
      >
        404
      </h1>
      <h2
        style={{
          fontSize: "22px",
          fontWeight: "600",
          color: "#444444",
          marginBottom: "12px",
        }}
      >
        Page Not Found
      </h2>
      <p
        style={{
          color: "#777777",
          maxWidth: "460px",
          lineHeight: "1.6",
          marginBottom: "28px",
        }}
      >
        The page you are looking for might have been moved, deleted, or does not
        exist on the Blood Response Network.
      </p>
      <Link
        to="/"
        style={{
          display: "inline-flex",
          alignItems: "center",
          gap: "8px",
          backgroundColor: "#ed1c24",
          color: "#ffffff",
          padding: "12px 28px",
          borderRadius: "10px",
          fontWeight: "600",
          textDecoration: "none",
          boxShadow: "0 4px 14px rgba(237, 28, 36, 0.2)",
        }}
      >
        <FaHome /> Back to Home
      </Link>
    </div>
  );
}

export default NotFound;
