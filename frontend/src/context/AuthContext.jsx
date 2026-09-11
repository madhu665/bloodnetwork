import React, { createContext, useContext, useState, useEffect } from "react";

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem("user");
    try {
      return saved ? JSON.parse(saved) : null;
    } catch {
      return null;
    }
  });

  const [token, setToken] = useState(() => localStorage.getItem("token") || null);

  const login = (authData) => {
    setToken(authData.token);
    setUser({
      id: authData.id,
      fullName: authData.fullName,
      email: authData.email,
      role: authData.role,
      city: authData.city,
      verified: authData.verified,
    });
    localStorage.setItem("token", authData.token);
    localStorage.setItem(
      "user",
      JSON.stringify({
        id: authData.id,
        fullName: authData.fullName,
        email: authData.email,
        role: authData.role,
        city: authData.city,
        verified: authData.verified,
      })
    );
  };

  const logout = () => {
    setToken(null);
    setUser(null);
    localStorage.removeItem("token");
    localStorage.removeItem("user");
  };

  return (
    <AuthContext.Provider value={{ user, token, isAuthenticated: !!token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}
