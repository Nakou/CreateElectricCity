package dev.nakou.createelectriccity.registry;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.nakou.createelectriccity.CreateElectricCity;

public class CECPartialModels {
    public static final PartialModel
            SMALL_LIGHT_BULB_ON = block("lights/small_light_bulb/on"),
            SMALL_LIGHT_BULB_OFF = block("lights/small_light_bulb/off"),
            SMALL_LIGHT_BULB_LIGHT = block("lights/small_light_bulb/light"),
            BIG_LIGHT_BULB_ON = block("lights/big_light_bulb/on"),
            BIG_LIGHT_BULB_OFF = block("lights/big_light_bulb/off"),
            OLD_LIGHT_BULB_ON = block("lights/old_light_bulb/on"),
            OLD_LIGHT_BULB_OFF = block("lights/old_light_bulb/off"),
            CELLING_LIGHT_ON = block("lights/celling_light/on"),
            CELLING_LIGHT_OFF = block("lights/celling_light/off"),
            LANTERN_ON = block("lights/lantern/on"),
            LANTERN_OFF = block("lights/lantern/off"),
            NEON_ON = block("lights/neon/on"),
            NEON_OFF = block("lights/neon/off");

    private static PartialModel block(String path) {
        return PartialModel.of(CreateElectricCity.asResource("block/" + path));
    }

    public static void init() {
    }
}
