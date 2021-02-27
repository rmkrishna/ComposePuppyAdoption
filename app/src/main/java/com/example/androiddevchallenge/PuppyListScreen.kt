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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.data.model.Puppy
import com.example.androiddevchallenge.data.puppies
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun HomeScreen(
    puppies: List<Puppy>,
    detailNavigator: (Puppy) -> Unit
) {
    val list = puppies
    var searchState by remember { mutableStateOf(TextFieldValue("")) }

    var textFieldFocusState by remember { mutableStateOf(false) }

    Column {
        HomeScreenAppBar(
            searchState,
            onTextChanged = {
                searchState = it
            },
            onTextFieldFocused = { focused ->
                textFieldFocusState = focused
            },
            focusState = textFieldFocusState
        )
        PuppyListView(list, detailNavigator)
    }
}

@Composable
private fun HomeScreenAppBar(
    textFieldValue: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    onTextFieldFocused: (Boolean) -> Unit,
    focusState: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            var lastFocusState by remember { mutableStateOf(FocusState.Inactive) }

            BasicTextField(
                value = textFieldValue,
                onValueChange = onTextChanged,
                singleLine = true,
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .onFocusChanged { state ->
                        if (lastFocusState != state) {
                            onTextFieldFocused(state == FocusState.Active)
                        }
                        lastFocusState = state
                    },
            )

            val disableContentColor =
                MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
            if (textFieldValue.text.isEmpty() && !focusState) {
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .align(Alignment.CenterStart),
                    text = "Search..",
                    style = MaterialTheme.typography.body1.copy(color = disableContentColor)
                )
            }
        }
    }
}

@Composable
private fun PuppyListView(
    puppies: List<Puppy>,
    detailNavigator: (Puppy) -> Unit
) {
    LazyColumn(
        content = {
            items(puppies) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .clickable(onClick = { detailNavigator(it) })
                ) {
                    PuppyRowItem(
                        it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                            .background(
                                MaterialTheme.colors.surface,
                                shape = RoundedCornerShape(8.dp)
                            )
                    )
                }
            }
        }
    )
}

@Composable
fun PuppyRowItem(puppy: Puppy, modifier: Modifier) {
    Row(modifier = modifier.padding(12.dp)) {
        GlideImage(
            data = puppy.photoUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(5.dp))
        )

        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            Text(
                text = puppy.name,
                style = MaterialTheme.typography.h6,
            )

            Spacer(modifier = Modifier.size(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    tint = Color.LightGray,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = puppy.location,
                    style = MaterialTheme.typography.caption.copy(fontSize = 14.sp),
                    modifier = Modifier.alpha(0.6f)
                )
            }

            Spacer(modifier = Modifier.size(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = "Origin: ",
                    style = MaterialTheme.typography.caption
                )

                Text(
                    text = puppy.origin,
                    style = MaterialTheme.typography.caption.copy(fontSize = 14.sp),
                    modifier = Modifier.alpha(0.6f)
                )
            }

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = "${puppy.gender} | ${puppy.age}",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(puppies = puppies, detailNavigator = { /*TODO*/ })
}

@Composable
@Preview
fun PuppyListItemPreview() {
    PuppyRowItem(
        puppy = puppies[0],
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(8.dp))
    )
}
