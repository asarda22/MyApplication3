package com.example.myapplication3.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.myapplication3.R
import com.example.myapplication3.data.entity.Payment
import com.google.accompanist.insets.systemBarsPadding
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Home(
    modifier : Modifier = Modifier,
    navController : NavController

){
    //Text(text = "Home screen list view")
    val viewModel : HomeViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()
    
    val pts = viewState.payments

    Surface(modifier = Modifier.fillMaxSize()){
        HomeContent(
            navController = navController,
            pts = pts
        )
    }
    /*Column(modifier = modifier)
    {
     PaymentList(
         list = viewState.payments
     )
    }*/
}

@Composable
fun HomeContent(
    navController : NavController,
    pts : List<Payment>
){
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate("payment")},
                contentColor = Color.Magenta,
                modifier = Modifier.padding(all = 20.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ){
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ){
            val appBarColor = MaterialTheme.colors.secondary.copy(alpha = 0.87f)

            HomeAppBar(
                backgroundColor = appBarColor
            )
            PaymentList(list = pts)
        }
       /* Column()
        {
            PaymentList(
                list = pts
            )
        }*/
    }

}

@Composable
private fun HomeAppBar(
    backgroundColor: Color
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        backgroundColor = backgroundColor,
        actions = {
            IconButton( onClick = {} ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription =
                                stringResource(R.string.search))
            }
            IconButton( onClick = {} ) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription =
                                stringResource(R.string.account))
            }
        }
    )
}

@Composable
private fun PaymentList(
    list : List<Payment>
){
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ){
        items(list){ item ->
            PaymentListItem(
                payment = item,
                onClick = {},
                modifier = Modifier.fillParentMaxWidth()
            )
        }
    }
}

@Composable
private fun PaymentListItem(
    payment: Payment,
    onClick : () -> Unit,
    modifier : Modifier = Modifier
){
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, paymentTitle, paymentCategory, icon, date) = createRefs()
        Divider(
            Modifier.constrainAs(divider){
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        //title
        Text(
            text = payment.paymentTitle,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(paymentTitle){
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin =  16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        //category
        Text(
            text = payment.paymentCategory,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(paymentCategory){
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin =  8.dp,
                    bias = 0f
                )
                top.linkTo(paymentTitle.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        //date
        Text(
            text = when{
                 payment.paymentDate != null -> {payment.paymentDate.formatToString()}
                 else -> Date().formatToString()
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(date){
                linkTo(
                    start = paymentCategory.end,
                    end = icon.start,
                    startMargin = 8.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                centerVerticallyTo(paymentCategory)
                top.linkTo(paymentTitle.bottom,6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
            }
        )

        //icon
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(icon){
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        )
        {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "check_mark"
            )
        }
    }
}

private fun Date.formatToString(): String {
    return SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(this)
}