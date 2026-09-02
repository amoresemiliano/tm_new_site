# El Criollo — Taquería Tradicional Mexicana (MVP Web App)

Aplicación web moderna y mobile-first para la taquería **El Criollo**, ubicada en el puesto tradicional del **Mercado Maravillas** (Calle Bravo Murillo 122, Tetuán, Madrid).

---

## 🏗️ Arquitectura del Proyecto

El repositorio está estructurado para desacoplar el frontend moderno de cualquier servicio backend:

```
├── frontend/             # Aplicación Next.js (App Router, TypeScript, Tailwind CSS)
│   ├── app/              # Rutas Next.js App Router (layout, page, menu, pedir, reservar, club, local, etc.)
│   ├── components/       # Componentes React reutilizables (Header, BottomNav, DishCard, PromoBanners...)
│   ├── config/           # Configuraciones centralizadas e integraciones externas (Last.app, Google Maps...)
│   ├── context/          # Estado global (Carrito, Pedido, Club Taquero, Servicio en Mesa, Toasts)
│   ├── data/             # Fixtures y datos auténticos de la taquería
│   ├── types/            # Definiciones de TypeScript estrictas
│   ├── public/           # Assets estáticos y recursos
│   ├── package.json      # Dependencias y scripts del frontend
│   ├── tsconfig.json     # Configuración TypeScript strict
│   ├── tailwind.config.ts# Design tokens de El Criollo
│   └── next.config.mjs   # Configuración de Next.js
├── backend/              # Arquitectura backend PHP/MySQL independiente (para Bluehost)
└── README.md
```

---

## 🚀 Despliegue en Vercel

La carpeta `frontend/` es **100% compatible con Vercel** de manera nativa:

| Ajuste en Vercel | Valor Recomendado |
| :--- | :--- |
| **Root Directory** | `frontend` |
| **Framework Preset** | `Next.js` |
| **Build Command** | `npm run build` |
| **Install Command** | `npm install` |
| **Output Directory** | *Por defecto (`.next`)* |
| **Node.js Version** | `18.x` / `20.x` / `22.x` |

### Variables de Entorno en Vercel (`.env.production`):
```env
NEXT_PUBLIC_API_BASE_URL=https://api.elcriollo-madrid.es
NEXT_PUBLIC_APP_NAME="El Criollo Taquería"
NEXT_PUBLIC_GOOGLE_MAPS_URL="https://maps.google.com/?q=El+Criollo+Mercado+Maravillas+Bravo+Murillo+122+Madrid"
NEXT_PUBLIC_WHATSAPP_PHONE="+34612345678"
```

---

## 💻 Desarrollo Local

```bash
# 1. Entrar en la carpeta frontend
cd frontend

# 2. Instalar dependencias
npm install

# 3. Iniciar el servidor de desarrollo
npm run dev

# 4. Probar compilación de producción
npm run build
```

La app estará disponible en `http://localhost:3000`.

---

## 📱 Rutas y Experiencias de Usuario

- **`/` (Inicio)**: Hero banner, acceso rápido al servicio en mesa, banner interactivo de *1 Taco Gratis con Reseña Google*, selección de platillos estrella y reviews 4.9★ verificadas.
- **`/menu` (La Carta)**: Catálogo categorizado (Tacos, Especiales, Entrantes, Postres, Bebidas, Micheladas), buscador en tiempo real, filtros por picante/popularidad y modal con selección de salsas caseras.
- **`/pedir` (Pedidos & Carrito)**: Selector de modo (Recogida, Delivery con cálculo de portes gratis, En Mesa), propina al equipo taquero, resumen detallado y pasarela hacia tienda Last.app.
- **`/reservar` (Reservas)**: Selector de comensales (1-8+), fecha, franjas horarias, preferencia de zona (Barra de Trompo, Mesa Mercado, Puesto Alto) y confirmación digital inmediata.
- **`/club` (Club Taquero)**: Tarjeta de fidelización digital con QR personal, progresión de niveles (*Novato*, *Aficionado*, *Maestro*), catálogo de canje de premios y misiones taqueras.
- **`/local` (Ubicación & Contacto)**: Ficha completa del puesto en Mercado Maravillas (Tetuán), accesos en Metro Alvarado / Cuatro Caminos, horarios y enlaces a WhatsApp y teléfono.
- **`/en-el-local` (En el Local)**: Modo comensal inteligente para pedir directo a cocina por número de mesa, avisar al camarero, pedir la cuenta y copiar la clave Wi-Fi de clientes.
- **`/novedades` (Novedades & Eventos)**: Promociones activas (incluyendo la campaña de reseña Google), lanzamientos gastronómicos y servicio de catering para eventos.

---

## 🔗 Integraciones Externas Encapsuladas
- **Last.app Shop**: `https://elcriollo.last.shop`
- **Last.app Reservas**: `https://elcriollo.last.app/reservas`
- **Google Reviews & Maps**: Ficha oficial en Mercado Maravillas Tetuán
- **WhatsApp & Teléfono**: Botones de contacto directo integrados
