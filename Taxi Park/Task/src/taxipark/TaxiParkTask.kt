package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        this.allDrivers.filter {
            it !in this.trips.map {
                it.driver
            }
        }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        this.allPassengers.filter {
            this.trips.filter { trip ->
                it in trip.passengers
            }.count() >= minTrips
        }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.allPassengers.filter {
            this.trips.filter { trip ->
                it in trip.passengers && trip.driver == driver
            }.count() > 1
        }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        this.allPassengers.filter {
            this.trips.filter { trip ->
                it in trip.passengers && trip.discount != null
            }.count() > this.trips.filter { t -> it in t.passengers && t.discount == null }.count()
        }.toSet()


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (this.trips.isEmpty()) {
        return null
    } else {
        val max = trips.map { it.duration }.max() ?: 0
        val noOfTrips = HashMap<Int, IntRange>()
        for (i in 0..max step 10) {
            val range = IntRange(i, i + 9)
            val noOfTripRange = this.trips.filter { it.duration in range }.count()
            noOfTrips[noOfTripRange] = range
        }
        return noOfTrips[noOfTrips.toSortedMap().lastKey()]
    }
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (this.trips.isEmpty()) {
        return false
    } else {
        val total = this.trips.map { it.cost }.sum()
        val costByDriver = trips.groupBy {
            it.driver
        }.mapValues { (_, value) ->
            value.sumByDouble { it.cost }

        }.toList()
                .sortedByDescending { (_, it) ->
                    it
                }.toMap()
        var current = 0.0
        var noOfDriver = 0
        for (value in costByDriver.values) {
            noOfDriver++
            current += value
            if (current >= (total * 0.8)) break
        }
        return noOfDriver <= (allDrivers.size * 0.2)
    }
}