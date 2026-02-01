package com.example.smartquiz.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.smartquiz.R

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
                text = stringResource(id = R.string.label_your_interests),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(id = R.string.label_interests_selected, selected.size, maxSelection),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_small)))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(Modifier.padding(dimensionResource(id = R.dimen.medium_padding))) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (isEditMode) stringResource(id = R.string.label_select_interests) else stringResource(id = R.string.label_your_interests),
                        style = MaterialTheme.typography.titleSmall
                    )

                    if (isEditMode) {
                        Row {
                            if (selected.isNotEmpty()) {
                                TextButton(onClick = onClearAll) {
                                    Text(
                                        text = stringResource(id = R.string.action_clear),
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

                Spacer(Modifier.height(dimensionResource(id = R.dimen.small_padding)))

                if (!isEditMode) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_small)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_small))
                    ) {
                        selected.forEach {
                            InterestChip(label = it, selected = true)
                        }
                    }

                    TextButton(onClick = onEdit) {
                        Text(stringResource(id = R.string.action_edit_interests))
                    }
                }

                if (isEditMode && expand) {
                    Column(
                        modifier = Modifier
                            .heightIn(max = 220.dp) // Specific UI constraint, maybe move to dimens if repeated
                            .verticalScroll(rememberScrollState())
                    ) {
                        FlowRow(
                            maxItemsInEachRow = 3,
                            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_small)),
                            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_small))
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

                        Spacer(Modifier.height(dimensionResource(id = R.dimen.medium_padding)))

                        Button(
                            onClick = onSave,
                            enabled = selected.isNotEmpty(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(id = R.string.action_save_profile))
                        }
                    }
                }
            }
        }
    }
}
