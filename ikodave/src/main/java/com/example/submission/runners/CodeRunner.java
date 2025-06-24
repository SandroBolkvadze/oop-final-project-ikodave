package com.example.submission.runners;


import com.example.submission.DTO.TestCase;
import java.util.List;

public interface CodeRunner {


    boolean testCodeMultipleTests(String userCode,
                                  long timeoutMillis,
                                  List<TestCase> testcases);


    boolean testCodeSingleTest(String userCode,
                               long timeoutMillis,
                               TestCase test);

}
