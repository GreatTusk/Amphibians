package com.f776.amphibians.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.f776.amphibians.R
import com.f776.amphibians.model.Amphibian
import com.f776.amphibians.model.title
import com.f776.amphibians.ui.theme.AmphibiansTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    amphibianUiState: AmphibianUiState,
    onReload: () -> Unit
) {
    when (amphibianUiState) {
        AmphibianUiState.Error -> ErrorScreen(
            modifier = modifier.fillMaxSize(),
            onReload = onReload
        )

        AmphibianUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AmphibianUiState.Success -> AmphibianCardList(
            modifier = modifier,
            amphibians = amphibianUiState.amphibians
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(dimensionResource(R.dimen.icon_size))
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, onReload: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = stringResource(R.string.error),
            modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
        )
        Text(
            text = stringResource(R.string.error),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding))
        )
        Button(onClick = onReload) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
private fun AmphibianCardList(
    modifier: Modifier = Modifier,
    amphibians: List<Amphibian>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.card_separation)),
        contentPadding = PaddingValues(
            bottom = dimensionResource(R.dimen.medium_padding)
        )
    ) {
        items(amphibians) {
            AmphibianCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.card_padding)),
                amphibian = it
            )
        }
    }
}


@Composable
private fun AmphibianCard(modifier: Modifier = Modifier, amphibian: Amphibian) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.card_elevation))
    ) {
        Column {
            Text(
                text = amphibian.title, style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding))
            )
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(amphibian.imgSrc)
                        .crossfade(true)
                        .build(),
                    contentDescription = amphibian.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .sizeIn(maxHeight = dimensionResource(R.dimen.card_image_size))
                        .fillMaxWidth()
                )
            }
            Text(
                text = amphibian.description, style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AmphibianCardPreview() {
    AmphibiansTheme {
        AmphibianCard(
            amphibian = Amphibian(
                name = "Great Basin Spadefoot",
                type = "Toad",
                description = "This toad spends most of its life underground due to the arid desert conditions in which it lives. Spadefoot toads earn the name because of their hind legs which are wedged to aid in digging. They are typically grey, green, or brown with dark spots.",
                imgSrc = "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/great-basin-spadefoot.png"
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.card_padding)),
        )
    }

}

