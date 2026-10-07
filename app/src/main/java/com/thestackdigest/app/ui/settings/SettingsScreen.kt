package com.thestackdigest.app.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.SettingsBrightness
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thestackdigest.app.ui.theme.TheStackDigestTheme

@Composable
fun SettingsRoute(
    paddingValues: PaddingValues,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val appTheme =
        viewModel.appTheme.collectAsStateWithLifecycle()

    SettingsScreen(
        paddingValues = paddingValues,
        selectedTheme = appTheme.value,
        onThemeSelected = viewModel::onThemeSelected
    )
}

@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            SectionTitle(
                title = "APPEARANCE"
            )
        }

        item {
            ThemeSettingsCard(
                selectedTheme = selectedTheme,
                onThemeSelected = onThemeSelected
            )
        }

        item {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            SectionTitle(
                title = "ABOUT"
            )
        }

        item {
            AboutCard()
        }
    }
}

@Composable
private fun SectionTitle(
    title: String
) {

    Text(
        text = title,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun ThemeSettingsCard(
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surfaceContainer
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector =
                        Icons.Outlined.SettingsBrightness,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Column(
                    modifier = Modifier.padding(
                        start = 16.dp
                    )
                ) {

                    Text(
                        text = "App theme",
                        style =
                            MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )

                    Text(
                        text = "Choose how the app looks",
                        style =
                            MaterialTheme.typography.bodyMedium,
                        color =
                            MaterialTheme.colorScheme
                                .onSurfaceVariant
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                ThemeOptionButton(
                    title = "System",
                    icon = Icons.Outlined.SettingsBrightness,
                    selected =
                        selectedTheme == AppTheme.SYSTEM,
                    onClick = {
                        onThemeSelected(
                            AppTheme.SYSTEM
                        )
                    },
                    modifier = Modifier.weight(1f)
                )

                ThemeOptionButton(
                    title = "Light",
                    icon = Icons.Outlined.LightMode,
                    selected =
                        selectedTheme == AppTheme.LIGHT,
                    onClick = {
                        onThemeSelected(
                            AppTheme.LIGHT
                        )
                    },
                    modifier = Modifier.weight(1f)
                )

                ThemeOptionButton(
                    title = "Dark",
                    icon = Icons.Outlined.DarkMode,
                    selected =
                        selectedTheme == AppTheme.DARK,
                    onClick = {
                        onThemeSelected(
                            AppTheme.DARK
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ThemeOptionButton(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val containerColor =
        if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        }

    val contentColor =
        if (selected) {
            MaterialTheme.colorScheme.onPrimaryContainer
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }

    Surface(
        modifier = modifier.clickable(
            onClick = onClick
        ),
        shape = RoundedCornerShape(14.dp),
        color = containerColor
    ) {

        Column(
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 6.dp
            ),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = title,
                style =
                    MaterialTheme.typography.labelMedium,
                color = contentColor,
                fontWeight =
                    if (selected) {
                        FontWeight.SemiBold
                    } else {
                        FontWeight.Normal
                    }
            )
        }
    }
}

@Composable
private fun AboutCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surfaceContainer
        )
    ) {

        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Column(
                modifier = Modifier.padding(
                    start = 16.dp
                )
            ) {

                Text(
                    text = "The Stack Digest",
                    style =
                        MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Version 1.0",
                    style =
                        MaterialTheme.typography.bodyMedium,
                    color =
                        MaterialTheme.colorScheme
                            .onSurfaceVariant
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun SettingsScreenPreview() {

    TheStackDigestTheme {

        SettingsScreen(
            paddingValues = PaddingValues(),
            selectedTheme = AppTheme.SYSTEM,
            onThemeSelected = {}
        )
    }
}