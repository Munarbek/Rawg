package  com.spsoft.rawgames.data.models.tags
data class Tags(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)