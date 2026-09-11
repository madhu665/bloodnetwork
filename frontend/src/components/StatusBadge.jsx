import React from "react";
import "./StatusBadge.css";

function StatusBadge({ status, type = "status" }) {
  if (!status) return null;

  const normalized = status.toString().toUpperCase().replace(/\s+/g, "_");

  // Determine CSS class based on status/urgency type
  let badgeClass = "badge-default";

  switch (normalized) {
    case "CRITICAL":
      badgeClass = "badge-critical";
      break;
    case "URGENT":
      badgeClass = "badge-urgent";
      break;
    case "NORMAL":
      badgeClass = "badge-normal";
      break;
    case "FULFILLED":
    case "COMPLETED":
    case "ELIGIBLE":
    case "AVAILABLE":
    case "VERIFIED":
      badgeClass = "badge-success";
      break;
    case "IN_PROGRESS":
    case "MATCHING":
    case "DONOR_FOUND":
    case "HOSPITAL_CONFIRMED":
      badgeClass = "badge-progress";
      break;
    case "PENDING":
      badgeClass = "badge-pending";
      break;
    case "CANCELLED":
    case "EXPIRED":
    case "UNAVAILABLE":
    case "SUSPENDED":
      badgeClass = "badge-inactive";
      break;
    default:
      badgeClass = "badge-default";
  }

  const formatText = (text) => {
    return text.replace(/_/g, " ");
  };

  return (
    <span className={`status-badge ${badgeClass}`}>
      <span className="badge-dot"></span>
      {formatText(status)}
    </span>
  );
}

export default StatusBadge;
