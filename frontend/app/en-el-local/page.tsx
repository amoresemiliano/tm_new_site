"use client";

import React, { useState } from "react";
import Link from "next/link";
import { useApp } from "@/context/AppContext";
import { IntegrationsConfig } from "@/config/integrations";
import FreeTacoPromoBanner from "@/components/FreeTacoPromoBanner";
import { 
  UtensilsCrossed, 
  Bell, 
  CreditCard, 
  Wifi, 
  Copy, 
  Check, 
  ShoppingBag, 
  Sparkles, 
  CheckCircle2,
  PhoneCall
} from "lucide-react";

export default function EnElLocalPage() {
  const { 
    currentTable, 
    setCurrentTable, 
    callWaiterRequested, 
    setCallWaiterRequested,
    billRequested,
    setBillRequested,
    showToast 
  } = useApp();

  const [copiedWifi, setCopiedWifi] = useState(false);

  const handleSelectTable = (tableNum: number) => {
    setCurrentTable(tableNum);
    showToast(`📍 Conectado a la Mesa #${tableNum} de El Criollo`);
  };

  const handleCallWaiter = () => {
    const newState = !callWaiterRequested;
    setCallWaiterRequested(newState);
    if (newState) {
      showToast("🙋 ¡Aviso enviado al camarero! Enseguida se acerca a tu mesa.");
    } else {
      showToast("Aviso cancelado");
    }
  };

  const handleRequestBill = (method: string) => {
    setBillRequested(true);
    showToast(`💳 Solicitud de cuenta (${method}) enviada a barra.`);
  };

  const copyWifi = () => {
    navigator.clipboard.writeText(IntegrationsConfig.VENUE_WIFI_PASS);
    setCopiedWifi(true);
    showToast("Contraseña Wi-Fi copiada al portapapeles");
    setTimeout(() => setCopiedWifi(false), 2500);
  };

  return (
    <div className="space-y-6 pb-8">
      {/* Header */}
      <div>
        <div className="inline-flex items-center gap-1.5 bg-criollo-yellow text-criollo-black text-xs font-black uppercase px-3 py-1 rounded-full mb-2">
          <UtensilsCrossed className="w-3.5 h-3.5" />
          <span>Experiencia en Puesto</span>
        </div>
        <h1 className="text-2xl sm:text-3xl font-black text-white">Estoy en el Local</h1>
        <p className="text-xs sm:text-sm text-zinc-400 mt-1">
          Servicio inteligente en mesa: pide directo a cocina, solicita la cuenta o llama a tu camarero.
        </p>
      </div>

      {/* Table Selector Box */}
      <div className="bg-criollo-card border border-criollo-border rounded-3xl p-5 sm:p-6 space-y-4 shadow-xl">
        <div className="flex items-center justify-between">
          <h3 className="font-extrabold text-sm text-white flex items-center gap-2">
            <span>Selecciona tu Mesa:</span>
            {currentTable && (
              <span className="bg-criollo-red text-white text-xs font-black px-2 py-0.5 rounded-lg">
                Mesa #{currentTable} Activa
              </span>
            )}
          </h3>
          {currentTable && (
            <button
              onClick={() => setCurrentTable(null)}
              className="text-xs text-zinc-400 hover:text-white underline"
            >
              Cambiar
            </button>
          )}
        </div>

        <div className="grid grid-cols-4 sm:grid-cols-8 gap-2">
          {[1, 2, 3, 4, 5, 6, 7, 8].map((num) => {
            const isSelected = currentTable === num;
            return (
              <button
                key={num}
                type="button"
                onClick={() => handleSelectTable(num)}
                className={`py-3 rounded-2xl text-xs font-black transition-all flex flex-col items-center justify-center gap-0.5 ${
                  isSelected
                    ? "bg-criollo-red text-white shadow-lg shadow-criollo-red/30 scale-105"
                    : "bg-zinc-800/80 border border-zinc-700 text-zinc-300 hover:bg-zinc-700"
                }`}
              >
                <span className="text-[10px] text-zinc-400">Mesa</span>
                <span className="text-base">{num}</span>
              </button>
            );
          })}
        </div>
      </div>

      {/* Table Service Action Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
        {/* Order to table */}
        <Link
          href="/menu"
          className="bg-criollo-card border border-criollo-border hover:border-criollo-red p-5 rounded-3xl flex flex-col justify-between group transition-all"
        >
          <div>
            <div className="w-12 h-12 rounded-2xl bg-criollo-red/20 text-criollo-red flex items-center justify-center mb-3 group-hover:scale-110 transition-transform">
              <ShoppingBag className="w-6 h-6" />
            </div>
            <h4 className="font-extrabold text-base text-white group-hover:text-criollo-yellow transition-colors">
              Pedir a Cocina
            </h4>
            <p className="text-xs text-zinc-400 mt-1 leading-relaxed">
              Elige tus tacos de la carta y se marcharán directo a tu mesa #{currentTable || "1"}.
            </p>
          </div>
          <div className="mt-4 pt-2 border-t border-zinc-800 text-xs font-bold text-criollo-yellow flex items-center gap-1">
            <span>Abrir carta y pedir</span>
            <span>→</span>
          </div>
        </Link>

        {/* Call Waiter */}
        <div
          onClick={handleCallWaiter}
          className={`p-5 rounded-3xl border cursor-pointer transition-all flex flex-col justify-between ${
            callWaiterRequested
              ? "bg-amber-950/40 border-criollo-yellow text-white"
              : "bg-criollo-card border-criollo-border hover:border-zinc-700"
          }`}
        >
          <div>
            <div className={`w-12 h-12 rounded-2xl flex items-center justify-center mb-3 ${
              callWaiterRequested ? "bg-criollo-yellow text-criollo-black animate-pulse" : "bg-zinc-800 text-zinc-300"
            }`}>
              <Bell className="w-6 h-6" />
            </div>
            <h4 className="font-extrabold text-base text-white">
              {callWaiterRequested ? "¡Camarero Avisado!" : "Llamar al Camarero"}
            </h4>
            <p className="text-xs text-zinc-400 mt-1 leading-relaxed">
              {callWaiterRequested
                ? "El equipo taquero ya tiene la alerta en pantalla y se acerca a tu mesa."
                : "Solicita asistencia para bebidas, servilletas o cualquier consulta."}
            </p>
          </div>
          <div className="mt-4 pt-2 border-t border-zinc-800 text-xs font-bold text-criollo-yellow">
            {callWaiterRequested ? "Toca para cancelar aviso" : "Tocar para llamar"}
          </div>
        </div>

        {/* Request Bill */}
        <div className="bg-criollo-card border border-criollo-border p-5 rounded-3xl flex flex-col justify-between">
          <div>
            <div className="w-12 h-12 rounded-2xl bg-zinc-800 text-emerald-400 flex items-center justify-center mb-3">
              <CreditCard className="w-6 h-6" />
            </div>
            <h4 className="font-extrabold text-base text-white">Pedir la Cuenta</h4>
            <p className="text-xs text-zinc-400 mt-1 leading-relaxed">
              ¿Listo para pagar? Te acercamos el datáfono o el cambio a tu mesa.
            </p>
          </div>

          <div className="mt-4 pt-2 border-t border-zinc-800 flex gap-2">
            <button
              onClick={() => handleRequestBill("Tarjeta")}
              className="flex-1 bg-zinc-800 hover:bg-zinc-700 text-white font-bold py-2 rounded-xl text-xs transition-colors"
            >
              Con Tarjeta
            </button>
            <button
              onClick={() => handleRequestBill("Efectivo")}
              className="flex-1 bg-zinc-800 hover:bg-zinc-700 text-white font-bold py-2 rounded-xl text-xs transition-colors"
            >
              En Efectivo
            </button>
          </div>
        </div>
      </div>

      {/* Guest Wi-Fi Access Box */}
      <div className="bg-criollo-card border border-criollo-border rounded-3xl p-5 flex flex-col sm:flex-row items-center justify-between gap-4">
        <div className="flex items-center gap-3.5 w-full sm:w-auto">
          <div className="w-11 h-11 rounded-2xl bg-zinc-800 flex items-center justify-center text-criollo-yellow flex-shrink-0">
            <Wifi className="w-5 h-5" />
          </div>
          <div>
            <h4 className="font-bold text-sm text-white">Wi-Fi Clientes Mercado</h4>
            <div className="text-xs text-zinc-400 flex items-center gap-2 mt-0.5">
              <span>Red: <strong className="text-white">{IntegrationsConfig.VENUE_WIFI_SSID}</strong></span>
              <span>·</span>
              <span>Pass: <strong className="text-white font-mono">{IntegrationsConfig.VENUE_WIFI_PASS}</strong></span>
            </div>
          </div>
        </div>

        <button
          onClick={copyWifi}
          className="bg-zinc-800 hover:bg-zinc-700 border border-zinc-700 text-white font-bold py-2 px-4 rounded-xl text-xs flex items-center gap-1.5 transition-colors w-full sm:w-auto justify-center"
        >
          {copiedWifi ? <Check className="w-3.5 h-3.5 text-emerald-400" /> : <Copy className="w-3.5 h-3.5" />}
          <span>{copiedWifi ? "Copiada" : "Copiar Contraseña"}</span>
        </button>
      </div>

      {/* Free taco review banner */}
      <FreeTacoPromoBanner />
    </div>
  );
}
