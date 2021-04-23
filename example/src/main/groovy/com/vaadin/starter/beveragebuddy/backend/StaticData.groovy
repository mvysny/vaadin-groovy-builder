package com.vaadin.starter.beveragebuddy.backend

import groovy.transform.CompileStatic

/**
 * @author mavi
 */
@CompileStatic
class StaticData {

    private static final String MINERAL_WATER = "Mineral Water"
    private static final String SOFT_DRINK = "Soft Drink"
    private static final String COFFEE = "Coffee"
    private static final String TEA = "Tea"
    private static final String DAIRY = "Dairy"
    private static final String CIDER = "Cider"
    private static final String BEER = "Beer"
    private static final String WINE = "Wine"
    private static final String OTHER = "Other"

    /**
     * Maps beverage name to a beverage category.
     */
    static final Map<String, String> BEVERAGES = new LinkedHashMap<>()

    static {
        ["Evian",
         "Voss",
         "Veen",
         "San Pellegrino",
         "Perrier"]
                .forEach { String name -> BEVERAGES.put(name, MINERAL_WATER) }

        ["Coca-Cola",
         "Fanta",
         "Sprite"]
                .forEach { String name -> BEVERAGES.put(name, SOFT_DRINK) }

        ["Maxwell Ready-to-Drink Coffee",
         "Nescafé Gold",
         "Starbucks East Timor Tatamailau"]
                .forEach { String name -> BEVERAGES.put(name, COFFEE) }

        ["Prince Of Peace Organic White Tea",
         "Pai Mu Tan White Peony Tea",
         "Tazo Zen Green Tea",
         "Dilmah Sencha Green Tea",
         "Twinings Earl Grey",
         "Twinings Lady Grey",
         "Classic Indian Chai"]
                .forEach { String name -> BEVERAGES.put(name, TEA) }

        ["Cow's Milk",
         "Goat's Milk",
         "Unicorn's Milk",
         "Salt Lassi",
         "Mango Lassi",
         "Airag"]
                .forEach { String name -> BEVERAGES.put(name, DAIRY) }

        ["Crowmoor Extra Dry Apple",
         "Golden Cap Perry",
         "Somersby Blueberry",
         "Kopparbergs Naked Apple Cider",
         "Kopparbergs Raspberry",
         "Kingstone Press Wild Berry Flavoured Cider",
         "Crumpton Oaks Apple",
         "Frosty Jack's",
         "Ciderboys Mad Bark",
         "Angry Orchard Stone Dry",
         "Walden Hollow",
         "Fox Barrel Wit Pear"]
                .forEach { String name -> BEVERAGES.put(name, CIDER) }

        ["Budweiser",
         "Miller",
         "Heineken",
         "Holsten Pilsener",
         "Krombacher",
         "Weihenstephaner Hefeweissbier",
         "Ayinger Kellerbier",
         "Guinness Draught",
         "Kilkenny Irish Cream Ale",
         "Hoegaarden White",
         "Barbar",
         "Corsendonk Agnus Dei",
         "Leffe Blonde",
         "Chimay Tripel",
         "Duvel",
         "Pilsner Urquell",
         "Kozel",
         "Staropramen",
         "Lapin Kulta IVA",
         "Kukko Pils III",
         "Finlandia Sahti"]
                .forEach { String name -> BEVERAGES.put(name, BEER) }

        ["Jacob's Creek Classic Shiraz",
         "Chateau d’Yquem Sauternes",
         "Oremus Tokaji Aszú 5 Puttonyos"]
                .forEach { String name -> BEVERAGES.put(name, WINE) }

        ["Pan Galactic Gargle Blaster",
         "Mead",
         "Soma"]
                .forEach { String name -> BEVERAGES.put(name, OTHER) }
    }
}
