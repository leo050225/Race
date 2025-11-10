class Horse(n: Int) {
    var horseX = 0
    var horseY = 100 + 220 * n

    var number = 0

    fun HorseRun(){
        number ++
        if (number>3) {number = 0}
        horseX += (10..30).random()
    }
}
