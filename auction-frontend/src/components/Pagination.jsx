import React from 'react'
import { motion } from 'framer-motion'

export default function Pagination({ page, totalPages, onPageChange }) {
  if (totalPages <= 1) return null

  return (
    <div className="flex items-center justify-center gap-2 mt-6">
      <button
        onClick={() => onPageChange(page - 1)}
        disabled={page === 0}
        className="w-9 h-9 rounded-lg border border-obsidian-600/40 flex items-center justify-center
                   text-obsidian-300 hover:text-white hover:border-gold-500/40 disabled:opacity-30 
                   disabled:cursor-not-allowed transition-all cursor-pointer"
      >
        <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
        </svg>
      </button>

      <div className="flex items-center gap-1">
        {Array.from({ length: totalPages }, (_, i) => (
          <motion.button
            key={i}
            whileTap={{ scale: 0.9 }}
            onClick={() => onPageChange(i)}
            className={`w-9 h-9 rounded-lg text-sm font-semibold transition-all cursor-pointer
              ${i === page
                ? 'bg-gold-gradient text-obsidian-950'
                : 'border border-obsidian-600/40 text-obsidian-300 hover:border-gold-500/40 hover:text-white'
              }`}
          >
            {i + 1}
          </motion.button>
        ))}
      </div>

      <button
        onClick={() => onPageChange(page + 1)}
        disabled={page >= totalPages - 1}
        className="w-9 h-9 rounded-lg border border-obsidian-600/40 flex items-center justify-center
                   text-obsidian-300 hover:text-white hover:border-gold-500/40 disabled:opacity-30 
                   disabled:cursor-not-allowed transition-all cursor-pointer"
      >
        <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
        </svg>
      </button>

      <span className="text-xs text-obsidian-500 ml-2 font-mono">
        {page + 1} / {totalPages}
      </span>
    </div>
  )
}
