package com.example.data

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiCoachService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun queryGemini(prompt: String, systemPrompt: String? = null): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            // Provide intelligent high-quality swimming advice fallback if key is not configured yet
            return@withContext getLocalFallbackAdvice(prompt)
        }

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.8-flash:generateContent?key=$apiKey"

            val contentsArray = JSONArray()
            val contentObj = JSONObject()
            val partsArray = JSONArray()

            val textPart = JSONObject()
            textPart.put("text", prompt)
            partsArray.put(textPart)

            contentObj.put("parts", partsArray)
            contentsArray.put(contentObj)

            val rootJson = JSONObject()
            rootJson.put("contents", contentsArray)

            // System instruction if provided
            val finalSystemPrompt = systemPrompt ?: (
                "You are the Head Olympic Master Coach and Biomechanics Expert at 'Premium Swimming Academy'. " +
                "You provide elite, precise, actionable swimming guidance covering babies & infant water safety, " +
                "children stroke foundations, women's private aquatic fitness, men's conditioning & triathlon, " +
                "and competitive FINA squads. Structure your response with clear headings, bullet points, sets/reps, " +
                "and encouraging, high-end professional tone."
            )

            val systemInstruction = JSONObject()
            val sysParts = JSONArray()
            val sysPartText = JSONObject()
            sysPartText.put("text", finalSystemPrompt)
            sysParts.put(sysPartText)
            systemInstruction.put("parts", sysParts)
            rootJson.put("systemInstruction", systemInstruction)

            val requestBody = rootJson.toString().toRequestBody(jsonMediaType)
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                return@withContext "Coach note: Unable to connect to live telemetry (${response.code}). Falling back to academy guidance:\n\n" +
                        getLocalFallbackAdvice(prompt)
            }

            val parsedResponse = JSONObject(responseBody)
            val candidates = parsedResponse.optJSONArray("candidates")
            if (candidates != null && candidates.length() > 0) {
                val candidate = candidates.getJSONObject(0)
                val content = candidate.optJSONObject("content")
                val parts = content?.optJSONArray("parts")
                if (parts != null && parts.length() > 0) {
                    val text = parts.getJSONObject(0).optString("text")
                    if (text.isNotBlank()) {
                        return@withContext text
                    }
                }
            }

            return@withContext "No response generated. " + getLocalFallbackAdvice(prompt)
        } catch (e: Exception) {
            return@withContext "Coach connection notice (${e.localizedMessage ?: "Network error"}). Standard Academy Protocol:\n\n" +
                    getLocalFallbackAdvice(prompt)
        }
    }

    private fun getLocalFallbackAdvice(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("baby") || lower.contains("infant") || lower.contains("toddler") -> {
                """
                ★ PREMIUM INFANT AQUATICS PROTOCOL:
                1. Water Comfort & Vestibular Cues:
                   • Maintain direct skin-to-skin contact with relaxed shoulders in 32°C water.
                   • Use the 'Cheek-to-Cheek' cradle hold while singing rhythmic 3-beat cues before gentle water pours.
                2. Roll-to-Back Breath Recovery:
                   • Support the infant under occipital base and lower back.
                   • Gently initiate a 180° rotation while verbalizing 'Turn and Breathe'.
                3. Edge Grasping Safety:
                   • Encourage two-handed monkey crawl along the gutter ledge to promote independent egress.
                """.trimIndent()
            }
            lower.contains("drop") || lower.contains("hip") || lower.contains("sink") -> {
                """
                ★ BIOMECHANICAL FIX: DROPPED HIPS & LOWER BODY SINK:
                1. Head Position Neutrality:
                   • Look directly downward at the black line, not forward. The water line should cut across the crown of your head. Every centimeter the head raises, the hips sink by two centimeters.
                2. Press the Sternum ('Downhill Swimming'):
                   • Gently press the chest/buoyancy center downward into the water to pivot hips upward to the surface.
                3. High-Cadence Flutter Kick Drill:
                   • 4 x 50m: 6-Kick Switch Drill with alignment snorkel. Focus on initiating the kick from the hip flexor, not bending excessively at the knees.
                """.trimIndent()
            }
            lower.contains("sprint") || lower.contains("50m") || lower.contains("taper") || lower.contains("competitive") -> {
                """
                ★ ELITE SPRINT TAPER DRILL SET (45 MINS):
                • Warm-up:
                  - 300m Choice Aerobic (75m Free / 25m Scull)
                  - 4 x 50m Kick with board (Build to 85% effort on odds)
                • Speed Activation:
                  - 6 x 25m Assisted Blast with fins & paddles @ :40
                    (Focus on first 4 cycles explosive rate; zero breathing inside 15m)
                • Main Sprint Set:
                  - 4 x 50m Dive Starts with electronic split check @ 2:30
                    - Lap 1: 15m underwater dolphin breakout, max stroke rate (Target: 48-52 SPM)
                    - Lap 2: Strong wall finish with firm head-down touch.
                • Cool-down:
                  - 200m Easy Backstroke recovery with deep diaphragmatic breathing.
                """.trimIndent()
            }
            lower.contains("butterfly") || lower.contains("fly") -> {
                """
                ★ BUTTERFLY TIMING & UNDULATION ARCHITECTURE:
                1. Two-Kick Rule:
                   • Kick #1 (Entry): As hands slice thumb-first into the water shoulder-width apart, hips elevate.
                   • Kick #2 (Exit / Propulsion): Strong downward snap as hands accelerate past thighs, catapulting chest forward for breath.
                2. Breathing Vector:
                   • Chin stays low on the surface water-mirror; do not crane neck upward.
                3. Correction Drill:
                   • 4 x 50m: 'One-Arm Fly' (2 rights, 2 lefts, 2 full strokes) with fins to master seamless undulating rhythm without shoulder strain.
                """.trimIndent()
            }
            else -> {
                """
                ★ COACH'S TECHNICAL PRESCRIPTION:
                • Primary Focus: Optimize hydrodynamic streamline index and core rotational power.
                • Key Cues:
                  1. Keep body elongated: reach forward through the armpit on each hand extension.
                  2. Rotate along the longitudinal axis from hips, keeping the head steady.
                  3. Maintain high elbow catch (Early Vertical Forearm - EVF) before pull acceleration.
                • Recommended Set:
                  - 4 x 75m (25m Drill / 25m Build / 25m Max Pace) on 1:30 interval.
                """.trimIndent()
            }
        }
    }
}
