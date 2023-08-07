package mcpc.tedo0627.kzeaddon.fabric.option

import com.mojang.datafixers.util.Pair
import net.minecraft.client.OptionInstance
import net.minecraft.util.FastColor

class OverlayTextOption(val name: String, val useColor: Boolean = true) {

    // java用
    constructor(name: String) : this(name, true)

    private val xOption = AddonOptions.createLocationOption()
    private val yOption = AddonOptions.createLocationOption()

    private val scaleOption = AddonOptions.createScaleOption()

    private val rOption = AddonOptions.createColorOption()
    private val gOption = AddonOptions.createColorOption()
    private val bOption = AddonOptions.createColorOption()
    private val aOption = AddonOptions.createColorOption()

    var x: Int
        get() = xOption.get()
        set(value) = xOption.set(value)
    var y: Int
        get() = yOption.get()
        set(value) = yOption.set(value)

    var scale: Int
        get() = scaleOption.get()
        set(value) = scaleOption.set(value)

    var r: Int
        get() = rOption.get()
        set(value) = rOption.set(value)
    var g: Int
        get() = gOption.get()
        set(value) = gOption.set(value)
    var b: Int
        get() = bOption.get()
        set(value) = bOption.set(value)
    var a: Int
        get() = aOption.get()
        set(value) = aOption.set(value)

    val scalePercent: Float
        get() = scale.toFloat() / 100

    val color: Int
        get() = FastColor.ARGB32.color(a, r, g, b)

    fun addSaveList(list: ArrayList<Pair<String, OptionInstance<*>>>) {
        list.add(Pair("${name}LocationX", xOption))
        list.add(Pair("${name}LocationY", yOption))

        list.add(Pair("${name}Scale", scaleOption))

        if (!useColor) return

        list.add(Pair("${name}ColorR", rOption))
        list.add(Pair("${name}ColorG", gOption))
        list.add(Pair("${name}ColorB", bOption))
        list.add(Pair("${name}ColorA", aOption))
    }
}