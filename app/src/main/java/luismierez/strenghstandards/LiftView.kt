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
    abstract fun resolve(weight: Int, repetition: Int) : Int
    class Epley : Formula() {
        override fun resolve(weight: Int, repetition: Int): Int {
            return weight * (1 + (repetition/30))
        }
    }

    class Brzycki : Formula() {
        override fun resolve(weight: Int, repetition: Int): Int {
            return weight/(1.0278 - 0.0278 * repetition).toInt()
        }
    }
    
    class McGlothin : Formula() {
        override fun resolve(weight: Int, repetition: Int): Int {
            return ((100 * weight) / (101.3 - 2.67123 * repetition)).toInt()   
        }
    }
    
    class Lombardi : Formula() {
        override fun resolve(weight: Int, repetition: Int): Int {
            return (weight * Math.pow(repetition.toDouble(), .10)).toInt()
        }
    }
    
    class Mayhew : Formula() {
        override fun resolve(weight: Int, repetition: Int): Int {
            return (100 * weight) / (52.2 + (41.9 * Math.exp(-0.055 * repetition))).toInt()
        }
    }
}

