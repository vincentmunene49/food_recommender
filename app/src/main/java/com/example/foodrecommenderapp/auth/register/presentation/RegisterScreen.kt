package com.example.foodrecommenderapp.auth.register.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodrecommenderapp.R
import com.example.foodrecommenderapp.common.UiEvent
import com.example.foodrecommenderapp.common.components.RecommenderAppButton
import com.example.foodrecommenderapp.common.components.RecommenderAppTextField
import com.example.foodrecommenderapp.navigation.Route
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavController
) {

    RegisterScreenContent(
        state = viewModel.state,
        onEvent = viewModel::event,
        uiEvent = viewModel.uiEvent,
        navController = navController
    )

}

@Composable
fun RegisterScreenContent(
    modifier: Modifier = Modifier,
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    navController: NavController,
    onClickSignUpWithGoogle: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true){
       uiEvent.collect{ event ->
              when(event){
                is UiEvent.OnSuccess -> {
                     navController.navigate(route = Route.Login.route)
                }
              }

       }
    }
    Scaffold(
        topBar = {
            IconButton(onClick = navController::navigateUp) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurface,
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Sign Up \uD83E\uDD13",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Create an account to get started.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            RecommenderAppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.username,
                onValueChange = { onEvent(RegisterEvent.OnTypeUsername(it)) },
                label = { Text(text = "Name") },
                placeholder = {
                    Text(
                        text = "John Doe",
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                ),
                textStyle = MaterialTheme.typography.bodyMedium,
                isError = state.isUsernameError,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }


            )
            Spacer(modifier = Modifier.height(16.dp))
            RecommenderAppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.email,
                onValueChange = { onEvent(RegisterEvent.OnTypeEmail(it)) },
                label = { Text(text = "Email") },
                placeholder = {
                    Text(
                        text = "johndoe@gmail.com",
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                ),
                textStyle = MaterialTheme.typography.bodyMedium,
                isError = state.isEmailError,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }


            )

            Spacer(modifier = Modifier.height(16.dp))
            RecommenderAppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.password,
                onValueChange = { onEvent(RegisterEvent.OnTypePassword(it)) },
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                ),
                textStyle = MaterialTheme.typography.bodyMedium,
                isError = state.isPasswordError,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                },
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(
                    '*'
                ),
                trailingIcon = {
                    IconButton(onClick = { onEvent(RegisterEvent.OnClickTogglePasswordVisibility(!state.isPasswordVisible)) }) {
                        Icon(
                            painter = if (state.isPasswordVisible) painterResource(id = R.drawable.baseline_visibility_24) else painterResource(
                                id = R.drawable.baseline_visibility_off_24
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }


            )
            Spacer(modifier = Modifier.height(16.dp))

            RecommenderAppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.confirmPassword,
                onValueChange = { onEvent(RegisterEvent.OnTypeConfirmPassword(it)) },
                label = { Text(text = "Confirm Password") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                textStyle = MaterialTheme.typography.bodyMedium,
                isError = state.isConfirmPasswordError,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                },
                visualTransformation = if (state.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(
                    '*'
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        onEvent(
                            RegisterEvent.OnClickToggleConfirmPasswordVisibility(
                                !state.isConfirmPasswordVisible
                            )
                        )
                    }) {
                        Icon(
                            painter = if (state.isConfirmPasswordVisible) painterResource(id = R.drawable.baseline_visibility_24) else painterResource(
                                id = R.drawable.baseline_visibility_off_24
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }


            )


            Spacer(modifier = Modifier.height(24.dp))

            RecommenderAppButton(onClick = { onEvent(RegisterEvent.OnClickRegister) }, text = "Register")

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Text(
                        modifier = Modifier,
                        text = "Already have an account?",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        modifier = Modifier
                            .clickable { navController.navigate(route = Route.Login.route) },
                        text = "Sign In",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = "Or With",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Divider(modifier = Modifier.weight(1f))

            }
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .googleSignUp()
                    .clickable { onClickSignUpWithGoogle() },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icons_google),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Sign Up with Google",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                }
            }


        }

    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.googleSignUp(): Modifier = composed {
    if (isSystemInDarkTheme()) {
        background(color = Color.Transparent).border(
            width = 1.dp,
            shape = RoundedCornerShape(4.dp),
            color = Color(0xFF334155)
        )
    } else {
        background(color = Color(0XFFF1F5F9))
    }

}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRegisterScreenContent() {
    FoodRecommenderAppTheme {
        RegisterScreenContent(
            state = RegisterState(),
            onEvent = {},
            uiEvent = flowOf(),
            navController = rememberNavController()
        )

    }

}