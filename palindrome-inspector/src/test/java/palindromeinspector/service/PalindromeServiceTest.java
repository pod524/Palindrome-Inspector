package palindromeinspector.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import palindromeinspector.model.PalindromeRequest;
import palindromeinspector.repository.PalindromeRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PalindromeServiceTest {

    @Mock
    private PalindromeRepository palindromeRepository;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private PalindromeService palindromeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(cacheManager.getCache("text")).thenReturn(cache);
    }

    @Test
    public void testValidatePalindrome_ValidPalindrome() {
        String palindromeText = "madam";
        String userName = "user";

        // Stubbing repository method
        when(palindromeRepository.findAll()).thenReturn(Collections.emptyList());

        String result = palindromeService.validatePalindrome(palindromeText, userName);

        assertEquals(palindromeText + " is a palindrome", result);

        // Verify that the cache.put method was called once
        verify(cache, times(1)).put(any(), any());
    }

    @Test
    public void testValidatePalindrome_InvalidInput() {
        String invalidText = "1234";
        String userName = "user";

        // Stubbing repository method
        when(palindromeRepository.findAll()).thenReturn(Collections.emptyList());

        String result = palindromeService.validatePalindrome(invalidText, userName);

        assertEquals("Text must contain no punctuation, numbers, or spaces, and must contain at least one character", result);

        // Verify that the cache.put method was not called
        verify(cache, never()).put(any(), any());
    }

    @Test
    public void testPopulateCacheOnStartup() {
        // Mock data
        PalindromeRequest request = new PalindromeRequest();
        request.setText("level");
        request.setIsValid(true);
        List<PalindromeRequest> requests = Collections.singletonList(request);

        // Stubbing repository method
        when(palindromeRepository.findAll()).thenReturn(requests);

        palindromeService.populateCacheOnStartup();

        // Verify that cache.put was called for each request
        verify(cache, times(requests.size())).put(any(), any());
    }
}
