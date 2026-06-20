import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { motion } from 'framer-motion'
import { useToast } from '../context/ToastContext'
import { createUser, parseError } from '../services/api'

export default function RegisterPage() {
  const navigate = useNavigate()
  const toast = useToast()
  const [form, setForm] = useState({ email: '', userName: '', password: '' })
  const [loading, setLoading] = useState(false)

  const handleChange = (e) => setForm(f => ({ ...f, [e.target.name]: e.target.value }))

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    try {
      await createUser(form)
      toast.success('¡Cuenta creada! Revisa tu correo para verificar tu cuenta.')
      navigate('/verify-email', { state: { email: form.email } })
    } catch (err) {
      toast.error(parseError(err))
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-obsidian-950 flex items-center justify-center px-6 py-12 relative overflow-hidden">
      {/* Ambient background */}
      <div
        className="absolute inset-0 pointer-events-none"
        style={{
          background: 'radial-gradient(ellipse 80% 60% at 50% 0%, rgba(212,160,23,0.06) 0%, transparent 60%)',
        }}
      />

      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5, ease: [0.22, 1, 0.36, 1] }}
        className="w-full max-w-sm"
      >
        {/* Logo */}
        <Link to="/login" className="flex items-center justify-center gap-3 mb-10 group">
          <div className="w-9 h-9 rounded-full bg-gold-gradient flex items-center justify-center">
            <span className="text-obsidian-950 font-display font-black text-sm">AV</span>
          </div>
          <span className="font-display font-bold text-xl gold-text">AuctionVault</span>
        </Link>

        <div className="card border border-gold-600/20 p-8">
          <div className="mb-7">
            <p className="section-label">Únete ahora</p>
            <h2 className="font-display font-bold text-2xl text-white">Crear cuenta</h2>
            <p className="text-obsidian-400 text-sm mt-1">Comienza a pujar en segundos</p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="section-label block mb-1.5" htmlFor="reg-email">Correo electrónico</label>
              <input
                id="reg-email"
                name="email"
                type="email"
                required
                value={form.email}
                onChange={handleChange}
                placeholder="tu@correo.com"
                className="input-field"
              />
            </div>

            <div>
              <label className="section-label block mb-1.5" htmlFor="reg-userName">Nombre de usuario</label>
              <input
                id="reg-userName"
                name="userName"
                type="text"
                required
                value={form.userName}
                onChange={handleChange}
                placeholder="ej. pujador_pro"
                className="input-field"
              />
            </div>

            <div>
              <label className="section-label block mb-1.5" htmlFor="reg-password">Contraseña</label>
              <input
                id="reg-password"
                name="password"
                type="password"
                required
                value={form.password}
                onChange={handleChange}
                placeholder="Mínimo 8 caracteres"
                className="input-field"
              />
            </div>

            <motion.button
              type="submit"
              disabled={loading}
              whileHover={{ scale: 1.01 }}
              whileTap={{ scale: 0.98 }}
              className="btn-gold w-full flex items-center justify-center gap-2 py-3.5 mt-2"
            >
              {loading ? 'Creando cuenta...' : 'Crear cuenta'}
            </motion.button>
          </form>

          <p className="text-center text-obsidian-500 text-sm mt-6">
            ¿Ya tienes cuenta?{' '}
            <Link to="/login" className="text-gold-400 hover:text-gold-300 font-medium transition-colors">
              Inicia sesión
            </Link>
          </p>
        </div>
      </motion.div>
    </div>
  )
}
