import React, { useState, useEffect, useCallback } from 'react'
import { motion } from 'framer-motion'
import { useToast } from '../context/ToastContext'
import { getMyAuctions, parseError } from '../services/api'
import AuctionCard from '../components/AuctionCard'
import Pagination from '../components/Pagination'
import CreateAuctionModal from '../components/CreateAuctionModal'

const FILTERS = ['TODAS', 'CREATED', 'ACTIVE', 'CLOSED']

export default function MyAuctionsPage() {
  const toast = useToast()
  const [auctions, setAuctions] = useState([])
  const [meta, setMeta] = useState({ page: 0, totalPages: 0, totalItems: 0 })
  const [loading, setLoading] = useState(true)
  const [createOpen, setCreateOpen] = useState(false)
  const [filter, setFilter] = useState('TODAS')
  const PAGE_SIZE = 8

  const fetchAuctions = useCallback(async (page = 0) => {
  setLoading(true)
  try {
    const res = await getMyAuctions(page, PAGE_SIZE)
    setAuctions(res.data?.data || [])
    setMeta({
      page: res.data?.page ?? 0,
      totalPages: res.data?.totalPages ?? 0,
      totalItems: res.data?.totalItems ?? 0,
    })
  } catch (err) {
    if (err?.code !== 'ERR_CANCELED') {
      console.error('Error cargando mis subastas:', err)
    }
  } finally {
    setLoading(false)
  }
  }, []) // <- dependencias vacías

  useEffect(() => { fetchAuctions(0) }, [fetchAuctions])

  const filtered = filter === 'TODAS'
    ? auctions
    : auctions.filter(a => a.status === filter)

  const statusLabels = { TODAS: 'Todas', CREATED: 'Creadas', ACTIVE: 'Activas', CLOSED: 'Cerradas' }

  return (
    <div className="page-container pt-16">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">

        {/* Header */}
        <motion.div
          initial={{ opacity: 0, y: -10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.4 }}
          className="flex flex-col sm:flex-row sm:items-end justify-between gap-4 mb-8"
        >
          <div>
            <p className="section-label">Mis operaciones</p>
            <h1 className="font-display font-black text-3xl sm:text-4xl text-white">
              Mis <span className="gold-text">Subastas</span>
            </h1>
            <p className="text-obsidian-400 text-sm mt-1">
              {meta.totalItems} subasta{meta.totalItems !== 1 ? 's' : ''} creadas por ti
            </p>
          </div>

          <div className="flex items-center gap-3">
            <button
              onClick={() => fetchAuctions(meta.page)}
              className="btn-ghost flex items-center gap-2 text-sm"
            >
              <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
              </svg>
              Actualizar
            </button>
            <button onClick={() => setCreateOpen(true)} className="btn-gold flex items-center gap-2 text-sm px-5 py-2.5">
              <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
              </svg>
              Nueva Subasta
            </button>
          </div>
        </motion.div>

        {/* Filter tabs */}
        <div className="flex items-center gap-2 mb-6 overflow-x-auto pb-1">
          {FILTERS.map(f => (
            <button
              key={f}
              onClick={() => setFilter(f)}
              className={`px-4 py-2 rounded-lg text-sm font-semibold whitespace-nowrap transition-all cursor-pointer
                ${filter === f
                  ? 'bg-gold-gradient text-obsidian-950'
                  : 'border border-obsidian-600/40 text-obsidian-400 hover:text-white hover:border-gold-500/40'
                }`}
            >
              {statusLabels[f]}
            </button>
          ))}
        </div>

        {/* Grid */}
        {loading ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
            {Array.from({ length: 8 }).map((_, i) => (
              <div key={i} className="card p-5 animate-pulse">
                <div className="h-4 bg-obsidian-700 rounded mb-3 w-1/3" />
                <div className="h-6 bg-obsidian-700 rounded mb-2 w-2/3" />
                <div className="h-4 bg-obsidian-700 rounded mb-4 w-full" />
                <div className="h-8 bg-obsidian-700 rounded w-1/2" />
              </div>
            ))}
          </div>
        ) : filtered.length === 0 ? (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="text-center py-20"
          >
            <p className="font-display font-bold text-xl text-obsidian-400 mb-2">Sin subastas</p>
            <p className="text-obsidian-600 text-sm mb-6">
              {filter === 'TODAS' ? 'Crea tu primera subasta' : `No tienes subastas en estado "${statusLabels[filter]}"`}
            </p>
            {filter === 'TODAS' && (
              <button onClick={() => setCreateOpen(true)} className="btn-gold">
                Crear subasta
              </button>
            )}
          </motion.div>
        ) : (
          <>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
              {filtered.map((auction, i) => (
                <AuctionCard
                  key={auction.idAuction ?? auction.auctionId}
                  auction={auction}
                  index={i}
                  context="mine"
                />
              ))}
            </div>
            {filter === 'TODAS' && (
              <Pagination
                page={meta.page}
                totalPages={meta.totalPages}
                onPageChange={(p) => fetchAuctions(p)}
              />
            )}
          </>
        )}
      </div>

      <CreateAuctionModal
        open={createOpen}
        onClose={() => setCreateOpen(false)}
        onCreated={() => fetchAuctions(0)}
      />
    </div>
  )
}
