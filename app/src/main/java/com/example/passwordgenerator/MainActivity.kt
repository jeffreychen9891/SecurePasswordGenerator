package com.example.passwordgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextKey = findViewById<EditText>(R.id.editTextKey)
        val editTextLength = findViewById<EditText>(R.id.editTextLength)
        val checkBoxRemovePunctuation = findViewById<CheckBox>(R.id.checkBoxRemovePunctuation)
        val buttonGenerate = findViewById<Button>(R.id.buttonGenerate)
        val textViewGeneratedPassword = findViewById<TextView>(R.id.textViewGeneratedPassword)
        val buttonCopy = findViewById<Button>(R.id.buttonCopy)

        buttonGenerate.setOnClickListener {
            val password = editTextPassword.text.toString()
            val key = editTextKey.text.toString()
            val lengthInput = editTextLength.text.toString()

            // defualt is 16
            val length = lengthInput.toIntOrNull() ?: 16

            if (password.isNotEmpty() && key.isNotEmpty()) {
                var generatedPassword = generatePassword(password, key, length)

                if (checkBoxRemovePunctuation.isChecked) {
                    generatedPassword = removePunctuationAndAdjustLength(generatedPassword, length)
                }

                textViewGeneratedPassword.text = generatedPassword
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCopy.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Encrypted Password", textViewGeneratedPassword.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied to Clipboard", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to generate password
    private fun generatePassword(password: String, key: String, length: Int): String {
        var hash = md5(password + key)

        repeat(length) {
            hash = md5(hash + "salt$it") // 添加额外的 salt
        }

        return seekPassword(hash, length)
    }

    // MD5 implementation
    private fun md5(input: String): String {
        val bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    // SeekPassword logic with dynamic length
    private fun seekPassword(hash: String, length: Int): String {
        val lower = "abcdefghijklmnopqrstuvwxyz".toList()
        val upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toList()
        val number = "0123456789".toList()
        val punctuation = ",.:;!?".toList()
        val alphabet = lower + upper + number + punctuation

        val result = StringBuilder()

        var i = 0
        while (result.length < length && i <= hash.length - 10) {
            val subHash = hash.substring(i, i + 10).toCharArray()
            var count = 0

            val mapIndex = subHash.map { c ->
                count = (count + c.code) % alphabet.size
                count
            }

            val skPwd = mapIndex.map { index -> alphabet[index] }

            val matched = listOf(
                skPwd.any { it in lower },
                skPwd.any { it in upper },
                skPwd.any { it in number },
                skPwd.any { it in punctuation }
            )

            if (matched.all { it }) {
                result.append(skPwd.joinToString(""))
            }

            i += 10
        }
        while (result.length < length) {
            val additionalHash = md5(hash + result.toString())
            additionalHash.forEach { char ->
                if (result.length < length) {
                    result.append(alphabet[char.code % alphabet.size])
                }
            }
        }

        return result.toString()
    }

    // Function to remove punctuation and adjust length deterministically
    private fun removePunctuationAndAdjustLength(password: String, length: Int): String {
        val cleanedPassword = password.replace(Regex("[\\p{Punct}]"), "")

        if (cleanedPassword.length < length) {
            val filler = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
            val additional = StringBuilder()

            val hash = md5(password)
            var hashIndex = 0

            while (additional.length + cleanedPassword.length < length) {
                additional.append(filler[hash[hashIndex % hash.length].code % filler.length])
                hashIndex++
            }

            return cleanedPassword + additional.toString()
        }
        return cleanedPassword.substring(0, length)
    }
}
