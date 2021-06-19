package dev.sourabh.programmerhumor.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import timber.log.Timber

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val memes = viewModel.postsFlow.collectAsLazyPagingItems()
    Scaffold(topBar = {
        AppBar(modifier = modifier, viewModel = viewModel)
    }) {
        MemesList(memes, navController)
    }
}

@Composable
fun AppBar(modifier: Modifier, viewModel: HomeViewModel) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "ProgrammerHumor", style = MaterialTheme.typography.h5)
        Sort(viewModel)
    }
}

@Composable
fun Sort(viewModel: HomeViewModel) {
    val postFilterList = listOf("Hot", "Top", "New")
    var selected by rememberSaveable { mutableStateOf(postFilterList[0]) }
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.wrapContentSize(),
        elevation = 2.dp,
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp, color = if (!expanded)
                MaterialTheme.colors.primary
            else
                Color.Gray
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 6.dp)
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = selected, style = MaterialTheme.typography.body1) // Current post filter type
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                postFilterList.forEach { label ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        if (selected != label)
                            Timber.d("Selected $label")
                        viewModel.getPosts(sort = label)
                        selected = label
                    }) {
                        Text(text = label)
                    }
                }
            }
        }
    }
}