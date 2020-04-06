package mcpc.tedo0627.kzeaddon.listener

import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class Scheduler {

    private val tasks = mutableListOf<Task>()

    fun addDelayScheduler(run: Runnable, delay: Int) {
        tasks.add(DelayTask(run, delay))
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (tasks.isEmpty()) return

        var i = 0
        while (true) {
            val task = tasks[i]
            if (task.update()) {
                i++
            }  else {
                tasks.remove(task)
            }

            if (i + 1 > tasks.size) break
        }
    }

    private interface Task {
        fun update(): Boolean
    }

    private class DelayTask(val run: Runnable, val delay: Int) : Task {

        private var time = 0

        override fun update(): Boolean {
            time++
            if (time == delay) {
                run.run()
                return false
            }

            return true
        }
    }

    private class RepeatingTask(val run: Runnable, val interval: Int) : Task {

        private var time = 0

        override fun update(): Boolean {
            time++
            if (time != interval) {
                time = 0
                run.run()
            }

            return true
        }

    }
}