package mcpc.tedo0627.kzeaddon.forge.option;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

// OptionInstanceの作成がなぜかKotlinで動かない
public class AddonOptions {

    private static final ArrayList<Pair<String, OptionInstance<?>>> list = new ArrayList<>();

    public static OptionInstance<?>[] getOptions() {
        return list.stream().map(Pair::getSecond).toList().toArray(new OptionInstance[0]);
    }

    public static List<Pair<String, OptionInstance<?>>> getOptionsList() {
        return list;
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

    public static final OptionInstance<Boolean> hidePlayerOverlay = OptionInstance.createBoolean(
        "kzeaddon.options.hidePlayerOverlay",
        OptionInstance.cachedConstantTooltip(Component.translatable("kzeaddon.options.hidePlayerOverlay.tooltip")),
        false,
        (bool) -> {}
    );

    public static final OptionInstance<InvisibleType> invisibleType = new OptionInstance<>(
        "kzeaddon.options.hidePlayerToggle",
        OptionInstance.cachedConstantTooltip(Component.translatable("kzeaddon.options.invisibleType.tooltip")),
        OptionInstance.forOptionEnum(),
        new OptionInstance.Enum<>(
            List.of(InvisibleType.values()),
            Codec.either(Codec.BOOL, Codec.STRING).xmap(
                (either) -> either.map(
                    (bool) -> bool ? InvisibleType.TRANSLUCENT : InvisibleType.INVISIBLE,
                    (str) -> switch (str) {
                        case "false" -> InvisibleType.INVISIBLE;
                        case "toggle" -> InvisibleType.TOGGLE;
                        default -> InvisibleType.TRANSLUCENT;
                    }
                ),
                (type) -> Either.right(switch (type) {
                    case TRANSLUCENT -> "true";
                    case INVISIBLE -> "false";
                    case TOGGLE -> "toggle";
                })
            )
        ),
        InvisibleType.TRANSLUCENT,
        (type) -> {}
    );

    public static final OptionInstance<Boolean> gamma = OptionInstance.createBoolean(
        "kzeaddon.options.gamma",
        OptionInstance.noTooltip(),
        false,
        (bool) -> {}
    );

    public static final OptionInstance<Boolean> displayBullet = OptionInstance.createBoolean(
        "kzeaddon.options.displayBullet",
        OptionInstance.noTooltip(),
        false,
        (bool) -> {}
    );

    static {
        list.add(new Pair<>("hidePlayerToggle", hidePlayerToggle));
        list.add(new Pair<>("hidePlayerOverlay", hidePlayerOverlay));
        list.add(new Pair<>("invisibleType", invisibleType));
        list.add(new Pair<>("gamma", gamma));
        list.add(new Pair<>("displayBullet", displayBullet));
    }
}
