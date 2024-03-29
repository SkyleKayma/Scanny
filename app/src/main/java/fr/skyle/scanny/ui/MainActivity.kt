package fr.skyle.scanny.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import fr.skyle.scanny.enums.WifiEncryptionType
import fr.skyle.scanny.theme.SCTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Needed for insets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SCTheme {
                MainScreen(
                    onAddToContact = {
                        addContact(it.names, it.formattedName, it.org, it.title, it.tels, it.emails, it.addresses, it.urls)
                    },
                    onConnectToWifi = {
                        connectToWifi(it.ssid, it.encryptionType, it.password)
                    },
                )
            }
        }
    }

    private fun addContact(
        names: List<String>?,
        formattedName: String?,
        org: String?,
        title: String?,
        tels: List<String>?,
        emails: List<String>?,
        addresses: List<String>?,
        urls: List<String>?
    ) {
        val intent = Intent(Intent.ACTION_INSERT_OR_EDIT).apply {
            type = ContactsContract.Contacts.CONTENT_ITEM_TYPE

            formattedName?.let { name ->
                putExtra(ContactsContract.Intents.Insert.NAME, name)
            } ?: names?.let { names ->
                putExtra(ContactsContract.Intents.Insert.NAME, names.joinToString(";"))
            }

            tels?.forEachIndexed { index, phoneNumber ->
                val (extraPhoneType, extraPhone, phoneType) = when (index) {
                    0 -> Triple(
                        ContactsContract.Intents.Insert.PHONE_TYPE,
                        ContactsContract.Intents.Insert.PHONE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_HOME
                    )

                    1 -> Triple(
                        ContactsContract.Intents.Insert.SECONDARY_PHONE_TYPE,
                        ContactsContract.Intents.Insert.SECONDARY_PHONE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK
                    )

                    2 -> Triple(
                        ContactsContract.Intents.Insert.TERTIARY_PHONE_TYPE,
                        ContactsContract.Intents.Insert.TERTIARY_PHONE_TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_OTHER
                    )

                    else -> return@forEachIndexed
                }

                putExtra(extraPhoneType, phoneType)
                putExtra(extraPhone, phoneNumber)
            }

            emails?.forEachIndexed { index, email ->
                val (extraMailType, extraMail, mailType) = when (index) {
                    0 -> Triple(
                        ContactsContract.Intents.Insert.EMAIL_TYPE,
                        ContactsContract.Intents.Insert.EMAIL,
                        ContactsContract.CommonDataKinds.Email.TYPE_HOME
                    )

                    1 -> Triple(
                        ContactsContract.Intents.Insert.SECONDARY_EMAIL_TYPE,
                        ContactsContract.Intents.Insert.SECONDARY_EMAIL,
                        ContactsContract.CommonDataKinds.Email.TYPE_WORK
                    )

                    2 -> Triple(
                        ContactsContract.Intents.Insert.TERTIARY_EMAIL_TYPE,
                        ContactsContract.Intents.Insert.TERTIARY_EMAIL,
                        ContactsContract.CommonDataKinds.Email.TYPE_OTHER
                    )

                    else -> return@forEachIndexed
                }

                putExtra(extraMailType, mailType)
                putExtra(extraMail, email)
            }

            org?.let { organization ->
                putExtra(ContactsContract.Intents.Insert.COMPANY, organization)
            }

            title?.let { jobTitle ->
                putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle)
            }

            addresses?.firstOrNull()?.let { addr ->
                putExtra(ContactsContract.Intents.Insert.POSTAL_TYPE, ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME)
                putExtra(ContactsContract.Intents.Insert.POSTAL, addr)
            }

            val dataUrls = urls?.map { url ->
                ContentValues(2).apply {
                    put(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE
                    )
                    put(ContactsContract.CommonDataKinds.Website.URL, url)
                }
            }

            dataUrls?.let {
                putParcelableArrayListExtra(
                    ContactsContract.Intents.Insert.DATA,
                    ArrayList(it)
                )
            }
        }

        startActivity(Intent.createChooser(intent, null))
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun connectToWifi(ssid: String?, encryptionType: WifiEncryptionType?, password: String?) {
        val suggestion = WifiNetworkSuggestion.Builder()

        ssid?.let {
            suggestion.setSsid(it)
        }

        if (encryptionType != null && password != null) {
            when (encryptionType) {
                WifiEncryptionType.WEP -> {
                    // Not handle by the API
                }

                WifiEncryptionType.WPA_WPA2 ->
                    suggestion.setWpa2Passphrase(password)

                WifiEncryptionType.NONE ->
                    suggestion.setWpa3Passphrase(password)
            }
        }

        startActivity(
            Intent.createChooser(
                Intent(Settings.ACTION_WIFI_ADD_NETWORKS).apply {
                    putParcelableArrayListExtra(Settings.EXTRA_WIFI_NETWORK_LIST, arrayListOf(suggestion.build()))
                },
                null
            )
        )
    }
}