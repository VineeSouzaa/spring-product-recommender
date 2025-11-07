package com.springdemo.demo.architecture;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

class HexagonalArchitectureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.springdemo.demo");
    }

    @Test
    void domain_should_not_depend_on_adapters() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..adapters..");

        rule.check(classes);
    }

    @Test
    void domain_should_not_depend_on_application_layer() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAPackage("..application..");

        rule.check(classes);
    }

    @Test
    void domain_should_only_depend_on_java_and_jakarta() {
        ArchRule rule = classes()
                .that().resideInAPackage("..domain..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        "..domain..",
                        "java..",
                        "jakarta.."
                );

        rule.check(classes);
    }

    @Test
    void adapters_should_not_depend_on_each_other() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..adapters.inbound..")
                .should().dependOnClassesThat()
                .resideInAPackage("..adapters.outbound..");

        rule.check(classes);
    }

    @Test
    void ports_should_be_interfaces() {
        ArchRule rule = classes()
                .that().resideInAPackage("..ports..")
                .should().beInterfaces();

        rule.check(classes);
    }

    @Test
    void inbound_ports_should_be_in_inbound_package() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("Inbound")
                .or().haveSimpleNameEndingWith("UseCase")
                .should().resideInAPackage("..ports.inbound..");

        rule.check(classes);
    }

    @Test
    void outbound_ports_should_be_in_outbound_package() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("Repository")
                .and().areInterfaces()
                .and().resideInAPackage("..domain..")
                .should().resideInAPackage("..ports.outbound..");

        rule.check(classes);
    }

    @Test
    void adapters_should_only_implement_ports() {
        ArchRule rule = classes()
                .that().resideInAPackage("..adapters..")
                .and().areNotInterfaces()
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..adapters..", "..application..", "..config..", "..ports..", "..domain..",
                        "java..", "jakarta..", "org.springframework..");
        rule.check(classes);
    }

    @Test
    void entities_should_only_exist_in_adapters() {
        ArchRule rule = classes()
                .that().areAnnotatedWith(jakarta.persistence.Entity.class)
                .should().resideInAPackage("..adapters.outbound.persistence..");

        rule.check(classes);
    }

    @Test
    void rest_controllers_should_only_exist_in_inbound_rest() {
        ArchRule rule = classes()
                .that().areAnnotatedWith(org.springframework.web.bind.annotation.RestController.class)
                .should().resideInAPackage("..adapters.inbound.rest..");

        rule.check(classes);
    }

    @Test
    void application_services_should_not_depend_on_adapters() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat()
                .resideInAPackage("..adapters..");

        rule.check(classes);
    }

    @Test
    void layered_architecture_should_be_respected() {
        layeredArchitecture()
                .consideringOnlyDependenciesInAnyPackage("com.springdemo.demo..")
                .layer("Domain").definedBy("..domain..")
                .layer("Application").definedBy("..application..")
                .layer("Adapters").definedBy("..adapters..")

                .whereLayer("Domain").mayNotAccessAnyLayer()
                .whereLayer("Application").mayOnlyAccessLayers("Domain")
                .whereLayer("Adapters").mayOnlyAccessLayers("Application", "Domain")

                .check(classes);
    }

    @Test
    void no_cycles_in_domain_packages() {
        slices()
                .matching("..domain.(*)..")
                .should().beFreeOfCycles()
                .check(classes);
    }
}

