"use client";

import React, { useState } from "react";
import { useApp } from "@/context/AppContext";
import { IntegrationsConfig } from "@/config/integrations";
import { ReservationRequest } from "@/types";
import { 
  Calendar, 
  Users, 
  Clock, 
  MapPin, 
  CheckCircle2, 
  ExternalLink, 
  Sparkles, 
  Info,
  QrCode
} from "lucide-react";

const TIME_SLOTS = [
  "13:30", "14:00", "14:30", "15:00", "15:30",
  "20:30", "21:00", "21:30", "22:00", "22:30"
];

const ZONES = [
  { id: "barra", name: "Barra de Trompo", desc: "Vistas directas al taquero cortando el pastor" },
  { id: "mesa", name: "Mesa en Mercado", desc: "Ambiente tradicional y espacioso" },
  { id: "terraza", name: "Puesto Alto", desc: "Mesas altas ideales para picar y micheladas" },
];

export default function ReservarPage() {
  const { lastReservation, setLastReservation, showToast } = useApp();
  
  const [guests, setGuests] = useState(2);
  const [date, setDate] = useState("Hoy");
  const [time, setTime] = useState("14:30");
  const [zone, setZone] = useState("Mesa en Mercado");
  const [name, setName] = useState("");
  const [phone, setPhone] = useState("");
  const [email, setEmail] = useState("");
  const [notes, setNotes] = useState("");
  const [isSuccess, setIsSuccess] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const reservation: ReservationRequest = {
      guests,
      date,
      time,
      customerName: name,
      customerPhone: phone,
      customerEmail: email,
      specialRequests: notes,
      zone,
    };
    setLastReservation(reservation);
    setIsSuccess(true);
    showToast("🎉 ¡Mesa confirmada con éxito en El Criollo!");
  };

  if (isSuccess && lastReservation) {
    return (
      <div className="bg-criollo-card border border-criollo-border rounded-3xl p-6 sm:p-8 text-center space-y-5 my-4 shadow-2xl animate-fadeIn">
        <div className="w-16 h-16 rounded-full bg-emerald-900/40 border-2 border-emerald-500 text-emerald-400 flex items-center justify-center mx-auto">
          <CheckCircle2 className="w-9 h-9" />
        </div>

        <div>
          <span className="bg-emerald-950 border border-emerald-500/40 text-emerald-300 text-xs font-black uppercase px-3 py-1 rounded-full">
            Reserva Confirmada
          </span>
          <h2 className="text-2xl font-black text-white mt-3">
            ¡Te esperamos en El Criollo, {lastReservation.customerName}!
          </h2>
          <p className="text-xs sm:text-sm text-zinc-300 mt-1 max-w-md mx-auto">
            Hemos guardado tu mesa en el puesto del Mercado Maravillas de Tetuán.
          </p>
        </div>

        {/* Digital Ticket */}
        <div className="bg-gradient-to-b from-zinc-900 to-black border border-criollo-yellow/30 rounded-3xl p-5 text-left max-w-md mx-auto shadow-xl space-y-3">
          <div className="flex items-center justify-between pb-3 border-b border-zinc-800">
            <div>
              <span className="text-[10px] font-bold text-zinc-400 uppercase">Lugar</span>
              <h4 className="font-extrabold text-sm text-white">Mercado Maravillas · Puesto Criollo</h4>
            </div>
            <div className="bg-criollo-yellow text-criollo-black font-black text-xs px-2.5 py-1 rounded-xl">
              {lastReservation.guests} {lastReservation.guests === 1 ? "Comensal" : "Comensales"}
            </div>
          </div>

          <div className="grid grid-cols-2 gap-3 text-xs">
            <div>
              <span className="text-[10px] text-zinc-400 block uppercase font-bold">Fecha & Hora</span>
              <strong className="text-criollo-yellow text-sm">{lastReservation.date} · {lastReservation.time}</strong>
            </div>
            <div>
              <span className="text-[10px] text-zinc-400 block uppercase font-bold">Zona Asignada</span>
              <strong className="text-white text-sm">{lastReservation.zone}</strong>
            </div>
          </div>

          <div className="pt-2 border-t border-zinc-800 flex items-center justify-between text-[11px] text-zinc-400">
            <span>Contacto: {lastReservation.customerPhone}</span>
            <span className="text-emerald-400 font-bold">Confirmación Inmediata</span>
          </div>
        </div>

        <div className="pt-2 flex flex-col sm:flex-row gap-3 justify-center max-w-md mx-auto">
          <a
            href={IntegrationsConfig.WHATSAPP_URL}
            target="_blank"
            rel="noopener noreferrer"
            className="bg-emerald-600 hover:bg-emerald-700 text-white font-bold py-3 px-4 rounded-xl text-xs flex items-center justify-center gap-2 transition-colors"
          >
            <span>Avisar retraso por WhatsApp</span>
            <ExternalLink className="w-3.5 h-3.5" />
          </a>

          <button
            onClick={() => setIsSuccess(false)}
            className="bg-zinc-800 hover:bg-zinc-700 text-white font-bold py-3 px-4 rounded-xl text-xs transition-colors"
          >
            Modificar Reserva
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6 pb-8">
      {/* Header */}
      <div>
        <h1 className="text-2xl sm:text-3xl font-black text-white">Reservar Mesa</h1>
        <p className="text-xs sm:text-sm text-zinc-400 mt-1">
          Asegura tu sitio en el puesto del Mercado Maravillas de Tetuán y disfruta de tacos recién hechos al momento.
        </p>
      </div>

      <form onSubmit={handleSubmit} className="bg-criollo-card border border-criollo-border rounded-3xl p-5 sm:p-7 space-y-6 shadow-xl">
        {/* Step 1: Number of guests */}
        <div>
          <label className="text-xs font-bold uppercase tracking-wider text-zinc-300 flex items-center gap-1.5 mb-2.5">
            <Users className="w-4 h-4 text-criollo-yellow" />
            <span>¿Cuántas personas van a comer?</span>
          </label>
          <div className="grid grid-cols-4 sm:grid-cols-8 gap-2">
            {[1, 2, 3, 4, 5, 6, 7, 8].map((num) => (
              <button
                key={num}
                type="button"
                onClick={() => setGuests(num)}
                className={`py-2.5 rounded-2xl text-xs font-bold transition-all ${
                  guests === num
                    ? "bg-criollo-red text-white shadow-lg shadow-criollo-red/30 scale-105"
                    : "bg-zinc-800/80 border border-zinc-700 text-zinc-300 hover:bg-zinc-700"
                }`}
              >
                {num} {num === 8 ? "+" : ""}
              </button>
            ))}
          </div>
        </div>

        {/* Step 2: Date Selector */}
        <div>
          <label className="text-xs font-bold uppercase tracking-wider text-zinc-300 flex items-center gap-1.5 mb-2.5">
            <Calendar className="w-4 h-4 text-criollo-yellow" />
            <span>Fecha:</span>
          </label>
          <div className="grid grid-cols-3 gap-2">
            {["Hoy", "Mañana", "Fin de Semana"].map((d) => (
              <button
                key={d}
                type="button"
                onClick={() => setDate(d)}
                className={`py-2.5 rounded-2xl text-xs font-bold transition-all ${
                  date === d
                    ? "bg-criollo-red text-white shadow-lg shadow-criollo-red/30"
                    : "bg-zinc-800/80 border border-zinc-700 text-zinc-300 hover:bg-zinc-700"
                }`}
              >
                {d}
              </button>
            ))}
          </div>
        </div>

        {/* Step 3: Time slot */}
        <div>
          <label className="text-xs font-bold uppercase tracking-wider text-zinc-300 flex items-center gap-1.5 mb-2.5">
            <Clock className="w-4 h-4 text-criollo-yellow" />
            <span>Hora de llegada:</span>
          </label>
          <div className="grid grid-cols-5 gap-2">
            {TIME_SLOTS.map((slot) => (
              <button
                key={slot}
                type="button"
                onClick={() => setTime(slot)}
                className={`py-2 rounded-xl text-xs font-bold transition-all ${
                  time === slot
                    ? "bg-criollo-yellow text-criollo-black font-black shadow-md"
                    : "bg-zinc-800/80 border border-zinc-700 text-zinc-300 hover:bg-zinc-700"
                }`}
              >
                {slot}
              </button>
            ))}
          </div>
        </div>

        {/* Step 4: Zone */}
        <div>
          <label className="text-xs font-bold uppercase tracking-wider text-zinc-300 flex items-center gap-1.5 mb-2.5">
            <MapPin className="w-4 h-4 text-criollo-yellow" />
            <span>Preferencia de ubicación:</span>
          </label>
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-2.5">
            {ZONES.map((z) => (
              <button
                key={z.id}
                type="button"
                onClick={() => setZone(z.name)}
                className={`p-3 rounded-2xl border text-left transition-all text-xs ${
                  zone === z.name
                    ? "bg-criollo-red/20 border-criollo-red text-white"
                    : "bg-zinc-800/60 border-zinc-700 text-zinc-300 hover:bg-zinc-800"
                }`}
              >
                <div className="font-bold text-white mb-0.5">{z.name}</div>
                <div className="text-[11px] text-zinc-400">{z.desc}</div>
              </button>
            ))}
          </div>
        </div>

        {/* Step 5: Contact Info */}
        <div className="space-y-3 pt-3 border-t border-criollo-border/60">
          <h4 className="font-bold text-xs uppercase tracking-wider text-zinc-300">
            Tus datos de reserva:
          </h4>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
            <div>
              <label className="text-[11px] text-zinc-400 block mb-1">Nombre completo:</label>
              <input
                type="text"
                required
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder="Ej: Emiliano Di Rosa"
                className="w-full bg-zinc-800 border border-zinc-700 rounded-xl px-3 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-criollo-yellow"
              />
            </div>
            <div>
              <label className="text-[11px] text-zinc-400 block mb-1">Teléfono:</label>
              <input
                type="tel"
                required
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
                placeholder="+34 600 000 000"
                className="w-full bg-zinc-800 border border-zinc-700 rounded-xl px-3 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-criollo-yellow"
              />
            </div>
          </div>
          <div>
            <label className="text-[11px] text-zinc-400 block mb-1">Email de confirmación:</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="tu@email.com"
              className="w-full bg-zinc-800 border border-zinc-700 rounded-xl px-3 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-criollo-yellow"
            />
          </div>
          <div>
            <label className="text-[11px] text-zinc-400 block mb-1">Petición especial (opcional):</label>
            <input
              type="text"
              value={notes}
              onChange={(e) => setNotes(e.target.value)}
              placeholder="Ej: Cumpleaños, trona para bebé, etc."
              className="w-full bg-zinc-800 border border-zinc-700 rounded-xl px-3 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-criollo-yellow"
            />
          </div>
        </div>

        {/* Submit */}
        <button
          type="submit"
          className="w-full bg-gradient-to-r from-criollo-red to-criollo-redDark hover:opacity-95 text-white font-black py-3.5 px-4 rounded-xl shadow-lg shadow-criollo-red/25 flex items-center justify-center gap-2 text-sm transition-all active:scale-[0.98]"
        >
          <span>Confirmar Reserva de Mesa</span>
          <Sparkles className="w-4 h-4 text-criollo-yellow" />
        </button>
      </form>

      {/* Alternative Last.app Link */}
      <div className="bg-zinc-900 border border-zinc-800 rounded-2xl p-4 text-center">
        <p className="text-xs text-zinc-400 mb-2">
          ¿Deseas gestionar tu reserva a través del motor de Last.app Reservas?
        </p>
        <a
          href={IntegrationsConfig.LAST_APP_RESERVATIONS_URL}
          target="_blank"
          rel="noopener noreferrer"
          className="inline-flex items-center gap-1.5 text-xs font-bold text-zinc-200 hover:text-white bg-zinc-800 hover:bg-zinc-700 px-3.5 py-2 rounded-xl transition-colors border border-zinc-700"
        >
          <span>Abrir Portal de Reservas Last.app</span>
          <ExternalLink className="w-3.5 h-3.5 text-zinc-400" />
        </a>
      </div>
    </div>
  );
}
