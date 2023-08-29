package ru.gradus.datagetter.compose.ui.screens.details

import android.view.InputDevice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.gradus.datagetter.R
import ru.gradus.datagetter.compose.models.screen.BaseScreen
import ru.gradus.datagetter.utils.Decoder
import ru.gradus.datagetter.utils.getSources

@Composable
fun Details(navController: NavController, deviceId: Int) {
    val requester = FocusRequester()
    BaseScreen {
        val decoder = remember {
            Decoder.Scanner(deviceId, NativeKeyEvent.KEYCODE_DPAD_DOWN)
        }
        var receivedData by remember {
            mutableStateOf("")
        }
        Column {
            Header(
                inputDevice = InputDevice.getDevice(deviceId),
                onBackPressed = { navController.popBackStack() }
            )
            ReceivedData(
                modifier = Modifier
                    .focusRequester(requester)
                    .focusable(true)
                    .onKeyEvent { event ->
                        if (event.type == KeyEventType.KeyDown) {
                            receivedData = decoder.handleKey(keyEvent = event.nativeKeyEvent) ?: ""
                        }
                        true
                    },
                data = receivedData
            ) {
                (decoder as? Decoder.Keyboard)?.clearTemp()
                receivedData = ""
            }
        }
    }
    SideEffect {
        requester.requestFocus()
    }

}

@Composable
fun Header(modifier: Modifier = Modifier, inputDevice: InputDevice, onBackPressed: () -> Unit) {
    Row {
        Icon(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .clickable { onBackPressed() },
            painter = painterResource(id = com.google.android.material.R.drawable.ic_arrow_back_black_24),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 8.dp, end = 16.dp)
                .then(modifier)
        ) {
            Row {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.device_name, inputDevice.name),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = ru.gradus.datagetter.R.string.device_id, inputDevice.id),
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.width(32.dp))
                Text(
                    text = stringResource(
                        id = ru.gradus.datagetter.R.string.device_sources,
                        getSources(inputDevice.sources)
                    ), style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
fun ReceivedData(modifier: Modifier = Modifier, data: String, onClickListener: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .then(modifier)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickListener() },
            text = stringResource(id = R.string.got_data),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = data,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
