import { useState, useEffect, useCallback } from 'react'

export function useCountdown(endTime) {
  const calc = useCallback(() => {
    const diff = new Date(endTime) - new Date()
    if (diff <= 0) return { days: 0, hours: 0, minutes: 0, seconds: 0, total: 0 }
    return {
      days:    Math.floor(diff / (1000 * 60 * 60 * 24)),
      hours:   Math.floor((diff / (1000 * 60 * 60)) % 24),
      minutes: Math.floor((diff / (1000 * 60)) % 60),
      seconds: Math.floor((diff / 1000) % 60),
      total:   diff,
    }
  }, [endTime])

  const [time, setTime] = useState(calc)

  useEffect(() => {
    setTime(calc())
    const id = setInterval(() => setTime(calc()), 1000)
    return () => clearInterval(id)
  }, [calc])

  return time
}
