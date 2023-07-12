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

    public static final OptionInstance<Boolean> hidePlayerItem = OptionInstance.createBoolean(
        "kzeaddon.options.hidePlayerItem",
        OptionInstance.noTooltip(),
        false,
        (bool) -> {}
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

    public static final OptionInstance<Boolean> displayKillLog = OptionInstance.createBoolean(
        "kzeaddon.options.displayKillLog",
        OptionInstance.noTooltip(),
        false,
        (bool) -> {}
    );

    public static final OptionInstance<Boolean> addKillLogWeaponName = OptionInstance.createBoolean(
        "kzeaddon.options.addKillLogWeaponName",
        OptionInstance.noTooltip(),
        false,
        (bool) -> {}
    );

    public static final OptionInstance<Boolean> disableKillLogWhenPressTab = OptionInstance.createBoolean(
        "kzeaddon.options.disableKillLogWhenPressTab",
        OptionInstance.noTooltip(),
        false,
        (bool) -> {}
    );

    public static final OptionInstance<Boolean> removeChatKillLog = OptionInstance.createBoolean(
        "kzeaddon.options.removeChatKillLog",
        OptionInstance.cachedConstantTooltip(Component.translatable("kzeaddon.options.removeChatKillLog.tooltip")),
        false,
        (bool) -> {}
    );

    public static final OptionInstance<Boolean> displayGlassTimer = OptionInstance.createBoolean(
        "kzeaddon.options.displayGlassTimer",
        OptionInstance.noTooltip(),
        false,
        (bool) -> {}
    );

    public static final OptionInstance<Boolean> displayScoreboardTimer = OptionInstance.createBoolean(
        "kzeaddon.options.displayScoreboardTimer",
        OptionInstance.noTooltip(),
        false,
        (bool) -> {}
    );

    public static final OptionInstance<Integer> currentBulletOverlayLocationX = createLocationOption();
    public static final OptionInstance<Integer> currentBulletOverlayLocationY = createLocationOption();

    public static final OptionInstance<Integer> remainingBulletOverlayLocationX = createLocationOption();
    public static final OptionInstance<Integer> remainingBulletOverlayLocationY = createLocationOption();

    public static final OptionInstance<Integer> killLogOverlayLocationX = createLocationOption();
    public static final OptionInstance<Integer> killLogOverlayLocationY = createLocationOption();

    public static final OptionInstance<Integer> glassTimerOverlayLocationX = createLocationOption();
    public static final OptionInstance<Integer> glassTimerOverlayLocationY = createLocationOption();

    public static final OptionInstance<Integer> scoreboardTimerOverlayLocationX = createLocationOption();
    public static final OptionInstance<Integer> scoreboardTimerOverlayLocationY = createLocationOption();

    static {
        list.add(new Pair<>("hidePlayerToggle", hidePlayerToggle));
        list.add(new Pair<>("hidePlayerOverlay", hidePlayerOverlay));
        list.add(new Pair<>("invisibleType", invisibleType));
        list.add(new Pair<>("hidePlayerItem", hidePlayerItem));
        list.add(new Pair<>("gamma", gamma));
        list.add(new Pair<>("displayBullet", displayBullet));
        list.add(new Pair<>("displayKillLog", displayKillLog));
        list.add(new Pair<>("addKillLogWeaponName", addKillLogWeaponName));
        list.add(new Pair<>("disableKillLogWhenPressTab", disableKillLogWhenPressTab));
        list.add(new Pair<>("removeChatKillLog", removeChatKillLog));
        list.add(new Pair<>("displayGlassTimer", displayGlassTimer));
        list.add(new Pair<>("displayScoreboardTimer", displayScoreboardTimer));

        list.add(new Pair<>("currentBulletOverlayLocationX", currentBulletOverlayLocationX));
        list.add(new Pair<>("currentBulletOverlayLocationY", currentBulletOverlayLocationY));
        list.add(new Pair<>("remainingBulletOverlayLocationX", remainingBulletOverlayLocationX));
        list.add(new Pair<>("remainingBulletOverlayLocationY", remainingBulletOverlayLocationY));
        list.add(new Pair<>("killLogOverlayLocationX", killLogOverlayLocationX));
        list.add(new Pair<>("killLogOverlayLocationY", killLogOverlayLocationY));
        list.add(new Pair<>("glassTimerOverlayLocationX", glassTimerOverlayLocationX));
        list.add(new Pair<>("glassTimerOverlayLocationY", glassTimerOverlayLocationY));
        list.add(new Pair<>("scoreboardTimerOverlayLocationX", scoreboardTimerOverlayLocationX));
        list.add(new Pair<>("scoreboardTimerOverlayLocationY", scoreboardTimerOverlayLocationY));
    }

    private static OptionInstance<Integer> createLocationOption() {
        return new OptionInstance<>(
            "",
            OptionInstance.noTooltip(),
            (text, value) -> Component.literal(""),
            new OptionInstance.IntRange(-10000, 10000),
            0,
            (value) -> {}
        );
    }
}
