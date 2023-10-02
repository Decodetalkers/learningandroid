package org.pop.pip.ui.components

import android.icu.text.DateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import org.pop.pip.aur.AurResult

val LEFT_WIDTH = 95.dp
val LEFT_WIDTH_FULL = 125.dp

fun Long.toDateString(dateFormat: Int = DateFormat.LONG): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this * 1000)
}

@Composable
fun AurResultFullCard(modifier: Modifier = Modifier, data: AurResult) {
    val uriHandler = LocalUriHandler.current
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                    text = "ID:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(LEFT_WIDTH_FULL)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = data.ID.toString())
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row {
            Text(
                    text = "Description:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(LEFT_WIDTH_FULL)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = data.Description ?: "")
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                    text = "Maintainer:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(LEFT_WIDTH_FULL)
            )
            Spacer(modifier = Modifier.width(4.dp))

            Text(text = data.Maintainer ?: "")
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                    text = "Url:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(LEFT_WIDTH_FULL)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                    modifier =
                            Modifier.clickable done@{
                                if (data.URL == null) return@done

                                uriHandler.openUri(data.URL)
                            },
                    text = data.URL ?: "No Url Founded",
                    textDecoration = TextDecoration.Underline,
                    color = Color(0xff64B5F6)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                    text = "PackageBase:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(LEFT_WIDTH_FULL)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = data.PackageBase)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                    text = "PackageBaseID:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(LEFT_WIDTH_FULL)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = data.PackageBaseID.toString())
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                    text = "NumVotes:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(LEFT_WIDTH_FULL)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = data.NumVotes.toString())
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                    text = "Popularity:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(LEFT_WIDTH_FULL)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = data.Popularity.toString())
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                    text = "Version:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(LEFT_WIDTH_FULL)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = data.Version)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                    text = "FirstSubmitted:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(LEFT_WIDTH_FULL)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = data.FirstSubmitted.toDateString())
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                    text = "LastModified:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(LEFT_WIDTH_FULL)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = data.LastModified.toDateString())
        }
        if (data.OutOfDate != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                        text = "OutOfDate:",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.width(LEFT_WIDTH_FULL)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = data.OutOfDate.toDateString())
            }
        }
    }
}

@Composable
fun AurResultCard(modifier: Modifier = Modifier, data: AurResult) {
    val uriHandler = LocalUriHandler.current
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(all = 8.dp).fillMaxSize()) {
            Text(
                    fontSize = 25.sp,
                    text = data.Name,
                    color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row {
                Text(
                        text = "Description:",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.width(LEFT_WIDTH)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = data.Description ?: "")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                        text = "Maintainer:",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.width(LEFT_WIDTH)
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(text = data.Maintainer ?: "")
            }

            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                        text = "Version:",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.width(LEFT_WIDTH)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = data.Version)
            }

            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                        text = "Url:",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.width(LEFT_WIDTH)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                        modifier =
                                Modifier.clickable done@{
                                    if (data.URL == null) return@done

                                    uriHandler.openUri(data.URL)
                                },
                        text = data.URL ?: "No Url Founded",
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xff64B5F6)
                )
            }

            if (data.OutOfDate != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                            text = "OutOfDate:",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.width(LEFT_WIDTH)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = data.OutOfDate.toDateString())
                }
            }
        }
    }
}

@Composable
fun AurCardError(modifier: Modifier = Modifier, err: String, textAlign: TextAlign? = null) {
    Text(modifier = modifier, text = err, textAlign = textAlign)
}
