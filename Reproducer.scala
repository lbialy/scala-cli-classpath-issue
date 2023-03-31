object Reproducer extends App:
  println("Checking that migrations are available on classpath:")
  scala.io.Source.fromResource("migrations/V1.sql").getLines.foreach(println)
  println()

  org.flywaydb.core.Flyway
    .configure()
    .dataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres")
    .locations(s"classpath:migrations")
    .cleanOnValidationError(true)
    .cleanDisabled(false)
    .load()
    .migrate()
