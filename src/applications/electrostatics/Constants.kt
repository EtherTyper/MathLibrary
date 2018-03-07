package applications.electrostatics

import kotlin.math.PI
import kotlin.math.pow

val permitivity_free_space = 8.854_187_817 * 10.0.pow(-12)
val coulumbs_constant = 1 / (4 * PI * permitivity_free_space)

val graphical_scale = 50.0