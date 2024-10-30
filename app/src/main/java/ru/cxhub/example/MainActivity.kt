package ru.cxhub.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import ru.cxhub.example.ui.screens.DemoScreen

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider.create(this as ViewModelStoreOwner, MainViewModel.Factory)[MainViewModel::class]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.init()

        setContent {
            DemoScreen(viewModel.holder)
        }
    }

    override fun onDestroy() {
        viewModel.dispose()
        super.onDestroy()
    }
}