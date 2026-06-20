import React, { useState } from 'react'
import { motion, AnimatePresence } from 'framer-motion'
import { createAuction } from '../services/api'
import { parseError } from '../services/api'
import { useToast } from '../context/ToastContext'

export default function CreateAuctionModal({ open, onClose, onCreated }) {
  const toast = useToast()
  const [loading, setLoading] = useState(false)
  const [form, setForm] = useState({
    title: '',
    description: '',
    startTime: '',
    endTime: '',
    startingPrice: '',
  })

  const handleChange = (e) => {
    setForm(f => ({ ...f, [e.target.name]: e.target.value }))
  }

  const handleSubmit = async (e) => {
  e.preventDefault()
  if (loading) return
  setLoading(true)
  try {
    const payload = {
      title: form.title,
      description: form.description,
      startTime: form.startTime,
      endTime: form.endTime,
      startingPrice: parseFloat(form.startingPrice),
    }
    const res = await createAuction(payload)
    toast.success(`Subasta "${res.data.title}" creada exitosamente`)
    onCreated?.(res.data)
    onClose()
    setForm({ title: '', description: '', startTime: '', endTime: '', startingPrice: '' })
  } catch (err) {
    const msg = parseError(err)
    const status = err.response?.status
    if (status === 400) {
      // Validaciones @Valid: título corto, descripción corta, precio negativo, etc.
      toast.error(msg)
    } else if (status === 406) {
      // El back lanza 406 para lógica de negocio (título duplicado, fechas inválidas)
      toast.error(msg)
    } else {
      toast.error(msg)
    }
  } finally {
    setLoading(false)
  }
  }

  return (
    <AnimatePresence>
      {open && (
        <>
          {/* Backdrop */}
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            onClick={onClose}
            className="fixed inset-0 z-50 bg-obsidian-950/80 backdrop-blur-sm"
          />

          {/* Modal */}
          <motion.div
            initial={{ opacity: 0, scale: 0.95, y: 20 }}
            animate={{ opacity: 1, scale: 1, y: 0 }}
            exit={{ opacity: 0, scale: 0.95, y: 20 }}
            transition={{ duration: 0.25, ease: [0.22, 1, 0.36, 1] }}
            className="fixed inset-0 z-50 flex items-center justify-center p-4 pointer-events-none"
          >
            <div className="w-full max-w-lg card border border-gold-600/30 shadow-2xl pointer-events-auto overflow-y-auto max-h-[90vh]">

              {/* Header */}
              <div className="flex items-center justify-between p-6 border-b border-obsidian-700/40">
                <div>
                  <p className="section-label">Nueva operación</p>
                  <h2 className="font-display font-bold text-xl gold-text">Crear Subasta</h2>
                </div>
                <button
                  onClick={onClose}
                  className="w-9 h-9 rounded-full border border-obsidian-600/40 flex items-center justify-center
                             hover:border-red-500/40 hover:text-red-400 transition-all cursor-pointer text-obsidian-400"
                >
                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                  </svg>
                </button>
              </div>

              {/* Form */}
              <form onSubmit={handleSubmit} className="p-6 space-y-4">
                <div>
                  <label className="section-label block mb-1.5" htmlFor="title">Título del artículo</label>
                  <input
                    id="title"
                    name="title"
                    type="text"
                    required
                    value={form.title}
                    onChange={handleChange}
                    placeholder="ej. iPhone 15 Pro Max nuevo sellado"
                    className="input-field"
                  />
                </div>

                <div>
                  <label className="section-label block mb-1.5" htmlFor="description">Descripción</label>
                  <textarea
                    id="description"
                    name="description"
                    required
                    rows={3}
                    value={form.description}
                    onChange={handleChange}
                    placeholder="Describe el artículo en detalle..."
                    className="input-field resize-none"
                  />
                </div>

                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="section-label block mb-1.5" htmlFor="startTime">Inicio</label>
                    <input
                      id="startTime"
                      name="startTime"
                      type="datetime-local"
                      required
                      value={form.startTime}
                      onChange={handleChange}
                      className="input-field"
                    />
                  </div>
                  <div>
                    <label className="section-label block mb-1.5" htmlFor="endTime">Fin</label>
                    <input
                      id="endTime"
                      name="endTime"
                      type="datetime-local"
                      required
                      value={form.endTime}
                      onChange={handleChange}
                      className="input-field"
                    />
                  </div>
                </div>

                <div>
                  <label className="section-label block mb-1.5" htmlFor="startingPrice">Precio inicial (COP)</label>
                  <div className="relative">
                    <span className="absolute left-3 top-1/2 -translate-y-1/2 text-gold-400 font-mono font-bold text-sm">$</span>
                    <input
                        id="startingPrice"
                        name="startingPrice"
                        type="number"
                        required
                        min="100000"
                        step="0.01"
                        value={form.startingPrice}
                        onChange={handleChange}
                        placeholder="Mínimo $100,000"
                        className="input-field pl-7"
                    />
                  </div>
                </div>

                <div className="flex gap-3 pt-2">
                  <button
                    type="button"
                    onClick={onClose}
                    className="btn-outline-gold flex-1 text-sm"
                  >
                    Cancelar
                  </button>
                  <button
                    type="submit"
                    disabled={loading}
                    className="btn-gold flex-1 text-sm flex items-center justify-center gap-2"
                  >
                    {loading ? (
                      <>
                        <svg className="w-4 h-4 animate-spin" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v4m0 8v4M4 12h4m8 0h4" />
                        </svg>
                        Creando...
                      </>
                    ) : 'Crear Subasta'}
                  </button>
                </div>
              </form>
            </div>
          </motion.div>
        </>
      )}
    </AnimatePresence>
  )
}
