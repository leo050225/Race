package s1131244o365st.pu.edu.race

class Horse() {
    var horseX = 0
    var horseY = 100

    var number = 0

    fun HorseRun(){
        number ++
        if (number>3) {number = 0}
        horseX += (10..30).random()
    }
}
