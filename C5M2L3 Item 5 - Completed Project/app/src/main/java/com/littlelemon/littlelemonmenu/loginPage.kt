import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.gson.GsonBuilder
import com.littlelemon.littlelemonmenu.LoginService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Composable
fun loginPage(onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginResult by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "username", modifier = Modifier.padding(start = 25.dp)) //label
        TextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            singleLine = true
        )  // textfield
        Text(text = "Password", modifier = Modifier.padding(start = 25.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            val api: LoginService = Retrofit.Builder()
                .baseUrl("http://192.168.1.7:8000") // Replace with your base URL
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(LoginService::class.java)

            val call = api.login(username, password)
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("Response", response.body().toString())
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && responseBody == "Login successful") {
                            loginResult = "Login Successful"
                            Log.d("Success", "Logged In")
                            errorMessage = ""
                            onLoginSuccess()
                        } else {
                            errorMessage = "Login Failed"
                            Log.e("Response", "Invalid response body")
                        }
                    } else {
                        errorMessage = "Login Failed"
                        Log.e("Response", "Failed to login: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    errorMessage = "Login Failed: ${t.message}"
                    Log.e("Response", "Failed to login: ${t.message}")
                }
            })

        }) {
            Text(text = "Login")
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage)
        }
        if (loginResult.isNotEmpty()){
            Text(text = loginResult )
        }
    }
}
