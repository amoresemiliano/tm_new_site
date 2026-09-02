"use client";

import React, { useState, useMemo } from "react";
import { MENU_ITEMS } from "@/data/mockData";
import { DishCategory } from "@/types";
import DishCard from "@/components/DishCard";
import { 
  Search, 
  Flame, 
  Sparkles, 
  Filter, 
  X, 
  UtensilsCrossed 
} from "lucide-react";

const CATEGORIES: { id: DishCategory | "ALL"; label: string; icon: string }[] = [
  { id: "ALL", label: "Todo", icon: "🌮" },
  { id: "TACOS", label: "Tacos", icon: "🌮" },
  { id: "ESPECIALES", label: "Especiales", icon: "✨" },
  { id: "ENTRANTES", label: "Entrantes", icon: "🥑" },
  { id: "POSTRES", label: "Postres", icon: "🍰" },
  { id: "MICHELADAS", label: "Micheladas", icon: "🍺" },
  { id: "BEBIDAS", label: "Bebidas", icon: "🥤" },
];

export default function MenuPage() {
  const [selectedCategory, setSelectedCategory] = useState<DishCategory | "ALL">("ALL");
  const [searchQuery, setSearchQuery] = useState("");
  const [onlySpicy, setOnlySpicy] = useState(false);
  const [onlyPopular, setOnlyPopular] = useState(false);

  const filteredDishes = useMemo(() => {
    return MENU_ITEMS.filter((item) => {
      // Category filter
      if (selectedCategory !== "ALL" && item.category !== selectedCategory) {
        return false;
      }
      // Search query
      if (searchQuery.trim()) {
        const query = searchQuery.toLowerCase();
        const matchesName = item.name.toLowerCase().includes(query);
        const matchesDesc = item.description.toLowerCase().includes(query);
        const matchesTags = item.tags.some((t) => t.toLowerCase().includes(query));
        if (!matchesName && !matchesDesc && !matchesTags) return false;
      }
      // Spicy filter
      if (onlySpicy && item.spicyLevel === 0) {
        return false;
      }
      // Popular filter
      if (onlyPopular && !item.isPopular && !item.isSignature) {
        return false;
      }
      return true;
    });
  }, [selectedCategory, searchQuery, onlySpicy, onlyPopular]);

  return (
    <div className="space-y-5 pb-8">
      {/* Header */}
      <div>
        <h1 className="text-2xl sm:text-3xl font-black text-white">Nuestra Carta</h1>
        <p className="text-xs sm:text-sm text-zinc-400 mt-1">
          Recetas mexicanas con ingredientes frescos, salsas caseras y tortillas nixtamalizadas.
        </p>
      </div>

      {/* Search Input */}
      <div className="relative">
        <Search className="absolute left-3.5 top-1/2 -translate-y-1/2 w-4 h-4 text-zinc-400" />
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder="Buscar tacos, quesabirrias, micheladas..."
          className="w-full bg-criollo-card border border-criollo-border rounded-2xl py-3 pl-10 pr-10 text-sm text-white placeholder-zinc-500 focus:outline-none focus:border-criollo-yellow"
        />
        {searchQuery && (
          <button
            onClick={() => setSearchQuery("")}
            className="absolute right-3.5 top-1/2 -translate-y-1/2 text-zinc-400 hover:text-white"
            aria-label="Limpiar búsqueda"
          >
            <X className="w-4 h-4" />
          </button>
        )}
      </div>

      {/* Category Filter Horizontal Scroll */}
      <div className="flex gap-2 overflow-x-auto pb-2 scrollbar-none -mx-4 px-4 sm:mx-0 sm:px-0">
        {CATEGORIES.map((cat) => {
          const isSelected = selectedCategory === cat.id;
          return (
            <button
              key={cat.id}
              onClick={() => setSelectedCategory(cat.id)}
              className={`px-4 py-2 rounded-2xl text-xs font-bold flex items-center gap-1.5 whitespace-nowrap transition-all flex-shrink-0 ${
                isSelected
                  ? "bg-criollo-red text-white shadow-lg shadow-criollo-red/30 scale-105"
                  : "bg-criollo-card border border-criollo-border text-zinc-300 hover:border-zinc-600"
              }`}
            >
              <span>{cat.icon}</span>
              <span>{cat.label}</span>
            </button>
          );
        })}
      </div>

      {/* Extra Filters Pills */}
      <div className="flex items-center gap-2 text-xs">
        <button
          onClick={() => setOnlyPopular(!onlyPopular)}
          className={`px-3 py-1.5 rounded-xl border flex items-center gap-1 font-semibold transition-colors ${
            onlyPopular
              ? "bg-criollo-yellow/20 border-criollo-yellow text-criollo-yellow"
              : "border-zinc-800 text-zinc-400 hover:text-zinc-200"
          }`}
        >
          <Sparkles className="w-3.5 h-3.5" />
          <span>Más Populares</span>
        </button>

        <button
          onClick={() => setOnlySpicy(!onlySpicy)}
          className={`px-3 py-1.5 rounded-xl border flex items-center gap-1 font-semibold transition-colors ${
            onlySpicy
              ? "bg-red-950/40 border-red-500 text-red-400"
              : "border-zinc-800 text-zinc-400 hover:text-zinc-200"
          }`}
        >
          <Flame className="w-3.5 h-3.5" />
          <span>Con Picante</span>
        </button>

        <div className="ml-auto text-zinc-500 text-xs font-medium">
          {filteredDishes.length} platillos
        </div>
      </div>

      {/* Dish Grid */}
      {filteredDishes.length > 0 ? (
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          {filteredDishes.map((item) => (
            <DishCard key={item.id} item={item} />
          ))}
        </div>
      ) : (
        <div className="bg-criollo-card border border-criollo-border rounded-3xl p-8 text-center space-y-3">
          <UtensilsCrossed className="w-10 h-10 text-zinc-500 mx-auto" />
          <h3 className="font-bold text-white text-base">No se encontraron platillos</h3>
          <p className="text-xs text-zinc-400 max-w-xs mx-auto">
            Prueba a cambiar el término de búsqueda o selecciona otra categoría de la carta.
          </p>
          <button
            onClick={() => {
              setSelectedCategory("ALL");
              setSearchQuery("");
              setOnlySpicy(false);
              setOnlyPopular(false);
            }}
            className="bg-zinc-800 hover:bg-zinc-700 text-white text-xs font-bold px-4 py-2 rounded-xl transition-colors"
          >
            Restablecer Filtros
          </button>
        </div>
      )}
    </div>
  );
}
