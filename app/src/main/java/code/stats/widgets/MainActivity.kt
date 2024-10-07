package code.stats.widgets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.lifecycleScope
import code.stats.analytics.AppLogger
import code.stats.widget.totalxp.TotalXpWidget
import code.stats.widgets.ui.theme.CodeStatsWidgetsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPrefs = getSharedPreferences("Prefs", MODE_PRIVATE)
        val username = sharedPrefs.getString("username", null) ?: ""

        setContent {
            CodeStatsWidgetsTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    AppContent(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(innerPadding),
                        username = username,
                    ) {
                        sharedPrefs.edit {
                            putString("username", it)
                            lifecycleScope.launch { TotalXpWidget().updateAll(this@MainActivity) }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppContent(modifier: Modifier, username: String, save: (String) -> Unit) {
    val (usernameText, onChangeUsername) = remember(username) {
        mutableStateOf(username)
    }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter your nickname on the website Code::Stats",
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium
        )

        TextField(modifier = Modifier.fillMaxWidth(),
            value = usernameText,
            onValueChange = onChangeUsername,
            label = { Text("Username") }
        )

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            save(usernameText)
        }) {
            Text("Save")
        }
    }

}