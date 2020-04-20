package cz.nestresuju.screens.about.epoxy

import com.airbnb.epoxy.EpoxyController

/**
 * Epoxy controller for displaying list of contacts.
 */
class ContactsController(
    private val onEmailClicked: (String) -> Unit,
    private val onPhoneClicked: (String) -> Unit,
    private val onMessageClicked: (String) -> Unit
) : EpoxyController() {

    override fun buildModels() {
        // TODO: remove testing data
        contact {
            id("contact-1")
            name("Petr Tobork")
            photoUrl("https://1.bp.blogspot.com/-k5m7Qpam7wI/XbSKYWQKcII/AAAAAAAAEg0/c9MquWeFsxAVj3BhxHKjsYImF6ii87MVgCNcBGAsYHQ/s400/stylish%2Bgirl%2Bpic%2Bwith%2Battitude-min.jpg")
            description("C# .NET Developer")
            email("petr@nestresuju.cz")
            phone("+420 777 123 456")
            onEmailClicked(onEmailClicked)
            onPhoneClicked(onPhoneClicked)
            onMessageClicked(onMessageClicked)
        }

        contact {
            id("contact-2")
            name("Josef Doležal")
            photoUrl("https://lh3.googleusercontent.com/proxy/BHHvBM7r1pdzKLkNr4xogzO58mpG0MEmL_6-MhUShAf5LA3T1WlhWHjKcIDlLRhWH4azSouHb70XsJMt8R1j4lNVsmSz0YF_wWBvLjlMkOHPliOmTyIl_cqxMFd85w")
            description("Student psychologie")
            email("josef@nestresuju.cz")
            phone("+420 602 448 654")
            onEmailClicked(onEmailClicked)
            onPhoneClicked(onPhoneClicked)
            onMessageClicked(onMessageClicked)
        }

        contact {
            id("contact-3")
            name("Adam Malý")
            photoUrl("https://i.pinimg.com/474x/81/a9/4a/81a94a7d0454d9ec58f1ea8db69d9b2e.jpg")
            description("Python Developer")
            email("adam@nestresuju.cz")
            phone("+420 725 065 984")
            onEmailClicked(onEmailClicked)
            onPhoneClicked(onPhoneClicked)
            onMessageClicked(onMessageClicked)
        }
    }
}