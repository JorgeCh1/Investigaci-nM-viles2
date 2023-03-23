package uca.jorch.investigacinmoviles2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import uca.jorch.investigacinmoviles2.databinding.EjemploXmlBinding
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException

class EjemploXMLFragment : Fragment() {
    private var text: String? = null

    private var _binding: EjemploXmlBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EjemploXmlBinding.inflate(inflater, container, false)
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
                    println(response)
                    try {
                        var datos = ""
                        var factory = XmlPullParserFactory.newInstance()
                        factory.isNamespaceAware = true
                        var parser = factory.newPullParser()
                        parser.setInput(response.reader())
                        var eventType = parser.eventType
                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            var tagname = parser.name
                            when (eventType) {
                                XmlPullParser.TEXT -> text = parser.text
                                XmlPullParser.END_TAG -> if (tagname.equals("clientId", ignoreCase = true)) {
                                    datos += "idCliente: \n"
                                } else if (tagname.equals("ContactDbId", ignoreCase = true)) {
                                    datos += "idContactoDb: $text\n"
                                } else if (tagname.equals("ContactId", ignoreCase = true)) {
                                    datos += "idContacto: $text\n"
                                } else if (tagname.equals("Errors", ignoreCase = true)) {
                                    datos += "Errores: $text\n"
                                } else if (tagname.equals("HasErrors", ignoreCase = true)) {
                                    datos += "Errores: $text\n\n"
                                }
                                else -> {
                                }
                            }
                            eventType = parser.next()
                        }
                        binding.textViewData.text = datos


                    } catch (e: XmlPullParserException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    Toast.makeText(context, "Done...", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
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