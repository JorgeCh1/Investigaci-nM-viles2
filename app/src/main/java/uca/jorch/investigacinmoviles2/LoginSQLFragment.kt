package uca.jorch.investigacinmoviles2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import uca.jorch.investigacinmoviles2.databinding.LoginSqlBinding
import uca.jorch.investigacinmoviles2.conexion.conexionSQL
import java.sql.PreparedStatement
import java.sql.SQLException

class LoginSQLFragment : Fragment() {

    private var _binding: LoginSqlBinding? = null
    private val binding get() = _binding!!

    private var SQLconexion = conexionSQL()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginSqlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val loginSuccessful = verifyCredentials(username, password)

            if (loginSuccessful) {
                it.findNavController().navigate(R.id.LoginSql)
            } else {
                Toast.makeText(context, "Credenciales incorrectas...", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun verifyCredentials(user: String, pw: String): Boolean {
        val validity = false

        try {
            val query: PreparedStatement =
                SQLconexion.dbConn()?.prepareStatement("SELECT * FROM users WHERE username=?")!!
            query.setString(1, user)
            val rs = query.executeQuery()

            if (rs.next()){
                if ((rs.getString(2) == user) && (rs.getString(3) == pw)){
                    return true
                }
            }

        } catch (ex: SQLException) {
            println("false por exception")
            return validity
        }
        println("false generico")
        return validity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}