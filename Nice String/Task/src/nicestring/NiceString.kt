package nicestring

fun String.isNice(): Boolean {
    val chars = listOf('a', 'e', 'i', 'o', 'u')
    val doesNotContainBuBaBe = !this.contains("bu")
            && !this.contains("ba") && !this.contains("be")
    val containsThreeVowels = this.filter { it ->
        chars.contains(it)
    }.count() >= 3

    var containDuplicated = false
    this.forEachIndexed { index, c ->
        if (index != 0) {
            if (this.get(index - 1) == c) {
                containDuplicated = true
            }
        }

    }
    return listOf( doesNotContainBuBaBe , containsThreeVowels,containDuplicated)
            .filter { it }
            .count() >= 2
}