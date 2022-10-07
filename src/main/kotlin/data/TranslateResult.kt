package data

data class TranslateResult(
    val elapsedTime: Int,
    val errorCode: Int,
    val translateResult: List<List<TranslateResultX>>,
    val type: String
)