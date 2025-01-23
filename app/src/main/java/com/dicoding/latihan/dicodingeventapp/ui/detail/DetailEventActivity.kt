package com.dicoding.latihan.dicodingeventapp.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.latihan.dicodingeventapp.data.response.Event
import com.dicoding.latihan.dicodingeventapp.databinding.ActivityDetailEventBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding
    private val detailEventViewModel: DetailEventViewModel by viewModels()

    companion object {
        const val TAG = "DetailEventActivity"
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getStringExtra(EXTRA_ID)
        if (eventId != null) detailEventViewModel.getDetailEvent(eventId)

        detailEventViewModel.eventDetail.observe(this) {
            showEventDetail(it)
        }

        detailEventViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun showEventDetail(event: Event?){

        val registrant = event?.registrants
        val quota = event?.quota
        val remainingQuota = registrant?.let { quota?.minus(it) }

        val beginEvent = event?.beginTime
        val inFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val outFormatter = DateTimeFormatter.ofPattern("EEEE  dd MMM yyyy")
        val localDateTime = LocalDateTime.parse(beginEvent, inFormatter)
        val eventDate = localDateTime.format(outFormatter)

        val ivCover = binding.ivMediaCover
        if (event != null) {
            Glide.with(ivCover).load(event.mediaCover).into(ivCover)
            with(binding) {
                tvTitle.text = event.name
                tvOwner.text = "Diselenggarakan Oleh: ${event.ownerName}"
                tvSummary.text = event.summary
                tvQuotaNominal.text = "Quota Peserta: ${event.quota.toString()}"
                tvRegistrantsNominal.text = "Sisa Quota: ${remainingQuota.toString()}"
                tvBeginTime.text = "Acara Terbuka Hingga:\n${eventDate}"
                tvDescription.text = HtmlCompat.fromHtml(
                    event.description.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                btnRegister.setOnClickListener{
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                    startActivity(intent)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}