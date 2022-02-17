package at.birnbaua.sudoku_service.auth.user.jpa.projection.user

interface UserInfo {
    val username: String?
    val firstName: String?
    val lastName: String?
    val nickname: String?
    val profilePicture: ByteArray?
}