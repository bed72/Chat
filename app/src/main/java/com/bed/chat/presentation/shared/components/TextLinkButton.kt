package com.bed.chat.presentation.shared.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.LinkInteractionListener

@Composable
fun TextLinkButton(
    @StringRes text: Int,
    @StringRes link: Int,
    click:  LinkInteractionListener,
    modifier: Modifier = Modifier
) {
    val first = stringResource(text)
    val second = stringResource(link)
    val both = "$first $second"

    val annotated = buildAnnotatedString {
        val startIndex = both.indexOf(second)
        val endIndex = startIndex + second.length

        append(both)
        addStyle(
            start = 0,
            end = startIndex,
            style = SpanStyle(
                color = MaterialTheme.colorScheme.outline
            )
        )

        addLink(
            end = endIndex,
            start = startIndex,
            clickable = LinkAnnotation.Clickable(
                tag = "link_button",
                linkInteractionListener = click,
                styles = TextLinkStyles(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ),
            )
        )
    }

    Text(annotated, modifier = modifier)
}
