import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { motion } from 'framer-motion'
import { useAuth } from '../context/AuthContext'
import { useToast } from '../context/ToastContext'
import { login, resendVerificationEmail, parseError } from '../services/api'
import GoldCoin from '../components/GoldCoin'

// Floating particles background
function Particles() {
  const particles = Array.from({ length: 18 }, (_, i) => ({
    id: i,
    size: Math.random() * 4 + 2,
    x: Math.random() * 100,
    y: Math.random() * 100,
    duration: Math.random() * 8 + 5,
    delay: Math.random() * 4,
    opacity: Math.random() * 0.4 + 0.1,
  }))

  return (
    <div className="absolute inset-0 overflow-hidden pointer-events-none">
      {particles.map(p => (
        <motion.div
          key={p.id}
          className="absolute rounded-full bg-gold-400"
          style={{
            width: p.size,
            height: p.size,
            left: `${p.x}%`,
            top: `${p.y}%`,
            opacity: p.opacity,
          }}
          animate={{
            y: [0, -40, 0],
            x: [0, Math.random() * 20 - 10, 0],
            opacity: [p.opacity, p.opacity * 2.5, p.opacity],
          }}
          transition={{
            duration: p.duration,
            delay: p.delay,
            repeat: Infinity,
            ease: 'easeInOut',
          }}
        />
      ))}
    </div>
  )
}

export default function LoginPage() {
  const navigate = useNavigate()
  const { signIn } = useAuth()
  const toast = useToast()
  const [form, setForm] = useState({ email: '', password: '' })
  const [loading, setLoading] = useState(false)

  const handleChange = (e) => setForm(f => ({ ...f, [e.target.name]: e.target.value }))

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (loading) return
    setLoading(true)
    try {
      const res = await login(form)
      signIn(res.data.accessToken, res.data.userData)
      toast.success(`Bienvenido, ${res.data.userData.userName}`)
      navigate('/dashboard')
    } catch (err) {
      console.log('=== LOGIN ERROR ===')
      console.log('Status:', err.response?.status)
      console.log('Data:', err.response?.data)
      const status = err.response?.status
      const rawData = err.response?.data
      const rawMsg = typeof rawData === 'string' ? rawData.toLowerCase() : ''

      if (status === 400) {
        toast.error(parseError(err))
      } else if (status === 401 || status === 403 || status === 406) {
        if (rawMsg.includes('verif') || rawMsg.includes('verificad')) {
          // Usuario no verificado — reenviar correo con el email del formulario
          console.log('=== REENVIO EMAIL ===')
          console.log('Email del formulario:', form.email)
          console.log('form completo:', form)
          try {
            const res2 = await resendVerificationEmail(form.email)
            const confirmation = typeof res2.data === 'string' ? res2.data : 'Correo reenviado.'
            toast.warning(`Cuenta no verificada. ${confirmation}`)
            navigate('/verify-email', { state: { email: form.email } })
          } catch (resendErr) {
            console.log('=== ERROR REENVIO ===')
            console.log('Status:', resendErr.response?.status)
            console.log('Data:', resendErr.response?.data)
            console.log('Email que se intentó enviar:', form.email)
            const resendStatus = resendErr.response?.status
            const resendMsg = typeof resendErr.response?.data === 'string' && resendErr.response.data.trim()
              ? resendErr.response.data
              : parseError(resendErr)
            if (resendStatus === 406) {
              toast.error(resendMsg)
            } else {
              toast.warning('Cuenta no verificada. Revisa tu correo e ingresa el código.')
              navigate('/verify-email', { state: { email: form.email } })
            }
          }
        } else {
          // Mostrar el mensaje exacto del back (ej: "Contraseña incorrecta", "El usuario no se encontro")
          toast.error(typeof rawData === 'string' && rawData.trim() ? rawData : parseError(err))
        }
      } else if (status === 404) {
        toast.error('No existe una cuenta con ese correo electrónico.')
      } else {
        toast.error(parseError(err))
      }
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-obsidian-950 flex overflow-hidden relative">
      <Particles />

      {/* Background grid lines */}
      <div
        className="absolute inset-0 opacity-5 pointer-events-none"
        style={{
          backgroundImage: 'linear-gradient(rgba(212,160,23,0.5) 1px, transparent 1px), linear-gradient(90deg, rgba(212,160,23,0.5) 1px, transparent 1px)',
          backgroundSize: '60px 60px',
        }}
      />

      {/* Left — Coin showcase */}
      <motion.div
        initial={{ opacity: 0, x: -40 }}
        animate={{ opacity: 1, x: 0 }}
        transition={{ duration: 0.8, ease: [0.22, 1, 0.36, 1] }}
        className="hidden lg:flex flex-1 flex-col items-center justify-center relative px-12"
      >
        {/* Large ambient glow behind coin */}
        <div
          className="absolute rounded-full pointer-events-none"
          style={{
            width: 500,
            height: 500,
            background: 'radial-gradient(circle, rgba(212,160,23,0.08) 0%, transparent 70%)',
          }}
        />

        {/* Orbit ring */}
        <motion.div
          className="absolute rounded-full border border-gold-600/20"
          style={{ width: 380, height: 380 }}
          animate={{ rotate: 360 }}
          transition={{ duration: 20, repeat: Infinity, ease: 'linear' }}
        >
          {/* Orbit dot */}
          <div
            className="absolute w-3 h-3 rounded-full bg-gold-400 shadow-lg"
            style={{ top: -6, left: '50%', transform: 'translateX(-50%)' }}
          />
        </motion.div>
        <motion.div
          className="absolute rounded-full border border-gold-600/10"
          style={{ width: 480, height: 480 }}
          animate={{ rotate: -360 }}
          transition={{ duration: 30, repeat: Infinity, ease: 'linear' }}
        >
          <div
            className="absolute w-2 h-2 rounded-full bg-gold-600/60"
            style={{ top: -4, left: '50%', transform: 'translateX(-50%)' }}
          />
        </motion.div>

        {/* The coin */}
        <motion.div
          initial={{ scale: 0.5, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          transition={{ duration: 0.9, delay: 0.3, ease: [0.22, 1, 0.36, 1] }}
          className="relative z-10"
        >
          <GoldCoin size={240} />
        </motion.div>

        {/* Text below coin */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.7, duration: 0.6 }}
          className="text-center mt-12 z-10"
        >
          <h1 className="font-display font-black text-4xl gold-text text-shadow-gold mb-3">
            AuctionVault
          </h1>
          <p className="text-obsidian-400 text-sm font-medium max-w-xs leading-relaxed">
            Subastas en tiempo real. Cada puja, cada segundo, completamente en vivo.
          </p>

          {/* Stats */}
          <div className="flex items-center justify-center gap-8 mt-8">
            {[['∞', 'Subastas'], ['0ms', 'Latencia'], ['100%', 'En Vivo']].map(([val, label]) => (
              <div key={label} className="text-center">
                <p className="font-display font-bold text-xl gold-text">{val}</p>
                <p className="text-obsidian-500 text-xs tracking-widest uppercase font-mono">{label}</p>
              </div>
            ))}
          </div>
        </motion.div>
      </motion.div>

      {/* Vertical divider */}
      <div className="hidden lg:block w-px bg-gradient-to-b from-transparent via-gold-600/30 to-transparent self-stretch" />

      {/* Right — Login form */}
      <div className="flex-1 flex flex-col items-center justify-center px-6 py-12 lg:px-12 relative">

        {/* Mobile logo */}
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
          className="lg:hidden flex flex-col items-center gap-4 mb-10"
        >
          <GoldCoin size={100} />
          <h1 className="font-display font-black text-3xl gold-text">AuctionVault</h1>
        </motion.div>

        <motion.div
          initial={{ opacity: 0, x: 30 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 0.6, delay: 0.2, ease: [0.22, 1, 0.36, 1] }}
          className="w-full max-w-sm"
        >
          {/* Form header */}
          <div className="mb-8">
            <p className="section-label">Acceso seguro</p>
            <h2 className="font-display font-bold text-3xl text-white">Inicia sesión</h2>
            <p className="text-obsidian-400 text-sm mt-1.5">Entra a tus subastas en tiempo real</p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="section-label block mb-1.5" htmlFor="email">Correo electrónico</label>
              <input
                id="email"
                name="email"
                type="email"
                required
                autoComplete="email"
                value={form.email}
                onChange={handleChange}
                placeholder="tu@correo.com"
                className="input-field"
              />
            </div>

            <div>
              <label className="section-label block mb-1.5" htmlFor="password">Contraseña</label>
              <input
                id="password"
                name="password"
                type="password"
                required
                autoComplete="current-password"
                value={form.password}
                onChange={handleChange}
                placeholder="••••••••"
                className="input-field"
              />
            </div>

            <motion.button
              type="submit"
              disabled={loading}
              whileHover={{ scale: 1.01 }}
              whileTap={{ scale: 0.98 }}
              className="btn-gold w-full mt-2 flex items-center justify-center gap-2 py-3.5"
            >
              {loading ? (
                <>
                  <svg className="w-4 h-4 animate-spin" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <circle cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" className="opacity-25" />
                    <path fill="currentColor" d="M4 12a8 8 0 018-8v4l3-3-3-3V4a10 10 0 100 20v-4l-3 3 3 3v-4a10 10 0 01-8-8z" className="opacity-75" />
                  </svg>
                  Ingresando...
                </>
              ) : (
                <>
                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1" />
                  </svg>
                  Entrar
                </>
              )}
            </motion.button>
          </form>

          <p className="text-center text-obsidian-500 text-sm mt-6">
            ¿Sin cuenta?{' '}
            <Link to="/register" className="text-gold-400 hover:text-gold-300 font-medium transition-colors">
              Regístrate gratis
            </Link>
          </p>
        </motion.div>
      </div>
    </div>
  )
}
