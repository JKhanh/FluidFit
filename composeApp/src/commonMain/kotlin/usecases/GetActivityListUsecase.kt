package usecases

import presentations.main.ActivityRepository

class GetActivityListUsecase(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke() = repository.getActivityList()
}