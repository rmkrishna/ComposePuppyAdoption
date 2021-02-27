package com.example.androiddevchallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
@Preview
fun HomeScreenPreview() {
    HomeScreen(puppies = puppies, detailNavigator = { /*TODO*/ })
}

@Composable
fun HomeScreen(
    puppies: List<Puppy>,
    detailNavigator: (Puppy) -> Unit
) {
    val list = puppies
    var searchState by remember { mutableStateOf(TextFieldValue()) }

    Column {
        HomeScreenAppBar(searchState, onTextChanged = {
            searchState = it
        })
        PuppyListView(list)
    }
}

@Composable
private fun HomeScreenAppBar(
    textFieldValue: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit
) {
    Box(modifier = Modifier.padding(horizontal = 2.dp, vertical = 4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 8.dp)
                .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    .weight(1f)
                    .fillMaxWidth(),
            )
        }
    }


}

@Composable
private fun PuppyListView(puppies: List<Puppy>) {
    LazyColumn(content = {
        items(puppies) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clickable(onClick = {})
            ) {
                PuppyRowItem(
                    it, modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                        .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(8.dp))
                )
            }
        }
    })
}

@Composable
@Preview
fun PuppyListItemPreview() {
    PuppyRowItem(
        puppy = puppies[0], modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(8.dp))
    )
}

@Composable
fun PuppyRowItem(puppy: Puppy, modifier: Modifier) {
    Row(modifier = modifier.padding(12.dp)) {
        GlideImage(
            data = puppy.photo,
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
