package com.example.data.mock

import com.example.data.model.*

object MockDataFixtures {

  val primaryLocation = Location(
    id = "loc_mercadomaravillas",
    slug = "mercado-maravillas",
    name = "El Criollo - Mercado Maravillas",
    neighborhood = "Tetuán / Cuatro Caminos",
    address = "Calle de Bravo Murillo, 122 (Puesto Mercado Maravillas)",
    city = "Madrid",
    postalCode = "28020",
    metro = "Metro Alvarado (L2) / Cuatro Caminos (L1, L2, L6)",
    phone = "+34 607 74 03 58",
    schedule = "Mar - Dom: 13:00 - 16:30 | 20:00 - 23:30 (Lunes cerrado)",
    googleRating = 4.9,
    googleReviewCount = 808,
    mapsUrl = "https://maps.google.com/?q=El+Criollo+Taqueria+Mercado+Maravillas+Madrid",
    orderUrl = "https://order.last.app/el-criollo",
    reservationUrl = "https://book.last.app/el-criollo",
    qrUrl = "https://qr.last.app/el-criollo",
    isPrimary = true,
    active = true
  )

  val futureSecondLocation = Location(
    id = "loc_malasana_2027",
    slug = "malasana-madrid",
    name = "El Criollo - Malasaña (Próxima apertura)",
    neighborhood = "Malasaña / Centro",
    address = "Calle Pez (Próximamente 2027)",
    city = "Madrid",
    postalCode = "28004",
    metro = "Metro Noviciado / Tribunal",
    phone = "+34 607 74 03 58",
    schedule = "Apertura estimada 2027",
    googleRating = 5.0,
    googleReviewCount = 0,
    mapsUrl = "",
    orderUrl = "",
    reservationUrl = "",
    qrUrl = "",
    isPrimary = false,
    active = false
  )

  val locations = listOf(primaryLocation, futureSecondLocation)

  val categories = listOf(
    Category(id = "combos", name = "Combos", iconName = "local_offer", subtitle = "Ahorra y prueba de todo"),
    Category(id = "tacos", name = "Tacos", iconName = "restaurant", subtitle = "Con tortilla de maíz artesanal"),
    Category(id = "quesadillas", name = "Quesadillas", iconName = "lunch_dining", subtitle = "Queso fundido al comal"),
    Category(id = "costras", name = "Costras", iconName = "local_fire_department", subtitle = "Queso dorado crujiente"),
    Category(id = "antojitos", name = "Antojitos", iconName = "emoji_food_beverage", subtitle = "Guacamole y totopos"),
    Category(id = "aguas", name = "Aguas & Boing", iconName = "water_drop", subtitle = "Sabor 100% frutal"),
    Category(id = "cervezas", name = "Cervezas & Chelas", iconName = "sports_bar", subtitle = "Micheladas y botellines"),
    Category(id = "dulces", name = "Dulces Mex", iconName = "cake", subtitle = "Chamoy, tamarindo y picapica")
  )

  val menuItems = listOf(
    // Combos
    MenuItem(
      id = "combo_degustacion",
      name = "Combo Taquero Cuatro Estaciones",
      description = "4 tacos variados a elegir: 1 Pastor, 1 Carnitas, 1 Birria y 1 Champiñón con salsas caseras y totopos.",
      price = 14.50,
      categoryId = "combos",
      tags = listOf("Más Vendido", "Ideal 1-2 pers"),
      isFavorite = true,
      portions = "4 tacos + totopos"
    ),
    MenuItem(
      id = "combo_pareja",
      name = "Combo Criollo Pareja",
      description = "6 tacos artesanos a elección + 1 Guacamole casero con totopos + 2 Aguas frescas o cañas de grifo.",
      price = 24.90,
      categoryId = "combos",
      tags = listOf("Ahorro", "Para 2 personas"),
      portions = "6 tacos + guac + 2 bebidas"
    ),

    // Tacos
    MenuItem(
      id = "taco_carnitas",
      name = "Tacos de Carnitas Michoacanas",
      description = "Cerdo confitado lentamente en su propio jugo con especias tradicionales, cilantro fresco, cebolla picada y gajos de lima.",
      price = 6.90,
      categoryId = "tacos",
      tags = listOf("Clásico", "Favorito"),
      spicyLevel = 1,
      isFavorite = true,
      portions = "Orden de 2 tacos"
    ),
    MenuItem(
      id = "taco_pastor",
      name = "Tacos al Pastor con Piña Asada",
      description = "Carne marinada en adobo de achiote, chiles secos y especias, servida con piña asada al punto, cilantro y cebolla.",
      price = 6.90,
      categoryId = "tacos",
      tags = listOf("Favorito del Chef"),
      spicyLevel = 1,
      isFavorite = true,
      portions = "Orden de 2 tacos"
    ),
    MenuItem(
      id = "taco_birria",
      name = "Tacos de Birria de Res con Consomé",
      description = "Ternera estofada durante 8 horas con chiles guajillo y ancho. Incluye vaso de consomé hirviendo para sopear.",
      price = 7.80,
      categoryId = "tacos",
      tags = listOf("Estrella de la Casa", "Con Consomé"),
      spicyLevel = 2,
      isFavorite = true,
      portions = "Orden de 2 tacos + consomé"
    ),
    MenuItem(
      id = "taco_tinga",
      name = "Tacos de Tinga de Pollo",
      description = "Pechuga deshebrada en salsa de tomate asado y chile chipotle ahumado con cebolla pochada y crema agria.",
      price = 6.50,
      categoryId = "tacos",
      tags = listOf("Suave"),
      spicyLevel = 1,
      portions = "Orden de 2 tacos"
    ),
    MenuItem(
      id = "taco_champinon",
      name = "Tacos de Champiñón al Ajillo (Veggie)",
      description = "Champiñones salteados con epazote, ajo tierno, cebolla caramelizada y un toque de chile guajillo en tiras.",
      price = 6.00,
      categoryId = "tacos",
      tags = listOf("Vegetariano", "Sin Gluten"),
      spicyLevel = 1,
      portions = "Orden de 2 tacos"
    ),

    // Quesadillas
    MenuItem(
      id = "quesadilla_pastor_pina",
      name = "Quesadilla Pastor & Piña",
      description = "Tortilla de comal dorada rellena de queso fundido, carne al pastor y tropezones de piña asada caramelizada.",
      price = 7.50,
      categoryId = "quesadillas",
      tags = listOf("Popular"),
      spicyLevel = 1,
      portions = "1 quesadilla grande doble"
    ),
    MenuItem(
      id = "quesadilla_champinon",
      name = "Quesadilla con Champiñones",
      description = "Tortilla crujiente al comal con abundante queso fundido y salteado aromático de hongos al ajillo.",
      price = 6.80,
      categoryId = "quesadillas",
      tags = listOf("Vegetariano"),
      spicyLevel = 0,
      portions = "1 quesadilla grande doble"
    ),

    // Costras
    MenuItem(
      id = "costra_asada",
      name = "Costra Asada Criolla",
      description = "Manta de queso fundido y dorado a la plancha hasta quedar crujiente, rellena de ternera asada o carnitas.",
      price = 8.50,
      categoryId = "costras",
      tags = listOf("Crujiente", "Sin Harinas"),
      spicyLevel = 1,
      isFavorite = true,
      portions = "1 costra artesana"
    ),

    // Antojitos
    MenuItem(
      id = "guacamole_totopos",
      name = "Guacamole Criollo en Cazuela",
      description = "Aguacates maduros machacados al momento con lima, cilantro, tomate, cebolla morada y totopos de maíz recién fritos.",
      price = 7.90,
      categoryId = "antojitos",
      tags = listOf("Casero", "Para compartir"),
      spicyLevel = 0,
      isFavorite = true,
      portions = "Cazuela de barro 250g"
    ),

    // Aguas y Boing
    MenuItem(
      id = "agua_horchata",
      name = "Agua Fresca de Horchata Casera",
      description = "Elaborada con arroz, canela mexicana, leche condensada y vainilla. Muy fría y refrescante.",
      price = 3.50,
      categoryId = "aguas",
      tags = listOf("Artesanal"),
      portions = "Vaso 400ml"
    ),
    MenuItem(
      id = "agua_jamaica",
      name = "Agua Fresca de Flor de Jamaica",
      description = "Infusión natural de flores de hibisco con toque cítrico. Digestiva y revitalizante.",
      price = 3.50,
      categoryId = "aguas",
      tags = listOf("Natural 100%"),
      portions = "Vaso 400ml"
    ),
    MenuItem(
      id = "boing_lata",
      name = "Jugo Boing! Mexicano en Lata",
      description = "Bebida clásica mexicana con pulpa de fruta natural. Elige entre Guayaba, Mango o Fresa.",
      price = 3.20,
      categoryId = "aguas",
      tags = listOf("Importado de México"),
      portions = "Lata 340ml"
    ),

    // Cervezas
    MenuItem(
      id = "michelada_criollo",
      name = "Michelada El Criollo Preparada",
      description = "Cerveza servida en jarra helada con escarchado de sal marina, Tajín, salsas negras, zumo de lima y chamoy.",
      price = 5.20,
      categoryId = "cervezas",
      tags = listOf("Favorito de Barra"),
      spicyLevel = 1,
      portions = "Jarra 500ml"
    ),
    MenuItem(
      id = "cerveza_grifo",
      name = "Caña de Grifo Amstel / Águila",
      description = "Tirada perfecta bien fría, rubia de pura malta.",
      price = 2.50,
      categoryId = "cervezas",
      portions = "Caña 300ml"
    ),
    MenuItem(
      id = "cerveza_mex_botella",
      name = "Cerveza Mexicana en Botellín (Corona / Sol / XX)",
      description = "Cervezas importadas servidas con rodaja de lima natural.",
      price = 3.80,
      categoryId = "cervezas",
      portions = "Tercio 330ml"
    ),

    // Dulces
    MenuItem(
      id = "dulce_lucas_muecas",
      name = "Lucas Muecas Sabor Chamoy",
      description = "Paleta de caramelo con polvito ácido y picosito de chamoy mexicano.",
      price = 1.80,
      categoryId = "dulces",
      tags = listOf("Street Candy"),
      portions = "1 unidad"
    ),
    MenuItem(
      id = "dulce_chipileta",
      name = "Chipileta Naranja & Chile",
      description = "Piruleta sabor naranja con polvo dulce picante mexicano.",
      price = 1.20,
      categoryId = "dulces",
      portions = "1 unidad"
    ),
    MenuItem(
      id = "dulce_rellerindos",
      name = "Vero Rellerindos de Tamarindo",
      description = "Caramelos duros de tamarindo rellenos de chile suave.",
      price = 1.50,
      categoryId = "dulces",
      portions = "Bolsita 3 pzas"
    )
  )

  val sampleReviews = listOf(
    Review(
      id = "rev_1",
      authorName = "Carlos Mendoza",
      rating = 5,
      text = "¡Los mejores tacos de Madrid sin duda! La birria con consomé es de otro planeta y el trato en el Mercado Maravillas es súper cercano. Volveremos cada semana.",
      relativeTime = "Hace 2 días",
      avatarInitial = "C"
    ),
    Review(
      id = "rev_2",
      authorName = "Lucía Gómez",
      rating = 5,
      text = "Sabor auténtico mexicano. Nada de tex-mex comercial. Las carnitas están tiernísimas y la costra asada con queso dorado es brutal. Un 10.",
      relativeTime = "Hace 5 días",
      avatarInitial = "L"
    ),
    Review(
      id = "rev_3",
      authorName = "Guillermo R.",
      rating = 5,
      text = "La michelada está preparada como en CDMX. El personal súper amable y el ambiente del local con los rótulos y música te mete de lleno en la taquería.",
      relativeTime = "Hace 1 semana",
      avatarInitial = "G"
    ),
    Review(
      id = "rev_4",
      authorName = "Sara P.",
      rating = 5,
      text = "Descubrimiento total en Tetuán. Pedimos el combo degustación y el guacamole casero, todo riquísimo y a un precio inmejorable.",
      relativeTime = "Hace 2 semanas",
      avatarInitial = "S"
    )
  )

  val mockMember = ClubMember(
    id = "mem_diego_01",
    name = "Diego Méndez",
    email = "diego.mendez@example.com",
    phone = "+34 612 34 56 78",
    points = 1280,
    tier = MemberTier.TAQUERO_LEYENDA,
    nextTierPoints = 1500,
    nextRewardThreshold = 1500,
    memberSince = "Octubre 2024",
    visitsCount = 9,
    freeTacosRedeemed = 4
  )

  val clubRewards = listOf(
    Reward(
      id = "rew_taco_gratis",
      title = "1 Taco a Elegir Gratis",
      description = "Canjea por cualquier taco de la carta (Pastor, Carnitas, Tinga o Champiñón).",
      pointsCost = 350,
      icon = "restaurant",
      category = "Comida",
      isAvailable = true,
      code = "TACOFREE-882"
    ),
    Reward(
      id = "rew_bebida_boing",
      title = "Jugo Boing! o Caña de Grifo",
      description = "Disfruta de una bebida refrescante sin coste.",
      pointsCost = 200,
      icon = "sports_bar",
      category = "Bebidas",
      isAvailable = true,
      code = "DRINKFREE-412"
    ),
    Reward(
      id = "rew_quesadilla",
      title = "Quesadilla Especial Gratis",
      description = "Quesadilla con queso fundido y carne a elección con salsa.",
      pointsCost = 600,
      icon = "lunch_dining",
      category = "Comida",
      isAvailable = true,
      code = "QUESAFREE-991"
    ),
    Reward(
      id = "rew_costra",
      title = "Costra Asada Dorada",
      description = "Nuestra famosa costra crujiente rellena de carne asada.",
      pointsCost = 850,
      icon = "local_fire_department",
      category = "Comida",
      isAvailable = true,
      code = "COSTRAFREE-312"
    ),
    Reward(
      id = "rew_pack_dulces",
      title = "Pack Dulces Mexicanos",
      description = "Surtido con Lucas Muecas, Chipileta y Rellerindos.",
      pointsCost = 150,
      icon = "cake",
      category = "Dulces",
      isAvailable = true,
      code = "DULCEFREE-773"
    )
  )

  val gamificationMissions = listOf(
    GamificationMission(
      id = "mis_review",
      title = "¡Deja tu reseña en Google!",
      description = "Ayuda a que más taqueros nos conozcan y gana puntos directos + 1 taquito gratis en local.",
      pointsReward = 80,
      icon = "star",
      isCompleted = false,
      actionType = "REVIEW"
    ),
    GamificationMission(
      id = "mis_friday",
      title = "Viernes Taquero",
      description = "Ven a comer o pide a domicilio en viernes y suma doble puntaje.",
      pointsReward = 120,
      icon = "event",
      isCompleted = true,
      actionType = "VISIT"
    ),
    GamificationMission(
      id = "mis_birria",
      title = "Prueba la Birria con Consomé",
      description = "Sopea tus tacos en consomé hirviendo y desbloquea la medalla.",
      pointsReward = 50,
      icon = "soup_kitchen",
      isCompleted = true,
      actionType = "TRY_TACO"
    ),
    GamificationMission(
      id = "mis_invite",
      title = "Invita a un Amigo al Club",
      description = "Comparte tu código y ambos recibirán 100 puntos en su primer pedido.",
      pointsReward = 100,
      icon = "group_add",
      isCompleted = false,
      actionType = "INVITE"
    )
  )

  val newsItems = listOf(
    NewsItem(
      id = "news_taquito_gratis",
      slug = "campana-taquito-gratis-google",
      title = "¡1 Taquito Gratis por tu Reseña en Google!",
      excerpt = "Muéstranos tu reseña en Google Maps al pedir en el local y llévate un taco de regalo al instante.",
      body = "En El Criollo queremos agradecer el tremendo cariño que nos dais todos los días en el Mercado Maravillas. Si ya has venido a probar nuestros tacos, déjanos tu opinión sincera en Google Maps. Muestra la pantalla a nuestro equipo en barra y te serviremos un taquito gratis de carnitas o pastor al momento. ¡Promoción válida en local por tiempo limitado!",
      date = "1 Septiembre 2026",
      tag = "PROMO ESTRELLA",
      isPromo = true,
      promoCode = "RESEÑA-GRATIS"
    ),
    NewsItem(
      id = "news_aguas_frescas",
      slug = "nuevas-aguas-frescas-artesanales",
      title = "Llegan las Aguas Frescas en Jarra de Cristal",
      excerpt = "Horchata de arroz con canela mexicana y Flor de Jamaica recién infusionada.",
      body = "Para acompañar tus tacos como manda la tradición, hemos incorporado jarras de agua fresca artesanal: Horchata bien fría con toque de canela y vainilla, y Jamaica con flor de hibisco 100% natural. ¡Pídelas individuales o en formato jarra para compartir en tu mesa!",
      date = "28 Agosto 2026",
      tag = "NOVEDAD",
      isPromo = false
    ),
    NewsItem(
      id = "news_taquizas_eventos",
      slug = "servicio-taquizas-a-domicilio",
      title = "¿Fiesta en casa? Llevamos la Taquería a tu Evento",
      excerpt = "Taquizas completas para cumpleaños, empresas y celebraciones en todo Madrid.",
      body = "Montamos la taquería donde tú quieras. Llevamos comal, carnes al pastor, carnitas, tinga, salsas caseras, totopos y bebidas. Contáctanos por WhatsApp al +34 607 74 03 58 para pedir tu presupuesto a medida sin compromiso.",
      date = "20 Agosto 2026",
      tag = "EVENTOS",
      isPromo = false
    )
  )
}
