package at.birnbaua.sudoku_service.auth.user.jpa.projection

@Suppress("unused")
interface SignUpRequest {
    val email: String?
    val firstName: String?
    val lastName: String?
    val password: String?
    val username: String?
}