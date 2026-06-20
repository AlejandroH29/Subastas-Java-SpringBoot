/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        gold: {
          50:  '#fffbeb',
          100: '#fef3c7',
          200: '#fde68a',
          300: '#fcd34d',
          400: '#fbbf24',
          500: '#D4A017',
          600: '#B8860B',
          700: '#92650A',
          800: '#6B4C08',
          900: '#4A3406',
        },
        obsidian: {
          50:  '#f0f0f5',
          100: '#d8d8e8',
          200: '#c0c0d8',
          300: '#a0a0c4',
          400: '#8080b0',  
          500: '#6060a0',  
          600: '#3a3a70',  
          700: '#2a2a55',  
          800: '#1a1a3a',  
          900: '#0e0e28',  
          950: '#07071a',  
        },
        ember: {
          400: '#FF6B35',
          500: '#E85D25',
        }
      },
      fontFamily: {
        display: ['Cinzel', 'serif'],
        body: ['Inter', 'sans-serif'],
        mono: ['JetBrains Mono', 'monospace'],
      },
      animation: {
        'spin-slow': 'spin 8s linear infinite',
        'pulse-gold': 'pulseGold 2s ease-in-out infinite',
        'float': 'float 6s ease-in-out infinite',
        'shimmer': 'shimmer 2.5s linear infinite',
      },
      keyframes: {
        pulseGold: {
          '0%, 100%': { boxShadow: '0 0 20px rgba(212, 160, 23, 0.4)' },
          '50%': { boxShadow: '0 0 60px rgba(212, 160, 23, 0.8)' },
        },
        shimmer: {
          '0%': { backgroundPosition: '-200% 0' },
          '100%': { backgroundPosition: '200% 0' },
        },
      },
      backgroundImage: {
        'gold-gradient': 'linear-gradient(135deg, #D4A017 0%, #F5D05A 40%, #D4A017 60%, #92650A 100%)',
        'obsidian-gradient': 'linear-gradient(180deg, #060612 0%, #0c0c1e 50%, #060612 100%)',
        'card-gradient': 'linear-gradient(135deg, rgba(212,160,23,0.08) 0%, rgba(6,6,18,0.95) 100%)',
      }
    },
  },
  plugins: [],
}
