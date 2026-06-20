import axios from 'axios'

const BASE_URL = 'https://localhost:8080'

const api = axios.create({
  baseURL: BASE_URL,
  headers: { 'Content-Type': 'application/json' }
})

// Attach JWT to every request
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// SOLO redirigir a login si el 401 viene de un endpoint de autenticación
// (token realmente vencido), no de errores de negocio
const AUTH_ENDPOINTS = ['/Auction/auction/', '/Auction/bid/']

api.interceptors.response.use(
  (res) => res,
  (error) => {
    const status = error.response?.status
    const url = error.config?.url || ''

    // Solo cerrar sesión si es 401 Y viene de endpoint protegido
    // Y el token realmente existe (no es un error de credenciales en login)
    const isLoginEndpoint = url.includes('/user/login')
    const token = localStorage.getItem('accessToken')

    if (status === 401 && !isLoginEndpoint && token) {
      // Verificar que sea realmente token vencido y no un error de negocio
      const data = error.response?.data
      const msg = typeof data === 'string' ? data.toLowerCase() : ''
      const isTokenError = msg.includes('token') || msg.includes('jwt') || msg.includes('expired') || msg.includes('unauthorized')

      // Si el mensaje habla de token, sí cerrar sesión
      // Si no, dejar que el componente maneje el error
      if (isTokenError) {
        localStorage.removeItem('accessToken')
        localStorage.removeItem('userData')
        window.location.href = '/login'
      }
    }

    return Promise.reject(error)
  }
)

// ── USER ──────────────────────────────────────────────────────────────
export const createUser = (data) =>
  api.post('/Auction/user/createUser', data)

export const verifyEmail = (data) =>
  api.put('/Auction/user/verifyEmail', data)

export const login = (data) =>
  api.post('/Auction/user/login', data)

export const resendVerificationEmail = (email) =>
  api.post('/Auction/user/emailVerification', { email })

// ── AUCTION ───────────────────────────────────────────────────────────
export const createAuction = (data) =>
  api.post('/Auction/auction/createAuction', data)

export const activeAuction = (data) =>
  api.put('/Auction/auction/activeAuction', data)

export const closeAuction = (data) =>
  api.put('/Auction/auction/closeAuction', data)

export const getActiveAuctions = (page = 0, size = 10) =>
  api.get('/Auction/auction/activeAuctions', { params: { page, size } })

export const getMyAuctions = (page = 0, size = 10) =>
  api.get('/Auction/auction/myAuctions', { params: { page, size } })

export const getAuctionById = (auctionId) =>
  api.get(`/Auction/auction/${auctionId}`)

// ── BID ───────────────────────────────────────────────────────────────
export const createBid = (data) =>
  api.post('/Auction/bid/createBid', data)

export const getBidHistory = (auctionId, page = 0, size = 10) =>
  api.get(`/Auction/bid/${auctionId}`, { params: { page, size } })

// ── ERROR PARSER ──────────────────────────────────────────────────────
export const parseError = (error) => {
  if (!error.response) {
    return 'No se pudo conectar con el servidor. Verifica que el backend esté corriendo.'
  }

  const { status, data } = error.response

  // Spring @Valid — devuelve 400 con varios formatos posibles
  if (status === 400) {
  // Formato de tu Spring Boot: { "password": "mensaje", "title": "mensaje" }
  // Es un objeto plano donde cada key es el campo con error
  if (data && typeof data === 'object' && !Array.isArray(data)) {
    const messages = Object.values(data).filter(v => typeof v === 'string')
    if (messages.length > 0) return messages.join(' • ')
  }
  if (Array.isArray(data?.errors) && data.errors.length > 0) {
    return data.errors.join(' • ')
  }
  if (Array.isArray(data?.fieldErrors) && data.fieldErrors.length > 0) {
    return data.fieldErrors.map(e => e.message).join(' • ')
  }
  if (typeof data?.message === 'string' && data.message.trim()) return data.message
  if (typeof data === 'string' && data.trim()) return data
  return 'Datos inválidos. Revisa los campos.'
  }

  // String plana del backend (RuntimeException personalizada)
  if (typeof data === 'string' && data.trim()) return data
  if (typeof data?.message === 'string' && data.message.trim()) return data.message

  return `Error ${status}: Ocurrió un problema inesperado.`
}

export default api