package com.salanevich.githubapp.ui.screen.main

import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.util.LinkifyCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.salanevich.githubapp.R
import com.salanevich.githubapp.domain.model.UserDomain
import com.salanevich.githubapp.ui.theme.Shapes
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MainScreen(navigateToDetail: (login: String) -> Unit) {
    val viewModel: MainViewModel = koinViewModel()
    val state = viewModel.collectAsState()

    MainScreen(
        state = state,
        navigateToDetail = viewModel::reduce,
        searchAction = viewModel::reduce
    )

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is MainSideEffect.NavigateToDetail -> navigateToDetail(sideEffect.login)
        }
    }
}

@Composable
private fun MainScreen(
    state: State<MainState>,
    navigateToDetail: (MainAction.GoToDetail) -> Unit,
    searchAction: (MainAction.Search) -> Unit
) {
    Column {
        ProgressIndicator(
            isProgressShown = state.value.loading
        )
        if (!state.value.loading) {
            var text by remember { mutableStateOf(state.value.query) }
            val action = remember {
                { v: String ->
                    text = v
                    searchAction(MainAction.Search(v))
                }
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                value = text,
                label = { Text(text = stringResource(R.string.search)) },
                onValueChange = action
            )
        }
        if (!state.value.loading && state.value.filteredUsers.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "There are no users.")
            }
        }
        LazyColumn {
            items(state.value.filteredUsers.size, key = { state.value.filteredUsers[it].id }) { id ->
                ListItem(user = state.value.filteredUsers[id], navigateToDetail)
            }
        }
    }
}

@Composable
private fun ListItem(user: UserDomain, navigateToDetail: (MainAction.GoToDetail) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clip(Shapes.medium)
            .padding(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatarUrl)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.no_image_placeholder)
            )
            Column(
                modifier = Modifier
                    .height(200.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = stringResource(R.string.user_name, user.login))
                val context = LocalContext.current
                val linkifyText = remember { TextView(context) }
                AndroidView(factory = { linkifyText }) { textView ->
                    textView.text = context.getString(R.string.user_page, user.webSite)
                    textView.textSize = 14F
                    LinkifyCompat.addLinks(textView, Linkify.WEB_URLS)
                    textView.movementMethod = LinkMovementMethod.getInstance()
                }
                val navigationAction = remember {
                    { navigateToDetail(MainAction.GoToDetail(user.login)) }
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), contentAlignment = Alignment.BottomEnd) {
                    TextButton(onClick = navigationAction) {
                        Text(text = stringResource(R.string.more))
                    }
                }
            }
        }
    }
}

@Composable
private fun ProgressIndicator(
    modifier: Modifier = Modifier,
    isProgressShown: Boolean
) {
    if (isProgressShown) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = modifier
                    .width(64.dp)
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    Surface {
        MainScreen(state = object : State<MainState> {
            val users = listOf(
                UserDomain(
                    id = 1,
                    login = "username",
                    avatarUrl = "",
                    webSite = "https://www.web-site.com"
                )
            )
            override val value: MainState
                get() = MainState(users = users, filteredUsers = users, loading = false)
        }, {}, {})
    }
}