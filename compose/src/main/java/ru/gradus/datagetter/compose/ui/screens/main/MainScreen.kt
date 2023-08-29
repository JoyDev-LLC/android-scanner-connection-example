package ru.gradus.datagetter.compose.ui.screens.main

import android.view.InputDevice
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.gradus.datagetter.R
import ru.gradus.datagetter.compose.models.screen.BaseScreen
import ru.gradus.datagetter.compose.models.screen.Screen
import ru.gradus.datagetter.utils.getSources

@Composable
fun MainScreen(navController: NavController) {
    BaseScreen {
        val inputDevices = InputDevice.getDeviceIds().map { InputDevice.getDevice(it) }
        LazyColumn {
            item {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.devices),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(inputDevices) { inputDevice ->
                DeviceItem(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Screen.Details.getRoute(inputDevice.id))
                        },
                    item = inputDevice
                )
            }
        }
    }
}

@Composable
fun DeviceItem(modifier: Modifier = Modifier, item: InputDevice) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, RoundedCornerShape(16.dp))
            .padding(8.dp)
            .then(modifier)

    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id =R.string.device_name, item.name),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.device_id, item.id),
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.width(32.dp))
            Text(
                text = stringResource(id = R.string.device_sources, getSources(item.sources)),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}
