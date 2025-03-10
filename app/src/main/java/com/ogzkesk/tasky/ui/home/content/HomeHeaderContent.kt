package com.ogzkesk.tasky.ui.home.content

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ogzkesk.tasky.R
import com.ogzkesk.tasky.ui.home.HomeScreenState
import com.ogzkesk.ui.theme.IndicatorBrush
import com.ogzkesk.ui.theme.lowAlpha
import com.ogzkesk.ui.theme.placeholder

@Composable
fun HomeHeaderContent(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
) {
    state.tasks?.let { tasks ->
        if(tasks.isNotEmpty()){
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                TaskCircularComponent(
                    completedTasks = tasks.filter { it.isCompleted }.size,
                    totalTasks = tasks.size,
                )
            }
        }
    }
}

@Composable
private fun TaskCircularComponent(
    modifier: Modifier = Modifier,
    completedTasks: Int,
    totalTasks: Int,
    backgroundIndicatorStrokeWidth: Float = 60F,
    foreGroundIndicatorStrokeWidth: Float = 60F,
    animationDuration: Int = 600,
    strokeCap: StrokeCap = StrokeCap.Round,
    backgroundIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.lowAlpha,
    foreGroundIndicatorColor: Brush = IndicatorBrush,
    bigTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(
        color = MaterialTheme.colorScheme.onSurface
    ),
    smallTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(
        color = MaterialTheme.colorScheme.placeholder
    ),
) {
    var allowedIndicatorValue by remember {
        mutableStateOf(totalTasks)
    }

    allowedIndicatorValue = if (completedTasks <= totalTasks) {
        completedTasks
    } else {
        totalTasks
    }

    var animatedIndicatorValue by remember {
        mutableStateOf(0f)
    }

    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue = allowedIndicatorValue.toFloat()
    }

    val percentage = (animatedIndicatorValue / totalTasks) * 100

    val sweepAngle by animateFloatAsState(
        targetValue = (2.4 * percentage).toFloat(),
        animationSpec = tween(animationDuration),
        label = ""
    )

    val receivedValue by animateIntAsState(
        targetValue = allowedIndicatorValue,
        animationSpec = tween(animationDuration),
        label = ""
    )

    Column(
        modifier = modifier
            .size(220.dp)
            .drawBehind {
                backgroundIndicator(
                    componentSize = size,
                    indicatorColor = backgroundIndicatorColor,
                    indicatorStrokeWidth = backgroundIndicatorStrokeWidth,
                    strokeCap = strokeCap
                )
                foreGroundIndicator(
                    componentSize = size,
                    sweepAngle = sweepAngle,
                    indicatorColor = foreGroundIndicatorColor,
                    indicatorStrokeWidth = foreGroundIndicatorStrokeWidth,
                    strokeCap = strokeCap
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmbeddedElements(
            bigText = stringResource(R.string.home_circular_completed_tasks, receivedValue),
            smallText = stringResource(R.string.home_circular_completed_tasks_desc, totalTasks),
            bigTextStyle = bigTextStyle,
            smallTextStyle = smallTextStyle
        )
    }
}

@Composable
private fun EmbeddedElements(
    bigText: String,
    smallText: String,
    bigTextStyle: TextStyle,
    smallTextStyle: TextStyle
) {
    Text(
        text = smallText,
        style = smallTextStyle,
        textAlign = TextAlign.Center
    )
    Text(
        text = bigText,
        style = bigTextStyle,
        fontWeight = FontWeight.Bold
    )
}

private fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
    strokeCap: StrokeCap
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = strokeCap
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

private fun DrawScope.foreGroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Brush,
    indicatorStrokeWidth: Float,
    strokeCap: StrokeCap
) {
    drawArc(
        size = componentSize,
        brush = indicatorColor,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = strokeCap
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}
