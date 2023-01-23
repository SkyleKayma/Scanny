package fr.skyle.scanny.events

import fr.skyle.scanny.enums.ScanModalType
import kotlinx.coroutines.flow.MutableSharedFlow


val scanModalTypeEvent = MutableSharedFlow<ScanModalType?>()