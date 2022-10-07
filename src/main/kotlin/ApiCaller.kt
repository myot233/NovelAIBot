import com.google.gson.Gson
import data.Parameters
import data.PostData
import data.TranslateResult
import io.ktor.util.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.Base64

object ApiCaller {
    private val client = OkHttpClient.Builder().build()
    @OptIn(InternalAPI::class)
    fun callApi(tags:String,translated:Boolean=false,configuration: Parameters): ByteArray {

        val json = Gson().toJson(PostData(if(!translated) tags else tags.split(",").joinToString(",") { translate(it) },"safe-diffusion",configuration).also { println(it) })
        val request = Request.Builder().apply {
            url("https://api.novelai.net/ai/generate-image")
            post(json.toRequestBody())
            addHeader("content-type","application/json")
            addHeader("authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InpnU3hkUjd2MkZVT3plWk1rbW5nUyIsIm5jIjoiMXNWVTJhUW90YS11ZVhxelV1dVp1IiwiaWF0IjoxNjY1MDYwNTMyLCJleHAiOjE2Njc2NTI1MzJ9.s4PZ6h20LA8zDpzIxptRSHv6MCpEo5XqiBZnhDuWmhg")

        }.build()
        val decoer = Base64.getDecoder()
        return decoer.decode(client.newCall(request).execute().body!!.string().split("\n").map{
            it.split(":")
        }[2][1])
    }

    fun translate(text:String):String{
        val request = Request.Builder().apply {
            url("https://fanyi.youdao.com/translate?&doctype=json&type=AUTO&i=${text}")
            get()
        }.build()

        val result = Gson().fromJson(client.newCall(request).execute().body!!.string(), TranslateResult::class.java)
        return result.translateResult[0][0].tgt
    }


}



