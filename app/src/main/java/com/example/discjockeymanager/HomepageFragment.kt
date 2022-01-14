package com.example.discjockeymanager

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
import com.example.discjockeymanager.databinding.ChartMarkerBinding
import com.example.discjockeymanager.databinding.FragmentHomepageBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomepageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomepageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomepageBinding

    private lateinit var viewModel: DashboardViewModel

    private val intFormatter = IntFormatter()
    private val dropDownArray = arrayOf("7 days", "30 days", "90 days", "1 year")
    private val numDays = arrayOf(7, 30, 90, 365)

    private lateinit var buttonToJson: Map<ImageButton, String>
/*
    private val upcomingEvents = ArrayList<Entry>()
    private val newBookings = ArrayList<Entry>()

    private val newBookingsCount = arrayOf(0,0,0,0)
    private val upcomingEventsCount = arrayOf(0,0,0,0)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

        viewModel = ViewModelProvider(requireActivity())[DashboardViewModel::class.java]
        viewModel.fetchData(requireContext()) {
            setupAvgEventsChart()
            setupUpcomingEventsChart()
            setupNewBookingsChart()

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
        APIRequestHelper.jsonRequestWithAuth(requireContext(), RequestType.GET_USER_INFO, JSONObject(), {
            val companyInfo = it.getJSONObject("nodes").getJSONObject("comp_p_arr")
            for (btn in buttonToJson.keys) {
                var url = companyInfo.getString(buttonToJson[btn])
                if (url != "null" && !URLUtil.isValidUrl(url)) {
                    url = "https://$url"
                }
                if (URLUtil.isValidUrl(url)) {
                    btn.setOnClickListener {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(browserIntent)
                    }
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
        val series = LineDataSet(viewModel.upcomingEvents.subList(0, length), "")
        val chart = binding.LineChartHomeUpcomingEvents
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
        Log.i("TEST", viewModel.newBookings.size.toString())
        if (viewModel.newBookings.size > 0) {
            val series = LineDataSet(viewModel.newBookings.subList(viewModel.newBookings.size-length-1, viewModel.newBookings.size), "")
            val chart = binding.LineChartHomeNewBookings
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

    private fun formatLineChart(chart: LineChart) {
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        //chart.isDragEnabled = false
        chart.setScaleEnabled(false)
        chart.axisRight.isEnabled = false
        chart.axisLeft.isEnabled = false
        chart.xAxis.isEnabled = false
        chart.setDrawBorders(false)
        chart.setDrawGridBackground(false)
        chart.disableScroll()
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomepageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomepageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}