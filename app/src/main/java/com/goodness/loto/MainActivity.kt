package com.goodness.loto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.goodness.loto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

  private val addBtn by lazy { binding.btnAdd }
  private val clearBtn by lazy { binding.btnClear }
  private val autoBtn by lazy { binding.btnAuto }
  private val numPick by lazy { binding.npNum }
  private val newTextViewList by lazy { binding.circles.children.filterIsInstance<TextView>().toList() }

  private var nums: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    numPick.minValue = 1
    numPick.maxValue = 45

    initAddBtn()
    initClearBtn()
    initAutoBtn()
  }

  private fun initClearBtn() {
    clearBtn.setOnClickListener {
      nums.fill(0)
      setNumsColor()
    }
  }

  private fun initAutoBtn() {
    autoBtn.setOnClickListener {
      val newNums = nums.map { if (it == 0) (1..45).random() else it }.toMutableList()
      newNums.sort()

      nums = newNums
      setNumsColor()
    }
  }

  private fun initAddBtn() {
    addBtn.setOnClickListener {
      when {
        nums.filter { it != 0 }.size >= 5 -> showToastMessage("클리어 해주세요")
        else -> {
          nums[0] = numPick.value
          nums.sort()

          setNumsColor()
        }
      }
    }
  }

  private fun setNumsColor() {
    nums.forEachIndexed { index, num ->
      if (num == 0) {
        newTextViewList[index].visibility = View.GONE
        return@forEachIndexed
      }

      newTextViewList[index].text = num.toString()
      newTextViewList[index].visibility = View.VISIBLE

      val bg = when (num) {
        in 1..10 -> R.drawable.circle_yellow
        in 11..20 -> R.drawable.circle_blue
        in 21..30 -> R.drawable.circle_red
        in 31..40 -> R.drawable.circle_gray
        else -> R.drawable.circle_green
      }

      newTextViewList[index].background = ContextCompat.getDrawable(this, bg)
    }
  }

  private fun showToastMessage(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
  }
}