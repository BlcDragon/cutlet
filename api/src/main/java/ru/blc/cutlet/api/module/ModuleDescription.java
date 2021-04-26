package ru.blc.cutlet.api.module;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

@Data
@NoArgsConstructor
public class ModuleDescription {
    private File file;
    private String name, main;
    private String description, website, version;
    private List<String> depends, softDepends;
    private List<String> authors;

}
