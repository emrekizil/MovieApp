package com.emrekizil.movieapp.data.repository.mapper

inline fun <I, O> I.mapTo(crossinline mapper: (I) -> O): O {
    return mapper(this)
}