package me.asayah.reservatium.features.shared

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import me.asayah.reservatium.R
import me.asayah.reservatium.components.extensions.setTextColorRes
import me.asayah.reservatium.databinding.CalendarDayLayoutBinding
import me.asayah.reservatium.databinding.CalendarMonthLegendBinding
import me.asayah.reservatium.databinding.ChooserDateRangeBinding
import me.asayah.reservatium.features.shared.base.BaseActivity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.*

class DateRangeChooserActivity: BaseActivity() {
    private lateinit var binding: ChooserDateRangeBinding
    private lateinit var startBackground: GradientDrawable
    private lateinit var endBackground: GradientDrawable

    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null

    private val currentDate = LocalDate.now()
    private val headerDateFormatter = DateTimeFormatter.ofPattern("EEE'\n'd MMM")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChooserDateRangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopAppBar(binding.toolbar)
        with(window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                statusBarColor = ContextCompat.getColor(this@DateRangeChooserActivity,
                        R.color.color_surface)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else statusBarColor = Color.GRAY
        }

        startBackground = ResourcesCompat.getDrawable(resources,
                R.drawable.shape_calendar_selected_start, null) as GradientDrawable
        endBackground = ResourcesCompat.getDrawable(resources,
                R.drawable.shape_calendar_selected_end, null) as GradientDrawable

        binding.calendarView.post {}

        val daysOfWeek = daysOfWeekFromLocale()
        binding.calendarLegendLayout.root.children.forEachIndexed { index, view ->
            (view as TextView).run {
                text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.getDefault())
                setTextColorRes(R.color.color_primary)
            }
        }

        val currentMonth = YearMonth.now()
        binding.calendarView.setup(currentMonth, currentMonth.plusMonths(10),
            daysOfWeek.first())
        binding.calendarView.scrollToMonth(currentMonth)

        class DayViewContainer(view: View): ViewContainer(view) {
            lateinit var day: CalendarDay
            val binding = CalendarDayLayoutBinding.bind(view)

            init {
                binding.root.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH && (day.date == currentDate
                                    || day.date.isAfter(currentDate))) {
                        val date = day.date
                        if (startDate != null) {
                            if (date < startDate || endDate != null) {
                                startDate = date
                                endDate = null
                            } else if (date != startDate) {
                                endDate = date
                            }
                        } else {
                            startDate = date
                        }
                        this@DateRangeChooserActivity.binding.calendarView.notifyCalendarChanged()
                        bindSummaryViews()
                    }
                }
            }
        }

        binding.calendarView.dayBinder = object: DayBinder<DayViewContainer> {
            override fun create(view: View): DayViewContainer {
                return DayViewContainer(view)
            }
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.exFourDayText
                val roundBgView = container.binding.exFourRoundBgView

                textView.text = null
                textView.background = null
                roundBgView.isVisible = false

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.text = day.day.toString()

                    if (day.date.isBefore(currentDate)) {
                        textView.setTextColorRes(R.color.color_secondary_text)
                    } else {
                        when {
                            startDate == day.date && endDate == null -> {
                                textView.setTextColorRes(android.R.color.white)
                                roundBgView.isVisible = true
                                roundBgView.setBackgroundResource(R.drawable.shape_calendar_selected_single)
                            }
                            day.date == startDate -> {
                                textView.setTextColorRes(android.R.color.white)
                                textView.background = startBackground
                            }
                            startDate != null && endDate != null && (day.date > startDate && day.date < endDate) -> {
                                textView.setTextColorRes(android.R.color.white)
                                textView.setBackgroundResource(R.drawable.shape_calendar_selected_middle)
                            }
                            day.date == endDate -> {
                                textView.setTextColorRes(android.R.color.white)
                                textView.background = endBackground
                            }
                            day.date == currentDate -> {
                                textView.setTextColorRes(R.color.color_secondary_text)
                                roundBgView.isVisible = true
                                roundBgView.setBackgroundResource(R.drawable.shape_calendar_today)
                            }
                            else -> textView.setTextColorRes(R.color.color_secondary_text)
                        }
                    }
                } else {

                    // This part is to make the coloured selection background continuous
                    // on the blank in and out dates across various months and also on dates(months)
                    // between the start and end dates if the selection spans across multiple months.

                    val startDate = startDate
                    val endDate = endDate
                    if (startDate != null && endDate != null) {
                        // Mimic selection of inDates that are less than the startDate.
                        // Example: When 26 Feb 2019 is startDate and 5 Mar 2019 is endDate,
                        // this makes the inDates in Mar 2019 for 24 & 25 Feb 2019 look selected.
                        if ((day.owner == DayOwner.PREVIOUS_MONTH &&
                                        startDate.monthValue == day.date.monthValue &&
                                        endDate.monthValue != day.date.monthValue) ||
                                // Mimic selection of outDates that are greater than the endDate.
                                // Example: When 25 Apr 2019 is startDate and 2 May 2019 is endDate,
                                // this makes the outDates in Apr 2019 for 3 & 4 May 2019 look selected.
                                (day.owner == DayOwner.NEXT_MONTH &&
                                        startDate.monthValue != day.date.monthValue &&
                                        endDate.monthValue == day.date.monthValue) ||

                                // Mimic selection of in and out dates of intermediate
                                // months if the selection spans across multiple months.
                                (startDate < day.date && endDate > day.date &&
                                        startDate.monthValue != day.date.monthValue &&
                                        endDate.monthValue != day.date.monthValue)
                        ) {
                            textView.setBackgroundResource(R.drawable.shape_calendar_selected_middle)
                        }
                    }
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = CalendarMonthLegendBinding.bind(view)
        }
        binding.calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                val monthTitle = "${month.yearMonth.month.name} ${month.year}"
                container.textView.monthNameTextView.text = monthTitle
            }
        }

        binding.actionButton.setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra(EXTRA_START_DATE, startDate)
                putExtra(EXTRA_END_DATE, endDate)
            })
            finish()
        }

        bindSummaryViews()
    }

    private fun bindSummaryViews() {
        binding.startDateTextView.run {
            if (startDate != null) {
                text = headerDateFormatter.format(startDate)
                setTextColorRes(R.color.color_primary_text)
            } else {
                text = getString(R.string.input_start_date)
                setTextColorRes(R.color.color_secondary_text)
            }
        }

        binding.endDateTextView.run {
            if (endDate != null) {
                text = headerDateFormatter.format(endDate)
                setTextColorRes(R.color.color_primary_text)
            } else {
                text = getString(R.string.input_end_date)
                setTextColorRes(R.color.color_secondary_text)
            }
        }

        // Enable save button if a range is selected or no date is selected at all
        binding.actionButton.isEnabled = endDate != null || (startDate == null && endDate == null)
    }

    private fun daysOfWeekFromLocale(): Array<DayOfWeek> {
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        var daysOfWeek = DayOfWeek.values()

        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
            val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
            daysOfWeek = rhs + lhs
        }
        return daysOfWeek
    }

    companion object {
        const val EXTRA_START_DATE = "extra:date:start"
        const val EXTRA_END_DATE = "extra:date:end"
        const val REQUEST_CODE_CHOOSE = 7
    }
}