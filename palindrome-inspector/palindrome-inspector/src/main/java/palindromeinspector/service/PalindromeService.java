package palindromeinspector.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import palindromeinspector.model.PalindromeRequest;
import palindromeinspector.repository.PalindromeRepository;

import jakarta.annotation.PostConstruct;

import java.util.List;

@Service
public class PalindromeService {

    private final PalindromeRepository palindromeRepository;
    private final CacheManager cacheManager;

    // Constructor injecting necessary dependencies and triggering cache population on startup
    public PalindromeService(PalindromeRepository palindromeRepository, CacheManager cacheManager) {
        this.palindromeRepository = palindromeRepository;
        this.cacheManager = cacheManager;
        populateCacheOnStartup(); // Populates the cache on application startup
    }

    // Method to validate palindrome
    @Cacheable("text") // Caches the results of this method with the key "text"
    public String validatePalindrome(String text, String userName) {
        Cache cache = cacheManager.getCache("text"); // Gets the cache by name
        String cachedValue = cache.get(text, String.class); // Retrieves cached value for the given text

        if (cachedValue != null) { // If the value is cached, return it
            return text + cachedValue;
        }

        String validationResult = validateText(text); // Validates the text format

        if (!validationResult.equals("valid")) { // If the text is not valid, return the validation message
            return validationResult;
        }

        boolean isPalindrome = isPalindrome(text); // Checks if the text is a palindrome

        saveToRepository(text, userName, isPalindrome); // Saves the validated text to the repository

        //simulates a delay
        /*
        try {
            Thread.sleep(6000); // Simulates a delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        */

        // Puts the result in the cache and returns the result with a message about being a palindrome or not
        cache.put(text, (isPalindrome ? " is a palindrome" : " is not a palindrome"));
        return text + (isPalindrome ? " is a palindrome" : " is not a palindrome");
    }

    // Method to populate the cache on application startup
    @PostConstruct
    public void populateCacheOnStartup() {
        List<PalindromeRequest> allRequests = palindromeRepository.findAll();
        Cache cache = cacheManager.getCache("text"); // Gets the cache by name
        allRequests.forEach(request -> {
            // Populates the cache with text and its palindrome validation result from the repository
            cache.put(request.getText(), (request.isValid ? " is a palindrome" : " is not a palindrome"));
        });
    }

    // Method to save a palindrome request to the repository
    private void saveToRepository(String text, String userName, boolean isPalindrome) {
        PalindromeRequest request = new PalindromeRequest(); // Creates a new PalindromeRequest object
        request.setUserName(userName); // Sets the username in the request
        request.setText(text); // Sets the text in the request
        request.setIsValid(isPalindrome); // Sets whether the text is a palindrome or not

        palindromeRepository.save(request); // Saves the request to the repository
    }

    // Method to validate the text format
    private String validateText(String text) {
        String pattern = "^[a-zA-Z]+$"; // Defines the pattern for valid text (only letters)
        if (!text.matches(pattern)) {
            return ("Text must contain no punctuation, numbers, or spaces, and must contain at least one character");
        }
        return "valid"; // Returns 'valid' if the text matches the pattern
    }

    // Method to check if a text is a palindrome
    private boolean isPalindrome(String text) {
        return text.contentEquals(new StringBuilder(text).reverse()); // Compares the text with its reverse
    }
}
