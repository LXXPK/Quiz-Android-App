package com.example.smartquiz.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestsSection(
    allInterests: List<String>,
    selected: List<String>,
    isEditMode: Boolean,
    expand: Boolean,
    maxSelection: Int,
    onEdit: () -> Unit,
    onExpandToggle: () -> Unit,
    onClearAll: () -> Unit,
    onSave: () -> Unit,
    onToggleInterest: (String) -> Unit
) {
    Column {


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Your Interests",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${selected.size}/$maxSelection",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(14.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(18.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (isEditMode) "Select up to $maxSelection"
                        else "Selected interests",
                        style = MaterialTheme.typography.titleSmall
                    )

                    if (isEditMode) {
                        IconButton(onClick = onExpandToggle) {
                            Icon(Icons.Default.KeyboardArrowDown, null)
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Read mode
                if (!isEditMode) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        selected.forEach {
                            InterestChip(label = it, selected = true)
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    TextButton(onClick = onEdit) {
                        Text("Edit interests")
                    }
                }


                if (isEditMode && expand) {
                    Column(
                        modifier = Modifier
                            .heightIn(max = 240.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        FlowRow(
                            maxItemsInEachRow = 3,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            allInterests.forEach { interest ->
                                InterestChip(
                                    label = interest,
                                    selected = interest in selected,
                                    disabled =
                                        interest !in selected &&
                                                selected.size >= maxSelection,
                                    onClick = { onToggleInterest(interest) }
                                )
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        Button(
                            onClick = onSave,
                            enabled = selected.isNotEmpty(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.large
                        ) {
                            Text("Save Preferences")
                        }

                        if (selected.isNotEmpty()) {
                            TextButton(
                                onClick = onClearAll,
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text(
                                    "Clear all",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
