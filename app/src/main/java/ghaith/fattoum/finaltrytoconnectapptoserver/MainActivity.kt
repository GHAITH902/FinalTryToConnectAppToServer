package ghaith.fattoum.finaltrytoconnectapptoserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// Find the button by its ID
        val lockdownButton: Button = findViewById(R.id.lockdownButton)

// Set a click listener for the button
        lockdownButton.setOnClickListener {
            // Call the function to send the fixed notification to all users
            sendLockdownNotificationToAllUsers()
        }

    }// This function sends a lockdown notification to all users
   private fun sendLockdownNotificationToAllUsers() {
        // Get all user tokens from your database or wherever they are stored
        val userTokens: List<String> = getAllUserTokens()
        // i need to change the userTokens, replace getAllUserTokens with the real Tokens

        // Loop through each token and send the notification
        userTokens.forEach { token ->
            sendLockdownNotificationToUser(token)
        }
    }

    }

    // This function sends a notification with fixed content to a specific FCM token
    fun sendLockdownNotificationToUser(token: String) {
        val notification = JSONObject()
        val notificationBody = JSONObject()

        notificationBody.put("title", "Lockdown")
        notificationBody.put("body", "hide and stay safe")

        notification.put("to", token)
        notification.put("notification", notificationBody)

        val fcmApiUrl = "https://fcm.googleapis.com/fcm/send"
        val request = object : JsonObjectRequest(
            Request.Method.POST, fcmApiUrl, notification,
            Response.Listener {
                // Handle success if needed
            },
            Response.ErrorListener {
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "key=YOUR_SERVER_KEY"
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        // Add the request to the request queue
        Volley.newRequestQueue().add(request)


    }


