"use client";

import React from "react";
import { useApp } from "@/context/AppContext";

export default function ToastNotification() {
  const { toastMessage } = useApp();

  if (!toastMessage) return null;

  return (
    <div className="fixed top-14 left-1/2 -translate-x-1/2 z-50 pointer-events-none px-4 w-full max-w-sm">
      <div className="bg-criollo-black/95 text-white border border-criollo-yellow/40 rounded-2xl py-2.5 px-4 shadow-2xl backdrop-blur-md flex items-center justify-center text-center text-xs font-bold animate-slideDown">
        <span>{toastMessage}</span>
      </div>
    </div>
  );
}
