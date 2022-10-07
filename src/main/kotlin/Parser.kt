class Parser {
    fun parse(text: String) = text.split(",").filter {
        it.startsWith("#") &&
                it.contains("=")
    }.associate {
        val resu = it.removePrefix("#").split("=")
        Pair(resu[0], resu[1])
    }

    fun clear(text:String) = text.split(",").filter {
        SavedData.mostTags[it.replace(" ","_")] = SavedData.mostTags.getOrDefault(it,0) + 1
        !it.startsWith("#") or
                !it.contains("=")
    }.joinToString(",")
}