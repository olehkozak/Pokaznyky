package pokaznyky.lviv.model

import java.util.*

/**
 * Created by olehkozak on 12/13/17.
 */
class Counter {

    private var id = UUID.randomUUID()
    var number: Int = -1
    var result: Int = -1

    fun isEmpty() : Boolean{
        return number == -1 && result == -1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Counter

        if (id != other.id) return false
        if (number != other.number) return false
        if (result != other.result) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + number
        result = 31 * result + this.result
        return result
    }


}