package com.elevenine.artinstitute.domain.mapper.base

/**
 * @author Sherzod Nosirov
 * @since July 29, 2021
 */

/**
 * The base interface for the DTO-to-Domain mapping classes.
 *
 * The Mappers in this project are used for mapping the API's DTO models to the presentation Domain
 * models, or domain models to UI models.
 *
 */
interface Mapper<I, O> {
    fun map(input: I): O
}