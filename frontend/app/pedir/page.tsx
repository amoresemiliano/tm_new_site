"use client";

import React, { useState } from "react";
import Link from "next/link";
import Image from "next/image";
import { useApp } from "@/context/AppContext";
import { IntegrationsConfig } from "@/config/integrations";
import { 
  ShoppingBag, 
  Trash2, 
  Plus, 
  Minus, 
  MapPin, 
  Clock, 
  CreditCard, 
  ExternalLink, 
  CheckCircle2, 
  Heart, 
  ArrowRight,
  Utensils
} from "lucide-react";

type OrderMode = "PICKUP" | "DELIVERY" | "DINE_IN";

export default function PedirPage() {
  const { cart, updateQuantity, removeFromCart, clearCart, cartTotal, currentTable } = useApp();
  const [orderMode, setOrderMode] = useState<OrderMode>(currentTable ? "DINE_IN" : "PICKUP");
  const [tip, setTip] = useState<number>(1);
  const [customerName, setCustomerName] = useState("");
  const [customerPhone, setCustomerPhone] = useState("");
  const [deliveryAddress, setDeliveryAddress] = useState("");
  const [orderNotes, setOrderNotes] = useState("");
  const [orderConfirmed, setOrderConfirmed] = useState(false);
  const [confirmedOrderId, setConfirmedOrderId] = useState("");

  const deliveryFee = orderMode === "DELIVERY" ? (cartTotal >= 25 ? 0 : 2.50) : 0;
  const grandTotal = (cartTotal + deliveryFee + tip).toFixed(2);

  const handlePlaceOrder = (e: React.FormEvent) => {
    e.preventDefault();
    const newId = `CRIOLLO-${Math.floor(100000 + Math.random() * 900000)}`;
    setConfirmedOrderId(newId);
    setOrderConfirmed(true);
    clearCart();
  };

  if (orderConfirmed) {
    return (
      <div className="bg-criollo-card border border-criollo-border rounded-3xl p-6 sm:p-8 text-center space-y-5 my-4 shadow-2xl animate-fadeIn">
        <div className="w-16 h-16 rounded-full bg-emerald-900/40 border-2 border-emerald-500 text-emerald-400 flex items-center justify-center mx-auto">
          <CheckCircle2 className="w-9 h-9" />
        </div>

        <div>
          <span className="bg-emerald-950 border border-emerald-500/40 text-emerald-300 text-xs font-black uppercase px-3 py-1 rounded-full">
            ¡Pedido Recibido en Cocina!
          </span>
          <h2 className="text-2xl font-black text-white mt-3">
            Referencia: #{confirmedOrderId}
          </h2>
          <p className="text-xs sm:text-sm text-zinc-300 mt-2 max-w-md mx-auto leading-relaxed">
            Nuestros taqueros ya están preparando tus platillos al momento con todo el sazón de México.
          </p>
        </div>

        <div className="bg-zinc-900 border border-zinc-800 rounded-2xl p-4 text-left text-xs space-y-2 max-w-md mx-auto">
          <div className="flex justify-between text-zinc-400">
            <span>Modo:</span>
            <strong className="text-white">
              {orderMode === "PICKUP" && "Recogida en Puesto Mercado"}
              {orderMode === "DELIVERY" && "Entrega a Domicilio"}
              {orderMode === "DINE_IN" && `Servir en Mesa #${currentTable || "1"}`}
            </strong>
          </div>
          <div className="flex justify-between text-zinc-400">
            <span>Tiempo estimado:</span>
            <strong className="text-criollo-yellow">15 - 20 minutos</strong>
          </div>
          <div className="flex justify-between text-zinc-400">
            <span>Total abonado:</span>
            <strong className="text-white">{grandTotal} €</strong>
          </div>
        </div>

        <div className="pt-2 flex flex-col sm:flex-row gap-3 justify-center max-w-md mx-auto">
          <a
            href={IntegrationsConfig.WHATSAPP_URL}
            target="_blank"
            rel="noopener noreferrer"
            className="bg-emerald-600 hover:bg-emerald-700 text-white font-bold py-3 px-4 rounded-xl text-xs flex items-center justify-center gap-2 transition-colors"
          >
            <span>Consultar por WhatsApp</span>
            <ExternalLink className="w-3.5 h-3.5" />
          </a>

          <button
            onClick={() => setOrderConfirmed(false)}
            className="bg-zinc-800 hover:bg-zinc-700 text-white font-bold py-3 px-4 rounded-xl text-xs transition-colors"
          >
            Hacer Otro Pedido
          </button>
        </div>
      </div>
    );
  }

  if (cart.length === 0) {
    return (
      <div className="bg-criollo-card border border-criollo-border rounded-3xl p-8 sm:p-12 text-center space-y-4 my-6 shadow-2xl">
        <div className="w-16 h-16 rounded-2xl bg-zinc-800 flex items-center justify-center mx-auto text-zinc-400">
          <ShoppingBag className="w-8 h-8" />
        </div>
        <h2 className="text-xl font-black text-white">Tu carrito está vacío</h2>
        <p className="text-xs text-zinc-400 max-w-xs mx-auto leading-relaxed">
          Añade tus tacos favoritos, quesabirrias y micheladas para pedir a domicilio o recoger en el Mercado Maravillas.
        </p>
        <div className="pt-2">
          <Link
            href="/menu"
            className="bg-criollo-red hover:bg-criollo-redDark text-white font-bold py-3 px-6 rounded-xl text-xs inline-flex items-center gap-2 shadow-lg shadow-criollo-red/20 transition-all active:scale-95"
          >
            <Utensils className="w-4 h-4" />
            <span>Explorar Nuestra Carta</span>
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6 pb-8">
      {/* Header */}
      <div>
        <h1 className="text-2xl sm:text-3xl font-black text-white">Finalizar Pedido</h1>
        <p className="text-xs sm:text-sm text-zinc-400 mt-1">
          Revisa tus platillos, elige la forma de entrega y confirma tu comanda.
        </p>
      </div>

      {/* Mode Selector Tabs */}
      <div className="grid grid-cols-3 gap-2 bg-zinc-900/80 p-1 rounded-2xl border border-zinc-800">
        <button
          type="button"
          onClick={() => setOrderMode("PICKUP")}
          className={`py-2 px-3 rounded-xl text-xs font-bold transition-all flex items-center justify-center gap-1.5 ${
            orderMode === "PICKUP"
              ? "bg-criollo-red text-white shadow-md"
              : "text-zinc-400 hover:text-white"
          }`}
        >
          <Clock className="w-3.5 h-3.5" />
          <span>Recogida</span>
        </button>

        <button
          type="button"
          onClick={() => setOrderMode("DELIVERY")}
          className={`py-2 px-3 rounded-xl text-xs font-bold transition-all flex items-center justify-center gap-1.5 ${
            orderMode === "DELIVERY"
              ? "bg-criollo-red text-white shadow-md"
              : "text-zinc-400 hover:text-white"
          }`}
        >
          <MapPin className="w-3.5 h-3.5" />
          <span>A Domicilio</span>
        </button>

        <button
          type="button"
          onClick={() => setOrderMode("DINE_IN")}
          className={`py-2 px-3 rounded-xl text-xs font-bold transition-all flex items-center justify-center gap-1.5 ${
            orderMode === "DINE_IN"
              ? "bg-criollo-red text-white shadow-md"
              : "text-zinc-400 hover:text-white"
          }`}
        >
          <Utensils className="w-3.5 h-3.5" />
          <span>En Mesa {currentTable ? `#${currentTable}` : ""}</span>
        </button>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-12 gap-6">
        {/* Left: Cart items list */}
        <div className="lg:col-span-7 space-y-3">
          <div className="flex items-center justify-between pb-1">
            <h3 className="font-bold text-sm text-white flex items-center gap-1.5">
              <span>Tus Platillos</span>
              <span className="text-xs font-normal text-zinc-400">({cart.length})</span>
            </h3>
            <button
              onClick={clearCart}
              className="text-xs text-red-400 hover:text-red-300 flex items-center gap-1"
            >
              <Trash2 className="w-3.5 h-3.5" />
              <span>Vaciar</span>
            </button>
          </div>

          <div className="space-y-2.5">
            {cart.map((item) => (
              <div
                key={item.menuItem.id}
                className="bg-criollo-card border border-criollo-border rounded-2xl p-3.5 flex gap-3 items-center"
              >
                <div className="relative w-16 h-16 rounded-xl overflow-hidden bg-zinc-800 flex-shrink-0">
                  <Image
                    src={item.menuItem.imageUrl}
                    alt={item.menuItem.name}
                    fill
                    className="object-cover"
                  />
                </div>

                <div className="flex-1 min-w-0">
                  <h4 className="font-bold text-white text-xs sm:text-sm truncate">
                    {item.menuItem.name}
                  </h4>
                  <div className="text-[11px] text-zinc-400 truncate">
                    {item.selectedSauces.join(", ")}
                  </div>
                  <div className="font-extrabold text-xs text-criollo-yellow mt-0.5">
                    {(item.menuItem.price * item.quantity).toFixed(2)} €
                  </div>
                </div>

                {/* Quantity adjuster */}
                <div className="flex items-center bg-zinc-800 border border-zinc-700 rounded-xl p-0.5">
                  <button
                    onClick={() => updateQuantity(item.menuItem.id, item.quantity - 1)}
                    className="p-1 text-zinc-400 hover:text-white"
                    aria-label="Menos"
                  >
                    <Minus className="w-3.5 h-3.5" />
                  </button>
                  <span className="w-6 text-center text-xs font-black text-white">
                    {item.quantity}
                  </span>
                  <button
                    onClick={() => updateQuantity(item.menuItem.id, item.quantity + 1)}
                    className="p-1 text-zinc-400 hover:text-white"
                    aria-label="Más"
                  >
                    <Plus className="w-3.5 h-3.5" />
                  </button>
                </div>
              </div>
            ))}
          </div>

          <Link
            href="/menu"
            className="text-xs font-bold text-criollo-yellow hover:underline inline-flex items-center gap-1 pt-1"
          >
            <Plus className="w-3.5 h-3.5" />
            <span>Añadir más platillos a la comanda</span>
          </Link>
        </div>

        {/* Right: Checkout & Details form */}
        <div className="lg:col-span-5 space-y-4">
          <form onSubmit={handlePlaceOrder} className="bg-criollo-card border border-criollo-border rounded-3xl p-5 space-y-4 shadow-xl">
            <h3 className="font-extrabold text-white text-sm">Datos del Pedido</h3>

            <div className="space-y-3">
              <div>
                <label className="text-[11px] font-bold uppercase text-zinc-400 block mb-1">
                  Tu Nombre:
                </label>
                <input
                  type="text"
                  required
                  value={customerName}
                  onChange={(e) => setCustomerName(e.target.value)}
                  placeholder="Ej: Emiliano Di Rosa"
                  className="w-full bg-zinc-800 border border-zinc-700 rounded-xl px-3 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-criollo-yellow"
                />
              </div>

              <div>
                <label className="text-[11px] font-bold uppercase text-zinc-400 block mb-1">
                  Teléfono de Contacto:
                </label>
                <input
                  type="tel"
                  required
                  value={customerPhone}
                  onChange={(e) => setCustomerPhone(e.target.value)}
                  placeholder="+34 600 000 000"
                  className="w-full bg-zinc-800 border border-zinc-700 rounded-xl px-3 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-criollo-yellow"
                />
              </div>

              {orderMode === "DELIVERY" && (
                <div>
                  <label className="text-[11px] font-bold uppercase text-zinc-400 block mb-1">
                    Dirección de Entrega (Madrid):
                  </label>
                  <input
                    type="text"
                    required
                    value={deliveryAddress}
                    onChange={(e) => setDeliveryAddress(e.target.value)}
                    placeholder="Calle, Número, Piso, Código Postal"
                    className="w-full bg-zinc-800 border border-zinc-700 rounded-xl px-3 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-criollo-yellow"
                  />
                  {cartTotal < 25 && (
                    <p className="text-[10px] text-amber-400 mt-1">
                      Envío gratis a partir de 25€ (te faltan {(25 - cartTotal).toFixed(2)}€)
                    </p>
                  )}
                </div>
              )}

              {/* Tip selector */}
              <div>
                <label className="text-[11px] font-bold uppercase text-zinc-400 flex items-center gap-1 mb-1.5">
                  <Heart className="w-3 h-3 text-criollo-red fill-criollo-red" />
                  <span>Propina para el equipo taquero:</span>
                </label>
                <div className="grid grid-cols-4 gap-1.5">
                  {[0, 1, 2, 3].map((val) => (
                    <button
                      key={val}
                      type="button"
                      onClick={() => setTip(val)}
                      className={`py-1.5 rounded-xl border text-xs font-bold transition-all ${
                        tip === val
                          ? "bg-criollo-yellow text-criollo-black border-criollo-yellow font-black"
                          : "bg-zinc-800 border-zinc-700 text-zinc-300 hover:bg-zinc-700"
                      }`}
                    >
                      {val === 0 ? "0 €" : `${val} €`}
                    </button>
                  ))}
                </div>
              </div>
            </div>

            {/* Breakdown */}
            <div className="pt-3 border-t border-criollo-border/60 space-y-1.5 text-xs">
              <div className="flex justify-between text-zinc-400">
                <span>Subtotal platillos:</span>
                <span className="text-white font-medium">{cartTotal.toFixed(2)} €</span>
              </div>
              {orderMode === "DELIVERY" && (
                <div className="flex justify-between text-zinc-400">
                  <span>Gastos de entrega:</span>
                  <span className={deliveryFee === 0 ? "text-emerald-400 font-bold" : "text-white"}>
                    {deliveryFee === 0 ? "GRATIS" : `${deliveryFee.toFixed(2)} €`}
                  </span>
                </div>
              )}
              {tip > 0 && (
                <div className="flex justify-between text-zinc-400">
                  <span>Propina al taquero:</span>
                  <span className="text-white">{tip.toFixed(2)} €</span>
                </div>
              )}
              <div className="flex justify-between text-sm font-black text-white pt-2 border-t border-zinc-800">
                <span>Total a Pagar:</span>
                <span className="text-base text-criollo-yellow">{grandTotal} €</span>
              </div>
            </div>

            <button
              type="submit"
              className="w-full bg-gradient-to-r from-criollo-red to-criollo-redDark hover:opacity-95 text-white font-black py-3.5 px-4 rounded-xl shadow-lg shadow-criollo-red/25 flex items-center justify-center gap-2 text-sm transition-all active:scale-[0.98]"
            >
              <span>Confirmar y Enviar a Cocina</span>
              <ArrowRight className="w-4 h-4" />
            </button>
          </form>

          {/* Last.app direct redirect button */}
          <div className="bg-zinc-900 border border-zinc-800 rounded-2xl p-3.5 text-center">
            <p className="text-[11px] text-zinc-400 mb-2">
              ¿Prefieres pedir a través de nuestro TPV oficial de Last.app?
            </p>
            <a
              href={IntegrationsConfig.LAST_APP_SHOP_URL}
              target="_blank"
              rel="noopener noreferrer"
              className="inline-flex items-center gap-1.5 text-xs font-bold text-zinc-200 hover:text-white bg-zinc-800 hover:bg-zinc-700 px-3.5 py-2 rounded-xl transition-colors border border-zinc-700"
            >
              <span>Abrir Tienda Oficial Last.app</span>
              <ExternalLink className="w-3.5 h-3.5 text-zinc-400" />
            </a>
          </div>
        </div>
      </div>
    </div>
  );
}
