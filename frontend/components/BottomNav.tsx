"use client";

import React from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { useApp } from "@/context/AppContext";
import { Home, Utensils, ShoppingBag, Award, MapPin } from "lucide-react";

export default function BottomNav() {
  const pathname = usePathname();
  const { cartCount } = useApp();

  const navItems = [
    { label: "Inicio", href: "/", icon: Home },
    { label: "Carta", href: "/menu", icon: Utensils },
    { label: "Pedir", href: "/pedir", icon: ShoppingBag, badge: cartCount },
    { label: "Club", href: "/club", icon: Award },
    { label: "Local", href: "/local", icon: MapPin },
  ];

  return (
    <nav 
      aria-label="Navegación principal"
      className="fixed bottom-0 left-0 right-0 z-50 bg-criollo-black/95 backdrop-blur-lg border-t border-criollo-border text-zinc-400 py-1.5 px-3 shadow-2xl safe-area-bottom"
    >
      <div className="max-w-md mx-auto flex items-center justify-around">
        {navItems.map((item) => {
          const isActive = pathname === item.href;
          const Icon = item.icon;

          return (
            <Link
              key={item.href}
              href={item.href}
              className={`flex flex-col items-center justify-center py-1 px-2.5 rounded-xl transition-all relative ${
                isActive
                  ? "text-criollo-yellow font-bold scale-105"
                  : "text-zinc-400 hover:text-zinc-200"
              }`}
            >
              <div className="relative">
                <Icon className={`w-5 h-5 ${isActive ? "text-criollo-yellow stroke-[2.5]" : ""}`} />
                {Boolean(item.badge && item.badge > 0) && (
                  <span className="absolute -top-1.5 -right-2.5 bg-criollo-red text-white text-[10px] font-extrabold w-4 h-4 rounded-full flex items-center justify-center border-2 border-criollo-black">
                    {item.badge}
                  </span>
                )}
              </div>
              <span className="text-[11px] mt-0.5 tracking-tight">{item.label}</span>
              {isActive && (
                <div className="w-1.5 h-1.5 bg-criollo-yellow rounded-full mt-0.5" />
              )}
            </Link>
          );
        })}
      </div>
    </nav>
  );
}
