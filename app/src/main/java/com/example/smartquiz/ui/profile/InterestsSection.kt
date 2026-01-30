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
            Text("Your Interests", style = MaterialTheme.typography.titleMedium)
            Text(
                "${selected.size}/$maxSelection selected",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(Modifier.padding(16.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        if (isEditMode) "Select interests" else "Your interests",
                        style = MaterialTheme.typography.titleSmall
                    )

                    if (isEditMode) {
                        Row {
                            if (selected.isNotEmpty()) {
                                TextButton(onClick = onClearAll) {
                                    Text(
                                        "Clear",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                            IconButton(onClick = onExpandToggle) {
                                Icon(Icons.Default.KeyboardArrowDown, null)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                if (!isEditMode) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        selected.forEach {
                            InterestChip(label = it, selected = true)
                        }
                    }

                    TextButton(onClick = onEdit) {
                        Text("Edit interests")
                    }
                }

                if (isEditMode && expand) {
                    Column(
                        modifier = Modifier
                            .heightIn(max = 220.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        FlowRow(
                            maxItemsInEachRow = 3,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
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

                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = onSave,
                            enabled = selected.isNotEmpty(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save Profile")
                        }
                    }
                }
            }
        }
    }
}
