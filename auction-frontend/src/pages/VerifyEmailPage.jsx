import React, { useState } from 'react'
import { useNavigate, useLocation, Link } from 'react-router-dom'
import { motion } from 'framer-motion'
import { useToast } from '../context/ToastContext'
import { verifyEmail, parseError } from '../services/api'

export default function VerifyEmailPage() {
  const navigate = useNavigate()
  const location = useLocation()
  const toast = useToast()

  // El email llega desde RegisterPage o LoginPage via navigate state
  const [email, setEmail] = useState(location.state?.email || '')
  const [code, setCode] = useState('')
  const [loading, setLoading] = useState(false)
  const [emailMissing, setEmailMissing] = useState(!location.state?.email)

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (loading) return

    if (!email.trim()) {
      toast.error('No se encontró el correo. Ingresa tu correo manualmente.')
      setEmailMissing(true)
      return
    }
    if (!code.trim()) {
      toast.error('Ingresa el código de verificación.')
      return
    }

    setLoading(true)
    try {
      // Log temporal para confirmar qué se envía
      const tokenNum = parseInt(code.trim(), 10)
      console.log('Enviando verificación:', { email: email.trim(), token: tokenNum })
      await verifyEmail({
        email: email.trim(),
        token: tokenNum, // el back espera número entero 
      })
      toast.success('¡Cuenta verificada! Ya puedes iniciar sesión.')
      navigate('/login')
    } catch (err) {
      console.log('Verify error:', err.response?.status, JSON.stringify(err.response?.data))
      const status = err.response?.status
      if (status === 403 || status === 406) {
        const raw = err.response?.data
        const msg = typeof raw === 'string' ? raw : parseError(err)
        toast.error(msg || 'Código incorrecto o expirado.')
      } else if (status === 400) {
        toast.error(parseError(err))
      } else {
        toast.error(parseError(err))
      }
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-obsidian-950 flex items-center justify-center px-6 relative overflow-hidden">
      <div
        className="absolute inset-0 pointer-events-none"
        style={{ background: 'radial-gradient(ellipse 60% 50% at 50% 0%, rgba(212,160,23,0.06) 0%, transparent 60%)' }}
      />

      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
        className="w-full max-w-sm"
      >
        <Link to="/login" className="flex items-center justify-center gap-3 mb-10">
          <div className="w-9 h-9 rounded-full bg-gold-gradient flex items-center justify-center">
            <span className="text-obsidian-950 font-display font-black text-sm">AV</span>
          </div>
          <span className="font-display font-bold text-xl gold-text">AuctionVault</span>
        </Link>

        <div className="card border border-gold-600/20 p-8 text-center">
          <motion.div
            initial={{ scale: 0 }}
            animate={{ scale: 1 }}
            transition={{ delay: 0.2, type: 'spring', stiffness: 200 }}
            className="w-16 h-16 rounded-full bg-gold-500/15 border border-gold-500/30 flex items-center justify-center mx-auto mb-6"
          >
            <svg className="w-8 h-8 text-gold-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.5}
                d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
            </svg>
          </motion.div>

          <h2 className="font-display font-bold text-2xl text-white mb-2">Verifica tu correo</h2>
          <p className="text-obsidian-400 text-sm mb-1">Enviamos un código a</p>

          {/* Si no llegó el email, permitir ingresarlo manualmente */}
          {emailMissing ? (
            <input
              type="email"
              value={email}
              onChange={e => setEmail(e.target.value)}
              placeholder="Ingresa tu correo"
              className="input-field text-center mb-4"
            />
          ) : (
            <p className="text-gold-400 font-mono font-semibold text-sm mb-6 break-all">{email}</p>
          )}

          <form onSubmit={handleSubmit} className="space-y-4 text-left">
            <div>
              <label className="section-label block mb-1.5" htmlFor="verify-code">
                Código de verificación
              </label>
              <input
                id="verify-code"
                type="text"
                inputMode="numeric"
                required
                maxLength={10}
                value={code}
                onChange={e => setCode(e.target.value.replace(/\D/g, ''))}
                placeholder="ej. 7535"
                className="input-field text-center text-2xl font-mono tracking-widest"
              />
            </div>

            <motion.button
              type="submit"
              disabled={loading}
              whileHover={{ scale: 1.01 }}
              whileTap={{ scale: 0.98 }}
              className="btn-gold w-full py-3.5 flex items-center justify-center gap-2"
            >
              {loading ? 'Verificando...' : 'Verificar cuenta'}
            </motion.button>
          </form>

          <div className="mt-6 pt-6 border-t border-obsidian-700/40">
            <p className="text-obsidian-500 text-sm mb-3">¿No recibiste el código?</p>
            <Link to="/login" className="text-gold-400 hover:text-gold-300 font-medium text-sm transition-colors">
              Volver al inicio de sesión
            </Link>
          </div>
        </div>
      </motion.div>
    </div>
  )
}