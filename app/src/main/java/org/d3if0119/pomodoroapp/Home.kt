package org.d3if0119.pomodoroapp


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import org.d3if0119.pomodoroapp.databinding.FragmentHomeBinding



class Home:Fragment(R.layout.fragment_home) {
    private var binding: FragmentHomeBinding? = null
    private var timer: CountDownTimer? = null
    private var timerRunning = false
    private val CHANNEL_ID = "my_channel"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.button?.setOnClickListener{
            if (!timerRunning){
                startTimer()
                timerRunning = true
                binding?.button?.text = "Pause"
            }
            else {
                timer?.cancel()
                timerRunning = false
                binding?.button?.text = "Start"
            }
        }
    }
    private fun startTimer(){
        val lengthTimer : Long = 25 * 60 * 1000
        timer = object : CountDownTimer(lengthTimer, 1000){
            override fun onTick(millisUntilFinished: Long) {
                val menit = millisUntilFinished / 1000 / 60
                val detik = millisUntilFinished / 1000 % 60
                binding?.textTimer?.text = "$menit:$detik"
            }

            override fun onFinish() {
                timer?.cancel()
                timerRunning = false

                val  builder = android.app.AlertDialog.Builder(requireContext())
                builder.setMessage("Mulai waktu istirahat?")
                    .setPositiveButton("Yes") {_, _ -> breakTimer()}
                    .setNegativeButton("No") {_, _ ->
                        startTimer()
                        timerRunning = true
                        binding?.button?.text = "Pause"
                    }
                    .show()

                notification()
            }
        }
        timer?.start()
    }
    private fun notification(){
        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Check if Android version is greater than or equal to Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create notification channel
            val channel = NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Build notification
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.sukses)
            .setContentTitle("Waktu telah selesai!")
            .setContentText("Your timer has completed.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Show notification
        notificationManager.notify(0, builder.build())
    }

    private fun breakTimer() {
        val timerLength : Long = 5 * 60 * 1000
        timer = object : CountDownTimer(timerLength, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                binding?.textTimer?.text = "$minutes:$seconds"

            }
            override fun onFinish() {
                timer?.cancel()
                timerRunning = false
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Mulai Belajar Lagi?")
                    .setPositiveButton("Yes") { _, _ -> startTimer() }
                    .setNegativeButton("No") { _, _ ->
                        Toast.makeText(requireContext(), "Yeyy, proses belajarmu telah selesai", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            }
        }
        timer?.start()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
