package mcpc.tedo0627.kzeaddon.fabric.option;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

// OptionInstanceの作成がなぜかKotlinで動かない
public class AddonOptions {

    private static final ArrayList<Pair<String, SimpleOption<?>>> list = new ArrayList<>();

    public static SimpleOption<?>[] getOptions() {
        return list.stream().map(Pair::getSecond).toList().toArray(new SimpleOption[0]);
    }

    public static List<Pair<String, SimpleOption<?>>> getOptionsList() {
        return list;
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

    public static final SimpleOption<InvisibleType> invisibleType = new SimpleOption<>(
        "kzeaddon.options.invisibleType",
        SimpleOption.constantTooltip(Text.translatable("kzeaddon.options.invisibleType.tooltip")),
        SimpleOption.enumValueText(),
        new SimpleOption.PotentialValuesBasedCallbacks<>(
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
        list.add(new Pair<>("hidePlayerToggle", hidePlayerToggle));
        list.add(new Pair<>("hidePlayerOverlay", hidePlayerOverlay));
        list.add(new Pair<>("invisibleType", invisibleType));
        list.add(new Pair<>("gamma", gamma));
        list.add(new Pair<>("displayBullet", displayBullet));
    }
}
