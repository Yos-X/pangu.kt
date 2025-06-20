package pangu_example

import ws.vinta.pangu.Pangu

object ExampleApp {
    @JvmStatic
    fun main(args: Array<String>) {
        val pangu = Pangu()

        val text =
            pangu.spacingText("Mongolia大草原神话也不少，蒸气的英文是Steam，顺带一提瓦特改良了蒸汽机(Steam Engine)，刘德华为什么很少演反派，小华为了考试提前就醒来了，挤了5g牙膏刷刷牙就匆匆赶到学校了")
        println(text)
    }
}
