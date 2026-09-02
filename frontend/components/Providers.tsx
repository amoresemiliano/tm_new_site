"use client";

import React from "react";
import { AppProvider } from "@/context/AppContext";
import Header from "@/components/Header";
import BottomNav from "@/components/BottomNav";
import DishDetailModal from "@/components/DishDetailModal";
import ToastNotification from "@/components/ToastNotification";

export default function Providers({ children }: { children: React.ReactNode }) {
  return (
    <AppProvider>
      <Header />
      <ToastNotification />
      <main className="flex-1 max-w-4xl mx-auto w-full px-4 pt-4">
        {children}
      </main>
      <DishDetailModal />
      <BottomNav />
    </AppProvider>
  );
}
