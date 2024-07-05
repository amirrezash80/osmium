import org.apache.commons.math3.analysis.MultivariateFunction
import org.apache.commons.math3.optim.MaxEval
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer.PopulationSize
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer.Sigma

data class UEData(val x: Double, val y: Double, val power: Double)

fun calculateDistance(power: Double, p0: Double, n: Double): Double {
    return Math.pow(10.0, (p0 - power) / (10 * n))
}

fun calculateDistances(ueDataList: List<UEData>, p0: Double, n: Double): List<Double> {
    return ueDataList.map { calculateDistance(it.power, p0, n) }
}

fun objectiveFunction(cellX: Double, cellY: Double, ueDataList: List<UEData>, distances: List<Double>): Double {
    return ueDataList.indices.sumByDouble { i ->
        val estimatedDistance = Math.sqrt(Math.pow(cellX - ueDataList[i].x, 2.0) + Math.pow(cellY - ueDataList[i].y, 2.0))
        Math.pow(estimatedDistance - distances[i], 2.0)
    }
}

fun findCellLocation(ueDataList: List<UEData>, p0: Double, n: Double): Pair<Double, Double> {
    val distances = calculateDistances(ueDataList, p0, n)

    val function = MultivariateFunction { point ->
        objectiveFunction(point[0], point[1], ueDataList, distances)
    }

    val optimizer = CMAESOptimizer(10000, 1e-9, true, 0, 10, null, false, null)
    val initialGuess = doubleArrayOf(0.0, 0.0)
    val sigma = doubleArrayOf(0.1, 0.1)
    val popSize = 50

    val optimum = optimizer.optimize(
        ObjectiveFunction(function),
        GoalType.MINIMIZE,
        PopulationSize(popSize),
        Sigma(sigma),
        MaxEval(10000),
        org.apache.commons.math3.optim.InitialGuess(initialGuess)
    )

    val solution = optimum.point
    return Pair(solution[0], solution[1])
}

fun getCellLocation(ueDataList: List<UEData>): Pair<Double, Double> {
    val p0 = -30.0 // Example value for P0
    val n = 2.0   // Example value for path loss exponent

    val cellLocation = findCellLocation(ueDataList, p0, n)

    return cellLocation
}

//fun main() {
//    val ueDataList = listOf(
//        UEData(1.0, 1.0, -50.0),
//        UEData(2.0, 2.0, -60.0),
//        UEData(3.0, 3.0, -70.0)
//    )
//    val p0 = -30.0 // Example value for P0
//    val n = 2.0   // Example value for path loss exponent
//
//    val cellLocation = findCellLocation(ueDataList, p0, n)
//    println("Estimated cell location: (${cellLocation.first}, ${cellLocation.second})")
//}