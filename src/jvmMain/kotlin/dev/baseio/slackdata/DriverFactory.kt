package dev.baseio.slackdata

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

actual class DriverFactory {
  actual fun createDriver(schema: SqlDriver.Schema): SqlDriver {
    return JdbcSqliteDriver(url = "jdbc:sqlite:SlackDB.db").also {
      schema.create(it)
    }
  }

  actual suspend fun createDriverBlocking(schema: SqlDriver.Schema): SqlDriver {
    return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also {
      schema.create(it)
    }
  }
}