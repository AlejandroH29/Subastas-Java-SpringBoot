import React, { useState, useEffect, useCallback } from 'react'
import { motion } from 'framer-motion'
import { useAuth } from '../context/AuthContext'
import { useToast } from '../context/ToastContext'
import { getActiveAuctions, parseError } from '../services/api'
import AuctionCard from '../components/AuctionCard'
import Pagination from '../components/Pagination'
import CreateAuctionModal from '../components/CreateAuctionModal'

export default function DashboardPage() {
  const { user } = useAuth()
  const toast = useToast()
  const [auctions, setAuctions] = useState([])
  const [meta, setMeta] = useState({ page: 0, totalPages: 0, totalItems: 0 })
  const [loading, setLoading] = useState(true)
  const [createOpen, setCreateOpen] = useState(false)
  const PAGE_SIZE = 9

  const fetchAuctions = useCallback(async (page = 0) => {
  setLoading(true)
  try {
    const res = await getActiveAuctions(page, PAGE_SIZE)
    setAuctions(res.data?.data || [])
    setMeta({
      page: res.data?.page ?? 0,
      totalPages: res.data?.totalPages ?? 0,
      totalItems: res.data?.totalItems ?? 0,
    })
  } catch (err) {
    if (err?.code !== 'ERR_CANCELED') {
      console.error('Error cargando subastas:', err)
    }
  } finally {
    setLoading(false)
  }
  }, []) // <- dependencias vacías, toast no va aquí

  useEffect(() => { fetchAuctions(0) }, [fetchAuctions])

  return (
    <div className="page-container pt-16">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">

        {/* Page header */}
        <motion.div
          initial={{ opacity: 0, y: -10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.4 }}
          className="flex flex-col sm:flex-row sm:items-end justify-between gap-4 mb-8"
        >
          <div>
            <p className="section-label">Bienvenido, {user?.userName}</p>
            <h1 className="font-display font-black text-3xl sm:text-4xl text-white">
              Subastas{' '}
              <span className="gold-text">Activas</span>
            </h1>
            <p className="text-obsidian-400 text-sm mt-1">
              {meta.totalItems} subasta{meta.totalItems !== 1 ? 's' : ''} en vivo ahora mismo
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
            <motion.button
              whileHover={{ scale: 1.02 }}
              whileTap={{ scale: 0.98 }}
              onClick={() => setCreateOpen(true)}
              className="btn-gold flex items-center gap-2 text-sm px-5 py-2.5"
            >
              <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
              </svg>
              Nueva Subasta
            </motion.button>
          </div>
        </motion.div>

        {/* Stats bar */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.15 }}
          className="grid grid-cols-3 gap-4 mb-8"
        >
          {[
            { label: 'En vivo', value: meta.totalItems, icon: '●', color: 'text-emerald-400' },
            { label: 'Esta página', value: auctions.length, icon: '◈', color: 'text-gold-400' },
            { label: 'Páginas', value: meta.totalPages || 1, icon: '▤', color: 'text-obsidian-300' },
          ].map((s, i) => (
            <div key={i} className="card px-4 py-3 flex items-center gap-3">
              <span className={`text-lg ${s.color} animate-pulse`}>{s.icon}</span>
              <div>
                <p className="font-display font-bold text-lg text-white">{s.value}</p>
                <p className="text-xs text-obsidian-500 font-mono tracking-wider">{s.label}</p>
              </div>
            </div>
          ))}
        </motion.div>

        {/* Grid */}
        {loading ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
            {Array.from({ length: 6 }).map((_, i) => (
              <div key={i} className="card p-5 animate-pulse">
                <div className="h-4 bg-obsidian-700 rounded mb-3 w-1/3" />
                <div className="h-6 bg-obsidian-700 rounded mb-2 w-2/3" />
                <div className="h-4 bg-obsidian-700 rounded mb-4 w-full" />
                <div className="h-8 bg-obsidian-700 rounded w-1/2" />
              </div>
            ))}
          </div>
        ) : auctions.length === 0 ? (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="text-center py-20"
          >
            <div className="w-20 h-20 rounded-full border border-obsidian-700/40 flex items-center justify-center mx-auto mb-4">
              <svg className="w-10 h-10 text-obsidian-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1} d="M3 3h2l.4 2M7 13h10l4-8H5.4m0 0L7 13m0 0l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17M7 13h10M9 17a2 2 0 100 4 2 2 0 000-4zm8 0a2 2 0 100 4 2 2 0 000-4z" />
              </svg>
            </div>
            <p className="font-display font-bold text-xl text-obsidian-400 mb-2">Sin subastas activas</p>
            <p className="text-obsidian-600 text-sm mb-6">Sé el primero en crear una subasta</p>
            <button onClick={() => setCreateOpen(true)} className="btn-gold">
              Crear primera subasta
            </button>
          </motion.div>
        ) : (
          <>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
              {auctions.map((auction, i) => (
                <AuctionCard
                  key={auction.auctionId}
                  auction={auction}
                  index={i}
                  context="active"
                />
              ))}
            </div>
            <Pagination
              page={meta.page}
              totalPages={meta.totalPages}
              onPageChange={(p) => fetchAuctions(p)}
            />
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
