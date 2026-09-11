import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  FaTint,
  FaUser,
  FaHospital,
  FaHospitalAlt,
  FaArrowRight,
  FaArrowLeft,
  FaShieldAlt,
  FaEnvelope,
  FaLock,
  FaPhone,
  FaMapMarkerAlt,
  FaEye,
  FaEyeSlash,
  FaCalendarAlt,
  FaCheckCircle,
  FaExclamationCircle,
} from "react-icons/fa";
import { authApi } from "../services/api";
import { useAuth } from "../context/AuthContext";
import "./Register.css";

function Register() {
  const [selectedRole, setSelectedRole] = useState(null);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");

  const navigate = useNavigate();
  const { login } = useAuth();

  // Form State
  const [formData, setFormData] = useState({
    // Common
    fullName: "",
    email: "",
    password: "",
    confirmPassword: "",
    phone: "",
    city: "",
    address: "",

    // Donor Specific
    bloodGroup: "O+",
    dateOfBirth: "",
    gender: "Male",
    isAvailable: true,

    // Patient Specific
    emergencyContactName: "",
    emergencyContactPhone: "",

    // Hospital Specific
    hospitalName: "",
    licenseNumber: "",
    emergencyContactNumber: "",
    department: "",
    bedCount: "",

    // Blood Bank Specific
    bloodBankName: "",
    registrationNumber: "",
    contactPerson: "",
    operatingHours: "24/7",
  });

  const roles = [
    {
      id: "donor",
      title: "Blood Donor",
      icon: FaTint,
      desc: "Register to donate blood, track donations, and receive real-time emergency alerts when patients need your blood type.",
    },
    {
      id: "patient",
      title: "Patient / Recipient",
      icon: FaUser,
      desc: "Submit emergency blood requests, locate verified donors in your vicinity, and track transfusion coordination.",
    },
    {
      id: "hospital",
      title: "Hospital",
      icon: FaHospital,
      desc: "Manage patient emergency blood needs, request bulk units from blood banks, and connect with local donors.",
    },
    {
      id: "blood-bank",
      title: "Blood Bank",
      icon: FaHospitalAlt,
      desc: "Maintain live blood inventory levels across all 8 blood groups and supply regional hospitals.",
    },
  ];

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMsg("");
    setSuccessMsg("");

    if (formData.password !== formData.confirmPassword) {
      setErrorMsg("Passwords do not match. Please re-enter.");
      return;
    }

    if (formData.password.length < 6) {
      setErrorMsg("Password must be at least 6 characters long.");
      return;
    }

    setLoading(true);

    try {
      let response;
      if (selectedRole === "donor") {
        response = await authApi.registerDonor({
          fullName: formData.fullName,
          email: formData.email,
          password: formData.password,
          phone: formData.phone,
          city: formData.city,
          address: formData.address,
          bloodGroup: formData.bloodGroup,
          dateOfBirth: formData.dateOfBirth || null,
          gender: formData.gender,
          isAvailable: formData.isAvailable,
        });
      } else if (selectedRole === "patient") {
        response = await authApi.registerPatient({
          fullName: formData.fullName,
          email: formData.email,
          password: formData.password,
          phone: formData.phone,
          city: formData.city,
          address: formData.address,
          bloodGroup: formData.bloodGroup,
          emergencyContactName: formData.emergencyContactName,
          emergencyContactPhone: formData.emergencyContactPhone,
        });
      } else if (selectedRole === "hospital") {
        response = await authApi.registerHospital({
          hospitalName: formData.hospitalName,
          licenseNumber: formData.licenseNumber,
          email: formData.email,
          password: formData.password,
          phone: formData.phone,
          city: formData.city,
          address: formData.address,
          emergencyContactNumber: formData.emergencyContactNumber,
          department: formData.department,
          bedCount: formData.bedCount ? parseInt(formData.bedCount) : null,
        });
      } else if (selectedRole === "blood-bank") {
        response = await authApi.registerBloodBank({
          bloodBankName: formData.bloodBankName,
          registrationNumber: formData.registrationNumber,
          email: formData.email,
          password: formData.password,
          phone: formData.phone,
          city: formData.city,
          address: formData.address,
          contactPerson: formData.contactPerson,
          operatingHours: formData.operatingHours,
        });
      }

      const authData = response.data;
      login(authData);
      setSuccessMsg("Registration successful! Redirecting to dashboard...");

      setTimeout(() => {
        navigate("/");
      }, 1500);
    } catch (err) {
      console.error("Registration error:", err);
      const backendMessage =
        err.response?.data?.message ||
        "Registration failed. Please verify that the backend server is running on port 8080.";
      setErrorMsg(backendMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-container">
      <div className="register-content">
        {/* STEP 1: ROLE SELECTION */}
        {!selectedRole ? (
          <div>
            <div className="register-header">
              <div className="register-badge">
                <FaShieldAlt />
                <span>JOIN THE RESPONSE NETWORK</span>
              </div>
              <h1 className="register-title">
                Create Your <span>Account</span>
              </h1>
              <p className="register-subtitle">
                Select your role on the platform. Tailored registration fields and
                verification flows will open for you.
              </p>
            </div>

            <div className="role-grid">
              {roles.map((role) => {
                const Icon = role.icon;
                return (
                  <div
                    key={role.id}
                    className="role-card"
                    onClick={() => {
                      setSelectedRole(role.id);
                      window.scrollTo({ top: 120, behavior: "smooth" });
                    }}
                  >
                    <div>
                      <div className="role-icon-box">
                        <Icon />
                      </div>
                      <h3 className="role-name">{role.title}</h3>
                      <p className="role-desc">{role.desc}</p>
                    </div>
                    <div className="role-action">
                      Continue as {role.title} <FaArrowRight />
                    </div>
                  </div>
                );
              })}
            </div>

            <div style={{ textAlign: "center", fontSize: "14.5px", color: "#666" }}>
              Already have an account?{" "}
              <Link to="/login" style={{ color: "#ed1c24", fontWeight: "600" }}>
                Sign in here
              </Link>
            </div>
          </div>
        ) : (
          /* STEP 2: REGISTRATION FORM */
          <div className="form-card">
            <div className="form-top-bar">
              <div className="form-role-tag">
                <div className="role-icon-box" style={{ width: "42px", height: "42px", fontSize: "18px", margin: 0 }}>
                  {selectedRole === "donor" && <FaTint />}
                  {selectedRole === "patient" && <FaUser />}
                  {selectedRole === "hospital" && <FaHospital />}
                  {selectedRole === "blood-bank" && <FaHospitalAlt />}
                </div>
                <div>
                  <h3 style={{ textTransform: "capitalize" }}>
                    {selectedRole === "donor" && "Blood Donor Registration"}
                    {selectedRole === "patient" && "Patient / Recipient Registration"}
                    {selectedRole === "hospital" && "Hospital Facility Registration"}
                    {selectedRole === "blood-bank" && "Blood Bank Registration"}
                  </h3>
                  <small style={{ color: "#777" }}>Please fill out all required details accurately</small>
                </div>
              </div>

              <button
                type="button"
                className="btn-change-role"
                onClick={() => {
                  setSelectedRole(null);
                  setErrorMsg("");
                  setSuccessMsg("");
                }}
              >
                <FaArrowLeft style={{ marginRight: "6px" }} /> Change Role
              </button>
            </div>

            {errorMsg && (
              <div className="alert-box alert-error">
                <FaExclamationCircle />
                <span>{errorMsg}</span>
              </div>
            )}

            {successMsg && (
              <div className="alert-box alert-success">
                <FaCheckCircle />
                <span>{successMsg}</span>
              </div>
            )}

            <form onSubmit={handleSubmit}>
              <div className="form-grid">
                {/* DONOR / PATIENT FULL NAME */}
                {(selectedRole === "donor" || selectedRole === "patient") && (
                  <div className="form-group full-width">
                    <label>Full Name *</label>
                    <div className="form-input-wrapper">
                      <FaUser className="field-icon" />
                      <input
                        type="text"
                        name="fullName"
                        placeholder="e.g. John Doe"
                        value={formData.fullName}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                  </div>
                )}

                {/* HOSPITAL SPECIFIC */}
                {selectedRole === "hospital" && (
                  <>
                    <div className="form-group">
                      <label>Hospital Name *</label>
                      <div className="form-input-wrapper">
                        <FaHospital className="field-icon" />
                        <input
                          type="text"
                          name="hospitalName"
                          placeholder="e.g. City Care Multispeciality Hospital"
                          value={formData.hospitalName}
                          onChange={handleInputChange}
                          required
                        />
                      </div>
                    </div>
                    <div className="form-group">
                      <label>License / Registration ID *</label>
                      <div className="form-input-wrapper">
                        <FaShieldAlt className="field-icon" />
                        <input
                          type="text"
                          name="licenseNumber"
                          placeholder="e.g. HOSP-MED-2024-998"
                          value={formData.licenseNumber}
                          onChange={handleInputChange}
                          required
                        />
                      </div>
                    </div>
                    <div className="form-group">
                      <label>Emergency Department Line *</label>
                      <div className="form-input-wrapper">
                        <FaPhone className="field-icon" />
                        <input
                          type="text"
                          name="emergencyContactNumber"
                          placeholder="e.g. +91 40 2345 6789"
                          value={formData.emergencyContactNumber}
                          onChange={handleInputChange}
                          required
                        />
                      </div>
                    </div>
                    <div className="form-group">
                      <label>Department / ICU Facility</label>
                      <div className="form-input-wrapper">
                        <FaHospital className="field-icon" />
                        <input
                          type="text"
                          name="department"
                          placeholder="e.g. Emergency & Trauma Care"
                          value={formData.department}
                          onChange={handleInputChange}
                        />
                      </div>
                    </div>
                  </>
                )}

                {/* BLOOD BANK SPECIFIC */}
                {selectedRole === "blood-bank" && (
                  <>
                    <div className="form-group">
                      <label>Blood Bank Name *</label>
                      <div className="form-input-wrapper">
                        <FaHospitalAlt className="field-icon" />
                        <input
                          type="text"
                          name="bloodBankName"
                          placeholder="e.g. Red Cross Central Blood Center"
                          value={formData.bloodBankName}
                          onChange={handleInputChange}
                          required
                        />
                      </div>
                    </div>
                    <div className="form-group">
                      <label>Registration ID *</label>
                      <div className="form-input-wrapper">
                        <FaShieldAlt className="field-icon" />
                        <input
                          type="text"
                          name="registrationNumber"
                          placeholder="e.g. BB-REG-2024-110"
                          value={formData.registrationNumber}
                          onChange={handleInputChange}
                          required
                        />
                      </div>
                    </div>
                    <div className="form-group">
                      <label>Contact Person Name *</label>
                      <div className="form-input-wrapper">
                        <FaUser className="field-icon" />
                        <input
                          type="text"
                          name="contactPerson"
                          placeholder="e.g. Dr. Ramesh Kumar"
                          value={formData.contactPerson}
                          onChange={handleInputChange}
                          required
                        />
                      </div>
                    </div>
                    <div className="form-group">
                      <label>Operating Hours</label>
                      <div className="form-input-wrapper">
                        <FaCalendarAlt className="field-icon" />
                        <input
                          type="text"
                          name="operatingHours"
                          placeholder="e.g. 24 Hours / 7 Days"
                          value={formData.operatingHours}
                          onChange={handleInputChange}
                        />
                      </div>
                    </div>
                  </>
                )}

                {/* EMAIL */}
                <div className="form-group">
                  <label>Email Address *</label>
                  <div className="form-input-wrapper">
                    <FaEnvelope className="field-icon" />
                    <input
                      type="email"
                      name="email"
                      placeholder="you@example.com"
                      value={formData.email}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                </div>

                {/* PHONE */}
                <div className="form-group">
                  <label>Phone Number *</label>
                  <div className="form-input-wrapper">
                    <FaPhone className="field-icon" />
                    <input
                      type="tel"
                      name="phone"
                      placeholder="e.g. +91 98765 43210"
                      value={formData.phone}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                </div>

                {/* PASSWORD */}
                <div className="form-group">
                  <label>Password (Min. 6 chars) *</label>
                  <div className="form-input-wrapper">
                    <FaLock className="field-icon" />
                    <input
                      type={showPassword ? "text" : "password"}
                      name="password"
                      placeholder="Create a strong password"
                      value={formData.password}
                      onChange={handleInputChange}
                      required
                    />
                    <button
                      type="button"
                      className="btn-eye-toggle"
                      onClick={() => setShowPassword(!showPassword)}
                    >
                      {showPassword ? <FaEyeSlash /> : <FaEye />}
                    </button>
                  </div>
                </div>

                {/* CONFIRM PASSWORD */}
                <div className="form-group">
                  <label>Confirm Password *</label>
                  <div className="form-input-wrapper">
                    <FaLock className="field-icon" />
                    <input
                      type={showConfirmPassword ? "text" : "password"}
                      name="confirmPassword"
                      placeholder="Re-enter password"
                      value={formData.confirmPassword}
                      onChange={handleInputChange}
                      required
                    />
                    <button
                      type="button"
                      className="btn-eye-toggle"
                      onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                    >
                      {showConfirmPassword ? <FaEyeSlash /> : <FaEye />}
                    </button>
                  </div>
                </div>

                {/* DONOR SPECIFIC: BLOOD GROUP, DOB, GENDER */}
                {selectedRole === "donor" && (
                  <>
                    <div className="form-group">
                      <label>Blood Group *</label>
                      <div className="form-input-wrapper">
                        <FaTint className="field-icon" />
                        <select
                          name="bloodGroup"
                          value={formData.bloodGroup}
                          onChange={handleInputChange}
                          required
                        >
                          {["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"].map((bg) => (
                            <option key={bg} value={bg}>
                              {bg}
                            </option>
                          ))}
                        </select>
                      </div>
                    </div>

                    <div className="form-group">
                      <label>Date of Birth</label>
                      <div className="form-input-wrapper">
                        <FaCalendarAlt className="field-icon" />
                        <input
                          type="date"
                          name="dateOfBirth"
                          value={formData.dateOfBirth}
                          onChange={handleInputChange}
                        />
                      </div>
                    </div>

                    <div className="form-group">
                      <label>Gender</label>
                      <div className="form-input-wrapper">
                        <FaUser className="field-icon" />
                        <select
                          name="gender"
                          value={formData.gender}
                          onChange={handleInputChange}
                        >
                          <option value="Male">Male</option>
                          <option value="Female">Female</option>
                          <option value="Other">Other</option>
                          <option value="Prefer not to say">Prefer not to say</option>
                        </select>
                      </div>
                    </div>
                  </>
                )}

                {/* PATIENT SPECIFIC: EMERGENCY CONTACT */}
                {selectedRole === "patient" && (
                  <>
                    <div className="form-group">
                      <label>Blood Group (If known)</label>
                      <div className="form-input-wrapper">
                        <FaTint className="field-icon" />
                        <select
                          name="bloodGroup"
                          value={formData.bloodGroup}
                          onChange={handleInputChange}
                        >
                          <option value="">Unknown / Pending Lab Test</option>
                          {["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"].map((bg) => (
                            <option key={bg} value={bg}>
                              {bg}
                            </option>
                          ))}
                        </select>
                      </div>
                    </div>

                    <div className="form-group">
                      <label>Emergency Contact Person</label>
                      <div className="form-input-wrapper">
                        <FaUser className="field-icon" />
                        <input
                          type="text"
                          name="emergencyContactName"
                          placeholder="e.g. Sarah Smith (Spouse/Relative)"
                          value={formData.emergencyContactName}
                          onChange={handleInputChange}
                        />
                      </div>
                    </div>

                    <div className="form-group">
                      <label>Emergency Contact Phone</label>
                      <div className="form-input-wrapper">
                        <FaPhone className="field-icon" />
                        <input
                          type="tel"
                          name="emergencyContactPhone"
                          placeholder="e.g. +91 98765 11223"
                          value={formData.emergencyContactPhone}
                          onChange={handleInputChange}
                        />
                      </div>
                    </div>
                  </>
                )}

                {/* CITY / DISTRICT */}
                <div className="form-group">
                  <label>City / District *</label>
                  <div className="form-input-wrapper">
                    <FaMapMarkerAlt className="field-icon" />
                    <input
                      type="text"
                      name="city"
                      placeholder="e.g. Hyderabad"
                      value={formData.city}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                </div>

                {/* ADDRESS */}
                <div className="form-group full-width">
                  <label>Full Address *</label>
                  <div className="form-input-wrapper">
                    <FaMapMarkerAlt className="field-icon" />
                    <input
                      type="text"
                      name="address"
                      placeholder="Street, area, landmarks, postal code"
                      value={formData.address}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                </div>

                {/* DONOR AVAILABILITY TOGGLE */}
                {selectedRole === "donor" && (
                  <div className="form-group full-width" style={{ marginTop: "8px" }}>
                    <label className="checkbox-label">
                      <input
                        type="checkbox"
                        name="isAvailable"
                        checked={formData.isAvailable}
                        onChange={handleInputChange}
                      />
                      <span>
                        <strong>I am actively available for emergency blood donations.</strong>
                        <br />
                        <small style={{ color: "#777" }}>
                          You can pause or resume this availability at any time from your dashboard.
                        </small>
                      </span>
                    </label>
                  </div>
                )}
              </div>

              <button
                type="submit"
                className="btn-register-submit"
                disabled={loading}
              >
                {loading ? "Creating Account..." : "Complete Registration"} <FaArrowRight />
              </button>
            </form>
          </div>
        )}
      </div>
    </div>
  );
}

export default Register;
