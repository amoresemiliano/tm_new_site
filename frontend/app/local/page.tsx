"use client";

import React from "react";
import Image from "next/image";
import Link from "next/link";
import { PRIMARY_LOCATION } from "@/data/mockData";
import { IntegrationsConfig } from "@/config/integrations";
import GoogleReviewsSection from "@/components/GoogleReviewsSection";
import FreeTacoPromoBanner from "@/components/FreeTacoPromoBanner";
import { 
  MapPin, 
  Clock, 
  Phone, 
  MessageSquare, 
  Navigation, 
  ExternalLink, 
  Calendar, 
  QrCode, 
  Sparkles,
  Train
} from "lucide-react";

export default function LocalPage() {
  return (
    <div className="space-y-6 pb-8">
      {/* Header */}
      <div>
        <h1 className="text-2xl sm:text-3xl font-black text-white">El Local & Ubicación</h1>
        <p className="text-xs sm:text-sm text-zinc-400 mt-1">
          El sabor auténtico de México dentro de uno de los mercados más castizos de Madrid.
        </p>
      </div>

      {/* Hero Venue Image & Highlights */}
      <div className="relative overflow-hidden rounded-3xl bg-zinc-900 border border-criollo-border h-64 sm:h-72 shadow-2xl">
        <Image
          src={PRIMARY_LOCATION.imageUrl}
          alt={PRIMARY_LOCATION.name}
          fill
          className="object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-t from-black via-black/40 to-transparent" />

        <div className="absolute bottom-4 left-4 right-4 text-white">
          <span className="bg-criollo-red text-white text-[10px] font-black uppercase px-2.5 py-1 rounded-full shadow-md">
            Puesto Tradicional · Mercado Maravillas
          </span>
          <h2 className="text-xl sm:text-2xl font-black mt-2">
            Calle de Bravo Murillo, 122 (Tetuán)
          </h2>
          <p className="text-xs text-zinc-300 mt-0.5">
            Madrid · A 2 minutos de Metro Alvarado (L1) y Cuatro Caminos (L1, L2, L6)
          </p>
        </div>
      </div>

      {/* Quick Action Shortcuts */}
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-2.5">
        <a
          href={PRIMARY_LOCATION.mapsUrl}
          target="_blank"
          rel="noopener noreferrer"
          className="bg-criollo-card border border-criollo-border hover:border-criollo-red p-3.5 rounded-2xl flex flex-col items-center text-center transition-colors group"
        >
          <Navigation className="w-5 h-5 text-criollo-red mb-1 group-hover:scale-110 transition-transform" />
          <span className="font-bold text-xs text-white">Abrir Maps</span>
          <span className="text-[10px] text-zinc-400">Cómo llegar</span>
        </a>

        <a
          href={IntegrationsConfig.WHATSAPP_URL}
          target="_blank"
          rel="noopener noreferrer"
          className="bg-criollo-card border border-criollo-border hover:border-emerald-500 p-3.5 rounded-2xl flex flex-col items-center text-center transition-colors group"
        >
          <MessageSquare className="w-5 h-5 text-emerald-400 mb-1 group-hover:scale-110 transition-transform" />
          <span className="font-bold text-xs text-white">WhatsApp</span>
          <span className="text-[10px] text-zinc-400">Chat directo</span>
        </a>

        <a
          href={IntegrationsConfig.PHONE_CLEAN}
          className="bg-criollo-card border border-criollo-border hover:border-criollo-yellow p-3.5 rounded-2xl flex flex-col items-center text-center transition-colors group"
        >
          <Phone className="w-5 h-5 text-criollo-yellow mb-1 group-hover:scale-110 transition-transform" />
          <span className="font-bold text-xs text-white">Llamar</span>
          <span className="text-[10px] text-zinc-400">{PRIMARY_LOCATION.phone}</span>
        </a>

        <Link
          href="/reservar"
          className="bg-criollo-card border border-criollo-border hover:border-purple-500 p-3.5 rounded-2xl flex flex-col items-center text-center transition-colors group"
        >
          <Calendar className="w-5 h-5 text-purple-400 mb-1 group-hover:scale-110 transition-transform" />
          <span className="font-bold text-xs text-white">Reservar</span>
          <span className="text-[10px] text-zinc-400">Tu mesa</span>
        </Link>
      </div>

      {/* "Estoy en el Local" QR Dine-In Experience CTA */}
      <div className="bg-gradient-to-r from-amber-950/40 via-zinc-900 to-zinc-900 border border-criollo-yellow/40 rounded-3xl p-5 flex items-center justify-between gap-4">
        <div className="flex items-center gap-3.5">
          <div className="w-12 h-12 rounded-2xl bg-criollo-yellow/20 border border-criollo-yellow/40 flex items-center justify-center text-criollo-yellow flex-shrink-0">
            <QrCode className="w-6 h-6" />
          </div>
          <div>
            <h3 className="font-extrabold text-sm text-white">
              ¿Comiendo ahora mismo en el mercado?
            </h3>
            <p className="text-xs text-zinc-300 mt-0.5">
              Accede a la pantalla de servicio en mesa para pedir directo a cocina o solicitar la cuenta.
            </p>
          </div>
        </div>

        <Link
          href="/en-el-local"
          className="bg-criollo-yellow hover:bg-amber-400 text-criollo-black font-black text-xs py-2.5 px-4 rounded-xl flex-shrink-0 shadow-lg transition-transform active:scale-95"
        >
          Modo En El Local
        </Link>
      </div>

      {/* Detailed Schedule & Venue Information */}
      <div className="bg-criollo-card border border-criollo-border rounded-3xl p-5 sm:p-6 space-y-4">
        <h3 className="font-black text-base text-white flex items-center gap-2">
          <Clock className="w-4 h-4 text-criollo-yellow" />
          <span>Horarios de Atención</span>
        </h3>

        <div className="space-y-2.5 text-xs">
          <div className="flex justify-between py-2 border-b border-zinc-800">
            <span className="text-zinc-400">Martes a Jueves:</span>
            <span className="text-white font-semibold">13:00 - 16:30 | 20:00 - 23:30</span>
          </div>
          <div className="flex justify-between py-2 border-b border-zinc-800">
            <span className="text-zinc-400">Viernes, Sábado y Domingo:</span>
            <span className="text-criollo-yellow font-bold">13:00 - 23:30 (Continuo)</span>
          </div>
          <div className="flex justify-between py-2">
            <span className="text-zinc-400">Lunes:</span>
            <span className="text-red-400 font-bold">Cerrado por descanso</span>
          </div>
        </div>

        <div className="pt-3 border-t border-criollo-border/60">
          <h4 className="font-bold text-xs text-zinc-300 mb-1.5 flex items-center gap-1.5">
            <Train className="w-3.5 h-3.5 text-criollo-red" />
            <span>Cómo llegar en transporte público:</span>
          </h4>
          <p className="text-xs text-zinc-400 leading-relaxed">
            • <strong>Metro Alvarado</strong> (Línea 1): Salida Calle Bravo Murillo, 2 minutos a pie.<br />
            • <strong>Metro Cuatro Caminos</strong> (Líneas 1, 2 y 6): 5 minutos a pie.<br />
            • Autobuses EMT: Líneas 3, 64, 66, 124, 128 (Parada Mercado Maravillas).
          </p>
        </div>
      </div>

      {/* Free Taco Promo Banner with Google Reviews */}
      <FreeTacoPromoBanner />

      {/* Google Reviews */}
      <GoogleReviewsSection />
    </div>
  );
}
