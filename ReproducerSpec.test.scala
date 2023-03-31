import com.dimafeng.testcontainers.munit.TestContainerForAll
import com.dimafeng.testcontainers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import munit.FunSuite

class ReproducerSpec extends FunSuite with TestContainerForAll:
  test("migrations work") {
    withContainers { case pg: PostgreSQLContainer => 
      org.flywaydb.core.Flyway
        .configure()
        .dataSource(pg.jdbcUrl, pg.username, pg.password)
        .locations(s"classpath:/migrations")
        .cleanOnValidationError(true)
        .cleanDisabled(false)
        .load()
        .migrate()
    }
  }

  val containerDef = PostgreSQLContainer.Def(databaseName = "postgres", username = "postgres", password = "postgres", dockerImageName = DockerImageName.parse("postgres:15.1"))
