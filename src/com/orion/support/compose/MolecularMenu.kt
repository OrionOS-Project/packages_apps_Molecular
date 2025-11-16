package com.orion.support.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.android.settings.R
import com.orion.support.fragments.Miscellaneous
import com.orion.support.fragments.Notifications
import com.orion.support.fragments.Statusbar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuSection() {
    Column{
        Spacer(modifier = Modifier.height(12.dp))

        MenuItem("top", "Statusbar", "Bar status here..")
        MenuItem("middle", "Notification", "Is it about notice you?")
        MenuItem("middle", "Gestures", "I know your gesture.")
        MenuItem("bottom", "Miscellaneous", "Like store, anything here, maybe..")
    }
}

@Composable
fun MenuItem(position: String, title: String, subtitle: String) {
    val context = LocalContext.current
    
    val fragment: Fragment? = when (title) {
        "Statusbar" -> Statusbar()
        "Notification" -> Notifications()
        "Gestures" -> null // Gestures() - Belum ada fragmentnya
        "Miscellaneous" -> Miscellaneous()
        else -> null
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp, horizontal = 16.dp)
            .clip(
                when (position) {
                    "top" -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 3.dp, bottomEnd = 3.dp)
                    "bottom" -> RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
                    else -> RoundedCornerShape(3.dp)
                }
            )
            .background(MaterialTheme.colorScheme.primary)
            .clickable { 
                fragment?.let { frag ->
                    (context as? FragmentActivity)?.let { activity ->
                        activity.supportFragmentManager.beginTransaction()
                            .replace(R.id.main_content, frag)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }
}