package palindromeinspector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import palindromeinspector.service.PalindromeService;

@RestController
@RequestMapping("/palindrome_checker")
public class PalindromeController {

    private final PalindromeService palindromeService;

    @Autowired
    public PalindromeController(PalindromeService palindromeService) {
        this.palindromeService = palindromeService;
    }


    @GetMapping("/{userName}/{text}")
    public String validatePalindrome(@PathVariable String userName, @PathVariable String text) {
        return palindromeService.validatePalindrome(text, userName);
    }
}