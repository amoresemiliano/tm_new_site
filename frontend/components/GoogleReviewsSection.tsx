"use client";

import React from "react";
import { GOOGLE_REVIEWS, PRIMARY_LOCATION } from "@/data/mockData";
import { IntegrationsConfig } from "@/config/integrations";
import { Star, MessageSquare, ExternalLink, Quote, Sparkles } from "lucide-react";

export default function GoogleReviewsSection() {
  return (
    <section className="mt-8 space-y-4">
      {/* Header Rating Box */}
      <div className="bg-criollo-card border border-criollo-border rounded-3xl p-5 flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div className="flex items-center gap-4">
          <div className="w-14 h-14 rounded-2xl bg-zinc-800 border border-zinc-700 flex flex-col items-center justify-center flex-shrink-0">
            <span className="text-xl font-black text-white">4.9</span>
            <div className="flex text-criollo-yellow">
              <Star className="w-2.5 h-2.5 fill-criollo-yellow" />
              <Star className="w-2.5 h-2.5 fill-criollo-yellow" />
              <Star className="w-2.5 h-2.5 fill-criollo-yellow" />
              <Star className="w-2.5 h-2.5 fill-criollo-yellow" />
              <Star className="w-2.5 h-2.5 fill-criollo-yellow" />
            </div>
          </div>
          <div>
            <div className="flex items-center gap-1.5">
              <h4 className="font-extrabold text-white text-base">Google Reviews</h4>
              <span className="bg-emerald-900/60 border border-emerald-500/40 text-emerald-300 text-[10px] font-black uppercase px-2 py-0.5 rounded-full">
                Verificadas
              </span>
            </div>
            <p className="text-xs text-zinc-400 mt-0.5">
              Basado en más de <strong>{PRIMARY_LOCATION.totalReviews} reseñas reales</strong> en Google Maps
            </p>
          </div>
        </div>

        <a
          href={IntegrationsConfig.GOOGLE_REVIEWS_URL}
          target="_blank"
          rel="noopener noreferrer"
          className="bg-zinc-800 hover:bg-zinc-700 border border-zinc-600 text-white font-bold px-4 py-2.5 rounded-xl text-xs flex items-center justify-center gap-2 transition-colors self-start sm:self-auto w-full sm:w-auto"
        >
          <MessageSquare className="w-4 h-4 text-criollo-yellow" />
          <span>Ver todas en Google</span>
          <ExternalLink className="w-3.5 h-3.5 text-zinc-400" />
        </a>
      </div>

      {/* Reviews Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
        {GOOGLE_REVIEWS.map((rev) => (
          <div
            key={rev.id}
            className="bg-criollo-card/80 border border-criollo-border/60 rounded-2xl p-4 flex flex-col justify-between hover:border-zinc-700 transition-colors"
          >
            <div>
              <div className="flex items-center justify-between mb-2">
                <div className="flex items-center gap-2">
                  <div className="w-8 h-8 rounded-full bg-gradient-to-tr from-criollo-red to-criollo-yellow flex items-center justify-center text-xs font-black text-white">
                    {rev.author.charAt(0)}
                  </div>
                  <div>
                    <h5 className="font-bold text-white text-xs">{rev.author}</h5>
                    <span className="text-[10px] text-zinc-400">{rev.relativeTime}</span>
                  </div>
                </div>
                <div className="flex text-criollo-yellow">
                  {Array.from({ length: rev.rating }).map((_, i) => (
                    <Star key={i} className="w-3 h-3 fill-criollo-yellow" />
                  ))}
                </div>
              </div>

              <p className="text-zinc-300 text-xs italic leading-relaxed">
                "{rev.comment}"
              </p>
            </div>

            {rev.highlightDish && (
              <div className="mt-3 pt-2 border-t border-criollo-border/40 flex items-center gap-1.5 text-[11px] text-criollo-yellow font-medium">
                <Sparkles className="w-3 h-3" />
                <span>Recomienda: <strong>{rev.highlightDish}</strong></span>
              </div>
            )}
          </div>
        ))}
      </div>
    </section>
  );
}
