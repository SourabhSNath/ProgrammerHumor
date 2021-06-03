package dev.sourabh.programmerhumor.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Title(modifier)
}

@Composable
fun Title(modifier: Modifier) {
    Text(
        text = "Programmer\r\nHumor;", style = MaterialTheme.typography.h4,
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxWidth()
    )
}

@Composable
fun Meme() {

}