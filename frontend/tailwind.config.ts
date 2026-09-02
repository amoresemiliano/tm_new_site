import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        criollo: {
          red: "#D82B1B",
          redDark: "#8A1208",
          yellow: "#F4B41A",
          green: "#2E7D32",
          black: "#121212",
          card: "#1E1E1E",
          surface: "#262626",
          border: "#383838",
          cream: "#FFF9F2",
          darkBg: "#111111",
        },
      },
      fontFamily: {
        sans: ["var(--font-inter)", "sans-serif"],
      },
    },
  },
  plugins: [],
};
export default config;
