package mcpc.tedo0627.kzeaddon.fabric.option;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// OptionInstanceの作成がなぜかKotlinで動かない
public class AddonOptions {

    private static final HashMap<String, SimpleOption<?>> map = new HashMap<>();

    public static SimpleOption<?>[] getOptions() {
        return map.values().toArray(new SimpleOption[0]);
    }

    public static Map<String, SimpleOption<?>> getOptionsMap() {
        return map;
    }

    public static final SimpleOption<HideToggleType> hidePlayerToggle = new SimpleOption<>(
        "kzeaddon.options.hidePlayerToggle",
        SimpleOption.emptyTooltip(),
        SimpleOption.enumValueText(),
        new SimpleOption.PotentialValuesBasedCallbacks<>(
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

    public static final SimpleOption<Boolean> hidePlayerOverlay = SimpleOption.ofBoolean(
        "kzeaddon.options.hidePlayerOverlay",
        SimpleOption.constantTooltip(Text.translatable("kzeaddon.options.hidePlayerOverlay.tooltip")),
        false,
        (bool) -> {}
    );

    public static final SimpleOption<Boolean> gamma = SimpleOption.ofBoolean(
        "kzeaddon.options.gamma",
        SimpleOption.emptyTooltip(),
        false,
        (bool) -> {}
    );

    public static final SimpleOption<Boolean> displayBullet = SimpleOption.ofBoolean(
        "kzeaddon.options.displayBullet",
        SimpleOption.emptyTooltip(),
        false,
        (bool) -> {}
    );

    static {
        map.put("hidePlayerToggle", hidePlayerToggle);
        map.put("hidePlayerOverlay", hidePlayerOverlay);
        map.put("gamma", gamma);
        map.put("displayBullet", displayBullet);
    }
}
