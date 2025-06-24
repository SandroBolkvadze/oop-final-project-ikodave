package com.example.submission.Docker;

import java.io.FileOutputStream;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        try {
            System.out.println("\\n[Test 1] Attempting to write to /tmp/malicious.txt...");
            new FileOutputStream("tmp/malicious.txt").write("test".getBytes());
            System.out.println("==> FAILURE: Wrote to /tmp successfully.");
        } catch (Exception e) {
            System.out.println("==> SUCCESS: Blocked from writing to /tmp. Error: " + e.getClass().getName() + ": " + e.getMessage());
        }
    }
}