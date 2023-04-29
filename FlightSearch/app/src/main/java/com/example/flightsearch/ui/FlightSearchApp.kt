package com.example.flightsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchApp(
    modifier: Modifier = Modifier,
    viewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.factory)
) {

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier.padding(8.dp)) {
        TextField(
            value = viewModel.query.value,
            onValueChange = {
                viewModel.query.value = it
                viewModel.depart.value = ""
            },
            modifier = modifier.fillMaxWidth()
        )

        Box(modifier = modifier.fillMaxSize()) {
            if (viewModel.query.value.isEmpty()) {
                val favorites by viewModel.getFavorites()
                    .collectAsState(emptyList())
                Favorites(favorites = favorites)
            } else {
                val airports by viewModel.getAutoCompleteResults(viewModel.query.value)
                    .collectAsState(emptyList())

                if (viewModel.depart.value.isEmpty()) {
                    AutoCompleteResults(
                        airports = airports,
                        onSelected = { airport ->
                            viewModel.depart.value = airport.iataCode
                        }
                    )
                } else {
                    val depart by viewModel.getAirport(viewModel.depart.value)
                        .collectAsState(initial = null)
                    val destinations by viewModel.getFlightsFrom(viewModel.depart.value)
                        .collectAsState(emptyList())
                    FlightsFrom(
                        depart = depart,
                        destinations = destinations,
                        onFavorite = { departureCode, destinationCode ->
                            coroutineScope.launch {
                                viewModel.addFavorite(departureCode, destinationCode)
                            }
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun AutoCompleteResults(
    airports: List<Airport>,
    onSelected: (Airport) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(vertical = 8.dp)) {
        items(
            items = airports,
            key = { airport -> airport.id }
        ) { airport ->
            Row(modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp)
                .clickable { onSelected(airport) }
            ) {
                Text(
                    text = airport.iataCode,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = airport.name,
                    maxLines = 1
                )
            }
        }
    }
}


@Composable
fun Favorites(
    favorites: List<Favorite>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(8.dp)
    ) {
        Text(
            text = "Favorite Routes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
        )
        LazyColumn(modifier = modifier, contentPadding = PaddingValues(vertical = 8.dp)) {
            items(
                items = favorites,
                key = { favorite -> favorite.id }
            ) { favorite ->
                Column() {
                    Row() {
                        Column {
                            Text(
                                text = "Depart",
                                fontWeight = FontWeight.Bold
                            )
                            Text(favorite.departureCode)
                        }
                    }
                    Row() {
                        Column {
                            Text(
                                text = "Arrive",
                                fontWeight = FontWeight.Bold
                            )
                            Text(favorite.destinationCode)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FlightsFrom(
    depart: Airport?,
    destinations: List<Airport>,
    onFavorite: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier
        .fillMaxSize()
        .padding(8.dp)
    ) {
        if (depart != null) {
            Text(
                text = "Flights from ${depart.iataCode}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }
        LazyColumn(modifier = modifier, contentPadding = PaddingValues(vertical = 8.dp)) {
            items(
                items = destinations,
                key = { airport -> airport.id }
            ) { airport ->
                Card(
                    modifier = modifier.padding(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(modifier = modifier.weight(3f)) {
                            Text(
                                text = "Depart",
                                fontWeight = FontWeight.Bold
                            )
                            if (depart != null) {
                                Text(
                                    text = "${depart.iataCode} - ${depart.name}",
                                    maxLines = 1
                                )
                            }
                            Text(
                                text = "Arrive",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${airport.iataCode} - ${airport.name}",
                                maxLines = 1
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "favorite",
                            modifier = modifier
                                .weight(1f)
                                .clickable {
                                    if (depart != null) {
                                        onFavorite(depart.iataCode, airport.iataCode)
                                    }
                                }
                        )
                    }
                }
            }
        }
    }
}