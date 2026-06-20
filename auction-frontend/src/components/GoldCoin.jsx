import React from 'react'
import { motion } from 'framer-motion'

export default function GoldCoin({ size = 200, className = '' }) {
  const s = size

  return (
    <div className={`relative ${className}`} style={{ width: s, height: s }}>
      {/* Outer glow rings */}
      <motion.div
        className="absolute inset-0 rounded-full"
        style={{
          background: 'radial-gradient(circle, rgba(212,160,23,0.15) 0%, transparent 70%)',
          width: s * 1.6,
          height: s * 1.6,
          top: -(s * 0.3),
          left: -(s * 0.3),
        }}
        animate={{ scale: [1, 1.15, 1], opacity: [0.5, 1, 0.5] }}
        transition={{ duration: 3, repeat: Infinity, ease: 'easeInOut' }}
      />

      {/* Coin scene */}
      <div className="coin-wrapper" style={{ width: s, height: s }}>
        <div className="coin" style={{ width: s, height: s, animation: 'coinSpin 4s linear infinite' }}>
          {/* Front face */}
          <div
            className="coin-face coin-front"
            style={{ width: s, height: s }}
          >
            <svg
              viewBox="0 0 200 200"
              width={s}
              height={s}
              style={{ position: 'absolute', top: 0, left: 0 }}
            >
              {/* Embossed ring */}
              <circle cx="100" cy="100" r="88" fill="none" stroke="rgba(255,255,255,0.25)" strokeWidth="3" />
              <circle cx="100" cy="100" r="78" fill="none" stroke="rgba(0,0,0,0.2)" strokeWidth="1.5" />

              {/* "AV" monogram */}
              <text
                x="100"
                y="115"
                textAnchor="middle"
                fontFamily="Cinzel, serif"
                fontWeight="900"
                fontSize="52"
                fill="rgba(255,255,255,0.85)"
                style={{ textShadow: '2px 2px 4px rgba(0,0,0,0.5)' }}
              >AV</text>

              {/* Stars around */}
              {[0, 60, 120, 180, 240, 300].map((deg, i) => {
                const rad = (deg * Math.PI) / 180
                const cx = 100 + 68 * Math.cos(rad)
                const cy = 100 + 68 * Math.sin(rad)
                return (
                  <polygon
                    key={i}
                    points="0,-5 1.5,-1.5 5,-1.5 2.5,1 3.5,5 0,2.5 -3.5,5 -2.5,1 -5,-1.5 -1.5,-1.5"
                    transform={`translate(${cx},${cy})`}
                    fill="rgba(255,255,255,0.5)"
                  />
                )
              })}

              {/* Bottom text */}
              <text
                x="100"
                y="148"
                textAnchor="middle"
                fontFamily="Cinzel, serif"
                fontWeight="600"
                fontSize="10"
                fill="rgba(255,255,255,0.6)"
                letterSpacing="3"
              >AUCTION VAULT</text>
            </svg>
          </div>

          {/* Back face */}
          <div
            className="coin-face coin-back"
            style={{ width: s, height: s }}
          >
            <svg
              viewBox="0 0 200 200"
              width={s}
              height={s}
              style={{ position: 'absolute', top: 0, left: 0 }}
            >
              <circle cx="100" cy="100" r="88" fill="none" stroke="rgba(255,255,255,0.2)" strokeWidth="3" />
              <circle cx="100" cy="100" r="65" fill="none" stroke="rgba(255,255,255,0.1)" strokeWidth="1" />
              {/* Hammer icon (auction symbol) */}
              <g transform="translate(100,100)" fill="rgba(255,255,255,0.75)">
                {/* Gavel head */}
                <rect x="-28" y="-18" width="40" height="18" rx="4" transform="rotate(-35)" />
                {/* Handle */}
                <rect x="8" y="-4" width="32" height="8" rx="4" transform="rotate(-35)" />
              </g>
              <text
                x="100"
                y="155"
                textAnchor="middle"
                fontFamily="Cinzel, serif"
                fontWeight="600"
                fontSize="9"
                fill="rgba(255,255,255,0.5)"
                letterSpacing="4"
              >EST. 2025</text>
            </svg>
          </div>

          {/* Edge shimmer */}
          <div className="coin-edge" style={{ width: s, height: s }} />
        </div>
      </div>

      {/* Ground shadow */}
      <motion.div
        className="absolute bottom-0 left-1/2 -translate-x-1/2 rounded-full"
        style={{
          width: s * 0.7,
          height: 12,
          background: 'radial-gradient(ellipse, rgba(212,160,23,0.4) 0%, transparent 70%)',
          bottom: -(s * 0.06),
        }}
        animate={{ scaleX: [0.8, 1.1, 0.8], opacity: [0.5, 0.9, 0.5] }}
        transition={{ duration: 4, repeat: Infinity, ease: 'linear' }}
      />
    </div>
  )
}
