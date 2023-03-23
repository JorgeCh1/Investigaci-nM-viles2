package uca.jorch.investigacinmoviles2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import uca.jorch.investigacinmoviles2.databinding.EjemploJsonBinding
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class EjemploJSONFragment : Fragment() {

    private var _binding: EjemploJsonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EjemploJsonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLoad.setOnClickListener {
            Toast.makeText(context, "Requesting...", Toast.LENGTH_SHORT).show()
            cargarDatosDesdeUrl(binding.editTextUrl.text.toString())
        }
    }

    private fun cargarDatosDesdeUrl(url: String) {
        val queue = Volley.newRequestQueue(context)
        Toast.makeText(context, "Queue Requested...", Toast.LENGTH_SHORT).show()

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val data = response.toString()
                    var jArray = JSONArray(data)
                    for (i in 0..jArray.length()-1)
                    {
                        var jobject = jArray.getJSONObject(i)
                        val userId = jobject.getInt("userId")
                        val id = jobject.getInt("id")
                        val title = jobject.getString("title")
                        val body = jobject.getString("body")
                        val datos = "userId: $userId \nid: $id \ntitle: $title \n body: $body \n"
                        binding.textViewData.text = datos
                        Toast.makeText(context, "Done...", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error en la solicitud...", Toast.LENGTH_LONG).show()
                }
            }) { error -> println(error.message) }

        queue.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}