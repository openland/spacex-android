package com.openland.spacex

import com.openland.spacex.model.OutputType

interface OperationDefinition {
    val kind: OperationKind
    val selector: OutputType.Object
    val name: String
    val body: String
}