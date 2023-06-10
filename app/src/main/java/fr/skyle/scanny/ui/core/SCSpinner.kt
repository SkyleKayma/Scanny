package fr.skyle.scanny.ui.core

import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import fr.skyle.scanny.ext.debounceClickable

@Composable
fun <T> SCSpinner(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    selectedItemFactory: @Composable (Modifier, T) -> Unit,
    dropdownItemFactory: @Composable (T, Int) -> Unit,
    modifier: Modifier = Modifier,
    dropDownModifier: Modifier = Modifier
) {
    var expanded: Boolean by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        selectedItemFactory(
            Modifier.debounceClickable { expanded = true },
            selectedItem
        )

        DropdownMenu(
            modifier = dropDownModifier,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, element ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(items[index])
                        expanded = false
                    }
                ) {
                    dropdownItemFactory(element, index)
                }
            }
        }
    }
}

//@Composable
//@Preview
//fun PreviewMontvelSpinner() {
//    SCTheme {
//        SCSpinner(
//            items = stringArrayResource(id = R.array.dev_board_state_forced_mode_list).toList(),
//            selectedItem = stringArrayResource(id = R.array.dev_board_state_forced_mode_list).toList().first(),
//            onItemSelected = {  },
//            selectedItemFactory = { modifier, item ->
//                Row(
//                    modifier = modifier
//                        .padding(8.dp)
//                        .wrapContentHeight()
//                ) {
//                    Text(
//                        modifier = Modifier
//                            .weight(1f)
//                            .wrapContentHeight(),
//                        text = item
//                    )
//
//                    Icon(
//                        imageVector = Icons.Filled.ArrowDropDown,
//                        contentDescription = ""
//                    )
//                }
//            },
//            dropdownItemFactory = { item, _ ->
//                Text(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight(),
//                    text = item
//                )
//            }
//        )
//    }
//}