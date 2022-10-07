import data.Parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import java.time.LocalTime

object CommandHub : CompositeCommand(
    Demo,
    "ai"
) {
    private val parser = Parser()
    private suspend fun CommandSenderOnMessage<GroupMessageEvent>.generate(vararg text: String, tr: Boolean) {
        val now = LocalTime.now()
        val record = Recorder.map.getOrPut(this.fromEvent.sender.id) { LocalTime.of(0, 0) }
        if (record.minute == now.minute && record.hour == now.hour) {
            sendMessage(this.fromEvent.message.quote() + "你已经在一分钟内使用过本服务")
            return
        }
        Recorder.map[this.fromEvent.sender.id] = LocalTime.now()

        val args = parser.parse(text.joinToString())


        val res =
            withContext(Dispatchers.IO) {
                ApiCaller.callApi(text.joinToString(" ").clean(), translated = tr, generateParameters(args)).toExternalResource()
            }
        sendMessage(this.fromEvent.message.quote() + this.fromEvent.group.uploadImage(res))
        withContext(Dispatchers.IO) {
            res.close()
        }
    }

    private fun generateParameters(map:Map<String,String>) = Parameters{
        println(map)
        seed = map.getOrDefault("seed",seed.toString()).toInt()
        height = map.getOrDefault("height",height.toString()).toInt()
        width = map.getOrDefault("height",width.toString()).toInt()
    }

    private fun String.clean() = parser.clear(this)

    @SubCommand
    suspend fun CommandSenderOnMessage<GroupMessageEvent>.gentr(vararg text: String) {
        generate(*text, tr = true)
    }

    @SubCommand
    suspend fun CommandSenderOnMessage<GroupMessageEvent>.gen(vararg text: String) {
        generate(*text, tr = false)
    }

    @SubCommand
    suspend fun CommandSenderOnMessage<GroupMessageEvent>.mostTag(){
        val sorted = SavedData.mostTags.toList().sortedBy {
            it.second
        }.reversed().subList(0,10)
        val message = MessageChainBuilder().apply {
            sorted.forEachIndexed { index, pair ->
                append("${index+1}.${pair.first}:${pair.second}\n")
            }
        }.build()

        sendMessage(message)
    }
}

