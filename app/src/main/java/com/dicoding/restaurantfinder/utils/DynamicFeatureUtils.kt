package com.dicoding.restaurantfinder.utils

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import com.dicoding.restaurantfinder.factory.DynamicFeaturePackageFactory
import com.dicoding.restaurantfinder.presentation.ui.screen.notFound.DFNotFoundScreen

class DynamicFeatureUtils {
    @Composable
    fun dfFavoriteScreen(paddingValues: PaddingValues,navigationToDetail: (String) -> Unit): Boolean {
        return loadDF(
            paddingValues = paddingValues,
            navigationToDetail = navigationToDetail,
            className = DynamicFeaturePackageFactory.DFFavorite.DF_FAVORITE,
            methodName = DynamicFeaturePackageFactory.DFFavorite.COMPOSE_METHOD_NAME
        )
    }

    @Composable
    fun ShowDFNotFoundScreen(paddingValues: PaddingValues) {
        DFNotFoundScreen(paddingValues)
    }

    @Composable
    private fun loadDF(
        paddingValues: PaddingValues,
        navigationToDetail: (String) -> Unit,
        className: String,
        methodName: String,
        objectInstance: Any = Any()
    ): Boolean {
        val dfClass = loadClassByReflection(className)
        Log.e("DynamicFeatureUtils", "Class: $dfClass")
        if (dfClass != null) {
            val composer = currentComposer
            val method = findMethodByReflection(
                dfClass,
                methodName
            )
            Log.e("DynamicFeatureUtils", "Method: $method")
            if (method != null) {
                val isMethodInvoked =
                    invokeMethod(method, objectInstance, paddingValues,navigationToDetail, composer, 0)
                if (!isMethodInvoked) {
                    Log.e("DynamicFeatureUtils", "Method not invoked")
                    ShowDFNotFoundScreen(paddingValues = paddingValues)
                    return false
                }
                return true
            } else {
                Log.e("DynamicFeatureUtils", "Method not found")
                ShowDFNotFoundScreen(paddingValues = paddingValues)
                return false
            }
        } else {
            Log.e("DynamicFeatureUtils", "Class not found")
            ShowDFNotFoundScreen(paddingValues = paddingValues)
            return false
        }
    }
}