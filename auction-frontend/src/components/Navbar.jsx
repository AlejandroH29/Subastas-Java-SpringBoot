import React, { useState } from 'react'
import { Link, useNavigate, useLocation } from 'react-router-dom'
import { motion, AnimatePresence } from 'framer-motion'
import { useAuth } from '../context/AuthContext'

const navLinks = [
  { label: 'Dashboard', to: '/dashboard' },
  { label: 'Mis Subastas', to: '/my-auctions' },
]

export default function Navbar() {
  const { user, signOut } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()
  const [mobileOpen, setMobileOpen] = useState(false)
  const [userMenuOpen, setUserMenuOpen] = useState(false)

  const handleLogout = () => {
    signOut()
    navigate('/login')
  }

  return (
    <>
      <nav className="fixed top-0 left-0 right-0 z-50 bg-obsidian-950/80 backdrop-blur-xl border-b border-gold-600/20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">

            {/* Logo */}
            <Link to="/dashboard" className="flex items-center gap-3 group">
              <div className="w-8 h-8 rounded-full bg-gold-gradient flex items-center justify-center glow-gold">
                <span className="text-obsidian-950 font-display font-black text-xs">AV</span>
              </div>
              <span className="font-display font-bold text-lg gold-text tracking-wide hidden sm:block">
                AuctionVault
              </span>
            </Link>

            {/* Desktop nav links */}
            <div className="hidden md:flex items-center gap-1">
              {navLinks.map(link => (
                <Link
                  key={link.to}
                  to={link.to}
                  className={`px-4 py-2 rounded-lg text-sm font-medium tracking-wide transition-all duration-200 cursor-pointer
                    ${location.pathname === link.to
                      ? 'text-gold-400 bg-gold-500/10 border border-gold-500/30'
                      : 'text-obsidian-300 hover:text-white hover:bg-obsidian-700/60'
                    }`}
                >
                  {link.label}
                </Link>
              ))}
            </div>

            {/* Right side */}
            <div className="flex items-center gap-3">
              {/* User menu */}
              <div className="relative">
                <button
                  onClick={() => setUserMenuOpen(!userMenuOpen)}
                  className="flex items-center gap-2.5 px-3 py-2 rounded-xl border border-obsidian-600/40 hover:border-gold-500/40 transition-all duration-200 cursor-pointer group"
                >
                  <div className="w-7 h-7 rounded-full bg-gold-gradient flex items-center justify-center">
                    <span className="text-obsidian-950 font-bold text-xs">
                      {user?.userName?.[0]?.toUpperCase() || 'U'}
                    </span>
                  </div>
                  <span className="text-sm text-obsidian-200 font-medium hidden sm:block max-w-[120px] truncate">
                    {user?.userName}
                  </span>
                  <svg className="w-3.5 h-3.5 text-obsidian-400 group-hover:text-gold-400 transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
                  </svg>
                </button>

                <AnimatePresence>
                  {userMenuOpen && (
                    <motion.div
                      initial={{ opacity: 0, y: -8, scale: 0.95 }}
                      animate={{ opacity: 1, y: 0, scale: 1 }}
                      exit={{ opacity: 0, y: -8, scale: 0.95 }}
                      transition={{ duration: 0.15 }}
                      className="absolute right-0 top-full mt-2 w-52 card border border-obsidian-600/40 shadow-2xl overflow-hidden"
                    >
                      <div className="px-4 py-3 border-b border-obsidian-700/40">
                        <p className="text-xs text-obsidian-400 font-mono">Conectado como</p>
                        <p className="text-sm font-semibold text-white truncate">{user?.email}</p>
                      </div>
                      <button
                        onClick={handleLogout}
                        className="w-full flex items-center gap-3 px-4 py-3 text-sm text-red-400 hover:bg-red-500/10 transition-colors cursor-pointer"
                      >
                        <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                        </svg>
                        Cerrar sesión
                      </button>
                    </motion.div>
                  )}
                </AnimatePresence>
              </div>

              {/* Mobile hamburger */}
              <button
                onClick={() => setMobileOpen(true)}
                className="md:hidden w-9 h-9 rounded-lg border border-obsidian-600/40 flex flex-col items-center justify-center gap-1 hover:border-gold-500/40 transition-all cursor-pointer"
              >
                <span className="w-4 h-0.5 bg-obsidian-300 rounded" />
                <span className="w-4 h-0.5 bg-obsidian-300 rounded" />
                <span className="w-3 h-0.5 bg-obsidian-300 rounded self-start ml-0.5" />
              </button>
            </div>
          </div>
        </div>
      </nav>

      {/* Mobile menu overlay */}
      <AnimatePresence>
        {mobileOpen && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="fixed inset-0 z-[100] bg-obsidian-950/95 backdrop-blur-xl flex flex-col p-6"
          >
            <div className="flex items-center justify-between">
              <div className="flex items-center gap-3">
                <div className="w-8 h-8 rounded-full bg-gold-gradient flex items-center justify-center">
                  <span className="text-obsidian-950 font-display font-black text-xs">AV</span>
                </div>
                <span className="font-display font-bold text-lg gold-text">AuctionVault</span>
              </div>
              <button
                onClick={() => setMobileOpen(false)}
                className="w-9 h-9 rounded-full border border-obsidian-600/40 flex items-center justify-center hover:border-red-500/40 transition-all cursor-pointer"
              >
                <svg className="w-4 h-4 text-obsidian-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            </div>

            <div className="flex flex-col gap-3 mt-12">
              {navLinks.map((link, i) => (
                <motion.div
                  key={link.to}
                  initial={{ opacity: 0, x: -20 }}
                  animate={{ opacity: 1, x: 0 }}
                  transition={{ delay: i * 0.08 }}
                >
                  <Link
                    to={link.to}
                    onClick={() => setMobileOpen(false)}
                    className="block py-4 text-2xl font-display font-bold text-white hover:text-gold-400 transition-colors border-b border-obsidian-800/60"
                  >
                    {link.label}
                  </Link>
                </motion.div>
              ))}
            </div>

            <div className="mt-auto">
              <button
                onClick={handleLogout}
                className="w-full py-4 text-red-400 font-semibold border border-red-500/30 rounded-xl hover:bg-red-500/10 transition-all cursor-pointer"
              >
                Cerrar sesión
              </button>
            </div>
          </motion.div>
        )}
      </AnimatePresence>

      {/* Click outside to close user menu */}
      {userMenuOpen && (
        <div className="fixed inset-0 z-40" onClick={() => setUserMenuOpen(false)} />
      )}
    </>
  )
}
