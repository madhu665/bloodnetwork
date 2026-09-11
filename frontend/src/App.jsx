import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import MainLayout from "./components/MainLayout";
import Home from "./pages/Home";
import About from "./pages/About";
import FindDonors from "./pages/FindDonors";
import RequestBlood from "./pages/RequestBlood";
import BloodBanks from "./pages/BloodBanks";
import BloodBankDetails from "./pages/BloodBankDetails";
import TrackDonor from "./pages/TrackDonor";
import BloodRequests from "./pages/BloodRequests";
import Dashboard from "./pages/Dashboard";
import Notifications from "./pages/Notifications";
import Contact from "./pages/Contact";
import Login from "./pages/Login";
import Register from "./pages/Register";
import NotFound from "./pages/NotFound";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          {/* Full-screen Split-screen Auth Pages */}
          <Route path="/login" element={<Login />} />

          {/* Public Portal Pages with Shared Navbar & Footer */}
          <Route path="/" element={<MainLayout />}>
            <Route index element={<Home />} />
            <Route path="about" element={<About />} />
            <Route path="donors" element={<FindDonors />} />
            <Route path="requests" element={<BloodRequests />} />
            <Route path="blood-requests" element={<BloodRequests />} />
            <Route path="request-blood" element={<RequestBlood />} />
            <Route path="track" element={<TrackDonor />} />
            <Route path="track/:requestId" element={<TrackDonor />} />
            <Route path="blood-banks" element={<BloodBanks />} />
            <Route path="blood-banks/:id" element={<BloodBankDetails />} />
            <Route path="dashboard" element={<Dashboard />} />
            <Route path="notifications" element={<Notifications />} />
            <Route path="contact" element={<Contact />} />
            <Route path="register" element={<Register />} />
            {/* Catch-all 404 Route */}
            <Route path="*" element={<NotFound />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;