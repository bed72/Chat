package com.bed.chat.presentation.shared.components

import kotlin.reflect.KClass

import androidx.annotation.StringRes

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy

import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.FloatingActionButtonDefaults

import com.bed.chat.R

import com.bed.chat.presentation.shared.routes.RoutesState
import com.bed.chat.presentation.shared.routes.TopLevelRoutes

@Composable
fun BottomBar(
    state: RoutesState,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        state.topLevelRoutes.forEach { routes ->
            if (routes == TopLevelRoutes.USERS)
                NavigationBarItemCenterComponent(state = state, routes = routes)
            else
                NavigationBarItemComponent(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    state = state,
                    routes = routes,
                )
        }
    }
}

@Composable
private fun RowScope.LabelBarItemComponent(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    isSelected: Boolean
) {
    Text(
        modifier = modifier,
        text = stringResource(id = title),
        style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.W700,
            color = if (isSelected) MaterialTheme.colorScheme.onSurface
            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4F)
        ),
    )
}

@Composable
private fun RowScope.NavigationBarItemCenterComponent(
    state: RoutesState,
    routes: TopLevelRoutes,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        modifier = modifier,
        shape = CircleShape,
        onClick = { state.navigateToTopLevel(routes) },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.primaryContainer,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp,
        )
    ) {
        Icon(
            contentDescription = stringResource(id = R.string.bottom_navigation_item_plus),
            painter = painterResource(id = R.drawable.ic_plus),
        )
    }
}

@Composable
private fun RowScope.NavigationBarItemComponent(
    modifier: Modifier = Modifier,
    state: RoutesState,
    routes: TopLevelRoutes,
) {
    val isSelected = state.currentRoute.isRouteInHierarchy(routes.route)
    val label = routes.title?.let { stringResource(it) }

    NavigationBarItem(
        modifier = modifier,
        selected = isSelected,
        alwaysShowLabel = true,
        onClick = { state.navigateToTopLevel(routes) },
        label = {
            routes.title?.let { LabelBarItemComponent(title = it, isSelected = isSelected) }
        },
        icon = {
            routes.icon?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = label,
                    tint = if (isSelected) MaterialTheme.colorScheme.onSurface
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4F)
                )
            }
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.background,
            selectedIconColor = MaterialTheme.colorScheme.onSurface,
            selectedTextColor = MaterialTheme.colorScheme.onSurface,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    )
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any { it.hasRoute(route) } == true
