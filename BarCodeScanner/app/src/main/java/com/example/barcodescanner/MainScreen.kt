package com.example.barcodescanner

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen() {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    var scannedBarcode by remember { mutableStateOf<String?>(null) } // State for scanned barcode
    var isShowDialog by remember { mutableStateOf(false) }

    if (cameraPermissionState.status.isGranted) {
        CameraScreen{data->
            scannedBarcode = data
            isShowDialog=true
        }


        scannedBarcode?.let { barcode ->
            if (isShowDialog){
                CustomDialogScreen(
                    onDismissRequest = { scannedBarcode = null
                        isShowDialog=false
                    }, // Reset state on dismiss
                    onConfirmationRequest = { isShowDialog=false },
                    productId = barcode,
                )
            }

        }

    } else if (cameraPermissionState.status.shouldShowRationale) {
        Text("Camera Permission permanently denied")
    } else {
        SideEffect {
            /*Since we can’t request the permission during composition as it would cause a crash,
             we need to request the permission in a SideEffect. SideEffect schedules the permission request
             so that the permission is requested after a successful composition.*/
            cameraPermissionState.run { launchPermissionRequest() }
        }
        Text("No Camera Permission")
    }
}