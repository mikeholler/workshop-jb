package ii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year != other.year) return year - other.year
        if (month != other.month) return month - other.month
        return dayOfMonth - other.dayOfMonth
    }
}

fun MyDate.rangeTo(endDate: MyDate): DateRange = DateRange(this, endDate)
fun MyDate.plus(timeInterval: TimeInterval): MyDate = addTimeIntervals(timeInterval, 1)
fun MyDate.plus(timeIntervals: RepeatedTimeInterval) = addTimeIntervals(timeIntervals.timeInterval, timeIntervals.number)

class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)

enum class TimeInterval {
    DAY
    WEEK
    YEAR
}

fun TimeInterval.times(number: Int) = RepeatedTimeInterval(this, number)

class DateRange(
        public override val start: MyDate,
        public override val end: MyDate
): Iterable<MyDate>, Range<MyDate> {
    override fun iterator(): Iterator<MyDate> = DateIterator(this)
    override fun contains(item: MyDate): Boolean = start <= item && item <= end
}

class DateIterator(val dateRange: DateRange): Iterator<MyDate> {
    var current = dateRange.start

    override fun next(): MyDate {
        val result = current
        current = current.addTimeIntervals(TimeInterval.DAY, 1)
        return result
    }

    override fun hasNext(): Boolean = dateRange.end >= current
}
