package dev.fzymgc.bootstrap.graphqlmodelmutate.example;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

/**
 * Example Java class demonstrating JSpecify annotations.
 * 
 * This class shows how JSpecify annotations work in Java code
 * and how they interoperate with Kotlin's null-safety system.
 */
@Component
public class JavaInteropExample {
    
    /**
     * Process a string value that might be null.
     * 
     * @param input The input string (may be null)
     * @return A processed string, never null
     */
    public String processString(@Nullable String input) {
        if (input == null) {
            return "default";
        }
        return input.trim().toLowerCase();
    }
    
    /**
     * Get a configuration value that is guaranteed to exist.
     * 
     * @param key The configuration key (must not be null)
     * @return The configuration value (never null)
     */
    public String getRequiredConfig(String key) {
        // In a real implementation, this would throw if the config is missing
        return "config-value-for-" + key;
    }
    
    /**
     * Find an optional configuration value.
     * 
     * @param key The configuration key
     * @return The configuration value if present, null otherwise
     */
    @Nullable
    public String getOptionalConfig(String key) {
        // Example: return null for unknown keys
        if ("known.key".equals(key)) {
            return "known-value";
        }
        return null;
    }
}