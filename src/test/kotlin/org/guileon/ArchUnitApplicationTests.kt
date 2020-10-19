package org.guileon

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AnalyzeClasses(packagesOf = [ArchUnitApplicationTests::class])
class ArchUnitApplicationTests {

    /* We try to follow this architecture:

                           +-----> Backends
    Clients +---> UseCases |
                           +-----> Domain
     */

    @ArchTest
    val `there are no package cycles` =
            slices()
                    .matching("$BASE_PACKAGE.(**)..")
                    .should()
                    .beFreeOfCycles()

    @ArchTest
    val `layers dependencies are honored`=
            layeredArchitecture()
                    .layer("Clients").definedBy("$CLIENTS_PACKAGE..")
                    .layer("Backends").definedBy("$BACKENDS_PACKAGE..")
                    .layer("UseCases").definedBy("$USECASES_PACKAGE..")
                    //.layer("Services").definedBy("$SERVICES_PACKAGE..")

                    .whereLayer("Clients").mayNotBeAccessedByAnyLayer()
                    .whereLayer("Backends").mayOnlyBeAccessedByLayers("UseCases")
                    .whereLayer("UseCases").mayOnlyBeAccessedByLayers("Clients")
                    // .whereLayer("Services").mayOnlyBeAccessedByLayers("UseCases")


    @ArchTest
    val `one boundary should not access another boundary` =
            slices()
                    .matching("$BOUNDARIES_PACKAGE.(*)")
                    .should()
                    .notDependOnEachOther()

    companion object {
        private val BASE_PACKAGE = ArchUnitApplicationTests::class.java.`package`.name

        private val DOMAIN_PACKAGE = "$BASE_PACKAGE.domain"
        private val MODEL_PACKAGE = "$DOMAIN_PACKAGE.model"
        private val SERVICES_PACKAGE = "$DOMAIN_PACKAGE.services"

        private val BOUNDARIES_PACKAGE = "$BASE_PACKAGE.boundaries"
        private val CLIENTS_PACKAGE = "$BOUNDARIES_PACKAGE.clients"
        private val BACKENDS_PACKAGE = "$BOUNDARIES_PACKAGE.backends"

        private val USECASES_PACKAGE = "$BASE_PACKAGE.usecases"

    }
}