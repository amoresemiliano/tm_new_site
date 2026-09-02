export type DishCategory =
  | "TACOS"
  | "ESPECIALES"
  | "ENTRANTES"
  | "POSTRES"
  | "BEBIDAS"
  | "MICHELADAS";

export interface MenuItem {
  id: string;
  name: string;
  description: string;
  price: number;
  category: DishCategory;
  imageUrl: string;
  tags: string[];
  spicyLevel: number; // 0 to 3
  allergens: string[];
  isPopular?: boolean;
  isNew?: boolean;
  isSignature?: boolean;
}

export interface CartItem {
  menuItem: MenuItem;
  quantity: number;
  selectedSauces: string[];
  specialInstructions?: string;
}

export interface LocationInfo {
  id: string;
  name: string;
  address: string;
  neighborhood: string;
  city: string;
  metro: string;
  schedule: string;
  weekendSchedule?: string;
  phone: string;
  whatsapp: string;
  mapsUrl: string;
  googleReviewsUrl: string;
  imageUrl: string;
  rating: number;
  totalReviews: number;
}

export interface Promotion {
  id: string;
  title: string;
  subtitle: string;
  description: string;
  badge: string;
  code?: string;
  ctaText: string;
  actionUrl?: string;
  isGoogleReviewPromo?: boolean;
}

export interface NewsItem {
  id: string;
  title: string;
  body: string;
  date: string;
  tag: string;
  isPromo?: boolean;
}

export type MemberTier = "TAQUERO_NOVATO" | "TAQUERO_AFICIONADO" | "TAQUERO_MAESTRO" | "LEYENDA_DEL_TACO";

export interface ClubMember {
  id: string;
  name: string;
  phone: string;
  email: string;
  points: number;
  tier: MemberTier;
  tierTitle: string;
  tierColor: string;
  memberSince: string;
  qrCode: string;
  tacosEaten: number;
  nextTierPoints: number;
}

export interface Reward {
  id: string;
  title: string;
  description: string;
  pointsRequired: number;
  category: string;
  iconName: string;
  isAvailable: boolean;
}

export interface ReservationRequest {
  guests: number;
  date: string;
  time: string;
  customerName: string;
  customerPhone: string;
  customerEmail?: string;
  specialRequests?: string;
  zone: string;
}

export interface GoogleReview {
  id: string;
  author: string;
  rating: number;
  comment: string;
  relativeTime: string;
  avatarUrl?: string;
  highlightDish?: string;
}
