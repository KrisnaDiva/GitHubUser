import com.krisna.diva.githubuser.utils.DateFormatter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateFormatterTest {
    private lateinit var formatDate: SimpleDateFormat
    private lateinit var calendar: Calendar
    private lateinit var currentDate: String

    @Before
    fun setup() {
        formatDate = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        calendar = Calendar.getInstance()
        currentDate = DateFormatter.getCurrentDate()
    }

    @Test
    fun getCurrentDate_returnsCorrectTime() {
        val expected = formatDate.format(calendar.time)
        assertEquals(expected, currentDate)
    }

    @Test
    fun getRelativeTime_returnsSeconds() {
        val result = DateFormatter.getRelativeTime(currentDate)
        assertEquals("Added 0 seconds ago", result)
    }

    @Test
    fun getRelativeTime_returnsMinutes() {
        calendar.add(Calendar.MINUTE, -30)
        val pastDate = formatDate.format(calendar.time)
        val result = DateFormatter.getRelativeTime(pastDate)
        assertEquals("Added 30 minutes ago", result)
    }

    @Test
    fun getRelativeTime_returnsHours() {
        calendar.add(Calendar.HOUR, -1)
        val pastDate = formatDate.format(calendar.time)
        val result = DateFormatter.getRelativeTime(pastDate)
        assertEquals("Added 1 hours ago", result)
    }
}