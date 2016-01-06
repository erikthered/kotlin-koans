package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return (year * 10000 + month * 100 + dayOfMonth) - (other.year * 10000 + other.month * 100 + other.dayOfMonth)
    }
}


operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)
operator fun MyDate.plus(interval: TimeInterval) = addTimeIntervals(interval, 1)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate): ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = DateIterator(this)
}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
    var current: MyDate = dateRange.start
    override fun next(): MyDate {
        val result = current
        current = current.addTimeIntervals(TimeInterval.DAY, 1)
        return result
    }

    override fun hasNext(): Boolean {
        return current <= dateRange.endInclusive
    }

}

class RepeatedTimeIntervals(val interval: TimeInterval, val number: Int)

operator fun TimeInterval.times(number: Int) = RepeatedTimeIntervals(this, number)

operator fun MyDate.plus(timeIntervals: RepeatedTimeIntervals) = addTimeIntervals(timeIntervals.interval, timeIntervals.number)
