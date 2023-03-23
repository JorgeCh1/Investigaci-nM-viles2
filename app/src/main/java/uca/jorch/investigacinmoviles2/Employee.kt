package uca.jorch.investigacinmoviles2

class Employee {
    var id: Int = 0
    var name: String? = null
    var salary: Float = 0.toFloat()

    override fun toString(): String {
        return " Id = $id\n Name = $name\n Salary = $salary"
    }
}