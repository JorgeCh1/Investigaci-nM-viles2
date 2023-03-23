package uca.jorch.investigacinmoviles2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import uca.jorch.investigacinmoviles2.databinding.EjemploXmlBinding
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import android.widget.ListAdapter
import org.w3c.dom.Node
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.FileInputStream
import java.io.InputStream
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import org.w3c.dom.Element
import java.net.URL



class EjemploXMLFragment : Fragment() {
    private var text: String? = null

    private var _binding: EjemploXmlBinding? = null
    private val binding get() = _binding!!



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLoad.setOnClickListener {
            //Manda a request usando la url ingresada
            Toast.makeText(context, "Requesting...", Toast.LENGTH_SHORT).show()
            cargarDatosDesdeUrl(binding.editTextUrl.text.toString())
        }
    }
    private fun cargarDatosDesdeUrl(url: String) {
        //Crea una una solicitud usando Volley
        val queue = Volley.newRequestQueue(context)
        Toast.makeText(context, "Queue Requested...", Toast.LENGTH_SHORT).show()

        //Crea una solicitud GET para la URL proporcionada
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response -> //Devuelve la response del url
                try {
                    println(response)
                    try {
                        var datos = ""
                        val factory = XmlPullParserFactory.newInstance()
                        factory.isNamespaceAware = true
                        val parser = factory.newPullParser()
                        parser.setInput(response.reader())
                        var eventType = parser.eventType
                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            val tagname = parser.name
                            when (eventType) {
                                XmlPullParser.TEXT -> text = parser.text
                                XmlPullParser.END_TAG -> if (tagname.equals("id", ignoreCase = true)) {
                                    datos += "Id: \n"
                                } else if (tagname.equals("name", ignoreCase = true)) {
                                    datos += "Name: $text\n"
                                } else if (tagname.equals("brand", ignoreCase = true)) {
                                    datos += "Brand: $text\n"
                                }
                                /*else if (tagname.equals("year", ignoreCase = true)) {
                                    datos += "Año: $text\n"
                                } else if (tagname.equals("price", ignoreCase = true)) {
                                    datos += "Precio: $text\n\n"
                                }*/
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
