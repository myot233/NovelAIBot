import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info

object Demo : KotlinPlugin(
    JvmPluginDescription(
        id = "com.example.demo",
        name = "Demo",
        version = "0.1.0",
    ) {
        author("Administrator")
    }
) {
    override fun onEnable() {
        CommandHub.register()
        SavedData.reload()
        logger.info { "Plugin loaded" }
    }
}