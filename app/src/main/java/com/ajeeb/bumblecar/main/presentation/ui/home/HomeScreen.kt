package com.ajeeb.bumblecar.main.presentation.ui.home

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajeeb.bumblecar.R
import com.ajeeb.bumblecar.common.core.parseDate
import com.ajeeb.bumblecar.common.core.poppinsFontFamily
import com.ajeeb.bumblecar.common.presentation.ui.picker.date.BumbleCarDatePicker
import com.ajeeb.bumblecar.common.presentation.ui.text_field.basic.BumbleCarBasicTextField
import kotlinx.coroutines.flow.Flow
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: State<HomeState>,
    sideEffect: Flow<HomeSideEffect>,
    onEvent: (HomeIntent) -> Unit,
) {

    val context = LocalContext.current

    /**Collect SideEffects using Orbit*/
    LaunchedEffect(Unit) {
        sideEffect.collect { action ->
            when (action) {
                is HomeSideEffect.ShowToast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }

                is HomeSideEffect.OpenDeepLink -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(action.deepLink))
                    context.startActivity(intent)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HomeScreenTopBar()
            Spacer(Modifier.height(8.dp))

            LazyColumn {
                item {
                    Column {

                        //Pick up TextField
                        BumbleCarBasicTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                            value = state.value.pickUpPoint,
                            label = stringResource(R.string.pickup_location),
                            placeholder = stringResource(R.string.enter_your_pick_up_location),
                            isError = state.value.isErrorOnPickUpPoint,
                            filteredSuggestions = state.value.pickUpSuggestions,
                            setOnValueChange = { newText ->
                                onEvent(HomeIntent.SetPickUpPoint(newText))
                            },
                            searchOnValueChange = {
                                onEvent(HomeIntent.SearchPickUpPoint(it))
                            })

                        Spacer(Modifier.height(4.dp))

                        //Drop off TextField
                        BumbleCarBasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            value = state.value.dropOffPoint,
                            label = stringResource(R.string.drop_off_point),
                            placeholder = stringResource(R.string.enter_your_drop_off_location),
                            isError = state.value.isErrorOnDropOffPoint,
                            filteredSuggestions = state.value.dropOffSuggestions,
                            setOnValueChange = { newText ->
                                onEvent(HomeIntent.SetDropOffPoint(newText))
                            },
                            searchOnValueChange = {
                                onEvent(HomeIntent.SearchDropOffPoint(it))
                            },
                        )

                        Spacer(Modifier.height(12.dp))

                        //DatePickers
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BumbleCarDatePicker(
                                modifier = Modifier.weight(1f),
                                placeholder = stringResource(R.string.pick_up_date),
                                value = state.value.pickUpDate,
                                isError = state.value.isErrorOnPickUpDate,
                                onClick = {
                                    openDatePicker(context = context, onDateSelected = { date ->
                                        onEvent(HomeIntent.SetPickUpDate(date))
                                    })
                                },
                            )

                            Spacer(Modifier.width(12.dp))

                            BumbleCarDatePicker(
                                modifier = Modifier.weight(1f),
                                placeholder = stringResource(R.string.drop_off_date),
                                value = state.value.dropOffDate,
                                isError = state.value.isErrorOnDropOffDate,
                                onClick = {
                                    openDatePicker(context = context, onDateSelected = { date ->
                                        onEvent(HomeIntent.SetDropOffDate(date))
                                    })
                                },
                            )
                        }

                        Spacer(Modifier.height(20.dp))

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(16.dp),
                            onClick = {
                                onEvent(HomeIntent.GenerateDeepLink)
                            },
                            colors = ButtonDefaults.buttonColors().copy(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(
                                text = "Search on Kayak",
                                style = MaterialTheme.typography.displayMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenTopBar(modifier: Modifier = Modifier) {
    TopAppBar(modifier = modifier, colors = TopAppBarDefaults.topAppBarColors().copy(
        containerColor = MaterialTheme.colorScheme.background
    ), title = {
        Row {
            Text(
                text = stringResource(R.string.bumble), style = TextStyle(
                    fontFamily = poppinsFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp,
                ), color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.car), style = TextStyle(
                    fontFamily = poppinsFontFamily(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                ), color = MaterialTheme.colorScheme.onBackground
            )
        }
    })
}

private fun openDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val cal = Calendar.getInstance()
    val calYear = cal[Calendar.YEAR]
    val calMonth = cal[Calendar.MONTH]
    val calDay = cal[Calendar.DAY_OF_MONTH]

    val dpd = DatePickerDialog(context, { _, year, monthOfYear, dayOfMonth ->

        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        val formattedDate = date.parseDate("yyyy-MM-dd")

        onDateSelected(formattedDate)

    }, calYear, calMonth, calDay)
    dpd.show()
}