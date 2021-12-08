package com.elevenine.artinstitute.domain.mapper.base

/**
 * @author Sherzod Nosirov
 * @since July 29, 2021
 */

/**
 * The base mapper interface for mapping the list of objects of I type to the list of objects of O
 * type.
 */
interface ListMapper<I, O> : Mapper<List<I>, List<O>>

