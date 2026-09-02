"use client";

import React from "react";
import Link from "next/link";
import { NEWS_ITEMS } from "@/data/mockData";
import { IntegrationsConfig } from "@/config/integrations";
import FreeTacoPromoBanner from "@/components/FreeTacoPromoBanner";
import { 
  Sparkles, 
  Calendar, 
  Star, 
  ExternalLink, 
  ArrowRight, 
  Tag, 
  Flame 
} from "lucide-react";

export default function NovedadesPage() {
  return (
    <div className="space-y-6 pb-8">
      {/* Header */}
      <div>
        <h1 className="text-2xl sm:text-3xl font-black text-white flex items-center gap-2">
          <span>Noticias & Promociones</span>
          <Sparkles className="w-5 h-5 text-criollo-yellow" />
        </h1>
        <p className="text-xs sm:text-sm text-zinc-400 mt-1">
          Nuevas incorporaciones a la carta, eventos especiales y promociones en Tetuán.
        </p>
      </div>

      {/* Main Promo Banner */}
      <FreeTacoPromoBanner />

      {/* News list */}
      <div className="space-y-4">
        {NEWS_ITEMS.map((item) => (
          <article
            key={item.id}
            className="bg-criollo-card border border-criollo-border hover:border-zinc-700 rounded-3xl p-5 sm:p-6 transition-all shadow-lg space-y-3"
          >
            <div className="flex items-center justify-between gap-2">
              <span className={`text-[10px] font-black uppercase px-2.5 py-0.5 rounded-full ${
                item.isPromo 
                  ? "bg-criollo-red text-white" 
                  : "bg-criollo-yellow text-criollo-black"
              }`}>
                {item.tag}
              </span>

              <div className="flex items-center gap-1 text-[11px] text-zinc-400">
                <Calendar className="w-3.5 h-3.5" />
                <span>{item.date}</span>
              </div>
            </div>

            <h3 className="text-lg sm:text-xl font-bold text-white leading-snug">
              {item.title}
            </h3>

            <p className="text-xs sm:text-sm text-zinc-300 leading-relaxed">
              {item.body}
            </p>

            <div className="pt-2 flex items-center justify-between">
              {item.isPromo ? (
                <a
                  href={IntegrationsConfig.GOOGLE_REVIEWS_URL}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="bg-criollo-red hover:bg-criollo-redDark text-white font-bold py-2 px-4 rounded-xl text-xs inline-flex items-center gap-1.5 transition-colors"
                >
                  <Star className="w-3.5 h-3.5 fill-white" />
                  <span>Participar en la promo</span>
                </a>
              ) : (
                <a
                  href={IntegrationsConfig.INSTAGRAM_URL}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="bg-zinc-800 hover:bg-zinc-700 text-white font-bold py-2 px-4 rounded-xl text-xs inline-flex items-center gap-1.5 transition-colors border border-zinc-700"
                >
                  <span>Ver en Instagram</span>
                  <ExternalLink className="w-3.5 h-3.5 text-zinc-400" />
                </a>
              )}
            </div>
          </article>
        ))}
      </div>
    </div>
  );
}
