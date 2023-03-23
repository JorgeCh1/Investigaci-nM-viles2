package uca.jorch.investigacinmoviles2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import uca.jorch.investigacinmoviles2.databinding.AutenticarLoginBinding
import uca.jorch.investigacinmoviles2.databinding.MenuOpcionesBinding

class MenuFragment : Fragment() {


    private var _binding: MenuOpcionesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = MenuOpcionesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnJSON.setOnClickListener(){
            it.findNavController().navigate(R.id.Menu_JSON)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}