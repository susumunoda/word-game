package com.susumunoda.wordgame.ui.component

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

data class DragOptions(
    val onDragScaleX: Float = 1.0f,
    val onDragScaleY: Float = 1.0f,
    val onDropScaleX: Float = 1.0f,
    val onDropScaleY: Float = 1.0f,
)

enum class DragStatus { NONE, DRAGGING, DROPPED }

class DragContext<T> {
    private inner class DragTargetState(
        var dragStatus: MutableState<DragStatus> = mutableStateOf(DragStatus.NONE),
        var dragOffset: MutableState<Offset> = mutableStateOf(Offset.Zero),
        var dropTargets: MutableSet<DropTargetState> = mutableSetOf()
    ) {
        fun resetState() {
            dragStatus.value = DragStatus.NONE
            dragOffset.value = Offset.Zero
        }

        fun detachFromDropTargets() {
            // Update any attached drop targets to no longer be associated with this drag target
            dropTargets.forEach { dropTarget ->
                dropTarget.dragTargets.remove(this)
                // Update state - e.g. update hover state in case this was the only drag target
                dropTarget.updateState()
            }
            dropTargets.clear()
        }

        fun onDispose() = detachFromDropTargets()
    }

    private val dragTargetStates = mutableSetOf<DragTargetState>()

    fun resetDragTargets() {
        dragTargetStates.forEach {
            // Detach from and update any associated drop targets
            it.detachFromDropTargets()
            // Reset state - e.g. return to the initial position
            it.resetState()
        }
    }

    @Composable
    private fun rememberDragTargetState(
        data: T?,
        content: @Composable () -> Unit
    ): DragTargetState {
        val dragTargetState = remember(data, content) { DragTargetState() }
        DisposableEffect(data, content) {
            dragTargetStates.add(dragTargetState)
            onDispose {
                dragTargetState.onDispose()
                dragTargetStates.remove(dragTargetState)
            }
        }
        return dragTargetState
    }

    @Composable
    fun DragTarget(
        data: T? = null,
        dragOptions: DragOptions = DragOptions(),
        content: @Composable () -> Unit
    ) {
        val dragTargetState = rememberDragTargetState(data, content)
        val dragStatusState = dragTargetState.dragStatus
        val dragOffsetState = dragTargetState.dragOffset

        Box(
            modifier = Modifier
                .graphicsLayer {
                    when (dragStatusState.value) {
                        DragStatus.DRAGGING -> {
                            scaleX = dragOptions.onDragScaleX
                            scaleY = dragOptions.onDragScaleY
                        }

                        DragStatus.DROPPED -> {
                            scaleX = dragOptions.onDropScaleX
                            scaleY = dragOptions.onDropScaleY
                        }

                        else -> {
                            scaleX = 1f
                            scaleY = 1f
                        }
                    }
                }
                .offset {
                    val dragOffset = dragOffsetState.value
                    when (dragStatusState.value) {
                        DragStatus.DRAGGING, DragStatus.NONE -> {
                            IntOffset(
                                x = dragOffset.x.roundToInt(),
                                y = dragOffset.y.roundToInt()
                            )
                        }

                        DragStatus.DROPPED -> {
                            // When a drag event happens, scaling may be applied in `graphicsLayer`
                            // above (e.g. scaled down to 50% in size). In such a case, the `onDrag`
                            // callback of `detectDragGestures` will return a drag amount that has
                            // been scaled to accommodate for the applied graphical scaling (in this
                            // example, the returned offset will be 2x what it would normally be
                            // without scaling). When a such a target is dropped and a scale factor
                            // has been defined for the dropped state, then we must first "undo" the
                            // scaling that was applied by `detectDragGestures` for the dragged state,
                            // and then apply the appropriate scaling for the dropped state.
                            IntOffset(
                                x = (dragOffset.x * dragOptions.onDragScaleY / dragOptions.onDropScaleX).roundToInt(),
                                y = (dragOffset.y * dragOptions.onDragScaleY / dragOptions.onDropScaleY).roundToInt()
                            )
                        }
                    }
                }
                .onGloballyPositioned { coordinates ->
                    // Necessary to check if actually being dragged by the user and not moving due
                    // to an animating composable (e.g. AnimatedVisibility)
                    if (dragStatusState.value == DragStatus.DRAGGING) {
                        val dragTargetRect = coordinates.boundsInWindow()
                        // M:N relationship between drag and drop targets; i.e. one drag target can get
                        // dropped into one or more drop targets, and one drop target can have one or
                        // more drag targets dropped into it.
                        dropTargetStates.forEach { dropTargetState ->
                            if (dropTargetState.globalRect.contains(dragTargetRect.center)) {
                                dragTargetState.dropTargets.add(dropTargetState)
                                dropTargetState.dragTargets.add(dragTargetState)
                            } else {
                                dragTargetState.dropTargets.remove(dropTargetState)
                                dropTargetState.dragTargets.remove(dragTargetState)
                            }
                            // Update drop target, e.g. to show a hover indicator
                            dropTargetState.updateState()
                        }
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            dragStatusState.value = DragStatus.DRAGGING
                            // Provide an opportunity for drop targets to respond to drag targets
                            // being dragged back out (e.g. update state to no longer account for
                            // this drag target's data)
                            // Future: Consider what it would mean for incoming data to change after the
                            // initial drop. How to inform drop targets of re-drag events in this case?
                            dragTargetState.dropTargets.forEach { dropTarget ->
                                dropTarget.onDrag(data)
                            }
                        },
                        onDragEnd = {
                            if (dragTargetState.dropTargets.isEmpty()) {
                                dragStatusState.value = DragStatus.NONE
                                // Return to original position
                                dragOffsetState.value = Offset.Zero
                            } else {
                                dragStatusState.value = DragStatus.DROPPED
                                // It is the responsibility of the onDrop callback to update state
                                // in such a way that this DropTarget leaves the composition (if desired).
                                dragTargetState.dropTargets.forEach { dropTarget ->
                                    dropTarget.onDrop(data)
                                }
                            }
                        },
                        onDragCancel = {
                            dragTargetState.resetState()
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            dragOffsetState.value += dragAmount
                        }
                    )
                }
        ) {
            content()
        }
    }

    private inner class DropTargetState(
        var globalRect: Rect = Rect.Zero,
        val onDrop: (T?) -> Unit = {},
        val onDrag: (T?) -> Unit = {},
        val dragTargets: MutableSet<DragTargetState> = mutableSetOf(),
        val isHovered: MutableState<Boolean> = mutableStateOf(false)
    ) {
        fun updateState() {
            // We could have made dragTargets a mutableStateListOf, and DropTarget would get
            // recomposed whenever this list changed, preventing the need for this separate
            // isHovered state. However, because that would return a SnapshotStateList
            // (unfortunately, there is no mutableStateSetOf), we would need to do a !contains()
            // check before adding to dragTargets every time a drag target entered this drop target
            // area. This seemed less clean, and for now the isHovered boolean state does what we need.
            isHovered.value = dragTargets.isNotEmpty()
        }

        fun detachFromDragTargets() {
            // Update any attached drag targets to no longer be associated with this drop target
            dragTargets.forEach { it.dropTargets.remove(this) }
            dragTargets.clear()
        }

        fun onDispose() = detachFromDragTargets()
    }

    private val dropTargetStates = mutableListOf<DropTargetState>()

    @Composable
    private fun rememberDropTargetState(
        onDrop: (T?) -> Unit,
        onDrag: (T?) -> Unit,
        content: @Composable (Boolean) -> Unit
    ): DropTargetState {
        val dropTargetState = remember(onDrop, onDrag, content) {
            @Suppress("UNCHECKED_CAST")
            DropTargetState(
                onDrop = onDrop as ((Any?) -> Unit),
                onDrag = onDrag as ((Any?) -> Unit)
            )
        }
        DisposableEffect(onDrop, onDrag, content) {
            dropTargetStates.add(dropTargetState)
            onDispose {
                dropTargetState.onDispose()
                dropTargetStates.remove(dropTargetState)
            }
        }
        return dropTargetState
    }

    @Composable
    fun DropTarget(
        onDrop: (T?) -> Unit,
        // Not required because there may be no need to respond to re-drag events
        onDrag: (T?) -> Unit = {},
        content: @Composable (Boolean) -> Unit
    ) {
        val dropTargetState = rememberDropTargetState(onDrop, onDrag, content)
        Box(
            modifier = Modifier.onGloballyPositioned {
                dropTargetState.globalRect = it.boundsInWindow()
            }
        ) {
            content(dropTargetState.isHovered.value)
        }
    }
}

@Composable
fun <T> withDragContext(context: DragContext<T>, body: @Composable DragContext<T>.() -> Unit) {
    context.body()
}
