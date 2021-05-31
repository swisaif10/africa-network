package com.mobiblanc.amdie.africa.network.utilities

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import com.mobiblanc.amdie.africa.network.BuildConfig
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager
import java.util.*

class ContextUtils(base: Context) : ContextWrapper(base) {

    companion object {
        @JvmStatic
        fun updateLocale(c: Context): ContextWrapper {
            var context = c
            val resources: Resources = context.resources
            val preferenceManager: PreferenceManager = PreferenceManager.Builder(c, MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build()
            val lang: String = preferenceManager.getValue(Constants.LANGUAGE, "fr")
            val localeToSwitchTo = Locale(lang)
            val configuration: Configuration = resources.configuration
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val localeList = LocaleList(localeToSwitchTo)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
            } else {
                configuration.locale = localeToSwitchTo
            }
            context = context.createConfigurationContext(configuration)
            return ContextUtils(context)
        }
    }
}
