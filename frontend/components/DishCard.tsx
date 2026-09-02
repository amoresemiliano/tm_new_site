"use client";

import React from "react";
import Image from "next/image";
import { MenuItem } from "@/types";
import { useApp } from "@/context/AppContext";
import { Plus, Flame, Sparkles, Check } from "lucide-react";

interface DishCardProps {
  item: MenuItem;
}

export default function DishCard({ item }: DishCardProps) {
  const { setSelectedDish, addToCart, cart } = useApp();

  const cartQuantity = cart.find((ci) => ci.menuItem.id === item.id)?.quantity || 0;

  const handleQuickAdd = (e: React.MouseEvent) => {
    e.stopPropagation();
    addToCart(item, 1);
  };

  return (
    <div
      onClick={() => setSelectedDish(item)}
      className="bg-criollo-card border border-criollo-border hover:border-criollo-red/50 rounded-2xl overflow-hidden cursor-pointer transition-all hover:shadow-xl hover:shadow-criollo-red/10 flex flex-col group relative"
    >
      {/* Dish Image */}
      <div className="relative w-full h-44 bg-zinc-900 overflow-hidden">
        <Image
          src={item.imageUrl}
          alt={item.name}
          fill
          sizes="(max-width: 640px) 100vw, (max-width: 1024px) 50vw, 33vw"
          className="object-cover group-hover:scale-105 transition-transform duration-500"
        />
        <div className="absolute inset-0 bg-gradient-to-t from-criollo-card via-transparent to-black/30" />

        {/* Top Badges */}
        <div className="absolute top-2.5 left-2.5 flex flex-wrap gap-1.5">
          {item.isSignature && (
            <span className="bg-criollo-red text-white text-[10px] font-black uppercase px-2 py-0.5 rounded-full flex items-center gap-1 shadow-md">
              <Sparkles className="w-2.5 h-2.5" />
              Especialidad
            </span>
          )}
          {item.isPopular && !item.isSignature && (
            <span className="bg-criollo-yellow text-criollo-black text-[10px] font-black uppercase px-2 py-0.5 rounded-full shadow-md">
              Popular
            </span>
          )}
          {item.isNew && (
            <span className="bg-emerald-600 text-white text-[10px] font-black uppercase px-2 py-0.5 rounded-full shadow-md">
              Nuevo
            </span>
          )}
        </div>

        {/* Spicy Level Indicator */}
        {item.spicyLevel > 0 && (
          <div className="absolute top-2.5 right-2.5 bg-black/70 backdrop-blur-sm px-1.5 py-0.5 rounded-md text-xs font-bold text-red-400 flex items-center gap-0.5">
            {Array.from({ length: item.spicyLevel }).map((_, i) => (
              <span key={i}>🌶️</span>
            ))}
          </div>
        )}

        {/* In Cart Indicator */}
        {cartQuantity > 0 && (
          <div className="absolute bottom-2 left-2.5 bg-emerald-600 text-white text-xs font-black px-2 py-0.5 rounded-lg flex items-center gap-1 shadow-lg">
            <Check className="w-3 h-3 stroke-[3]" />
            <span>{cartQuantity} en pedido</span>
          </div>
        )}
      </div>

      {/* Content */}
      <div className="p-3.5 flex-1 flex flex-col justify-between">
        <div>
          <h3 className="font-bold text-white text-base group-hover:text-criollo-yellow transition-colors leading-snug">
            {item.name}
          </h3>
          <p className="text-zinc-400 text-xs mt-1 line-clamp-2 leading-relaxed">
            {item.description}
          </p>
        </div>

        <div className="mt-3 pt-2.5 border-t border-criollo-border/60 flex items-center justify-between">
          <div>
            <span className="text-xs text-zinc-400 font-medium block -mb-0.5">Precio</span>
            <span className="text-base font-extrabold text-white">
              {item.price.toFixed(2)} €
            </span>
          </div>

          <button
            onClick={handleQuickAdd}
            className="bg-criollo-red hover:bg-criollo-redDark text-white p-2 rounded-xl transition-all shadow-md shadow-criollo-red/20 active:scale-90 flex items-center gap-1 text-xs font-bold px-3"
            aria-label={`Añadir ${item.name}`}
          >
            <Plus className="w-4 h-4" />
            <span>Añadir</span>
          </button>
        </div>
      </div>
    </div>
  );
}
