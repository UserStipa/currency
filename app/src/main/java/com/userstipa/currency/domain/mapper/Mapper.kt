package com.userstipa.currency.domain.mapper

interface Mapper<T, K> {

    fun map(input: List<T>): List<K>

}