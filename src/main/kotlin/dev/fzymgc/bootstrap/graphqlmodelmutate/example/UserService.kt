package dev.fzymgc.bootstrap.graphqlmodelmutate.example

import org.jspecify.annotations.Nullable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * Example service demonstrating JSpecify annotations usage with Kotlin.
 * 
 * This shows how JSpecify annotations can be used to express nullability
 * in Kotlin code, particularly useful for Java interoperability.
 */
@Service
class UserService {
    
    /**
     * Find a user by ID.
     * 
     * @param userId The user ID to search for
     * @return The user if found, or null if not found
     */
    fun findUserById(userId: String): User? {
        // Example implementation - in real code this would query a database
        return if (userId == "1") {
            User(userId, "John Doe", "john@example.com")
        } else {
            null
        }
    }
    
    /**
     * Get the current user's email.
     * The email is guaranteed to be non-null per our domain model.
     * 
     * @param user The user object (must not be null)
     * @return The user's email address
     */
    fun getUserEmail(user: User): String {
        return user.email
    }
    
    /**
     * Update user preferences.
     * 
     * @param userId The user ID
     * @param preferences The new preferences (may be null to clear preferences)
     * @return Mono completing when the update is done
     */
    fun updateUserPreferences(userId: String, preferences: UserPreferences?): Mono<Void> {
        // Example reactive implementation
        return Mono.fromRunnable {
            println("Updating preferences for user $userId: ${preferences?.toString() ?: "cleared"}")
        }
    }
}

/**
 * Example user data class.
 */
data class User(
    val id: String,
    val name: String,
    val email: String
)

/**
 * Example preferences data class.
 */
data class UserPreferences(
    val theme: String,
    val language: String,
    val timezone: String?
)