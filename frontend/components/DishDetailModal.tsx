"use client";

import React, { useState } from "react";
import Image from "next/image";
import { useApp } from "@/context/AppContext";
import { SAUCES } from "@/data/mockData";
import { X, Plus, Minus, Flame, Sparkles, Check, AlertCircle } from "lucide-react";

export default function DishDetailModal() {
  const { selectedDish, setSelectedDish, addToCart } = useApp();
  const [quantity, setQuantity] = useState(1);
  const [selectedSauces, setSelectedSauces] = useState<string[]>(["verde"]);
  const [instructions, setInstructions] = useState("");

  if (!selectedDish) return null;

  const toggleSauce = (sauceId: string) => {
    if (selectedSauces.includes(sauceId)) {
      setSelectedSauces(selectedSauces.filter((id) => id !== sauceId));
    } else {
      setSelectedSauces([...selectedSauces, sauceId]);
    }
  };

  const handleAddToCart = () => {
    const sauceNames = selectedSauces.map(
      (id) => SAUCES.find((s) => s.id === id)?.name || id
    );
    addToCart(selectedDish, quantity, sauceNames, instructions);
    setSelectedDish(null);
    setQuantity(1);
    setSelectedSauces(["verde"]);
    setInstructions("");
  };

  const totalPrice = (selectedDish.price * quantity).toFixed(2);

  return (
    <div className="fixed inset-0 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 bg-black/80 backdrop-blur-sm animate-fadeIn">
      <div 
        className="bg-criollo-card border-t sm:border border-criollo-border w-full max-w-lg rounded-t-3xl sm:rounded-3xl overflow-hidden max-h-[90vh] flex flex-col shadow-2xl text-white animate-slideUp"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Modal Header / Banner Image */}
        <div className="relative w-full h-56 bg-zinc-900 flex-shrink-0">
          <Image
            src={selectedDish.imageUrl}
            alt={selectedDish.name}
            fill
            className="object-cover"
          />
          <div className="absolute inset-0 bg-gradient-to-t from-criollo-card via-black/30 to-black/60" />
          
          <button
            onClick={() => setSelectedDish(null)}
            className="absolute top-4 right-4 bg-black/60 hover:bg-black/90 p-2 rounded-full text-white transition-colors"
            aria-label="Cerrar"
          >
            <X className="w-5 h-5" />
          </button>

          <div className="absolute bottom-4 left-4 right-4">
            <div className="flex gap-2 mb-1">
              {selectedDish.isSignature && (
                <span className="bg-criollo-red text-white text-[10px] font-black uppercase px-2 py-0.5 rounded-full">
                  Especialidad
                </span>
              )}
              {selectedDish.spicyLevel > 0 && (
                <span className="bg-black/60 backdrop-blur-sm text-red-400 text-[10px] font-black uppercase px-2 py-0.5 rounded-full flex items-center gap-1">
                  Picante nivel {selectedDish.spicyLevel}
                </span>
              )}
            </div>
            <h2 className="text-xl sm:text-2xl font-black text-white">{selectedDish.name}</h2>
          </div>
        </div>

        {/* Modal Scrollable Body */}
        <div className="p-4 sm:p-6 overflow-y-auto flex-1 space-y-5">
          <p className="text-zinc-300 text-sm leading-relaxed">{selectedDish.description}</p>

          {/* Allergens warning if any */}
          {selectedDish.allergens.length > 0 && (
            <div className="bg-amber-950/30 border border-amber-800/40 rounded-xl p-3 flex items-start gap-2.5 text-xs text-amber-300">
              <AlertCircle className="w-4 h-4 flex-shrink-0 mt-0.5" />
              <div>
                <span className="font-bold">Alérgenos presentes: </span>
                <span>{selectedDish.allergens.join(", ")}</span>
              </div>
            </div>
          )}

          {/* Sauce Customization */}
          <div>
            <h4 className="text-xs font-bold uppercase tracking-wider text-zinc-400 mb-2.5">
              Elige tus salsas caseras para acompañar:
            </h4>
            <div className="space-y-2">
              {SAUCES.map((sauce) => {
                const isSelected = selectedSauces.includes(sauce.id);
                return (
                  <button
                    key={sauce.id}
                    type="button"
                    onClick={() => toggleSauce(sauce.id)}
                    className={`w-full p-2.5 rounded-xl border text-left flex items-center justify-between text-xs transition-all ${
                      isSelected
                        ? "bg-criollo-red/20 border-criollo-red text-white"
                        : "bg-zinc-800/60 border-zinc-700/60 text-zinc-300 hover:bg-zinc-800"
                    }`}
                  >
                    <div className="flex items-center gap-2">
                      <div className={`w-4 h-4 rounded border flex items-center justify-center ${
                        isSelected ? "bg-criollo-red border-criollo-red text-white" : "border-zinc-500"
                      }`}>
                        {isSelected && <Check className="w-3 h-3 stroke-[3]" />}
                      </div>
                      <span className="font-medium">{sauce.name}</span>
                    </div>
                    <span className="text-xs">{sauce.heat}</span>
                  </button>
                );
              })}
            </div>
          </div>

          {/* Special notes */}
          <div>
            <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1.5">
              Notas para el taquero (opcional):
            </label>
            <input
              type="text"
              value={instructions}
              onChange={(e) => setInstructions(e.target.value)}
              placeholder="Ej: Sin cebolla, limón extra..."
              className="w-full bg-zinc-800 border border-zinc-700 rounded-xl px-3 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-criollo-yellow"
            />
          </div>
        </div>

        {/* Modal Footer Controls */}
        <div className="p-4 bg-criollo-surface border-t border-criollo-border flex items-center gap-3">
          {/* Quantity Controls */}
          <div className="flex items-center bg-zinc-800 border border-zinc-700 rounded-xl p-1">
            <button
              onClick={() => setQuantity(Math.max(1, quantity - 1))}
              className="p-1.5 rounded-lg hover:bg-zinc-700 text-zinc-300 transition-colors"
              aria-label="Disminuir"
            >
              <Minus className="w-4 h-4" />
            </button>
            <span className="w-8 text-center font-black text-sm text-white">{quantity}</span>
            <button
              onClick={() => setQuantity(quantity + 1)}
              className="p-1.5 rounded-lg hover:bg-zinc-700 text-zinc-300 transition-colors"
              aria-label="Aumentar"
            >
              <Plus className="w-4 h-4" />
            </button>
          </div>

          {/* Add to order button */}
          <button
            onClick={handleAddToCart}
            className="flex-1 bg-gradient-to-r from-criollo-red to-criollo-redDark hover:opacity-95 text-white font-bold py-3 px-4 rounded-xl shadow-lg shadow-criollo-red/25 flex items-center justify-between transition-all active:scale-[0.98]"
          >
            <span>Añadir a mi pedido</span>
            <span className="bg-black/30 px-2 py-0.5 rounded-lg text-sm font-black">
              {totalPrice} €
            </span>
          </button>
        </div>
      </div>
    </div>
  );
}
