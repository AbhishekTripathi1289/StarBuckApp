package com.example.starbuckapp

import android.content.Context
import com.example.starbuckapp.api.AssetManager
import com.example.starbuckapp.models.StarbuckData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

import java.io.ByteArrayInputStream
import java.io.InputStream

class AssetManagerTest {

    @Mock
    private lateinit var mockContext: Context

    private lateinit var assetManager: AssetManager

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        assetManager = AssetManager(mockContext)
    }

    @Test
    fun testLoadAssetFromFile() {
        val testJson = "[\n" +
                "  {\n" +
                "    \"store_name\": \"Banjara Hills\",\n" +
                "    \"address\": \"Lower Ground Floor, GVK One, Road Number 1, Banjara Hills\",\n" +
                "    \"City\": \"Hyderabad\",\n" +
                "    \"Longitude\": 78.45,\n" +
                "    \"Latitude\": 17.42\n" +
                "  },\n" +
                "  {\n" +
                "    \"store_name\": \"Kukatpally\",\n" +
                "    \"address\": \"Upper Ground Floor, Forum Sujana Mall, Kukatpally\",\n" +
                "    \"City\": \"Hyderabad\",\n" +
                "    \"Longitude\": 78.39,\n" +
                "    \"Latitude\": 17.48\n" +
                "  }]"
        val inputStream: InputStream = ByteArrayInputStream(testJson.toByteArray())
        `when`(mockContext.assets.open("starbuck.json")).thenReturn(inputStream)

        // Invoke the method and check if the result matches the expected data
        val result = assetManager.loadAssetFromFile()

        // Expected result based on the test JSON data
        val expectedList = listOf(
            StarbuckData(
                store_name = "Banjara Hills",
                address = "Lower Ground Floor, GVK One, Road Number 1, Banjara Hills",
                City = "Hyderabad",
                Longitude = 78.45,
                Latitude = 17.42
            ),
            StarbuckData(
                store_name = "Kukatpally",
                address = "Upper Ground Floor, Forum Sujana Mall, Kukatpally",
                City = "Hyderabad",
                Longitude =78.39,
                Latitude = 17.48
            ))

        assertEquals(expectedList, result)
    }
}


