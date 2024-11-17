package com.example.viewmodel.data.remote


sealed class NetworkResult<T>(

) {

    class Success<T>(val data: T) : NetworkResult<T>()

    class Error<T>(val message: String) : NetworkResult<T>()

    class Loading<T> : NetworkResult<T>()


    inline fun <R> map( transform :(data: T) -> R) : NetworkResult<R> =
        when(this){
            is Error -> Error(message)
            is Loading -> Loading()
            is Success -> Success(transform(data))
        }
    // Encadena transformaciones que retornan NetworkResult
    inline fun <R> then(transform: (data: T) -> NetworkResult<R>): NetworkResult<R> =
        when (this) {
            is Error -> Error(message)
            is Loading -> Loading()
            is Success -> transform(data)
        }




}

// Combina una lista de NetworkResult en un único NetworkResult de lista
fun <T> List<NetworkResult<T>>.combine(): NetworkResult<List<T>> {
    val successData = mutableListOf<T>()

    for (result in this) {
        when (result) {
            is NetworkResult.Success -> successData.add(result.data)
            is NetworkResult.Error -> return NetworkResult.Error(result.message)
            is NetworkResult.Loading -> return NetworkResult.Loading()
        }
    }
    return NetworkResult.Success(successData)
}

