package com.bed.chat.data.mappers

interface Mapper<in IN, out OUT> {
    operator fun invoke(parameter: IN): OUT
}
