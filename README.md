# 🚀 Real-Time Auction System (Backend + Frontend)

## 📌 1. Descripción del proyecto

Sistema de subastas en tiempo real desarrollado con enfoque en arquitectura limpia y buenas prácticas de backend.

El proyecto implementa un motor de subastas que permite a múltiples usuarios participar simultáneamente, garantizando consistencia en las pujas, manejo de concurrencia y actualización en tiempo real mediante WebSockets.

Se priorizó el diseño orientado a dominio, la separación de responsabilidades y la implementación de mecanismos reales de backend como eventos de dominio, seguridad con JWT y procesos automatizados.

---
## 🎥 Demo del sistema en tiempo real

Este video muestra:

- Creación de subasta
- Múltiples usuarios conectados
- Pujas en tiempo real mediante WebSockets
- Actualización instantánea entre clientes
- Cierre automático de subasta

[![Demo del sistema](https://img.youtube.com/vi/PvyHKctHuZo/maxresdefault.jpg)](https://www.youtube.com/watch?v=PvyHKctHuZo)

👉 Haz click en la imagen para ver el video completo
---

## 🧱 2. Arquitectura

El backend está diseñado bajo **Arquitectura Hexagonal (Ports & Adapters)**, separando claramente las responsabilidades:

```
API (Controllers)
    ↓
Application (UseCases)
    ↓
Domain (Reglas de negocio)
    ↓
Infrastructure (Persistencia, seguridad, eventos, WebSockets)
```

### 🔹 Características clave de la arquitectura:

* El **dominio es independiente de frameworks**
* Los **UseCases orquestan la lógica**
* La comunicación con infraestructura se hace mediante **Ports**
* Implementación de **Domain Events** para desacoplar lógica
* Uso de `@TransactionalEventListener(AFTER_COMMIT)` para garantizar consistencia
* Separación entre comandos, resultados y DTOs

---

## ⚙️ 3. Tecnologías utilizadas

### 🧠 Backend

* Java 17
* Spring Boot
* Spring Security
* JWT
* WebSockets (STOMP + nativo)
* JPA / Hibernate
* PostgreSQL
* Schedulers (@Scheduled)
* Domain Events
* Arquitectura Hexagonal
* Optimistic Locking
* API de correos (Resend)
* Paginación personalizada
* BCrypt

---

### 🎨 Frontend

#### Core

* React 18
* Vite
* React Router v6

#### Estilos y diseño

* Tailwind CSS v3
* Framer Motion
* PostCSS + Autoprefixer

#### Comunicación

* Axios
* @stomp/stompjs
* WebSocket nativo

#### Tipografía

* Google Fonts

#### Estado

* React Context API
* React Hooks

#### Persistencia

* localStorage

---

## 📋 4. Requisitos previos

### Backend

* Java 17+
* PostgreSQL
* Cuenta en Resend

### Frontend

* Node.js 18+
* npm

---

## ⚙️ 5. Instalación y ejecución

### 🧠 Backend

#### Variables de entorno
Debes crear estas variables de entorno en tu sistema:
* PW_LOCALDB (Password bd)
* RESEND_API_KEY (Tu api Key de resend)
* JWT_SECRET (Tu firma secreta para JWT, minimo 32 bytes)
* JWT_EXPIRATION (tu fecha de expiracion en milisegundos)

#### Configuración (`application.properties`)
En base a lo que pueda cambiar en tu sistema (direccion url bd postgresql, username) deber ajustar el application.properties del backend.
Si tienes un correo verificado de resend (para envio de correos a cualquier correo) deber ajustar resend.from.email.
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/auctions_db
spring.datasource.username=postgres
spring.datasource.password=${PW_LOCALDB}

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.default_schema=auctions_db

resend.api.key=${RESEND_API_KEY}
resend.api.url=https://api.resend.com/emails
resend.from.email=onboarding@resend.dev

jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION:3600000}
```

#### Notas

* Crear API Key en Resend
* Verificar dominio o correo para envíos reales
* Ajustar configuración según entorno

---

### 🎨 Frontend

#### Instalación
Ubicado en la carpeta del front

```
npm install 
```

#### Configuración

Editar:

`src/services/api.js`

```javascript
const BASE_URL = 'http://localhost:8080' 
```

`src/hooks/useAuctionWebSocket.js`

```javascript
const WS_URL = 'http://localhost:8080/Auction/ws-native'
```
👉 Ajustar si el backend cambia de puerto o dominio
#### Requisitos

* Backend corriendo
* PostgreSQL activo

#### Ejecutar

```
npm run dev
```

#### 🔴 Importante (tiempo real)

Para pruebas de subastas en vivo entre múltiples usuarios:
Backend y frontend deben exponerse mediante túneles (ej: ngrok) 
Se deben actualizar con la url del tunel del back:
* BASE_URL
* WS_URL
En el frontend
---

## 🧩 6. Estructura del proyecto (Backend)

```
└── 📁src
            └── 📁main
                └── 📁java
                    └── 📁com
                        └── 📁dhernandez
                            └── 📁auction_service
                                └── 📁api
                                    └── 📁controller
                                        ├── AuctionController.java
                                        ├── BidController.java
                                        ├── UserController.java
                                    └── 📁controllerAdvice
                                        ├── AuctionControllerAdvice.java
                                        ├── BidControllerAdvice.java
                                        ├── LoginControllerAdvice.java
                                        ├── TokenVerificationControllerAdvice.java
                                        ├── UserControllerAdvice.java
                                        ├── ValidateControllerAdvice.java
                                    └── 📁dto
                                        ├── ActiveAuctionRequest.java
                                        ├── CloseAuctionRequest.java
                                        ├── CreateAuctionRequest.java
                                        ├── CreateBidRequest.java
                                        ├── CreateUserRequest.java
                                        ├── LoginRequest.java
                                        ├── SendEmailVerificationRequest.java
                                        ├── VerifyEmailRequest.java
                                    └── 📁exception
                                        ├── InvalidPageException.java
                                        ├── InvalidPaginationException.java
                                └── 📁application
                                    └── 📁command
                                        ├── ActiveAuctionCommand.java
                                        ├── CloseAuctionCommand.java
                                        ├── CreateAuctionCommand.java
                                        ├── CreateBidCommand.java
                                        ├── CreateUserCommand.java
                                        ├── LoginCommand.java
                                        ├── SendEmailVerificationCommand.java
                                        ├── VerifyEmailCommand.java
                                    └── 📁pagination
                                        ├── PageRequest.java
                                        ├── PageResult.java
                                    └── 📁port
                                    └── 📁result
                                        ├── ActiveAuctionResult.java
                                        ├── AuctionInfoResult.java
                                        ├── AuctionStatusActive.java
                                        ├── BidData.java
                                        ├── CloseAuctionResult.java
                                        ├── CreateAuctionResult.java
                                        ├── CreateBidResult.java
                                        ├── CreateUserResult.java
                                        ├── LoginResult.java
                                        ├── MyAuctions.java
                                        ├── UserSummary.java
                                        ├── VerifyEmailResult.java
                                    └── 📁Service
                                        ├── EmailService.java
                                    └── 📁useCase
                                        └── 📁Auction
                                            ├── ActiveAuctionAutomaticUseCase.java
                                            ├── ActiveAuctionAutomaticUseCaseImp.java
                                            ├── ActiveAuctionManualUseCaseImp.java
                                            ├── ActiveAuctionMunualUseCase.java
                                            ├── CloseExpiredAuctionManualUseCase.java
                                            ├── CloseExpiredAuctionManualUseCaseImp.java
                                            ├── CloseExpiredAuctionsUseCase.java
                                            ├── CloseExpiredAuctionsUseCaseImp.java
                                            ├── CreateAuctionUseCase.java
                                            ├── CreateAuctionUseCaseImp.java
                                            ├── GetAuctionInfoUseCase.java
                                            ├── GetAuctionInfoUseCaseImp.java
                                            ├── ListActiveAuctionsUseCase.java
                                            ├── ListActiveAuctionUseCaseImp.java
                                            ├── ListMyAuctionsUseCase.java
                                            ├── ListMyAuctionsUseCaseImp.java
                                        └── 📁Bid
                                            ├── FindBidsHistoryUseCase.java
                                            ├── FindBidsHistoryUseCaseImp.java
                                            ├── PlaceBidUseCase.java
                                            ├── PlaceBidUseCaseImp.java
                                        └── 📁Exception
                                            ├── FailToPlaceBid.java
                                            ├── IncorrectPasswordLogin.java
                                            ├── IncorrectUserIdAuction.java
                                            ├── NoAuctionFound.java
                                            ├── UserAlreadyVerified.java
                                            ├── UserLoginNotVerified.java
                                        └── 📁User
                                            ├── CreateUserUseCase.java
                                            ├── CreateUserUseCaseImp.java
                                            ├── LoginUseCase.java
                                            ├── LoginUseCaseImp.java
                                            ├── SendEmailVerificationUseCase.java
                                            ├── SendEmailVerificationUseCaseImp.java
                                            ├── VerifyEmailUseCase.java
                                            ├── VerifyEmailUseCaseImp.java
                                └── 📁domain
                                    └── 📁event
                                        ├── AuctionClosed.java
                                        ├── BidPlaced.java
                                        ├── DomainEvent.java
                                    └── 📁exception
                                        ├── ErrorActivatingAuction.java
                                        ├── ErrorCreatingAuction.java
                                        ├── ErrorCreatingBid.java
                                        ├── ErrorCreatingToken.java
                                        ├── ErrorCreatingUser.java
                                        ├── ErrorFindingToken.java
                                        ├── ErrorFindingUser.java
                                        ├── ErrorPlacingBid.java
                                        ├── ErrorTokenAlreadyUsed.java
                                        ├── ErrorTokenExpired.java
                                    └── 📁model
                                        └── 📁Enum
                                            ├── EnumAuction.java
                                            ├── EnumRoleUser.java
                                        ├── Auction.java
                                        ├── Bid.java
                                        ├── DomainEvent.java
                                        ├── EmailMessage.java
                                        ├── EmailVerificationToken.java
                                        ├── User.java
                                └── 📁infrastructure
                                    └── 📁config
                                        └── 📁SecurityConfig
                                            ├── SecurityConfig.java
                                        ├── AuctionUseCaseConfig.java
                                        ├── BidUseCaseConfig.java
                                        ├── EmailServiceConfig.java
                                        ├── UserUseCaseConfig.java
                                        ├── VerifyEmailUseCaseConfig.java
                                    └── 📁event
                                        └── 📁listener
                                            ├── AuctionClosedEventListener.java
                                            ├── BidPlacedEventListener.java
                                        ├── EventPublisherAdapter.java
                                    └── 📁persistence
                                        └── 📁adapter
                                            └── 📁Auction
                                                ├── AuctionPersistanceAdapter.java
                                            └── 📁Bid
                                                ├── BidPersistanceAdapter.java
                                            └── 📁Email
                                                ├── EmailApiAdapter.java
                                            └── 📁User
                                                ├── FindTokenByUserIdAndTokenAdapter.java
                                                ├── FindUserByEmailAdapter.java
                                                ├── JwtGeneratorAdapter.java
                                                ├── PasswordAdapter.java
                                                ├── PasswordVerificationAdapter.java
                                                ├── TokenGeneratorAdapter.java
                                                ├── UserPersistanceAdapter.java
                                        └── 📁repository
                                            ├── AuctionJpaRepository.java
                                            ├── BidJpaRepository.java
                                            ├── TokenJpaRepository.java
                                            ├── UserJpaRepository.java
                                        ├── AuctionJpaEntity.java
                                        ├── BidJpaEntity.java
                                        ├── TokenJpaEntity.java
                                        ├── UserJpaEntity.java
                                    └── 📁scheduler
                                        ├── AuctionScheduler.java
                                    └── 📁security
                                        ├── JwtAuthenticationFilter.java
                                        ├── JwtChannelInterceptor.java
                                        ├── JwtHandshakeInterceptor.java
                                        ├── JwtUtil.java
                                    └── 📁webSocket
                                        └── 📁mapper
                                            ├── AuctionClosedEventMapper.java
                                            ├── BidPlacedEventMapper.java
                                        └── 📁payload
                                            ├── AuctionClosedEventPayload.java
                                            ├── BidPlacedEventPayload.java
                                            ├── PayLoadEvent.java
                                        └── 📁sender
                                            ├── WebSocketAuctionSender.java
                                        └── 📁WebSocketConfig
                                            ├── WebSocketConfig.java
                                ├── AuctionServiceApplication.java
                └── 📁resources
                    └── 📁static
                    └── 📁templates
                    ├── application.properties
```

---
## 🗄️ 7. Configuración de Base de Datos

### 📦 Guía Oficial de Configuración

Este proyecto requiere una configuración específica de base de datos para funcionar correctamente.

---

## ⚠️ Importante

* ❌ **NO modificar el script base**
* ✔ Ejecutar el script inicial **tal cual**
* ✔ Luego aplicar los ajustes obligatorios

---

## 🧱 Paso 1: Crear la base de datos

```sql
CREATE DATABASE auctions_db;
```

---

## 🧾 Paso 2: Ejecutar script base (sin modificar)

```sql
CREATE SCHEMA IF NOT EXISTS auctions_db;
SET search_path TO auctions_db, public;

CREATE TABLE IF NOT EXISTS auctions_db.Users (
	id bigint PRIMARY KEY NOT NULL,
	email varchar NOT NULL,
	userName varchar NOT NULL,
	password varchar NOT NULL,
	verified boolean NOT NULL,
	role varchar DEFAULT 'USER' NOT NULL 
	CONSTRAINT chk_users_role_valid CHECK (role IN ('USER', 'ADMIN'))
);

CREATE TABLE IF NOT EXISTS auctions_db.Auctions (
	id bigint PRIMARY KEY NOT NULL,
	title varchar NOT NULL,
	description text NOT NULL,
	startTime timestamp NOT NULL,
	endTime timestamp NOT NULL,
	status varchar NOT NULL 
	CONSTRAINT check_auction_status CHECK (status IN ('CREATED', 'ACTIVE', 'CLOSED')),
	startingPrice real NOT NULL,
	currentPrice real,
	ownerId bigint NOT NULL,
	winnerId bigint,
	version bigint NOT NULL,
	CONSTRAINT fk_Users_id_to_Auctions_ownerId 
	FOREIGN KEY (ownerId) REFERENCES auctions_db.Users (id)
);

CREATE TABLE IF NOT EXISTS auctions_db.Bids (
	id bigint PRIMARY KEY NOT NULL,
	auctionId bigint NOT NULL,
	userId bigint NOT NULL,
	amount integer NOT NULL,
	timeStamp timestamp NOT NULL,
	CONSTRAINT fk_Users_id_to_Bids_userId 
	FOREIGN KEY (userId) REFERENCES auctions_db.Users (id),
	CONSTRAINT fk_Auctions_id_to_Bids_auctionId 
	FOREIGN KEY (auctionId) REFERENCES auctions_db.Auctions (id)
);

CREATE TABLE IF NOT EXISTS auctions_db.Tokens (
	id bigint PRIMARY KEY NOT NULL,
	userId bigint NOT NULL,
	token integer NOT NULL,
	used boolean NOT NULL,
	expirationDate timestamp NOT NULL,
	createdAt timestamp NOT NULL,
	CONSTRAINT fk_Users_id_to_Tokens_userId 
	FOREIGN KEY (userId) REFERENCES auctions_db.Users (id)
);
```

---

## ⚠️ Paso 3: Ajustes obligatorios (CRÍTICO)

### 🔴 1. IDs autogenerados

```sql
ALTER TABLE auctions_db.Users 
ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY;

ALTER TABLE auctions_db.Auctions 
ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY;

ALTER TABLE auctions_db.Bids 
ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY;

ALTER TABLE auctions_db.Tokens 
ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY;
```

---

### 🔴 2. Tipos numéricos correctos

```sql
ALTER TABLE auctions_db.Auctions 
ALTER COLUMN startingPrice TYPE NUMERIC(38,2);

ALTER TABLE auctions_db.Auctions 
ALTER COLUMN currentPrice TYPE NUMERIC(38,2);

ALTER TABLE auctions_db.Bids 
ALTER COLUMN amount TYPE NUMERIC(38,2);
```

---

### 🔴 3. Eliminar restricción incorrecta

```sql
ALTER TABLE auctions_db.Auctions 
DROP CONSTRAINT IF EXISTS auctions_ownerid_key;
```

---

## ✅ Resultado esperado

Después de estos pasos:

* ✔ IDs autogenerados correctamente
* ✔ Tipos compatibles con `BigDecimal`
* ✔ Integración correcta con Hibernate
* ✔ Sin errores de persistencia

---

## 🚨 Problemas si no se aplica correctamente

Si omites estos ajustes, puedes tener errores como:

* IDs nulos en inserciones
* Errores 500 en backend
* Fallos de persistencia con JPA
* Comportamientos inconsistentes en subastas

---

## 🧠 Conclusión

El script base es solo el punto de partida.
Los ajustes posteriores son **necesarios para que el sistema funcione correctamente en producción**.
---
---

## 🎯 Cierre

Este proyecto aborda problemas reales de backend:

* Concurrencia
* Consistencia
* Eventos
* Tiempo real
* Arquitectura desacoplada

---


