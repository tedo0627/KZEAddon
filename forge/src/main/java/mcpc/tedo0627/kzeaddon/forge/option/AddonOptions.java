package mcpc.tedo0627.kzeaddon.forge.option;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// OptionInstanceの作成がなぜかKotlinで動かない
public class AddonOptions {

    private static final HashMap<String, OptionInstance<?>> map = new HashMap<>();

    public static OptionInstance<?>[] getOptions() {
        return map.values().toArray(new OptionInstance[0]);
    }

    public static Map<String, OptionInstance<?>> getOptionsMap() {
        return map;
    }

    public static final OptionInstance<HideToggleType> hidePlayerToggle = new OptionInstance<>(
        "kzeaddon.options.hidePlayerToggle",
        OptionInstance.noTooltip(),
        OptionInstance.forOptionEnum(),
        new OptionInstance.Enum<>(
            List.of(HideToggleType.values()),
            Codec.either(Codec.BOOL, Codec.STRING).xmap(
                (either) -> either.map(
                    (bool) -> bool ? HideToggleType.SWITCH : HideToggleType.LONG_PRESS,
                    (str) -> str.equals("true") ? HideToggleType.SWITCH : HideToggleType.LONG_PRESS
                ),
                (type) -> Either.right(switch (type) {
                    case SWITCH -> "true";
                    case LONG_PRESS -> "false";
                })
            )
        ),
        HideToggleType.SWITCH,
        (type) -> {}
    );

    public static final OptionInstance<Boolean> gamma = OptionInstance.createBoolean(
        "kzeaddon.options.gamma",
        OptionInstance.noTooltip(),
        false,
        (bool) -> {}
    );

    static {
        map.put("hidePlayerToggle", hidePlayerToggle);
        map.put("gamma", gamma);
    }
}
