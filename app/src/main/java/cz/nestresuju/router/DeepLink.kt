package cz.nestresuju.router

/**
 * Enum with possible notification deep-link redirects.
 */
enum class DeepLink(val clickAction: String) {

    DIARY("diary"),
    PROGRAM("program"),
    OUTPUT_TEST("outputTest");

    companion object {

        fun fromClickAction(clickAction: String): DeepLink? {
            return when (clickAction) {
                DIARY.clickAction -> DIARY
                PROGRAM.clickAction -> PROGRAM
                OUTPUT_TEST.clickAction -> OUTPUT_TEST
                else -> null
            }
        }
    }
}