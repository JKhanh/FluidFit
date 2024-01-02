package org.jkhanh.fluidfit

import App
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mmk.kmpnotifier.permission.permissionUtil

class MainActivity : ComponentActivity() {
    private val permissionUtil by permissionUtil()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
        permissionUtil.askNotificationPermission() { result ->
            if (!result) {
                Toast.makeText(this,
                    "FluidFit need to have notification permission to remind you. Please grant it",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}