import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Attach JWT token to requests automatically
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Centralized error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      // Token expired or invalid
      // Optional: localStorage.removeItem("token");
    }
    return Promise.reject(error);
  }
);

// ==================== AUTH APIS ====================
export const authApi = {
  login: (credentials) => api.post("/auth/login", credentials),
  registerDonor: (donorData) => api.post("/auth/register/donor", donorData),
  registerPatient: (patientData) => api.post("/auth/register/patient", patientData),
  registerHospital: (hospitalData) => api.post("/auth/register/hospital", hospitalData),
  registerBloodBank: (bankData) => api.post("/auth/register/blood-bank", bankData),
  getCurrentUser: () => api.get("/auth/me"),
};

// ==================== DONOR APIS ====================
export const donorApi = {
  search: (bloodGroup, city) =>
    api.get("/donors/search", { params: { bloodGroup, city } }),
  getMyProfile: () => api.get("/donors/me"),
  updateAvailability: (isAvailable) =>
    api.put("/donors/me/availability", { isAvailable }),
  getDashboardStats: () => api.get("/donors/me/dashboard"),
  getDonations: () => api.get("/donors/me/donations"),
};

// ==================== BLOOD REQUEST APIS ====================
export const requestApi = {
  create: (requestData) => api.post("/blood-requests", requestData),
  filter: (params) => api.get("/blood-requests", { params }),
  getById: (id) => api.get(`/blood-requests/${id}`),
  updateStatus: (id, status) =>
    api.put(`/blood-requests/${id}/status`, { status }),
  getMyRequests: () => api.get("/blood-requests/me"),
};

// ==================== BLOOD BANK APIS ====================
export const bloodBankApi = {
  getAll: (city) => api.get("/blood-banks", { params: { city } }),
  getById: (id) => api.get(`/blood-banks/${id}`),
  getInventory: (id) => api.get(`/blood-banks/${id}/inventory`),
  getMyInventory: () => api.get("/blood-banks/me/inventory"),
  updateMyInventory: (inventoryData) =>
    api.put("/blood-banks/me/inventory", inventoryData),
};

// ==================== NOTIFICATIONS ====================
export const notificationApi = {
  getAll: () => api.get("/notifications"),
  getUnreadCount: () => api.get("/notifications/unread-count"),
  markAsRead: (id) => api.put(`/notifications/${id}/read`),
  markAllAsRead: () => api.put("/notifications/read-all"),
};

// ==================== ADMIN APIS ====================
export const adminApi = {
  getStats: () => api.get("/admin/stats"),
  getUsers: (role) => api.get("/admin/users", { params: { role } }),
  verifyUser: (id) => api.put(`/admin/users/${id}/verify`),
  toggleStatus: (id) => api.put(`/admin/users/${id}/toggle-status`),
  getAuditLogs: () => api.get("/admin/audit-logs"),
};

export default api;
