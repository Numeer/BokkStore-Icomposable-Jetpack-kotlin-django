import android.os.AsyncTask
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
            AsyncLoginTask(username, password) { loginResult ->
                if (loginResult == "Login Successful") {
                    errorMessage = ""
                    onLoginSuccess()
                } else {
                    errorMessage = "Login Failed"
                }
            }.execute()
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
class AsyncLoginTask(
    private val username: String,
    private val password: String,
    private val onLoginSuccess: (String) -> Unit
) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String {
        val api: LoginService = Retrofit.Builder()
            .baseUrl("http://192.168.1.7:8000")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(LoginService::class.java)

        val call = api.login(username, password)
        return try {
            val response = call.execute()
            if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.let {
                    if (it == "Login successful") {
                        return "Login Successful"
                    }
                }
            }
            "Login Failed"
        } catch (e: Exception) {
            "Login Failed: ${e.message}"
        }
    }

    override fun onPostExecute(result: String) {
        onLoginSuccess(result)
    }
}
