import React, { useState, useEffect } from 'react'
import { useParams, useSearchParams, useNavigate } from 'react-router-dom'
import { motion } from 'framer-motion'
import { useToast } from '../context/ToastContext'
import { useAuth } from '../context/AuthContext'
import { getAuctionById, activeAuction, closeAuction, getBidHistory, parseError } from '../services/api'
import { useCountdown } from '../hooks/useCountdown'
import Pagination from '../components/Pagination'

function CountdownDisplay({ endTime }) {
  const { days, hours, minutes, seconds, total } = useCountdown(endTime)
  const isUrgent = total > 0 && total < 5 * 60 * 1000

  const parts = [
    { label: 'Días', val: days },
    { label: 'Horas', val: hours },
    { label: 'Min', val: minutes },
    { label: 'Seg', val: seconds },
  ]

  if (total <= 0) return (
    <div className="text-center py-4">
      <p className="font-display font-bold text-2xl text-obsidian-400">Subasta finalizada</p>
    </div>
  )

  return (
    <div className="flex items-center justify-center gap-3">
      {parts.map(({ label, val }, i) => (
        <React.Fragment key={label}>
          <div className={`text-center ${isUrgent ? 'animate-pulse' : ''}`}>
            <div className={`card px-3 py-2 min-w-[52px] text-center ${isUrgent ? 'border-red-500/40' : 'border-gold-500/20'}`}>
              <p className={`font-mono font-bold text-2xl ${isUrgent ? 'text-red-400' : 'text-gold-400'}`}>
                {String(val).padStart(2, '0')}
              </p>
            </div>
            <p className="text-xs text-obsidian-500 mt-1 font-mono tracking-wider">{label}</p>
          </div>
          {i < 3 && <span className={`font-bold text-2xl mb-5 ${isUrgent ? 'text-red-400' : 'text-gold-600/60'}`}>:</span>}
        </React.Fragment>
      ))}
    </div>
  )
}

export default function AuctionDetailPage() {
  const { auctionId } = useParams()
  const [searchParams] = useSearchParams()
  const navigate = useNavigate()
  const toast = useToast()
  const { user } = useAuth()
  const ctx = searchParams.get('ctx') || 'active' // 'active' | 'mine'

  const [auction, setAuction] = useState(null)
  const [loading, setLoading] = useState(true)
  const [actionLoading, setActionLoading] = useState(null)

  // Bid history (for mine + CLOSED)
  const [bidHistory, setBidHistory] = useState([])
  const [bidMeta, setBidMeta] = useState({ page: 0, totalPages: 0 })
  const [bidLoading, setBidLoading] = useState(false)
  const [showBidHistory, setShowBidHistory] = useState(false)

  useEffect(() => {
    fetchAuction()
  }, [auctionId])

  const fetchAuction = async () => {
    setLoading(true)
    try {
      const res = await getAuctionById(auctionId)
      setAuction(res.data)
    } catch (err) {
      toast.error(parseError(err))
      // No redirigir automáticamente — dejar al usuario decidir
    } finally {
      setLoading(false)
    }
  }

  const handleActivate = async () => {
    setActionLoading('activate')
    try {
      await activeAuction({ auctionId: parseInt(auctionId) })
      toast.success('Subasta activada exitosamente')
      fetchAuction()
    } catch (err) {
      toast.error(parseError(err))
    } finally {
      setActionLoading(null)
    }
  }

  const handleClose = async () => {
    setActionLoading('close')
    try {
      await closeAuction({ auctionId: parseInt(auctionId) })
      toast.success('Subasta cerrada')
      fetchAuction()
    } catch (err) {
      toast.error(parseError(err))
    } finally {
      setActionLoading(null)
    }
  }

const fetchBidHistory = async (page = 0) => {
  setBidLoading(true)
  try {
    const token = localStorage.getItem('accessToken')
    if (!token) {
      toast.error('Tu sesión expiró. Por favor inicia sesión de nuevo.')
      setBidHistory([])
      setBidMeta({ page: 0, totalPages: 0 })
      return
    }
    const res = await getBidHistory(auctionId, page, 8)
    const data = res.data
    setBidHistory(Array.isArray(data?.data) ? data.data : [])
    setBidMeta({
      page: data?.page ?? 0,
      totalPages: data?.totalPages ?? 0,
    })
  } catch (err) {
    const status = err.response?.status
    if (status === 401) {
      // El back rechazó el token en este endpoint específico
      // Mostrar mensaje sin cerrar sesión
      toast.error('No tienes autorización para ver el historial de esta subasta.')
    } else if (status === 404) {
      // Subasta sin pujas — no es un error real
      setBidHistory([])
      setBidMeta({ page: 0, totalPages: 0 })
    } else {
      toast.error(parseError(err))
    }
    setBidHistory([])
    setBidMeta({ page: 0, totalPages: 0 })
  } finally {
    setBidLoading(false)
  }
  }
  

  const toggleBidHistory = () => {
    if (!showBidHistory) fetchBidHistory(0)
    setShowBidHistory(v => !v)
  }

  const statusConfig = {
    ACTIVE:  { label: 'Activa',  cls: 'status-active' },
    CREATED: { label: 'Creada',  cls: 'status-created' },
    CLOSED:  { label: 'Cerrada', cls: 'status-closed' },
  }

  if (loading) return (
    <div className="page-container pt-16 flex items-center justify-center min-h-screen">
      <div className="flex flex-col items-center gap-4">
        <div className="w-12 h-12 rounded-full border-2 border-gold-500/30 border-t-gold-500 animate-spin" />
        <p className="text-obsidian-400 font-mono text-sm">Cargando subasta...</p>
      </div>
    </div>
  )

  if (!auction) return null

  const cfg = statusConfig[auction.status] || statusConfig.CREATED
  const isMine = ctx === 'mine'

  return (
    <div className="page-container pt-16">
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">

        {/* Back */}
        <motion.button
          initial={{ opacity: 0, x: -10 }}
          animate={{ opacity: 1, x: 0 }}
          onClick={() => navigate(-1)}
          className="flex items-center gap-2 text-obsidian-400 hover:text-white transition-colors mb-6 cursor-pointer group"
        >
          <svg className="w-4 h-4 group-hover:-translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
          </svg>
          <span className="text-sm font-medium">Volver</span>
        </motion.button>

        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.4 }}
          className="space-y-6"
        >
          {/* Main card */}
          <div className="card border border-gold-600/20 p-6 sm:p-8">
            {/* Header */}
            <div className="flex items-start justify-between gap-4 mb-6">
              <div>
                <div className="flex items-center gap-3 mb-2">
                  <p className="section-label">ID #{auction.auctionId}</p>
                  <span className={cfg.cls}>{cfg.label}</span>
                </div>
                <h1 className="font-display font-black text-2xl sm:text-3xl text-white leading-tight">
                  {auction.title}
                </h1>
              </div>
            </div>

            <p className="text-obsidian-300 text-sm leading-relaxed mb-8">{auction.description}</p>

            {/* Price info */}
            <div className="grid grid-cols-2 sm:grid-cols-3 gap-4 mb-8">
              <div className="card p-4">
                <p className="section-label mb-1">Precio Inicial</p>
                <p className="font-display font-bold text-lg text-obsidian-200">
                  ${Number(auction.startingPrice).toLocaleString('es-CO')}
                </p>
              </div>
              <div className="card-gold p-4">
                <p className="section-label mb-1">Precio Actual</p>
                <p className="font-display font-bold text-xl gold-text">
                  ${Number(auction.currentPrice ?? auction.startingPrice).toLocaleString('es-CO')}
                </p>
              </div>
              {auction.winnerId && (
                <div className="card p-4">
                  <p className="section-label mb-1">Ganador actual</p>
                  <p className="font-mono font-bold text-emerald-400">ID #{auction.winnerId}</p>
                </div>
              )}
            </div>

            {/* Dates */}
            <div className="grid grid-cols-2 gap-4 mb-8">
              <div>
                <p className="section-label mb-1">Inicio</p>
                <p className="text-sm text-obsidian-200 font-mono">
                  {new Date(auction.startTime).toLocaleString('es-CO')}
                </p>
              </div>
              <div>
                <p className="section-label mb-1">Fin</p>
                <p className="text-sm text-obsidian-200 font-mono">
                  {new Date(auction.endTime).toLocaleString('es-CO')}
                </p>
              </div>
            </div>

            {/* Countdown for active */}
            {auction.status === 'ACTIVE' && (
              <div className="mb-8">
                <p className="section-label text-center mb-4">Tiempo restante</p>
                <CountdownDisplay endTime={auction.endTime} />
              </div>
            )}

            {/* Action buttons */}
            <div className="flex flex-wrap gap-3 pt-4 border-t border-obsidian-700/40">
              {/* "Enter live" button - available when ACTIVE, from any context */}
              {auction.status === 'ACTIVE' && (
                <motion.button
                  whileHover={{ scale: 1.02 }}
                  whileTap={{ scale: 0.98 }}
                  onClick={() => navigate(`/auction/${auction.auctionId}/live`)}
                  className="btn-gold flex items-center gap-2"
                >
                  <span className="w-2 h-2 bg-obsidian-950 rounded-full animate-pulse" />
                  Entrar en Vivo
                </motion.button>
              )}

              {/* Owner-only actions */}
              {isMine && (
                <>
                  {(auction.status === 'CREATED' || auction.status === 'ACTIVE') && (
                    <button
                      onClick={handleClose}
                      disabled={actionLoading === 'close'}
                      className="btn-outline-gold flex items-center gap-2 border-red-500/40 text-red-400 hover:bg-red-500/10"
                    >
                      {actionLoading === 'close' ? 'Cerrando...' : (
                        <>
                          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M18.364 18.364A9 9 0 005.636 5.636m12.728 12.728A9 9 0 015.636 5.636m12.728 12.728L5.636 5.636" />
                          </svg>
                          Cerrar Subasta
                        </>
                      )}
                    </button>
                  )}

                  {auction.status === 'CREATED' && (
                    <button
                      onClick={handleActivate}
                      disabled={actionLoading === 'activate'}
                      className="btn-outline-gold flex items-center gap-2"
                    >
                      {actionLoading === 'activate' ? 'Activando...' : (
                        <>
                          <svg className="w-4 h-4 text-emerald-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z" />
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                          </svg>
                          Activar Subasta
                        </>
                      )}
                    </button>
                  )}

                  {/* Bid history - only for CLOSED */}
                  {auction.status === 'CLOSED' && (
                    <button
                      onClick={toggleBidHistory}
                      className="btn-ghost flex items-center gap-2"
                    >
                      <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                      </svg>
                      {showBidHistory ? 'Ocultar historial' : 'Ver historial de pujas'}
                    </button>
                  )}
                </>
              )}
            </div>
          </div>

          {/* Bid history panel */}
          {showBidHistory && isMine && auction.status === 'CLOSED' && (
            <motion.div
              initial={{ opacity: 0, y: 10 }}
              animate={{ opacity: 1, y: 0 }}
              className="card border border-obsidian-600/40 p-6"
            >
              <h3 className="font-display font-bold text-lg gold-text mb-4">Historial de Pujas</h3>
              {bidLoading ? (
                <div className="space-y-3">
                  {Array.from({ length: 4 }).map((_, i) => (
                    <div key={i} className="h-12 bg-obsidian-700/40 rounded animate-pulse" />
                  ))}
                </div>
              ) : bidHistory.length === 0 ? (
                <p className="text-obsidian-500 text-sm text-center py-6">Sin pujas registradas</p>
              ) : (
                <>
                  <div className="space-y-2">
                    {bidHistory.map((bid, i) => (
                      <motion.div
                        key={bid.bidId}
                        initial={{ opacity: 0, x: -10 }}
                        animate={{ opacity: 1, x: 0 }}
                        transition={{ delay: i * 0.04 }}
                        className="flex items-center justify-between p-3 rounded-lg bg-obsidian-800/40 border border-obsidian-700/30"
                      >
                        <div className="flex items-center gap-3">
                          <span className="text-xs font-mono text-obsidian-500">#{bid.bidId}</span>
                          <span className="text-xs text-obsidian-400">Usuario #{bid.userId}</span>
                        </div>
                        <div className="text-right">
                          <p className="font-display font-bold text-gold-400 text-sm">
                            ${Number(bid.amount).toLocaleString('es-CO')}
                          </p>
                          <p className="text-xs text-obsidian-500 font-mono">
                            {new Date(bid.timeStamp).toLocaleTimeString('es-CO')}
                          </p>
                        </div>
                      </motion.div>
                    ))}
                  </div>
                  <Pagination
                    page={bidMeta.page}
                    totalPages={bidMeta.totalPages}
                    onPageChange={(p) => fetchBidHistory(p)}
                  />
                </>
              )}
            </motion.div>
          )}
        </motion.div>
      </div>
    </div>
  )
}
