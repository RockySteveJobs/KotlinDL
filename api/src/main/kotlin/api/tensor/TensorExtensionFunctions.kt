package api.tensor

import api.keras.shape.numElementsInShape
import api.keras.shape.reshape2DTo1D
import api.keras.shape.reshape3DTo1D
import api.keras.shape.reshape4DTo1D
import org.tensorflow.Tensor

fun Tensor<*>.convertTensorToFlattenFloatArray(): FloatArray {
    val tensorForCopying = this

    val shape = tensorForCopying.shape()
    val reshaped: FloatArray
    return when (shape.size) {
        1 -> {
            reshaped = FloatArray(shape[0].toInt()) { 0.0f }
            tensorForCopying.copyTo(reshaped)
            reshaped
        }
        2 -> {
            val dst =
                Array(shape[0].toInt()) { FloatArray(shape[1].toInt()) }
            tensorForCopying.copyTo(dst)
            reshaped = reshape2DTo1D(dst, numElementsInShape(shape).toInt())
            reshaped
        }
        3 -> {
            val dst = Array(shape[0].toInt()) {
                Array(shape[1].toInt()) {
                    FloatArray(shape[2].toInt())
                }
            }
            tensorForCopying.copyTo(dst)
            reshaped = reshape3DTo1D(dst, numElementsInShape(shape).toInt())
            reshaped
        }
        4 -> {
            val dst = Array(shape[0].toInt()) {
                Array(shape[1].toInt()) {
                    Array(shape[2].toInt()) {
                        FloatArray(shape[3].toInt())
                    }
                }
            }
            tensorForCopying.copyTo(dst)
            reshaped = reshape4DTo1D(dst, numElementsInShape(shape).toInt())
            reshaped
        }
        else -> {
            throw UnsupportedOperationException("Parsing for ${shape.size} dimensions is not supported yet!")
        }
    }
}

fun Tensor<*>.convertTensorToMultiDimArray(): Array<*> {
    val tensorForCopying = this

    val shape = tensorForCopying.shape()
    val dst: Array<*>

    return when (shape.size) {
        1 -> {
            val oneDimDst = FloatArray(shape[0].toInt()) { 0.0f }
            tensorForCopying.copyTo(oneDimDst)
            dst = Array(oneDimDst.size) { 0.0f }
            for (i in oneDimDst.indices) dst[i] = oneDimDst[i]
            dst
        }
        2 -> {
            dst = Array(shape[0].toInt()) { FloatArray(shape[1].toInt()) }

            tensorForCopying.copyTo(dst)
            dst
        }
        3 -> {
            dst = Array(shape[0].toInt()) {
                Array(shape[1].toInt()) {
                    FloatArray(shape[2].toInt())
                }
            }
            tensorForCopying.copyTo(dst)
            dst
        }
        4 -> {
            dst = Array(shape[0].toInt()) {
                Array(shape[1].toInt()) {
                    Array(shape[2].toInt()) {
                        FloatArray(shape[3].toInt())
                    }
                }
            }
            tensorForCopying.copyTo(dst)
            dst
        }
        else -> {
            throw UnsupportedOperationException("Parsing for ${shape.size} dimensions is not supported yet!")
        }
    }
}