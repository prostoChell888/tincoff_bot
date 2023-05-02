package com.example.jooccodegen;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;


public class JoocCodeGenApplication {

    public static void main(String[] args) throws Exception {
        Database database = new Database()
                .withName("org.jooq.meta.extensions.liquibase.LiquibaseDatabase")
                .withProperties(
                        new Property().withKey("rootPath").withValue("migrations"),
                        new Property().withKey("scripts").withValue("master.xml")
                );

        Generate options = new Generate()
                .withGeneratedAnnotation(true)
                .withGeneratedAnnotationDate(false)
                .withNullableAnnotation(true)
                .withNullableAnnotationType("org.jetbrains.annotations.Nullable")
                .withNonnullAnnotation(true)
                .withNonnullAnnotationType("org.jetbrains.annotations.NotNull")
                .withJpaAnnotations(false)
                .withValidationAnnotations(true)
                .withSpringAnnotations(true)
                .withConstructorPropertiesAnnotation(true)
                .withConstructorPropertiesAnnotationOnPojos(true)
                .withConstructorPropertiesAnnotationOnRecords(true)
                .withFluentSetters(false)
                .withDaos(false)
                .withPojos(true)
                .withJooqVersionReference(false);

        Target target = new Target()
                .withPackageName("scrapper.domains.jooq")
                .withDirectory("scrapper/src/main/java/ru/tinkoff");

        Configuration configuration = new Configuration()
                .withGenerator(
                        new Generator()
                                .withDatabase(database)
                                .withGenerate(options)
                                .withTarget(target)
                )
                .withJdbc(new Jdbc()
                        .withDriver("org.postgresql.Driver")
                        .withUrl("jdbc:postgresql://localhost:5432/link_bot")
                        .withUser("postgres")
                        .withPassword("123"));

        GenerationTool.generate(configuration);
    }

}
