package arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;


public class ArchTest {

    private final JavaClasses importedClasses = new ClassFileImporter().importPackages(
            "Servlets",
            "EJB",
            "port",
            "Dao",
            "Entity",
            "exception",
            "com.mycompany.minishopstateless");

    @Test
    public void layered_architecture_should_be_respected() {

        layeredArchitecture()

                .consideringOnlyDependenciesInLayers()

                .layer("Presentation").definedBy("..Servlets..")
                .layer("Rest").definedBy("..minishopstateless..")
                .layer("Port").definedBy("..port..")
                .layer("Service").definedBy("..EJB..")
                .layer("Persistence").definedBy("..Dao..")
                .layer("Domain").definedBy("..Entity..", "..exception..")

                .whereLayer("Presentation").mayOnlyAccessLayers("Port", "Domain")
                .whereLayer("Rest").mayOnlyAccessLayers("Domain")
                .whereLayer("Port").mayOnlyAccessLayers("Domain", "Port")
                .whereLayer("Service").mayOnlyAccessLayers("Port", "Persistence", "Domain")
                .whereLayer("Persistence").mayOnlyAccessLayers("Domain")

                .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
                .whereLayer("Rest").mayNotBeAccessedByAnyLayer()
                .whereLayer("Port").mayOnlyBeAccessedByLayers("Presentation", "Service")
                .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service")

                .check(importedClasses);
    }


    @Test
    public void servlets_ne_doivent_pas_dependre_des_dao() {
        noClasses()
                .that().resideInAPackage("..Servlets..")
                .should().dependOnClassesThat().resideInAPackage("..Dao..")
                .because("la presentation passe par les ports, pas par ProductDAO / OrderDAO")
                .check(importedClasses);
    }


    @Test
    public void ports_ne_doivent_pas_dependre_des_dao() {
        noClasses()
                .that().resideInAPackage("..port..")
                .should().dependOnClassesThat().resideInAPackage("..Dao..")
                .because("les ports decrivent le metier vers l'exterieur, pas l'acces donnees")
                .check(importedClasses);
    }
}
