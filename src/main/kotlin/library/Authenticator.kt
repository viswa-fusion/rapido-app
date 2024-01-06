package library
import database.*
object Authenticator {
    fun authenticate(userName : String, password : String) : AuthenticationResponse{
        return when(val response = DbService.isValidCredential(userName,password)){
            is AuthenticationResponse.UserFound -> DbService.getUser(userName)
            else -> response
        }
    }
}