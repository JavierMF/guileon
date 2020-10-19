package org.guileon.boundaries.backends.persistence

import org.neo4j.driver.Driver
import org.neo4j.driver.Value
import org.neo4j.driver.Record
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NeoDb @Inject constructor(
        private val driver: Driver
) {
    fun <T> readAsList(query:String, params: Map<String,Any> = emptyMap(), mapper:(Value) -> T): List<T> =
            driver.session().readTransaction {
                val result = it.run(query, params)
                result.list().map { mapper(it.values()[0]) }
            }

    fun <T> readAsEntity(query: String, params: Map<String,Any> = emptyMap(), mapper: (Value) -> T): T? =
            driver.session().readTransaction {
                val result = it.run(query, params)
                result.single()?.get(0)?.let(mapper)
            }

    fun <T> readListAndMap(query: String,params: Map<String,Any> = emptyMap(), mapper: (Record) -> T): List<T> =
        driver.session().readTransaction {
            val result = it.run(query, params)
            result.list().map(mapper)
        }
}

fun Value.string(name: String) = this.get(name).asString()
fun Value.int(name: String) = this.get(name).asInt()
