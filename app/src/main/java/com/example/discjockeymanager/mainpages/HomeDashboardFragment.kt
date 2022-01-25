package com.example.discjockeymanager.mainpages

import android.content.Intent
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.example.discjockeymanager.*
import com.example.discjockeymanager.databinding.ChartMarkerBinding
import com.example.discjockeymanager.databinding.FragmentHomepageBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import org.json.JSONObject

/**
 * Dashboard page displaying metrics for events booked and links to social media
 */

class HomeDashboardFragment : Fragment() {
    private lateinit var binding: FragmentHomepageBinding
    private lateinit var viewModel: DashboardViewModel

    private val intFormatter = IntFormatter()

    //String entries for dropdown menu
    private val dropDownArray = arrayOf("7 days", "30 days", "90 days", "1 year")

    //Corresponding int values for number of days
    private val numDays = arrayOf(7, 30, 90, 365)

    //map of buttons to json keys
    private lateinit var buttonToJson: Map<ImageButton, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomepageBinding.inflate(layoutInflater)

        buttonToJson = mapOf(
            binding.imgBtnHomeFacebook to "company_facebook",
            binding.imgBtnHomeInstagram to "company_instagram",
            binding.imgBtnHomePinterest to "company_pinterest",
            binding.imgBtnHomeSoundcloud to "company_soundcloud",
            binding.imgBtnHomeTiktok to "company_tiktok",
            binding.imgBtnHomeTwitter to "company_twitter",
            binding.imgBtnHomeYoutube to "company_youtube",
            binding.imgBtnHomeWeb to "company_site",
        )

        //Setup viewmodel to store chart data
        viewModel = ViewModelProvider(requireActivity())[DashboardViewModel::class.java]
        viewModel.fetchData(requireContext()) {
            setupAvgEventsChart()
            setupUpcomingEventsChart()
            setupNewBookingsChart()

            //Setup dropdown menu to update charts
            binding.spinnerHomeUpcomingEvents.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, dropDownArray)
            binding.spinnerHomeUpcomingEvents.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    updateUpcomingEventsChart(numDays[position])
                    binding.textHomeUpcomingEvents.text = viewModel.upcomingEventsCount[position].toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

            binding.spinnerHomeNewBookings.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, dropDownArray)
            binding.spinnerHomeNewBookings.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    updateNewBookingsChart(numDays[position])
                    binding.textHomeNewBookings.text = viewModel.newBookingsCount[position].toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }

        //API request for social media links
        APIRequestHelper.jsonRequestWithAuth(
            requireContext(),
            RequestType.GET_USER_INFO,
            JSONObject(),
            {
                val companyInfo = it.getJSONObject("nodes").getJSONObject("comp_p_arr")

                //For each button, assign url to open on click
                for (btn in buttonToJson.keys) {
                    var url = companyInfo.getString(buttonToJson[btn]!!)

                    //If url is not valid, add https to front
                    if (url != "null" && !URLUtil.isValidUrl(url)) {
                        url = "https://$url"
                    }

                    if (URLUtil.isValidUrl(url)) {
                        btn.setOnClickListener {
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(browserIntent)
                        }
                        //Update button color if url is valid
                        btn.setColorFilter(Colors.getThemeColor(requireContext(), R.attr.mainColor))
                    }
                }
            })
    }

    private fun setupUpcomingEventsChart() {
        binding.textHomeUpcomingEvents.text = viewModel.upcomingEventsCount[0].toString()
        updateUpcomingEventsChart(7)
        val chart = binding.LineChartHomeUpcomingEvents
        formatLineChart(chart)
        chart.invalidate()
    }

    private fun updateUpcomingEventsChart(length: Int) {
        //Create new dataset from list of upcoming events based on number of days selected
        val series = LineDataSet(viewModel.upcomingEvents.subList(0, length), "")
        val chart = binding.LineChartHomeUpcomingEvents

        //Formatting dataset
        series.color = Colors.getThemeColor(requireContext(), R.attr.mainColor)
        series.valueFormatter = intFormatter
        series.setDrawHighlightIndicators(false)
        series.valueColors[0] = Colors.getThemeColor(requireContext(), R.attr.mainText)
        series.setDrawCircles(false)
        series.lineWidth = 2f
        if (length > 30) {
            series.setDrawValues(false)
        }
        chart.data = LineData(series)
        chart.invalidate()
    }

    private fun setupNewBookingsChart() {
        binding.textHomeNewBookings.text = viewModel.newBookingsCount[0].toString()
        updateNewBookingsChart(7)
        val chart = binding.LineChartHomeNewBookings
        formatLineChart(chart)
        chart.invalidate()
    }

    private fun updateNewBookingsChart(length: Int) {
        if (viewModel.newBookings.size > 0) {
            //Create new dataset from list of booked events based on number of days selected
            val series = LineDataSet(viewModel.newBookings.subList(viewModel.newBookings.size-length-1, viewModel.newBookings.size), "")
            val chart = binding.LineChartHomeNewBookings

            //Formatting dataset
            series.color = Colors.getThemeColor(requireContext(), R.attr.secondaryColor)
            series.valueFormatter = intFormatter
            series.setDrawHighlightIndicators(false)
            series.valueColors[0] = Colors.getThemeColor(requireContext(), R.attr.mainText)
            series.setDrawCircles(false)
            series.lineWidth = 2f
            if (length > 30) {
                series.setDrawValues(false)
            }
            chart.data = LineData(series)
            chart.invalidate()
        }
    }

    private fun setupAvgEventsChart() {
        binding.textHomeAvgEvents.text = viewModel.avgEventsCount.toString()
        val series = BarDataSet(viewModel.avgEvents, "")
        val chart = binding.BarChartHomeAvgEvents
        series.color = Colors.getThemeColor(requireContext(), R.attr.mainText)
        series.valueFormatter = intFormatter
        series.valueColors[0] = Colors.getThemeColor(requireContext(), R.attr.mainText)
        chart.data = BarData(series)
        formatBarChart(chart)
        chart.invalidate()
    }

    //Formats bar chart (average events)
    private fun formatBarChart(chart: BarChart) {
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.isDragEnabled = false
        chart.setScaleEnabled(false)
        chart.axisRight.isEnabled = false
        chart.axisLeft.isEnabled = false
        chart.xAxis.isEnabled = false
        chart.setDrawBorders(false)
        chart.setDrawGridBackground(false)
        chart.disableScroll()

        //Creates chart marker when user selects entry in chart
        chart.marker = object : MarkerView(requireContext(), R.layout.chart_marker) {
            override fun refreshContent(e: Entry?, highlight: Highlight?) {
                chartView = chart
                super.refreshContent(e, highlight)
                val b = ChartMarkerBinding.bind(findViewById(R.id.chartMarkerView))
                b.txtChartMarkerTitle.text = e?.data.toString()
                b.txtChartMarkerValue.text = "Events: ${e?.y?.toInt().toString()}"
            }

            override fun draw(canvas: Canvas?, posX: Float, posY: Float) {
                super.draw(canvas, posX, posY);
                getOffsetForDrawingAtPoint(posX, posY);
            }
        }
    }

    //Formats line chart (upcoming events and new bookings)
    private fun formatLineChart(chart: LineChart) {
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.setScaleEnabled(false)
        chart.axisRight.isEnabled = false
        chart.axisLeft.isEnabled = false
        chart.xAxis.isEnabled = false
        chart.setDrawBorders(false)
        chart.setDrawGridBackground(false)
        chart.disableScroll()

        //Creates chart marker when user selects entry in chart
        chart.marker = object : MarkerView(requireContext(), R.layout.chart_marker) {
            override fun refreshContent(e: Entry?, highlight: Highlight?) {
                chartView = chart
                super.refreshContent(e, highlight)
                val b = ChartMarkerBinding.bind(findViewById(R.id.chartMarkerView))
                b.txtChartMarkerTitle.text = e?.data.toString()
                b.txtChartMarkerValue.text = "Events: ${e?.y?.toInt().toString()}"
            }

            override fun draw(canvas: Canvas?, posX: Float, posY: Float) {
                super.draw(canvas, posX, posY);
                getOffsetForDrawingAtPoint(posX, posY);
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeDashboardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}