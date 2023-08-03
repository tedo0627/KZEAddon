package mcpc.tedo0627.kzeaddon.fabric.item

import net.minecraft.client.player.LocalPlayer
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.item.HoeItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tiers
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.animatable.client.RenderProvider
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animation.AnimatableManager
import software.bernie.geckolib.core.animation.AnimationController
import software.bernie.geckolib.core.animation.RawAnimation
import software.bernie.geckolib.core.`object`.PlayState
import software.bernie.geckolib.util.GeckoLibUtil
import java.util.function.Consumer
import java.util.function.Supplier

class KnifeItem(
    val name: String
) : HoeItem(Tiers.DIAMOND, -3, 0f, Properties()), GeoItem {

    private val randomSource = RandomSource.create()

    private val cache = GeckoLibUtil.createInstanceCache(this)
    private val renderProvider = GeoItem.makeRenderer(this)

    override fun createRenderer(consumer: Consumer<Any>) {
        consumer.accept(object: RenderProvider {
            private val renderer = KnifeItemRenderer(name)

            override fun getCustomRenderer() = renderer
        })
    }

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(
            AnimationController(this, "PickupAnimation", 0) {
                it.controller.forceAnimationReset()
                PlayState.CONTINUE
            }
                .triggerableAnim("idle", RawAnimation.begin().thenLoop("idle"))
                .triggerableAnim("pick1", RawAnimation.begin().thenPlay("pick1").thenLoop("idle"))
                .triggerableAnim("pick2", RawAnimation.begin().thenPlay("pick2").thenLoop("idle"))
                .triggerableAnim("pick3", RawAnimation.begin().thenPlay("pick3").thenLoop("idle"))
        )
    }

    override fun getRenderProvider(): Supplier<Any> = renderProvider

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = cache

    fun runIdle(player: LocalPlayer, itemStack: ItemStack) {
        resetAnimation(itemStack)
        triggerAnim<KnifeItem>(player, GeoItem.getId(itemStack), "PickupAnimation", "idle")
    }

    fun runAnimation(player: LocalPlayer, itemStack: ItemStack) {
        resetAnimation(itemStack)
        val rare = Mth.nextInt(randomSource, 0, 10000)
        val animation = if (rare == 6449) { // あえて適当な数字
            if (name == "survival_knife") {
                if (Mth.nextInt(randomSource, 0, 2) == 0) "pick2" else "pick3"
            } else {
                "pick2"
            }
        } else {
            "pick1"
        }
        triggerAnim<KnifeItem>(player, GeoItem.getId(itemStack), "PickupAnimation", animation)
    }

    private fun resetAnimation(itemStack: ItemStack) {
        animatableInstanceCache
            .getManagerForId<KnifeItem>(GeoItem.getId(itemStack))
            .animationControllers["PickupAnimation"]
            ?.stop()
    }
}