package com.example.submissions.DAO;

import com.example.submissions.DTO.CodeLanguage;

import java.util.List;

public interface CodeLanguageDAO {

    CodeLanguage getCodeLanguageIdByName(String codeLanguage);

    List<CodeLanguage> getCodeLanguages();

}
