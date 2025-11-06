package com.springdemo.demo.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

@Disabled("Temporarily disabled for refactoring")
class SolidPrinciplesTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.springdemo.demo");
    }

    @Test
    void classes_should_not_depend_on_concrete_classes_but_interfaces() {
        // Dependency Inversion Principle
        ArchRule rule = classes()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..ports..", "..domain..", "java..")
                .orShould().dependOnClassesThat().areAnnotatedWith("org.springframework.stereotype.Component");

        rule.check(classes);
    }

    @Test
    void no_classes_should_access_standard_streams() {
        NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.check(classes);
    }

    @Test
    void no_classes_should_throw_generic_exceptions() {
        NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.check(classes);
    }

    @Test
    void no_classes_should_use_java_util_logging() {
        NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.check(classes);
    }

    @Test
    void no_classes_should_use_jodatime() {
        NO_CLASSES_SHOULD_USE_JODATIME.check(classes);
    }

    @Test
    void interfaces_should_not_have_names_ending_with_interface() {
        classes()
                .that().areInterfaces()
                .should().haveSimpleNameNotEndingWith("Interface")
                .check(classes);
    }
}