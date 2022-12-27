package fr.skyle.scanny.events

import fr.skyle.scanny.enums.ModalType
import kotlinx.coroutines.flow.MutableSharedFlow


val modalTypeEvent = MutableSharedFlow<ModalType?>()