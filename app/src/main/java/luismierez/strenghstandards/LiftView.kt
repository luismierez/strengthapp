package luismierez.strenghstandards

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.lift_view.view.*

/**
 * Created by luismierez on 10/25/17.
 */
class LiftView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    var liftName: String
        set(value) {
            lift_name.text = value
        }
        get() = lift_name.text.toString()

    var liftMax: String
        set(value) {
            lift_max.text = resources.getString(R.string.one_rep_max, value)
        }
        get() = lift_max.text.toString()

    var formula: Formula = Formula.Epley()
        set(value) {
            field = value
            liftMax = field.resolve(repetitions_input.text.toString().safeToInt(), weight_input.text.toString().safeToInt()).toString()
        }

    init {
        View.inflate(context, R.layout.lift_view, this)

        val typeArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.LiftView,
                0, 0)

        liftName = typeArray.getString(R.styleable.LiftView_liftName)

        repetitions_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                liftMax = formula.resolve(s.toString().safeToInt(), weight_input.text.toString().safeToInt()).toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        weight_input.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                liftMax = formula.resolve(repetitions_input.text.toString().safeToInt(), weight_input.text.toString().safeToInt()).toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }


}

fun String.safeToInt() = if (this.isEmpty()) {
        0
    } else {
        this.toInt()
    }

sealed class Formula {
    abstract fun resolve(weight: Number, repetition: Number) : Float
    class Epley : Formula() {
        override fun resolve(weight: Number, repetition: Number): Float = (weight.toFloat() * (1F + repetition.toFloat()/30F))
    }

    class Brzycki : Formula() {
        override fun resolve(weight: Number, repetition: Number): Float =
                (weight.toFloat()/(1.0278F - 0.0278F * repetition.toFloat()))
    }
    
    class McGlothin : Formula() {
        override fun resolve(weight: Number, repetition: Number): Float =
                ((100F * weight.toFloat()) / (101.3F - 2.67123F * repetition.toFloat()))
    }
    
    class Lombardi : Formula() {
        override fun resolve(weight: Number, repetition: Number): Float =
                (weight.toFloat() * Math.pow(repetition.toDouble(), .10)).toFloat()
    }
    
    class Mayhew : Formula() {
        override fun resolve(weight: Number, repetition: Number): Float =
                ((100F * weight.toFloat()) / (52.2F + (41.9F * Math.exp(-0.055F * repetition.toDouble())))).toFloat()
    }
}

