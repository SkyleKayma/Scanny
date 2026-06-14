# ARCHITECTURE.md

Ce document est la référence de structure que l'agent IA doit suivre pour ajouter, modifier ou créer
des fonctionnalités dans un projet KMP/CMP.

## Architecture du projet

Il faut différents modules pour accueillir les fonctionnalités de l'application:

- `app`
    - Module Android qui contient, entre autre, les fichiers suivants: Manifest, MainActivity,
      l'icone de l'app, et son build.gradle.kts.
- `designSystem`
    - Module dedie au design system (colors, typography, shapes, theme provider).
- `data`
    - Module qui contient la base de données (Room, Datastore), ainsi que les appels APIs et les
      Repositories.

## Bibliothèque du projet

Voici une liste non-exhaustive des bibliothèques à utiliser dans le projet au fur et à mesure que
ces besoins apparaissent:

- Vue/Ecrans: Jetpack Compose
- Base de données: Room
- Parseur Json: Kotlin Serialization
- Injection de dépendance: Hilt
- Navigation: Jetpack Navigation3
- Travailler avec des dates: KotlinX DateTime
- Appels réseaux: Retrofit
- Remonté de crashs: Firebase Crashlytics
- Annotation Processor: KSP
- Loading d'image: Coil

## Navigation

- Les routes sont centralisees dans `ui/main/navigation/Route.kt`.
- Le host principal reste dans `ui/main/navigation/RootNavHost.kt`.

Exemples de déclaration:

`navigation/Route.kt`

```kotlin
sealed interface Route {
    data object Login : Route

    data object Home : Route

    // Other routes
}
```

`navigation/RootNavHost.kt`

```kotlin
@Composable
fun RootNavigationHost(
    modifier: Modifier = Modifier,
) {
    val backStack = remember {
        mutableStateListOf<Route>(Route.Login)
    }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        },
        entryProvider = entryProvider {
            loginRouteImpl(backStack = backStack)

            homeRouteImpl(backStack = backStack)
        },
    )
}
```

## Architecture d'un écran

La création d'un nouvel écran consiste en la création, à minima, d'une "Route", d'une "RouteEntry",
d'un "Screen" et si besoin d'un ViewModel.

Ces différents fichiers sont crées en suivant cette architecture:

```
<namespaceProjet>/
└── ui/
    └── screen/
        └── <feature>/
            ├── entry/
            │   └── <Feature>Entry.kt
            └── ui/
                ├── <Feature>Route.kt
                ├── <Feature>Screen.kt
                ├── <Feature>ViewModel.kt
                ├── model/
                └── component/
```

- `<feature>.ui.component`: composants UI de la feature.
- `<feature>.ui.model`: modeles UI de la feature.

Exemples de déclaration:

`entry/LoginEntry.kt`

```kotlin
fun EntryProviderScope<Route>.loginRouteImpl(
    backStack: SnapshotStateList<Route>,
) {
    entry<Route.Login> {
        LoginRoute(
            navigateToHome = {},
        )
    }
}
```

Une `...Route` Compose:

- gere la collecte d'etat
- branche les callbacks
- declenche la navigation/evenements

`ui/LoginRoute.kt`

```kotlin
@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val errorMessage: String? by remember {
        mutableStateOf(null)
    }
    val showLoginErrorDialog by remember {
        mutableStateOf(false)
    }

    LaunchEffect(uiState.event) {
        uiState.event?.let { event ->
            when (event) {
                LoginViewModel.LoginEvent.LoginSuccess -> {
                    navigateToHome()
                }

                is LoginViewModel.LoginEvent.LoginError -> {
                    errorMessage = event.message
                    showLoginErrorDialog = true
                }
            }

            viewModel.eventDelivered()
        }
    }

    LoginScreen(
        isLoading = uiState.isLoading,
        username = uiState.username,
        password = uiState.password,
        errorMessage = uiState.errorMessage,
        onUsernameChange = viewmodel::setUsername,
        onPasswordChange = viewmodel::setPassword,
        onLoginClicked = viewmodel::login
    )

    if (showLoginErrorDialog) {
        // Show error dialog
    }
}
```

Un `...Screen` Compose:

- recoit les paramètres nécessaires à son affichage
- recoit des callbacks explicites

`ui/LoginScreen.kt`

```kotlin
@Composable
fun LoginScreen(
    isLoading: Boolean,
    username: String,
    password: String,
    errorMessage: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClicked: () -> Unit,
) {
    // Implémentation de l'écran de Login
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    AppTheme {
        LoginScreen(
            isLoading = false,
            username = "John",
            password = "",
            errorMessage = "Error message",
            onUsernameChange = {},
            onPasswordChange = {},
            onLoginClicked = {}
        )
    }
}
```

Un `...ViewModel`:

- expose un `StateFlow` pour l'etat de l'ecran,
- expose des events via type sealed si necessaire,
- orchestre les use cases (si présents dans le projet) et transformations utiles pour l'UI.

`ui/LoginViewModel.kt`

```kotlin
class LoginViewModel(
    private val authManager: AuthManager
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> by lazy { _state.asStateFlow() }

    fun onUsernameChange(username: String) {
        _state.update {
            it.copy(username = username)
        }
    }

    fun onPasswordChange(password: String) {
        _state.update {
            it.copy(password = password)
        }
    }

    fun login() {
        viewModelScope.launch {
            val state = _state.value

            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                // Do suspend login
                authManager.login(
                    username = username,
                    password = password
                )

                // Update state
                _state.update {
                    state.copy(
                        event = LoginEvent.LoginSuccess,
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.update {
                    state.copy(
                        event = LoginEvent.LoginError("Error ${e.message}"),
                    )
                }
            } finally {
                _state.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun eventDelivered() {
        _state.update {
            it.copy(event = null)
        }
    }

    data class LoginUiState(
        val isLoading: Boolean = false,
        val username: String = "",
        val password: String = "",
        val event: LoginEvent? = null
    )

    sealed interface LoginEvent {
        data object LoginSuccess : LoginEvent
        data class LoginError(val message: String?) : LoginEvent
    }
}
```

### Règles importantes

Routes:

- Une "Route" nécessitant un viewmodel, elle ne peut pas avoir de `Preview` Compose car celle-ci ne
  peut s'afficher.
- Tous les autres Composables/Components d'une "Route" doivent contenir une `Preview`.

Compose:

- Dans un `@Composable`, ajouter une ligne vide entre chaque declaration soeur dans un meme bloc.
    - Exemple: ajouter une ligne vide entre deux appels successifs `Text`, `Spacer`, `Button`, etc.
    - Objectif: ameliorer la lisibilite et garder un style homogene dans tous les ecrans.
- Tous les composants utilises doivent declarer un `modifier` explicitement a l'appel.
    - Le `modifier` doit expliciter la strategie de taille (ex: `fillMaxWidth()`, `fillMaxSize()`,
      `wrapContentWidth()`,
      `size(...)`, etc.).
    - Les `Route` et les `Screen` ne doivent pas exposer de parametre `modifier`, ils sont de base
      `fillMaxSize()`
    - Exemple: ne pas appeler `Text(...)` sans `modifier` de taille.
- Dans les appels de composables, `modifier = ...` doit etre le premier argument nomme.
    - Exemple:

```kotlin
Text(
    modifier = Modifier.wrapContentWidth(),
    text = "Text",
)
```

- Dans la signature des composables, `modifier` doit etre le premier parametre optionnel avec comme
  valeur par defaut:
  `modifier: Modifier = Modifier`.
    - Exemple:

```kotlin
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    style: ProjectButtonDefaults.Style,
    tint: ProjectButtonDefaults.Tint,
    size: ProjectButtonDefaults.Size,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
)
```

- Le `Screen` ne doit pas contenir davantage de `@Composable` que lui et sa `Preview`.
    - Les sous-composants doivent être implémentés dans `/ui/component/`
- Un ViewModel ne doit pas utiliser directement une entité venant de `data`
    - Il doit mapper vers son entité uniquement les champs nécessaires à l'UI si nécessaire

Previews:

- Le nom doit suivre `Preview` + nom du composable previsualise.
    - Les fonctions `@Preview` doivent etre `private`.
    - Exemples:
        - `private fun PreviewApp`
        - `private fun PreviewApp_WithMessage`
- Si une Preview a plusieurs paramètres "customisables", alors prévoir d'implémenter (ou d'utiliser
  s'il existe déjà) des PreviewParameters.

## Architecture du module `data`

Ce module contient:

- Les Repositories
- Les DAO et la Database
- Les Entités de la database
- Les APIs et les DTO associés
- Le mapping DTO -> Entités de la BDD

Ces différents fichiers sont crées en suivant cette architecture:

```
<namespaceProjet>/
└── db/
    ├── converter/
    ├── dao/
    ├── AppDatabase.kt
    ├── interceptor/
    ├── rest/
        └── user/
            └── post/
                └── PostUserDTO.kt
            └── response/
                └── UserDTO.kt
    ├── repository/
        └── user/
            ├── interface/
            │   └── UserRepository.kt
            └── UserRepositoryImpl.kt
    ├── util/
    └── vo/
        ├── junction/ 
            └── UserWithOrders.kt
        ├── mapper/
            └── UserMapper.kt
        └── UserEntity.kt
```

Exemples d'implémentation:

Règles importantes:

- Chaque Repository à son interface et l'implémentation de son interface

`repository/UserRepository.kt`

```kotlin
interface UserRepository {
    fun insertUser(user: User)

    suspend fun getUser(id: String): User?

    fun watchUser(id: String): Flow<User>
}
```

`repository/UserRepositoryImpl.kt`

```kotlin
class UserRepositoryImpl(
    private val userDao: UserDao
) {
    fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getUser(id: String): User? =
        userDao.getUser(id)

    fun watchUser(id: String): Flow<User> =
        userDao.watchUser(id)
}
```

## Architecture du module `designSystem`

Ce module contient:

- Le thème de l'app (AppColors, AppShapes, AppTypography, AppTheme)
- Les composants génériques à réutiliser (AppButton, AppTextField, AppDialog, AppStructure, etc.)
- Les fonts du projet

Ce module ne contient pas:

- Les composants graphiques dit "Design Produit"
    - Exemple: un composable réutilisable dans plusieurs écrans mais propre au projet

Ces différents fichiers sont crées en suivant cette architecture:

```
<namespaceProjet>/
└── designSystem/
    └── core/
      ├── button/
          ├── AppButton.kt
          └── AppButtonDefaults.kt
      └── dialog/
          ├── AppDialog.kt
          └── AppDialogDefaults.kt
    └── theme/
      ├── AppColors.kt
      ├── AppShapes.kt
      ├── AppTheme.kt
      └── AppTypography.kt
```

Exemple d'implémentation:

`AppColors.kt`

```kotlin
val Primary = Color(0xFF5060F3)
val Neutral = Color(0xFF84858C)
val SurfaceNeutral = Color(0xFFFFFAF4)
val OnSurfaceNeutral = Color(0xFFFFFAF4)
val OnSurfaceNeutralVariant = Color(0xFF3D3D41)
val SurfacePrimary = Color(0xFF202022)
val OnSurfacePrimary = Color(0xFFFFFAF4)
val OnSurfacePrimaryVariant = Color(0xFFAFB0B6)
val SurfaceSecondary = Color(0xFF5060F3)
val OnSurfaceSecondary = Color(0xFF202022)
val OnSurfaceSecondaryVariant = Color(0xFFFFFFFF)
val AccentPrimary = Color(0xFF3FF31E)
val AccentSecondary = Color(0xFFFFFAF4)
val AccentTertiary = Color(0xFFFFFAF4)
val BorderNeutral = Color(0xFFFCF1E3)
val Error = Color(0xFFDC2626)
val Warning = Color(0xFFF59E0B)
val Success = Color(0xFF22C55E)
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

@Immutable
data class AppColors(
    val primary: Color,
    val neutral: Color,
    val surfaceNeutral: Color,
    val onSurfaceNeutral: Color,
    val onSurfaceNeutralVariant: Color,
    val surfacePrimary: Color,
    val onSurfacePrimary: Color,
    val onSurfacePrimaryVariant: Color,
    val surfaceSecondary: Color,
    val onSurfaceSecondary: Color,
    val onSurfaceSecondaryVariant: Color,
    val accentPrimary: Color,
    val accentSecondary: Color,
    val accentTertiary: Color,
    val borderNeutral: Color,
    val error: Color,
    val warning: Color,
    val success: Color,
    val white: Color,
    val black: Color,
)

internal val LocalColors = staticCompositionLocalOf {
    AppColors(
        primary = Color.Unspecified,
        neutral = Color.Unspecified,
        surfaceNeutral = Color.Unspecified,
        onSurfaceNeutral = Color.Unspecified,
        onSurfaceNeutralVariant = Color.Unspecified,
        surfacePrimary = Color.Unspecified,
        onSurfacePrimary = Color.Unspecified,
        onSurfacePrimaryVariant = Color.Unspecified,
        surfaceSecondary = Color.Unspecified,
        onSurfaceSecondary = Color.Unspecified,
        onSurfaceSecondaryVariant = Color.Unspecified,
        accentPrimary = Color.Unspecified,
        accentSecondary = Color.Unspecified,
        accentTertiary = Color.Unspecified,
        borderNeutral = Color.Unspecified,
        error = Color.Unspecified,
        warning = Color.Unspecified,
        success = Color.Unspecified,
        white = Color.Unspecified,
        black = Color.Unspecified,
    )
}

val DarkColorScheme = AppColors(
    primary = Primary,
    neutral = Neutral,
    surfaceNeutral = SurfaceNeutral,
    onSurfaceNeutral = OnSurfaceNeutral,
    onSurfaceNeutralVariant = OnSurfaceNeutralVariant,
    surfacePrimary = SurfacePrimary,
    onSurfacePrimary = OnSurfacePrimary,
    onSurfacePrimaryVariant = OnSurfacePrimaryVariant,
    surfaceSecondary = SurfaceSecondary,
    onSurfaceSecondary = OnSurfaceSecondary,
    onSurfaceSecondaryVariant = OnSurfaceSecondaryVariant,
    accentPrimary = AccentPrimary,
    accentSecondary = AccentSecondary,
    accentTertiary = AccentTertiary,
    borderNeutral = BorderNeutral,
    error = Error,
    warning = Warning,
    success = Success,
    white = White,
    black = Black,
)
```

Pour le thème de l'app, si l'app ne gère qu'un seul thème initialement, alors ne prévoir qu'un seul
thème.
Le second viendra plus tard.

`AppTheme.kt`

```kotlin
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val rippleConfiguration =
        RippleConfiguration(
            color = AppTheme.colors.black,
            rippleAlpha = if (AppTheme.colors.black.luminance() > 0.5) {
                RippleAlpha(
                    pressedAlpha = 0.24f,
                    focusedAlpha = 0.24f,
                    draggedAlpha = 0.16f,
                    hoveredAlpha = 0.08f
                )
            } else {
                RippleAlpha(
                    pressedAlpha = 0.12f,
                    focusedAlpha = 0.12f,
                    draggedAlpha = 0.08f,
                    hoveredAlpha = 0.04f
                )
            }
        )

    CompositionLocalProvider(
        LocalColors provides colorScheme,
        LocalTypography provides CustomTypography,
        LocalShapes provides CustomShapes,
        LocalIndication provides ripple(),
        LocalRippleConfiguration provides rippleConfiguration,
        content = content,
    )
}

object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shape: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}
```

## Autres Conventions

- Toute nouvelle string doit etre ajoutee dans `strings.xml`.
- Le fichier `strings.xml` doit rester trie par ordre alphabetique des cles.
- Les cles de strings doivent etre en anglais, meme si la valeur est en francais.
- Le nom de la cle doit decrire le contenu et non l'usage ecran.
    - Exemple attendu: `home -> Home`.
    - Exemple a eviter: `home_title -> Home`.
- Ne jamais laisser de ligne vide a la fin des fichiers.
    - Aérer son code pour gagner en lisibilité
- Les commentaires doivent etre en anglais si jamais il doit y en avoir.
- `UI` s'écrit toujours en majuscule et non `Ui`