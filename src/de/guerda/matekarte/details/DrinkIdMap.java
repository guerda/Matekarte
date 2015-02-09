package de.guerda.matekarte.details;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by philip on 08.02.2015.
 */
public class DrinkIdMap {

    private static DrinkIdMap instance;
    private Map<String, String> drinkMap;

    private DrinkIdMap() {
        drinkMap = new HashMap<>();
        drinkMap.put("528bd9c27a58b4fa63000031", "Mier");
        drinkMap.put("52c5a4e07a58b4ae7000395f", "Rio Mate");
        drinkMap.put("53cab8e07a58b43bba00199b", "UltiMate Melone");
        drinkMap.put("53cab8147a58b4b5f9001dba", "UltiMate");
        drinkMap.put("510e5e33ce007c8e99000008", "Buenos Mate");
        drinkMap.put("51ea5c3b7a58b427e4000003", "MAKAvA ice tea");
        drinkMap.put("51262e17ce007c863b001ba7", "Top Mate");
        drinkMap.put("50fb3d16ce007c40fc00080f", "Flora Power");
        drinkMap.put("50fb3d16ce007c40fc000812", "CM Winter-Edition");
        drinkMap.put("5299b64a7a58b41054000bc6", "kolle-mate");
        drinkMap.put("50fb3d16ce007c40fc00080e", "1337MATE");
        drinkMap.put("51ea5df47a58b4f9e1000002", "gekkoMATE");
        drinkMap.put("50fb3d16ce007c40fc000810", "Club-Mate Cola");
        drinkMap.put("533031297a58b42079003bb4", "ChariTea mate");
        drinkMap.put("520665d47a58b439ed0041e2", "Club-Mate Granat");
        drinkMap.put("53cab8a27a58b4762c001949", "UltiMate Granate");
        drinkMap.put("528bd8977a58b4d040000026", "Oettinger Mate-Classic");
        drinkMap.put("53cab2db7a58b44d0d001c29", "Oettinger Mate-Cola");
        drinkMap.put("528bd6c87a58b40e0d000002", "MAYA Mate");
        drinkMap.put("510e62b8ce007c115e000006", "Mio Mio Mate");
        drinkMap.put("50fb3d16ce007c40fc000811", "Club-Mate Ice Tea");
        drinkMap.put("50fb3d16ce007c40fc00080d", "Club-Mate");
    }

    public static DrinkIdMap getInstance() {
        if (instance == null) {
            instance = new DrinkIdMap();
        }
        return instance;
    }

    public String getNameForDrinkId(String anId) {
        return drinkMap.get(anId);
    }
}
