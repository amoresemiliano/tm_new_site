"use client";

import React, { useState } from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { useApp } from "@/context/AppContext";
import { IntegrationsConfig } from "@/config/integrations";
import { 
  ShoppingBag, 
  MapPin, 
  UtensilsCrossed, 
  Sparkles, 
  ExternalLink,
  Flame,
  Phone
} from "lucide-react";

export default function Header() {
  const pathname = usePathname();
  const { cartCount, currentTable } = useApp();

  return (
    <header className="sticky top-0 z-40 bg-criollo-black/95 backdrop-blur-md border-b border-criollo-border/60 text-white">
      {/* Top micro bar with location info & phone */}
      <div className="bg-criollo-redDark/80 px-4 py-1 text-xs flex justify-between items-center text-white/90">
        <div className="flex items-center gap-1.5 truncate">
          <MapPin className="w-3.5 h-3.5 text-criollo-yellow flex-shrink-0" />
          <span className="truncate">Mercado Maravillas · Tetuán, Madrid</span>
        </div>
        <a 
          href={IntegrationsConfig.PHONE_CLEAN}
          className="flex items-center gap-1 hover:text-criollo-yellow transition-colors font-medium ml-2 flex-shrink-0"
        >
          <Phone className="w-3 h-3 text-criollo-yellow" />
          <span>{IntegrationsConfig.PHONE}</span>
        </a>
      </div>

      {/* Main Bar */}
      <div className="max-w-4xl mx-auto px-4 py-2.5 flex items-center justify-between">
        {/* Brand Logo & Name */}
        <Link href="/" className="flex items-center gap-2 group">
          <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-criollo-red to-criollo-yellow flex items-center justify-center shadow-lg shadow-criollo-red/30 group-hover:scale-105 transition-transform">
            <span className="text-2xl" role="img" aria-label="Taco">🌮</span>
          </div>
          <div>
            <div className="flex items-center gap-1.5">
              <span className="font-extrabold text-lg tracking-tight text-white group-hover:text-criollo-yellow transition-colors">
                EL CRIOLLO
              </span>
              <span className="bg-criollo-red text-[10px] font-black uppercase px-1.5 py-0.5 rounded text-white tracking-wider">
                Taquería
              </span>
            </div>
            <p className="text-[11px] text-zinc-400 -mt-0.5">Tradición Chilanga en Madrid</p>
          </div>
        </Link>

        {/* Right Actions: Table indicator, Last.app Shop, Cart */}
        <div className="flex items-center gap-2">
          {currentTable && (
            <Link
              href="/en-el-local"
              className="bg-criollo-yellow/20 border border-criollo-yellow/50 text-criollo-yellow px-2.5 py-1 rounded-full text-xs font-bold flex items-center gap-1 animate-pulse"
            >
              <UtensilsCrossed className="w-3.5 h-3.5" />
              <span>Mesa {currentTable}</span>
            </Link>
          )}

          <a
            href={IntegrationsConfig.LAST_APP_SHOP_URL}
            target="_blank"
            rel="noopener noreferrer"
            className="hidden sm:flex items-center gap-1.5 text-xs font-semibold bg-zinc-800 hover:bg-zinc-700 border border-zinc-700 px-3 py-1.5 rounded-lg transition-colors text-zinc-200 hover:text-white"
            title="Abrir tienda Last.app"
          >
            <span>Last.app Shop</span>
            <ExternalLink className="w-3.5 h-3.5 text-zinc-400" />
          </a>

          {/* Pedir / Cart Button */}
          <Link
            href="/pedir"
            className={`relative p-2 rounded-xl transition-all ${
              pathname === "/pedir"
                ? "bg-criollo-red text-white"
                : "bg-zinc-800 hover:bg-zinc-700 text-zinc-100"
            }`}
            aria-label="Ver pedido"
          >
            <ShoppingBag className="w-5 h-5" />
            {cartCount > 0 && (
              <span className="absolute -top-1.5 -right-1.5 bg-criollo-yellow text-criollo-black text-xs font-black w-5 h-5 rounded-full flex items-center justify-center shadow-md animate-bounce">
                {cartCount}
              </span>
            )}
          </Link>
        </div>
      </div>
    </header>
  );
}
