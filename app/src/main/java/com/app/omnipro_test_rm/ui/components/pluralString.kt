package com.app.omnipro_test_rm.ui.components

import androidx.annotation.PluralsRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun pluralString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
    val context = LocalContext.current
    return context.resources.getQuantityString(id, quantity, *formatArgs)
}