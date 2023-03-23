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
import uca.jorch.investigacinmoviles2.Employee
import uca.jorch.investigacinmoviles2.XmlPullParserHandler
import java.io.IOException


class EjemploXMLFragment : Fragment() {
    private var text: String? = null

    //el view binding
    private var _binding: EjemploXmlBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = EjemploXmlBinding.inflate(inflater, container, false)
        return binding.root

        buscar()
    }
    private fun buscar()
    {
        val listView = binding.listView
        var employees: List<Employee>? = null
        try {
            val parser = XmlPullParserHandler()
            val istream = resources.assets.open("employees.xml")
            employees = parser.parse(istream)

            val adapter =
                context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, employees) }
            listView.adapter = adapter

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}


