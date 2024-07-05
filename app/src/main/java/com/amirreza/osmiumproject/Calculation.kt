import android.util.Log
import org.apache.commons.math3.analysis.MultivariateFunction
import org.apache.commons.math3.optim.MaxEval
import org.apache.commons.math3.optim.PointValuePair
import org.apache.commons.math3.optim.SimpleValueChecker
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer

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

fun findCellLocation(ueDataList: List<UEData>, p0: Double, n: Double): Pair<Double, Double>? {
    if (ueDataList.isEmpty()) return null

    val distances = calculateDistances(ueDataList, p0, n)
    Log.d("Calculation", "Distances: $distances")

    val function = MultivariateFunction { point ->
        objectiveFunction(point[0], point[1], ueDataList, distances)
    }

    val optimizer = CMAESOptimizer(
        10000, 1e-9, true, 0, 10, null, false, SimpleValueChecker(1e-9, 1e-9)
    )

    val initialGuess = doubleArrayOf(0.0, 0.0)
    val sigma = doubleArrayOf(0.1, 0.1)
    val popSize = 50
    val meanX = ueDataList.map { it.x }.average()
    val meanY = ueDataList.map { it.y }.average()

    return try {
        val optimum: PointValuePair = optimizer.optimize(
            MaxEval(10000),
            org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction(function),
            GoalType.MINIMIZE,
            CMAESOptimizer.PopulationSize(popSize),
            CMAESOptimizer.Sigma(sigma),
            org.apache.commons.math3.optim.InitialGuess(initialGuess)
        )

        val solution = optimum.point
        Log.d("Calculation", "Solution: $solution")

        if (solution != null && solution.isNotEmpty()) {
            Pair(solution[0], solution[1])
        } else {
            Pair(meanX, meanY)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Pair(meanX, meanY)
    }
}

fun getCellLocation(ueDataList: List<UEData>): Pair<Double, Double>? {
    val p0 = -30.0 // Example value for P0
    val n = 2.0   // Example value for path loss exponent

    Log.d("Calculation", "UEDataList: $ueDataList")
    return findCellLocation(ueDataList, p0, n)
}
