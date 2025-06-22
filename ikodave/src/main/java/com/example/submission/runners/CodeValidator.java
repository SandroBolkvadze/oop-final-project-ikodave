package com.example.submission.runners;

import java.util.Arrays;
import java.util.List;

public class CodeValidator {
    
    private static final List<String> DANGEROUS_PATTERNS = Arrays.asList(
        "Runtime.getRuntime()",
        "Runtime.exec(",
        "ProcessBuilder",
        "System.exit(",
        "System.gc()",
        "File.delete(",
        "File.createNewFile(",
        "Socket(",
        "URL.openConnection(",
        "Class.forName(",
        "ClassLoader",
        "Thread.start(",
        "while(true)",
        "for(;;)",
        "new byte[1000000]",
        "new int[1000000]",
        "import java.lang.Runtime",
        "import java.lang.ProcessBuilder",
        "import java.io.File",
        "import java.net.Socket",
        "import java.net.URL",
        "import java.lang.reflect"
    );
    
    public static void validateCode(String sourceCode) throws SecurityException {
        if (sourceCode == null || sourceCode.trim().isEmpty()) {
            throw new SecurityException("Empty code is not allowed");
        }
        
        String normalizedCode = sourceCode.toLowerCase().replaceAll("\\s+", " ");
        
        for (String pattern : DANGEROUS_PATTERNS) {
            if (normalizedCode.contains(pattern.toLowerCase())) {
                throw new SecurityException("Dangerous pattern detected: " + pattern);
            }
        }
        
        if (containsInfiniteLoop(normalizedCode)) {
            throw new SecurityException("Potential infinite loop detected");
        }
        
        if (containsExcessiveMemory(normalizedCode)) {
            throw new SecurityException("Excessive memory usage detected");
        }
    }
    
    private static boolean containsInfiniteLoop(String code) {
        return code.contains("while(true)") || 
               code.contains("for(;;)") ||
               code.contains("while (true)") ||
               code.contains("for (;;)");
    }
    
    private static boolean containsExcessiveMemory(String code) {
        return (code.contains("new byte[") && code.contains("1000000")) ||
               (code.contains("new int[") && code.contains("1000000")) ||
               (code.contains("new string[") && code.contains("1000000"));
    }
} 