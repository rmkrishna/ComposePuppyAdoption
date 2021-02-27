/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.model.Puppy
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun DetailScreen(
    puppy: Puppy,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            DetailAppBar(puppy, navigateBack)
        },
        content = {
            DetailContent(puppy)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Adopt Now") },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_pets),
                        contentDescription = null
                    )
                },
                onClick = { },
            )
        }
    )
}

@Composable
private fun DetailAppBar(
    puppy: Puppy,
    navigateBack: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        title = {
            Text(text = puppy.name)
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        navigateBack.invoke()
                    }
            )
        },
    )
}

@Composable
private fun DetailContent(puppy: Puppy) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        puppyDetail(puppy)
        Spacer(modifier = Modifier.size(8.dp))
        ownerDetail(puppy = puppy)

        Spacer(modifier = Modifier.size(8.dp))

        puppyDescription(puppy)
    }
}

@Composable
private fun puppyDescription(puppy: Puppy) {
    Column(modifier = Modifier.padding(12.dp)) {
        Text(
            text = "Details",
            style = MaterialTheme.typography.h5,
        )

        Text(
            text = puppy.details,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.alpha(0.75f)
        )
    }
}

@Composable
private fun puppyDetail(puppy: Puppy) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Column {
            GlideImage(
                data = puppy.photo,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = puppy.name,
                    style = MaterialTheme.typography.h5,
                )

                Spacer(
                    modifier = Modifier.size(8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Origin: ",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.alpha(0.6f)
                    )

                    Text(
                        text = puppy.origin,
                        style = MaterialTheme.typography.subtitle1
                    )
                }

                Spacer(
                    modifier = Modifier.size(6.dp)
                )

                Text(
                    text = "${puppy.gender} | ${puppy.age}",
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}

@Composable
private fun ownerDetail(puppy: Puppy) {
    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_bussiness_man),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(70.dp)
            )

            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = puppy.ownerName,
                    style = MaterialTheme.typography.h6,
                )

                Spacer(
                    modifier = Modifier.size(2.dp)
                )

                Text(
                    text = "Owner",
                    style = MaterialTheme.typography.caption,
                )

                Spacer(
                    modifier = Modifier.size(4.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        tint = Color.LightGray,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = puppy.location,
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier
                            .alpha(0.6f)
                            .padding(start = 4.dp)
                    )
                }
            }
        }
    }
}
