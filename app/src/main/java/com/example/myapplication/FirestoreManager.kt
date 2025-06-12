package com.example.myapplication

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreManager {
    private val db = FirebaseFirestore.getInstance()

    fun createUser(uid: String, email: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userData = hashMapOf(
            "email" to email,
            "createdAt" to FieldValue.serverTimestamp()
        )

        db.collection("users")
            .document(uid)
            .set(userData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    fun createPurpose(
        title: String,
        hour: String,
        days: List<String>,
        location: String?,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val purpose = hashMapOf(
            "title" to title,
            "hour" to hour,
            "days" to days,
            "location" to location,
            "createdAt" to Timestamp.now(),
            "userId" to uid
        )

        db.collection("purposes")
            .add(purpose)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    // Puoi aggiungere qui altre funzioni come updatePurpose, deletePurpose, getPurposes ecc.
}
