package ru.gendalf13666.nasapictures.ui.picture.view_pager

import android.os.Bundle
import android.view.*
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import coil.api.load
import ru.gendalf13666.nasapictures.ui.picture.PictureOfTheDayData
import ru.gendalf13666.nasapictures.ui.picture.PictureOfTheDayViewModel
import ru.gendalf13666.nasapictures.R
import ru.gendalf13666.nasapictures.databinding.TodayFragmentStartBinding

class TodayFragment : Fragment() {

    private var _binding: TodayFragmentStartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }
    private var shown = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TodayFragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.todayImageView.setOnClickListener {
            if (shown) {
                hideInfo()
            } else {
                showInfo()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(null).observe(
            viewLifecycleOwner,
            {
                renderData(it)
            }
        )
    }

    private fun showInfo() {
        shown = true
        val constraintSet = ConstraintSet()
        constraintSet.clone(context, R.layout.today_fragment_end)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(5.0f)
        transition.duration = 1000
        TransitionManager.beginDelayedTransition(binding.todayFragmentStartContainer, transition)
        constraintSet.applyTo(binding.todayFragmentStartContainer)
    }

    private fun hideInfo() {
        shown = false
        val constraintSet = ConstraintSet()
        constraintSet.clone(context, R.layout.today_fragment_start)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(5.0f)
        transition.duration = 1000
        TransitionManager.beginDelayedTransition(binding.todayFragmentStartContainer, transition)
        constraintSet.applyTo(binding.todayFragmentStartContainer)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    toast("Url is empty")
                } else {
                    with(binding) {
                        todayImageView.load(url) {
                            lifecycle(this@TodayFragment)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo_vector)
                        }
                        todayTitle.text = serverResponseData.title
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
            }
            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = TodayFragment()
    }
}
