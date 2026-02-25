package dev.nakou.createelectriccity.registry;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.nakou.createelectriccity.CreateElectricCity;

public class CECPartialModels {
    public static final PartialModel
            SMALL_LIGHT_BULB = block("lights/small_light_bulb/light"),
            LANTERN = block("lights/lantern/light"),
            BIG_LIGHT_BULB = block("lights/big_light_bulb/light"),
            OLD_LIGHT_BULB = block("lights/old_light_bulb/light"),
            CELLING_LIGHT = block("lights/celling_light/light"),
            NEON = block("lights/neon/light");

    private static PartialModel block(String path) {
        return PartialModel.of(CreateElectricCity.asResource("block/" + path));
    }

    public static void init() {
    }
}
