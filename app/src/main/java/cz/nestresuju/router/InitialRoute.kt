package cz.nestresuju.router

/**
 * Classes representing possible routes on app launch.
 */
sealed class InitialRoute {

    object Login : InitialRoute()
    object Main : InitialRoute()
}