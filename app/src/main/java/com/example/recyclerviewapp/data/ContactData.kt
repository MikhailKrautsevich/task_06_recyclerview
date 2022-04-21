package com.example.recyclerviewapp.data

data class ContactData(
    var id: Int = 0,
    var name: String,
    var lastname: String,
    var number: String,
    val picId: Int
) {
    companion object {
        const val URL_SMALL_PIC_SAMPLE: String = "https://picsum.photos/id/%d/120"
        const val URL_BIG_PIC_SAMPLE: String = "https://picsum.photos/id/%d/240"
    }

    override fun toString() = String.format("%s\n%s\n%s", name, lastname, number)

    fun getSmallPicURL() = String.format(URL_SMALL_PIC_SAMPLE, picId)

    fun getBigPicURL() = String.format(URL_BIG_PIC_SAMPLE, picId)
}
