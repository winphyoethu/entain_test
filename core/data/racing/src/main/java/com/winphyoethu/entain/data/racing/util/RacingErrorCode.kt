package com.winphyoethu.entain.data.racing.util

import com.winphyoethu.entain.common.result.ErrorCode

/**
 * Error Codes for Racing
 *
 * 1. HttpError
 * 2. UnexpectedError
 * 3. EmptyRacingResultError
 */
sealed class RacingErrorCode : ErrorCode {

    data object HttpError: RacingErrorCode()

    data object UnexpectedError: RacingErrorCode()

    data object EmptyRacingResultError: RacingErrorCode()

}