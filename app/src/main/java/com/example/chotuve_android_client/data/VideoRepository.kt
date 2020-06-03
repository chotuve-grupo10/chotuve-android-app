package com.example.chotuve_android_client.data

import android.content.Context
import android.net.Uri
import com.example.chotuve_android_client.data.model.Video
import org.json.JSONException
import org.json.JSONObject

class VideoRepository(
    val context : Context?
) {

//    fun getVideos() : ArrayList<Video> {
//        // TODO: endpoint /api/home/ me dará 10 videos en formato json
//    }

    fun getVideosFromFile(filename: String): ArrayList<Video> {
        val videosList = ArrayList<Video>()

        try {
            // Load data
            val jsonString = context?.let { loadJsonFromAsset(filename, it) }
            val json = JSONObject(jsonString)
            val videos = json.getJSONArray("videos")

            // Get Recipe objects from data
            (0 until videos.length()).mapTo(videosList) {
                Video(videos.getJSONObject(it).getString("id"),
                    videos.getJSONObject(it).getString("title"),
                    videos.getJSONObject(it).getString("url"))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return videosList
    }

    private fun loadJsonFromAsset(filename: String, context: Context): String? {
        var json: String? = null

        try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: java.io.IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

}