package com.elevenine.artinstitute.domain.mapper.base

/**
 * The default mapper class for mapping the list of objects of I type to the list of objects of O
 * type.
 *
 * @param mapper - mapper object for the given I and O types of objects to be mapped.
 */
class ListMapperImpl<I, O>(private val mapper: Mapper<I, O>) : ListMapper<I, O> {

    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }
}