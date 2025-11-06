package com.springdemo.demo.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@Disabled("Temporarily disabled for refactoring")
class DomainDrivenDesignRulesTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.springdemo.demo");
    }

    @Test
    void aggregates_should_be_in_aggregate_package() {
        ArchRule rule = classes()
                .that().resideInAPackage("..aggregate..")
                .should().bePublic()
                .andShould().notBeInterfaces();

        rule.check(classes);
    }

    @Test
    void value_objects_should_be_in_value_object_package() {
        ArchRule rule = classes()
                .that().resideInAPackage("..valueObject..")
                .should().bePublic();

        rule.check(classes);
    }

    @Test
    void domain_should_not_use_spring_annotations() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("org.springframework..");

        rule.check(classes);
    }

    @Test
    void domain_should_not_use_jpa_annotations() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("jakarta.persistence..");

        rule.check(classes);
    }

    @Test
    void repositories_should_only_be_accessed_through_ports() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("Repository")
                .and().areInterfaces()
                .and().resideInAPackage("..domain..")
                .should().onlyBeAccessed().byClassesThat()
                .resideInAnyPackage("..application..", "..adapters..", "..domain..");

        rule.check(classes);
    }
}