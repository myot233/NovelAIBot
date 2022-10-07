import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object SavedData:AutoSavePluginData("data") {
    val mostTags:MutableMap<String,Int> by value()

}