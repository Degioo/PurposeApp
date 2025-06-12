package com.example.myapplication

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val list = mutableListOf<Purpose>()
    private val adapter = PurposeAdapter(list)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        val suggestion = SuggestionUtils.getDailySuggestion()
        Toast.makeText(requireContext(), "Suggerimento del giorno: $suggestion", Toast.LENGTH_LONG).show()

        loadPurposes()
    }

    private fun loadPurposes() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("purposes")
            .whereEqualTo("userId", uid)
            .get()
            .addOnSuccessListener { snap ->
                val nuovi = snap.documents.mapNotNull {
                    it.toObject(Purpose::class.java)?.copy(id = it.id)
                }
                adapter.setData(nuovi)

            }
            .addOnFailureListener {
                Toast.makeText(context, "Errore caricamento", Toast.LENGTH_SHORT).show()
            }
    }
}
