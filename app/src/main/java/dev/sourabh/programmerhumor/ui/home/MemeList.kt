package dev.sourabh.programmerhumor.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import dev.sourabh.programmerhumor.R
import dev.sourabh.programmerhumor.data.model.ImageData
import dev.sourabh.programmerhumor.data.response.PostData
import dev.sourabh.programmerhumor.ui.theme.WhiteTranslucent
import dev.sourabh.programmerhumor.utils.gifImageLoader
import dev.sourabh.programmerhumor.utils.shareImage
import timber.log.Timber

@Composable
fun MemesList(memes: LazyPagingItems<PostData>, navController: NavController, viewModel: HomeViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(bottom = 56.dp)
    ) {
        items(memes) {
            if (it?.postHint == "image" && !it.stickied) {
                Meme(postData = it, navController = navController)
            }
        }

        memes.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { Loading() }
                }

                loadState.append is LoadState.Loading -> {
                    item { Loading() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = memes.loadState.refresh as LoadState.Error
                    item {
                        ExceptionMessage(e = e.error, viewModel)
                    }
                    Timber.d("refresh error $e")
                }

                loadState.append is LoadState.Error -> {
                    val e = memes.loadState.append as LoadState.Error
                    Timber.d("refresh error $e")
                }
            }
        }
    }
}

@Composable
fun Meme(postData: PostData, navController: NavController) {

    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(24.dp), elevation = 0.dp,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 16.dp)
    ) {
        Column(Modifier
            .clickable {
                Timber.d("Clicked")
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.reddit.com${postData.commentLink}")
                    )
                )
            }
            .padding(bottom = if (MaterialTheme.colors.isLight) 16.dp else 24.dp)) {

            val imageUrl = postData.url

            var isImageLoaded by remember { mutableStateOf(false) }
            var isImageSharingComplete by remember { mutableStateOf<Boolean?>(null) }

            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp).fillMaxWidth()) {
                Text(
                    text = postData.author,
                    style = MaterialTheme.typography.caption
                )
                Text(text = postData.title, style = MaterialTheme.typography.h6)
            }

            StatsAndShareIcons(postData.score, postData.numComments, imageUrl, postData.title, isImageLoaded) {
                isImageSharingComplete = it
            }

            val isGif = imageUrl.substring(imageUrl.lastIndexOf('.') + 1) == "gif"
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = (1.4).dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {

                val painter = rememberCoilPainter(
                    request = imageUrl,
                    imageLoader = gifImageLoader(context).build(),
                    fadeIn = true
                )

                if (painter.loadState is ImageLoadState.Loading) {
                    LinearProgressIndicator(modifier = Modifier.padding(8.dp).fillMaxWidth())
                    isImageLoaded = false
                }
                if (painter.loadState is ImageLoadState.Success) {
                    isImageLoaded = true
                }

                Image(
                    painter = painter,
                    contentDescription = "Meme Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp, max = 500.dp)
                        .clickable {
                            val image = ImageData(
                                postData.title, imageUrl,
                                postData.commentLink
                            )
                            navController.currentBackStackEntry?.arguments = Bundle().apply {
                                putParcelable("image_data", image)
                            }
                            navController.navigate("image_view")
                        },
                    contentScale = ContentScale.Crop,
                )
                if (isGif) {
                    Text(
                        text = "Gif",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .padding(8.dp)
                            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(16.dp))
                            .padding(4.dp)
                    )
                }

                if (isImageSharingComplete == false) {
                    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(modifier = Modifier.size(56.dp).padding(end = 8.dp))
                        Text(
                            text = "Downloading Image for sharing.", style = MaterialTheme.typography.body1,
                            modifier = Modifier.background(WhiteTranslucent, RoundedCornerShape(16.dp)).padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatsAndShareIcons(
    upvotes: Int,
    comments: Int,
    imageUrl: String,
    title: String,
    isImageLoaded: Boolean,
    onShareImageDone: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val modifier = Modifier.size(18.dp)
        Row {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 8.dp)) {
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Upvotes", modifier = modifier)
                Text(
                    text = upvotes.toString(),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 8.dp)) {
                Icon(
                    painterResource(R.drawable.ic_outline_chat_bubble_outline),
                    contentDescription = "Comments",
                    modifier = modifier
                )
                Text(
                    text = comments.toString(),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }


        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        if (isImageLoaded) {
            IconButton(onClick = {
                onShareImageDone(false)
                Toast.makeText(context, "Please wait. Loading.", Toast.LENGTH_SHORT).show()
                shareImage(coroutineScope, imageUrl, title, context) {
                    onShareImageDone(it) // Set to true when the share panel is shown
                }
            }) {
                Icon(Icons.Outlined.Share, contentDescription = "Share", modifier)
            }
        } else {
            Spacer(modifier = modifier.padding(vertical = 8.dp))
        }

    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxWidth()) {
        CircularProgressIndicator(
            Modifier.padding(24.dp).align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun ExceptionMessage(e: Throwable, viewModel: HomeViewModel) {
    Timber.e(e)
    val modifier = Modifier.padding(end = 4.dp)
    Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Column {
            Text(text = "I use Arch BTW!", Modifier.padding(bottom = 8.dp), style = MaterialTheme.typography.caption)
            
            Row {
                Text(">> $", style = MaterialTheme.typography.body1, modifier = modifier)
                Text(
                    text = e.cause.toString(),
                    style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold
                )
            }
            Row {
                Text(">> $", style = MaterialTheme.typography.body1, modifier = modifier)
                Text(
                    text = e.message.toString(), style = MaterialTheme.typography.body1,
                )
            }

            Button(
                onClick = { viewModel.getPosts() },
                modifier = Modifier.padding(24.dp).align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(text = "Retry")
            }
        }
    }
}