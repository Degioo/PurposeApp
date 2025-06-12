package com.example.myapplication

import android.Manifest
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class CreatePurposeFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var lat: Double? = null
    private var lon: Double? = null

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        return i.inflate(R.layout.fragment_create_purpose, c, false)
    }

    override fun onViewCreated(view: View, s: Bundle?) {
        val titleField = view.findViewById<EditText>(R.id.titleField)
        val daysField = view.findViewById<EditText>(R.id.daysField)
        val timeField = view.findViewById<EditText>(R.id.timeField)
        val sharedWithField = view.findViewById<EditText>(R.id.sharedWithField)
        val pickTimeBtn = view.findViewById<Button>(R.id.pickTimeBtn)
        val locBtn = view.findViewById<Button>(R.id.locBtn)
        val saveBtn = view.findViewById<Button>(R.id.saveBtn)
        val reminderSpinner = view.findViewById<Spinner>(R.id.reminderSpinner)

        pickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            TimePickerDialog(requireContext(), { _, h, m ->
                timeField.setText(String.format("%02d:%02d", h, m))
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        locBtn.setOnClickListener {
            val client = LocationServices.getFusedLocationProviderClient(requireContext())
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                client.lastLocation.addOnSuccessListener { loc: Location? ->
                    loc?.let {
                        lat = it.latitude
                        lon = it.longitude
                        Toast.makeText(context, "Posizione acquisita", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Permesso non concesso", Toast.LENGTH_SHORT).show()
            }
        }

        saveBtn.setOnClickListener {
            val title = titleField.text.toString()
            val days = daysField.text.toString().split(",").map { it.trim() }
            val timestamp = System.currentTimeMillis()
            val uid = auth.currentUser?.uid ?: return@setOnClickListener

            val basePurpose = Purpose(
                userId = uid,
                title = title,
                timestamp = timestamp,
                days = days,
                latitude = lat,
                longitude = lon
            )

            val sharedEmail = sharedWithField.text.toString().trim()
            if (sharedEmail.isNotEmpty()) {
                FirebaseAuth.getInstance().fetchSignInMethodsForEmail(sharedEmail)
                    .addOnSuccessListener { result ->
                        if (result.signInMethods?.isNotEmpty() == true) {
                            db.collection("users")
                                .whereEqualTo("email", sharedEmail)
                                .get()
                                .addOnSuccessListener { docs ->
                                    val sharedUserId = docs.firstOrNull()?.id
                                    val updatedPurpose = basePurpose.copy(sharedWith = listOfNotNull(sharedUserId))
                                    savePurpose(updatedPurpose)
                                }
                        } else {
                            Toast.makeText(context, "Email non registrata", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                savePurpose(basePurpose)
                val reminderOffset = when (reminderSpinner.selectedItemPosition) {
                    1 -> 5 * 60 * 1000  // 5 minuti
                    2 -> 10 * 60 * 1000
                    3 -> 30 * 60 * 1000
                    else -> null
                }
                if (reminderOffset != null) {
                    val reminderTime = timestamp - reminderOffset
                    ReminderScheduler.schedule(requireContext(), title, reminderTime)
                }

            }
        }
    }

    private fun savePurpose(purpose: Purpose) {
        db.collection("purposes").add(purpose)
            .addOnSuccessListener {
                Toast.makeText(context, "Proposito salvato", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Errore salvataggio", Toast.LENGTH_SHORT).show()
            }
    }
}
