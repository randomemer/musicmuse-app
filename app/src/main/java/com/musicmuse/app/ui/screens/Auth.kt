@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.musicmuse.app.R
import com.musicmuse.app.models.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Auth(viewModel: AuthViewModel) {
  val context = LocalContext.current
  val token = stringResource(R.string.default_web_client_id)
  val contract = ActivityResultContracts.StartActivityForResult()
  val launcher = rememberLauncherForActivityResult(contract) { res ->
    val task = GoogleSignIn.getSignedInAccountFromIntent(res.data)

    try {
      val account = task.getResult(ApiException::class.java)
      val idToken = account?.idToken
      val credential = GoogleAuthProvider.getCredential(idToken, null)

      viewModel.signInWithCredential(credential)
    } catch (e: ApiException) {
      // TODO : Handle ApiException
      println("Error during google auth : ${e.message}")
      e.printStackTrace()
    }
  }

  Scaffold {
    Column(
      modifier = Modifier.fillMaxSize().padding(it).padding(top = 96.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Column(
        Modifier.fillMaxWidth(0.75f),
        verticalArrangement = Arrangement.spacedBy(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
          Image(
            painterResource(R.drawable.logo),
            "",
            modifier = Modifier.size(96.dp)
          )
          Text(
            "Dive into a world of music.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall
          )
        }

        Column(
          Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
          Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(100),
            onClick = {
              val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                  .requestIdToken(token).requestEmail().requestProfile().build()
              val client = GoogleSignIn.getClient(context, gso)
              launcher.launch(client.signInIntent)
            }) {
            Text("Sign In With Google", fontWeight = FontWeight.Bold)
          }

          Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(100),
            onClick = {}) {
            Text("Login With Facebook", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
