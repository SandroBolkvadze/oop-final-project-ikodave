package com.example.submissions.DAO;

import com.example.submissions.DTO.CodeLanguage;

import java.util.List;

public interface CodeLanguageDAO {

    CodeLanguage getCodeLanguageByName(String codeLanguage);

    CodeLanguage getCodeLanguageById(int id);

    List<CodeLanguage> getCodeLanguages();

}
