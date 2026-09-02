"use client";

import React, { useState } from "react";
import { IntegrationsConfig } from "@/config/integrations";
import { useApp } from "@/context/AppContext";
import { Star, Gift, ExternalLink, CheckCircle2 } from "lucide-react";

export default function FreeTacoPromoBanner() {
  const { claimGoogleReviewPromo, hasClaimedReviewPromo } = useApp();
  const [showInstructions, setShowInstructions] = useState(false);

  const handleClaim = () => {
    claimGoogleReviewPromo();
    window.open(IntegrationsConfig.GOOGLE_REVIEWS_URL, "_blank");
  };

  return (
    <div className="relative overflow-hidden rounded-3xl bg-gradient-to-br from-criollo-red via-criollo-redDark to-black border-2 border-criollo-yellow/40 p-5 shadow-2xl text-white">
      {/* Decorative background glow */}
      <div className="absolute -right-10 -bottom-10 w-40 h-40 bg-criollo-yellow/20 rounded-full blur-2xl pointer-events-none" />

      <div className="relative z-10">
        <div className="flex items-center justify-between gap-2 mb-2">
          <div className="flex items-center gap-1.5 bg-criollo-yellow text-criollo-black font-black text-[11px] uppercase tracking-wider px-2.5 py-0.5 rounded-full shadow-md">
            <Gift className="w-3.5 h-3.5" />
            <span>Promo Exclusiva</span>
          </div>
          <div className="flex items-center gap-0.5 text-criollo-yellow text-xs font-bold">
            <Star className="w-3.5 h-3.5 fill-criollo-yellow" />
            <Star className="w-3.5 h-3.5 fill-criollo-yellow" />
            <Star className="w-3.5 h-3.5 fill-criollo-yellow" />
            <Star className="w-3.5 h-3.5 fill-criollo-yellow" />
            <Star className="w-3.5 h-3.5 fill-criollo-yellow" />
          </div>
        </div>

        <h3 className="text-xl sm:text-2xl font-black text-white leading-tight">
          ¡Llévate 1 Taco Gratis con tu Reseña en Google!
        </h3>

        <p className="text-zinc-200 text-xs sm:text-sm mt-1.5 leading-relaxed max-w-lg">
          Apoya a tu taquería favorita en el Mercado Maravillas de Tetuán. Déjanos tu valoración sincera de 5 estrellas y llévate un <strong className="text-criollo-yellow font-black">Taco al Pastor o Suadero</strong> totalmente gratis.
        </p>

        {showInstructions ? (
          <div className="mt-4 bg-black/40 border border-white/10 rounded-2xl p-4 text-xs space-y-2 animate-fadeIn">
            <div className="flex items-start gap-2">
              <span className="w-5 h-5 rounded-full bg-criollo-yellow text-criollo-black font-black flex items-center justify-center text-[10px] flex-shrink-0">
                1
              </span>
              <span>Haz clic en el botón abajo para abrir la ficha oficial de <strong>Google Reviews</strong>.</span>
            </div>
            <div className="flex items-start gap-2">
              <span className="w-5 h-5 rounded-full bg-criollo-yellow text-criollo-black font-black flex items-center justify-center text-[10px] flex-shrink-0">
                2
              </span>
              <span>Escribe tu reseña y cuéntanos qué platillo te gustó más (¡con foto suma más!).</span>
            </div>
            <div className="flex items-start gap-2">
              <span className="w-5 h-5 rounded-full bg-criollo-yellow text-criollo-black font-black flex items-center justify-center text-[10px] flex-shrink-0">
                3
              </span>
              <span>Muestra la pantalla de confirmación a los taqueros en la barra para canjear tu taco.</span>
            </div>
          </div>
        ) : null}

        <div className="mt-4 flex flex-wrap gap-2.5">
          <button
            onClick={handleClaim}
            className="flex-1 min-w-[200px] bg-criollo-yellow hover:bg-amber-400 text-criollo-black font-black py-2.5 px-4 rounded-xl shadow-lg flex items-center justify-center gap-2 text-xs sm:text-sm transition-all active:scale-95"
          >
            <Star className="w-4 h-4 fill-criollo-black" />
            <span>{hasClaimedReviewPromo ? "Abrir Reseña Nuevamente" : "Escribir Reseña en Google"}</span>
            <ExternalLink className="w-3.5 h-3.5" />
          </button>

          <button
            onClick={() => setShowInstructions(!showInstructions)}
            className="bg-white/10 hover:bg-white/20 text-white font-bold py-2.5 px-3.5 rounded-xl text-xs transition-colors border border-white/15"
          >
            {showInstructions ? "Ocultar pasos" : "¿Cómo funciona?"}
          </button>
        </div>

        {hasClaimedReviewPromo && (
          <div className="mt-3 flex items-center gap-1.5 text-xs text-emerald-300 font-bold bg-emerald-950/50 border border-emerald-500/30 px-3 py-1.5 rounded-xl">
            <CheckCircle2 className="w-4 h-4" />
            <span>¡Promo activada! Muestra tu reseña al pagar para recibir tu taco gratis.</span>
          </div>
        )}
      </div>
    </div>
  );
}
