package com.example.firstkmp.presentation.fonts

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import firstkmp.shared.generated.resources.Res
import firstkmp.shared.generated.resources.RobotoMono_Bold
import firstkmp.shared.generated.resources.RobotoMono_BoldItalic
import firstkmp.shared.generated.resources.RobotoMono_Italic
import firstkmp.shared.generated.resources.RobotoMono_Regular
import org.jetbrains.compose.resources.Font

@Composable
fun getCustomFontFamily() = FontFamily(
    Font(Res.font.RobotoMono_Regular, FontWeight.Normal),
    Font(Res.font.RobotoMono_Bold, FontWeight.Bold),
    Font(Res.font.RobotoMono_BoldItalic, FontWeight.SemiBold),
    Font(Res.font.RobotoMono_Italic, FontWeight.Light),
)
