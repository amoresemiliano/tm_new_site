"use client";

import React from "react";
import Link from "next/link";
import Image from "next/image";
import { MENU_ITEMS, PROMOTIONS, PRIMARY_LOCATION } from "@/data/mockData";
import { IntegrationsConfig } from "@/config/integrations";
import DishCard from "@/components/DishCard";
import FreeTacoPromoBanner from "@/components/FreeTacoPromoBanner";
import GoogleReviewsSection from "@/components/GoogleReviewsSection";
import { 
  Utensils, 
  ShoppingBag, 
  Calendar, 
  Award, 
  MapPin, 
  Clock, 
  Flame, 
  Sparkles, 
  ChevronRight, 
  Phone,
  QrCode,
  ArrowRight
} from "lucide-react";

export default function HomePage() {
  const popularDishes = MENU_ITEMS.filter((item) => item.isPopular || item.isSignature).slice(0, 4);

  return (
    <div className="space-y-6 pb-6">
      {/* Hero Section */}
      <section className="relative overflow-hidden rounded-3xl bg-gradient-to-r from-criollo-black via-zinc-900 to-criollo-redDark/70 border border-criollo-border p-6 sm:p-8 text-white shadow-2xl">
        <div className="relative z-10 max-w-xl">
          <div className="inline-flex items-center gap-1.5 bg-criollo-red text-white text-xs font-black uppercase px-3 py-1 rounded-full mb-3 shadow-md">
            <Flame className="w-3.5 h-3.5 fill-criollo-yellow text-criollo-yellow" />
            <span>Taquería Auténtica de Mercado</span>
          </div>

          <h1 className="text-3xl sm:text-4xl md:text-5xl font-black tracking-tight leading-tight">
            El verdadero sabor de <span className="text-criollo-yellow">México</span> en Tetuán
          </h1>

          <p className="text-zinc-300 text-sm sm:text-base mt-2.5 leading-relaxed">
            Trompo de pastor artesanal, quesabirrias jugosas con consomé y tortillas nixtamalizadas en el puesto del mítico Mercado Maravillas.
          </p>

          {/* Hero CTAs */}
          <div className="mt-5 flex flex-wrap gap-3">
            <Link
              href="/menu"
              className="bg-criollo-red hover:bg-criollo-redDark text-white font-bold py-3 px-5 rounded-xl text-sm flex items-center gap-2 shadow-lg shadow-criollo-red/30 transition-all active:scale-95"
            >
              <Utensils className="w-4 h-4" />
              <span>Ver Nuestra Carta</span>
            </Link>

            <Link
              href="/pedir"
              className="bg-zinc-800 hover:bg-zinc-700 border border-zinc-600 text-white font-bold py-3 px-5 rounded-xl text-sm flex items-center gap-2 transition-all active:scale-95"
            >
              <ShoppingBag className="w-4 h-4 text-criollo-yellow" />
              <span>Pedir para Llevar</span>
            </Link>
          </div>
        </div>

        {/* Decorative background image overlay */}
        <div className="absolute right-0 top-0 bottom-0 w-1/3 opacity-20 pointer-events-none hidden md:block">
          <Image
            src="https://images.unsplash.com/photo-1551504734-5ee1c4a1479b?w=600&auto=format&fit=crop&q=80"
            alt="Tacos"
            fill
            className="object-cover"
          />
        </div>
      </section>

      {/* Dine-In Bar / "Estoy en el Local" Quick Banner */}
      <section className="bg-gradient-to-r from-amber-950/40 via-zinc-900 to-zinc-900 border border-criollo-yellow/30 rounded-2xl p-4 flex items-center justify-between gap-3">
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 rounded-xl bg-criollo-yellow/20 border border-criollo-yellow/40 flex items-center justify-center text-criollo-yellow flex-shrink-0">
            <QrCode className="w-5 h-5" />
          </div>
          <div>
            <h3 className="font-bold text-sm text-white flex items-center gap-1.5">
              <span>¿Estás en el Mercado Maravillas?</span>
              <span className="bg-criollo-yellow text-criollo-black text-[10px] font-black uppercase px-1.5 py-0.2 rounded">
                Mesa QR
              </span>
            </h3>
            <p className="text-xs text-zinc-400">Pide directo a tu mesa, pide la cuenta o llama al camarero</p>
          </div>
        </div>

        <Link
          href="/en-el-local"
          className="bg-criollo-yellow hover:bg-amber-400 text-criollo-black font-black text-xs py-2 px-3.5 rounded-xl flex-shrink-0 transition-transform active:scale-95"
        >
          En el Local
        </Link>
      </section>

      {/* Free Taco Promo Banner with Google Reviews */}
      <FreeTacoPromoBanner />

      {/* Quick Navigation Cards Grid */}
      <section className="grid grid-cols-2 sm:grid-cols-4 gap-3">
        <Link
          href="/menu"
          className="bg-criollo-card border border-criollo-border hover:border-criollo-red/50 rounded-2xl p-4 flex flex-col items-center text-center group transition-all"
        >
          <div className="w-11 h-11 rounded-xl bg-criollo-red/20 text-criollo-red flex items-center justify-center mb-2 group-hover:scale-110 transition-transform">
            <Utensils className="w-5 h-5" />
          </div>
          <span className="font-bold text-sm text-white group-hover:text-criollo-yellow transition-colors">La Carta</span>
          <span className="text-[11px] text-zinc-400 mt-0.5">Tacos, salsas & birria</span>
        </Link>

        <Link
          href="/pedir"
          className="bg-criollo-card border border-criollo-border hover:border-criollo-red/50 rounded-2xl p-4 flex flex-col items-center text-center group transition-all"
        >
          <div className="w-11 h-11 rounded-xl bg-criollo-yellow/20 text-criollo-yellow flex items-center justify-center mb-2 group-hover:scale-110 transition-transform">
            <ShoppingBag className="w-5 h-5" />
          </div>
          <span className="font-bold text-sm text-white group-hover:text-criollo-yellow transition-colors">Pedir Online</span>
          <span className="text-[11px] text-zinc-400 mt-0.5">Recogida & Delivery</span>
        </Link>

        <Link
          href="/reservar"
          className="bg-criollo-card border border-criollo-border hover:border-criollo-red/50 rounded-2xl p-4 flex flex-col items-center text-center group transition-all"
        >
          <div className="w-11 h-11 rounded-xl bg-emerald-900/30 text-emerald-400 flex items-center justify-center mb-2 group-hover:scale-110 transition-transform">
            <Calendar className="w-5 h-5" />
          </div>
          <span className="font-bold text-sm text-white group-hover:text-criollo-yellow transition-colors">Reservar Mesa</span>
          <span className="text-[11px] text-zinc-400 mt-0.5">Mesa en el puesto</span>
        </Link>

        <Link
          href="/club"
          className="bg-criollo-card border border-criollo-border hover:border-criollo-red/50 rounded-2xl p-4 flex flex-col items-center text-center group transition-all"
        >
          <div className="w-11 h-11 rounded-xl bg-amber-900/30 text-criollo-yellow flex items-center justify-center mb-2 group-hover:scale-110 transition-transform">
            <Award className="w-5 h-5" />
          </div>
          <span className="font-bold text-sm text-white group-hover:text-criollo-yellow transition-colors">Club Taquero</span>
          <span className="text-[11px] text-zinc-400 mt-0.5">Puntos & Premios</span>
        </Link>
      </section>

      {/* Featured / Popular Dishes Section */}
      <section className="space-y-4">
        <div className="flex items-center justify-between">
          <div>
            <h2 className="text-xl font-black text-white flex items-center gap-2">
              <span>Los Favoritos del Trompo</span>
              <Sparkles className="w-4 h-4 text-criollo-yellow" />
            </h2>
            <p className="text-xs text-zinc-400">Las recetas más aclamadas por nuestros clientes en Madrid</p>
          </div>
          <Link
            href="/menu"
            className="text-xs font-bold text-criollo-yellow hover:underline flex items-center gap-0.5"
          >
            <span>Ver toda la carta</span>
            <ChevronRight className="w-3.5 h-3.5" />
          </Link>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          {popularDishes.map((item) => (
            <DishCard key={item.id} item={item} />
          ))}
        </div>
      </section>

      {/* Google Reviews Section */}
      <GoogleReviewsSection />

      {/* Location Box & Operating Hours */}
      <section className="bg-criollo-card border border-criollo-border rounded-3xl p-5 space-y-4">
        <div className="flex items-center gap-2 text-criollo-yellow font-black text-xs uppercase tracking-wider">
          <MapPin className="w-4 h-4" />
          <span>Visítanos en el Mercado</span>
        </div>

        <div className="space-y-2">
          <h3 className="text-lg font-black text-white">{PRIMARY_LOCATION.name}</h3>
          <p className="text-xs text-zinc-300">{PRIMARY_LOCATION.address}</p>
          <p className="text-xs text-zinc-400 flex items-center gap-1.5">
            <span className="w-2 h-2 rounded-full bg-emerald-500 inline-block" />
            <span>{PRIMARY_LOCATION.metro}</span>
          </p>
        </div>

        <div className="pt-3 border-t border-criollo-border/60 flex items-center justify-between text-xs">
          <div className="flex items-center gap-2 text-zinc-300">
            <Clock className="w-4 h-4 text-criollo-yellow" />
            <span>{PRIMARY_LOCATION.schedule}</span>
          </div>
          <a
            href={PRIMARY_LOCATION.mapsUrl}
            target="_blank"
            rel="noopener noreferrer"
            className="bg-criollo-red hover:bg-criollo-redDark text-white font-bold px-3 py-1.5 rounded-xl transition-colors flex items-center gap-1"
          >
            <span>Cómo Llegar</span>
            <ArrowRight className="w-3 h-3" />
          </a>
        </div>
      </section>
    </div>
  );
}
