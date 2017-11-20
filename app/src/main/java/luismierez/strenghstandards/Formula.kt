package luismierez.strenghstandards

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