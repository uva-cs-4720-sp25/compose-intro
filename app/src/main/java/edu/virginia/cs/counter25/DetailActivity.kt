package edu.virginia.cs.counter25

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import edu.virginia.cs.counter25.model.CounterDatabase
import edu.virginia.cs.counter25.ui.theme.Counter25Theme
import edu.virginia.cs.counter25.views.CounterDetail

class DetailActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            CounterDatabase::class.java,
            "counters.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val counterId = intent.getLongExtra("id", -1L) // Retrieve the passed ID
        println("DetailActivity:, Received ID: $counterId")
        enableEdgeToEdge()
        setContent {
            Counter25Theme {
                val viewModel by viewModels<DetailViewModel>(
                    factoryProducer = {
                        object: ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return DetailViewModel(counterId, database.counterDao()) as T
                            }
                        }
                    }
                )

                val counter by viewModel.counterState.collectAsState()
                Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        CounterDetail(
                            counter = counter,
                            onNameSave = { newName -> viewModel.renameCounter(newName) },
                            onFinish = {
                                setResult(Activity.RESULT_OK)
                                finish()
                            }
                        )
                    }
                }
            }
        }
    }
}