import React, { useState, useEffect, useRef, useCallback } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { motion, AnimatePresence } from 'framer-motion'
import { useToast } from '../context/ToastContext'
import { useAuth } from '../context/AuthContext'
import { getAuctionById, closeAuction, createBid, parseError } from '../services/api'
import { useAuctionWebSocket } from '../hooks/useAuctionWebSocket'
import { useCountdown } from '../hooks/useCountdown'

function LiveCountdown({ endTime, onExpire }) {
  const { days, hours, minutes, seconds, total } = useCountdown(endTime)
  const isUrgent = total > 0 && total < 60 * 1000 // < 1 min
  const isCritical = total > 0 && total < 10 * 1000 // < 10 sec
  const expiredRef = useRef(false)

  useEffect(() => {
    if (total <= 0 && !expiredRef.current) {
      expiredRef.current = true
      onExpire?.()
    }
  }, [total, onExpire])

  if (total <= 0) return (
    <div className="text-center">
      <p className="font-display font-bold text-3xl text-obsidian-400">Tiempo agotado</p>
    </div>
  )

  const radius = 45
  const circ = 2 * Math.PI * radius
  const maxTotal = 60 * 60 * 1000
  const progress = Math.min(total / maxTotal, 1)
  const dashoffset = circ * (1 - progress)

  return (
    <div className="flex flex-col items-center gap-4">
      {/* SVG countdown ring */}
      <div className="relative w-36 h-36">
        <svg className="w-full h-full -rotate-90" viewBox="0 0 100 100">
          <circle cx="50" cy="50" r={radius} fill="none" stroke="rgba(212,160,23,0.1)" strokeWidth="6" />
          <circle
            cx="50" cy="50" r={radius} fill="none"
            stroke={isCritical ? '#ef4444' : isUrgent ? '#f59e0b' : '#D4A017'}
            strokeWidth="6"
            strokeLinecap="round"
            strokeDasharray={circ}
            strokeDashoffset={dashoffset}
            style={{ transition: 'stroke-dashoffset 1s linear, stroke 0.3s' }}
          />
        </svg>
        <div className="absolute inset-0 flex flex-col items-center justify-center">
          <span className={`font-mono font-black text-2xl ${isCritical ? 'text-red-400 animate-pulse' : isUrgent ? 'text-amber-400' : 'text-gold-400'}`}>
            {String(minutes).padStart(2,'0')}:{String(seconds).padStart(2,'0')}
          </span>
          {days > 0 && <span className="text-xs text-obsidian-400 font-mono">{days}d {hours}h</span>}
        </div>
      </div>

      {isCritical && (
        <motion.p
          animate={{ opacity: [1, 0.3, 1] }}
          transition={{ duration: 0.6, repeat: Infinity }}
          className="text-red-400 font-bold text-sm tracking-widest"
        >
          ¡ÚLTIMOS SEGUNDOS!
        </motion.p>
      )}
    </div>
  )
}

export default function LiveAuctionPage() {
  const { auctionId } = useParams()
  const navigate = useNavigate()
  const toast = useToast()
  const { user } = useAuth()

  const [auction, setAuction] = useState(null)
  const [loading, setLoading] = useState(true)
  const [closed, setClosed] = useState(false)
  const [closeData, setCloseData] = useState(null)

  const [bidAmount, setBidAmount] = useState('')
  const [bidLoading, setBidLoading] = useState(false)
  const [lastBid, setLastBid] = useState(null)
  const [bidFlash, setBidFlash] = useState(false)
  const [actionLoading, setActionLoading] = useState(false)

  // Live updates from WS
  const [livePrice, setLivePrice] = useState(null)
  const [liveWinner, setLiveWinner] = useState(null)
  const [liveTimestamp, setLiveTimestamp] = useState(null)
  const [recentBids, setRecentBids] = useState([])

  const flashBid = useCallback(() => {
    setBidFlash(true)
    setTimeout(() => setBidFlash(false), 1000)
  }, [])

  const handleBidUpdate = useCallback((data) => {
    setLivePrice(data.currentPrice)
    setLiveWinner(data.winnerId)
    setLiveTimestamp(data.timeStamp)
    setRecentBids(prev => [data, ...prev].slice(0, 8))
    flashBid()
  }, [flashBid])

  const handleAuctionClose = useCallback((data) => {
    setClosed(true)
    setCloseData(data)
    setLivePrice(data.finalPrice)
    toast.info('La subasta ha finalizado')
  }, [toast])

  const handleExpire = useCallback(() => {
    if (!closed) {
      setClosed(true)
      toast.info('El tiempo de la subasta ha expirado')
    }
  }, [closed, toast])

  useEffect(() => {
    getAuctionById(auctionId)
      .then(res => {
        setAuction(res.data)
        setLivePrice(res.data.currentPrice)
        setLiveWinner(res.data.winnerId)
        if (res.data.status === 'CLOSED') setClosed(true)
      })
      .catch(err => { toast.error(parseError(err)); navigate(-1) })
      .finally(() => setLoading(false))
  }, [auctionId])

  const { disconnect } = useAuctionWebSocket({
    auctionId,
    onBidUpdate: handleBidUpdate,
    onAuctionClose: handleAuctionClose,
    enabled: !loading && auction?.status === 'ACTIVE',
  })

  useEffect(() => {
    if (closed) disconnect()
  }, [closed, disconnect])

  const isOwner = auction && user && auction.currentPrice !== undefined
  // We don't have ownerId in the response — owner actions are always available in mine context
  // For safety, show close button if user came from own auctions page
  // This page is entered via navigate, so we check referrer approach via simple state
  // We'll just always show close for any user (back blocks it anyway)

  const handleBid = async (e) => {
    e.preventDefault()
    if (!bidAmount || parseFloat(bidAmount) <= 0) return
    setBidLoading(true)
    try {
      const res = await createBid({ auctionId: parseInt(auctionId), amount: parseFloat(bidAmount) })
      console.log('=== RESPUESTA PLACE BID ===', JSON.stringify(res.data))
      setLastBid(res.data)
      toast.success(`Puja de $${Number(res.data.amount).toLocaleString('es-CO')} registrada`)
      setBidAmount('')
    } catch (err) {
      toast.error(parseError(err))
    } finally {
      setBidLoading(false)
    }
  }

  const handleClose = async () => {
    setActionLoading(true)
    try {
      await closeAuction({ auctionId: parseInt(auctionId) })
      toast.success('Subasta cerrada')
      setClosed(true)
    } catch (err) {
      toast.error(parseError(err))
    } finally {
      setActionLoading(false)
    }
  }

  if (loading) return (
    <div className="page-container pt-16 flex items-center justify-center min-h-screen">
      <div className="flex flex-col items-center gap-4">
        <div className="w-12 h-12 rounded-full border-2 border-gold-500/30 border-t-gold-500 animate-spin" />
        <p className="text-obsidian-400 font-mono text-sm">Conectando a la subasta...</p>
      </div>
    </div>
  )

  if (!auction) return null

  const currentPrice = livePrice ?? auction.currentPrice ?? auction.startingPrice

  return (
    <div className="page-container pt-16">
      {/* Ambient bg */}
      <div
        className="fixed inset-0 pointer-events-none z-0"
        style={{ background: 'radial-gradient(ellipse 60% 40% at 50% 0%, rgba(212,160,23,0.04) 0%, transparent 60%)' }}
      />

      <div className="relative z-10 max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">

        {/* Header */}
        <motion.div
          initial={{ opacity: 0, y: -10 }}
          animate={{ opacity: 1, y: 0 }}
          className="flex items-center justify-between gap-4 mb-6"
        >
          <button
            onClick={() => navigate(-1)}
            className="flex items-center gap-2 text-obsidian-400 hover:text-white transition-colors cursor-pointer group"
          >
            <svg className="w-4 h-4 group-hover:-translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
            </svg>
            <span className="text-sm">Salir</span>
          </button>

          <div className="flex items-center gap-2">
            {!closed ? (
              <div className="flex items-center gap-2 px-3 py-1.5 rounded-full bg-emerald-500/10 border border-emerald-500/30">
                <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse" />
                <span className="text-emerald-400 text-xs font-mono font-bold tracking-wider">EN VIVO</span>
              </div>
            ) : (
              <div className="px-3 py-1.5 rounded-full bg-obsidian-700/40 border border-obsidian-600/30">
                <span className="text-obsidian-400 text-xs font-mono">FINALIZADA</span>
              </div>
            )}

            {!closed && (
              <button
                onClick={handleClose}
                disabled={actionLoading}
                className="px-3 py-1.5 rounded-lg border border-red-500/30 text-red-400 text-xs font-semibold
                           hover:bg-red-500/10 transition-all cursor-pointer disabled:opacity-50"
              >
                {actionLoading ? 'Cerrando...' : 'Cerrar subasta'}
              </button>
            )}
          </div>
        </motion.div>

        {/* Auction title */}
        <motion.div
          initial={{ opacity: 0, y: 10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.1 }}
          className="text-center mb-8"
        >
          <p className="section-label">Subasta #{auctionId}</p>
          <h1 className="font-display font-black text-2xl sm:text-3xl text-white">{auction.title}</h1>
          <p className="text-obsidian-400 text-sm mt-1 max-w-lg mx-auto">{auction.description}</p>
        </motion.div>

        {/* Main grid */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">

          {/* Left col — price + countdown + bid form */}
          <div className="lg:col-span-2 space-y-6">

            {/* Price card */}
            <motion.div
              initial={{ opacity: 0, scale: 0.98 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ delay: 0.15 }}
              className={`card-gold p-6 text-center transition-all duration-300 ${bidFlash ? 'border-gold-400 glow-gold' : ''}`}
            >
              <p className="section-label mb-2">Precio actual</p>
              <motion.p
                key={currentPrice}
                initial={{ scale: 1.05, color: '#F5D05A' }}
                animate={{ scale: 1, color: '#D4A017' }}
                transition={{ duration: 0.4 }}
                className="font-display font-black text-4xl sm:text-5xl gold-text text-shadow-gold"
              >
                ${Number(currentPrice).toLocaleString('es-CO')}
              </motion.p>
              <p className="text-obsidian-500 text-xs mt-1 font-mono">
                Inicio: ${Number(auction.startingPrice).toLocaleString('es-CO')}
              </p>

              {liveWinner && (
                <div className="mt-3 flex items-center justify-center gap-2">
                  <span className="w-2 h-2 rounded-full bg-emerald-400" />
                  <span className="text-emerald-400 text-sm font-mono">Ganando: Usuario #{liveWinner}</span>
                </div>
              )}

              {liveTimestamp && (
                <p className="text-obsidian-500 text-xs mt-1 font-mono">
                  Última puja: {new Date(liveTimestamp).toLocaleTimeString('es-CO')}
                </p>
              )}
            </motion.div>

            {/* Countdown */}
            {!closed && (
              <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                transition={{ delay: 0.2 }}
                className="card p-6"
              >
                <p className="section-label text-center mb-4">Tiempo restante</p>
                <LiveCountdown endTime={auction.endTime} onExpire={handleExpire} />
              </motion.div>
            )}

            {/* Closed banner */}
            {closed && closeData && (
              <motion.div
                initial={{ opacity: 0, scale: 0.95 }}
                animate={{ opacity: 1, scale: 1 }}
                className="card border border-gold-500/40 p-6 text-center glow-gold"
              >
                <p className="text-3xl mb-3">🏆</p>
                <h3 className="font-display font-black text-2xl gold-text mb-2">Subasta Finalizada</h3>
                {closeData.winnerId ? (
                  <>
                    <p className="text-obsidian-300 text-sm mb-1">Ganador</p>
                    <p className="font-mono font-bold text-emerald-400 text-lg">Usuario #{closeData.winnerId}</p>
                    <p className="font-display font-bold text-2xl gold-text mt-2">
                      ${Number(closeData.finalPrice ?? livePrice).toLocaleString('es-CO')}
                    </p>
                  </>
                ) : (
                  <p className="text-obsidian-400">Sin pujas registradas</p>
                )}
              </motion.div>
            )}

            {/* Bid form */}
            {!closed && (
              <motion.div
                initial={{ opacity: 0, y: 10 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: 0.25 }}
                className="card border border-gold-600/20 p-6"
              >
                <h3 className="font-display font-bold text-lg text-white mb-4">
                  Hacer una puja
                </h3>

                <form onSubmit={handleBid} className="space-y-4">
                  <div className="relative">
                    <span className="absolute left-4 top-1/2 -translate-y-1/2 text-gold-400 font-mono font-bold">$</span>
                    <input
                      type="number"
                      min={parseFloat(currentPrice) + 10000}
                      step="1000"
                      value={bidAmount}
                      onChange={e => setBidAmount(e.target.value)}
                      placeholder={`Mínimo $${(parseFloat(currentPrice) + 10000).toLocaleString('es-CO')}`}
                      className="input-field pl-8 text-lg font-mono"
                    />
                  </div>

                  {/* Quick bid buttons */}
                  <div className="flex gap-2 flex-wrap">
                    {[10000, 20000, 50000, 100000].map(inc => (
                    <button
                      key={inc}
                      type="button"
                      onClick={() => setBidAmount(String(parseFloat(currentPrice) + inc))}
                      className="px-3 py-1.5 rounded-lg border border-gold-600/30 text-gold-400 text-xs font-mono
                                hover:bg-gold-500/10 transition-all cursor-pointer"
                      >
                        +${inc.toLocaleString('es-CO')}
                      </button>
                    ))}
                  </div>

                  <motion.button
                    type="submit"
                    disabled={bidLoading || !bidAmount}
                    whileHover={{ scale: 1.01 }}
                    whileTap={{ scale: 0.98 }}
                    className="btn-gold w-full py-4 flex items-center justify-center gap-2 text-base"
                  >
                    {bidLoading ? (
                      <>
                        <svg className="w-4 h-4 animate-spin" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v4m0 8v4M4 12h4m8 0h4" />
                        </svg>
                        Enviando puja...
                      </>
                    ) : (
                      <>
                        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
                        </svg>
                        Pujar ahora
                      </>
                    )}
                  </motion.button>
                </form>

                {/* Last bid confirmation */}
                <AnimatePresence>
                  {lastBid && (
                    <motion.div
                      initial={{ opacity: 0, y: 8 }}
                      animate={{ opacity: 1, y: 0 }}
                      exit={{ opacity: 0, y: -8 }}
                      className="mt-4 p-3 rounded-lg bg-emerald-500/10 border border-emerald-500/30"
                    >
                      <p className="text-emerald-400 text-sm font-semibold">✓ Tu última puja</p>
                      <div className="flex items-center justify-between mt-1">
                        <p className="text-emerald-300 font-mono font-bold">
                          ${Number(lastBid.amount).toLocaleString('es-CO')}
                        </p>
                        <p className="text-emerald-700 text-xs font-mono">
                          {new Date(lastBid.timeStamp).toLocaleTimeString('es-CO')}
                        </p>
                      </div>
                    </motion.div>
                  )}
                </AnimatePresence>
              </motion.div>
            )}
          </div>

          {/* Right col — live feed */}
          <div className="space-y-4">
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: 0.3 }}
              className="card p-5"
            >
              <div className="flex items-center gap-2 mb-4">
                <span className="w-2 h-2 rounded-full bg-gold-400 animate-pulse" />
                <h3 className="font-display font-bold text-sm text-white tracking-wide">Actividad en vivo</h3>
              </div>

              {recentBids.length === 0 ? (
                <div className="text-center py-8">
                  <p className="text-obsidian-500 text-sm">Esperando pujas...</p>
                  <p className="text-obsidian-600 text-xs mt-1">Sé el primero en pujar</p>
                </div>
              ) : (
                <div className="space-y-2">
                  <AnimatePresence initial={false}>
                    {recentBids.map((bid, i) => (
                      <motion.div
                        key={bid.timeStamp + i}
                        initial={{ opacity: 0, x: -20, height: 0 }}
                        animate={{ opacity: 1, x: 0, height: 'auto' }}
                        exit={{ opacity: 0 }}
                        transition={{ duration: 0.3 }}
                        className="flex items-center justify-between p-2.5 rounded-lg bg-obsidian-800/60 border border-obsidian-700/30"
                      >
                        <div>
                          <p className="text-xs text-obsidian-400 font-mono">Usuario #{bid.winnerId ?? '—'}</p>
                          <p className="text-xs text-obsidian-500">
                            {bid.timeStamp ? new Date(bid.timeStamp).toLocaleTimeString('es-CO') : '—'}
                          </p>
                        </div>
                        <p className="font-display font-bold text-sm gold-text">
                          ${Number(bid.currentPrice).toLocaleString('es-CO')}
                        </p>
                      </motion.div>
                    ))}
                  </AnimatePresence>
                </div>
              )}
            </motion.div>

            {/* Auction info summary */}
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: 0.4 }}
              className="card p-5 space-y-3"
            >
              <h3 className="font-display font-bold text-sm text-white mb-3">Detalles</h3>
              {[
                { label: 'ID Subasta', val: `#${auctionId}` },
                { label: 'Precio inicio', val: `$${Number(auction.startingPrice).toLocaleString('es-CO')}` },
                { label: 'Finaliza', val: new Date(auction.endTime).toLocaleString('es-CO') },
              ].map(({ label, val }) => (
                <div key={label} className="flex items-center justify-between">
                  <p className="text-xs text-obsidian-500 font-mono">{label}</p>
                  <p className="text-xs text-obsidian-200 font-mono font-semibold">{val}</p>
                </div>
              ))}
            </motion.div>
          </div>
        </div>
      </div>
    </div>
  )
}
