"use client";

import React, { createContext, useContext, useState, useEffect } from "react";
import { MenuItem, CartItem, ClubMember, ReservationRequest } from "@/types";
import { MOCK_CLUB_MEMBER } from "@/data/mockData";

export interface AppContextType {
  cart: CartItem[];
  addToCart: (item: MenuItem, quantity?: number, sauces?: string[], instructions?: string) => void;
  removeFromCart: (itemId: string) => void;
  updateQuantity: (itemId: string, quantity: number) => void;
  clearCart: () => void;
  cartTotal: number;
  cartCount: number;
  
  // Dish modal
  selectedDish: MenuItem | null;
  setSelectedDish: (dish: MenuItem | null) => void;
  
  // Table dine-in
  currentTable: number | null;
  setCurrentTable: (table: number | null) => void;
  callWaiterRequested: boolean;
  setCallWaiterRequested: (called: boolean) => void;
  billRequested: boolean;
  setBillRequested: (requested: boolean) => void;
  
  // Club Taquero
  member: ClubMember;
  redeemReward: (rewardId: string, points: number) => boolean;
  claimGoogleReviewPromo: () => void;
  hasClaimedReviewPromo: boolean;
  
  // Reservations
  lastReservation: ReservationRequest | null;
  setLastReservation: (reservation: ReservationRequest | null) => void;
  
  // Global Toast / Feedback
  toastMessage: string | null;
  showToast: (msg: string) => void;
}

const defaultContext: AppContextType = {
  cart: [],
  addToCart: () => {},
  removeFromCart: () => {},
  updateQuantity: () => {},
  clearCart: () => {},
  cartTotal: 0,
  cartCount: 0,
  selectedDish: null,
  setSelectedDish: () => {},
  currentTable: null,
  setCurrentTable: () => {},
  callWaiterRequested: false,
  setCallWaiterRequested: () => {},
  billRequested: false,
  setBillRequested: () => {},
  member: MOCK_CLUB_MEMBER,
  redeemReward: () => false,
  claimGoogleReviewPromo: () => {},
  hasClaimedReviewPromo: false,
  lastReservation: null,
  setLastReservation: () => {},
  toastMessage: null,
  showToast: () => {},
};

const AppContext = createContext<AppContextType>(defaultContext);

export function AppProvider({ children }: { children: React.ReactNode }) {
  const [cart, setCart] = useState<CartItem[]>([]);
  const [selectedDish, setSelectedDish] = useState<MenuItem | null>(null);
  const [currentTable, setCurrentTable] = useState<number | null>(null);
  const [callWaiterRequested, setCallWaiterRequested] = useState(false);
  const [billRequested, setBillRequested] = useState(false);
  const [member, setMember] = useState<ClubMember>(MOCK_CLUB_MEMBER);
  const [hasClaimedReviewPromo, setHasClaimedReviewPromo] = useState(false);
  const [lastReservation, setLastReservation] = useState<ReservationRequest | null>(null);
  const [toastMessage, setToastMessage] = useState<string | null>(null);

  // Load from LocalStorage if browser
  useEffect(() => {
    try {
      const savedCart = localStorage.getItem("criollo_cart");
      if (savedCart) setCart(JSON.parse(savedCart));
      
      const savedTable = localStorage.getItem("criollo_table");
      if (savedTable) setCurrentTable(Number(savedTable));
      
      const savedPoints = localStorage.getItem("criollo_points");
      if (savedPoints) {
        setMember(prev => ({ ...prev, points: Number(savedPoints) }));
      }
    } catch (e) {
      console.error("Failed to load local state", e);
    }
  }, []);

  // Sync to local storage
  useEffect(() => {
    try {
      localStorage.setItem("criollo_cart", JSON.stringify(cart));
    } catch (e) {
      // ignore
    }
  }, [cart]);

  const showToast = (msg: string) => {
    setToastMessage(msg);
    setTimeout(() => {
      setToastMessage(null);
    }, 3500);
  };

  const addToCart = (item: MenuItem, quantity = 1, sauces: string[] = ["Verde Tomatillo"], instructions = "") => {
    setCart(prev => {
      const existingIndex = prev.findIndex(ci => ci.menuItem.id === item.id);
      if (existingIndex > -1) {
        const updated = [...prev];
        updated[existingIndex].quantity += quantity;
        if (sauces.length > 0) updated[existingIndex].selectedSauces = sauces;
        return updated;
      }
      return [...prev, { menuItem: item, quantity, selectedSauces: sauces, specialInstructions: instructions }];
    });
    showToast(`🌮 ¡${quantity}x ${item.name} añadido a tu pedido!`);
  };

  const removeFromCart = (itemId: string) => {
    setCart(prev => prev.filter(item => item.menuItem.id !== itemId));
    showToast("Artículo eliminado de tu pedido");
  };

  const updateQuantity = (itemId: string, quantity: number) => {
    if (quantity <= 0) {
      removeFromCart(itemId);
      return;
    }
    setCart(prev =>
      prev.map(item =>
        item.menuItem.id === itemId ? { ...item, quantity } : item
      )
    );
  };

  const clearCart = () => {
    setCart([]);
  };

  const cartTotal = cart.reduce((acc, item) => acc + item.menuItem.price * item.quantity, 0);
  const cartCount = cart.reduce((acc, item) => acc + item.quantity, 0);

  const redeemReward = (rewardId: string, points: number): boolean => {
    if (member.points >= points) {
      const newPoints = member.points - points;
      setMember(prev => ({ ...prev, points: newPoints }));
      try {
        localStorage.setItem("criollo_points", String(newPoints));
      } catch (e) {}
      showToast("🎉 ¡Recompensa canjeada con éxito! Muestra tu código al taquero.");
      return true;
    } else {
      showToast("❌ No tienes suficientes puntos aún. ¡Pide más tacos para acumular!");
      return false;
    }
  };

  const claimGoogleReviewPromo = () => {
    setHasClaimedReviewPromo(true);
    setMember(prev => ({ ...prev, points: prev.points + 25 }));
    showToast("⭐ ¡Gracias por tu reseña! 1 Taco Gratis asignado a tu cuenta.");
  };

  return (
    <AppContext.Provider
      value={{
        cart,
        addToCart,
        removeFromCart,
        updateQuantity,
        clearCart,
        cartTotal,
        cartCount,
        selectedDish,
        setSelectedDish,
        currentTable,
        setCurrentTable: (tbl) => {
          setCurrentTable(tbl);
          if (tbl) {
            try { localStorage.setItem("criollo_table", String(tbl)); } catch (e) {}
          } else {
            try { localStorage.removeItem("criollo_table"); } catch (e) {}
          }
        },
        callWaiterRequested,
        setCallWaiterRequested,
        billRequested,
        setBillRequested,
        member,
        redeemReward,
        claimGoogleReviewPromo,
        hasClaimedReviewPromo,
        lastReservation,
        setLastReservation,
        toastMessage,
        showToast,
      }}
    >
      {children}
    </AppContext.Provider>
  );
}

export function useApp() {
  return useContext(AppContext);
}
