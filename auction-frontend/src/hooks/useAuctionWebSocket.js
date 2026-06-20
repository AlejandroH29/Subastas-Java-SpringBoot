import { useEffect, useRef, useCallback } from 'react'
import { Client } from '@stomp/stompjs'

//Para poder ajustar la url base del backend
const WS_URL = 'https://localhost:8080' + "/Auction/ws-native"

export function useAuctionWebSocket({ auctionId, onBidUpdate, onAuctionClose, enabled = true }) {
  const clientRef = useRef(null)
  const reconnectDelay = useRef(3000)

  const connect = useCallback(() => {
  const token = localStorage.getItem('accessToken')
  if (!token || !auctionId || !enabled) return

  // Dynamic import para evitar el crash de 'global' en Vite
  import('sockjs-client').then(({ default: SockJS }) => {
    const client = new Client({
      webSocketFactory: () => new WebSocket(WS_URL.replace('https://', 'wss://').replace('http://', 'ws://')),    
                                      connectHeaders: {
                                                Authorization: `Bearer ${token}`,
                                      },
      reconnectDelay: reconnectDelay.current,
      onConnect: () => {
        console.log('=== WS CONECTADO ===')
        reconnectDelay.current = 3000
        client.subscribe(`/topic/auction/${auctionId}`, (message) => {
          console.log('=== MENSAJE RECIBIDO ===', message.body)
          try {
            const data = JSON.parse(message.body)
            if (data.type === 'BID_PLACED') {
              onBidUpdate?.(data)
            } else if (data.type === 'AUCTION_CLOSED') {
              onAuctionClose?.(data)
              client.deactivate()
            }
          } catch (e) {
            console.error('WS parse error:', e)
          }
        })
      },
      onStompError: (frame) => {
        console.error('=== STOMP ERROR ===', frame)
      },
      onWebSocketError: (err) => {
        console.error('=== WEBSOCKET ERROR ===', err)
      },
      onDisconnect: () => {
        console.log('=== WS DESCONECTADO ===')
      },
    })

    client.activate()
    clientRef.current = client
  })
}, [auctionId, onBidUpdate, onAuctionClose, enabled]) 

  useEffect(() => {
    if (enabled) connect()
    return () => {
      clientRef.current?.deactivate()
    }
  }, [connect, enabled])

  const disconnect = useCallback(() => {
    clientRef.current?.deactivate()
  }, [])

  return { disconnect }
}
