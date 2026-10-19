package com.example

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testAcademyProgramsCoverAllDisciplines() {
    val categories = com.example.model.ProgramCategory.values()
    assertEquals(5, categories.size)
    val programs = com.example.data.AcademyData.programs
    assertTrue(programs.isNotEmpty())
    categories.forEach { cat ->
      assertTrue(programs.any { it.category == cat })
    }
  }
}
