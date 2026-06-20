import React from 'react'
import { motion } from 'framer-motion'
import { useNavigate } from 'react-router-dom'
import { useCountdown } from '../hooks/useCountdown'

function CountdownBadge({ endTime }) {
  const { days, hours, minutes, seconds, total } = useCountdown(endTime)
  const isUrgent = total > 0 && total < 5 * 60 * 1000 // < 5 min

  if (total <= 0) return (
    <span className="text-xs font-mono text-obsidian-400">Finalizada</span>
  )

  return (
    <span className={`text-xs font-mono font-semibold tabular-nums ${isUrgent ? 'text-red-400 animate-pulse' : 'text-gold-400'}`}>
      {days > 0 && `${days}d `}{String(hours).padStart(2,'0')}:{String(minutes).padStart(2,'0')}:{String(seconds).padStart(2,'0')}
    </span>
  )
}

const statusConfig = {
  ACTIVE:   { label: 'Activa',   cls: 'status-active' },
  CREATED:  { label: 'Creada',   cls: 'status-created' },
  CLOSED:   { label: 'Cerrada',  cls: 'status-closed' },
}

export default function AuctionCard({ auction, index = 0, context = 'active', onClick }) {
  const navigate = useNavigate()
  const cfg = statusConfig[auction.status] || statusConfig.CREATED
  const id = auction.auctionId ?? auction.idAuction

  const handleClick = () => {
    if (onClick) { onClick(auction); return }
    navigate(`/auction/${id}?ctx=${context}`)
  }

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ delay: index * 0.06, duration: 0.4, ease: [0.22, 1, 0.36, 1] }}
      whileHover={{ y: -3, transition: { duration: 0.2 } }}
      onClick={handleClick}
      className="card-gold p-5 cursor-pointer group"
    >
      {/* Header */}
      <div className="flex items-start justify-between gap-3 mb-4">
        <div className="flex-1 min-w-0">
          <p className="section-label">#{id}</p>
          <h3 className="font-display font-bold text-white text-base leading-tight group-hover:text-gold-300 transition-colors truncate">
            {auction.title}
          </h3>
        </div>
        <span className={cfg.cls}>{cfg.label}</span>
      </div>

      {/* Description */}
      <p className="text-obsidian-400 text-sm leading-relaxed mb-4 line-clamp-2">
        {auction.description}
      </p>

      {/* Price row */}
      <div className="flex items-end justify-between gap-4">
        <div>
          <p className="text-xs text-obsidian-500 mb-0.5">Precio actual</p>
          <p className="font-display font-bold text-xl gold-text">
            ${(auction.currentPrice ?? auction.startingPrice ?? 0).toLocaleString('es-CO')}
          </p>
          {auction.startingPrice !== auction.currentPrice && (
            <p className="text-xs text-obsidian-500 line-through">
              ${Number(auction.startingPrice).toLocaleString('es-CO')}
            </p>
          )}
        </div>

        {/* Countdown or winner */}
        <div className="text-right">
          {auction.status === 'ACTIVE' && (
            <>
              <p className="text-xs text-obsidian-500 mb-0.5">Tiempo restante</p>
              <CountdownBadge endTime={auction.endTime} />
            </>
          )}
          {auction.status === 'CLOSED' && auction.winnerId && (
            <>
              <p className="text-xs text-obsidian-500 mb-0.5">Ganador ID</p>
              <p className="text-xs font-mono text-emerald-400">#{auction.winnerId}</p>
            </>
          )}
          {auction.status === 'CREATED' && (
            <>
              <p className="text-xs text-obsidian-500 mb-0.5">Inicio</p>
              <p className="text-xs font-mono text-gold-400/80">
                {new Date(auction.startTime).toLocaleDateString('es-CO')}
              </p>
            </>
          )}
        </div>
      </div>

      {/* Bottom enter hint */}
      <div className="mt-4 pt-3 border-t border-obsidian-700/40 flex items-center justify-end gap-1.5 opacity-0 group-hover:opacity-100 transition-opacity">
        <span className="text-xs text-gold-400 font-medium">Ver detalle</span>
        <svg className="w-3.5 h-3.5 text-gold-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
        </svg>
      </div>
    </motion.div>
  )
}
