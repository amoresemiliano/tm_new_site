import type { Metadata, Viewport } from "next";
import "./globals.css";
import Providers from "@/components/Providers";

export const metadata: Metadata = {
  title: "El Criollo · Taquería Tradicional Mexicana en Madrid",
  description: "Auténtica taquería mexicana en el Mercado Maravillas (Tetuán, Madrid). Tacos al Pastor en trompo, Quesabirrias con consomé, carnitas y micheladas. Pide online, reserva o únete al Club Taquero.",
  keywords: ["Tacos Madrid", "Taquería Mercado Maravillas", "Tetuán tacos", "Quesabirria Madrid", "Tacos al Pastor", "El Criollo Madrid"],
  authors: [{ name: "El Criollo Taquería" }],
  openGraph: {
    title: "El Criollo · Taquería Mexicana en Mercado Maravillas",
    description: "Tacos al Pastor, Quesabirrias con consomé, micheladas y auténtico sabor chilango en Tetuán, Madrid.",
    url: "https://elcriollo-madrid.es",
    siteName: "El Criollo Taquería",
    images: [
      {
        url: "https://images.unsplash.com/photo-1551504734-5ee1c4a1479b?w=1200&auto=format&fit=crop&q=80",
        width: 1200,
        height: 630,
        alt: "Tacos El Criollo Madrid",
      },
    ],
    locale: "es_ES",
    type: "website",
  },
  icons: {
    icon: "/favicon.ico",
  },
};

export const viewport: Viewport = {
  themeColor: "#D82B1B",
  width: "device-width",
  initialScale: 1,
  maximumScale: 1,
  userScalable: false,
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="es" className="dark">
      <body className="bg-criollo-darkBg text-white min-h-screen flex flex-col antialiased selection:bg-criollo-red selection:text-white pb-20">
        <Providers>{children}</Providers>
      </body>
    </html>
  );
}
