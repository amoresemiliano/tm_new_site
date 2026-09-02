"use client";

import React from "react";
import Link from "next/link";
import { Utensils } from "lucide-react";

export default function NotFound() {
  return (
    <div className="bg-criollo-card border border-criollo-border rounded-3xl p-8 sm:p-12 text-center space-y-4 my-10 shadow-2xl">
      <div className="text-5xl">🌮</div>
      <h2 className="text-2xl font-black text-white">404 · Platillo No Encontrado</h2>
      <p className="text-xs text-zinc-400 max-w-xs mx-auto leading-relaxed">
        La página que buscas no existe o ha cambiado de dirección.
      </p>
      <div className="pt-2">
        <Link
          href="/"
          className="bg-criollo-red hover:bg-criollo-redDark text-white font-bold py-2.5 px-5 rounded-xl text-xs inline-flex items-center gap-2 shadow-lg"
        >
          <Utensils className="w-4 h-4" />
          <span>Volver a Inicio</span>
        </Link>
      </div>
    </div>
  );
}
