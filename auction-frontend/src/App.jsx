import React from 'react'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext'
import { ToastProvider } from './context/ToastContext'
import ProtectedLayout from './components/ProtectedRoute'

// Pages
import LoginPage        from './pages/LoginPage'
import RegisterPage     from './pages/RegisterPage'
import VerifyEmailPage  from './pages/VerifyEmailPage'
import DashboardPage    from './pages/DashboardPage'
import MyAuctionsPage   from './pages/MyAuctionsPage'
import AuctionDetailPage from './pages/AuctionDetailPage'
import LiveAuctionPage  from './pages/LiveAuctionPage'
import NotFoundPage     from './pages/NotFoundPage'

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <ToastProvider>
          <Routes>
            {/* Public */}
            <Route path="/login"        element={<LoginPage />} />
            <Route path="/register"     element={<RegisterPage />} />
            <Route path="/verify-email" element={<VerifyEmailPage />} />

            {/* Protected — ProtectedLayout es el guard + el layout con Navbar + Outlet */}
            <Route element={<ProtectedLayout />}>
              <Route path="/dashboard"                element={<DashboardPage />} />
              <Route path="/my-auctions"              element={<MyAuctionsPage />} />
              <Route path="/auction/:auctionId"       element={<AuctionDetailPage />} />
              <Route path="/auction/:auctionId/live"  element={<LiveAuctionPage />} />
            </Route>

            {/* Redirects */}
            <Route path="/" element={<Navigate to="/dashboard" replace />} />
            <Route path="*" element={<NotFoundPage />} />
          </Routes>
        </ToastProvider>
      </AuthProvider>
    </BrowserRouter>
  )
}
