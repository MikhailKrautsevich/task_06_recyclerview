package com.example.recyclerviewapp.data

data class ContactData(
    var name: String,
    var lastname: String,
    var number: String,
    val picId: Int
) {
    companion object {
        const val URL_SAMPLE: String = "https://picsum.photos/id/%d/120"
    }

    override fun toString() = String.format("%s\n%s\n%s", name, lastname, number)

    fun getSmallPicURL() = String.format(URL_SAMPLE, picId)
}
