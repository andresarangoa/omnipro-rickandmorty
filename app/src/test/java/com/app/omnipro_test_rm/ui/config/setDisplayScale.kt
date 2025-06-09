package com.app.omnipro_test_rm.ui.config

import android.content.Context
import android.content.res.Configuration

fun Context.setDisplayScale(displayScale: Float) {
    apply {
        val density = resources.configuration.densityDpi
        val config = Configuration(resources.configuration)

        config.densityDpi = (density * displayScale).toInt()

        createConfigurationContext(config)
    }
}
