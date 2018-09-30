package com.kovapps.kovalev.psytests.markdown

import android.content.Context
import ru.noties.markwon.SpannableConfiguration
import ru.noties.markwon.spans.SpannableTheme
import ru.noties.markwon.view.IMarkwonView


class MdConfigProvider : IMarkwonView.ConfigurationProvider {
    override fun provide(context: Context): SpannableConfiguration {
        val theme = SpannableTheme.builder()
                .codeTextSize(25)
                .build()
        return SpannableConfiguration.builder(context)
                .theme(theme)
                .build()
    }
}