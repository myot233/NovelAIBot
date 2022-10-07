package data

import kotlin.math.absoluteValue

data class Parameters(
    var height: Int = 768,
    var n_samples: Int = 1,
    var noise: Double = 0.2,
    var sampler: String = "k_euler_ancestral",
    var scale: Int = 12,
    var seed: Int = System.currentTimeMillis().toInt().absoluteValue,
    var steps: Int = 28,
    var strength: Double = 0.7,
    var uc: String = "lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry",
    var ucPreset: Int = 0,
    var width: Int = 512,
    val parm: Parameters.()->Unit
){
    init {
        parm()
    }
}


