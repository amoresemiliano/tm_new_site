"use client";

import React, { useState } from "react";
import { useApp } from "@/context/AppContext";
import { REWARDS_CATALOG } from "@/data/mockData";
import { IntegrationsConfig } from "@/config/integrations";
import FreeTacoPromoBanner from "@/components/FreeTacoPromoBanner";
import { 
  Award, 
  QrCode, 
  Sparkles, 
  CheckCircle2, 
  Flame, 
  Gift, 
  ChevronRight, 
  Lock, 
  Star,
  ExternalLink,
  UtensilsCrossed
} from "lucide-react";

export default function ClubPage() {
  const { member, redeemReward, showToast } = useApp();
  const [showQrModal, setShowQrModal] = useState(false);

  const progressPercent = Math.min(100, Math.round((member.points / member.nextTierPoints) * 100));

  return (
    <div className="space-y-6 pb-8">
      {/* Header */}
      <div>
        <h1 className="text-2xl sm:text-3xl font-black text-white flex items-center gap-2">
          <span>Club Taquero</span>
          <Award className="w-6 h-6 text-criollo-yellow" />
        </h1>
        <p className="text-xs sm:text-sm text-zinc-400 mt-1">
          Acumula puntos con cada taco, sube de nivel y desbloquea platillos y premios gratis.
        </p>
      </div>

      {/* Digital Membership Pass Card */}
      <div className="relative overflow-hidden rounded-3xl bg-gradient-to-br from-amber-600 via-criollo-redDark to-black border-2 border-criollo-yellow/50 p-6 text-white shadow-2xl space-y-4">
        {/* Glow effect */}
        <div className="absolute top-0 right-0 w-48 h-48 bg-criollo-yellow/20 rounded-full blur-3xl pointer-events-none" />

        <div className="relative z-10 flex items-center justify-between">
          <div className="flex items-center gap-2">
            <span className="text-2xl">🌮</span>
            <div>
              <span className="text-[10px] font-black uppercase tracking-widest text-zinc-300">
                Pase Digital Oficial
              </span>
              <h3 className="text-base font-black text-white">El Criollo · Hermandad Taquera</h3>
            </div>
          </div>

          <span 
            className="text-xs font-black px-3 py-1 rounded-full shadow-md text-criollo-black flex items-center gap-1"
            style={{ backgroundColor: member.tierColor }}
          >
            <Sparkles className="w-3.5 h-3.5" />
            <span>{member.tierTitle}</span>
          </span>
        </div>

        {/* Member Details */}
        <div className="relative z-10 grid grid-cols-2 gap-4 pt-2">
          <div>
            <span className="text-[10px] uppercase font-bold text-zinc-300">Miembro</span>
            <div className="font-extrabold text-base text-white truncate">{member.name}</div>
            <div className="text-[11px] text-zinc-300">Socio #{member.id}</div>
          </div>

          <div className="text-right">
            <span className="text-[10px] uppercase font-bold text-zinc-300">Balance Actual</span>
            <div className="text-3xl font-black text-criollo-yellow tracking-tight">
              {member.points} <span className="text-sm font-bold text-white">pts</span>
            </div>
          </div>
        </div>

        {/* Progress to Next Tier */}
        <div className="relative z-10 pt-2 space-y-1.5">
          <div className="flex justify-between text-[11px] text-zinc-300 font-medium">
            <span>Progreso a <strong>Taquero Maestro</strong></span>
            <span>{member.points} / {member.nextTierPoints} pts</span>
          </div>
          <div className="w-full bg-black/50 h-2.5 rounded-full overflow-hidden border border-white/20">
            <div
              className="bg-gradient-to-r from-criollo-yellow to-amber-400 h-full rounded-full transition-all duration-500"
              style={{ width: `${progressPercent}%` }}
            />
          </div>
        </div>

        {/* Pass Footer Actions */}
        <div className="relative z-10 pt-2 flex items-center justify-between border-t border-white/20">
          <span className="text-[11px] text-zinc-300">
            🌮 <strong>{member.tacosEaten} tacos</strong> saboreados
          </span>

          <button
            onClick={() => setShowQrModal(true)}
            className="bg-criollo-yellow hover:bg-amber-400 text-criollo-black font-black text-xs px-3.5 py-2 rounded-xl flex items-center gap-1.5 shadow-md transition-all active:scale-95"
          >
            <QrCode className="w-4 h-4" />
            <span>Ver mi QR de Caja</span>
          </button>
        </div>
      </div>

      {/* QR Modal */}
      {showQrModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm animate-fadeIn">
          <div className="bg-criollo-card border border-criollo-border rounded-3xl p-6 max-w-sm w-full text-center space-y-4 shadow-2xl text-white animate-slideUp">
            <div className="flex justify-between items-center pb-2 border-b border-zinc-800">
              <h4 className="font-extrabold text-sm">Tu Pase Digital</h4>
              <button
                onClick={() => setShowQrModal(false)}
                className="text-zinc-400 hover:text-white text-xs font-bold"
              >
                Cerrar
              </button>
            </div>

            <div className="bg-white p-4 rounded-2xl inline-block shadow-lg">
              <div className="w-44 h-44 bg-zinc-900 rounded-xl flex flex-col items-center justify-center text-zinc-100 p-2 text-center">
                <QrCode className="w-28 h-28 text-criollo-black" />
                <span className="text-[10px] text-zinc-600 font-mono mt-1">{member.qrCode}</span>
              </div>
            </div>

            <p className="text-xs text-zinc-300">
              Muestra este código al taquero al pagar en el puesto para sumar puntos o canjear tus premios.
            </p>

            <button
              onClick={() => {
                setShowQrModal(false);
                showToast("¡QR listo para escanear!");
              }}
              className="w-full bg-criollo-red hover:bg-criollo-redDark text-white font-bold py-2.5 rounded-xl text-xs"
            >
              Listo
            </button>
          </div>
        </div>
      )}

      {/* Promo banner: Taco gratis con reseña */}
      <FreeTacoPromoBanner />

      {/* Rewards Catalog */}
      <div className="space-y-3">
        <div>
          <h2 className="text-lg font-black text-white flex items-center gap-2">
            <span>Catálogo de Premios</span>
            <Gift className="w-4 h-4 text-criollo-yellow" />
          </h2>
          <p className="text-xs text-zinc-400">Canjea tus puntos acumulados por comida y regalos</p>
        </div>

        <div className="space-y-2.5">
          {REWARDS_CATALOG.map((reward) => {
            const canAfford = member.points >= reward.pointsRequired;
            return (
              <div
                key={reward.id}
                className="bg-criollo-card border border-criollo-border rounded-2xl p-4 flex items-center justify-between gap-3 hover:border-zinc-700 transition-colors"
              >
                <div className="flex items-center gap-3">
                  <div className={`w-11 h-11 rounded-2xl flex items-center justify-center flex-shrink-0 ${
                    canAfford ? "bg-criollo-red/20 text-criollo-red" : "bg-zinc-800 text-zinc-500"
                  }`}>
                    <Award className="w-5 h-5" />
                  </div>
                  <div>
                    <h4 className="font-bold text-sm text-white">{reward.title}</h4>
                    <p className="text-xs text-zinc-400 mt-0.5">{reward.description}</p>
                    <span className="text-[11px] font-extrabold text-criollo-yellow">
                      {reward.pointsRequired} puntos
                    </span>
                  </div>
                </div>

                <button
                  onClick={() => redeemReward(reward.id, reward.pointsRequired)}
                  disabled={!canAfford}
                  className={`px-3.5 py-2 rounded-xl text-xs font-bold transition-all flex-shrink-0 ${
                    canAfford
                      ? "bg-criollo-yellow hover:bg-amber-400 text-criollo-black font-black shadow-md active:scale-95"
                      : "bg-zinc-800 text-zinc-500 border border-zinc-700 cursor-not-allowed"
                  }`}
                >
                  {canAfford ? "Canjear" : "Te faltan pts"}
                </button>
              </div>
            );
          })}
        </div>
      </div>

      {/* Gamification / Taquero Missions */}
      <div className="bg-criollo-card border border-criollo-border rounded-3xl p-5 space-y-3">
        <h3 className="font-black text-sm text-white flex items-center gap-2">
          <Flame className="w-4 h-4 text-criollo-red" />
          <span>Misiones Taqueras de la Semana</span>
        </h3>

        <div className="space-y-2 text-xs">
          <div className="bg-zinc-800/60 border border-zinc-700/60 rounded-2xl p-3 flex items-center justify-between">
            <div>
              <span className="font-bold text-white block">Escribe tu reseña en Google Maps</span>
              <span className="text-zinc-400 text-[11px]">Suma puntos y llévate 1 taco gratis</span>
            </div>
            <span className="bg-criollo-yellow text-criollo-black text-[10px] font-black px-2 py-0.5 rounded-full">
              +25 PTS
            </span>
          </div>

          <div className="bg-zinc-800/60 border border-zinc-700/60 rounded-2xl p-3 flex items-center justify-between">
            <div>
              <span className="font-bold text-white block">Pide los 4 tacos tradicionales</span>
              <span className="text-zinc-400 text-[11px]">Pastor, Birria, Suadero y Carnitas (2/4)</span>
            </div>
            <span className="bg-criollo-yellow text-criollo-black text-[10px] font-black px-2 py-0.5 rounded-full">
              +40 PTS
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}
