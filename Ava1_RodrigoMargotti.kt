// Classe Livro
class Livro(val titulo: String, val autor: String, var disponivel: Boolean = true) {
    fun exibirDetalhes() {
        println("Título: $titulo, Autor: $autor, Disponível: ${if (disponivel) "Sim" else "Não"}")
    }
}

// Classe Usuario
class Usuario(val nome: String) {
    val livrosEmprestados = mutableListOf<Livro>()

    fun emprestarLivro(livro: Livro, biblioteca: Biblioteca) {
        if (livro.disponivel) {
            biblioteca.emprestarLivro(livro, this)
            livrosEmprestados.add(livro)
            println("$nome emprestou o livro '${livro.titulo}'")
        } else {
            println("O livro '${livro.titulo}' não está disponível.")
        }
    }

    fun devolverLivro(livro: Livro, biblioteca: Biblioteca) {
        if (livrosEmprestados.remove(livro)) {
            biblioteca.devolverLivro(livro)
            println("$nome devolveu o livro '${livro.titulo}'")
        } else {
            println("$nome não tem o livro '${livro.titulo}' emprestado.")
        }
    }
}

// Classe Biblioteca
class Biblioteca(val nome: String) {
    private val livros = mutableListOf<Livro>()

    fun adicionarLivro(livro: Livro) {
        livros.add(livro)
    }

    fun exibirLivrosDisponiveis() {
        println("Livros disponíveis na biblioteca $nome:")
        livros.filter { it.disponivel }.forEach { it.exibirDetalhes() }
    }

    fun emprestarLivro(livro: Livro, usuario: Usuario) {
        if (livro.disponivel) {
            livro.disponivel = false
        } else {
            println("O livro '${livro.titulo}' já está emprestado.")
        }
    }

    fun devolverLivro(livro: Livro) {
        livro.disponivel = true
    }
}

// Fluxo principal
fun main() {
    // Criação da biblioteca
    val biblioteca = Biblioteca("Biblioteca Central")

    // Criação de alguns livros
    val livro1 = Livro("Dom Quixote", "Miguel de Cervantes")
    val livro2 = Livro("Guerra e Paz", "Liev Tolstói")
    val livro3 = Livro("1984", "George Orwell")

    // Adicionando livros à biblioteca
    biblioteca.adicionarLivro(livro1)
    biblioteca.adicionarLivro(livro2)
    biblioteca.adicionarLivro(livro3)

    // Exibindo livros disponíveis antes dos empréstimos
    biblioteca.exibirLivrosDisponiveis()

    // Criação de usuários
    val usuario1 = Usuario("Alice")
    val usuario2 = Usuario("Bob")

    // Empréstimo de livros
    usuario1.emprestarLivro(livro1, biblioteca)
    usuario2.emprestarLivro(livro2, biblioteca)

    // Exibindo livros disponíveis após os empréstimos
    biblioteca.exibirLivrosDisponiveis()

    // Devolução de livros
    usuario1.devolverLivro(livro1, biblioteca)
    usuario2.devolverLivro(livro2, biblioteca)

    // Exibindo livros disponíveis após as devoluções
    biblioteca.exibirLivrosDisponiveis()
}
